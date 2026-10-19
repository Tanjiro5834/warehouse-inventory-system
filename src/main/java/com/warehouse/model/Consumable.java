package com.warehouse.model;


import java.util.Objects;

import com.warehouse.enums.StockStatus;
import com.warehouse.enums.UnitOfMeasure;

public class Consumable {

    private String id;
    private String name;
    private int quantity;
    private UnitOfMeasure unit;
    private int reorderLevel;
    private StockStatus stockStatus;

    public Consumable() {}

    public Consumable(String id, String name, int quantity, UnitOfMeasure unit,
                      int reorderLevel, StockStatus stockStatus) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.reorderLevel = reorderLevel;
        this.stockStatus = stockStatus;
    }

    /** Derives stock status from qty vs reorder level. */
    public static StockStatus computeStatus(int quantity, int reorderLevel) {
        if (quantity <= 0) return StockStatus.OUT_OF_STOCK;
        if (quantity <= reorderLevel) return StockStatus.LOW_STOCK;
        return StockStatus.IN_STOCK;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public UnitOfMeasure getUnit() { return unit; }
    public void setUnit(UnitOfMeasure unit) { this.unit = unit; }

    public int getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(int reorderLevel) { this.reorderLevel = reorderLevel; }

    public StockStatus getStockStatus() { return stockStatus; }
    public void setStockStatus(StockStatus stockStatus) { this.stockStatus = stockStatus; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Consumable)) return false;
        return Objects.equals(id, ((Consumable) o).id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Consumable{id='" + id + "', name='" + name + "', quantity=" + quantity +
                ", unit=" + unit + ", reorderLevel=" + reorderLevel +
                ", status=" + stockStatus + '}';
    }
}