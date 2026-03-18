package com.kheng.pos.features.store.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.store.entity.StoreContact;
import com.kheng.pos.databases.pg.store.entity.StoreInfo;
import com.kheng.pos.databases.pg.store.repository.StoreContactRepository;
import com.kheng.pos.databases.pg.store.repository.StoreInfoRepository;
import com.kheng.pos.databases.pg.userinfo.entity.UserInformation;
import com.kheng.pos.databases.pg.userinfo.repository.UserInformationRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.store.mapper.StoreInfoResponseMapper;
import com.kheng.pos.features.store.dto.request.StoreInfoRequest;
import com.kheng.pos.features.store.dto.response.StoreInfoResponse;
import com.kheng.pos.features.store.service.contract.StoreService;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import com.kheng.pos.features.user.service.contract.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreInfoRepository storeInfoRepository;
    private final StoreContactRepository storeContactRepository;
    private final UserInfoService userInfoService;
    private final UserInformationRepository userInformationRepository;

    @Override
    public BaseApiResponse<StoreInfoResponse> createStore(StoreInfoRequest storeInfoRequest,
                                                          UserProfileResponse userInfo) {

        BaseApiResponse<StoreInfoResponse> response = new BaseApiResponse<>();

        if (storeInfoRequest == null) {
            throw new AppException("Store data is required",
                    HttpStatus.BAD_REQUEST, "INVALID_DATA");
        }

        if (userInfo == null) {
            throw new AppException("User information is required",
                    HttpStatus.BAD_REQUEST, "INVALID_USER");
        }

        StoreInfo storeInfo = buildStoreInfo(storeInfoRequest);
        StoreContact storeContact = buildStoreContact(storeInfoRequest);

        // save to entities
        storeContactRepository.save(storeContact);
        storeInfoRepository.save(storeInfo);

        // find user
        UserInformation userInformation =
                userInformationRepository.findById(userInfo.getUserId()).orElseThrow(() ->
                        new AppException("User not found", HttpStatus.NOT_FOUND, "USER_NOT_FOUND"));

        userInformation.setStoreId(storeInfo.getId());
        userInformation.setUpdatedAt(LocalDateTime.now());
        userInformationRepository.save(userInformation);

        StoreInfoResponse storeInfoResponse =
                StoreInfoResponseMapper.toStoreInfoResponse(storeInfo, storeContact);

        response.setData(storeInfoResponse);
        response.isSuccess();
        return response;
    }

    private StoreContact buildStoreContact(StoreInfoRequest storeInfoRequest) {
        StoreContact storeContact = new StoreContact();
        storeContact.setPhone(storeInfoRequest.getStoreContact().getStorePhone());
        storeContact.setEmail(storeInfoRequest.getStoreContact().getStoreEmail());
        storeContact.setAddress(storeInfoRequest.getStoreContact().getStoreAddress());
        return storeContact;
    }

    private StoreInfo buildStoreInfo(StoreInfoRequest storeInfoRequest) {
        StoreInfo storeInfo = new StoreInfo();
        storeInfo.setBrand(storeInfoRequest.getBrand());
        storeInfo.setDescription(storeInfoRequest.getDescription());
        storeInfo.setStoreType(storeInfoRequest.getStoreType());
        storeInfo.setStatus("PENDING");
        storeInfo.setCreatedAt(LocalDateTime.now());
        storeInfo.setUpdatedAt(LocalDateTime.now());
        return storeInfo;
    }

    @Override
    public BaseApiResponse<StoreInfoResponse> getStoreById(Long id) {
        BaseApiResponse<StoreInfoResponse> response = new BaseApiResponse<>();

        if (id == null) {
            throw new AppException("Invalid store ID",
                    HttpStatus.BAD_REQUEST, "INVALID_ID");
        }

        StoreInfo storeInfo = storeInfoRepository.findById(id).orElse(null);
        if (storeInfo == null) {
            throw new AppException("Store not found",
                    HttpStatus.NOT_FOUND, "STORE_NOT_FOUND");
        }

        StoreContact storeContact = storeContactRepository.findById(storeInfo.getId()).orElse(null);
        if (storeContact == null) {
            throw new AppException("Store not found",
                    HttpStatus.NOT_FOUND, "STORE_NOT_FOUND");
        }

        StoreInfoResponse storeInfoResponse =
                StoreInfoResponseMapper.toStoreInfoResponse(storeInfo, storeContact);
        response.setData(storeInfoResponse);
        response.isSuccess();
        return response;
    }

    @Override
    public BaseApiResponse<List<StoreInfoResponse>> getAllStores() {
        BaseApiResponse<List<StoreInfoResponse>> response = new BaseApiResponse<>();

        List<StoreInfo> stores = storeInfoRepository.findAll();
        for (StoreInfo storeInfo : stores) {
            StoreContact storeContact = storeContactRepository.findById(storeInfo.getId()).orElse(null);
            if (storeContact == null) {
                throw new AppException("Store not found",
                        HttpStatus.NOT_FOUND, "STORE_NOT_FOUND");
            }

            response.getData().add(StoreInfoResponseMapper.toStoreInfoResponse(storeInfo, storeContact));
        }

        response.isSuccess();
        return response;
    }

    @Override
    public BaseApiResponse<StoreInfoResponse> getStoreByAdmin() {
        BaseApiResponse<StoreInfoResponse> response = new BaseApiResponse<>();
        BaseApiResponse<UserProfileResponse> admin = userInfoService.getCurrentUserInfo();

        UserInformation userInformation = userInformationRepository.findByEmail(admin.getData().getEmail());

        StoreInfo store = storeInfoRepository.findById(userInformation.getStoreId()).orElse(null);
        if (store == null) {
            throw new AppException("Store not found for admin",
                    HttpStatus.NOT_FOUND, "STORE_NOT_FOUND");
        }

        StoreContact storeContact = storeContactRepository.findById(store.getId()).orElse(null);
        if (storeContact == null) {
            throw new AppException("Store not found for admin",
                    HttpStatus.NOT_FOUND, "STORE_NOT_FOUND");
        }

        StoreInfoResponse storeInfoResponse =
                StoreInfoResponseMapper.toStoreInfoResponse(store, storeContact);

        response.setData(storeInfoResponse);
        response.isSuccess();

        return response;
    }

    @Override
    public BaseApiResponse<StoreInfoResponse> updateStore(Long id, StoreInfoRequest storeInfoRequest) {
        BaseApiResponse<StoreInfoResponse> response = new BaseApiResponse<>();

        if (id == null) {
            throw new AppException("Invalid store ID",
                    HttpStatus.BAD_REQUEST, "INVALID_ID");
        }

        UserProfileResponse currentUser = userInfoService.getCurrentUserInfo().getData();
        UserInformation userInformation = userInformationRepository.findByEmail(currentUser.getEmail());

        StoreInfo existingStore = storeInfoRepository.findById(userInformation.getId()).orElse(null);
        if (existingStore == null) {
            throw new AppException("Store not found",
                    HttpStatus.NOT_FOUND, "STORE_NOT_FOUND");
        }

        if (storeInfoRequest.getBrand() != null) {
            existingStore.setBrand(storeInfoRequest.getBrand());
        }
        if (storeInfoRequest.getDescription() != null) {
            existingStore.setDescription(storeInfoRequest.getDescription());
        }
        if (storeInfoRequest.getStoreType() != null) {
            existingStore.setStoreType(storeInfoRequest.getStoreType());
        }

        StoreInfo savedStore = storeInfoRepository.save(existingStore);

        StoreContact contact = storeContactRepository.findById(savedStore.getId()).orElse(null);
        if (contact == null) {
            throw new AppException("Store contact not found",
                    HttpStatus.NOT_FOUND, "STORE_CONTACT_NOT_FOUND");
        }

        if (storeInfoRequest.getStoreContact() != null) {
            if (storeInfoRequest.getStoreContact().getStoreEmail() != null) {
                contact.setEmail(storeInfoRequest.getStoreContact().getStoreEmail());
            }
            if (storeInfoRequest.getStoreContact().getStorePhone() != null) {
                contact.setPhone(storeInfoRequest.getStoreContact().getStorePhone());
            }
            if (storeInfoRequest.getStoreContact().getStoreAddress() != null) {
                contact.setAddress(storeInfoRequest.getStoreContact().getStoreAddress());
            }

            storeContactRepository.save(contact);
        }

        StoreInfoResponse storeInfoResponse =
                StoreInfoResponseMapper.toStoreInfoResponse(savedStore, contact);

        response.setData(storeInfoResponse);
        response.isSuccess();
        return response;
    }

    @Override
    public BaseApiResponse<Void> deleteStore(Long id) {
        BaseApiResponse<Void> response = new BaseApiResponse<>();

        UserProfileResponse currentUser = userInfoService.getCurrentUserInfo().getData();
        UserInformation userInformation = userInformationRepository.findByEmail(currentUser.getEmail());

        StoreInfo storeInfo = storeInfoRepository.findById(userInformation.getId()).orElse(null);
        if (storeInfo == null) {
            throw new AppException("Store not found", HttpStatus.NOT_FOUND, "STORE_NOT_FOUND");
        }

        storeInfoRepository.delete(storeInfo);
        response.isSuccess();
        return response;
    }

    @Override
    public BaseApiResponse<StoreInfoResponse> getStoreByEmployee() {
        BaseApiResponse<StoreInfoResponse> response = new BaseApiResponse<>();

        UserProfileResponse employee = userInfoService.getCurrentUserInfo().getData();
        UserInformation userInformation = userInformationRepository.findByEmail(employee.getEmail());

        StoreInfo storeInfo = storeInfoRepository.findById(userInformation.getId()).orElse(null);
        if (storeInfo == null) {
            throw new AppException("Employee has no associated store",
                    HttpStatus.NOT_FOUND, "STORE_NOT_FOUND");
        }
        StoreContact storeContact = storeContactRepository.findById(storeInfo.getId()).orElse(null);
        if (storeContact == null) {
            throw new AppException("Employee has no associated store",
                    HttpStatus.NOT_FOUND, "STORE_NOT_FOUND");
        }

        StoreInfoResponse storeInfoResponse =
                StoreInfoResponseMapper.toStoreInfoResponse(storeInfo, storeContact);

        response.setData(storeInfoResponse);
        response.isSuccess();
        return response;
    }

    @Override
    public BaseApiResponse<StoreInfoResponse> moderateStore(Long id, String status) {
        BaseApiResponse<StoreInfoResponse> response = new BaseApiResponse<>();
        if (status == null) {
            throw new AppException("Store status is required",
                    HttpStatus.BAD_REQUEST, "INVALID_STATUS");
        }

        StoreInfo store = storeInfoRepository.findById(id).orElse(null);
        if (store == null) {
            throw new AppException("Store not found",
                    HttpStatus.NOT_FOUND, "STORE_NOT_FOUND");
        }

        store.setStatus(status);
        store.setUpdatedAt(LocalDateTime.now());
        StoreInfo updatedStore = storeInfoRepository.save(store);
        StoreContact storeContact = storeContactRepository.findById(updatedStore.getId()).orElse(null);
        if (storeContact == null) {
            throw new AppException("Store not found",
                    HttpStatus.NOT_FOUND, "STORE_NOT_FOUND");
        }

        StoreInfoResponse storeInfoResponse =
                StoreInfoResponseMapper.toStoreInfoResponse(updatedStore, storeContact);

        response.setData(storeInfoResponse);
        response.isSuccess();
        return response;
    }
}
