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
    private Integer employeesNumber;
    private List<String> employeesId;

    public Company() {
    }

    public Company(String companyName, List<String> employees) {
        this.companyName = companyName;
        this.employeesId = employees;
        this.employeesNumber = employees.size();
    }

    public void setId(String id) {
        this.id = id;
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

    public List<String> getEmployeesId() {
        return employeesId;
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
        return this.id.equals(other.id) && this.companyName.equals(other.companyName) && this.employeesNumber == other.employeesNumber && this.employeesId == other.employeesId;
    }
}
