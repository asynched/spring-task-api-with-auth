package tech.asynched.auth.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tech.asynched.auth.repositories.UserRepository;

@Component()
public class JwtSecurityFilter extends OncePerRequestFilter {
  private final TokenService tokenService;
  private final UserRepository userRepository;

  public JwtSecurityFilter(TokenService tokenService, UserRepository userRepository) {
    this.tokenService = tokenService;
    this.userRepository = userRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    var token = this.getToken(request);

    if (token == null) {
      filterChain.doFilter(request, response);
      return;
    }

    var email = this.tokenService.validate(token);

    if (email == null) {
      filterChain.doFilter(request, response);
      return;
    }

    var result = this.userRepository.findByEmail(email);

    if (result.isEmpty()) {
      filterChain.doFilter(request, response);
      return;
    }

    var user = result.get();

    var authentication = new UsernamePasswordAuthenticationToken(
        user,
        null,
        user.getAuthorities());

    SecurityContextHolder.getContext().setAuthentication(authentication);

    filterChain.doFilter(request, response);
  }

  private String getToken(HttpServletRequest request) {
    var header = request.getHeader("Authorization");

    if (header == null || !header.startsWith("Bearer ")) {
      return null;
    }

    return header.substring(7);
  }

}
