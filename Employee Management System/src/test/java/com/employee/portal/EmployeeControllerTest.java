package com.employee.portal;

import com.employee.controller.EmployeeController;
import com.employee.employee.Employee;
import com.employee.employee.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Employee Controller Test")
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Test Get All Employees")
    public void testGetAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee());
        employees.add(new Employee());

        when(employeeService.getAllEmployees()).thenReturn(employees);

        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employees, response.getBody());
    }

    @Test
    @DisplayName("Test Get Employee By Id - Employee Found")
    public void testGetEmployeeById_EmployeeFound() {
        Employee employee = new Employee();
        int employeeId = 1;

        when(employeeService.getEmployeeById(employeeId)).thenReturn(Optional.of(employee));

        ResponseEntity<Employee> response = employeeController.getEmployeeById(employeeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee, response.getBody());
    }

    @Test
    @DisplayName("Test Get Employee By Id - Employee Not Found")
    public void testGetEmployeeById_EmployeeNotFound() {
        int employeeId = 1;

        when(employeeService.getEmployeeById(employeeId)).thenReturn(Optional.empty());

        ResponseEntity<Employee> response = employeeController.getEmployeeById(employeeId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    @DisplayName("Test Create Employee")
    public void testCreateEmployee() {
        Employee newEmployee = new Employee();
        newEmployee.setFullname("John Doe");

        when(employeeService.createEmployee(newEmployee)).thenReturn(newEmployee);

        ResponseEntity<Employee> response = employeeController.createEmployee(newEmployee);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newEmployee, response.getBody());
    }

    @Test
    @DisplayName("Test Update Employee")
    public void testUpdateEmployee() {
        int employeeId = 1;
        Employee updatedEmployee = new Employee();

        when(employeeService.updateEmployee(employeeId, updatedEmployee)).thenReturn(updatedEmployee);

        ResponseEntity<Employee> response = employeeController.updateEmployee(employeeId, updatedEmployee);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedEmployee, response.getBody());
    }

    @Test
    @DisplayName("Test Delete Employee")
    public void testDeleteEmployee() {
        int employeeId = 1;

        ResponseEntity<Void> response = employeeController.deleteEmployee(employeeId);

        verify(employeeService, times(1)).deleteEmployee(employeeId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
}
