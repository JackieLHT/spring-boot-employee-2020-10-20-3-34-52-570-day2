package com.thoughtworks.springbootemployee.Repository;

import com.thoughtworks.springbootemployee.Model.Company;
import com.thoughtworks.springbootemployee.Model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CompanyRepository extends MongoRepository<Company, String> {
}
