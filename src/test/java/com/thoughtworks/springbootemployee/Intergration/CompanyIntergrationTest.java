package com.thoughtworks.springbootemployee.Intergration;

import com.thoughtworks.springbootemployee.Model.Company;
import com.thoughtworks.springbootemployee.Repository.CompanyRepository1;
import com.thoughtworks.springbootemployee.Repository.EmployeeRepository1;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntergrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private CompanyRepository1 companyRepository;
    @Autowired
    private EmployeeRepository1 employeeRepository1;

    @AfterEach
    void tearDown() {
        companyRepository.deleteAll();
    }

    @Test
    public void should_return_all_companies_when_get_all_given_all_companies() throws Exception {
        //given
        final List<String> employeeIds = Arrays.asList("1", "2");
        Company company = new Company("alibaba", 2, employeeIds);
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
        Company company = new Company("alibaba", 2, employeeIds);
        companyRepository.save(company);
        //when
        //then
        mockMvc.perform(get("/companies/"+company.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.companyName").value("alibaba"))
                .andExpect(jsonPath("$.employeesNumber").value(2))
                .andExpect(jsonPath("$.employeesId[0]").value(1))
                .andExpect(jsonPath("$.employeesId[1]").value(2));

    }
//
//    @Test
//    public void should_return_all_male_employees_when_get_by_gender_given_gender_is_male() throws Exception {
//        //given
//        Employee employee1 = new Employee("David", 18, "male", 10000);
//        Employee employee2 = new Employee("Jackie", 18, "female", 10000);
//        employeeRepository.save(employee1);
//        employeeRepository.save(employee2);
//        //when
//        //then
//        mockMvc.perform(get("/employees").param("gender", "male"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id").isString())
//                .andExpect(jsonPath("$[0].name").value("David"))
//                .andExpect(jsonPath("$[0].age").value(18))
//                .andExpect(jsonPath("$[0].gender").value("male"))
//                .andExpect(jsonPath("$[0].salary").value(10000));
//
//        List<Employee> filteredEmployees = employeeRepository.findAllByGender("male");
//        assertEquals(1, filteredEmployees.size());
//    }
//
//    @Test
//    public void should_return_2_employees_when_get_by_paging_given_3_employees_and_page_number_is_1_and_pagesize_is_2() throws Exception {
//        //given
//        Employee employee1 = new Employee("David", 18, "male", 10000);
//        Employee employee2 = new Employee("Jackie", 18, "female", 10000);
//        Employee employee3 = new Employee("Tom", 18, "male", 10000);
//        employeeRepository.save(employee1);
//        employeeRepository.save(employee2);
//        employeeRepository.save(employee3);
//        //when
//        //then
//        mockMvc.perform(get("/employees").param("page", String.valueOf(1)).param("pageSize", String.valueOf(2)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id").isString())
//                .andExpect(jsonPath("$[0].name").value("David"))
//                .andExpect(jsonPath("$[0].age").value(18))
//                .andExpect(jsonPath("$[0].gender").value("male"))
//                .andExpect(jsonPath("$[0].salary").value(10000));
//    }
//
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
        Company company = new Company("alibaba", 2, employeeIds);
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
        Company company = new Company("alibaba", 2, employeeIds);
        companyRepository.save(company);

        //when
        //then
        mockMvc.perform(delete("/companies/" + company.getId()))
                .andExpect(status().isOk());

        List<Company> employees = companyRepository.findAll();
        assertEquals(0, employees.size());
    }
}
