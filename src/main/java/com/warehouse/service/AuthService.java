package com.warehouse.service;

import com.warehouse.model.User;
import com.warehouse.repository.UserRepository;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

public class AuthService {

    public static final String DEFAULT_ADMIN_USERNAME = "admin";
    public static final String DEFAULT_ADMIN_PASSWORD = "admin123"; // force a change later

    private static final int SALT_BYTES = 16;
    private static final int ITERATIONS = 210_000;
    private static final int KEY_BITS = 256;
    private static final SecureRandom RANDOM = new SecureRandom();

    private final UserRepository users;

    public AuthService(UserRepository users) {
        this.users = users;
    }

    /** Call once at startup, after Database.init(). */
    public void ensureDefaultAdmin() {
        if (users.count() > 0) return;
        String salt = newSalt();
        users.save(new User(
                UUID.randomUUID().toString(),
                DEFAULT_ADMIN_USERNAME,
                "Administrator",
                "ADMIN",
                hash(DEFAULT_ADMIN_PASSWORD.toCharArray(), salt),
                salt));
    }

    /** Returns the user if the credentials match; empty otherwise. Never says which part was wrong. */
    public Optional<User> login(String username, char[] password) {
        try {
            if (username == null || username.isBlank() || password == null || password.length == 0) {
                return Optional.empty();
            }
            Optional<User> found = users.findByUsername(username.trim());
            if (found.isEmpty()) return Optional.empty();

            User u = found.get();
            String attempt = hash(password, u.getPasswordSalt());
            boolean ok = MessageDigest.isEqual(
                    Base64.getDecoder().decode(attempt),
                    Base64.getDecoder().decode(u.getPasswordHash()));
            return ok ? found : Optional.empty();
        } finally {
            if (password != null) Arrays.fill(password, '\0');
        }
    }

    public void changePassword(String userId, char[] newPassword) {
        try {
            if (newPassword == null || newPassword.length < 8) {
                throw new IllegalArgumentException("Password must be at least 8 characters");
            }
            String salt = newSalt();
            users.updatePassword(userId, hash(newPassword, salt), salt);
        } finally {
            if (newPassword != null) Arrays.fill(newPassword, '\0');
        }
    }

    private static String newSalt() {
        byte[] salt = new byte[SALT_BYTES];
        RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private static String hash(char[] password, String saltB64) {
        PBEKeySpec spec = new PBEKeySpec(password, Base64.getDecoder().decode(saltB64), ITERATIONS, KEY_BITS);
        try {
            byte[] key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(key);
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("PBKDF2 not available", e);
        } finally {
            spec.clearPassword();
        }
    }
}