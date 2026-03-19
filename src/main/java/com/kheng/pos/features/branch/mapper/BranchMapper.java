package com.kheng.pos.features.branch.mapper;

import com.kheng.pos.databases.pg.branch.entity.Branch;
import com.kheng.pos.features.branch.payload.dto.BranchDto;
import com.kheng.pos.features.branch.payload.request.CreateBranchRequest;

import java.time.LocalDateTime;

public class BranchMapper {
    public static BranchDto toDto(Branch savedBranch){
        BranchDto branchDto = new BranchDto();
        branchDto.setBranchId(savedBranch.getId());
        branchDto.setName(savedBranch.getName());
        branchDto.setEmail(savedBranch.getEmail());
        branchDto.setAddress(savedBranch.getAddress());
        branchDto.setPhone(savedBranch.getPhone());
        branchDto.setOpenTime(savedBranch.getOpenTime());
        branchDto.setCloseTime(savedBranch.getCloseTime());
        branchDto.setWorkingDays(savedBranch.getWorkingDays());
        branchDto.setStoreId(savedBranch.getStoreId());
        branchDto.setManagerId(savedBranch.getManagerId());
        branchDto.setCreateAt(savedBranch.getCreateAt());
        branchDto.setUpdateAt(savedBranch.getUpdateAt());

        return branchDto;
    }

    public static Branch toEntity(CreateBranchRequest request){
        Branch branch = new Branch();
        branch.setName(request.getName());
        branch.setEmail(request.getEmail());
        branch.setPhone(request.getPhone());
        branch.setAddress(request.getAddress());
        branch.setWorkingDays(request.getWorkingDays());
        branch.setOpenTime(request.getOpenTime());
        branch.setCloseTime(request.getCloseTime());

        branch.setStoreId(request.getStoreId());
        branch.setManagerId(request.getManagerId());

        branch.setCreateAt(LocalDateTime.now());
        branch.setUpdateAt(LocalDateTime.now());

        return branch;
    }
}
