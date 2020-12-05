package com.thoughtworks.springbootemployee.DTO;

import java.util.List;

public class CompanyRequest {
    public CompanyRequest() { }
    private String companyName;
    private List<String> employeeIds;

    public CompanyRequest(String companyName, List<String> employeeIds) {
        this.companyName = companyName;
        this.employeeIds = employeeIds;

    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<String> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<String> employeeIds) {
        this.employeeIds = employeeIds;
    }
}
