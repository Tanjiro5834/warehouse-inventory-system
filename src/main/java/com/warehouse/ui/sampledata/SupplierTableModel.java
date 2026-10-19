package com.warehouse.ui.sampledata;

import javax.swing.table.AbstractTableModel;

/**
 * Sample data for suppliers.
 * TODO: wire to backend
 */
public class SupplierTableModel extends AbstractTableModel {

    private static final String[] COLS = {
            "Supplier Name", "Contact Person", "Phone", "Email", "Address"
    };

    private static final Object[][] DATA = {
            {"Cool Air Supply Co.",   "Maria Lim",     "0917-555-1001", "maria@coolair.ph",   "12 Industrial Ave., QC"},
            {"RefriChem Trading",     "Ben Ong",       "0917-555-1002", "ben@refrichem.ph",   "45 Delta St., Pasig"},
            {"Electrical Depot",      "Carla Reyes",   "0917-555-1003", "carla@eldepot.ph",   "88 Aurora Blvd., QC"},
            {"HVAC Parts Hub",        "Dan Tan",       "0917-555-1004", "dan@hvacparts.ph",   "3 Ortigas Ext., Pasig"},
            {"Bayani Hardware",       "Lito Cruz",     "0917-555-1005", "lito@bayanihw.ph",   "22 Rizal St., Makati"},
            {"Polar Climate Supply",  "Nina Ang",      "0917-555-1006", "nina@polarclimate.ph","7 Shaw Blvd., Mandaluyong"},
    };

    @Override public int getRowCount() { return DATA.length; }
    @Override public int getColumnCount() { return COLS.length; }
    @Override public String getColumnName(int c) { return COLS[c]; }
    @Override public Object getValueAt(int r, int c) { return DATA[r][c]; }
}