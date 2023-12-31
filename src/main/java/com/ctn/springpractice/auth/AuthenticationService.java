package com.ctn.springpractice.auth;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ctn.springpractice.common.ApiResponse;
import com.ctn.springpractice.configs.JwtService;
import com.ctn.springpractice.user.Role;
import com.ctn.springpractice.user.User;
import com.ctn.springpractice.user.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthenticationService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AuthenticationResponse authResponse;

	public ApiResponse register(RegisterRequest request) {
		
		  var user = User.builder() .firstname(request.getFirstname())
		  .lastname(request.getLastname()) .email(request.getEmail())
		  .password(passwordEncoder.encode(request.getPassword())) .role(Role.USER) .build();
		 log.info(""+user);
		 repository.save(user);
		 
		var jwtToken = jwtService.generateToken(user);
		authResponse = 
				AuthenticationResponse
				.builder()
				.token(jwtToken).build();
		return ApiResponse.builder()
			.message("Token Geneareated")
			.responseCode(HttpStatus.OK)
			.responseBody(authResponse)
		.build();
		

	}
	
	public ApiResponse authenticate(AuthenticateRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()
						)
				);
		var user = repository.findByEmail(request.getEmail())
				.orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		authResponse = 
				AuthenticationResponse
				.builder()
				.token(jwtToken).build();
		return ApiResponse.builder()
				.message("Token Geneareated")
				.responseCode(HttpStatus.OK)
				.responseBody(authResponse)
				.build();
	}
}
