package com.kheng.pos.features.employee.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.branch.entity.Branch;
import com.kheng.pos.databases.pg.branch.repository.BranchRepository;
import com.kheng.pos.databases.pg.employee.entity.Employee;
import com.kheng.pos.databases.pg.employee.repository.EmployeeRepository;
import com.kheng.pos.databases.pg.userinfo.entity.UserInformation;
import com.kheng.pos.databases.pg.userinfo.repository.UserInformationRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.employee.payload.response.EmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetBranchEmployeeService {
    private final UserInformationRepository userInformationRepository;
    private final BranchRepository branchRepository;
    private final EmployeeRepository employeeRepository;

    public BaseApiResponse<List<EmployeeResponse>> getBranchEmployees(Long branchId) {
        BaseApiResponse<List<EmployeeResponse>> response = new BaseApiResponse<>();

        Branch branch = branchRepository.findById(branchId).orElse(null);
        if(branch== null){
            throw new AppException("Branch not found",
                    HttpStatus.NOT_FOUND, "0001");
        }

        List<Employee> employeeList = employeeRepository.findByBranchId(branchId);
        if (employeeList.isEmpty()) {
            response.notFound("no data");
            return response;
        }

        List<EmployeeResponse> employeeResponseList = new ArrayList<>();
        for (Employee emp : employeeList) {
            UserInformation userInformation = userInformationRepository.findById(emp.getUserId()).orElse(null);
            if (userInformation == null) continue;

            EmployeeResponse employeeResponse = new EmployeeResponse();
            employeeResponse.setEmployeeId(userInformation.getId());
            employeeResponse.setPhone(userInformation.getPhone());
            employeeResponse.setEmail(userInformation.getEmail());
            employeeResponse.setFullName(userInformation.getFullName());
            employeeResponse.setRole(userInformation.getUserRole().name());

            employeeResponse.setBranchId(emp.getBranchId());
            employeeResponse.setStoreId(emp.getStoreId());

            employeeResponse.setCreatedAt(userInformation.getCreatedAt());
            employeeResponse.setUpdatedAt(userInformation.getUpdatedAt());

            employeeResponseList.add(employeeResponse);
        }

        response.setData(employeeResponseList);
        response.isSuccess();
        return response;
    }
}
