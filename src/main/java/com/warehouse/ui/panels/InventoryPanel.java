package com.warehouse.ui.panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.warehouse.ui.components.SearchField;
import com.warehouse.ui.components.StatusBadge;
import com.warehouse.ui.dialogs.ItemDialog;
import com.warehouse.ui.sampledata.ConsumableTableModel;
import com.warehouse.ui.sampledata.ToolTableModel;
import com.warehouse.ui.sampledata.UnitTableModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

/**
 * Inventory screen with tabs for Units / Consumables / Tools.
 * Tables are sortable; stock status is rendered as a StatusBadge.
 * TODO: wire to backend
 */
public class InventoryPanel extends JPanel {

    public InventoryPanel() {
        setLayout(new MigLayout("fill, insets 20", "[grow,fill]", "[grow,fill]"));

        JTabbedPane tabs = new JTabbedPane();
        tabs.putClientProperty(FlatClientProperties.STYLE, "tabType: card;");
        tabs.addTab("Units", buildUnitsTab());
        tabs.addTab("Consumables", buildConsumablesTab());
        tabs.addTab("Tools", buildToolsTab());
        add(tabs, "grow");
    }

    // ---------- Units ----------

    private JPanel buildUnitsTab() {
        JPanel p = new JPanel(new MigLayout("fill, insets 16, gapy 12", "[grow,fill]", "[][grow]"));
        p.setOpaque(false);

        JPanel toolbar = buildToolbar("Add Unit", "Serial number");
        p.add(toolbar, "growx");

        UnitTableModel model = new UnitTableModel();
        JTable table = buildTable(model);
        // Render the last column (status) as a badge
        table.getColumnModel().getColumn(5).setCellRenderer(new StatusBadgeRenderer());
        // Center the capacity column
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(2).setCellRenderer(center);

        p.add(scroll(table), "grow");
        return p;
    }

    // ---------- Consumables ----------

    private JPanel buildConsumablesTab() {
        JPanel p = new JPanel(new MigLayout("fill, insets 16, gapy 12", "[grow,fill]", "[][grow]"));
        p.setOpaque(false);

        p.add(buildToolbar("Add Consumable", "Item name"), "growx");

        ConsumableTableModel model = new ConsumableTableModel();
        JTable table = buildTable(model);
        table.getColumnModel().getColumn(4).setCellRenderer(new StatusBadgeRenderer());

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(1).setCellRenderer(center);
        table.getColumnModel().getColumn(3).setCellRenderer(center);

        p.add(scroll(table), "grow");
        return p;
    }

    // ---------- Tools ----------

    private JPanel buildToolsTab() {
        JPanel p = new JPanel(new MigLayout("fill, insets 16, gapy 12", "[grow,fill]", "[][grow]"));
        p.setOpaque(false);

        p.add(buildToolbar("Add Tool", "Tool name"), "growx");

        ToolTableModel model = new ToolTableModel();
        JTable table = buildTable(model);
        table.getColumnModel().getColumn(2).setCellRenderer(new ToolStatusRenderer());

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(1).setCellRenderer(center);

        p.add(scroll(table), "grow");
        return p;
    }

    // ---------- Shared helpers ----------

    private JPanel buildToolbar(String addLabel, String placeholder) {
        JPanel bar = new JPanel(new MigLayout("insets 0, gap 8", "[][][grow,fill][]", "[center]"));
        bar.setOpaque(false);

        SearchField search = new SearchField(20);
        search.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search " + placeholder.toLowerCase() + "…");

        JComboBox<String> filter = new JComboBox<>(new String[]{"All Status", "In Stock", "Low Stock", "Out of Stock"});

        JButton addBtn = new JButton(addLabel);
        addBtn.putClientProperty(FlatClientProperties.STYLE_CLASS, "default");
        addBtn.addActionListener(e -> {
            // TODO: wire to backend (persist new item)
            new ItemDialog(SwingUtilities.getWindowAncestor(this)).setVisible(true);
        });

        bar.add(search);
        bar.add(filter);
        bar.add(new JLabel(""), "growx");
        bar.add(addBtn);
        return bar;
    }

    private JTable buildTable(AbstractTableModel model) {
        JTable table = new JTable(model);
        table.setRowHeight(UIManager.getInt("Table.rowHeight"));
        table.setAutoCreateRowSorter(true);
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        table.setFillsViewportHeight(true);
        table.putClientProperty(FlatClientProperties.STYLE, "showTrailingIcon: false;");

        TableRowSorter<AbstractTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(header.getWidth(), 34));

        // Column widths (Units)
        if (model instanceof UnitTableModel) {
            setCol(table, 0, 100);
            setCol(table, 1, 120);
            setCol(table, 2, 100);
            setCol(table, 3, 100);
            setCol(table, 4, 140);
            setCol(table, 5, 120);
        } else if (model instanceof ConsumableTableModel) {
            setCol(table, 0, 200);
            setCol(table, 1, 90);
            setCol(table, 2, 80);
            setCol(table, 3, 110);
            setCol(table, 4, 120);
        } else if (model instanceof ToolTableModel) {
            setCol(table, 0, 200);
            setCol(table, 1, 90);
            setCol(table, 2, 120);
            setCol(table, 3, 160);
        }

        return table;
    }

    private void setCol(JTable t, int idx, int w) {
        TableColumn c = t.getColumnModel().getColumn(idx);
        c.setPreferredWidth(w);
    }

    private JScrollPane scroll(JTable table) {
        JScrollPane sp = new JScrollPane(table);
        sp.putClientProperty(FlatClientProperties.STYLE, "arc: 8;");
        return sp;
    }

    /** Renders the last column value ("IN_STOCK"/"LOW_STOCK"/"OUT_OF_STOCK") as a StatusBadge. */
    private static class StatusBadgeRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(
                JTable t, Object v, boolean sel, boolean focus, int row, int col) {
            String key = String.valueOf(v);
            StatusBadge badge;
            switch (key) {
                case "IN_STOCK":     badge = StatusBadge.inStock(); break;
                case "LOW_STOCK":    badge = StatusBadge.lowStock(); break;
                case "OUT_OF_STOCK": badge = StatusBadge.outOfStock(); break;
                default:             badge = StatusBadge.neutral(key);
            }
            JPanel wrap = new JPanel(new MigLayout("insets 0 6 0 6, fill", "[center]", "[center]"));
            wrap.setOpaque(sel);
            if (sel) wrap.setBackground(t.getSelectionBackground());
            wrap.add(badge);
            return wrap;
        }
    }

    /** Renders tool status text with color coding. */
    private static class ToolStatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(
                JTable t, Object v, boolean sel, boolean focus, int row, int col) {
            Component c = super.getTableCellRendererComponent(t, v, sel, focus, row, col);
            if (!sel) {
                String s = String.valueOf(v);
                Color fg = UIManager.getColor("Label.foreground");
                switch (s) {
                    case "Available":    fg = UIManager.getColor("Inventory.inStockColor"); break;
                    case "Checked Out":  fg = UIManager.getColor("Inventory.lowStockColor"); break;
                    case "Under Repair": fg = UIManager.getColor("Label.disabledForeground"); break;
                    case "Lost":         fg = UIManager.getColor("Inventory.outOfStockColor"); break;
                }
                c.setForeground(fg);
            }
            return c;
        }
    }
}