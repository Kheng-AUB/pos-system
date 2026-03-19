package com.kheng.pos.features.branch.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.branch.entity.Branch;
import com.kheng.pos.databases.pg.branch.repository.BranchRepository;
import com.kheng.pos.databases.pg.store.entity.StoreInfo;
import com.kheng.pos.databases.pg.store.repository.StoreInfoRepository;
import com.kheng.pos.databases.pg.userinfo.entity.UserStore;
import com.kheng.pos.databases.pg.userinfo.repository.UserStoreRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.branch.mapper.BranchMapper;
import com.kheng.pos.features.branch.payload.dto.BranchDto;
import com.kheng.pos.features.branch.payload.request.CreateBranchRequest;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import com.kheng.pos.features.user.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateBranchService {
    private final UserInfoService userInfoService;
    private final BranchRepository branchRepository;
    private final StoreInfoRepository storeInfoRepository;
    private final UserStoreRepository userStoreRepository;


    public BaseApiResponse<BranchDto> createBranch(CreateBranchRequest request, UserProfileResponse userInfo){
        BaseApiResponse<BranchDto> response = new BaseApiResponse<>();

        //check user
        UserProfileResponse currentUser = userInfoService.getCurrentUserInfo().getData();
        UserStore userStore = userStoreRepository.findTopByUserIdOrderByIdDesc(currentUser.getUserId());

        //check store
        StoreInfo storeInfo = storeInfoRepository.findTopByIdOrderByIdDesc(userStore.getStoreId());
        if(storeInfo == null){
            throw new AppException("Store not found",
                    HttpStatus.NOT_FOUND,"STORE_NOT_FOUND"
            );
        }

        Branch branch = BranchMapper.toEntity(request);
        Branch savedBranch = branchRepository.save(branch);
        BranchDto branchDto = BranchMapper.toDto(savedBranch);

        response.setData(branchDto);
        response.isSuccess();
        return response;
    }
}
