package tech.asynched.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration()
@EnableWebSecurity()
public class SecurityConfiguration {
  @SuppressWarnings("unused")
  private final CustomUserDetailsService customUserDetailsService;

  private final JwtSecurityFilter securityFilter;

  @Autowired()
  public SecurityConfiguration(CustomUserDetailsService customUserDetailsService, JwtSecurityFilter securityFilter) {
    this.customUserDetailsService = customUserDetailsService;
    this.securityFilter = securityFilter;
  }

  @Bean()
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf((forgery) -> forgery.disable())
        .sessionManagement((management) -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize -> {
          authorize.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll();
          authorize.requestMatchers("/users/sign-*").permitAll();
          authorize.anyRequest().authenticated();
        })
        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean()
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean()
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
