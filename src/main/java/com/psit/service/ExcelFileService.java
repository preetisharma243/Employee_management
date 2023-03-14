package com.psit.service;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.psit.entity.Employee;


public interface ExcelFileService {
	
	void saveExcelfile(MultipartFile file ) throws IOException;
	public List<Employee> getAllEmployeeData();


}
