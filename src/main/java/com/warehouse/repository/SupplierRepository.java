package com.warehouse.repository;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.warehouse.db.Database;
import com.warehouse.exception.DataAccessException;
import com.warehouse.model.Supplier;

public class SupplierRepository {
    private static final String SELECT_BASE = "SELECT id, name, contact_person, phone, email, address FROM suppliers";
    private static final String INSERT = "INSERT INTO suppliers (id, name, contact_person, phone, email, address)"
    + " VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE suppliers SET name = ?, contact_person = ?, phone = ?,"
    + "email = ?, address = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM suppliers WHERE id = ?";

    public List<Supplier> findAll(){
        return queryList(SELECT_BASE + " ORDER BY name", null);
    }

    public Optional<Supplier> findById(String id){
        if (id == null || id.isBlank()) return Optional.empty();
        return queryOne(SELECT_BASE + " WHERE id = ?", id);
    }

    public Supplier save(Supplier s){
        String sql = INSERT;
        try(Connection c = Database.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getId());
            ps.setString(2, s.getName());
            ps.setString(3, s.getContactPerson());
            ps.setString(4, s.getPhone());
            ps.setString(5, s.getEmail());
            ps.setString(6, s.getAddress());
            ps.executeUpdate();
            return s;
        } catch (SQLException e) {
            throw new DataAccessException("Cannot add supplier", e);
        }
    }

    public void update(Supplier s){
        String sql = UPDATE;
        try(Connection c = Database.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getContactPerson());
            ps.setString(3, s.getPhone());
            ps.setString(4, s.getEmail());
            ps.setString(5, s.getAddress());
            ps.setString(6, s.getId());
            if(ps.executeUpdate() == 0){
                throw new DataAccessException("No supplier with id: " + s.getId(), null);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Cannot update supplier", e);
        }
    }

    public void delete(String id){
        String sql = DELETE;
        try(Connection c = Database.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Cannot delete supplier", e);
        }
    }

    private List<Supplier> queryList(String sql, String param){
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            if (param != null) ps.setString(1, param);
            try (ResultSet rs = ps.executeQuery()) {
                List<Supplier> suppliers = new ArrayList<>();
                while (rs.next()) suppliers.add(mapRow(rs));
                return suppliers;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Cannot load suppliers", e);
        }
    }

    private Optional<Supplier> queryOne(String sql, String param){
        List<Supplier> found = queryList(sql, param);
        return found.isEmpty() ? Optional.empty() : Optional.of(found.get(0));
    }

    private Supplier mapRow(ResultSet rs) throws SQLException{
        return new Supplier(
            rs.getString("id"),
            rs.getString("name"),
            rs.getString("contact_person"),
            rs.getString("phone"),
            rs.getString("email"),
            rs.getString("address")
        );
    }
}
