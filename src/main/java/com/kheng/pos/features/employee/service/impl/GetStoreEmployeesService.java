package com.kheng.pos.features.employee.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.employee.entity.Employee;
import com.kheng.pos.databases.pg.employee.repository.EmployeeRepository;
import com.kheng.pos.databases.pg.store.entity.StoreInfo;
import com.kheng.pos.databases.pg.store.repository.StoreInfoRepository;
import com.kheng.pos.databases.pg.userinfo.entity.UserInformation;
import com.kheng.pos.databases.pg.userinfo.repository.UserInformationRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.employee.payload.response.EmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.kheng.pos.databases.pg.userinfo.enums.UserRole.ROLE_BRANCH_MANAGER;
import static com.kheng.pos.databases.pg.userinfo.enums.UserRole.ROLE_STORE_MANAGER;

@Service
@RequiredArgsConstructor
public class GetStoreEmployeesService {
    private final UserInformationRepository userInformationRepository;
    private final StoreInfoRepository storeInfoRepository;
    private final EmployeeRepository employeeRepository;

    public BaseApiResponse<List<EmployeeResponse>> getStoreEmployees(Long storeId) {
        BaseApiResponse<List<EmployeeResponse>> response = new BaseApiResponse<>();

        StoreInfo storeInfo = storeInfoRepository.findTopByIdOrderByIdDesc(storeId);
        if (storeInfo == null) {
            throw new AppException("Store not found",
                    HttpStatus.NOT_FOUND, "0001");
        }

        List<String> roleList = List.of(ROLE_BRANCH_MANAGER.name(), ROLE_STORE_MANAGER.name());
        List<Employee> employeeList = employeeRepository.findByStoreIdAndRole(storeId, roleList);
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
            employeeResponse.setStoreId(storeInfo.getId());

            employeeResponse.setCreatedAt(userInformation.getCreatedAt());
            employeeResponse.setUpdatedAt(userInformation.getUpdatedAt());

            employeeResponseList.add(employeeResponse);
        }

        response.setData(employeeResponseList);
        response.isSuccess();
        return response;
    }
}
