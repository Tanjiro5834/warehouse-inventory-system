package com.warehouse.ui.components;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * KPI stat card used on the Dashboard.
 * Colors come from UIManager keys only (no hardcoded colors).
 */
public class StatCard extends JPanel {

    private final JLabel valueLabel = new JLabel("0");
    private final JLabel subtitleLabel = new JLabel(" ");

    public StatCard(String title, String value, String subtitle, String statusKey) {
        setLayout(new MigLayout("wrap 1, insets 18 20 18 20, gapy 6", "[grow,fill]", "[]"));
        putClientProperty(FlatClientProperties.STYLE,
                "arc: 12; background: $Panel.background; border: 1,1,1,1,$Component.borderColor,10");

        JLabel titleLabel = new JLabel(title);
        titleLabel.putClientProperty(FlatClientProperties.STYLE_CLASS, "small");
        titleLabel.setForeground(UIManager.getColor("Label.disabledForeground"));

        valueLabel.setText(value);
        valueLabel.putClientProperty(FlatClientProperties.STYLE_CLASS, "h1");
        if (statusKey != null) {
            valueLabel.setForeground(UIManager.getColor(statusKey));
        }

        subtitleLabel.setText(subtitle);
        subtitleLabel.putClientProperty(FlatClientProperties.STYLE_CLASS, "small");
        subtitleLabel.setForeground(UIManager.getColor("Label.disabledForeground"));

        add(titleLabel);
        add(valueLabel);
        add(subtitleLabel);
    }

    public void setValue(String value) { valueLabel.setText(value); }
    public void setSubtitle(String subtitle) { subtitleLabel.setText(subtitle); }
}