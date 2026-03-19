package com.kheng.pos.features.branch.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.branch.entity.Branch;
import com.kheng.pos.databases.pg.branch.repository.BranchRepository;
import com.kheng.pos.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveBranchService {
    private final BranchRepository branchRepository;

    public BaseApiResponse<Void> deleteBranch(Long branchId){
        BaseApiResponse<Void> response = new BaseApiResponse<>();

        // check if branch exist
        Branch branch = branchRepository.findById(branchId).orElse(null);
        if(branch == null){
            throw new AppException("Branch not found",
                    HttpStatus.NOT_FOUND,"BRANCH_NOT_FOUND");
        }

        branchRepository.delete(branch);
        response.isSuccess();
        return response;
    }
}
