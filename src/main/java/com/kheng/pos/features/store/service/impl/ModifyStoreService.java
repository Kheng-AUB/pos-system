package com.kheng.pos.features.store.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.store.entity.StoreContact;
import com.kheng.pos.databases.pg.store.entity.StoreInfo;
import com.kheng.pos.databases.pg.store.repository.StoreContactRepository;
import com.kheng.pos.databases.pg.store.repository.StoreInfoRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.store.dto.request.StoreInfoRequest;
import com.kheng.pos.features.store.dto.response.StoreInfoResponse;
import com.kheng.pos.features.store.mapper.StoreInfoResponseMapper;
import com.kheng.pos.features.store.service.util.StoreServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.kheng.pos.core.util.GlobalUtils.isBlank;

@Service
@RequiredArgsConstructor
public class ModifyStoreService {
    private final StoreContactRepository storeContactRepository;
    private final StoreInfoRepository storeInfoRepository;

    public BaseApiResponse<StoreInfoResponse> updateStore(Long id, StoreInfoRequest storeInfoRequest) {
        BaseApiResponse<StoreInfoResponse> response = new BaseApiResponse<>();

        if (id == null) {
            throw new AppException("Invalid store ID",
                    HttpStatus.BAD_REQUEST, "INVALID_ID");
        }

        StoreInfo existingStore = storeInfoRepository.findById(id).orElse(null);
        if (existingStore == null) {
            throw new AppException("Store not found",
                    HttpStatus.NOT_FOUND, "STORE_NOT_FOUND");
        }

        if (!isBlank(storeInfoRequest.getBrand())) {
            existingStore.setBrand(storeInfoRequest.getBrand());
        }
        if (!isBlank(storeInfoRequest.getDescription())) {
            existingStore.setDescription(storeInfoRequest.getDescription());
        }
        if (!isBlank(storeInfoRequest.getStoreType())) {
            existingStore.setStoreType(storeInfoRequest.getStoreType());
        }

        existingStore.setUpdatedAt(LocalDateTime.now());
        StoreInfo savedStore = storeInfoRepository.save(existingStore);

        StoreContact contact = storeContactRepository.findByStoreId(savedStore.getId());
        if (contact == null) {
            throw new AppException("Store contact not found",
                    HttpStatus.NOT_FOUND, "STORE_CONTACT_NOT_FOUND");
        }

        if (storeInfoRequest.getStoreContact() != null) {
            if (!isBlank(storeInfoRequest.getStoreContact().getEmail())) {
                contact.setEmail(storeInfoRequest.getStoreContact().getEmail());
            }
            if (!isBlank(storeInfoRequest.getStoreContact().getPhone())) {
                contact.setPhone(storeInfoRequest.getStoreContact().getPhone());
            }
            if (!isBlank(storeInfoRequest.getStoreContact().getAddress())) {
                contact.setAddress(storeInfoRequest.getStoreContact().getAddress());
            }

            storeContactRepository.save(contact);
        }

        StoreInfoResponse storeInfoResponse =
                StoreInfoResponseMapper.toStoreInfoResponse(savedStore, contact);

        response.setData(storeInfoResponse);
        response.isSuccess();
        return response;
    }

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
        StoreContact storeContact =
                StoreServiceUtil.checkStoreContact(updatedStore.getId(), storeContactRepository);

        StoreInfoResponse storeInfoResponse =
                StoreInfoResponseMapper.toStoreInfoResponse(updatedStore, storeContact);

        response.setData(storeInfoResponse);
        response.isSuccess();
        return response;
    }
}
