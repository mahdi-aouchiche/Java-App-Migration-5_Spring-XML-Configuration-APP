package com.demo.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Employee {

	private int id;
	private String name;
	private int age;
	private double salary;
	private List<Department> departments = new ArrayList<>();
	
	
	/**
	 * Default Constructor
	 */
	public Employee() {}
	
	
	/**
	 * Constructor
	 * @param name
	 * @param age
	 * @param salary
	 */
	public Employee(String name, int age, double salary) {
		super();
		this.name = name;
		this.age = age;
		this.salary = salary;
	}


	/**
	 * Constructor
	 * @param id
	 * @param name
	 * @param age
	 * @param salary
	 */
	public Employee(int id, String name, int age, double salary) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.salary = salary;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}
	
	
	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}
	
	
	/**
	 * @return the salary
	 */
	public double getSalary() {
		return salary;
	}
	
	
	/**
	 * @param salary the salary to set
	 */
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	
	/**
	 * Get departments list of where the employee is affiliated 
	 * @return departments list
	 */
	public List<Department> getDepartments() { 
		return departments; 
	}
	
	
	/**
	 * Set the department list where the employee is affiliated
	 * @param departments
	 */
	public void setDepartments(List<Department> departments) {
		this.departments = departments; 
	}
	
	
	/**
	 * Helper method to add a department
	 * @param department
	 */
    public void addDepartment(Department department) {
        this.departments.add(department);
        department.getEmployees().add(this);
    }
	
    
	@Override
	public int hashCode() {
		return Objects.hash(age, id, name, salary);
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return age == other.age && id == other.id && Objects.equals(name, other.name)
				&& Double.doubleToLongBits(salary) == Double.doubleToLongBits(other.salary);
	}
	
	
	@Override
	public String toString() {
		return "Employee [ID=" + id + ", Name=" + name + ", Age=" + age + ", Salary=" + salary + "]";
	}
}

