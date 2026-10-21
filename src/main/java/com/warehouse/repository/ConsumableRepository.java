package com.warehouse.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.sql.*;

import com.warehouse.db.Database;
import com.warehouse.enums.StockStatus;
import com.warehouse.enums.UnitOfMeasure;
import com.warehouse.exception.DataAccessException;
import com.warehouse.model.Consumable;

public class ConsumableRepository {
    private static final String SELECT_BASE = "SELECT id, name, quantity, unit, reorder_level FROM consumables";

    public List<Consumable> findAll(){
        return queryList(SELECT_BASE + " ORDER BY name", null);
    }

    public Optional<Consumable> findById(String id){
        if (id == null || id.isBlank()) return Optional.empty();
        return queryOne(SELECT_BASE + " WHERE id = ?", id);
    }

    // block duplicates (name is UNIQUE)
    public Optional<Consumable> findByName(String name){
        if (name == null || name.isBlank()) return Optional.empty();
        return queryOne(SELECT_BASE + " WHERE name = ?", name.trim());
    }   

    public List<Consumable> findLowStock(){
        return queryList(SELECT_BASE + " WHERE quantity <= reorder_level ORDER BY quantity, name", null);
    }

    public Consumable save(Consumable item){
        String sql = "INSERT INTO consumables (id, name, quantity, unit, reorder_level) VALUES (?, ?, ?, ?, ?)";
        try(Connection c = Database.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, item.getId());
            ps.setString(2, item.getName());
            ps.setInt(3, item.getQuantity());
            ps.setString(4, item.getUnit().name());
            ps.setInt(5, item.getReorderLevel());
            ps.executeUpdate();
            return item;
        } catch (Exception e) {
            throw new DataAccessException("Cannot add consumable", e);
        }
    }

    // name, unit, reorder_level only
    public void update(Consumable consumable){
        String sql = "UPDATE consumables SET name = ?, unit = ?, reorder_level = ? WHERE id = ?";
        try(Connection c = Database.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, consumable.getName());
            ps.setString(2, consumable.getUnit().name());
            ps.setInt(3, consumable.getReorderLevel());
            ps.setString(4, consumable.getId());
            if(ps.executeUpdate() == 0){
                throw new DataAccessException("No consumable with id " + consumable.getId(), null);
            }
        } catch (Exception e) {
            throw new DataAccessException("Cannot update consumable", e);
        }
    }   

    public void delete(String id){
        String sql = "DELETE FROM consumables WHERE id = ?";
        try(Connection c = Database.getConnection();
           PreparedStatement ps = c.prepareStatement(sql)){
           ps.setString(1, id);
           ps.executeUpdate();
        } catch (Exception e) {
           throw new DataAccessException("Cannot delete consumables", e);
        }
    }

    private List<Consumable> queryList(String sql, String param) {
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            if (param != null) ps.setString(1, param);
            try (ResultSet rs = ps.executeQuery()) {
                List<Consumable> items = new ArrayList<>();
                while (rs.next()) items.add(mapRowToConsumable(rs));
                return items;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Cannot load consumables", e);
        }
    }

    private Optional<Consumable> queryOne(String sql, String param){
        List<Consumable> found = queryList(sql, param);
        return found.isEmpty() ? Optional.empty() : Optional.of(found.get(0));
    }

    private Consumable mapRowToConsumable(ResultSet rs) throws SQLException{
        return new Consumable(
            rs.getString("id"),
            rs.getString("name"),
            rs.getInt("quantity"),
            UnitOfMeasure.valueOf(rs.getString("unit")),
            rs.getInt("reorder_level"),
            StockStatus.valueOf(rs.getString("status"))
        );
    }
}
