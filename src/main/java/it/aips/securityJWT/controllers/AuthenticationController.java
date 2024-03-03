package it.aips.securityJWT.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.aips.securityJWT.auth.AuthenticationRequest;
import it.aips.securityJWT.auth.AuthenticationResponse;
import it.aips.securityJWT.auth.AuthenticationService;
import it.aips.securityJWT.auth.RegisterRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

  private final AuthenticationService service;
  
  public AuthenticationController(AuthenticationService service) {
	  this.service=service;
  }

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
          @RequestBody RegisterRequest request
  ) {
      return ResponseEntity.ok(service.register(request));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
          @RequestBody AuthenticationRequest request
  ) {
      return ResponseEntity.ok(service.authenticate(request));
  }
}
