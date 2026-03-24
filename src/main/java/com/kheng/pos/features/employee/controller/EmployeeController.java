package com.kheng.pos.features.employee.controller;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.features.employee.payload.request.CreateEmployeeRequest;
import com.kheng.pos.features.employee.payload.request.UpdateEmployeeRequest;
import com.kheng.pos.features.employee.payload.response.EmployeeResponse;
import com.kheng.pos.features.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/store/{storeId}")
    public BaseApiResponse<EmployeeResponse> createStoreEmployee(
            @PathVariable("storeId") Long storeId,
            @RequestBody CreateEmployeeRequest request
    ) {
        return employeeService.createStoreEmployee(storeId, request);
    }

    @PostMapping("/branch/{branchId}")
    public BaseApiResponse<EmployeeResponse> createBranchEmployee(
            @PathVariable("branchId") Long storeId,
            @RequestBody CreateEmployeeRequest request
    ) {
        return employeeService.createBranchEmployee(storeId, request);
    }

    @PutMapping("/{employeeId}")
    public BaseApiResponse<EmployeeResponse> updateEmployee(
            @PathVariable("employeeId") Long employeeId,
            @RequestBody UpdateEmployeeRequest request
    ) {
        return employeeService.updateEmployee(employeeId, request);
    }

    @DeleteMapping("/{employeeId}")
    public BaseApiResponse<Void> deleteEmployee(
            @PathVariable("employeeId") Long employeeId
    ) {
        return employeeService.deleteEmployee(employeeId);
    }

    @GetMapping("/store/{storeId}")
    public BaseApiResponse<List<EmployeeResponse>> getStoreEmployees(
            @PathVariable("storeId") Long storeId
    ){
        return employeeService.getStoreEmployees(storeId);
    }

    @GetMapping("/branch/{branchId}")
    public BaseApiResponse<List<EmployeeResponse>> getBranchEmployees(
            @PathVariable("branchId") Long branchId
    ){
        return employeeService.getBranchEmployees(branchId);
    }
}
