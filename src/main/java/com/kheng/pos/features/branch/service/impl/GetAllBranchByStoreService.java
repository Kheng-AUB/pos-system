package com.kheng.pos.features.branch.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.branch.entity.Branch;
import com.kheng.pos.databases.pg.branch.repository.BranchRepository;
import com.kheng.pos.features.branch.mapper.BranchMapper;
import com.kheng.pos.features.branch.payload.dto.BranchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllBranchByStoreService {
    private final BranchRepository branchRepository;

    public BaseApiResponse<List<BranchDto>> getBranchesByStoreId(Long storeId){
        BaseApiResponse<List<BranchDto>> response = new BaseApiResponse<>();

        List<Branch> branchList = branchRepository.findByStoreId(storeId);
        List<BranchDto> branchDtoList = new ArrayList<>();
        if(!branchList.isEmpty()){
            branchDtoList = branchList.stream()
                    .map(BranchMapper::toDto).toList();
        }

        response.setData(branchDtoList);
        response.isSuccess();
        return response;
    }
}
