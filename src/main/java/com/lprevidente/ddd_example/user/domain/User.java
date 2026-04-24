package com.lprevidente.ddd_example.user.domain;

import com.lprevidente.ddd_example.user.domain.exception.EmailAlreadyInUseException;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Getter;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;
import org.springframework.util.Assert;
import com.lprevidente.ddd_example.user.api.UserId;

@Getter
@AggregateRoot
@Table(name = "users")
public class User {

  @Identity private UserId id;

  private String firstName;
  private String lastName;

  @AttributeOverride(name = "hashedValue", column = @Column(name = "password"))
  private Password password;

  @AttributeOverride(name = "value", column = @Column(name = "email", unique = true))
  private Email email;

  protected User() {}

  public User(String firstName, String lastName, Password password, Email email, Users users) {
    Assert.notNull(firstName, "firstName must not be null");
    Assert.notNull(lastName, "lastName must not be null");
    Assert.notNull(email, "email must not be null");
    Assert.notNull(password, "password must not be null");
    if (users.existsByEmail(email)) throw new EmailAlreadyInUseException(email);

    this.id = new UserId();
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.email = email;
  }

  public void updateDetails(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof User user)) return false;
    return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
