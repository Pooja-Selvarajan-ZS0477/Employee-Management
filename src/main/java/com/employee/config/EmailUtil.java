package com.employee.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailUtil {
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendOtpEmail(String email, String otp) throws MessagingException {
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("OTP Verification");
        mimeMessageHelper.setText("""
                <div>
                    <a href="http://localhost:8080/verify-account?email=%s&otp=%s" target="_blank">Click link to verify your account</a>
                </div>
                """.formatted(email,otp),true);
        javaMailSender.send(mimeMessage);
    }
    public void sendforgotPasswordEmail(String email,String otp) throws MessagingException {
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Set Password");
        mimeMessageHelper.setText("""
                <div>
                    <a href="http://localhost:8080/set-password?email=%s&otp=%s" target="_blank">Click link to set new password</a>
                </div>
                """.formatted(email,otp),true);
        javaMailSender.send(mimeMessage);
    }
}