package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.Model.Company;
import com.thoughtworks.springbootemployee.Model.Employee;
import com.thoughtworks.springbootemployee.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    List<Company> companies = new ArrayList<>();

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public List<Company> getAll(){
        return companyService.getAll();
    }

    @GetMapping("/{companyId}")
    public Company getById(@PathVariable Integer companyId) {
        return companyService.getById(companyId);
    }

    @GetMapping("/{companyId}/employees")
    public List<Employee> getEmployeesByCompanyId(@PathVariable Integer companyId) {
        return companyService.getEmployeesByCompanyId(companyId);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getPaginatedAll(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize) {
        return companyService.getPaginatedAll(page, pageSize);
    }

    @PostMapping
    public Company create(@RequestBody Company company) {
        companies.add(company);
        return company;
    }
}
