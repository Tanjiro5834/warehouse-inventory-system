package com.warehouse.ui.panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.warehouse.ui.components.FormRow;
import com.warehouse.ui.components.ItemGridPanel;
import com.warehouse.ui.components.SearchField;
import com.warehouse.ui.components.Toast;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * Stock In screen: header form + item grid + Save.
 * Serial field appears for Units (handled by ItemGridPanel).
 * TODO: wire to backend
 */
public class StockInPanel extends JPanel {

    public StockInPanel() {
        setLayout(new MigLayout("fill, wrap, insets 20, gapy 12", "[grow,fill]", "[][grow][]"));
        setOpaque(false);

        // ---------- Header ----------
        JPanel header = new JPanel(new MigLayout(
                "wrap 2, insets 16, gap 12",
                "[grow,fill][grow,fill]",
                "[]"));
        header.putClientProperty(FlatClientProperties.STYLE,
                "arc: 12; background: $Panel.background; border: 1,1,1,1,$Component.borderColor,1,12");

        JTextField date = new JTextField(LocalDate.now().toString());
        JTextField supplier = new JTextField();
        supplier.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Supplier name");
        JTextField drNumber = new JTextField();
        drNumber.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "e.g. DR-10234");
        JTextField receivedBy = new JTextField();
        receivedBy.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Receiver name");
        JTextField remarks = new JTextField();
        remarks.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Optional notes");

        header.add(new FormRow("Date", date));
        header.add(new FormRow("Supplier", supplier));
        header.add(new FormRow("DR Number", drNumber));
        header.add(new FormRow("Received By", receivedBy));
        header.add(new FormRow("Remarks", remarks), "span 2, growx");
        add(header, "growx");

        // ---------- Item grid ----------
        JPanel gridCard = new JPanel(new MigLayout("fill, insets 16, gapy 8", "[grow,fill]", "[][grow]"));
        gridCard.putClientProperty(FlatClientProperties.STYLE,
                "arc: 12; background: $Panel.background; border: 1,1,1,1,$Component.borderColor,1,12");

        JLabel gridTitle = new JLabel("Items");
        gridTitle.putClientProperty(FlatClientProperties.STYLE_CLASS, "h3");
        gridCard.add(gridTitle, "split 2, growx");

        SearchField itemSearch = new SearchField(18);
        itemSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Filter items…");
        gridCard.add(itemSearch, "right, w 220!, wrap");

        ItemGridPanel grid = new ItemGridPanel();
        gridCard.add(grid, "grow");

        add(gridCard, "grow");

        // ---------- Footer ----------
        JPanel footer = new JPanel(new MigLayout("insets 0, gap 8", "[grow,fill][][]", "[center]"));
        footer.setOpaque(false);

        JButton cancel = new JButton("Cancel");
        cancel.putClientProperty(FlatClientProperties.BUTTON_TYPE,
                FlatClientProperties.BUTTON_TYPE_TOOLBAR_BUTTON);
        cancel.addActionListener(e -> Toast.show(this, "Cancelled (UI placeholder)."));

        JButton save = new JButton("Save Stock In");
        save.putClientProperty(FlatClientProperties.STYLE_CLASS, "default");
        save.addActionListener(e -> {
            // TODO: wire to backend
            Toast.show(this, "Stock In recorded (UI placeholder).");
        });

        footer.add(new JLabel(""), "growx");
        footer.add(cancel);
        footer.add(save);
        add(footer, "growx");
    }
}