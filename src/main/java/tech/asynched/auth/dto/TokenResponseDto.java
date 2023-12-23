package tech.asynched.auth.dto;

public record TokenResponseDto(
    String token,
    String type) {

  public TokenResponseDto(String token) {
    this(token, "Bearer");
  }
}
