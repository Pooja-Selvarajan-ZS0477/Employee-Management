package com.employee.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(int id) {
        return employeeRepository.findById(id);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(int id, Employee updatedEmployee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee existingEmployee = optionalEmployee.get();
            existingEmployee.setFullname(updatedEmployee.getFullname());
            existingEmployee.setDepartment(updatedEmployee.getDepartment());
            existingEmployee.setDesignation(updatedEmployee.getDesignation());
            existingEmployee.setGender(updatedEmployee.getGender());
            existingEmployee.setEmail(updatedEmployee.getEmail());
            existingEmployee.setMobile_number(updatedEmployee.getMobile_number());
            existingEmployee.setProject(updatedEmployee.getProject());
            return employeeRepository.save(existingEmployee);
        } else {
            throw new RuntimeException("Employee not found with id: " + id);
        }
    }

    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }

	
}
