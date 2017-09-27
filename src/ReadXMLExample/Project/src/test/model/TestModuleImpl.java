/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.DBSequence;
import oracle.jbo.server.ApplicationModuleImpl;
import test.model.common.TestModule;
import test.model.entities.EmpImpl;
import test.model.views.DeptViewImpl;
import test.model.views.EmpViewImpl;
import oracle.jbo.server.ViewLinkImpl;
import oracle.jbo.client.Configuration;
import oracle.jbo.*;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.*;
//  ---------------------------------------------------------------------
//  ---    File generated by Oracle ADF Business Components Design Time.
//  ---    Custom code may be added to this class.
//  ---------------------------------------------------------------------

public class TestModuleImpl extends ApplicationModuleImpl implements test.model.common.TestModule  {
  /**
   * 
   *  This is the default constructor (do not remove)
   */
  public TestModuleImpl() {
  }


  public void testNewEmpAndNewDeptProgrammatically() {
    /*
     * Consciously try to create the Emp before the Dept
     */
    ViewObject emps = getAllEmployees();
    Row newEmp = emps.createRow();
    emps.insertRow(newEmp);
    newEmp.setAttribute("Ename","Steve");
    ViewObject depts = getDepartments();
    Row newDept = depts.createRow();
    depts.insertRow(newDept);
    newDept.setAttribute("Dname","NewDept");
    newEmp.setAttribute("Deptno",((DBSequence)newDept.getAttribute("Deptno")).getSequenceNumber());
    getDBTransaction().commit();    
  }

  /**
   * 
   *  Container's getter for WorksInDeptLink1
   */
  public ViewLinkImpl getWorksInDeptLink1() {
    return (ViewLinkImpl)findViewLink("WorksInDeptLink1");
  }

  /**
   * 
   *  Sample main for debugging Business Components code using the tester.
   */
  public static void main(String[] args) {
    // launchTester("test.model", "TestModuleLocal");
    String        amDef = "test.model.TestModule";
    String        config = "TestModuleLocal";
    TestModule am = (TestModule)
    Configuration.createRootApplicationModule(amDef,config);
    am.testNewEmpAndNewDeptProgrammatically();
    Configuration.releaseRootApplicationModule(am,true);
  }

  /**
   * 
   *  Container's getter for Departments
   */
  public DeptViewImpl getDepartments() {
    return (DeptViewImpl)findViewObject("Departments");
  }

  /**
   * 
   *  Container's getter for AllEmployees
   */
  public EmpViewImpl getAllEmployees() {
    return (EmpViewImpl)findViewObject("AllEmployees");
  }

  /**
   * 
   *  Container's getter for Employees
   */
  public EmpViewImpl getEmployees() {
    return (EmpViewImpl)findViewObject("Employees");
  }

}