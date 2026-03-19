package com.kheng.pos.features.branch.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.branch.entity.Branch;
import com.kheng.pos.databases.pg.branch.repository.BranchRepository;
import com.kheng.pos.databases.pg.store.repository.StoreInfoRepository;
import com.kheng.pos.databases.pg.userinfo.repository.UserStoreRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.branch.mapper.BranchMapper;
import com.kheng.pos.features.branch.payload.dto.BranchDto;
import com.kheng.pos.features.branch.payload.request.UpdateBranchRequest;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import com.kheng.pos.features.user.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.kheng.pos.core.util.GlobalUtils.isBlank;

@Service
@RequiredArgsConstructor
public class UpdateBranchService {
    private final BranchRepository branchRepository;

    public BaseApiResponse<BranchDto> updateBranch(Long branchId, UpdateBranchRequest request, UserProfileResponse userInfo) {

        BaseApiResponse<BranchDto> response = new BaseApiResponse<>();

        // validate request
        if (request == null) {
            throw new AppException("No request for update",
                    HttpStatus.BAD_REQUEST, "NO_REQUEST_BODY");
        }

        // check exist
        Branch existing = branchRepository.findById(branchId).orElse(null);
        if (existing == null) {
            throw new AppException("Branch not found",
                    HttpStatus.NOT_FOUND, "BRANCH_NOT_FOUND");
        }

        if (!isBlank(request.getName())) {
            existing.setName(request.getName());
        }
        if (!isBlank(request.getPhone())) {
            existing.setPhone(request.getPhone());
        }
        if (!isBlank(request.getEmail())) {
            existing.setEmail(request.getEmail());
        }
        if (!isBlank(request.getAddress())) {
            existing.setAddress(request.getAddress());
        }
        if (!request.getWorkingDays().isEmpty()) {
            existing.setWorkingDays(request.getWorkingDays());
        }
        if (request.getOpenTime() != null) {
            existing.setOpenTime(request.getOpenTime());
        }
        if (request.getCloseTime() != null) {
            existing.setCloseTime(request.getCloseTime());
        }

        existing.setUpdateAt(LocalDateTime.now());
        Branch updatedBranch = branchRepository.save(existing);

        response.setData(BranchMapper.toDto(updatedBranch));
        response.isSuccess();
        return response;
    }
}
