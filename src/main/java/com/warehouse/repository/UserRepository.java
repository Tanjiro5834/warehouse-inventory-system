package com.warehouse.repository;

import com.warehouse.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepository {

    private final List<User> users = new ArrayList<>();

    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    public Optional<User> findById(String id) {
        for (User u : users) {
            if (u.getId() != null && u.getId().equals(id)) return Optional.of(u);
        }
        return Optional.empty();
    }

    public Optional<User> findByUsername(String username) {
        for (User u : users) {
            if (u.getUsername() != null && u.getUsername().equals(username)) return Optional.of(u);
        }
        return Optional.empty();
    }

    public User save(User user) {
        if (user.getId() == null || user.getId().isBlank()) {
            user.setId(UUID.randomUUID().toString());
        }
        users.add(user);
        return user;
    }

    public void update(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(user.getId())) {
                users.set(i, user);
                return;
            }
        }
        throw new IllegalArgumentException("No user with id " + user.getId());
    }

    public void delete(String id) {
        users.removeIf(u -> u.getId() != null && u.getId().equals(id));
    }
}