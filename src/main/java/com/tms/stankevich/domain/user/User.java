package com.tms.stankevich.domain.user;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User implements Serializable {
  private static final long serialVersionUID = 42L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(name = "password")
  private String password;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(name = "user_authority",
          joinColumns = {@JoinColumn(name = "user_id")},
          inverseJoinColumns = {@JoinColumn(name = "authority_id")})
  private List<Authority> authorities;

  @ManyToMany
  @JoinTable(name = "user_friends",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "friend_id"))
  protected List<User> friends = null;

  @ManyToMany(mappedBy = "friends")
  protected List<User> befriended = null;

  public User() {
  }

  public long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Authority> getRoles() {
    return authorities;
  }

  public void setRoles(List<Authority> authorities) {
    this.authorities = authorities;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(email, user.email) &&
            Objects.equals(password, user.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, password);
  }
}
