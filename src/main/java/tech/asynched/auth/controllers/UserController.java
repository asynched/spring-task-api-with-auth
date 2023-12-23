package tech.asynched.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tech.asynched.auth.dto.SignInDto;
import tech.asynched.auth.dto.SignUpDto;
import tech.asynched.auth.dto.TokenResponseDto;
import tech.asynched.auth.entities.UserEntity;
import tech.asynched.auth.services.UserService;

@RestController()
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  @Autowired()
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/sign-up")
  public UserEntity signUp(
      @RequestBody() @Valid() SignUpDto data) {
    return this.userService.signUp(data);
  }

  @PostMapping("/sign-in")
  public TokenResponseDto signIn(
      @RequestBody() @Valid() SignInDto data) {
    return this.userService.signIn(data.email(), data.password());
  }

  @GetMapping("/profile")
  public UserEntity getProfile(@AuthenticationPrincipal UserEntity user) {
    return user;
  }
}
