package com.psit.response;

import java.util.List;

import lombok.Data;


@Data
public class EmployeeResponse {
	private List employeeList;
	private int successCount;
	private int failCount;
	private int NumberOfRecord;
	

}
