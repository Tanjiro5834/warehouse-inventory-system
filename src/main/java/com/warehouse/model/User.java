package com.warehouse.model;

import java.util.Objects;

public class User {

    private String id;
    private String username;
    private String displayName;
    private String role;
    private String passwordHash;
    private String passwordSalt;

    public User() {}

    public User(String id, String username, String displayName, String role,
                String passwordHash, String passwordSalt) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.role = role;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
    }

    // Kept for backward compatibility if anything constructs a user without credentials
    public User(String id, String username, String displayName, String role) {
        this(id, username, displayName, role, null, null);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getPasswordSalt() { return passwordSalt; }
    public void setPasswordSalt(String passwordSalt) { this.passwordSalt = passwordSalt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        return Objects.equals(username, ((User) o).username);
    }

    @Override
    public int hashCode() { return Objects.hash(username); }

    @Override
    public String toString() {
        return "User{username='" + username + "', role='" + role + "'}";
    }
}