package com.thoughtworks.springbootemployee.Repository;

import com.thoughtworks.springbootemployee.Model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
    List<Employee> findAllByGender(String gender);
}
