package com.lprevidente.ddd_example.user.domain;

import java.util.regex.Pattern;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.util.Assert;

public record Password(String hashedValue) {

  // Compact constructor for validation
  public Password {
    Assert.hasText(hashedValue, "Hashed password cannot be empty");
  }

  // Factory method for creating new passwords
  public static Password create(String plainTextPassword) {
    validatePasswordStrength(plainTextPassword);
    String hashedPassword = hashPassword(plainTextPassword);
    return new Password(hashedPassword);
  }

  /* Factory method for reconstituting from persistence */
  public static Password fromHashed(String hashedPassword) {
    return new Password(hashedPassword);
  }

  private static void validatePasswordStrength(String password) {
    Assert.hasText(password, "Password cannot be empty");
    Assert.isTrue(password.length() >= 8, "Password must be at least 8 characters long");

    // Check for complexity requirements
    boolean hasUppercase = Pattern.compile("[A-Z]").matcher(password).find();
    boolean hasLowercase = Pattern.compile("[a-z]").matcher(password).find();
    boolean hasDigit = Pattern.compile("\\d").matcher(password).find();
    boolean hasSpecialChar = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]").matcher(password).find();

    Assert.isTrue(
        hasUppercase && hasLowercase && hasDigit && hasSpecialChar,
        "Password must contain at least one uppercase letter, one lowercase letter, "
            + "one digit, and one special character");
  }

  private static String hashPassword(String plainTextPassword) {
    return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(12));
  }

  // Verify if a plain text password matches this password
  public boolean matches(String plainTextPassword) {
    Assert.hasText(plainTextPassword, "Plain text password cannot be empty");
    return BCrypt.checkpw(plainTextPassword, this.hashedValue());
  }

  // Override toString to avoid exposing hash in logs
  @Override
  public String toString() {
    return "Password[hashedValue=*****]";
  }
}
