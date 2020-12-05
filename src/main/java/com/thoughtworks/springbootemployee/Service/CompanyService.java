package com.thoughtworks.springbootemployee.Service;

import com.thoughtworks.springbootemployee.Exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.Model.Company;
import com.thoughtworks.springbootemployee.Model.Employee;
import com.thoughtworks.springbootemployee.Repository.CompanyRepository;
import com.thoughtworks.springbootemployee.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CompanyService {
    public static final String COMPANY_ID_DOES_NOT_EXIST = "Company Id does not exist";
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;


    public CompanyService(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public Company getById(String id) {
        return companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException(COMPANY_ID_DOES_NOT_EXIST));
    }

    public List<Employee> getEmployeesByCompanyId(String id) {
        Company company = getById(id);
        List<String> employeeIds = company.getEmployeeIds();
        Iterable<Employee> employees = employeeRepository.findAllById(employeeIds);
        return StreamSupport
                .stream(employees.spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<Company> getPaginatedAll(Integer page, Integer pageSize) {
        return companyRepository.findAll().stream().skip(page * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company create(Company company) {
        return companyRepository.save(company);
    }

    public Company update(String id, Company companyUpdate) {
        Company company = getById(id);
        companyUpdate.setId(company.getId());
        return companyRepository.save(companyUpdate);
    }

    public void delete(String id) {
        Company company = getById(id);
        companyRepository.deleteById(company.getId());
        return;
    }
}
