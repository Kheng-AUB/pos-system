package com.kheng.pos.features.inventory.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.inventory.entity.Inventory;
import com.kheng.pos.databases.pg.inventory.repository.InventoryRepository;
import com.kheng.pos.features.inventory.mapper.InventoryMapper;
import com.kheng.pos.features.inventory.payload.dto.InventoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetInventoriesByBranchIdService {
    private final InventoryRepository inventoryRepository;

    public BaseApiResponse<List<InventoryDto>> getInventoriesByBranchId(Long branchId){
        BaseApiResponse<List<InventoryDto>> response = new BaseApiResponse<>();

        List<Inventory> inventoryList = inventoryRepository.findByBranchId(branchId);
        List<InventoryDto> inventoryDtoList =
                inventoryList.stream().map(InventoryMapper::toDto)
                        .toList();

        response.setData(inventoryDtoList);
        response.isSuccess();
        return response;
    }
}
