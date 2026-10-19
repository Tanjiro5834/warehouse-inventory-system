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
 * Stock Out screen: header form + item grid + Save.
 * TODO: wire to backend
 */
public class StockOutPanel extends JPanel {

    public StockOutPanel() {
        setLayout(new MigLayout("fill, insets 20, gapy 12", "[grow,fill]", "[][grow][]"));
        setOpaque(false);

        // ---------- Header ----------
        JPanel header = new JPanel(new MigLayout(
                "wrap 2, insets 16, gap 12",
                "[140!][grow,fill][140!][grow,fill]",
                "[]"));
        header.putClientProperty(FlatClientProperties.STYLE,
                "arc: 12; background: $Panel.background; border: 1,1,1,1,$Component.borderColor,10");

        JTextField date = new JTextField(LocalDate.now().toString());
        JTextField issuedTo = new JTextField();
        issuedTo.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Technician name");
        JTextField jobRef = new JTextField();
        jobRef.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "e.g. JR-4403");
        JTextField releasedBy = new JTextField();
        releasedBy.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Releaser name");
        JTextField remarks = new JTextField();
        remarks.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Optional notes");

        header.add(new FormRow("Date", date));
        header.add(new FormRow("Issued To", issuedTo));
        header.add(new FormRow("Job Reference", jobRef));
        header.add(new FormRow("Released By", releasedBy));
        header.add(new FormRow("Remarks", remarks), "span 2, growx");
        add(header, "growx");

        // ---------- Item grid ----------
        JPanel gridCard = new JPanel(new MigLayout("fill, insets 16, gapy 8", "[grow,fill]", "[][grow]"));
        gridCard.putClientProperty(FlatClientProperties.STYLE,
                "arc: 12; background: $Panel.background; border: 1,1,1,1,$Component.borderColor,10");

        JLabel gridTitle = new JLabel("Items");
        gridTitle.putClientProperty(FlatClientProperties.STYLE_CLASS, "h3");
        gridCard.add(gridTitle);

        SearchField itemSearch = new SearchField(18);
        itemSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Filter items…");
        gridCard.add(itemSearch, "right");

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

        JButton save = new JButton("Save Stock Out");
        save.putClientProperty(FlatClientProperties.STYLE_CLASS, "default");
        save.addActionListener(e -> {
            // TODO: wire to backend
            Toast.show(this, "Stock Out recorded (UI placeholder).");
        });

        footer.add(new JLabel(""), "growx");
        footer.add(cancel);
        footer.add(save);
        add(footer, "growx");
    }
}