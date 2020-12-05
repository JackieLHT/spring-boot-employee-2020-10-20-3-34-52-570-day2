package com.thoughtworks.springbootemployee.DTO;

import com.thoughtworks.springbootemployee.Model.Employee;

import java.util.List;

public class CompanyResponse {
    private String id;
    private String companyName;
    private Integer employeesNumber;
    private List<EmployeeResponse> employees;

    public CompanyResponse(){}

    public CompanyResponse(String id, String companyName, Integer employeesNumber, List<EmployeeResponse> employees) {
        this.id = id;
        this.employeesNumber = employees.size();
        this.companyName = companyName;
        this.employeesNumber = employeesNumber;
        this.employees = employees;
    }

    public String getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Integer getEmployeesNumber() {
        return employeesNumber;
    }

    public List<EmployeeResponse> getEmployees() {
        return employees;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setEmployeesNumber(Integer employeesNumber) {
        this.employeesNumber = employeesNumber;
    }

    public void setEmployees(List<EmployeeResponse> employees) {
        this.employees = employees;
    }
}
