package com.kheng.pos.features.employee.service;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.features.employee.payload.request.CreateEmployeeRequest;
import com.kheng.pos.features.employee.payload.request.UpdateEmployeeRequest;
import com.kheng.pos.features.employee.payload.response.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    BaseApiResponse<EmployeeResponse> createStoreEmployee(Long storeId, CreateEmployeeRequest request);
    BaseApiResponse<EmployeeResponse> createBranchEmployee(Long branchId, CreateEmployeeRequest request);
    BaseApiResponse<EmployeeResponse> updateEmployee(Long employeeId, UpdateEmployeeRequest request);
    BaseApiResponse<Void> deleteEmployee(Long employeeId);
    BaseApiResponse<List<EmployeeResponse>> getStoreEmployees(Long storeId);
    BaseApiResponse<List<EmployeeResponse>> getBranchEmployees(Long branchId);
}
