package com.warehouse.repository;

import com.warehouse.db.Database;
import com.warehouse.enums.ToolStatus;
import com.warehouse.exception.DataAccessException;
import com.warehouse.model.ToolCheckout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ToolCheckoutRepository {

    public List<ToolCheckout> findActive(){
        List<ToolCheckout> activeToolCheckouts = new ArrayList<>();
        String sql = "SELECT * FROM tool_checkouts WHERE status = 'AVAILABLE'";

        try(Connection c = Database.getConnection();
           PreparedStatement ps = c.prepareStatement(sql);
           ResultSet rs = ps.executeQuery()){
           while(rs.next()){
              activeToolCheckouts.add(mapRowToolCheckout(rs));
           }
        }catch(SQLException e){
            throw new DataAccessException("Cannot find active tool", e);
        }

        return activeToolCheckouts;
    }

    public List<ToolCheckout> findAll(){
        List<ToolCheckout> allTools = new ArrayList<>();
        String sql = "SELECT * FROM tool_checkouts";
        try(Connection c = Database.getConnection();
           PreparedStatement ps = c.prepareStatement(sql);
           ResultSet rs = ps.executeQuery()){
           while(rs.next()){
              allTools.add(mapRowToolCheckout(rs));
           }
        }catch(SQLException e){
            throw new DataAccessException("Cannot find all tool checkouts", e);
        }

        return allTools;
    }

    public Optional<ToolCheckout> findById(String id){
        String sql = "SELECT * FROM tool_checkouts WHERE id = ?";
        try(Connection c = Database.getConnection();
           PreparedStatement ps = c.prepareStatement(sql)){
           ps.setString(1, id);

           try(ResultSet rs = ps.executeQuery()){
              if(rs.next()) return Optional.of(mapRowToolCheckout(rs));

              return Optional.empty();
           }
        }catch(SQLException e){
            throw new DataAccessException("Cannot find tool's ID", e);
        }
    }

    public ToolCheckout save(ToolCheckout checkout){
        String sql = "INSERT INTO tool_checkouts (id, tool_id, tool_name, holder_id, holder_name, date_out, date_returned, status)" 
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection c = Database.getConnection();
           PreparedStatement ps = c.prepareStatement(sql)){
           ps.setString(1, checkout.getId());
           ps.setString(2, checkout.getToolId());
           ps.setString(3, checkout.getToolName());
           ps.setString(4, checkout.getHolderId());
           ps.setString(5, checkout.getHolderName());
           ps.setString(6, checkout.getDateOut() != null ? checkout.getDateOut().toString() : null);
           ps.setString(7, checkout.getDateReturned() != null ? checkout.getDateReturned().toString() : null);
           ps.setString(8, checkout.getStatus() != null ? checkout.getStatus().name() : null);
           ps.executeUpdate();
           return checkout;
        }catch(SQLException e){
            throw new DataAccessException("Failed to save tool checkout", e);
        }
    }

    public void markReturned(String id){
        String sql = "UPDATE tool_checkouts SET status = 'AVAILABLE', date_returned = ? WHERE id = ? AND status = 'CHECKED_OUT'";
        try(Connection c = Database.getConnection();
           PreparedStatement ps = c.prepareStatement(sql)){

           ps.setString(1, LocalDate.now().toString());
           ps.setString(2, id);

           int affectedRows = ps.executeUpdate();
           if(affectedRows == 0) throw new IllegalArgumentException("Tool checkout not found with ID: " + id);
        }catch(SQLException e){
            throw new DataAccessException("Failed to mark checkout returned", e);
        }
    }

    public int countActive(){
        String sql = "SELECT COUNT(*) FROM tool_checkouts WHERE status = 'AVAILABLE'";
        try(Connection c = Database.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            
            if(rs.next()) return rs.getInt(1);

            return 0;
        }catch(SQLException e){
            throw new IllegalArgumentException("Failed to return all active tool checkouts");
        }
    }

    private LocalDate parseDate(ResultSet rs, String colName) throws SQLException{
        String dateStr = rs.getString(colName);
        if(dateStr == null || dateStr.trim().isEmpty()) return null;

        if(dateStr.contains(" ")) dateStr = dateStr.split(" ")[0];
        return LocalDate.parse(dateStr);
    }

    private ToolCheckout mapRowToolCheckout(ResultSet rs) throws SQLException{
        return new ToolCheckout(
            rs.getString("id"),
            rs.getString("tool_id"),
            rs.getString("tool_name"),
            rs.getString("holder_id"),
            rs.getString("holder_name"),
            parseDate(rs, "date_out"),
            parseDate(rs, "date_returned"),
            ToolStatus.valueOf(rs.getString("status"))
        );
    }
}