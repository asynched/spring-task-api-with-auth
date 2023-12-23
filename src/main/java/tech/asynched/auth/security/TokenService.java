package tech.asynched.auth.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import tech.asynched.auth.entities.UserEntity;

@Service()
public class TokenService {
  @Value("${api.security.token.secret}")
  private String SECRET;

  public String generateToken(UserEntity user) {
    try {
      var algorithm = Algorithm.HMAC256(SECRET);
      var date = new Date();
      var exp = new Date(date.getTime() + 86400000);

      var token = JWT.create()
          .withIssuer("tech.asynched.auth")
          .withSubject(user.getEmail())
          .withIssuedAt(date)
          .withExpiresAt(exp)
          .sign(algorithm);

      return token;
    } catch (JWTCreationException e) {
      throw new RuntimeException("Error generating token", e);
    }
  }

  public String validate(String token) {
    try {
      var algorithm = Algorithm.HMAC256(SECRET);
      var verifier = JWT.require(algorithm)
          .withIssuer("tech.asynched.auth")
          .build();

      var decoded = verifier.verify(token);

      return decoded.getSubject();
    } catch (Exception e) {
      return null;
    }
  }
}
