package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FoodOrderingController handles web requests related to the home and menu pages of the food ordering application.
 */
@Controller
public class FoodOrderingController {

  private static final Logger logger = LoggerFactory.getLogger(FoodOrderingController.class);

  /**
   * Maps the root URL ("/") to the home page.
   *
   * @return the name of the view to render for the home page
   */
  @GetMapping("/")
  public String home() {
    logger.info("Accessing home page");
    return "home";
  }

  /**
   * Maps the "/menu" URL to the menu page and sets the authenticated user's username in the model.
   *
   * @param user  the authenticated OIDC (OpenID Connect) user
   * @param model Model object for passing data to the view
   * @return the name of the view to render for the menu page, or redirects to home if user is not authenticated
   */
  @GetMapping("/menu")
  public String menu(@AuthenticationPrincipal OidcUser user, Model model) {
    logger.info("Accessing menu page");

    if (user != null) {
      String username = user.getPreferredUsername();
      logger.info("User authenticated: {}", username);
      model.addAttribute("username", username);

      // Ajout d'informations supplémentaires pour le debugging
      model.addAttribute("userInfo", user.getClaims());

      return "menu";
    } else {
      logger.warn("User not authenticated, redirecting to home");
      return "redirect:/";
    }
  }

  /**
   * Endpoint pour tester l'authentification
   */
  @GetMapping("/profile")
  public String profile(@AuthenticationPrincipal OidcUser user, Model model) {
    logger.info("Accessing profile page");

    if (user != null) {
      logger.info("User claims: {}", user.getClaims());
      model.addAttribute("user", user);
      model.addAttribute("claims", user.getClaims());
      return "profile";
    } else {
      return "redirect:/";
    }
  }
}