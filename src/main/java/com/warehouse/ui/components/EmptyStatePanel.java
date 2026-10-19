package com.warehouse.ui.components;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * Empty-state placeholder shown when a table has no rows.
 */
public class EmptyStatePanel extends JPanel {

    public EmptyStatePanel(String title, String subtitle) {
        setLayout(new MigLayout("fill, insets 40", "[grow,center]", "[grow,center]"));
        JPanel inner = new JPanel(new MigLayout("wrap 1, insets 0, gapy 6", "[center]", "[]"));

        JLabel glyph = new JLabel("▢");
        glyph.setFont(glyph.getFont().deriveFont(48f));
        glyph.setForeground(UIManager.getColor("Label.disabledForeground"));

        JLabel t = new JLabel(title);
        t.putClientProperty(FlatClientProperties.STYLE_CLASS, "h3");
        JLabel s = new JLabel(subtitle);
        s.putClientProperty(FlatClientProperties.STYLE_CLASS, "small");
        s.setForeground(UIManager.getColor("Label.disabledForeground"));

        inner.add(glyph);
        inner.add(t);
        inner.add(s);
        add(inner);
    }
}