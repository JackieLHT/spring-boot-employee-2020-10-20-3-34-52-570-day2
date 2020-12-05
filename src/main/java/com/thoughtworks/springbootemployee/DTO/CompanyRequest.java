package com.thoughtworks.springbootemployee.DTO;

import com.thoughtworks.springbootemployee.Model.Employee;

import java.util.List;

public class CompanyRequest {
    public CompanyRequest() { }
    private String name;
    private List<EmployeeRequest> employees;

    public CompanyRequest(String name, List<EmployeeRequest> employeeRequest) {
        this.name = name;
        this.employees = employeeRequest;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EmployeeRequest> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeRequest> employees) {
        this.employees = employees;
    }
}
