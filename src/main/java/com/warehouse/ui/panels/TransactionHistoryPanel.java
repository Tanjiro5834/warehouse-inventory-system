package com.warehouse.ui.panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.warehouse.ui.components.SearchField;
import com.warehouse.ui.components.Toast;
import com.warehouse.ui.sampledata.TransactionTableModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.time.LocalDate;

/**
 * Transaction History: filters + sortable table + Export placeholder.
 * TODO: wire to backend
 */
public class TransactionHistoryPanel extends JPanel {

    public TransactionHistoryPanel() {
        setLayout(new MigLayout("fill, insets 20, gapy 12", "[grow,fill]", "[][grow]"));

        // ---------- Filter bar ----------
        JPanel filters = new JPanel(new MigLayout(
                "wrap 1, insets 16, gap 12",
                "[140!][grow,fill][140!][grow,fill][140!][grow,fill][grow,fill]",
                "[]"));
        filters.putClientProperty(FlatClientProperties.STYLE,
                "arc: 12; background: $Panel.background; border: 1,1,1,1,$Component.borderColor,10");

        JTextField from = new JTextField(LocalDate.now().minusMonths(1).toString());
        JTextField to = new JTextField(LocalDate.now().toString());
        JComboBox<String> type = new JComboBox<>(new String[]{"All Types", "Stock In", "Stock Out", "Checkout", "Return"});
        JComboBox<String> category = new JComboBox<>(new String[]{"All Categories", "Units", "Consumables", "Tools"});
        SearchField search = new SearchField(20);
        search.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search item, reference, or user…");

        JButton exportBtn = new JButton("Export");
        exportBtn.addActionListener(e -> {
            // TODO: wire to backend
            Toast.show(this, "Export not implemented yet (UI placeholder).");
        });

        filters.add(new JLabel("From"));
        filters.add(from);
        filters.add(new JLabel("To"));
        filters.add(to);
        filters.add(new JLabel("Type"));
        filters.add(type);
        filters.add(new JLabel("Category"));
        filters.add(category);
        filters.add(search, "span 4, growx");
        filters.add(new JLabel(""), "growx");
        filters.add(exportBtn, "right");
        add(filters, "growx");

        // ---------- Table ----------
        TransactionTableModel model = new TransactionTableModel();
        JTable table = new JTable(model);
        table.setRowHeight(UIManager.getInt("Table.rowHeight"));
        table.setAutoCreateRowSorter(true);
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        table.setFillsViewportHeight(true);
        table.putClientProperty(FlatClientProperties.STYLE, "showTrailingIcon: false;");
        table.setRowSorter(new TableRowSorter<>(model));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(3).setCellRenderer(center);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean focus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, v, sel, focus, row, col);
                if (!sel) c.setForeground(UIManager.getColor("Label.foreground"));
                // Color the Type cell
                if (col == 1 && !sel) {
                    String s = String.valueOf(v);
                    if ("Stock In".equals(s)) c.setForeground(UIManager.getColor("Inventory.inStockColor"));
                    else if ("Stock Out".equals(s)) c.setForeground(UIManager.getColor("Inventory.outOfStockColor"));
                }
                return c;
            }
        });

        JScrollPane sp = new JScrollPane(table);
        sp.putClientProperty(FlatClientProperties.STYLE, "arc: 8;");
        add(sp, "grow");
    }
}