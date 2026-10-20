package com.warehouse.repository;

import com.warehouse.db.Database;

import com.warehouse.enums.TransactionType;
import com.warehouse.exception.DataAccessException;
import com.warehouse.model.StockInRecord;
import com.warehouse.model.StockOutRecord;
import com.warehouse.model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionRepository {
    public void saveStockIn(StockInRecord record){
        if (record.getId() == null || record.getId().isBlank()) {
            record.setId(UUID.randomUUID().toString());
        }

        String headerSql = "INSERT INTO stock_in (id, date, supplier, dr_number, received_by, remarks) "
                     + "VALUES (?, ?, ?, ?, ?, ?)";
        String lineSql   = "INSERT INTO stock_in_lines (stock_in_id, item_id, quantity) VALUES (?, ?, ?)";
        String serialSql = "INSERT INTO stock_in_line_serials (line_id, serial) VALUES (?, ?)";

        try(Connection c = Database.getConnection()){
            c.setAutoCommit(false);

            try(PreparedStatement header = c.prepareStatement(headerSql);
                PreparedStatement line = c.prepareStatement(lineSql, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement serial = c.prepareStatement(serialSql)){

                header.setString(1, record.getId());
                header.setString(2, record.getDate().toString());   // LocalDate → "2026-10-20"
                header.setString(3, record.getSupplier());
                header.setString(4, record.getDrNumber());
                header.setString(5, record.getReceivedBy());
                header.setString(6, record.getRemarks());
                header.executeUpdate();

                for(StockInRecord.StockInLine l : record.getLines()){
                    line.setString(1, record.getId());
                    line.setString(2, l.getItemId());
                    line.setInt(3, l.getQuantity());
                    line.executeUpdate();

                    long lineId;
                    try(ResultSet keys = line.getGeneratedKeys()){
                        keys.next();
                        lineId = keys.getLong(1);
                    }

                    for(String s : l.getSerials()){
                        serial.setLong(1, lineId);
                        serial.setString(2, s);
                        serial.addBatch();
                    }
                    serial.executeBatch();
                }

                c.commit();

            }catch(SQLException e){
                c.rollback();
                throw e;
            }
        }catch(SQLException e){
            throw new RuntimeException("Failed to save stock-in " + record.getDrNumber(), e);
        }
    }

    public void saveStockOut(StockOutRecord record) {
        if (record.getId() == null || record.getId().isBlank()) {
            record.setId(UUID.randomUUID().toString());
        }

        String headerSql = "INSERT INTO stock_out (id, date, issued_to, job_reference, released_by, remarks) "
                        + "VALUES (?, ?, ?, ?, ?, ?)";
        String lineSql   = "INSERT INTO stock_out_lines (stock_out_id, item_id, quantity) VALUES (?, ?, ?)";
        String serialSql = "INSERT INTO stock_out_line_serials (line_id, serial) VALUES (?, ?)";

        try (Connection conn = Database.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement header = conn.prepareStatement(headerSql);
                PreparedStatement line   = conn.prepareStatement(lineSql, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement serial = conn.prepareStatement(serialSql)) {

                header.setString(1, record.getId());
                header.setString(2, record.getDate().toString());
                header.setString(3, record.getIssuedTo());
                header.setString(4, record.getJobReference());
                header.setString(5, record.getReleasedBy());
                header.setString(6, record.getRemarks());
                header.executeUpdate();

                for (StockOutRecord.StockOutLine l : record.getLines()) {
                    line.setString(1, record.getId());
                    line.setString(2, l.getItemId());
                    line.setInt(3, l.getQuantity());
                    line.executeUpdate();

                    long lineId;
                    try (ResultSet keys = line.getGeneratedKeys()) {
                        keys.next();
                        lineId = keys.getLong(1);
                    }

                    for (String s : l.getSerials()) {
                        serial.setLong(1, lineId);
                        serial.setString(2, s);
                        serial.addBatch();
                    }
                    serial.executeBatch();
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save stock-out " + record.getJobReference(), e);
        }
    }

    public List<Transaction> findAll(){
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions ORDER BY date DESC";
        try(Connection c = Database.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while(rs.next()) {
               transactions.add(mapRowToTransaction(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public List<Transaction> findByRange(LocalDate from, LocalDate to){
        List<Transaction> byDateRange = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE date >= ? AND date > ?";

        try(Connection c = Database.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){

            ps.setObject(1, from);
            ps.setObject(2, to);

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    byDateRange.add(mapRowToTransaction(rs));
                }
            }
        }catch(SQLException e){
            throw new DataAccessException("findByDateRange failed", e);
        }

        return byDateRange;
    }

    public List<Transaction> findByType(TransactionType type){
        List<Transaction> types = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE type = ?";

        try(Connection c = Database.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            
            ps.setString(1, String.valueOf(type));

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    types.add(mapRowToTransaction(rs));
                }
            }
        }catch(SQLException e){
            throw new DataAccessException("Cannot find by type", e);
        }

        return types;
    }

    public List<Transaction> findRecent(int limit){
        List<Transaction> recents = new ArrayList<>();
        String sql = "SELECT * FROM transactions ORDER BY date DESC LIMIT ?";

        try(Connection c = Database.getConnection();
           PreparedStatement ps = c.prepareStatement(sql)){
           
           ps.setInt(1, limit);

           try(ResultSet rs = ps.executeQuery()){
               while(rs.next()){
                  recents.add(mapRowToTransaction(rs));
               }
           }
        }catch(SQLException e){
            throw new DataAccessException("Cannot find by recent", e);
        }

        return recents;
    }

    private Transaction mapRowToTransaction(ResultSet rs) throws SQLException{
        String dateStr = rs.getString("date");
        LocalDate localDate = null;
        if(dateStr != null  && !dateStr.isEmpty()){
            if(dateStr.contains(" ")){
                localDate = LocalDate.parse(dateStr.split(" ")[0]);
            }else{
                localDate = LocalDate.parse(dateStr);
            }
        }

        return new Transaction(
            rs.getString("id"),
            localDate,
            TransactionType.valueOf(rs.getString("type")),
            rs.getString("item_id"),
            rs.getString("item_name"),
            rs.getInt("quantity"),
            rs.getString("reference"),
            rs.getString("user")
        );
    }
}