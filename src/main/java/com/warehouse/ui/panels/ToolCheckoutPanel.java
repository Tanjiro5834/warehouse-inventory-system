package com.warehouse.ui.panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.warehouse.ui.components.SearchField;
import com.warehouse.ui.components.Toast;
import com.warehouse.ui.sampledata.ToolCheckoutTableModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

/**
 * Tool Checkout / Return screen.
 * Overdue rows (days out > 14) are highlighted with the low-stock color.
 * TODO: wire to backend
 */
public class ToolCheckoutPanel extends JPanel {

    public ToolCheckoutPanel() {
        setLayout(new MigLayout("fill, insets 20, gapy 12", "[grow,fill]", "[][grow]"));

        // ---------- Toolbar ----------
        JPanel toolbar = new JPanel(new MigLayout("insets 0, gap 8", "[][][grow,fill][][]", "[center]"));
        toolbar.setOpaque(false);

        SearchField search = new SearchField(20);
        search.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search tools or holders…");

        JComboBox<String> status = new JComboBox<>(new String[]{"All", "Checked Out", "Available", "Overdue"});

        JButton checkOut = new JButton("Check Out");
        checkOut.addActionListener(e -> {
            // TODO: wire to backend
            Toast.show(this, "Open Check Out dialog (placeholder).");
        });

        JButton returnBtn = new JButton("Return");
        returnBtn.putClientProperty(FlatClientProperties.STYLE_CLASS, "default");
        returnBtn.addActionListener(e -> {
            // TODO: wire to backend
            Toast.show(this, "Tool returned (UI placeholder).");
        });

        toolbar.add(search);
        toolbar.add(status);
        toolbar.add(new JLabel(""), "growx");
        toolbar.add(checkOut);
        toolbar.add(returnBtn);
        add(toolbar, "growx");

        // ---------- Table ----------
        ToolCheckoutTableModel model = new ToolCheckoutTableModel();
        JTable table = new JTable(model);
        table.setRowHeight(UIManager.getInt("Table.rowHeight"));
        table.setAutoCreateRowSorter(true);
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        table.setFillsViewportHeight(true);
        table.putClientProperty(FlatClientProperties.STYLE, "showTrailingIcon: false;");
        table.setRowSorter(new TableRowSorter<>(model));

        table.setDefaultRenderer(Object.class, new OverdueRenderer(model));
        // Center the days-out column
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(3).setCellRenderer(new OverdueRenderer(model, true));

        JScrollPane sp = new JScrollPane(table);
        sp.putClientProperty(FlatClientProperties.STYLE, "arc: 8;");
        add(sp, "grow");
    }

    /** Renders the whole row; overdue rows get a subtle amber background + bold text. */
    private static class OverdueRenderer extends DefaultTableCellRenderer {
        private final ToolCheckoutTableModel model;
        private final boolean center;

        OverdueRenderer(ToolCheckoutTableModel m) { this(m, false); }
        OverdueRenderer(ToolCheckoutTableModel m, boolean center) {
            this.model = m;
            this.center = center;
            if (center) setHorizontalAlignment(SwingConstants.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable t, Object v, boolean sel, boolean focus, int row, int col) {
            Component c = super.getTableCellRendererComponent(t, v, sel, focus, row, col);
            int modelRow = t.convertRowIndexToModel(row);
            boolean overdue = model.isOverdue(modelRow);
            if (!sel) {
                if (overdue) {
                    Color bg = UIManager.getColor("Inventory.lowStockColor");
                    c.setBackground(alpha(bg, 30));
                    c.setForeground(UIManager.getColor("Label.foreground"));
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                } else {
                    c.setBackground(t.getBackground());
                    c.setForeground(UIManager.getColor("Label.foreground"));
                    c.setFont(c.getFont().deriveFont(Font.PLAIN));
                }
            }
            return c;
        }

        private static Color alpha(Color c, int a) {
            if (c == null) return new Color(249, 168, 37, a);
            return new Color(c.getRed(), c.getGreen(), c.getBlue(), a);
        }
    }
}