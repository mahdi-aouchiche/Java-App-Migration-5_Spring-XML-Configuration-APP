package com.demo.dao;

import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.demo.entity.Department;
import com.demo.entity.Employee;


public class DepartmentDaoImpl implements DepartmentDao {
	private final SessionFactory sessionFactory;
	

    /**
     * Constructor
	 * @param sessionFactory
	 */
	public DepartmentDaoImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	
	/**
     * Create a new department in the database.
     *
     * @param newDepartment The new department to create.
     * @return 2 if department exists,
     * 		   1 if one department was added, 
     * 		   0 if no department was added.
     */
    public int createDepartment(Department newDepartment) {
        Transaction transaction = null;
        int departmentCount = -1;

        try(Session session = sessionFactory.openSession())
        {
            transaction = session.beginTransaction();
            session.save(newDepartment);
            transaction.commit();
            
            departmentCount = 1;
            System.out.println("Department added to database");
        } catch (javax.persistence.PersistenceException e) {
        	if(e.getCause() instanceof ConstraintViolationException) {
        		departmentCount = 2;
        		System.out.println("Department exists in database");
        	}else {
        		departmentCount = -1;
        		System.out.println("Error saving the new department.");
        		e.printStackTrace();
        	}
        } catch (Exception e) {
            departmentCount = 0;
            if (transaction != null && transaction.getStatus().canRollback()) {
                try {
                    transaction.rollback();
                } catch (Exception rollbackEx) {
                    System.out.println("Rollback failed: " + rollbackEx.getMessage());
                }
            }
            System.out.println("Transaction is rolled back.");
            e.printStackTrace();
        }
        return departmentCount;
    }
            
    
 	/**
	 * Get all departments from database
	 * 
	 * @return list of all departments
	 */
	public List<Department> getAllDepartments(){
				
		try(Session session = sessionFactory.openSession()) 
		{
			return session.createQuery("from Department ORDER BY name", Department.class)
						  .setCacheable(true)	    
						  .getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	
	/**
	 * Get number of employees in each department
	 * 
	 * @return a map of employee count of each department sorted by department name
	 */
	public LinkedHashMap<Department, Integer> employeeCountByDepartment() {		
		LinkedHashMap<Department, Integer> map = new LinkedHashMap<>();
		
		String hql = "SELECT d, COUNT(e) FROM Department d "
				   + "LEFT JOIN d.employees e "
				   + "GROUP BY d "
				   + "ORDER BY d.name ASC";
		
		try(Session session = sessionFactory.openSession()) {
			
			List<Object[]> resultList = session.createQuery(hql, Object[].class)
											   .setCacheable(true)
											   .getResultList();
			
			for(Object[] row : resultList) {
				map.put((Department)row[0], ((Long) row[1]).intValue());
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}

		
	/**
	 * Get the average salary for each department Ordered by average salary Desc
	 * 
	 * @return list of departments and their average salaries
	 */
	public LinkedHashMap<Department, Double> averageSalaryByDepartment() {
		LinkedHashMap<Department, Double> avgSalaries = new LinkedHashMap<>();

		String hql = "SELECT d, COALESCE(AVG(e.salary), 0) AS avgSalary "
				   + "From Department d " 
				   + "LEFT JOIN d.employees e "
				   + "GROUP BY d " 
				   + "ORDER BY avgSalary DESC, d.name ASC";

		try (Session session = sessionFactory.openSession()) {
			List<Object[]> avgSalaryByDepartmentList = session.createQuery(hql, Object[].class)
															  .setCacheable(true)
															  .getResultList();
			
			for(Object[] row: avgSalaryByDepartmentList) {
				avgSalaries.put((Department) row[0], (Double) row[1]);			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return avgSalaries;
	}

	
	/**
	 * Get a list of departments which has a at least given number of employees
	 * Ordered by highest number of employees to lowest then by department name
	 * 
	 * @param minimum employee count per department
	 * @Return list of departments which have at least the minimum employee count
	 */	
	public List<Object[]> departmentsByNumberOfEmployees(long numEmployees) {		
		String hql = "SELECT d, COUNT(e) FROM Department d "
				   + "LEFT JOIN d.employees e "
				   + "GROUP BY d "
				   + "HAVING COUNT(e) >= :numberEmployees "
				   + "ORDER BY COUNT(e), d.name";
		
		try(Session session = sessionFactory.openSession()) {
			
			return session.createQuery(hql, Object[].class)
						  .setParameter("numberEmployees", numEmployees)
						  .setCacheable(true)
						  .getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}


	/**
	 * Delete a Department
	 * 
	 * @param department-id
	 * @return true if department is deleted, false otherwise
	 */
	public boolean deleteDepartment(int departmentId) {
	    Transaction transaction = null;
	    boolean isDeleted = false;
	    try (Session session = sessionFactory.openSession()) {
	        transaction = session.beginTransaction();
	        
	        Department department = session.get(Department.class, departmentId);
	                    
	        if (department != null) {
	            // Remove the department from each associated employee
	            for (Employee employee : department.getEmployees()) {
	                employee.getDepartments().remove(department);
	             // Persist the change to the employee
	                session.update(employee); 
	            }
	            
	            // Now, with all associations removed, safely delete the department
	            session.delete(department);
	            isDeleted = true;
	        }
	         
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	            System.out.println("Delete transaction is rolled back.");
	        }
	        e.printStackTrace();
	    }
	    return isDeleted;
	}
}