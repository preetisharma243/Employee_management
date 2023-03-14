package com.psit.excelHelper;


import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.psit.constant.AppConstant;
import com.psit.entity.Employee;
import com.psit.exception.TechnicalException;
import com.psit.response.EmployeeResponse;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ExcelHelper {
	public static String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	// static String[] HEADERs = { "FirstName", "LastName", "email", "mobileNo","salary"};
	static String SHEET = "emp";//this should same name as excell sheet name


	//check that file is type of excel or not
	public static boolean checkExcelFormat(MultipartFile file) {
		log.info("checking foramte of file");
		if (CONTENT_TYPE.equals(file.getContentType())) {
			return true;
		}

		return false;
	}

	//convert excel to list of employee
	public static EmployeeResponse convertExcelToEmployeeList(InputStream is) {
		log.info("inside convertExcelToEmployeeList:: reteriving record from excel file");
		EmployeeResponse employeeResponse=new EmployeeResponse();
		int failCount=0;
		int successCount=0;
		int scount=0;
		int fcount=0;
		//this will create empty list 
		List<Employee> employeeList = new ArrayList<Employee>();

		try{//it will create object of whole excel workbook that will hold complete excelSheet,excel sheet data will connected by inputstream, mean we are readin excel shhet data using input stream


			XSSFWorkbook workbook = new XSSFWorkbook(is);

			//excel can contain multiple sheet,i need to bring one sheet from excel workbook
			//XSSFSheet sheet = workbook.getSheet(SHEET);

			XSSFSheet sheet = workbook.getSheetAt(0);

			//sheet will contains row and rows will contains cells ,so i will iterator rows
			//if we will iterate sheet it will give us row, and if we will itearte row it will gives us cell .
			//so firt we will iterate sheet ,this will iterate the sheet and gives iterators of rows,it will iterate the rows.
			Iterator<Row> rows = sheet.iterator();
			int rowNumber = 0;
			log.info("inside convertExcelToEmployeeList::retriving rows and cells from excel file");
			while (rows.hasNext()) {
				Row currentRow = rows.next();

				//to skip header, as header is at 0th row and we do not need header , we need data that will start from row 1
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				//this will give current iterating row, that will give cell from row
				Iterator<Cell> cellsInRow = currentRow.iterator();
				Employee employee = new Employee();
				int cellId = 0;



				while (cellsInRow.hasNext()) {
					try {
						Cell currentCell = cellsInRow.next();
						switch (cellId) {

						case 0:
							if(Objects.nonNull(currentCell.getStringCellValue())) {
								employee.setFirstName( currentCell.getStringCellValue());
							}
							else {
								throw new Exception();
							}
							break;

						case 1:
							if(Objects.nonNull(currentCell.getStringCellValue())) {
								employee.setLastName(currentCell.getStringCellValue());
							}
							else {
								throw new Exception();
							}
							break;

						case 2:
							if(Objects.nonNull(currentCell.getStringCellValue())) {
								employee.setEmail(currentCell.getStringCellValue());
							}
							else {
								throw new Exception();
							}
							break;

						case 3:

							if(Objects.nonNull(currentCell.getNumericCellValue())) {
								employee.setMobileNo((long) currentCell.getNumericCellValue());
							}
							else {
								throw new Exception();
							}
							break;
						case 4:

							if(Objects.nonNull(currentCell.getNumericCellValue())) {
								employee.setSalary((double) currentCell.getNumericCellValue());
							}
							else {
								throw new Exception();
							}
							break;
						default:
							break;
						}
						cellId++;
						scount++;
						if(scount==1) {
							successCount++;

						}


					} catch (Exception e) {

						employee.setStatus("fail");
						switch (cellId) {
						case 0:
							employee.setFailedReason("name should be alphabetical");
							break;
						case 1:
							if(Objects.nonNull(employee.getFailedReason())) {
								employee.setFailedReason(employee.getFailedReason()+","+"lastName should be alphabeticl");
							}
							else
								employee.setFailedReason("invalid lastName");
							break;
						case 2:
							if(Objects.nonNull(employee.getFailedReason())) {
								employee.setFailedReason(employee.getFailedReason()+","+"invalid Email");
							}
							else
								employee.setFailedReason("invalid Email");
							break;



						case 3:
							if(Objects.nonNull(employee.getFailedReason())) {
								employee.setFailedReason(employee.getFailedReason()+","+"invalid mobileNumber");
							}
							else
								employee.setFailedReason("invalid mobileNumber");
							break;


						case 4:
							if(Objects.nonNull(employee.getFailedReason())) {
								employee.setFailedReason(employee.getFailedReason()+","+"invalid Salary");
							}
							else
								employee.setFailedReason("invalid Salary");
							break;

						default:
							break;
						}
						cellId++;
						fcount++;
						if(fcount==1) {
							failCount++;

						}
					}




				}
				
				employee.set_Active(true);
				employee.setCreated(LocalDateTime.now());
				
				employeeList.add(employee);
				scount=0;
				fcount=0;

			}
		}

		catch(Exception e) {
			log.error("exception occured while parsing file {} ",e.getMessage());
			throw new TechnicalException(AppConstant.INVALID_FILE);
		}

		employeeResponse.setEmployeeList(employeeList);
		employeeResponse.setFailCount(failCount);
		employeeResponse.setSuccessCount(successCount);
		employeeResponse.setNumberOfRecord(employeeList.size());

		return employeeResponse;
	}
}
