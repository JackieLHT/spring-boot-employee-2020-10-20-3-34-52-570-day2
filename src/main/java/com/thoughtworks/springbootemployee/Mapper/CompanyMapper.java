package com.thoughtworks.springbootemployee.Mapper;

import com.thoughtworks.springbootemployee.DTO.CompanyRequest;
import com.thoughtworks.springbootemployee.DTO.CompanyResponse;
import com.thoughtworks.springbootemployee.Model.Company;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {
    public Company toEntity(CompanyRequest companyRequest) {
        Company company = new Company();
        BeanUtils.copyProperties(companyRequest, company);
        return company;
    }

    public CompanyResponse toResponse(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        BeanUtils.copyProperties(company, companyResponse);
        return companyResponse;
    }
}
