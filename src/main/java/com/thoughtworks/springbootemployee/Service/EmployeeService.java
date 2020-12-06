package com.thoughtworks.springbootemployee.Service;

import com.thoughtworks.springbootemployee.Exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.Model.Employee;
import com.thoughtworks.springbootemployee.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public List<Employee> getByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
    }

    public Employee getById(String id) {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException());
    }

    public List<Employee> getPaginatedAll(Integer page, Integer pageSize) {
        return employeeRepository.findAll().stream().skip(page * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee update(String id, Employee employeeUpdate) {
        Employee employee = getById(id);
        employeeUpdate.setId(employee.getId());
        return employeeRepository.save(employeeUpdate);
    }

    public void delete(String id) {
        Employee employee = getById(id);
        employeeRepository.deleteById(employee.getId());
        return;

    }
}
