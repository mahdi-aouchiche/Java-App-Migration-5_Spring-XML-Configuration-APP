package com.demo.service;

import java.util.Collections;
import java.util.List;

import com.demo.dao.*;
import com.demo.entity.Employee;

public class EmployeeService {
	EmployeeDao employeeDao;

	/**
	 * Constructor
	 * 
	 * @param employeeDao
	 */
	public EmployeeService(EmployeeDao employeeDao) {
		super();
		this.employeeDao = employeeDao;
	}
	
	
	/**
	 * Delete an employee from the database
	 * 
	 * @param employeeID
	 * @return true if employee is deleted, false otherwise.
	 */
	public Boolean deleteEmployee(int employeeID) {
		return this.employeeDao.deleteEmployee(employeeID);
	}
	
	
	/**
	 * Update employee info
	 * 
	 * @param employeeID
	 * @param name
	 * @param age
	 * @param salary
	 * @return number of updated records in the DB. 
	 */
	public int updateEmployee(int employeeID, String name, int age, double salary) {
		if(name == null || name.isEmpty()) {
			return 0;
		}
		
		String[] splitName = name.trim().split(" ");
		
		String fullname = "";
		for(String str : splitName) {
			str = str.toLowerCase();
			fullname += str.substring(0, 1).toUpperCase() + str.substring(1) + " "; 
		}
		fullname = fullname.trim();
		
		return this.employeeDao.updateEmployee(employeeID, fullname, age, salary);
	}
	
	
	/**
	 * List of all employees sorted by name
	 * 
	 * @param columnLabels
	 * @param allEmployees
	 */
	public void listOfAllEmployees(
			List<String> columnLabels, 
			List<Employee> allEmployees) 
	{
		// Set the column labels
		columnLabels.add("Employee ID");
		columnLabels.add("Name");
		columnLabels.add("Age");
		columnLabels.add("Salary");
		
		// Get the list of all employees
		allEmployees.addAll(this.employeeDao.getAllEmployees());
	}
	
	
	/**
	 * Find all employees affiliated to a department
	 * Sorted by Name Ascending order
	 * 
	 * @param List of column labels
	 * @param List of employees associated to a department
	 */
	public void listOfEmployeesAssociatedToDepartment(
			List<String> columnLabels, 
			List<Employee> employeesAssociatedToDepartment)
	{
		// Set the column lables
		columnLabels.add("Employee ID");
		columnLabels.add("Name");
		columnLabels.add("Age");
		columnLabels.add("Salary");
		
		// Add the action column (delete and edit) an employee
		columnLabels.add("Action");

		// List of employees associated to a department
		this.employeeDao
		    .getEmployeesAssociatedToDepartment(
		    		employeesAssociatedToDepartment
		    );
		
		// sort the list by employee name
		Collections.sort( 
				employeesAssociatedToDepartment, 
				(e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName())
		);
	}
	
	
	/**
	 * Find all employees NOT affiliated to a department
	 * 
	 * @param List of column labels
	 * @param List of employees NOT associated to a department
	 */
	public void listOfEmployeesNotAssociatedToDepartment(
			List<String> columnLabels, 
			List<Employee> employeesNotAssociatedToDepartment) 
	{
		// Set the column lables
		columnLabels.add("Employee ID");
		columnLabels.add("Name");
		columnLabels.add("Age");
		columnLabels.add("Salary");
		// Add the action column to delete and edit an employee
		columnLabels.add("Action");
		
		// List of employees not associated to a department
		this.employeeDao
			.getEmployeesNotAssociatedToDepartment(
					employeesNotAssociatedToDepartment
			);
		
		// sort the list by employee name
		Collections.sort( 
				employeesNotAssociatedToDepartment, 
				(e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName())
		);
	}


	/**
	 * Add a new employee
	 * 
	 * @param firstname employee's first name
	 * @param lastname  employee's last name
	 * @param age       employee's age
	 * @param salary    employee's salary
	 * @return 1 if the employee is added, 2 if employee exists, 0 otherwise.
	 */
	public int addNewEmployee(String firstname, String lastname, int age, double salary) {
		String fullname = firstname + " " + lastname;
		String[] splitName = fullname.trim().split(" ");

		String name = "";
		for (String str : splitName) {
			str = str.toLowerCase();
			name += str.substring(0, 1).toUpperCase() + str.substring(1) + " ";
		}
		name = name.trim();
		
		int employeesAdded = this.employeeDao
								 .addEmployee(new Employee(name, age, salary));
		return employeesAdded;
	}

	
	/**
	 * Add an employee to a department
	 * 
	 * @param Department ID
	 * @param Employee ID
	 * @return 0 if failed,
	 * @return 1 if successfully associated employee to department
	 * @return 2 employee already associated to the department
	 */
	public int addEmployeeToDepartment(int departmentId, int employeeId){
		return this.employeeDao.addEmployeeToDepartment(departmentId,employeeId);
	}
	
	
	/**
	 * Get all employees
	 * 
	 * @return list of employees sorted by name
	 */
	public List<Employee> getEmployeeList() {
		return this.employeeDao.getAllEmployees();
	}


	/**
	 * Find the Average salary of all employees
	 * 
	 * @return The average salary of all employees
	 */
	public double getEmployeesAverageSalary() {
		return this.employeeDao.employeesAverageSalary();
	}
	
	
	/**
	 * Find the Maximum and Minimum salaries of employees
	 * 
	 * @return [minimum salary, maximum salary] 
	 */
	public List<Double> getMinAndMaxSalary() {
		return this.employeeDao.getMinAndMaxSalary();
	}
	
	
	/**
	 * Find the second Max salary
	 * 
	 * @return The second max salary
	 */
	public double secondMaxEmployeesSalary() {
		return this.employeeDao.getSecondMaximumSalary();
	}

	
	/**
	 * Find the employees earning the second maximum salary
	 * 
	 * @param List of column labels
	 * @param List of employees earning the second maximum salary
	 *        sorted by name ascending
	 */
	public void employeesEarningSecondMaximumSalary(
			List<String> columnLabels, 
			List<Employee> employeesEarningSecondMaximumSalary)
	{
		// Set the column labels
		columnLabels.add("Employee ID");
		columnLabels.add("Name");
		columnLabels.add("Age");
		columnLabels.add("Salary");
		
		// List of employees earning second highest salary
		this.employeeDao.getEmployeesEarningSecondMaximumSalary(
				employeesEarningSecondMaximumSalary
		);
		
		// Sort the list by name
		Collections.sort(
			employeesEarningSecondMaximumSalary,
			(e1,e2) -> e1.getName().compareToIgnoreCase(e2.getName())
		);
	}
}
