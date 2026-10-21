package com.warehouse.repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.warehouse.db.Database;
import com.warehouse.enums.ToolStatus;
import com.warehouse.exception.DataAccessException;
import com.warehouse.model.Tool;

public class ToolRepository {
    private static final String SELECT_BASE = """
    SELECT t.id, t.name, t.quantity, t.status, t.assigned_technician_id,
           tech.name AS assigned_technician_name
    FROM tools t
    LEFT JOIN technicians tech ON tech.id = t.assigned_technician_id
    """;

    public List<Tool> findAll(){
        return queryList(SELECT_BASE + " ORDER BY t.name", null);
    }

    public Optional<Tool> findById(String id) {
        if (id == null || id.isBlank()) return Optional.empty();
        return queryOne(SELECT_BASE + " WHERE t.id = ?", id);
    }

    public List<Tool> findByStatus(ToolStatus status) {
        if(status == null) return List.of();
        return queryList(SELECT_BASE + " WHERE t.status = ? ORDER BY t.name", status.name());
    }

    public List<Tool> findByTechnician(String technicianId) {
        if (technicianId == null || technicianId.isBlank()) return List.of();
        return queryList(SELECT_BASE + " WHERE t.assigned_technician_id = ? ORDER BY t.name", technicianId);
    }

    public Tool save(Tool t){
        String sql = "INSERT INTO tools (id, name, quantity, status, assigned_technician_id)"
        + " VALUES (?, ?, ?, ?, ?)";
        try(Connection c = Database.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, t.getId());
            ps.setString(2, t.getName());
            ps.setInt(3, t.getQuantity());
            ps.setString(4, t.getStatus().name());
            ps.setString(5, t.getAssignedTechnicianId());
            ps.executeUpdate();
            return t;
        } catch (SQLException e) {
            throw new DataAccessException("Cannot save tool", e);
        }
    }

     // name, quantity only
    public void update(Tool t){
        String sql = "UPDATE tools SET name = ?, quantity = ? WHERE id = ?";
        try(Connection c = Database.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1, t.getName());
            ps.setInt(2, t.getQuantity());
            ps.setString(3, t.getId());
            if(ps.executeUpdate() == 0){
                throw new DataAccessException("No tool with id: " + t.getId(), null);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Cannot update tool", e);
        }
    }

    public void delete(String id){
        String sql = "DELETE FROM tools WHERE id = ?";
        try(Connection c = Database.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Cannot delete tool with id: " + id, e);
        }
    }

    private List<Tool> queryList(String sql, String param){
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            if (param != null) ps.setString(1, param);
            try (ResultSet rs = ps.executeQuery()) {
                List<Tool> tools = new ArrayList<>();
                while (rs.next()) tools.add(mapRow(rs));
                return tools;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Cannot load tools", e);
        }
    }

    private Optional<Tool> queryOne(String sql, String param){
        List<Tool> found = queryList(sql, param);
        return found.isEmpty() ? Optional.empty() : Optional.of(found.get(0));
    }

    private Tool mapRow(ResultSet rs) throws SQLException{
        Tool tool = new Tool(
            rs.getString("id"),
            rs.getString("name"),
            rs.getInt("quantity"),
            ToolStatus.valueOf(rs.getString("status")),
            rs.getString("assigned_technician_id")
        );

        tool.setAssignedTechnicianName(rs.getString("assigned_technician_name"));
        return tool;
    }
}
