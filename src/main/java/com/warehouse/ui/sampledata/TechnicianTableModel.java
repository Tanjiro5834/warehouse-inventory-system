package com.warehouse.ui.sampledata;

import javax.swing.table.AbstractTableModel;

/**
 * Sample data for technicians.
 * TODO: wire to backend
 */
public class TechnicianTableModel extends AbstractTableModel {

    private static final String[] COLS = {
            "Name", "Specialty", "Phone", "Active Jobs"
    };

    private static final Object[][] DATA = {
            {"M. Reyes",      "Split / Inverter AC",   "0918-555-2001", 3},
            {"J. Santos",     "Refrigeration",         "0918-555-2002", 2},
            {"A. Cruz",       "Window AC",             "0918-555-2003", 4},
            {"R. Dela Cruz",  "Electrical",            "0918-555-2004", 1},
            {"K. Mendoza",    "General HVAC",          "0918-555-2005", 0},
            {"B. Villanueva", "Inverter Diagnostics",  "0918-555-2006", 2},
    };

    @Override public int getRowCount() { return DATA.length; }
    @Override public int getColumnCount() { return COLS.length; }
    @Override public String getColumnName(int c) { return COLS[c]; }
    @Override public Object getValueAt(int r, int c) { return DATA[r][c]; }
}