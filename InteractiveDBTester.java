///////////////////////////////////////////////////////////////////////////////
// Main Class File:  GUItester.java
// File:             InteractiveDBTester.java
// Semester:         CS-367 September 2017
//
// Author:           Sam Adams (sajadams@gmail.com)
// CS Login:         sjadams3
// Lecturer's Name:  Charles Fischer
// Lab Section:      Lecture 4
// TA:				 Gregory Short
//
// Pair Partner:     Mike Huang
// Email:            miked.huang@gmail.com
// CS Login:         zhuang335
// Lecturer's Name:  Charles Fischer
// Lab Section:      Lecture 4
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.*;
import java.io.*;

/**
 * Provides methods to interact with an EmployeeDatabase object as an end-user
 * 
 * Bugs: None none
 * @author sadams
 *
 */
public class InteractiveDBTester {
	
	// make the Employee database a static data member so it does not have to 
	// be passed to each help method
        // It is protected so that subclasses representing particular testers can
        // access it

	protected static EmployeeDatabase EmployeeDB = new EmployeeDatabase();
	
        // Initialize DB from external data file
        protected static void populateDB(String [] args) {
    	
    	// Step 1: check whether exactly one command-line argument is given
    	if(args.length != 1) {
    		System.out.println("Please provide input file as command-line argument");
    		return;
    	}
    	// Step 2: check whether the input file exists and is readable
	   
    	
    	
    	File srcFile = new File(args[0]);
    	if (!srcFile.exists() || !srcFile.canRead()) {
    		System.out.println("Error: Cannot access input file: "+srcFile.toString());
    		System.out.println("Exists: "+srcFile.exists());
    		System.out.println("CanRead: "+srcFile.canRead());
    		System.out.println("Working directory "+System.getProperty("user.dir"));
    		return;
    	}
    	
    	// Step 3: load the data from the input file and use it to construct a
    	//         Employee database
    	try {
    	Scanner fileIn = new Scanner(srcFile);
    	while(fileIn.hasNext()) {
    		String line = fileIn.nextLine();
    		String[] tokens = line.split(",");
    		for (int i = 0; i < tokens.length; i++) {
    			if (i==0) {
    				EmployeeDB.AddEmployee(tokens[i].toLowerCase());
    			}
    			else {
    				EmployeeDB.addDestination(tokens[0].toLowerCase(), tokens[i].toLowerCase());
    			}
    		}
    	}
    	fileIn.close();
    	}
    	catch (FileNotFoundException ex){
    		System.out.println("Error: Cannot access input file");
    		return;
    	}
    	//catch (IllegalArgumentException ex) {
    	//	System.out.println("Error: File contained illegal argument");
    	//	return;
    	//}
   
	  
       }

     static boolean GUIactive;  //is GUI tester active?

// Methods that implement GUI buttons or testing actions

    /**
     * Returns the supplied employee and their destination wish list
     * @param employeee The employee's username
     * @return Returns a string in the form "employee:destination1,destination2,destination3" if the employee exists other returns "Employee not found"
     */
    protected static String pushFind(String employee){
       /* Code to implement find command goes here:
          Find the supplied employee in the employee database
          employee:destination1,destination2,destination3
       */
    	String empInfo = "";
    	 if (EmployeeDB.containsEmployee(employee.toLowerCase())) {
    		 empInfo +=employee.toLowerCase()+":";
    		 List<String> tmpDest = EmployeeDB.getDestinations(employee.toLowerCase());
    		 for (int i = 0; i < tmpDest.size(); i++) {
    			 empInfo += tmpDest.get(i);
    			 if (i<(tmpDest.size()-1)) {
    				 empInfo +=",";
    			 }
    		 }
    	 }
    	 else {
    		 empInfo +="Employee not found\n";
    	 }
    	 return empInfo;
    }

    /**
     * Removes a destination from the database
     *
     * @param destination The destination to remove from the database
     * @return Returns a string of the form "destination removed" if the destination existed and was removed. Otherwise returns "destination not found".
     */
    protected static String pushDiscontinue(String destination){
       /* Code to implement discontinue command goes here:
          The supplied destination is removed from the wish lists
           of all employees in the employee database
       */
    	String discStr = "";
    	if(EmployeeDB.removeDestination(destination.toLowerCase())) {
    		discStr += "destination removed";
    	}
    	else {
    		discStr += "destination not found";
    	}
    	return discStr;
    }

    /**
     * Searches the employee database for employees with the specified destination
     *
     * @param destination The destination to search for
     * @return Returns a string of the form "destination:emp1,emp2,etc" if the destination is found. Otherwise returns "destination not found"
     */
    protected static String pushSearch(String destination){
       /* Code to implement search command goes here:
           Search the employee database for all employees who have
            the supplied destination in their wish list
       */
    	
    	String searchStr = "";
    	List<String> empList = EmployeeDB.getEmployees(destination.toLowerCase());
    	if(empList.size()>0){
    		searchStr += destination+":";
    		Iterator<String> it = empList.iterator();
    		while(it.hasNext()) {
    			searchStr += it.next();
    			if(it.hasNext()) {
    				searchStr +=",";
    			}
    		}
    	}
    	else {
    		searchStr += "destination not found";
    	}
    	return searchStr;		
    }

    /**
     * Removes an employee from the database
     *
     * @param employee Username of employee to remove
     * @return Returns string "Employee removed" if the employee was found and removed. Otherwise returns "Employee not found"
     */
    protected static String pushRemove(String employee){
       /* Code to implement remove command goes here:
          Remove the supplied employee from the employee database
       */
    	String removeStr = "";
    	if(EmployeeDB.removeEmployee(employee.toLowerCase())) {
    		removeStr +="Employee removed";
    	}
    	else {
    		removeStr +="Employee not found";
    	}
    	return removeStr;
    }

