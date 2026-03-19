package com.kheng.pos.features.branch.controller;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.features.branch.payload.dto.BranchDto;
import com.kheng.pos.features.branch.payload.request.CreateBranchRequest;
import com.kheng.pos.features.branch.payload.request.UpdateBranchRequest;
import com.kheng.pos.features.branch.service.BranchService;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import com.kheng.pos.features.user.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/branch")
public class BranchController {
    private final BranchService branchService;
    private final UserInfoService userInfoService;

    @PostMapping("/create")
    public BaseApiResponse<BranchDto> createBranch(
            @RequestHeader("Authorization") String token,
            @RequestBody CreateBranchRequest request
    ) {

        UserProfileResponse userInfo = userInfoService.getUserInfoFromJwtToken(token).getData();
        return branchService.createBranch(request, userInfo);
    }


    @GetMapping("/{branchId}")
    public BaseApiResponse<BranchDto> getBranchById(
            @PathVariable("branchId") Long branchId
    ){
        return branchService.getBranchById(branchId);
    }

    @GetMapping("/store/{storeId}")
    public BaseApiResponse<List<BranchDto>> getBranchesByStoreId(
            @PathVariable("storeId") Long storeId
    ){
        return branchService.getAllBranchesByStoreId(storeId);
    }

    @PutMapping("/{branchId}")
    public BaseApiResponse<BranchDto> updateBranch(
            @RequestHeader("Authorization") String token,
            @PathVariable("branchId") Long branchId,
            @RequestBody UpdateBranchRequest request
            ){

        UserProfileResponse userInfo = userInfoService.getUserInfoFromJwtToken(token).getData();
        return branchService.updateBranch(branchId,request,userInfo);
    }

    @DeleteMapping("/{branchId}")
    public BaseApiResponse<Void> deleteBranch(
            @PathVariable("branchId") Long branchId
    ){
        return branchService.deleteBranch(branchId);
    }
}
