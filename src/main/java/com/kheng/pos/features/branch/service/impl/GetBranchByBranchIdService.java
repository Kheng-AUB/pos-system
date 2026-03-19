package com.kheng.pos.features.branch.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.branch.entity.Branch;
import com.kheng.pos.databases.pg.branch.repository.BranchRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.branch.mapper.BranchMapper;
import com.kheng.pos.features.branch.payload.dto.BranchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetBranchByBranchIdService {
    private final BranchRepository branchRepository;

    public BaseApiResponse<BranchDto> getBranchById(Long branchId){
        BaseApiResponse<BranchDto> response = new BaseApiResponse<>();

        Branch branch = branchRepository.findById(branchId).orElse(null);
        if(branch == null){
            throw new AppException("Branch not found",
                    HttpStatus.NOT_FOUND, "BRANCH_NOT_FOUND");
        }

        response.setData(BranchMapper.toDto(branch));
        response.isSuccess();
        return response;
    }
}
