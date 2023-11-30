package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.employee.config.JwtTokenUtil;
import com.employee.employee.Employee;
import com.employee.employee.EmployeeService;
import com.employee.entity.JwtRequest;
import com.employee.entity.JwtResponse;
import com.employee.entity.User;
import com.employee.projects.Project;
import com.employee.projects.ProjectService;
import com.employee.user.UserService;

import java.util.List;
import java.util.Optional;

@RestController

public class Controller {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ProjectService projectService;
    @Autowired
	private AuthenticationManager authenticationManager;

	

    @GetMapping("/employee")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/employee/create")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @PutMapping("/employee/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody Employee updatedEmployee) {
        Employee employee = employeeService.updateEmployee(id, updatedEmployee);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/employee/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
    
   
    
        

        @GetMapping("/project")
        public List<Project> getAllProjects() {
            return projectService.getAllProjects();
        }

        @GetMapping("project/{id}")
        public ResponseEntity<Project> getProjectById(@PathVariable int id) {
            Optional<Project> project = projectService.getProjectById(id);
            return project.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }

        @PostMapping("/project/create")
        public Project createProject(@RequestBody Project project) {
            return projectService.createProject(project);
        }

        @PutMapping("/project/update/{id}")
        public ResponseEntity<Project> updateProject(@PathVariable int id, @RequestBody Project updatedProject) {
            Project project = projectService.updateProject(id, updatedProject);
            return ResponseEntity.ok(project);
        }

        @DeleteMapping("/project/delete/{id}")
        public ResponseEntity<Void> deleteProject(@PathVariable int id) {
            projectService.deleteProject(id);
            return ResponseEntity.noContent().build();
        }
    }

