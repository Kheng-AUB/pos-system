package com.kheng.pos.features.employee.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.branch.entity.Branch;
import com.kheng.pos.databases.pg.branch.repository.BranchRepository;
import com.kheng.pos.databases.pg.employee.entity.Employee;
import com.kheng.pos.databases.pg.employee.repository.EmployeeRepository;
import com.kheng.pos.databases.pg.store.entity.StoreInfo;
import com.kheng.pos.databases.pg.store.repository.StoreInfoRepository;
import com.kheng.pos.databases.pg.userinfo.entity.UserInformation;
import com.kheng.pos.databases.pg.userinfo.enums.UserRole;
import com.kheng.pos.databases.pg.userinfo.repository.UserInformationRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.employee.payload.request.CreateEmployeeRequest;
import com.kheng.pos.features.employee.payload.response.EmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateStoreEmployeeService {
    private final UserInformationRepository userInformationRepository;
    private final StoreInfoRepository storeInfoRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;

    public BaseApiResponse<EmployeeResponse> createStoreEmployee(Long storeId, CreateEmployeeRequest request) {
        BaseApiResponse<EmployeeResponse> response = new BaseApiResponse<>();

        StoreInfo storeInfo = storeInfoRepository.findTopByIdOrderByIdDesc(storeId);
        if (storeInfo == null) {
            throw new AppException("Store not found",
                    HttpStatus.NOT_FOUND, "STORE_NOT_FOUND");
        }

        Branch branch = branchRepository.findById(request.getBranchId()).orElse(null);
        if (branch == null) {
            throw new AppException("branch not found",
                    HttpStatus.NOT_FOUND, "BRANCH_NOT_FOUND");
        }

        if (request.getRole() != UserRole.ROLE_BRANCH_MANAGER) {
            throw new AppException("userRole is not valid",
                    HttpStatus.BAD_REQUEST, "USER_ROLE_NOT_VALID");
        }

        // save user
        UserInformation userInformation = new UserInformation();
        userInformation.setFullName(request.getFullName());
        userInformation.setEmail(request.getEmail());
        userInformation.setPassword(passwordEncoder.encode(request.getPassword()));
        userInformation.setPhone(request.getPhone());
        userInformation.setUserRole(request.getRole());
        userInformation.setCreatedAt(LocalDateTime.now());
        userInformation.setUpdatedAt(LocalDateTime.now());
        userInformationRepository.save(userInformation);

        // save Employee Store
        Employee employee = new Employee();
        employee.setUserId(userInformation.getId());
        employee.setBranchId(branch.getId());
        employee.setStoreId(storeInfo.getId());
        employee.setCreatedAt(LocalDateTime.now());
        employee.setUpdatedAt(LocalDateTime.now());
        employeeRepository.save(employee);

        // build response
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setEmployeeId(userInformation.getId());
        employeeResponse.setPhone(userInformation.getPhone());
        employeeResponse.setEmail(userInformation.getEmail());
        employeeResponse.setFullName(userInformation.getFullName());
        employeeResponse.setRole(userInformation.getUserRole().name());

        employeeResponse.setBranchId(branch.getId());
        employeeResponse.setStoreId(storeInfo.getId());

        employeeResponse.setCreatedAt(userInformation.getCreatedAt());
        employeeResponse.setUpdatedAt(userInformation.getUpdatedAt());

        response.setData(employeeResponse);
        response.isSuccess();
        return response;
    }
}
