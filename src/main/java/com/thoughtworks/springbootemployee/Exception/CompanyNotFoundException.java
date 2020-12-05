package com.thoughtworks.springbootemployee.Exception;

public class CompanyNotFoundException extends  RuntimeException{
    public CompanyNotFoundException(String message) {
        super(message);
    }
}