    /**
     * Provides high-level summary information about the Employee Database
     *
     * @return Returns statistics about:
     *  <p>total unique destinations and employees in database.
     *  <p> Most popular destination in DB
     *  <p> Most, least, and average number of employees per destination
     *  <p> Most, least, and average number of destinations per employee 
     */
    protected static String pushInformation(){
       /* Code to implement information command goes here:
           Compute key information on the state of the employee database
       */
    	String pushStr = "";
    	int empCnt = EmployeeDB.size();
    	int destPerEmpMax = Integer.MIN_VALUE;
    	int destPerEmpMin = Integer.MAX_VALUE;
    	double destPerEmpSum=0;
    	int empPerDestMax = Integer.MIN_VALUE;
    	int empPerDestMin = Integer.MAX_VALUE;
    	double empPerDestSum=0;
    	Iterator<Employee> it = EmployeeDB.iterator();
    	List<String> destList = new ArrayList<String>(); //Remove duplicates via hashed set
    	while(it.hasNext()) {
    		Employee curEmp = it.next();
    		Iterator<String> dit = curEmp.getWishlist().iterator();
    		while(dit.hasNext()) {
    			String curDest=dit.next();
    			if(!destList.contains(curDest)) {
    				destList.add(curDest);
    			}
    		}
    		//destList.addAll(curEmp.getWishlist()); //add unique destinations to hashed set
    		int curEmpDestCnt=curEmp.getWishlist().size();
    		destPerEmpMax = Math.max(destPerEmpMax,curEmpDestCnt);
    		destPerEmpMin = Math.min(destPerEmpMax,curEmpDestCnt);
    		destPerEmpSum+=curEmpDestCnt;
    	}
    	int destCnt=destList.size();
    	Iterator<String> itDest = destList.iterator();
    	List<String> destWithMaxEmp = new ArrayList<String>();
    	while(itDest.hasNext()) {
    		String curDest = itDest.next();
    		int curDestEmpCnt = EmployeeDB.getEmployees(curDest).size();
    		//pushStr +="Debug: "+curDest+" Size: "+curDestEmpCnt+"\n";
    		if (empPerDestMax <= curDestEmpCnt) {
    			if (empPerDestMax < curDestEmpCnt) {
    				empPerDestMax = curDestEmpCnt;
    				destWithMaxEmp.clear();
    			}
    			destWithMaxEmp.add(curDest);
    		}
    		empPerDestMin = Math.min(empPerDestMin, curDestEmpCnt);
    		empPerDestSum+=curDestEmpCnt;
    	}
    	
    	pushStr +="Employees: "+empCnt+", Destinations: "+destCnt+"\n";
    	pushStr +="# of destinations/employee: most "+destPerEmpMax+", least "+destPerEmpMin+", average "+Math.round(10*destPerEmpSum/empCnt)/10.0+"\n";
    	pushStr +="# of employees/destination: most "+empPerDestMax+", least "+empPerDestMin+", average "+Math.round(10*empPerDestSum/destCnt)/10.0+"\n";
    	pushStr +="Most popular destination: ";
    	Iterator<String> itDestPop = destWithMaxEmp.iterator();
    	while (itDestPop.hasNext()) {
    		pushStr +=itDestPop.next();
    		if (itDestPop.hasNext()){
    			pushStr+=",";
    		}
    	}
    	pushStr +="["+empPerDestMax+"]\n";
    	
    	return pushStr;
    }

    /**
     * Lists out full information about the DB content
     *
     * @return A string in the form of:
     * <p> Employee1:dest1,dest2,dest3,etc.
     * <p> Employee2:dest1,dest2,dest3
     * <p> Employee3:dest1,dest2,dest3
     * <p> etc
     */
    protected static String pushList(){
       /* Code to implement list command goes here:
          List the current contents of the employee database
       */
    	String listStr = "";
    	Iterator<Employee> itEmp = EmployeeDB.iterator();
    	while (itEmp.hasNext()) {
    		listStr +=pushFind(itEmp.next().getUsername());
    		if (itEmp.hasNext()) {
    			listStr +="\n";
    		}
    	}
    	
    	return listStr;
    }

    // The pushHelp method may be used as is:

    /**
     * Explains how to use other commands
     *
     * @return Returns help information about each relevant push command
     */
    protected static String pushHelp(){
        String cmds = "";
	if (GUIactive) {
          cmds +="Available commands:\n" +
                "\tFind employee\n" +
                "\tDiscontinue destination\n" +
                "\tSearch destination\n" +
                "\tRemove employee\n" +
                "\tInformation on database\n" +
                "\tList contents of database\n" +
                "\tText interface activated\n" +
                "\tHelp on available commands\n" +
                "\tQuit database testing\n" ;
       }else {
          cmds +=
        	("discontinue/d <destination> - discontinue the given <destination>\n") +
        	("find/f <Employee> - find the given <Employee>\n") +
        	("gui/g Switch to GUI testing interface\n") +
        	("help/h - display this help menu\n") +
        	("information/i - display information about this Employee database\n") +
        	("list/l - list contents of Employee database\n") +
        	("search/s <destination> - search for the given <destination>\n") +
        	("quit/q - quit\n") +
        	("remove/r <Employee> - remove the given <Employee>\n");
         
       }
            return cmds;
    }

    // The pushQuit method may be used as is:

    /**
     * Exits the program
     *
     * @return An empty string
     */
    protected static String pushQuit(){
        System.exit(0);
        return "";
    }
}