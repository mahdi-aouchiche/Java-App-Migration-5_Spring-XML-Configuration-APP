package com.demo.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Department {
	
	private int id;
	private String name;
	private List<Employee> employees = new ArrayList<>();
	
	
	/**
	 * Default Constructor
	 */
	public Department() {}
	
	
	/**
	 * @param name
	 */
	public Department(String name) {
		super();
		this.name = name;
	}
	
	
	/**
	 * @param id
	 * @param name
	 */
	public Department(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	
	/**
	 * @return department's name
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * @param name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * Get employees of this department
	 * @return list of employees affiliated to the department
	 */
	public List<Employee> getEmployees(){
		return employees;
	}
	
	
	/**
	 * Set the employee list affiliated to the department
	 * @param employees
	 */
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	
	
	/**
	 * Helper method to add an employee
	 * @param employee
	 */
    public void addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.getDepartments().add(this);
    }
	
    
	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		return id == other.id && Objects.equals(name, other.name);
	}
	
	
	@Override
	public String toString() {
		return "Department [ID=" + id + ", Name=" + name + "]";
	}
}