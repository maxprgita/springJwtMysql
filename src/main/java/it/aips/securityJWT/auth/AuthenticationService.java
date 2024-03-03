package it.aips.securityJWT.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.aips.securityJWT.config.JwtService;
import it.aips.securityJWT.entity.User;
import it.aips.securityJWT.entity.UserRepo;
import it.aips.securityJWT.security.Role;

@Service
public class AuthenticationService {
  private final UserRepo repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationService(UserRepo repository, PasswordEncoder passwordEncoder,
		  JwtService jwtService, AuthenticationManager authenticationManager) {
	  this.repository=repository;
	  this.passwordEncoder=passwordEncoder;
	  this.jwtService=jwtService;
	  this.authenticationManager=authenticationManager;
  }
  
  public AuthenticationResponse register(RegisterRequest request) {
      var user = User.builder()
              .firstname(request.getFirstname())
              .lastname(request.getLastname())
              .email(request.getEmail())
              .password(passwordEncoder.encode(request.getPassword()))
              .role(Role.User)
              .build();
      repository.save(user);
      var jwtToken = jwtService.generateToken(user);
      return AuthenticationResponse.builder()
              .token(jwtToken)
              .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      request.getEmail(),
                      request.getPassword()
              )
      );
      var user = repository.findByEmail(request.getEmail())
              .orElseThrow();
      var jwtToken = jwtService.generateToken(user);
      return AuthenticationResponse.builder()
              .token(jwtToken)
              .build();
  }
}

