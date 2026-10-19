package com.warehouse.ui.panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.warehouse.ui.components.Toast;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Reports screen: cards for each report type.
 * Clicking a card shows a placeholder dialog (no real report generation).
 * TODO: wire to backend
 */
public class ReportsPanel extends JPanel {

    public ReportsPanel() {
        setLayout(new MigLayout("fill, insets 24, gap 20", "[grow,fill][grow,fill][grow,fill]", "[grow,fill]"));
        setOpaque(false);

        add(reportCard("Stock Summary",
                "Totals per category and status.", "Open"), "grow");
        add(reportCard("Movement Report",
                "Stock in / out by date range.", "Generate"), "grow");
        add(reportCard("Low Stock Report",
                "All items at or below reorder level.", "View"), "grow");
    }

    private JPanel reportCard(String title, String subtitle, String buttonText) {
        JPanel card = new JPanel(new MigLayout(
                "fill, insets 20, gapy 10",
                "[grow,fill]",
                "[][grow][]"));
        card.putClientProperty(FlatClientProperties.STYLE,
                "arc: 12; background: $Panel.background; border: 1,1,1,1,$Component.borderColor,10");

        JLabel icon = new JLabel("▤");
        icon.setFont(icon.getFont().deriveFont(32f));
        icon.setForeground(UIManager.getColor("Inventory.accentColor"));

        JLabel t = new JLabel(title);
        t.putClientProperty(FlatClientProperties.STYLE_CLASS, "h3");

        JLabel s = new JLabel(subtitle);
        s.putClientProperty(FlatClientProperties.STYLE_CLASS, "small");
        s.setForeground(UIManager.getColor("Label.disabledForeground"));

        JButton b = new JButton(buttonText);
        b.putClientProperty(FlatClientProperties.STYLE_CLASS, "default");
        b.addActionListener(e -> {
            // TODO: wire to backend
            Toast.show(this, title + " – coming soon (UI placeholder).");
        });

        card.add(icon);
        card.add(t);
        card.add(s, "wrap");
        card.add(b, "bottom, right");
        return card;
    }
}