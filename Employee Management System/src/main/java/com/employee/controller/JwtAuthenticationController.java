package com.employee.controller;

import com.employee.DTO.EmailDto;
import com.employee.config.JwtTokenUtil;
import com.employee.entity.JwtRequest;
import com.employee.entity.JwtResponse;
import com.employee.entity.User;
import com.employee.user.UserRepository;
import com.employee.user.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	UserRepository userRepository;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	public BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private UserService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		try {
			authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

			final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
			final String token = jwtTokenUtil.generateToken(userDetails);
			User user = userRepository.findByUsername(authenticationRequest.getUsername());
//			if (!user.isActive()) {
//				return new ResponseEntity<String>("Please verify your account", HttpStatus.UNAUTHORIZED);
//			} else {
//
//			}
			return ResponseEntity.ok(new JwtResponse(token));
		} catch (UsernameNotFoundException e) {
			try {
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
			} catch (BadCredentialsException ex) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body("Invalid username or password");
			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Invalid username");
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Invalid password");
		} catch (DisabledException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("User is disabled");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred");
		}
	}

	@PostMapping("/register")
	public String saveUser(@RequestBody User user) throws Exception {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDetailsService.insertUser(user);
		return "User registered successfully";
	}

	private void authenticate(String username, String password) {
		UserDetails userDetails;
		try {
			userDetails = userDetailsService.loadUserByUsername(username);
		} catch (UsernameNotFoundException e) {
			throw new UsernameNotFoundException("Invalid username", e);
		}

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new DisabledException("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("INVALID_CREDENTIALS", e);
		}
	}

	@PostMapping("sendEmail")
	public String sendEmail(@ModelAttribute EmailDto email) throws MessagingException, IOException {
       /* SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(email.getTo());
        simpleMailMessage.setSubject(email.getSubject());
        simpleMailMessage.setText(email.getText());*/
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setTo(email.getTo());
		mimeMessageHelper.setSubject(email.getSubject());
		mimeMessageHelper.setText(email.getText(), true);
		mimeMessageHelper.addAttachment(email.getFile().getOriginalFilename(), convertMultipartToFile(email.getFile(), email.getFile().getOriginalFilename()));
		javaMailSender.send(mimeMessage);
		return "Email sent successfully";
	}

	private static File convertMultipartToFile(MultipartFile multipartFile, String fileName) throws IOException {
		File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
		multipartFile.transferTo(convFile);
		return convFile;
	}

	@PutMapping("/verify-account")
	public ResponseEntity<String> verifyAccount(@RequestParam String email, @RequestParam String otp) {
		return new ResponseEntity<>(userDetailsService.verifyAccount(email, otp), HttpStatus.OK);
	}

	@PutMapping("/regenerate-otp")
	public ResponseEntity<String> regenerateOtp(@RequestParam String email) throws MessagingException {
		return new ResponseEntity<>(userDetailsService.regenerateOtp(email), HttpStatus.OK);
	}

	@PutMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestParam String email) throws MessagingException {
		return new ResponseEntity<String>(userDetailsService.forgotPassword(email),HttpStatus.OK);
	}

	@PutMapping("/set-password")
	public ResponseEntity<String> setPassword(@RequestParam String email,@RequestParam String otp,@RequestHeader String newPassword){
		return new ResponseEntity<String>(userDetailsService.setPassword(email,otp,passwordEncoder.encode(newPassword)),HttpStatus.OK);
	}

}