package com.kheng.pos.features.employee.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.branch.entity.Branch;
import com.kheng.pos.databases.pg.branch.repository.BranchRepository;
import com.kheng.pos.databases.pg.employee.entity.Employee;
import com.kheng.pos.databases.pg.employee.repository.EmployeeRepository;
import com.kheng.pos.databases.pg.userinfo.entity.UserInformation;
import com.kheng.pos.databases.pg.userinfo.repository.UserInformationRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.employee.payload.request.UpdateEmployeeRequest;
import com.kheng.pos.features.employee.payload.response.EmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UpdateEmployeeService {
    private final UserInformationRepository userInformationRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;

    public BaseApiResponse<EmployeeResponse> updateEmployee(Long employeeId, UpdateEmployeeRequest request) {
        BaseApiResponse<EmployeeResponse> response = new BaseApiResponse<>();


        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            throw new AppException("Employee not found!",
                    HttpStatus.NOT_FOUND, "0001");
        }

        UserInformation userInformation = userInformationRepository.findById(employee.getUserId()).orElse(null);
        if (userInformation == null) {
            throw new AppException("User not found!",
                    HttpStatus.NOT_FOUND, "0001");
        }

        if (request.getPhone() != null) {
            userInformation.setPhone(request.getPhone());
        }

        if (request.getEmail() != null) {
            userInformation.setEmail(request.getEmail());
        }

        if (request.getFullName() != null) {
            userInformation.setFullName(request.getFullName());
        }

        if (request.getPassword() != null) {
            userInformation.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if(request.getRole() != null){
            userInformation.setUserRole(request.getRole());
        }
        userInformation.setUpdatedAt(LocalDateTime.now());
        userInformationRepository.save(userInformation);

        Branch branch = branchRepository.findById(request.getBranchId()).orElse(null);
        if (branch == null) {
            throw new AppException("Branch not found!",
                    HttpStatus.NOT_FOUND, "0001");
        }

        if (request.getBranchId() != null) {
            employee.setBranchId(request.getBranchId());
        }
        employeeRepository.save(employee);

        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setEmployeeId(userInformation.getId());
        employeeResponse.setPhone(userInformation.getPhone());
        employeeResponse.setEmail(userInformation.getEmail());
        employeeResponse.setFullName(userInformation.getFullName());
        employeeResponse.setRole(userInformation.getUserRole().name());

        employeeResponse.setBranchId(branch.getId());
        employeeResponse.setStoreId(employee.getStoreId());

        employeeResponse.setCreatedAt(userInformation.getCreatedAt());
        employeeResponse.setUpdatedAt(userInformation.getUpdatedAt());

        response.setData(employeeResponse);
        response.isSuccess();
        return response;
    }
}
