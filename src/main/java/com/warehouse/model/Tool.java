package com.warehouse.model;

import java.util.Objects;

import com.warehouse.enums.ToolStatus;

public class Tool {

    private String id;
    private String name;
    private int quantity;
    private ToolStatus status;
    private String assignedTechnicianId;

    public Tool() {}

    public Tool(String id, String name, int quantity, ToolStatus status,
                String assignedTechnicianId) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.status = status;
        this.assignedTechnicianId = assignedTechnicianId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public ToolStatus getStatus() { return status; }
    public void setStatus(ToolStatus status) { this.status = status; }

    public String getAssignedTechnicianId() { return assignedTechnicianId; }
    public void setAssignedTechnicianId(String assignedTechnicianId) {
        this.assignedTechnicianId = assignedTechnicianId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tool)) return false;
        return Objects.equals(id, ((Tool) o).id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Tool{id='" + id + "', name='" + name + "', quantity=" + quantity +
                ", status=" + status + ", assignedTo='" + assignedTechnicianId + "'}";
    }
}