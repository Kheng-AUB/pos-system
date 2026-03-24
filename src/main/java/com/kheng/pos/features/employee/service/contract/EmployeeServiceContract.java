package com.kheng.pos.features.employee.service.contract;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.features.employee.payload.request.CreateEmployeeRequest;
import com.kheng.pos.features.employee.payload.request.UpdateEmployeeRequest;
import com.kheng.pos.features.employee.payload.response.EmployeeResponse;
import com.kheng.pos.features.employee.service.EmployeeService;
import com.kheng.pos.features.employee.service.impl.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceContract implements EmployeeService {
    private final CreateStoreEmployeeService createStoreEmployeeService;
    private final CreateBranchEmployeeService createBranchEmployeeService;
    private final UpdateEmployeeService updateEmployeeService;
    private final RemoveEmployeeService removeEmployeeService;
    private final GetBranchEmployeeService getBranchEmployeeService;
    private final GetStoreEmployeesService getStoreEmployeesService;


    @Override
    public BaseApiResponse<EmployeeResponse> createStoreEmployee(Long storeId, CreateEmployeeRequest request) {
        return createStoreEmployeeService.createStoreEmployee(storeId, request);
    }

    @Override
    public BaseApiResponse<EmployeeResponse> createBranchEmployee(Long branchId, CreateEmployeeRequest request) {
        return createBranchEmployeeService.createBranchEmployee(branchId, request);
    }

    @Override
    public BaseApiResponse<EmployeeResponse> updateEmployee(Long employeeId, UpdateEmployeeRequest request) {
        return updateEmployeeService.updateEmployee(employeeId,request);
    }

    @Override
    public BaseApiResponse<Void> deleteEmployee(Long employeeId) {
        return removeEmployeeService.deleteEmployee(employeeId);
    }

    @Override
    public BaseApiResponse<List<EmployeeResponse>> getStoreEmployees(Long storeId) {
        return getStoreEmployeesService.getStoreEmployees(storeId);
    }

    @Override
    public BaseApiResponse<List<EmployeeResponse>> getBranchEmployees(Long branchId) {
        return getBranchEmployeeService.getBranchEmployees(branchId);
    }
}
