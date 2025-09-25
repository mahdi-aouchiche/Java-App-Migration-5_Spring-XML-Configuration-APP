package com.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.demo.entity.Department;
import com.demo.entity.Employee;


public class EmployeeDaoImpl implements EmployeeDao {
	public final SessionFactory sessionFactory;
	
	
	/**
	 * Constructor
	 * @param sessionFactory
	 */
	public EmployeeDaoImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}


	/**
	 * Add an employee to the employee table
	 * 
	 * @param new employee to add
	 * @return 0 if the employee is an error
	 * @return 1 if the new employee added successfully.
	 * @return 2 if the new employee exists already.
	 */
	public int addEmployee(Employee newEmployee) {
		Transaction transaction = null;
		int employeeCount = -1;
		
		try (Session session = sessionFactory.openSession()) 
		{
			transaction = session.beginTransaction();
			session.save(newEmployee);	
				
			// Commit the transaction 
		    transaction.commit();
			employeeCount = 1;
			System.out.println("Employee added to database.");
	    	
		} catch (javax.persistence.PersistenceException e) {
			if(e.getCause() instanceof ConstraintViolationException) {
				// Employee already exists, no need to save.
				employeeCount = 2;
				System.out.println("Employee exists in database");
				e.printStackTrace();
			} else {
        		employeeCount = -1;
        		System.out.println("Error saving the new department.");
        		e.printStackTrace();
			}
    		
		} catch (Exception e) {
			employeeCount = 0;
			transaction.rollback();
			System.out.println("Transaction is rolled back.");
			e.printStackTrace();
		}
		return employeeCount;
	}
	
	
	/**
	 * Delete an employee
	 * 
	 * @param employeeID to delete
	 * @return true if employee is deleted. false otherwise.
	 */
	public Boolean deleteEmployee(int employeeID) {
		Transaction transaction = null;
		Boolean isDeleted = false;
		
		try(Session session = sessionFactory.openSession())
		{			
			transaction = session.beginTransaction();
			// Retrieve the employee from database
			Employee employeeToDelete = session.get(Employee.class, employeeID);
			
			// Clear all departments associations 
			employeeToDelete.getDepartments().clear();
			
			// Delete the Employee
			session.delete(employeeToDelete);
			
			transaction.commit();
			isDeleted = true;
		} catch (Exception e) {
			isDeleted = false;
			transaction.rollback();
			System.out.println("Transaction is rolled back.");
		} 

		return isDeleted;
	}
	
	
	/**
	 * Update employee info
	 * 
	 * @param employeeID
	 * @param name
	 * @param age
	 * @param salary
	 * @return number of employees updated
	 */
	public int updateEmployee(int employeeID, String name, int age, double salary) 
	{
		Transaction transaction = null;
		int result = 0;
		
		try(Session session = sessionFactory.openSession())
		{
			transaction = session.beginTransaction();
				
			Employee employee = session.get(Employee.class, employeeID);
			if(employee != null) {
				employee.setName(name);
				employee.setAge(age);
				employee.setSalary(salary);
			}
			
			session.update(employee);
			transaction.commit();
			result = 1;
			
		} catch (javax.persistence.PersistenceException e) {
			System.out.println("Employee Exists Already.");
		} catch (Exception e) {
			transaction.rollback();
			System.out.println("Transaction is rolled back.");
			e.printStackTrace();
		} 
	
		return result;
	}
	
	
	/**
	 * Get all employees sorted by name
	 * 
	 * @return list of all employees
	 */
	public List<Employee> getAllEmployees() {
		List<Employee> employees = new ArrayList<>();
		
		try(Session session = sessionFactory.openSession()) {
			employees = session.createQuery("From Employee ORDER BY name", Employee.class)
						  	   .setCacheable(true)
						       .getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employees;
	}	
	
	
	/**
	 * Get the list of employees associated to a department
	 * 
	 * @param List of employees who are associated to a department 
	 */
	public void getEmployeesAssociatedToDepartment( 
			List<Employee> employeesAssociatedToDepartment)
	{		
		try (Session session = sessionFactory.openSession())
		{
			// Query to get the employees list
			String hql = "SELECT e FROM Employee e WHERE e.departments IS NOT EMPTY";
			
			// Clear if anyhting is on the list
			employeesAssociatedToDepartment.clear();
			
			// Add all the results (make sure we get a list of employees)
			employeesAssociatedToDepartment.addAll(
					session.createQuery(hql, Employee.class)
						   .setCacheable(true)	   
						   .getResultList()
			);
						
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	

	/**
	 * Get the list of employees associated to a department
	 * 
	 * @param List of employees who are NOT associated to a department 
	 */
	public void getEmployeesNotAssociatedToDepartment(
			List<Employee> employeesNotAssociatedToDepartment) 
	{
		try(Session session = sessionFactory.openSession()) {
									
			// Query to get the employees list
			String hql = "SELECT e FROM Employee e WHERE e.departments IS EMPTY";
			
			// Clear if anyhting is on the list
			employeesNotAssociatedToDepartment.clear();
			
			// Add all the results (make sure we get a list of employees)
			employeesNotAssociatedToDepartment.addAll(
					session.createQuery(hql, Employee.class)
						   .setCacheable(true)	
						   .getResultList());
									
		} catch (Exception e) {
			e.printStackTrace();	
		}		
	}
		
	
	/**
	 * Add an existing employee to a department
	 * 
	 * @param Department id
	 * @param Employee id
	 * @return 0 unsuccessful, employee not associated to the department
	 * @return 1 successfully added employee to department
	 * @return 2 employee is already assigned to the department
	 */
	public int addEmployeeToDepartment(int departmentId, int employeeId) {
        Transaction transaction = null;
        int result = 0;
                
        try(Session session = sessionFactory.openSession()) {
        	transaction = session.beginTransaction();
        	
        	// Retrieve employee and department objects
        	Employee employee = session.get(Employee.class, employeeId);
        	Department department = session.get(Department.class, departmentId);
        	
        	// Check if the employee is associated to the department already
        	if(employee.getDepartments().contains(department)) {
        		// Employee is already associated to the department, no need to save.
        		result = 2;
        		System.out.println("Employee is associated to the department already.");
        	} else {
        		employee.addDepartment(department);
        		session.persist(employee);				
        		result = 1;
				System.out.println("Employee assoiated to the department successfully");
        	}
        	
        	transaction.commit();
		} catch (Exception e) {
			result = 0;
			transaction.rollback();
			System.out.println("Transaction is rolled back.");
			e.printStackTrace();
			
		} 
		return result;
	}
	

	/**
	 * Get the average salary of all employees
	 * 
	 * @return average salary as a double value.
	 */
	public double employeesAverageSalary() {

		double averageSalary = 0.0;	
		String hql = "SELECT COALESCE(AVG(salary), 0) FROM Employee";
		
		try(Session session = sessionFactory.openSession())
		{
			averageSalary =  session.createQuery(hql, Double.class)
									.setCacheable(true)
									.getSingleResult();		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return averageSalary;
	}
	
	
	/**
	 * Get maximum and minimum salaries
	 * 
	 * @return [minimum salary, maximum salary]
	 */
	public List<Double>  getMinAndMaxSalary() {
		List<Double> minAndMaxSalary = new ArrayList<>();
				
		String hql = "SELECT COALESCE(MIN(salary), 0), COALESCE(MAX(salary), 0) FROM Employee";
		
		try(Session session = sessionFactory.openSession()) {
			
			Object result[] = session.createQuery(hql, Object[].class)
								     .setCacheable(true)
								     .getSingleResult();
			
			minAndMaxSalary.addAll(Arrays.asList((Double)result[0], (Double)result[1]));
 			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(minAndMaxSalary.isEmpty()) {
			minAndMaxSalary.addAll(Arrays.asList(0.0,0.0));
		}
		
		return minAndMaxSalary;
	}


	/**
	 * Get Second maximum salary
	 * 
	 * @return Second maximum salary
	 */
	public double getSecondMaximumSalary() {
		double secondMaxSalary = 0;
				
		String hql = "SELECT COALESCE(MAX(salary), 0) " 
				   + "FROM Employee "
				   + "WHERE salary < (SELECT MAX(salary) FROM Employee)";
	
		try(Session session = sessionFactory.openSession()) {	
			secondMaxSalary = session.createQuery(hql, Double.class)
									 .setCacheable(true)
									 .getSingleResult();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return secondMaxSalary;
	}
	
	
	/** 
	 * Get the list of employees earning the second highest salary
	 * 
	 * @param list which will hold the employees earning second max salary
	 */
	public void getEmployeesEarningSecondMaximumSalary(
			List<Employee> employeesEarningSecondMaximumSalary) 
	{
		// Query to get the list of employees
		String hql =  "FROM Employee WHERE salary = ( " 
				   +  "    SELECT MAX(salary) FROM Employee WHERE salary < ( "
				   +  "        SELECT MAX(salary) FROM Employee " 
				   +  "    )" 
				   +  ")";
	
		// Clear the list
		employeesEarningSecondMaximumSalary.clear();
		
		try(Session session = sessionFactory.openSession()) {
		
			employeesEarningSecondMaximumSalary.addAll(
					session.createQuery(hql, Employee.class)
						   .setCacheable(true)
					 	   .getResultList());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
