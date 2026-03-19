package com.kheng.pos.features.branch.service.contract;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.features.branch.payload.dto.BranchDto;
import com.kheng.pos.features.branch.payload.request.CreateBranchRequest;
import com.kheng.pos.features.branch.payload.request.UpdateBranchRequest;
import com.kheng.pos.features.branch.service.BranchService;
import com.kheng.pos.features.branch.service.impl.*;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchServiceContract implements BranchService {
    private final CreateBranchService createBranchService;
    private final UpdateBranchService updateBranchService;
    private final RemoveBranchService removeBranchService;
    private final GetBranchByBranchIdService getBranchByBranchIdService;
    private final GetAllBranchByStoreService getAllBranchByStoreService;

    @Override
    public BaseApiResponse<BranchDto> createBranch(CreateBranchRequest request, UserProfileResponse userInfo) {
        return createBranchService.createBranch(request, userInfo);
    }

    @Override
    public BaseApiResponse<BranchDto> updateBranch(Long branchId, UpdateBranchRequest request, UserProfileResponse userInfo) {
        return updateBranchService.updateBranch(branchId,request,userInfo);
    }

    @Override
    public BaseApiResponse<Void> deleteBranch(Long branchId) {
        return removeBranchService.deleteBranch(branchId);
    }

    @Override
    public BaseApiResponse<List<BranchDto>> getAllBranchesByStoreId(Long storeId) {
        return getAllBranchByStoreService.getBranchesByStoreId(storeId);
    }

    @Override
    public BaseApiResponse<BranchDto> getBranchById(Long branchId) {
        return getBranchByBranchIdService.getBranchById(branchId);
    }
}
