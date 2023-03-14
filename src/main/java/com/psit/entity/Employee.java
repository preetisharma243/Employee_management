package com.psit.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name="employee_management")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//@NotNull(message = "please enter first name")
	//@Size(max = 10,min = 4,message = "please enter last name between 4 to 6 character")
	@Column(name = "first_name")
	private String firstName;

	//@NotEmpty(message = "please enter last name")
	//@Size(max = 6,min = 4,message = "please enter last name between 4 to 6 character")
	@Column(name = "last_name")
	private String lastName;
	private String employeeCode;
	
	//@Pattern(regexp = "@gmail.com",message = "please enter proper email")
	private String email;
	
	//@NotNull(message = "please enter mobile no")
	//@Size(max = 10,message = "pleasee enter valid mobile no.")
    private Long mobileNo;
	
	//@NotNull(message = "please enter salary")
    private Double salary;
    private LocalDateTime dateOfJoining;
    private boolean is_Active;
    private LocalDateTime created;
	private LocalDateTime updated;
	
	private String status;
	private String failedReason;
	private LocalDateTime dob;
	private boolean isNotificationSent=false;
	private String url;
	
    private Integer varibal;
    private LocalDate doj;
    private Integer commitmentPeriod;
    
}
