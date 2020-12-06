package com.thoughtworks.springbootemployee.Intergration;

import com.thoughtworks.springbootemployee.Exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.Model.Company;
import com.thoughtworks.springbootemployee.Model.Employee;
import com.thoughtworks.springbootemployee.Repository.CompanyRepository;
import com.thoughtworks.springbootemployee.Repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @AfterEach
    void tearDown() {
        companyRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    public void should_return_all_companies_when_get_all_given_all_companies() throws Exception {
        //given
        Employee employee1 = new Employee("David", 18, "male", 10000);
        Employee employee2 = new Employee("Jackie", 18, "female", 10000);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        List<String> employees = new ArrayList<>();
        for (Employee employee : employeeRepository.findAll()) {
            employees.add(employee.getId());
        }
        Company company = new Company("alibaba", employees);
        companyRepository.save(company);
        //when
        //then
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].companyName").value("alibaba"))
                .andExpect(jsonPath("$[0].employeesNumber").value(2))
                .andExpect(jsonPath("$[0].employees", hasSize(2)));

    }

    @Test
    public void should_return_specific_company_when_get_by_id_given_valid_company_id() throws Exception {
        //given
        Employee employee1 = new Employee("David", 18, "male", 10000);
        Employee employee2 = new Employee("Jackie", 18, "female", 10000);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        List<String> employees = new ArrayList<>();
        for (Employee employee : employeeRepository.findAll()) {
            employees.add(employee.getId());
        }
        Company company = new Company("alibaba", employees);
        companyRepository.save(company);
        //when
        //then
        mockMvc.perform(get("/companies/" + company.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.companyName").value("alibaba"))
                .andExpect(jsonPath("$.employeesNumber").value(2))
                .andExpect(jsonPath("$.employees", hasSize(2)));

    }

    @Test
    public void should_throw_COMPANY_ID_DOES_NOT_EXIST_exception_when_getById_given_invalid_company_id() throws Exception {
        //given
        Employee employee1 = new Employee("David", 18, "male", 10000);
        Employee employee2 = new Employee("Jackie", 18, "female", 10000);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        List<String> employees = new ArrayList<>();
        for (Employee employee : employeeRepository.findAll()) {
            employees.add(employee.getId());
        }
        Company company = new Company("alibaba", employees);
        companyRepository.save(company);
        //when
        //then
        mockMvc.perform(get("/companies/" + "5fc8913234ba53396c26a863"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CompanyNotFoundException))
                .andExpect(result -> assertEquals("Company Id does not exist", result.getResolvedException().getMessage()));

    }

    @Test
    public void should_return_all_employees_of_specific_company_when_getEmployeesByCompanyId_given_companyId() throws Exception {
        //given
        Employee employee1 = new Employee("David", 18, "male", 10000);
        Employee employee2 = new Employee("Jackie", 18, "female", 10000);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        List<String> employees = new ArrayList<>();
        for (Employee employee : employeeRepository.findAll()) {
            employees.add(employee.getId());
        }
        Company company = new Company("alibaba", employees);
        companyRepository.save(company);
        //when
        //then
        mockMvc.perform(get("/companies/" + company.getId() + "/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(employees.get(0), employees.get(1))))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("David", "Jackie")))
                .andExpect(jsonPath("$[*].age", containsInAnyOrder(18, 18)))
                .andExpect(jsonPath("$[*].gender", containsInAnyOrder("male", "female")))
                .andExpect(jsonPath("$[*].salary", containsInAnyOrder(10000, 10000)));
    }

    @Test
    public void should_throw_COMPANY_ID_DOES_NOT_EXIST_exception_when_getEmployeesByCompanyId_given_invalid_company_id() throws Exception {
        //given
        Employee employee1 = new Employee("David", 18, "male", 10000);
        Employee employee2 = new Employee("Jackie", 18, "female", 10000);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        List<String> employees = new ArrayList<>();
        for (Employee employee : employeeRepository.findAll()) {
            employees.add(employee.getId());
        }
        Company company = new Company("alibaba", employees);
        companyRepository.save(company);
        //when
        //then
        mockMvc.perform(get("/companies/" + "5fc8913234ba53396c26a863"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CompanyNotFoundException))
                .andExpect(result -> assertEquals("Company Id does not exist", result.getResolvedException().getMessage()));

    }

    @Test
    public void should_return_2_companies_when_get_by_paging_given_3_companies_and_page_number_is_0_and_pageSize_is_2() throws Exception {
        //given
        Employee employee1 = new Employee("David", 18, "male", 10000);
        Employee employee2 = new Employee("Jackie", 18, "female", 10000);
        Employee employee3 = new Employee("Tom", 18, "male", 10000);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);
        Company company1 = new Company("KFC", Arrays.asList(employee1.getId()));
        Company company2 = new Company("UNIQUO", Arrays.asList(employee2.getId()));
        Company company3 = new Company("LOGON", Arrays.asList(employee3.getId()));
        companyRepository.save(company1);
        companyRepository.save(company2);
        companyRepository.save(company3);
        //when
        //then
        mockMvc.perform(get("/companies").param("page", String.valueOf(0)).param("pageSize", String.valueOf(2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void should_return_created_company_when_create_given_valid_company() throws Exception {
        //given
        Employee employee1 = new Employee("David", 18, "male", 10000);
        Employee employee2 = new Employee("Jackie", 18, "female", 10000);
        employee1.setId("5fc8829e3bf5ed6bcd35a295");
        employee2.setId("5fc882b23bf5ed6bcd35a296");
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        String companyAsJson = "{\n" +
                "    \"companyName\": \"alibaba\",\n" +
                "    \"employeeIds\": [\"5fc8829e3bf5ed6bcd35a295\",\"5fc882b23bf5ed6bcd35a296\"]\n" +
                "}";

        //when
        //then
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyAsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.companyName").value("alibaba"))
                .andExpect(jsonPath("$.employeesNumber").value(2))
                .andExpect(jsonPath("$.employees", hasSize(2)));

        List<Company> companies = companyRepository.findAll();
        assertEquals(1, companies.size());
        assertEquals("alibaba", companies.get(0).getCompanyName());
        assertEquals(Arrays.asList("5fc8829e3bf5ed6bcd35a295", "5fc882b23bf5ed6bcd35a296"), companies.get(0).getEmployeeIds());
    }

    @Test
    public void should_thorw_COMPANY_ID_DOES_NOT_EXIST_exception_when_update_given_invalid_company_id() throws Exception {
        //given
        Employee employee1 = new Employee("David", 18, "male", 10000);
        Employee employee2 = new Employee("Jackie", 18, "female", 10000);
        employee1.setId("5fc8829e3bf5ed6bcd35a295");
        employee2.setId("5fc882b23bf5ed6bcd35a296");
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        Company company = new Company("alibaba", Arrays.asList(employee1.getId(),employee2.getId()));
        companyRepository.save(company);
        String companyUpdateAsJson = "{\n" +
                "    \"companyName\": \"NEW\",\n" +
                "    \"employeeIds\": [\"5fc8829e3bf5ed6bcd35a295\",\"5fc882b23bf5ed6bcd35a296\"]\n" +
                "}";
        //when
        //then
        mockMvc.perform(put("/companies/" + "5fc8913234ba53396c26a863")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyUpdateAsJson))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CompanyNotFoundException))
                .andExpect(result -> assertEquals("Company Id does not exist", result.getResolvedException().getMessage()));

    }

    @Test
    public void should_update_company_when_update_given_company_id_and_companyUpdate_info() throws Exception {
        //given
        Employee employee1 = new Employee("David", 18, "male", 10000);
        Employee employee2 = new Employee("Jackie", 18, "female", 10000);
        employee1.setId("5fc8829e3bf5ed6bcd35a295");
        employee2.setId("5fc882b23bf5ed6bcd35a296");
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        Company company = new Company("alibaba", Arrays.asList(employee1.getId(),employee2.getId()));
        companyRepository.save(company);
        String companyUpdateAsJson = "{\n" +
                "    \"companyName\": \"NEW\",\n" +
                "    \"employeeIds\": [\"5fc8829e3bf5ed6bcd35a295\",\"5fc882b23bf5ed6bcd35a296\"]\n" +
                "}";

        //when
        //then
        mockMvc.perform(put("/companies/" + company.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyUpdateAsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.companyName").value("NEW"))
                .andExpect(jsonPath("$.employeesNumber").value(2))
                .andExpect(jsonPath("$.employees", hasSize(2)));

        List<Company> companies = companyRepository.findAll();
        assertEquals(1, companies.size());
        assertEquals("NEW", companies.get(0).getCompanyName());
        assertEquals(Arrays.asList("5fc8829e3bf5ed6bcd35a295", "5fc882b23bf5ed6bcd35a296"), companies.get(0).getEmployeeIds());
    }

    @Test
    public void should_delete_specific_company_when_delete_given_valid_company_id() throws Exception {
        //given
        Employee employee1 = new Employee("David", 18, "male", 10000);
        Employee employee2 = new Employee("Jackie", 18, "female", 10000);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employee1.setId("5fc8829e3bf5ed6bcd35a295");
        employee2.setId("5fc882b23bf5ed6bcd35a296");
        Company company = new Company("alibaba", Arrays.asList("5fc8829e3bf5ed6bcd35a295", "5fc882b23bf5ed6bcd35a296"));
        companyRepository.save(company);
        String companyId = company.getId();
        //when
        //then
        mockMvc.perform(delete("/companies/" + companyId))
                .andExpect(status().isOk());

        //TODO: change checking logic
        assertThat(companyRepository.findById(companyId)).isEmpty();
    }

    @Test
    public void should_thorw_COMPANY_ID_DOES_NOT_EXIST_exception_when_delete_given_invalid_company_id() throws Exception {
        //given
        Employee employee1 = new Employee("David", 18, "male", 10000);
        Employee employee2 = new Employee("Jackie", 18, "female", 10000);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employee1.setId("5fc8829e3bf5ed6bcd35a295");
        employee2.setId("5fc882b23bf5ed6bcd35a296");
        Company company = new Company("alibaba",  Arrays.asList("5fc8829e3bf5ed6bcd35a295", "5fc882b23bf5ed6bcd35a296"));
        companyRepository.save(company);
        //when
        //then
        mockMvc.perform(delete("/companies/" + "5fc8913234ba53396c26a863"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CompanyNotFoundException))
                .andExpect(result -> assertEquals("Company Id does not exist", result.getResolvedException().getMessage()));

    }
}
