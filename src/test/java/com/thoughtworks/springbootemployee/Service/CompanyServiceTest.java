package com.thoughtworks.springbootemployee.Service;

import com.thoughtworks.springbootemployee.Exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.Model.Company;
import com.thoughtworks.springbootemployee.Model.Employee;
import com.thoughtworks.springbootemployee.Repository.CompanyRepository;
import com.thoughtworks.springbootemployee.Repository.EmployeeRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @InjectMocks
    private CompanyService companyService;
    @Mock
    CompanyRepository companyRepository;
    @Mock
    EmployeeRepository employeeRepository;

    @Test
    public void should_return_all_companies_when_get_all_given_all_companies() {
        //given
        final List<String> employeeIds = Arrays.asList("1", "2");
        final List<Company> expected = Arrays.asList(
                new Company("alibaba", employeeIds)
        );
        when(companyRepository.findAll()).thenReturn(expected);

        //when
        final List<Company> companies = companyService.getAll();

        //then
        assertEquals(expected, companies);
    }

    @Test
    public void should_return_specific_company_when_get_by_id_given_valid_company_id() {
        //given
        final List<String> employeeIds = Arrays.asList("1", "2");
        final Company expected = new Company("alibaba", employeeIds);
        expected.setId("1");
        when(companyRepository.findById(anyString())).thenReturn(java.util.Optional.of(expected));

        //when
        final Company companies = companyService.getById("1");

        //then
        assertEquals(expected, companies);
    }

    @Test
    public void should_thorw_COMPANY_ID_DOES_NOT_EXIST_exception_when_getById_given_invalid_company_id() {
        //given
        //when
        //then
        assertThrows(CompanyNotFoundException.class, () -> {
            companyService.getById("999");
        }, "Company Id does not exist");
    }

    @Test
    public void should_return_employees_when_get_employees_by_company_given_valid_company_id() {
        //given
        //given
        final List<String> employeeIds = Arrays.asList("1", "2");
        final Company expected = new Company("alibaba", employeeIds);
        final List<Employee> expectedEmployees = Arrays.asList(
                new Employee("david", 22, "male", 11111),
                new Employee("peter", 22, "male", 11111)
        );
        expected.setId("1");
        expectedEmployees.get(0).setId("1");
        expectedEmployees.get(0).setId("2");
        when(companyRepository.findById(anyString())).thenReturn(java.util.Optional.of(expected));
        when(employeeRepository.findAllById(employeeIds)).thenReturn(expectedEmployees);

        //when
        final List<Employee> employees = companyService.getEmployeesByCompanyId("1");

        //then
        assertEquals(expectedEmployees.get(0), employees.get(0));
        assertEquals(expectedEmployees.get(1), employees.get(1));
    }

    @Test
    public void should_return_2_companies_when_get_paginated_all_given_3_companies_and_page_is_0_and_page_size_is_2() {
        //given
        final List<String> employees = Arrays.asList("1", "2");
        final List<Company> expected = Arrays.asList(
                new Company("alibaba", employees),
                new Company("blibaba", employees),
                new Company("clibaba", employees)
        );
        when(companyRepository.findAll()).thenReturn(expected);

        //when
        final List<Company> companies = companyService.getPaginatedAll(0, 2);

        //then
        assertEquals(2, companies.size());
    }

    @Test
    public void should_return_created_company_when_create_given_no_company_in_the_database() {
        //given
        final List<String> employees = Arrays.asList("1", "2");
        final Company expected = new Company("alibaba", employees);
        expected.setId("1");
        when(companyRepository.save(expected)).thenReturn(expected);

        //when
        companyService.create(expected);
        ArgumentCaptor<Company> companyArgumentCaptor = ArgumentCaptor.forClass(Company.class);
        verify(companyRepository, times(1)).save(companyArgumentCaptor.capture());

        //then
        final Company company = companyArgumentCaptor.getValue();
        assertEquals(expected, company);
    }

    @Test
    public void should_return_updated_company_when_update_given_valid_company_id() {
        //given
        final List<String> employees = Arrays.asList("1", "2");
        final Company company = new Company("baliaa", employees);
        final Company expected = new Company("alibaba", employees);
        company.setId("2");
        when(companyRepository.save(any(Company.class))).thenReturn(company);
        when(companyRepository.findById(anyString())).thenReturn(java.util.Optional.of(company));


        //when
        final Company updatedCompany = companyService.update("1", expected);

        //then
        assertEquals("baliaa", updatedCompany.getCompanyName());
        assertEquals(employees, updatedCompany.getEmployeesId());
    }

    @Test
    public void should_thorw_COMPANY_ID_DOES_NOT_EXIST_exception_when_update_given_invalid_company_id() {
        //given
        final List<String> employees = Arrays.asList("1", "2");
        final Company company = new Company("baliaa", employees);
        //when
        //then
        assertThrows(CompanyNotFoundException.class, () -> {
            companyService.update("999", company);
        }, "Company Id does not exist");
    }

    @Test
    public void should_delete_company_when_delete_given_valid_company_id() {
        //given
        final List<String> employees = Arrays.asList("1", "2");
        final Company expected = new Company("alibaba", employees);
        expected.setId("1");
        when(companyRepository.findById(anyString())).thenReturn(java.util.Optional.of(expected));

        //when
        companyService.delete("1");
        //then
        verify(companyRepository, times(1)).deleteById("1");
    }

    @Test
    public void should_thorw_COMPANY_ID_DOES_NOT_EXIST_exception_when_delete_given_invalid_company_id() {
        //given
        //when
        //then
        assertThrows(CompanyNotFoundException.class, () -> {
            companyService.delete("999");
        }, "Company Id does not exist");
    }
}
