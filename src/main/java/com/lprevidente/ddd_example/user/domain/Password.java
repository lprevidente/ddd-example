package com.lprevidente.ddd_example.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.regex.Pattern;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.util.Assert;

/**
 * Value Object imutável que encapsula senhas B-Crypt.
 *
 * <p>▪ Use {@link #create(String)} para gerar nova senha.<br>
 * ▪ Use {@link #fromHashed(String)} para reconstruir senha já persistida.</p>
 */
@Embeddable                                // JPA reconhece como objeto incorporável
public final class Password {

  /* ---------- Configuração da política ---------- */
  private static final int MIN_LENGTH = 8;
  private static final Pattern UPPER_PATTERN   = Pattern.compile("[A-Z]");
  private static final Pattern LOWER_PATTERN   = Pattern.compile("[a-z]");
  private static final Pattern DIGIT_PATTERN   = Pattern.compile("\\d");
  private static final Pattern SPECIAL_PATTERN =
      Pattern.compile("[!@#$%^&*(),.?\":{}|<>]");

  /* ---------- Estado ---------- */
  @Column(name = "hash", nullable = false, length = 255)
  private String hash;                       // nunca exposto publicamente

  /** Construtor exigido pelo JPA (proteção default). */
  protected Password() {}

  private Password(String hash) {
    Assert.hasText(hash, "Hashed password cannot be empty");
    this.hash = hash;
  }

  /* ---------- Fábricas ---------- */
  public static Password create(String plainText) {
    validateStrength(plainText);
    return new Password(hash(plainText));
  }

  public static Password fromHashed(String existingHash) {
    return new Password(existingHash);
  }

  /* ---------- Domínio ---------- */
  public boolean matches(String plainText) {
    Assert.hasText(plainText, "Plain text password cannot be empty");
    return BCrypt.checkpw(plainText, hash);
  }

  /* ---------- Utilitário ---------- */
  @Override public String toString() { return "Password[hash=*****]"; }

  /* package */ String value() { return hash; }   // acesso restrito a persistência

  /* ---------- Internos ---------- */
  private static void validateStrength(String pwd) {
    Assert.hasText(pwd, "Password cannot be empty");
    Assert.isTrue(pwd.length() >= MIN_LENGTH,
        "Password must be at least %d characters long".formatted(MIN_LENGTH));

    Assert.isTrue(
        UPPER_PATTERN.matcher(pwd).find() &&
        LOWER_PATTERN.matcher(pwd).find() &&
        DIGIT_PATTERN.matcher(pwd).find() &&
        SPECIAL_PATTERN.matcher(pwd).find(),
        "Password must contain uppercase, lowercase, digit and special character");
  }

  private static String hash(String plainText) {
    return BCrypt.hashpw(plainText, BCrypt.gensalt(12));
  }
}
