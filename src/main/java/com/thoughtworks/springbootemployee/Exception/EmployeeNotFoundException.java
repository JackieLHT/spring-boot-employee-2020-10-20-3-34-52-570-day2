package com.thoughtworks.springbootemployee.Exception;

public class EmployeeNotFoundException extends RuntimeException{

    public static final String EMPLOYEE_ID_DOES_NOT_EXIST = "Employee Id does not exist";

    public EmployeeNotFoundException() {
        super(EMPLOYEE_ID_DOES_NOT_EXIST);
    }
}

