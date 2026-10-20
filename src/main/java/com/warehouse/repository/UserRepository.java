package com.warehouse.repository;

import com.warehouse.db.Database;
import com.warehouse.exception.DataAccessException;
import com.warehouse.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private static final String COLS = "id, username, display_name, role, password_hash, salt";

    private User mapRow(ResultSet rs) throws SQLException {
        return new User(rs.getString("id"), rs.getString("username"),
                        rs.getString("display_name"), rs.getString("role"),
                        rs.getString("password_hash"), rs.getString("salt"));
    }

    public List<User> findAll() {
        String sql = "SELECT " + COLS + " FROM users ORDER BY username";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<User> users = new ArrayList<>();
            while (rs.next()) users.add(mapRow(rs));
            return users;
        } catch (SQLException e) {
            throw new DataAccessException("Error loading users", e);
        }
    }

    public Optional<User> findById(String id) {
        if (id == null || id.isBlank()) return Optional.empty();

        String sql = "SELECT " + COLS + " FROM users WHERE id = ?";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error finding user by ID", e);
        }
    }

    public Optional<User> findByUsername(String username) {
        if (username == null || username.isBlank()) return Optional.empty();

        String sql = "SELECT " + COLS + " FROM users WHERE username = ?";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error finding user by username", e);
        }
    }

    public User save(User u) {
        String sql = "INSERT INTO users (" + COLS + ") VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u.getId());
            ps.setString(2, u.getUsername());
            ps.setString(3, u.getDisplayName());
            ps.setString(4, u.getRole());
            ps.setString(5, u.getPasswordHash());
            ps.setString(6, u.getPasswordSalt());
            ps.executeUpdate();
            return u;
        } catch (SQLException e) {
            throw new DataAccessException("Error saving user", e);
        }
    }

    /** Profile fields only. Passwords change through updatePassword(). */
    public void update(User u) {
        String sql = "UPDATE users SET username = ?, display_name = ?, role = ? WHERE id = ?";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getDisplayName());
            ps.setString(3, u.getRole());
            ps.setString(4, u.getId());
            if (ps.executeUpdate() == 0) {
                throw new DataAccessException("No user with id " + u.getId(), null);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error updating user", e);
        }
    }

    public void updatePassword(String id, String hash, String salt) {
        String sql = "UPDATE users SET password_hash = ?, salt = ? WHERE id = ?";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, hash);
            ps.setString(2, salt);
            ps.setString(3, id);
            if (ps.executeUpdate() == 0) {
                throw new DataAccessException("No user with id " + id, null);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error updating password", e);
        }
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM users";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getLong(1) : 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error counting users", e);
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting user", e);
        }
    }
}