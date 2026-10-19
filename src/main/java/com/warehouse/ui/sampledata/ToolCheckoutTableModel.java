package com.warehouse.ui.sampledata;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Sample data for tool checkout/return.
 * Overdue = days out > 14 (static rule, UI-only).
 * TODO: wire to backend
 */
public class ToolCheckoutTableModel extends AbstractTableModel {

    private static final String[] COLS = {
            "Tool", "Holder", "Date Out", "Days Out", "Status"
    };

    // Each row: { tool, holder, dateOut(ISO), status }
    private static final Object[][] DATA = {
            {"Electric Drill",      "M. Reyes",      "2024-10-01", "Checked Out"},
            {"Vacuum Pump",         "J. Santos",     "2024-09-20", "Checked Out"}, // overdue
            {"Tube Cutter",         "A. Cruz",       "2024-10-10", "Checked Out"},
            {"Nut Driver Set",      "R. Dela Cruz",  "2024-09-28", "Checked Out"}, // overdue
            {"Torque Wrench",       "—",             "2024-10-05", "Available"},
            {"Manifold Gauge Set",  "—",             "2024-10-08", "Available"},
    };

    @Override public int getRowCount() { return DATA.length; }
    @Override public int getColumnCount() { return COLS.length; }
    @Override public String getColumnName(int c) { return COLS[c]; }

    @Override
    public Object getValueAt(int r, int c) {
        Object[] row = DATA[r];
        switch (c) {
            case 0: return row[0];
            case 1: return row[1];
            case 2: return row[2];
            case 3: {
                if ("—".equals(row[1])) return 0;
                LocalDate out = LocalDate.parse((String) row[2]);
                return (int) ChronoUnit.DAYS.between(out, LocalDate.now());
            }
            case 4: return row[3];
            default: return "";
        }
    }

    public boolean isOverdue(int row) {
        Object[] r = DATA[row];
        if ("—".equals(r[1])) return false;
        LocalDate out = LocalDate.parse((String) r[2]);
        return ChronoUnit.DAYS.between(out, LocalDate.now()) > 14;
    }
}