package com.psit.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.psit.constant.AppConstant;
import com.psit.entity.Employee;
import com.psit.excelHelper.ExcelHelper;
import com.psit.exception.InvalidFileException;
import com.psit.service.ExcelFileService;
import com.psit.service.ExcelFileService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/excel")
@CrossOrigin("*")
@Slf4j
public class ExcelController {
	@Autowired
	private ExcelFileService excelFileService;
     	
	@PostMapping(value="/upload",headers = { "Content-Type=multipart/form-data" })
	public ResponseEntity<?> uploadEmployeeExcellSheet(@RequestParam("file") MultipartFile file) throws IOException{
		
		
		if(ExcelHelper.checkExcelFormat(file)) {
		excelFileService.saveExcelfile(file);
		
	    
		return  null;
		}
		
		
		
		throw new InvalidFileException(AppConstant.INVALID_FILE);
		
	}

	
	@GetMapping("/emp")
	public List<Employee> getEmployeeData(){
		return excelFileService.getAllEmployeeData();
		
	
	}
}
