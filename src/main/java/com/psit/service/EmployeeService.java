package com.psit.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.psit.Dto.EmployeeDto;
import com.psit.entity.Employee;
//import com.psit.serviceImpl.$missing$;
//import com.psit.serviceImpl.list;
import com.psit.projection.EmployeeProjection;

public interface EmployeeService {

	Employee saveEmployeeDetails(EmployeeDto employeeDto);
	 void sendMail();
	 public String findByEmailId(String email);
	EmployeeProjection creditPayment(String empCode);
}
