package com.tms.stankevich.domain.user;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Role implements GrantedAuthority {
  private static final long serialVersionUID = 42L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "role_id")
  private long id;

  @Column(nullable = false)
  private String name;

  public Role() {
  }

  public Role(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Role(String name) {
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Role role = (Role) o;
    return id == role.id &&
            Objects.equals(name, role.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String getAuthority() {
    return getName();
  }
}
