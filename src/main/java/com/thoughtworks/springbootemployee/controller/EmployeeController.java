package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.DTO.EmployeeRequest;
import com.thoughtworks.springbootemployee.DTO.EmployeeResponse;
import com.thoughtworks.springbootemployee.Mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.Model.Employee;
import com.thoughtworks.springbootemployee.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private List<Employee> employees = new ArrayList<>();

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping(params = "gender")
    public List<EmployeeResponse> getByGender(@RequestParam String gender) {
        return employeeService.getByGender(gender).stream().map(employeeMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<EmployeeResponse> getPaginatedAll(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize) {
        return employeeService.getPaginatedAll(page, pageSize).stream().map(employeeMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping
    public List<EmployeeResponse> getAll() {
        return employeeService.getAll().stream().map(employeeMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/{employeeId}")
    public EmployeeResponse getById(@PathVariable String employeeId) {
        Employee employee = employeeService.getById(employeeId);
        return employeeMapper.toResponse(employee);
    }

    @PostMapping
    public EmployeeResponse create(@RequestBody EmployeeRequest employee) {
        Employee newEmployee = employeeService.save(employeeMapper.toEntity(employee));
        return employeeMapper.toResponse(newEmployee);
    }

    @PutMapping("/{employeeId}")
    public EmployeeResponse update(@PathVariable String employeeId, @RequestBody EmployeeRequest employeeUpdate) {
        Employee employee = employeeService.update(employeeId, employeeMapper.toEntity(employeeUpdate));
        return employeeMapper.toResponse(employee);
    }

    @DeleteMapping("/{employeeId}")
    public void delete(@PathVariable String employeeId) {
        employeeService.delete(employeeId);
    }
}

