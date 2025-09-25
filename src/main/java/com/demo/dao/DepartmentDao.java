package com.demo.dao;

import java.util.LinkedHashMap;
import java.util.List;

import com.demo.entity.Department;

public interface DepartmentDao {
	
	public LinkedHashMap<Department, Double> 
		averageSalaryByDepartment();
	
	public  List<Object[]> 
		departmentsByNumberOfEmployees(long numEmployees);
		
	public LinkedHashMap<Department, Integer> 
		employeeCountByDepartment();
	
	public int createDepartment(Department newDepartment);
		
	public List<Department> getAllDepartments();
	
	public boolean deleteDepartment(int departmentId);	
}
