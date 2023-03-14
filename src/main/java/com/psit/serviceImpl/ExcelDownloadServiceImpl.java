package com.psit.serviceImpl;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psit.entity.Employee;
import com.psit.excelHelper.ExcellDownloadHelper;
import com.psit.repository.EmployeeRepository;
import com.psit.service.ExcelDownloadService;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class ExcelDownloadServiceImpl implements ExcelDownloadService  {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	  public ByteArrayInputStream getExcelFile() {
		  log.info("FetchingEmployeeRecord from db");
		    List<Employee> employeeList = employeeRepository.findAll();
          log.info("invoking createEmployeeToExcel" );
		    ByteArrayInputStream in = ExcellDownloadHelper.createEmployeeToExcel(employeeList);
		    return in;
		  }

}
