package com.lprevidente.ddd_example.user.domain;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)      // exigido pelo JPA
public class User {

  /* ---------- Identidade ---------- */
  @EmbeddedId
  private UserId id = new UserId();                     // UUID gerado no ato

/* ---------- Atributos ---------- */

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  /* Password: já contém @Column(name = "hash") dentro do próprio objeto */
  @Embedded
  private Password password;

  /* Email: precisa apontar para a coluna "email" */
  @Embedded
  @AttributeOverride(
      name   = "value",
      column = @Column(name = "email", nullable = false, unique = true)
  )
  private Email email;


  /* ---------- Construtor privado ---------- */
  private User(String firstName, String lastName,
               Password password, Email email) {

    setNames(firstName, lastName);

    Assert.notNull(password, "password must not be null");
    Assert.notNull(email,    "email must not be null");

    this.password = password;
    this.email    = email;
  }

  /* ---------- Fábrica ---------- */
  public static User create(String firstName, String lastName,
                            Password password, Email email) {
    return new User(firstName, lastName, password, email);
  }

  /* ---------- Regras ---------- */
  public void updateDetails(String firstName, String lastName) {
    setNames(firstName, lastName);
  }

  /* ---------- Igualdade ---------- */
  @Override public boolean equals(Object o) {
    return o instanceof User that && Objects.equals(id, that.id);
  }
  @Override public int hashCode() { return Objects.hash(id); }

  /* ---------- Interno ---------- */
  private void setNames(String firstName, String lastName) {
    Assert.hasText(firstName, "firstName must not be blank");
    Assert.hasText(lastName,  "lastName must not be blank");
    this.firstName = firstName.trim();
    this.lastName  = lastName.trim();
  }
}
