package com.warehouse.repository;

import com.warehouse.enums.TransactionType;
import com.warehouse.model.StockInRecord;
import com.warehouse.model.StockOutRecord;
import com.warehouse.model.Transaction;

import java.time.LocalDate;
import java.util.List;

public class TransactionRepository {

    void saveStockIn(StockInRecord record);

    void saveStockOut(StockOutRecord record);

    List<Transaction> findAll();

    List<Transaction> findByRange(LocalDate from, LocalDate to);

    List<Transaction> findByType(TransactionType type);

    List<Transaction> findRecent(int limit);
}