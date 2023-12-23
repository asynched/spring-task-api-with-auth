package tech.asynched.auth.entities;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data()
@Entity()
@Table(name = "users")
public class UserEntity implements UserDetails {
  @Id()
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  @JsonIgnore()
  private String password;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private UserRole role = UserRole.USER;

  @Column(name = "created_at", nullable = false)
  @CreationTimestamp()
  private Date createdAt;

  @Column(name = "updated_at", nullable = false)
  @UpdateTimestamp()
  private Date updatedAt;

  @Override
  @JsonIgnore()
  public Collection<UserRole> getAuthorities() {
    return List.of(this.role);
  }

  @Override
  @JsonIgnore()
  public String getUsername() {
    return this.email;
  }

  @Override
  @JsonIgnore()
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore()
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  @JsonIgnore()
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore()
  public boolean isEnabled() {
    return true;
  }
}
