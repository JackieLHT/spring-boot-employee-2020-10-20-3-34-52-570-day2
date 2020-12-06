package com.thoughtworks.springbootemployee.Exception;

public class CompanyNotFoundException extends  RuntimeException{

    public static final String COMPANY_ID_DOES_NOT_EXIST = "Company Id does not exist";

    public CompanyNotFoundException() {
        super(COMPANY_ID_DOES_NOT_EXIST);
    }
}
