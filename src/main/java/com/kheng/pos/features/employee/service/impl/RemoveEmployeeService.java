package com.kheng.pos.features.employee.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.employee.entity.Employee;
import com.kheng.pos.databases.pg.employee.repository.EmployeeRepository;
import com.kheng.pos.databases.pg.userinfo.entity.UserInformation;
import com.kheng.pos.databases.pg.userinfo.repository.UserInformationRepository;
import com.kheng.pos.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveEmployeeService {
    private final EmployeeRepository employeeRepository;
    private final UserInformationRepository userInformationRepository;

    public BaseApiResponse<Void> deleteEmployee(Long employeeId) {
        BaseApiResponse<Void> response = new BaseApiResponse<>();

        // fetch employee
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            throw new AppException("Employee not found!",
                    HttpStatus.NOT_FOUND, "0001");
        }

        // fetch User
        UserInformation userInformation = userInformationRepository.findById(employee.getUserId()).orElse(null);
        if(userInformation == null){
            throw new AppException("User not found!",
                    HttpStatus.NOT_FOUND, "0001");
        }

        // delete
        employeeRepository.delete(employee);
        userInformationRepository.delete(userInformation);

        response.isSuccess();
        return response;
    }
}
