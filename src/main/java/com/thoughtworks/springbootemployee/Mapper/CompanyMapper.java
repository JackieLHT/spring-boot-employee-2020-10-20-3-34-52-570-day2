package com.thoughtworks.springbootemployee.Mapper;

import com.thoughtworks.springbootemployee.DTO.CompanyRequest;
import com.thoughtworks.springbootemployee.DTO.CompanyResponse;
import com.thoughtworks.springbootemployee.DTO.EmployeeResponse;
import com.thoughtworks.springbootemployee.Model.Company;
import com.thoughtworks.springbootemployee.Service.CompanyService;
import com.thoughtworks.springbootemployee.Service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompanyMapper {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private EmployeeService employeeService;
    public Company toEntity(CompanyRequest companyRequest) {
        Company company = new Company();
        BeanUtils.copyProperties(companyRequest, company);
        return company;
    }

    public CompanyResponse toResponse(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        BeanUtils.copyProperties(company, companyResponse);
        List<EmployeeResponse> employees= companyService.getEmployeesByCompanyId(company.getId()).stream().map(employeeMapper::toResponse).collect(Collectors.toList());
//        List<EmployeeResponse> employees = company.getEmployeeIds().stream().map(employeeId -> employeeMapper.toResponse(employeeService.getById(employeeId))).collect(Collectors.toList());
        companyResponse.setEmployees(employees);
        companyResponse.setEmployeesNumber(company.getEmployeeIds().size());
        return companyResponse;
    }
}
