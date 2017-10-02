

///////////////////////////////////////////////////////////////////////////////
// Main Class File:  GUItester.java
// File:             EmployeeDatabase.java
// Semester:         CS-367 September 2017
//
// Author:           Sam Adams (sajadams@gmail.com)
// CS Login:         sjadams3
// Lecturer's Name:  Charles Fischer
// Lab Section:      Lecture 4
// TA:				 Gregory Short
//
//
// Pair Partner:     Mike Huang
// Email:            miked.huang@gmail.com
// CS Login:         zhuang335
// Lecturer's Name:  Charles Fischer
// Lab Section:      Lecture 4
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a database of employees with travel wish lists within a company
 *
 * Bugs: none known
 * @author sadams
 * 
 */
public class EmployeeDatabase {
	
	/**
	 List used to store company's employees
	 */
	ArrayList<Employee> EmployeeList;
	
	

	/**
	 Constructs an EmployeeDatabase 
	 */
	EmployeeDatabase(){
		EmployeeList = new ArrayList<Employee>();	
	}
	
	

	/**
	 * Adds an employee to the database
	 *
	 * @param e The employee username to add
	 */
	void AddEmployee(String e) {
		if (containsEmployee(e)) {
			return;
		}
		Employee newEmployee = new Employee(e);
		EmployeeList.add(newEmployee);
	}
	
	/**
	 * Add a destination to a specific employee's wish list
	 *
	 * @param e The employee's username
	 * @param d The specific destination to add
	 */
	void addDestination(String e, String d) {
		if (e==null || d==null) {
			throw new IllegalArgumentException("cannot pass null value into addDestination");
		}
		if (!containsEmployee(e)) {
			throw new IllegalArgumentException("User "+e+" does not exist in database");
		}
		getDestinations(e).add(d);
	}
	
	/**
	 * Returns whether the employee exists in the database
	 *
	 * @param e Employee's username to search
	 * @return True if the employee is the database. Otherwise false
	 */
	boolean containsEmployee(String e) {
		if (e==null) {
			throw new IllegalArgumentException();
		}
		Iterator<Employee> it = EmployeeList.iterator();
		while(it.hasNext()) {
			Employee curEmp = it.next();
			if (curEmp.getUsername().equals(e)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 	Return true if and only if destination d appears in at least one employee's wish list in the database
	 *
	 * @param d The destination to search for
	 * @return Returns true if destination exists in database. False otherwise.
	 */
	boolean containsDestination(String d) {
		if (d==null) {
			throw new IllegalArgumentException("cannot pass in null value");
		}
		Iterator<Employee> it = iterator();
		while(it.hasNext()) {
			Employee curEmp = it.next();
			if (hasDestination(curEmp.getUsername(),d)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns true if and only if destination d is in the wish list for employee e. If employee e is not in the database, return false.
	 *
	 * @param e Username of employee 
	 * @param d Destination to search for
	 * @return Returns true if destination exists in user's wish list. False otherwise.
	 */
	boolean hasDestination(String e, String d) {
		if (d==null || e==null) {
			throw new IllegalArgumentException("cannot pass in null value");
		}
		if (containsEmployee(e)) {
			if(getDestinations(e).contains(d)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Return the list of employees who have destination d in their wish list. If destination d is not in the database, return a null list.
	 *
	 * @param d Destination to find employees with
	 * @return Returns a list of strings corresponding to usernames of employees with that destination in their wish list
	 */
	List<String> getEmployees(String d){
		if (d==null) {
			throw new IllegalArgumentException("cannot pass in null value");
		}
		List<String> empList = new ArrayList<String>();
		Iterator<Employee> it = iterator();
		while(it.hasNext()) {
			String curEmp = it.next().getUsername();
			if (hasDestination(curEmp,d)) {
				empList.add(curEmp);
			}
		}
		return empList;
	}
	
	/**
	 * This method will return an employee's wish list
	 *
	 * @param e The username of the employee
	 * @return Return the wish list for the employee e. If an employee e is not in the database, return null.
	 */
	List<String> getDestinations(String e){
		if (e==null) {
			throw new IllegalArgumentException();
		}
		Iterator<Employee> it = iterator();
		while(it.hasNext()) {
			Employee curEmp = it.next();
			if (curEmp.getUsername().equals(e)) {
				return curEmp.getWishlist();
			}
		}
		return null;
	}
	
	/**
	 * Creates an iterator for the EmployeeDatabase class
	 *
	 */
	Iterator<Employee> iterator(){
		Iterator<Employee> it = EmployeeList.iterator();
		return it;
	}
	

	/**
	 * Removes the specified employee from the database
	 *
	 * @param e Username of employee to remove
	 * @return Returns true if the employee is removed. Otherwise returns false.
	 */
	boolean removeEmployee(String e) {
		
		Iterator<Employee> it=iterator();
		while(it.hasNext()) {
			Employee curEmp = it.next();
			if (curEmp.getUsername().equals(e)) {
				it.remove();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Removes the specified destination from all wish lists in the database
	 *
	 * @param d The destination to remove
	 * @return Returns true if the destination was removed from at least 1 wish list. Otherwise returns false.
	 */
	boolean removeDestination(String d) {
		if (d==null) {
			throw new IllegalArgumentException("cannot pass null value");
		}
		boolean exitVal = false;
		Iterator<Employee> it=iterator();
		while(it.hasNext()) {
			Employee curEmp = it.next();
			if (curEmp.getWishlist().remove(d)) {
				exitVal = true;
			}
		}
		return exitVal;
	}
	
	/**
	 * Provides the size of the database
	 *
	 * @return Returns an integer representing the number of employees in the database
	 */
	int size() {
		return EmployeeList.size();
	}
}
