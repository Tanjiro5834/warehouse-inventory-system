package com.warehouse.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StockInRecord {

    private LocalDate date;
    private List<StockInLine> lines = new ArrayList<>();
    private String supplier;
    private String drNumber;
    private String receivedBy;
    private String remarks;

    public StockInRecord() {}

    public StockInRecord(LocalDate date, List<StockInLine> lines, String supplier,
                         String drNumber, String receivedBy, String remarks) {
        this.date = date;
        this.lines = lines != null ? lines : new ArrayList<>();
        this.supplier = supplier;
        this.drNumber = drNumber;
        this.receivedBy = receivedBy;
        this.remarks = remarks;
    }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public List<StockInLine> getLines() { return lines; }
    public void setLines(List<StockInLine> lines) {
        this.lines = lines != null ? lines : new ArrayList<>();
    }

    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }

    public String getDrNumber() { return drNumber; }
    public void setDrNumber(String drNumber) { this.drNumber = drNumber; }

    public String getReceivedBy() { return receivedBy; }
    public void setReceivedBy(String receivedBy) { this.receivedBy = receivedBy; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    @Override
    public String toString() {
        return "StockInRecord{date=" + date + ", lines=" + lines.size() +
                ", supplier='" + supplier + "', dr='" + drNumber + "'}";
    }

    // ----- Nested line class -----

    public static class StockInLine {

        private String itemId;
        private int quantity;
        private List<String> serials = new ArrayList<>();

        public StockInLine() {}

        public StockInLine(String itemId, int quantity, List<String> serials) {
            this.itemId = itemId;
            this.quantity = quantity;
            this.serials = serials != null ? serials : new ArrayList<>();
        }

        public String getItemId() { return itemId; }
        public void setItemId(String itemId) { this.itemId = itemId; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }

        public List<String> getSerials() { return serials; }
        public void setSerials(List<String> serials) {
            this.serials = serials != null ? serials : new ArrayList<>();
        }

        @Override
        public String toString() {
            return "StockInLine{itemId='" + itemId + "', qty=" + quantity +
                    ", serials=" + serials + '}';
        }
    }
}