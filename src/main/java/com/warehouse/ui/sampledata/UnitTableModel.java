package com.warehouse.ui.sampledata;

import javax.swing.table.AbstractTableModel;

/**
 * Sample data for air-conditioner units.
 * Replace with a real model backed by a service later.
 * TODO: wire to backend
 */
public class UnitTableModel extends AbstractTableModel {

    private static final String[] COLS = {
            "Brand", "Model", "Capacity (HP)", "Type", "Serial No.", "Stock Status"
    };

    // { brand, model, capacity, type, serial, statusKey }
    private static final Object[][] DATA = {
            {"Carrier",  "Optima 1.5",  "1.5", "Inverter", "CR-AC-1001", "IN_STOCK"},
            {"Carrier",  "Optima 2.0",  "2.0", "Inverter", "CR-AC-1002", "IN_STOCK"},
            {"Daikin",   "FTKQ 1.0",    "1.0", "Split",    "DK-AC-2001", "LOW_STOCK"},
            {"Daikin",   "FTKQ 1.5",    "1.5", "Inverter", "DK-AC-2002", "IN_STOCK"},
            {"Panasonic","CS-PN 1.0",  "1.0", "Window",   "PS-AC-3001", "OUT_OF_STOCK"},
            {"Panasonic","CS-PN 1.5",  "1.5", "Split",    "PS-AC-3002", "IN_STOCK"},
            {"LG",       "Hercules 2.0","2.0","Window",   "LG-AC-4001", "IN_STOCK"},
            {"LG",       "Dual Cool 1.5","1.5","Inverter", "LG-AC-4002", "LOW_STOCK"},
            {"Midea",    "Fairy 2.5",   "2.5", "Split",    "MD-AC-5001", "IN_STOCK"},
            {"Koppel",   "KSW 1.0",     "1.0", "Window",   "KP-AC-6001", "IN_STOCK"},
    };

    @Override public int getRowCount() { return DATA.length; }
    @Override public int getColumnCount() { return COLS.length; }
    @Override public String getColumnName(int col) { return COLS[col]; }

    @Override
    public Object getValueAt(int row, int col) {
        return DATA[row][col];
    }
}