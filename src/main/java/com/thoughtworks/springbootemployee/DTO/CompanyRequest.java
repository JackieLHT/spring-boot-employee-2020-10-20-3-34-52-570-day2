package com.thoughtworks.springbootemployee.DTO;

public class CompanyRequest {
    public CompanyRequest() { }
    private String name;

    public CompanyRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
