package com.warehouse.ui.sampledata;

import javax.swing.table.AbstractTableModel;

/**
 * Sample data for tools.
 * TODO: wire to backend
 */
public class ToolTableModel extends AbstractTableModel {

    private static final String[] COLS = {
            "Tool", "Quantity", "Status", "Assigned Technician"
    };

    private static final Object[][] DATA = {
            {"Torque Wrench",      2, "Available",    "—"},
            {"Electric Drill",     3, "Checked Out",  "M. Reyes"},
            {"Manifold Gauge Set", 4, "Available",    "—"},
            {"Vacuum Pump",        1, "Checked Out",  "J. Santos"},
            {"Flaring Tool Kit",   2, "Under Repair", "—"},
            {"Digital Multimeter", 5, "Available",    "—"},
            {"Tube Cutter",        6, "Checked Out",  "A. Cruz"},
            {"Pipe Bender",        1, "Lost",         "—"},
            {"Leak Detector",      3, "Available",    "—"},
            {"Nut Driver Set",     4, "Checked Out",  "R. Dela Cruz"},
    };

    @Override public int getRowCount() { return DATA.length; }
    @Override public int getColumnCount() { return COLS.length; }
    @Override public String getColumnName(int col) { return COLS[col]; }

    @Override
    public Object getValueAt(int row, int col) {
        return DATA[row][col];
    }
}