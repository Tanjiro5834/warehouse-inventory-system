package com.warehouse.repository;

import com.warehouse.db.Database;
import com.warehouse.exception.DataAccessException;
import com.warehouse.model.Technician;

import java.util.List;
import java.util.Optional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TechnicianRepository {
    private static final String COLS = "id, name, specialty, phone";

    public List<Technician> findAll(){
        List<Technician> technicians = new ArrayList<>();
        String sql = """
            SELECT t.id, t.name, t.specialty, t.phone,
                COUNT(tc.id) AS active_jobs
            FROM technicians t
            LEFT JOIN tool_checkouts tc
                ON tc.holder_id = t.id AND tc.status = 'CHECKED_OUT'
            GROUP BY t.id
            ORDER BY t.name
        """;
        try(Connection c = Database.getConnection();
           PreparedStatement ps = c.prepareStatement(sql);
           ResultSet rs = ps.executeQuery()){
           
           while(rs.next()){
              technicians.add(mapRowToTechnician(rs));
           }
        }catch(SQLException e){
            throw new DataAccessException("Cannot get all technicians", e);
        }

        return technicians;
    }

    public Optional<Technician> findById(String id){
        if(id == null || id.isBlank()) return Optional.empty();
        String sql = """
        SELECT t.id, t.name, t.specialty, t.phone,
               COUNT(tc.id) AS active_jobs
        FROM technicians t
        LEFT JOIN tool_checkouts tc
               ON tc.holder_id = t.id AND tc.status = 'CHECKED_OUT'
        WHERE t.id = ?
        GROUP BY t.id
        """;
        try(Connection c = Database.getConnection();
            PreparedStatement ps =  c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            return rs.next() ? Optional.of(mapRowToTechnician(rs)) : Optional.empty();
        }catch(SQLException e){
            throw new DataAccessException("Cannot update technician", e);
        }
    }

    public Technician save(Technician technician){
        String sql = "INSERT INTO technicians (" + COLS + ") VALUES (?, ?, ?, ?)";
        try(Connection c = Database.getConnection();
           PreparedStatement ps = c.prepareStatement(sql)){
           ps.setString(1, technician.getId());
           ps.setString(2, technician.getName());
           ps.setString(3, technician.getSpecialty());
           ps.setString(4, technician.getPhone());
           ps.executeUpdate();
           return technician;
        }catch(SQLException e){
            throw new DataAccessException("Cannot add technician", e);
        }
    }

    public void update(Technician technician){
        String sql = "UPDATE technicians SET name = ?, specialty = ?, phone = ? WHERE id = ?";
        try(Connection c = Database.getConnection();
            PreparedStatement ps =  c.prepareStatement(sql)){
            ps.setString(1, technician.getName());
            ps.setString(2, technician.getSpecialty());
            ps.setString(3, technician.getPhone());
            ps.setString(4, technician.getId());
            if(ps.executeUpdate() == 0) throw new DataAccessException("No technician with id " + technician.getId(), null);
        }catch(SQLException e){
            throw new DataAccessException("Cannot update technician", e);
        }
    }

    public void delete(String id){
        String sql = "DELETE FROM technicians WHERE id = ?";
        try(Connection c = Database.getConnection();
           PreparedStatement ps = c.prepareStatement(sql)){
           ps.setString(1, id);
           ps.executeUpdate();
        }catch(SQLException e){
            throw new DataAccessException("Cannot delete technician", e);
        }
    }

    private Technician mapRowToTechnician(ResultSet rs) throws SQLException{
        return new Technician(
            rs.getString("id"), 
            rs.getString("name"), 
            rs.getString("specialty"),
            rs.getString("phone"),
            rs.getInt("active_jobs")
        );
    }
}