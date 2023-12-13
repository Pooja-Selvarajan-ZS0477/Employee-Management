package com.employee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="user_details")
public class User {
    @Id
    @Column(name="User_id")
    int userId;
    @Column(name="name")
    String name;
    @Column(name="username")
    String username;
    @Column(name="password")
    String password;
    @Column(name="mobile_number")
    int mobileNumber;
    @Column(name="roles")
    String roles;
    @Column(name="active")
    boolean active;
    @Column(name="otp")
    String otp;
    @Column(name="otp_generated_time")
    LocalDateTime otpGeneratedTime;

    public User(){

    }
    public User(int userId, String name, String username, String password, int mobileNumber, String roles, boolean active, String otp, LocalDateTime otpGeneratedTime) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.roles = roles;
        this.active = active;
        this.otp = otp;
        this.otpGeneratedTime = otpGeneratedTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getOtpGeneratedTime() {
        return otpGeneratedTime;
    }

    public void setOtpGeneratedTime(LocalDateTime otpGeneratedTime) {
        this.otpGeneratedTime = otpGeneratedTime;
    }
}