package com.tms.stankevich.domain.user;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Authority implements Serializable {
  private static final long serialVersionUID = 42L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "authority_id")
  private long id;

  @Column(nullable = false)
  private String name;

  public Authority() {
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
    Authority role = (Authority) o;
    return id == role.id &&
            Objects.equals(name, role.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }
}
