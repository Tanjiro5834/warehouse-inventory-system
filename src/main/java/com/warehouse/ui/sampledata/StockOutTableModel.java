package com.warehouse.ui.sampledata;

import javax.swing.table.AbstractTableModel;

/**
 * Sample rows for Stock Out preview table.
 * TODO: wire to backend
 */
public class StockOutTableModel extends AbstractTableModel {

    private static final String[] COLS = {
            "Item", "Qty", "Serial No.", "Issued To", "Job Reference", "Released By"
    };

    private static final Object[][] DATA = {
            {"Daikin FTKQ 1.0 (AC Unit)",   2, "DK-AC-2001…2002", "A. Cruz",   "JR-4402", "J. Santos"},
            {"PVC Tape (black)",           10, "—",               "M. Reyes",  "JR-4401", "J. Santos"},
            {"R32 Refrigerant",             2, "—",               "R. Dela Cruz", "JR-4400", "J. Santos"},
    };

    @Override public int getRowCount() { return DATA.length; }
    @Override public int getColumnCount() { return COLS.length; }
    @Override public String getColumnName(int c) { return COLS[c]; }
    @Override public Object getValueAt(int r, int c) { return DATA[r][c]; }
}