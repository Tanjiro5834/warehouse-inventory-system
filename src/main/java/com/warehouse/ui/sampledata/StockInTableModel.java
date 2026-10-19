package com.warehouse.ui.sampledata;

import javax.swing.table.AbstractTableModel;

/**
 * Sample rows for Stock In preview table.
 * TODO: wire to backend
 */
public class StockInTableModel extends AbstractTableModel {

    private static final String[] COLS = {
            "Item", "Qty", "Serial No.", "Supplier", "DR Number", "Received By"
    };

    private static final Object[][] DATA = {
            {"Carrier Optima 1.5 (AC Unit)",  5, "CR-AC-1001…1005", "Cool Air Supply Co.", "DR-10231", "M. Reyes"},
            {"R410A Refrigerant",            12, "—",                "RefriChem Trading",   "DR-10230", "M. Reyes"},
            {"PVC Tape (black)",             40, "—",                "Electrical Depot",    "DR-10228", "J. Santos"},
    };

    @Override public int getRowCount() { return DATA.length; }
    @Override public int getColumnCount() { return COLS.length; }
    @Override public String getColumnName(int c) { return COLS[c]; }
    @Override public Object getValueAt(int r, int c) { return DATA[r][c]; }
}