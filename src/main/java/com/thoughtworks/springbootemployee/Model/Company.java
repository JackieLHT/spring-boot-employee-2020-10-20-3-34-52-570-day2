package com.thoughtworks.springbootemployee.Model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document
public class Company {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String companyName;
    private List<String> employeeIds;

    public Company() {
    }

    public Company(String companyName, List<String> employeeIds) {
        this.companyName = companyName;
        this.employeeIds = employeeIds;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setEmployeeIds(List<String> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public String getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public List<String> getEmployeeIds() {
        return employeeIds;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Company other = (Company) obj;
        return this.id.equals(other.id) && this.companyName.equals(other.companyName) && this.employeeIds == other.employeeIds;
    }
}
