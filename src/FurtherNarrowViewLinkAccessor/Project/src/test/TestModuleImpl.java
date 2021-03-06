/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.ViewLinkImpl;

import test.common.TestModule;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class TestModuleImpl extends ApplicationModuleImpl implements TestModule {
  /**This is the default constructor (do not remove)
   */
  public TestModuleImpl() {
  }

  /**Sample main for debugging Business Components code using the tester.
   */
  public static void main(String[] args) {
    launchTester("test", /* package name */
      "TestModuleLocal" /* Configuration Name */);
  }
  /*
   * Example to help understand the concepts described in section
   * "27.1.3 Understanding View Link Accessors Versus Data Model View Link Instances"
   * of the ADF Developer's Guide for Forms/4GL Developers
   * 
   * The WorksInDeptLink view link defines a link between DeptView and EmpView
   * with a view link accessor named "DepartmentStaff"
   * 
   * 
   */
  public void test() {
    ViewObject vo = getDepartments();
    // Find Department view row with Deptno = 10
    DeptViewRowImpl dept = (DeptViewRowImpl)vo.findByKey(new Key(new Object[]{10}),1)[0];
    // Access its RowSet of employees in the department using the
    // view link accessor attribute
    RowSet employees = (RowSet)dept.getDepartmentStaff();
    System.out.println("Employees in department "+dept.getDeptno());
    // Iterate over the view link accessor rowset to print the employees
    while (employees.hasNext()) {
      EmpViewRowImpl emp = (EmpViewRowImpl)employees.next();
      System.out.println(emp.getEname()+", id = "+emp.getEmpno());
    }
    /*
     * Now, further filter the view link accessor rowset by adding a new,
     * additional WHERE clause ENAME LIKE UPPER(:NameMatch)||'%',
     * defining a new named bind variable "NameMatch" at runtime, and
     * setting the value of this bind variable to the letter 'K' to restrict
     * the rowset to only show those employees in the department whose name
     * starts with the letter K
     */
    System.out.println("Employees in department "+dept.getDeptno()+" whose name starts with K...");
    // Access the system-created view object instance dedicated to the view link accessor
    ViewObject viewLinkAccessorVOInstance = employees.getViewObject();
    // Add a new where clause
    viewLinkAccessorVOInstance.setWhereClause("ENAME LIKE UPPER(:NameMatch)||'%'");
    // Define the new named bind variable
    viewLinkAccessorVOInstance.defineNamedWhereClauseParam("NameMatch",null,null);
    // Set the value of the new named bind variable.
    //
    // NOTE: We set the bind variable value on the RowSet, not the ViewObject instance
    // ~~~~
    employees.setNamedWhereClauseParam("NameMatch","K");
    employees.executeQuery();
    // Iterate over the view link accessor rowset to print the employees
    while (employees.hasNext()) {
      EmpViewRowImpl emp = (EmpViewRowImpl)employees.next();
      System.out.println(emp.getEname()+", id = "+emp.getEmpno());
    }
    
    System.out.println("Employees in department "+dept.getDeptno()+" using detail view object in data model...");
    /*
     * Now, show that the data model master/detail is based on a different
     * instance of the same base EmpView view object by showing that it
     * is not runtime-filtered in the same way as the view link accessor's
     * view object instance was above.
     */
    ViewObject empView = getEmployees();
    while (empView.hasNext()) {
      EmpViewRowImpl emp = (EmpViewRowImpl)empView.next();
      System.out.println(emp.getEname()+", id = "+emp.getEmpno());
    }
    System.out.println("Employees in department "+dept.getDeptno()+" using detail VO whose name contains 'L'...");
    /*
     * We can even runtime-filter this view object instance differently
     * than the one above to illustrate again that they are independent.
     * We'll define a similar condition but one where only the employees
     * whose name contains an 'L' will be returned...
     */
     empView.setWhereClause("ENAME LIKE '%'||UPPER(:NameMatch)||'%'");
     empView.defineNamedWhereClauseParam("NameMatch",null,null);
     empView.setNamedWhereClauseParam("NameMatch","L");
     empView.executeQuery();
    while (empView.hasNext()) {
      EmpViewRowImpl emp = (EmpViewRowImpl)empView.next();
      System.out.println(emp.getEname()+", id = "+emp.getEmpno());
    }
     
  }

  /**Container's getter for Departments
   */
  public DeptViewImpl getDepartments() {
    return (DeptViewImpl)findViewObject("Departments");
  }

  /**Container's getter for Employees
   */
  public EmpViewImpl getEmployees() {
    return (EmpViewImpl)findViewObject("Employees");
  }

  /**Container's getter for WorksInDeptLink1
   */
  public ViewLinkImpl getWorksInDeptLink1() {
    return (ViewLinkImpl)findViewLink("WorksInDeptLink1");
  }
}
