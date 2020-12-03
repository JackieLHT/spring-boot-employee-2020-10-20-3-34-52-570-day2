package com.thoughtworks.springbootemployee.Intergration;

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

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntergrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @AfterEach
    void tearDown() {
        companyRepository.deleteAll();
    }

    @Test
    public void should_return_all_companies_when_get_all_given_all_companies() throws Exception {
        //given
        final List<String> employeeIds = Arrays.asList("1", "2");
        Company company = new Company("alibaba", employeeIds);
        companyRepository.save(company);
        //when
        //then
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].companyName").value("alibaba"))
                .andExpect(jsonPath("$[0].employeesNumber").value(2))
                .andExpect(jsonPath("$[0].employeesId[0]").value(1))
                .andExpect(jsonPath("$[0].employeesId[1]").value(2));

    }

    @Test
    public void should_return_specific_company_when_get_by_id_given_valid_company_id() throws Exception {
        //given
        final List<String> employeeIds = Arrays.asList("1", "2");
        Company company = new Company("alibaba", employeeIds);
        companyRepository.save(company);
        //when
        //then
        mockMvc.perform(get("/companies/"+company.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.companyName").value("alibaba"))
                .andExpect(jsonPath("$.employeesNumber").value(2))
                .andExpect(jsonPath("$.employeesId", hasSize(2)));

    }

    @Test
    public void should_return_all_employees_of_specific_company_when_getEmployeesByCompanyId_given_companyId() throws Exception {
        //given
        Employee employee1 = new Employee("David", 18, "male", 10000);
        Employee employee2 = new Employee("Jackie", 18, "female", 10000);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        List<String> employees = new ArrayList<>();
        for(Employee employee:employeeRepository.findAll()){
            employees.add(employee.getId());
        }
        Company company = new Company("alibaba",employees);
        companyRepository.save(company);
        //when
        //then
        mockMvc.perform(get("/companies/"+company.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.companyName").value("alibaba"))
                .andExpect(jsonPath("$.employeesNumber").value(2))
                .andExpect(jsonPath("$.employeesId", hasSize(2)))
                .andExpect(jsonPath("$.employeesId[0]").value(employees.get(0)))
                .andExpect(jsonPath("$.employeesId[1]").value(employees.get(1)));
    }

    @Test
    public void should_return_2_companies_when_get_by_paging_given_3_companies_and_page_number_is_0_and_pagesize_is_2() throws Exception {
        //given
        Company company1 = new Company("KFC", Arrays.asList("1","2"));
        Company company2 = new Company("UNIQUO", Arrays.asList("4","5"));
        Company company3 = new Company("LOGON", Arrays.asList("6","7"));
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
    public void should_return_created_company_when_create_given_company() throws Exception {
        //given
        String companyAsJson = "{\n" +
                "    \"companyName\": \"alibaba\",\n" +
                "    \"employeesNumber\" : 2,\n" +
                "    \"employeesId\": [\"1\",\"2\"]\n" +
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
                .andExpect(jsonPath("$.employeesId[0]").value(1))
                .andExpect(jsonPath("$.employeesId[1]").value(2));

        List<Company> companies = companyRepository.findAll();
        assertEquals(1, companies.size());
        assertEquals("alibaba", companies.get(0).getCompanyName());
        assertEquals(2, companies.get(0).getEmployeesNumber());
        assertEquals(Arrays.asList("1","2"), companies.get(0).getEmployeesId());
    }

    @Test
    public void should_update_company_when_update_given_company_id_and_companyUpdate_info() throws Exception {
        //given
        final List<String> employeeIds = Arrays.asList("1", "2");
        Company company = new Company("alibaba", employeeIds);
        companyRepository.save(company);
        String companyUpdateAsJson = "{\n" +
                "    \"companyName\": \"NEW\",\n" +
                "    \"employeesNumber\" : 2,\n" +
                "    \"employeesId\": [\"4\",\"5\"]\n" +
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
                .andExpect(jsonPath("$.employeesId[0]").value(4))
                .andExpect(jsonPath("$.employeesId[1]").value(5));

        List<Company> employees = companyRepository.findAll();
        assertEquals(1, employees.size());
        assertEquals("NEW", employees.get(0).getCompanyName());
        assertEquals(2, employees.get(0).getEmployeesNumber());
        assertEquals(Arrays.asList("4","5"), employees.get(0).getEmployeesId());
    }

    @Test
    public void should_delete_specific_company_when_delete_given_valid_company_id() throws Exception {
        //given
        final List<String> employeeIds = Arrays.asList("1", "2");
        Company company = new Company("alibaba", employeeIds);
        companyRepository.save(company);

        //when
        //then
        mockMvc.perform(delete("/companies/" + company.getId()))
                .andExpect(status().isOk());

        List<Company> employees = companyRepository.findAll();
        assertEquals(0, employees.size());
    }
}
