package com.warehouse.repository;

import com.warehouse.db.Database;
import com.warehouse.exception.DataAccessException;
import com.warehouse.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    public List<User> findAll(){
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try(Connection c = Database.getConnection();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while(rs.next()) {
                User user = new User(
                    rs.getString("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public Optional<User> findById(String id) {
        if (id == null || id.isBlank()) return Optional.empty();

        String sql = "SELECT id, username, password, role FROM users WHERE id = ?";
        try (Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new User(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                    ));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error finding user by ID", e);
        }
    }
    
    public Optional<User> findByUsername(String name){
        if(name == null) return Optional.empty();

        String sql = "SELECT * FROM users WHERE username = ?";
        try(Connection c = Database.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    User user = new User(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                    );
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error finding user by username", e);
        }

        return Optional.empty();
    }

    public User save(User user) {
        String sql = "INSERT INTO users (id, username, password_hash, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getId());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPasswordHash());
            ps.setString(4, user.getRole());
            ps.executeUpdate();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error saving user", e);
        }
    }

    public void update(User user) {
        String sql = "UPDATE users SET username = ?, password_hash = ?, role = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getRole());
            ps.setString(4, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error updating user", e);
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error deleting user", e);
        }
    }
}