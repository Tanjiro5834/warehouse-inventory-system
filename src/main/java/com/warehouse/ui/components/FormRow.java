package com.warehouse.ui.components;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * A label + component row for form layouts in dialogs.
 */
public class FormRow extends JPanel {

    public FormRow(String label, JComponent field) {
        setLayout(new MigLayout("insets 0, gap 12", "[140!][grow,fill]", "[center]"));
        JLabel l = new JLabel(label);
        l.setForeground(UIManager.getColor("Label.foreground"));
        add(l);
        add(field, "growx, h 30!");
    }
}