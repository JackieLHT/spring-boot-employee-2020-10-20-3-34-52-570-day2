package com.thoughtworks.springbootemployee.DTO;

import java.util.List;

public class CompanyResponse {
    private String id;
    private String companyName;
    private Integer employeesNumber;
    private List<String> employeeIds;

    public CompanyResponse(){}

    public CompanyResponse(String id, String companyName, Integer employeesNumber, List<String> employeeIds) {
        this.id = id;
        this.companyName = companyName;
        this.employeesNumber = employeesNumber;
        this.employeeIds = employeeIds;
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

    public List<String> getEmployeeIds() {
        return employeeIds;
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

    public void setEmployeeIds(List<String> employeeIds) {
        this.employeeIds = employeeIds;
    }
}
