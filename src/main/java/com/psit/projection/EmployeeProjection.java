package com.psit.projection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


public interface EmployeeProjection {
	int getCommitmentPeriod();
	LocalDate getDoj();
	String getEmployeeCode();
    int getVaribal();
    double getSalary();
}
