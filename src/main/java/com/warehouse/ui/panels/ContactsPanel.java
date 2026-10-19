package com.warehouse.ui.panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.warehouse.ui.components.SearchField;
import com.warehouse.ui.dialogs.EntityDialog;
import com.warehouse.ui.sampledata.SupplierTableModel;
import com.warehouse.ui.sampledata.TechnicianTableModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;

/**
 * Suppliers and Technicians screen with two tabs.
 * TODO: wire to backend
 */
public class ContactsPanel extends JPanel {

    public ContactsPanel() {
        setLayout(new MigLayout("fill, insets 20", "[grow,fill]", "[grow,fill]"));
        JTabbedPane tabs = new JTabbedPane();
        tabs.putClientProperty(FlatClientProperties.STYLE, "tabType: card;");
        tabs.addTab("Suppliers", buildSuppliersTab());
        tabs.addTab("Technicians", buildTechniciansTab());
        add(tabs, "grow");
    }

    private JPanel buildSuppliersTab() {
        JPanel p = new JPanel(new MigLayout("fill, insets 16, gapy 12", "[grow,fill]", "[][grow]"));
        p.setOpaque(false);

        JPanel bar = new JPanel(new MigLayout("insets 0, gap 8", "[][grow,fill][]", "[center]"));
        bar.setOpaque(false);
        SearchField search = new SearchField(20);
        search.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search suppliers…");
        JButton add = new JButton("Add Supplier");
        add.putClientProperty(FlatClientProperties.STYLE_CLASS, "default");
        add.addActionListener(e -> new EntityDialog(
                SwingUtilities.getWindowAncestor(this),
                "Add Supplier",
                new String[]{"Name", "Contact Person", "Phone", "Email", "Address"},
                new String[]{"Supplier name", "Contact person", "Phone", "Email", "Address"}
        ).setVisible(true));
        bar.add(search);
        bar.add(new JLabel(""), "growx");
        bar.add(add);
        p.add(bar, "growx");

        SupplierTableModel model = new SupplierTableModel();
        JTable table = buildBaseTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.putClientProperty(FlatClientProperties.STYLE, "arc: 8;");
        p.add(sp, "grow");
        return p;
    }

    private JPanel buildTechniciansTab() {
        JPanel p = new JPanel(new MigLayout("fill, insets 16, gapy 12", "[grow,fill]", "[][grow]"));
        p.setOpaque(false);

        JPanel bar = new JPanel(new MigLayout("insets 0, gap 8", "[][grow,fill][]", "[center]"));
        bar.setOpaque(false);
        SearchField search = new SearchField(20);
        search.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search technicians…");
        JButton add = new JButton("Add Technician");
        add.putClientProperty(FlatClientProperties.STYLE_CLASS, "default");
        add.addActionListener(e -> new EntityDialog(
                SwingUtilities.getWindowAncestor(this),
                "Add Technician",
                new String[]{"Name", "Specialty", "Phone"},
                new String[]{"Full name", "e.g. Split / Inverter AC", "Phone"}
        ).setVisible(true));
        bar.add(search);
        bar.add(new JLabel(""), "growx");
        bar.add(add);
        p.add(bar, "growx");

        TechnicianTableModel model = new TechnicianTableModel();
        JTable table = buildBaseTable(model);
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(3).setCellRenderer(center);

        JScrollPane sp = new JScrollPane(table);
        sp.putClientProperty(FlatClientProperties.STYLE, "arc: 8;");
        p.add(sp, "grow");
        return p;
    }

    private JTable buildBaseTable(AbstractTableModel model) {
        JTable table = new JTable(model);
        table.setRowHeight(UIManager.getInt("Table.rowHeight"));
        table.setAutoCreateRowSorter(true);
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        table.setFillsViewportHeight(true);
        table.putClientProperty(FlatClientProperties.STYLE, "showTrailingIcon: false;");
        table.setRowSorter(new TableRowSorter<>(model));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean focus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, v, sel, focus, row, col);
                if (!sel) c.setForeground(UIManager.getColor("Label.foreground"));
                return c;
            }
        });

        // Double-click opens an edit dialog
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    // TODO: wire to backend (load record, prefill)
                    new EntityDialog(
                            SwingUtilities.getWindowAncestor(table),
                            "Edit Record",
                            new String[]{"Field 1", "Field 2", "Field 3"},
                            new String[]{"Value", "Value", "Value"}
                    ).setVisible(true);
                }
            }
        });

        return table;
    }
}