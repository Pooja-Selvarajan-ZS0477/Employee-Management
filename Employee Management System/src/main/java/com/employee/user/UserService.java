package com.employee.user;

import com.employee.config.EmailUtil;
import com.employee.config.OtpUtil;
import com.employee.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    public UserRepository userRepository;

    @Autowired
    private OtpUtil otpUtil;
    @Autowired
    private EmailUtil emailUtil;

    /**
     * @param username the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    new ArrayList<>());
        }
        throw new UsernameNotFoundException("User is not available");
    }

    public List<User> getAllUsers() {
        List<User> user = new ArrayList<>();
        userRepository.findAll().forEach(user::add);
        return user;
    }

    public void insertUser(User user) throws MessagingException {
        // Save the user details to the repository
        userRepository.save(user);

        // Generate OTP after saving user details
        String otp = otpUtil.generateOtp();

        // Set OTP and OTP generation time for the user
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());

        // Send OTP email
        emailUtil.sendOtpEmail(user.getUsername(), otp);

        // Update the user with the generated OTP and OTP generation time
        userRepository.save(user);
    }


    public void updateUser(User user) {
        userRepository.save(user);
    }

    public boolean deleteUser(int userId) {
        userRepository.deleteById(userId);
        return true;
    }

    public String verifyAccount(String email, String otp) {
        User user=userRepository.findByUsername(email);
        if(user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(),LocalDateTime.now()).getSeconds() < (3*60)){
            user.setActive(true);
            userRepository.save(user);
            return "OTP verified ";
        }
        return "Otp expired and try again";
    }

    public String regenerateOtp(String email) throws MessagingException {
        User user=userRepository.findByUsername(email);
        String otp=otpUtil.generateOtp();
        emailUtil.sendOtpEmail(email,otp);
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "Email sent .... please verify";

    }

    public String forgotPassword(String email) throws MessagingException {
        User user=userRepository.findByUsername(email);
        String otp=otpUtil.generateOtp();
        emailUtil.sendforgotPasswordEmail(email,otp);
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "Otp sent to Email! Please check to set new password";
    }

    public String setPassword(String email,String otp, String newPassword) {
        User user=userRepository.findByUsername(email);
        if(user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(),LocalDateTime.now()).getSeconds() < (3*60)){
            user.setPassword(newPassword);
            userRepository.save(user);
            return "New password set successfully! Please login with your new password";
        }else{
            return "Please send otp again";
        }
    }
}