package com.employee.employee;

import javax.persistence.*;

import com.employee.projects.Project;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int employeeid;

    @Column(nullable = false)
    public String fullname;

    @Column(nullable = false)
    public String department;

    @Column(nullable = false)
    public String designation;

    @Column(nullable = false)
    public String gender;

    @Column(nullable = false, unique = true)
    public String email;

    @Column(nullable = false)
    public String mobile_number;

    @ManyToOne
    @JoinColumn(name = "projectid") // Correct foreign key relationship
    public Project project;

    public int getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
