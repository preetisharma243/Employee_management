package com.psit.repository;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.psit.entity.Employee;
import com.psit.projection.EmployeeProjection;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {


Employee findByEmail(String email);

@Query("from Employee e where e.email IN(?1)")
List<Employee> fetchEmployeeByEmail(List<String> email);


@Query(value = "select * from associationmapping.employee_management em where DAY(em.dob)=DAY(current_date())and MONTH(em.dob)=MONTH(current_date()) and isNotificationSent=0", nativeQuery = true)
List<Employee> findAll();

@Query(value = "select commitment_period as CommitmentPeriod,doj as Doj,employee_code as EmployeeCode,varibal as Varibal,salary as Salary from associationmapping.employee_management em where em.employee_code=?1",nativeQuery = true)
EmployeeProjection   findByEmployeeCode(String empCode);


}
