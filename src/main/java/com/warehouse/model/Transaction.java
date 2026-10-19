package com.warehouse.model;

import java.time.LocalDate;
import java.util.Objects;

import com.warehouse.enums.TransactionType;

public class Transaction {

    private String id;
    private LocalDate date;
    private TransactionType type;
    private String itemId;
    private String itemName;
    private int quantity;
    private String reference;
    private String user;

    public Transaction() {}

    public Transaction(String id, LocalDate date, TransactionType type,
                       String itemId, String itemName, int quantity,
                       String reference, String user) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.reference = reference;
        this.user = user;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public String getItemId() { return itemId; }
    public void setItemId(String itemId) { this.itemId = itemId; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        return Objects.equals(id, ((Transaction) o).id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Transaction{id='" + id + "', date=" + date + ", type=" + type +
                ", item='" + itemName + "', qty=" + quantity +
                ", ref='" + reference + "', user='" + user + "'}";
    }
}