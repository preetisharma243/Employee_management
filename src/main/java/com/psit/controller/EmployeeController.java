package com.psit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.psit.Dto.EmployeeDto;
import com.psit.entity.Employee;
import com.psit.projection.EmployeeProjection;
import com.psit.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/emp")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	@PostMapping("/save")
	public ResponseEntity<?> saveEmployeeDetails(@RequestBody EmployeeDto dto){
		log.info("rest request for saving employee data");
		Employee employee = employeeService.saveEmployeeDetails(dto);


		return new ResponseEntity<Employee>(employee, HttpStatus.OK);

	}
	
	@GetMapping("/sendEmail")
	public String sendBirthdayWishes(@RequestParam("email") String email) {
		String msg = employeeService.findByEmailId(email);
		
		return msg;
	}
	
	@PostMapping("/email")
	public ResponseEntity<?> sendEmailForBirthdayWishes(){
		employeeService.sendMail();
		return null;
		
	}
	
	@PostMapping("/creditPayment")
	public ResponseEntity<?> creditPayment(@RequestParam String empCode){
		log.info("rest request processing ofr crediting payment");
		EmployeeProjection employee = employeeService.creditPayment(empCode);
		return new ResponseEntity<EmployeeProjection>(employee,HttpStatus.OK);
		
	}
}
