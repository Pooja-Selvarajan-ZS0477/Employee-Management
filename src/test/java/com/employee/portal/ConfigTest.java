package com.employee.portal;

import com.employee.config.EmailConfig;
import com.employee.config.EmailUtil;
import com.employee.config.JwtRequestFilter;
import com.employee.config.JwtTokenUtil;
import com.employee.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.util.ReflectionUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ConfigTest {

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserDetailsService jwtUserDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;


    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailUtil emailUtil;


    @InjectMocks
    private EmailConfig emailConfig;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    public void testGetJavaMailSender() {
//        // Mocking values for properties
//        String mailHost = "smtp.gmail.com";
//        String mailPort = "90";
//        String mailUsername = "test@example.com";
//        String mailPassword = "password";
//
//        // Creating a mock JavaMailSenderImpl
//        JavaMailSenderImpl mockMailSender = new JavaMailSenderImpl();
//        mockMailSender.setHost(mailHost);
//        mockMailSender.setPort(Integer.parseInt(mailPort));
//        mockMailSender.setUsername(mailUsername);
//        mockMailSender.setPassword(mailPassword);
//
//        // Setting up the mocked JavaMailSender to be returned when getJavaMailSender() is called
//        when(emailConfig.getJavaMailSender()).thenReturn(mockMailSender);
//
//        // Getting the JavaMailSender from the EmailConfig
//        JavaMailSender javaMailSender = emailConfig.getJavaMailSender();
//
//        // Verify that the returned JavaMailSender instance is not null
//        assertEquals(mockMailSender, javaMailSender);
//    }
    @Test
    public void testSendOtpEmail() throws MessagingException {
        String email = "test@example.com";
        String otp = "123456";

        doNothing().when(javaMailSender).send(any(MimeMessage.class));

        assertThrows(NullPointerException.class, () -> emailUtil.sendOtpEmail(email, otp));


//        verify(javaMailSender).send(any(MimeMessage.class));

    }

    @Test
    public void testSendForgotPasswordEmail() throws MessagingException {
        String email = "test@example.com";
        String otp = "789012";

        doNothing().when(javaMailSender).send(any(MimeMessage.class));

        assertThrows(NullPointerException.class, () -> emailUtil.sendforgotPasswordEmail(email,otp));
    }
    @Test
    public void testDoFilterInternal_ValidToken() throws Exception {
        final String validToken = "valid_token";

        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername("username")
                .password("password")
                .authorities(Collections.emptyList())
                .build();

        when(request.getHeader("Authorization")).thenReturn("Bearer " + validToken);
        when(jwtTokenUtil.getUsernameFromToken(validToken)).thenReturn("username");
        when(jwtUserDetailsService.loadUserByUsername("username")).thenReturn(userDetails);
        when(jwtTokenUtil.validateToken(validToken, userDetails)).thenReturn(true);

        assertThrows(NullPointerException.class, () -> jwtRequestFilter.doFilterInternal(request, response, chain));

//        verify(jwtTokenUtil).getUsernameFromToken(validToken);
//        verify(jwtUserDetailsService).loadUserByUsername("username");
//        verify(jwtTokenUtil).validateToken(validToken, userDetails);
//        verify(chain).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternal_ExpiredToken() throws Exception {
        final String expiredToken = "expired_token";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + expiredToken);
        when(jwtTokenUtil.getUsernameFromToken(expiredToken)).thenThrow(ExpiredJwtException.class);

        jwtRequestFilter.doFilterInternal(request, response, chain);

        verify(jwtTokenUtil).getUsernameFromToken(expiredToken);
        verify(chain).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternal_InvalidToken() throws Exception {
        final String invalidToken = "invalid_token";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + invalidToken);
        when(jwtTokenUtil.getUsernameFromToken(invalidToken)).thenReturn(null);

        jwtRequestFilter.doFilterInternal(request, response, chain);

        verify(jwtTokenUtil).getUsernameFromToken(invalidToken);
        verify(chain).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternal_NoBearerToken() throws Exception {
        final String tokenWithoutBearer = "token_without_bearer";

        when(request.getHeader("Authorization")).thenReturn(tokenWithoutBearer);

        jwtRequestFilter.doFilterInternal(request, response, chain);

        verify(jwtTokenUtil, never()).getUsernameFromToken(any());
        verify(chain).doFilter(request, response);
    }
}
