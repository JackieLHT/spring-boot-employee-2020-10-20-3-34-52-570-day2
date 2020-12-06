package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.DTO.CompanyRequest;
import com.thoughtworks.springbootemployee.DTO.CompanyResponse;
import com.thoughtworks.springbootemployee.DTO.EmployeeResponse;
import com.thoughtworks.springbootemployee.Mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.Mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.Model.Company;
import com.thoughtworks.springbootemployee.Model.Employee;
import com.thoughtworks.springbootemployee.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    List<Company> companies = new ArrayList<>();

    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping
    public List<CompanyResponse> getAll(){
        return companyService.getAll().stream().map(companyMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/{companyId}")
    public CompanyResponse getById(@PathVariable String companyId) {
        Company company = companyService.getById(companyId);
        return companyMapper.toResponse(company);
    }

    @GetMapping("/{companyId}/employees")
    public List<EmployeeResponse> getEmployeesByCompanyId(@PathVariable String companyId) {
        List<Employee> employees = companyService.getEmployeesByCompanyId(companyId);
        return employees.stream().map(employeeMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<CompanyResponse> getPaginatedAll(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize) {
        return companyService.getPaginatedAll(page, pageSize).stream().map(companyMapper::toResponse).collect(Collectors.toList());
    }

    @PostMapping
    public CompanyResponse create(@RequestBody CompanyRequest company) {
        Company newCompany =  companyService.create(companyMapper.toEntity((company)));
        return companyMapper.toResponse(newCompany);
    }

    @PutMapping("/{companyId}")
    public CompanyResponse update(@PathVariable String companyId, @RequestBody CompanyRequest companyUpdate) {
        Company company = companyService.update(companyId, companyMapper.toEntity(companyUpdate));
        return companyMapper.toResponse(company);
    }

    @DeleteMapping("/{companyId}")
    public void delete(@PathVariable String companyId) {
        companyService.delete(companyId);
    }
}
