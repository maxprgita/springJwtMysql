package it.aips.securityJWT.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/prova")
public class ProvaController {
  @GetMapping
  public ResponseEntity<?> prova1() {
      return ResponseEntity.ok("Sono il controller prova");
  }
}

