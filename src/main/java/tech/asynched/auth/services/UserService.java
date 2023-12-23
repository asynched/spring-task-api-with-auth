package tech.asynched.auth.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tech.asynched.auth.dto.SignUpDto;
import tech.asynched.auth.dto.TokenResponseDto;
import tech.asynched.auth.entities.UserEntity;
import tech.asynched.auth.repositories.UserRepository;
import tech.asynched.auth.security.TokenService;

@Slf4j()
@Service()
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenService tokenService;
  private final AuthenticationManager authenticationManager;

  @Autowired()
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager, TokenService tokenService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }

  public UserEntity signUp(SignUpDto data) {
    var user = new UserEntity();

    BeanUtils.copyProperties(data, user);
    user.setPassword(passwordEncoder.encode(data.password()));

    return userRepository.save(user);
  }

  public TokenResponseDto signIn(String email, String password) {
    var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

    log.info("Generating token for user '{}'", email);
    var token = tokenService.generateToken((UserEntity) authentication.getPrincipal());

    return new TokenResponseDto(token);
  }

}
