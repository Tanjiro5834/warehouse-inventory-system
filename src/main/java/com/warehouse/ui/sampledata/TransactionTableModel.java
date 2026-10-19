package com.warehouse.ui.sampledata;

import javax.swing.table.AbstractTableModel;

/**
 * Sample data for transaction history / recent transactions.
 * TODO: wire to backend
 */
public class TransactionTableModel extends AbstractTableModel {

    private static final String[] COLS = {
            "Date", "Type", "Item", "Qty", "Reference", "User"
    };

    private static final Object[][] DATA = {
            {"2024-10-14", "Stock In",  "Carrier Optima 1.5",      5, "DR-10231", "M. Reyes"},
            {"2024-10-14", "Stock Out", "PVC Tape (black)",        10, "JR-4402",  "J. Santos"},
            {"2024-10-13", "Stock In",  "R410A Refrigerant",       12, "DR-10230", "M. Reyes"},
            {"2024-10-13", "Stock Out", "Electric Drill",           1, "JR-4401",  "A. Cruz"},
            {"2024-10-12", "Stock Out", "Screws 3/8\" (100/pc)",   4, "JR-4400",  "R. Dela Cruz"},
            {"2024-10-12", "Stock In",  "Daikin FTKQ 1.0",          3, "DR-10229", "M. Reyes"},
            {"2024-10-11", "Stock Out", "R32 Refrigerant",          2, "JR-4399",  "J. Santos"},
    };

    @Override public int getRowCount() { return DATA.length; }
    @Override public int getColumnCount() { return COLS.length; }
    @Override public String getColumnName(int col) { return COLS[col]; }

    @Override
    public Object getValueAt(int row, int col) {
        return DATA[row][col];
    }
}