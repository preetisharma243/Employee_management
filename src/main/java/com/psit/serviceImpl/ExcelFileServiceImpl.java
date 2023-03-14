package com.psit.serviceImpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.psit.constant.AppConstant;
import com.psit.entity.Employee;
import com.psit.excelHelper.ExcelHelper;
import com.psit.exception.DuplicateDataException;
import com.psit.exception.TechnicalException;
import com.psit.repository.EmployeeRepository;
import com.psit.response.EmployeeResponse;
import com.psit.service.ExcelFileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class ExcelFileServiceImpl implements ExcelFileService{

	@Autowired
	private EmployeeRepository employeeRepository;
	public void  saveExcelfile(MultipartFile file) {
		log.info("inside saveExcelfile::saving record from excell to db");
		try {
			EmployeeResponse employeeResponse = ExcelHelper.convertExcelToEmployeeList(file.getInputStream());
			log.info("EmployeeResponse {}",employeeResponse);
			List<Employee> employeeList = employeeResponse.getEmployeeList();
			List<String> emailList = employeeList.stream().filter(e->Objects.nonNull(e.getEmail())).map(Employee::getEmail).toList();
			log.info("ListOfEmail {}",emailList);

			List<Employee> existingEmployeeList = employeeRepository.fetchEmployeeByEmail(emailList);
			log.info("existingEmployeeList {}",existingEmployeeList);


			List<Employee> finalEmployeeList=new ArrayList<>();	   
			//if fetchEmployeeByEmail is not empty or is not null
			if(!CollectionUtils.isEmpty(existingEmployeeList)) {
				for(Employee existingEmployee:existingEmployeeList) {
					for(Employee newEmployee:employeeList) {

						if(Objects.nonNull(newEmployee.getEmail()))
						
						{
							if(newEmployee.getEmail().equalsIgnoreCase(existingEmployee.getEmail())) {
							newEmployee.setId(existingEmployee.getId());
							newEmployee.setUpdated(LocalDateTime.now());
							BeanUtils.copyProperties(newEmployee, existingEmployee);
							finalEmployeeList.add(newEmployee);
							}
						
						else
						{
							newEmployee.setCreated(LocalDateTime.now());
							finalEmployeeList.add(newEmployee);
						}
						}

					}
					
				}
				
				employeeRepository.saveAllAndFlush(finalEmployeeList);

			}
			else {

               employeeRepository.saveAllAndFlush(finalEmployeeList);
			}



		}

		catch(Exception e) {
			log.error("technical error {}",e.getMessage());
			throw new TechnicalException(AppConstant.SOMETHING_WENT_WRONG);
		}

	}

	public List<Employee> getAllEmployeeData(){
		return employeeRepository.findAll();
	}
}



