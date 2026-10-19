package com.warehouse.ui.sampledata;

import javax.swing.table.AbstractTableModel;

/**
 * Sample data for consumables.
 * TODO: wire to backend
 */
public class ConsumableTableModel extends AbstractTableModel {

    private static final String[] COLS = {
            "Item", "Quantity", "Unit", "Reorder Level", "Stock Status"
    };

    private static final Object[][] DATA = {
            {"PVC Tape (black)",   120, "roll", 40, "IN_STOCK"},
            {"PVC Tape (white)",    35, "roll", 40, "LOW_STOCK"},
            {"R410A Refrigerant",   18, "kg",   10, "IN_STOCK"},
            {"R32 Refrigerant",      6, "kg",   10, "LOW_STOCK"},
            {"R22 Refrigerant",      0, "kg",    5, "OUT_OF_STOCK"},
            {"Screws 3/8\" (100/pc)", 58, "box", 20, "IN_STOCK"},
            {"Copper Tubing 1/4\"",  45, "roll", 15, "IN_STOCK"},
            {"Drain Hose",           80, "pcs",  30, "IN_STOCK"},
            {"Capacitor 35µF",       22, "pcs",  10, "IN_STOCK"},
            {"Foam Insulation",       4, "roll",  8, "LOW_STOCK"},
    };

    @Override public int getRowCount() { return DATA.length; }
    @Override public int getColumnCount() { return COLS.length; }
    @Override public String getColumnName(int col) { return COLS[col]; }

    @Override
    public Object getValueAt(int row, int col) {
        return DATA[row][col];
    }
}