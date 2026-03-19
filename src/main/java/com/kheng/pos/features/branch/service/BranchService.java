package com.kheng.pos.features.branch.service;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.features.branch.payload.dto.BranchDto;
import com.kheng.pos.features.branch.payload.request.CreateBranchRequest;
import com.kheng.pos.features.branch.payload.request.UpdateBranchRequest;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;

import java.util.List;

public interface BranchService {
    BaseApiResponse<BranchDto> createBranch(CreateBranchRequest request, UserProfileResponse userInfo);
    BaseApiResponse<BranchDto> updateBranch(Long branchId, UpdateBranchRequest request, UserProfileResponse userInfo);
    BaseApiResponse<Void> deleteBranch(Long branchId);
    BaseApiResponse<List<BranchDto>> getAllBranchesByStoreId(Long storeId);
    BaseApiResponse<BranchDto> getBranchById(Long branchId);
}
