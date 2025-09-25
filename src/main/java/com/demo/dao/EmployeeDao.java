package com.demo.dao;

import java.util.List;

import com.demo.entity.Employee;

public interface EmployeeDao {
	
	public void getEmployeesAssociatedToDepartment(
			List<Employee> employeesAssociatedToDepartment);

	public void getEmployeesNotAssociatedToDepartment(
			List<Employee> employeesNotAssociatedToDepartment);
		
	public void getEmployeesEarningSecondMaximumSalary(
			List<Employee> employeesEarningSecondMaximumSalary);

	public double employeesAverageSalary();
	
	public List<Double> getMinAndMaxSalary(); 
	
	public double getSecondMaximumSalary(); 
	
	public int addEmployee(Employee newEmployee);
	
	public int addEmployeeToDepartment(int departmentId, int employeeId);

	public Boolean deleteEmployee(int employeeID);

	public int updateEmployee(int employeeID, String name, int age, double salary);

	public List<Employee> getAllEmployees(); 
}
