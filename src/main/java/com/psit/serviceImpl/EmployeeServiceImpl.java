package com.psit.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.psit.Dto.EmployeeDto;
import com.psit.EmailHelper.Emailsending;
import com.psit.constant.AppConstant;
import com.psit.entity.Employee;
import com.psit.exception.DataNotExistException;
import com.psit.exception.DuplicateDataException;
import com.psit.projection.EmployeeProjection;
import com.psit.repository.EmployeeRepository;
import com.psit.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private Emailsending emailsending;

	
	public Employee saveEmployeeDetails(EmployeeDto employeeDto) {
		log.info("EmployeeDto{}:",employeeDto);
		
		if(employeeRepository.findByEmail(employeeDto.getEmail())!=null) {
			throw new DuplicateDataException(AppConstant.MAIL_EXIST);
			
			
		}
		
		modelMapper.getConfiguration().setAmbiguityIgnored(false);
		Employee employee = modelMapper.map(employeeDto, Employee.class);
		log.info("Employee entity::"+employee);
		employee.set_Active(true);
		employee.setCreated(LocalDateTime.now());
		
		Employee employee2 = employeeRepository.save(employee);
		
		return employee2;
		
		
		
		
	}
	
	//schedule ajob to send notification every 15 sec
	//@Scheduled(fixedRate =5000 ) //it will send message in every 5 minut
	//@Scheduled(cron="0 0 12 * * ?")
	public void sendMail() {
		
		sendEmailToEmployee();
		
	}
	
	public void sendEmailToEmployee() {
		List<Employee> employee = employeeRepository.findAll();
		System.out.println(employee.toString());
		if (Objects.nonNull(employee)) {
			for(Employee emp:employee) {
				
			
			emp.setNotificationSent(true);
			employeeRepository.save(emp);
			emailsending.sendSimpleMail(emp);
			
	}
			
			
			
}

}

	@Override
	public String findByEmailId(String email) {
		Employee employee = employeeRepository.findByEmail(email);
		log.info("employee {}::",employee);
		if(Objects.nonNull(employee)) {
			if(employee.getDob().getDayOfMonth()<LocalDateTime.now().getDayOfMonth()) {
				return "link expire";
			}
			else {
				
				return employee.getFirstName()+" "+employee.getLastName()+"wish you very happy birthday";

			}
		}
		else {
			throw new DataNotExistException(AppConstant.EMP_NOT_EXIST);
		}
			
	}

	@Override
	
	public EmployeeProjection creditPayment(String empCode) {
		log.info("inside creditPayment::");
		EmployeeProjection findByEmployeeCode = employeeRepository.findByEmployeeCode(empCode);
		log.info("findByEmployeeCode {}::",findByEmployeeCode);
		if (Objects.nonNull(findByEmployeeCode)) {
			
			/*
			 * LocalDateTime completionPeriod = findByEmployeeCode.getDoj().plusDays(365);
			 * int monthValue2=LocalDateTime.now().getMonthValue();
			 */
		
		String valueOf = String.valueOf(findByEmployeeCode.getDoj());
		String valueOf2 = String.valueOf(LocalDate.now());
		
		
		
		System.out.println(valueOf+"    "+valueOf2);
			
		 LocalDate doj= LocalDate.parse(valueOf);
		    LocalDate now = LocalDate.parse(valueOf2);
		    Months.monthsBetween(doj, now).getMonths();
		    System.out.println(months);
			
		
		    // Period diff = Period.between(LocalDate.parse(valueOf),
		    //		    LocalDate.parse(valueOf2));
		     
		     //System.out.println(diff);
			
		} else {
			throw new DataNotExistException(AppConstant.EMP_NOT_EXIST);

		}
		
		return findByEmployeeCode;
		
		
	}

}
