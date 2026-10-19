package com.warehouse.ui.components;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Multi-row item grid with add/remove.
 * Shows a serial field only when the selected item is a "Unit".
 * Visual only; no calculations or persistence.
 * TODO: wire to backend (persist stock in/out rows)
 */
public class ItemGridPanel extends JPanel {

    public static final String[] ITEMS = {
            // Units
            "Carrier Optima 1.5 (AC Unit)",
            "Daikin FTKQ 1.0 (AC Unit)",
            "Panasonic CS-PN 1.0 (AC Unit)",
            "LG Hercules 2.0 (AC Unit)",
            // Consumables
            "PVC Tape (black)",
            "R410A Refrigerant",
            "R32 Refrigerant",
            "Screws 3/8\" (100/pc)",
            // Tools
            "Torque Wrench",
            "Electric Drill",
            "Manifold Gauge Set"
    };

    private final JPanel rowsContainer = new JPanel(
            new MigLayout("wrap 1, insets 0, gapy 6", "[grow,fill]", "[]"));

    public ItemGridPanel() {
        setLayout(new MigLayout("fill, insets 0, gapy 8", "[grow,fill]", "[][grow]"));
        setOpaque(false);

        rowsContainer.setOpaque(false);

        JScrollPane scroll = new JScrollPane(rowsContainer);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setOpaque(false);
        scroll.putClientProperty(FlatClientProperties.STYLE, "arc: 8;");
        add(scroll, "grow, push");

        JButton addRow = new JButton("+ Add Item Row");
        addRow.putClientProperty(FlatClientProperties.BUTTON_TYPE,
                FlatClientProperties.BUTTON_TYPE_TOOLBAR_BUTTON);
        addRow.addActionListener(e -> addRow());
        add(addRow, "left");

        // Start with two rows
        addRow();
        addRow();
    }

    private void addRow() {
        rowsContainer.add(buildRow(), "growx");
        rowsContainer.revalidate();
        rowsContainer.repaint();
    }

    private JPanel buildRow() {
        JPanel row = new JPanel(new MigLayout(
                "insets 8, gap 8",
                "[grow,fill,240!][90!][180!][][][]",
                "[center]"));
        row.putClientProperty(FlatClientProperties.STYLE,
                "arc: 8; background: $Panel.background; border: 1,1,1,1,$Component.borderColor,8");

        JComboBox<String> item = new JComboBox<>(ITEMS);
        item.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Select item");

        JSpinner qty = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
        qty.setPreferredSize(new Dimension(80, 28));

        JTextField serial = new JTextField();
        serial.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Serial no.");

        // Serial field visible only for units
        Runnable updateSerial = () -> {
            String sel = (String) item.getSelectedItem();
            boolean isUnit = sel != null && sel.contains("(AC Unit)");
            serial.setVisible(isUnit);
            row.revalidate();
            row.repaint();
        };
        item.addActionListener(e -> updateSerial.run());
        updateSerial.run();

        JButton remove = new JButton("✕");
        remove.putClientProperty(FlatClientProperties.BUTTON_TYPE,
                FlatClientProperties.BUTTON_TYPE_TOOLBAR_BUTTON);
        remove.setToolTipText("Remove row");
        remove.addActionListener(e -> {
            Container parent = row.getParent();
            parent.remove(row);
            parent.revalidate();
            parent.repaint();
        });

        row.add(item, "growx");
        row.add(qty);
        row.add(serial, "growx");
        row.add(new JLabel(""), "growx");
        row.add(remove);
        return row;
    }

    public List<Component> getRows() {
        List<Component> list = new ArrayList<>();
        for (Component c : rowsContainer.getComponents()) list.add(c);
        return list;
    }
}