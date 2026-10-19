package com.warehouse.model;

import java.util.Objects;

import com.warehouse.enums.StockStatus;
import com.warehouse.enums.UnitType;

public class Unit {

    private String id;
    private String brand;
    private String model;
    private double capacityHp;
    private UnitType type;
    private String serial;
    private StockStatus stockStatus;

    public Unit() {}

    public Unit(String id, String brand, String model, double capacityHp,
                UnitType type, String serial, StockStatus stockStatus) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.capacityHp = capacityHp;
        this.type = type;
        this.serial = serial;
        this.stockStatus = stockStatus;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public double getCapacityHp() { return capacityHp; }
    public void setCapacityHp(double capacityHp) { this.capacityHp = capacityHp; }

    public UnitType getType() { return type; }
    public void setType(UnitType type) { this.type = type; }

    public String getSerial() { return serial; }
    public void setSerial(String serial) { this.serial = serial; }

    public StockStatus getStockStatus() { return stockStatus; }
    public void setStockStatus(StockStatus stockStatus) { this.stockStatus = stockStatus; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Unit)) return false;
        return Objects.equals(id, ((Unit) o).id)
                && Objects.equals(serial, ((Unit) o).serial);
    }

    @Override
    public int hashCode() { return Objects.hash(id, serial); }

    @Override
    public String toString() {
        return "Unit{id='" + id + "', brand='" + brand + "', model='" + model +
                "', capacityHp=" + capacityHp + ", type=" + type +
                ", serial='" + serial + "', status=" + stockStatus + '}';
    }
}