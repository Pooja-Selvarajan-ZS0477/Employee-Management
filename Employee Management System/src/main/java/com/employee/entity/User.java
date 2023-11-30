package com.employee.entity;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_details")
public class User {
    @Id
    @Column(name="User_id")
    int userId;
    @Column(name="username")
    String username;
    @Column(name="password")
    String password;
    @Column(name="mobile_number")
    int mobileNumber;
    @Column(name="roles")
    String roles;

    public User(){

    }

    public User(int userId, String username, String password, int mobileNumber, String roles) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.roles = roles;
    }

    public User(String username, String password, int mobileNumber, String roles) {
        this.username = username;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.roles = roles;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(int mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
