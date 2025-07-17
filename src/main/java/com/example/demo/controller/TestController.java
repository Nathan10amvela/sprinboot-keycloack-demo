package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class TestController {

  @GetMapping("/public")
  public String publicEndpoint() {
    return "Accessible sans authentification";
  }

  @GetMapping("/private")
  public String privateEndpoint(Principal principal) {
    return "Bienvenue " + principal.getName() + " ! Ceci est un endpoint sécurisé.";
  }
}