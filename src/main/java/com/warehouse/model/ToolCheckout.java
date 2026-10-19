package com.warehouse.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class ToolCheckout {

    private String id;
    private String toolId;
    private String toolName;
    private String holderId;
    private String holderName;
    private LocalDate dateOut;
    private LocalDate dateReturned;
    private String status;

    public ToolCheckout() {}

    public ToolCheckout(String id, String toolId, String toolName, String holderId,
                        String holderName, LocalDate dateOut, LocalDate dateReturned,
                        String status) {
        this.id = id;
        this.toolId = toolId;
        this.toolName = toolName;
        this.holderId = holderId;
        this.holderName = holderName;
        this.dateOut = dateOut;
        this.dateReturned = dateReturned;
        this.status = status;
    }

    public long daysOut(LocalDate today) {
        return dateOut == null ? 0 : ChronoUnit.DAYS.between(dateOut, today);
    }

    public boolean isOverdue(LocalDate today, int thresholdDays) {
        return dateReturned == null && daysOut(today) > thresholdDays;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getToolId() { return toolId; }
    public void setToolId(String toolId) { this.toolId = toolId; }

    public String getToolName() { return toolName; }
    public void setToolName(String toolName) { this.toolName = toolName; }

    public String getHolderId() { return holderId; }
    public void setHolderId(String holderId) { this.holderId = holderId; }

    public String getHolderName() { return holderName; }
    public void setHolderName(String holderName) { this.holderName = holderName; }

    public LocalDate getDateOut() { return dateOut; }
    public void setDateOut(LocalDate dateOut) { this.dateOut = dateOut; }

    public LocalDate getDateReturned() { return dateReturned; }
    public void setDateReturned(LocalDate dateReturned) { this.dateReturned = dateReturned; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ToolCheckout)) return false;
        return Objects.equals(id, ((ToolCheckout) o).id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "ToolCheckout{id='" + id + "', tool='" + toolName + "', holder='" + holderName +
                "', out=" + dateOut + ", returned=" + dateReturned + '}';
    }
}