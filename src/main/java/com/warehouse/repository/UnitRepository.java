package com.warehouse.repository;

import com.warehouse.db.Database;
import com.warehouse.enums.StockStatus;
import com.warehouse.enums.UnitType;
import com.warehouse.exception.DataAccessException;
import com.warehouse.model.Unit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UnitRepository {
    public List<Unit> findAll(){
        List<Unit> units = new ArrayList<>();
        String sql = "SELECT * FROM units";
        try(Connection c = Database.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {
                Unit unit = new Unit(
                    rs.getString("id"),
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getDouble("capacity_hp"),
                    UnitType.valueOf(rs.getString("type")),
                    rs.getString("serial"),
                    StockStatus.valueOf(rs.getString("stock_status"))
                );
                units.add(unit);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to load all units", e);
        }

        return units;
    }

    public Optional<Unit> findById(String id){
        if(id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID cannot be null or blank");
        }

        String sql = "SELECT * FROM units WHERE id = ?";
        try(Connection c = Database.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    Unit unit = new Unit(
                        rs.getString("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getDouble("capacity_hp"),
                        UnitType.valueOf(rs.getString("type")),
                        rs.getString("serial"),
                        StockStatus.valueOf(rs.getString("stock_status"))
                    );
                    return Optional.of(unit);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to find unit by ID", e);
        }

        return Optional.empty();
    }

    public Optional<Unit> findBySerial(String serial){
        if(serial == null || serial.isBlank()){
            throw new IllegalArgumentException("Serial cannot be null or blank");
        }

        String sql = "SELECT * FROM units WHERE serial = ?";
        try(Connection c = Database.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, serial);
            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    Unit unit = new Unit(
                        rs.getString("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getDouble("capacity_hp"),
                        UnitType.valueOf(rs.getString("type")),
                        rs.getString("serial"),
                        StockStatus.valueOf(rs.getString("stock_status"))
                    );
                    return Optional.of(unit);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to find unit by serial", e);
        }

        return Optional.empty();
    }

    public int count(){
        String sql = "SELECT COUNT(*) FROM units";
        try(Connection c = Database.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to count units", e);
        }
        return 0;
    }

    public Unit save(Unit unit) {
        String sql = "INSERT INTO units (id, brand, model, capacity_hp, type, serial, stock_status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, unit.getId());
            ps.setString(2, unit.getBrand());
            ps.setString(3, unit.getModel());
            ps.setDouble(4, unit.getCapacityHp());
            ps.setString(5, unit.getType().toString());
            ps.setString(6, unit.getSerial());
            ps.setString(7, unit.getStockStatus().toString());
            ps.executeUpdate();
            return unit;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error saving unit", e);
        }
    }

    public void update(Unit unit) {
        String sql = "UPDATE units SET brand = ?, model = ?, capacity_hp = ?, type = ?, serial = ?, stock_status = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, unit.getBrand());
            ps.setString(2, unit.getModel());
            ps.setDouble(3, unit.getCapacityHp());
            ps.setString(4, unit.getType().toString());
            ps.setString(5, unit.getSerial());
            ps.setString(6, unit.getStockStatus().toString());
            ps.setString(7, unit.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error updating unit", e);
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM units WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error deleting unit", e);
        }
    }
}