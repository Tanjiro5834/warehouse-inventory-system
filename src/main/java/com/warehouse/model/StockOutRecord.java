package com.warehouse.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StockOutRecord {

    private LocalDate date;
    private List<StockOutLine> lines = new ArrayList<>();
    private String issuedTo;
    private String jobReference;
    private String releasedBy;
    private String remarks;

    public StockOutRecord() {}

    public StockOutRecord(LocalDate date, List<StockOutLine> lines, String issuedTo,
                          String jobReference, String releasedBy, String remarks) {
        this.date = date;
        this.lines = lines != null ? lines : new ArrayList<>();
        this.issuedTo = issuedTo;
        this.jobReference = jobReference;
        this.releasedBy = releasedBy;
        this.remarks = remarks;
    }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public List<StockOutLine> getLines() { return lines; }
    public void setLines(List<StockOutLine> lines) {
        this.lines = lines != null ? lines : new ArrayList<>();
    }

    public String getIssuedTo() { return issuedTo; }
    public void setIssuedTo(String issuedTo) { this.issuedTo = issuedTo; }

    public String getJobReference() { return jobReference; }
    public void setJobReference(String jobReference) { this.jobReference = jobReference; }

    public String getReleasedBy() { return releasedBy; }
    public void setReleasedBy(String releasedBy) { this.releasedBy = releasedBy; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    @Override
    public String toString() {
        return "StockOutRecord{date=" + date + ", lines=" + lines.size() +
                ", issuedTo='" + issuedTo + "', job='" + jobReference + "'}";
    }

    // ----- Nested line class -----

    public static class StockOutLine {

        private String itemId;
        private int quantity;
        private List<String> serials = new ArrayList<>();

        public StockOutLine() {}

        public StockOutLine(String itemId, int quantity, List<String> serials) {
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
            return "StockOutLine{itemId='" + itemId + "', qty=" + quantity +
                    ", serials=" + serials + '}';
        }
    }
}