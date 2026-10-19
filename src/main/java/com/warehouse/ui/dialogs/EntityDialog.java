package com.warehouse.ui.dialogs;

import com.formdev.flatlaf.FlatClientProperties;
import com.warehouse.ui.components.FormRow;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Generic Add/Edit dialog for simple entities (Suppliers, Technicians).
 * TODO: wire to backend (persist entity)
 */
public class EntityDialog extends JDialog {

    public EntityDialog(Window owner, String title, String[] labels, String[] placeholders) {
        super(owner, title, ModalityType.APPLICATION_MODAL);
        setLayout(new MigLayout("fill, insets 20, gapy 10", "[grow,fill]", "[]"));
        setMinimumSize(new Dimension(480, 120 + labels.length * 46));
        setLocationRelativeTo(owner);

        JPanel form = new JPanel(new MigLayout("wrap 1, insets 0, gapy 10", "[grow,fill]", "[]"));
        form.setOpaque(false);
        for (int i = 0; i < labels.length; i++) {
            JTextField f = new JTextField();
            f.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT,
                    placeholders != null && i < placeholders.length ? placeholders[i] : "");
            form.add(new FormRow(labels[i], f));
        }
        add(form, "grow, push");

        JPanel buttons = new JPanel(new MigLayout("insets 0, gap 8", "[grow][]", "[center]"));
        buttons.setOpaque(false);
        JButton cancel = new JButton("Cancel");
        JButton save = new JButton("Save");
        save.putClientProperty(FlatClientProperties.STYLE_CLASS, "default");
        cancel.addActionListener(e -> dispose());
        save.addActionListener(e -> {
            // TODO: wire to backend
            JOptionPane.showMessageDialog(this, "Saved (UI placeholder).");
            dispose();
        });
        buttons.add(new JLabel(""), "growx");
        buttons.add(cancel);
        buttons.add(save);
        add(buttons, "growx, gaptop 12");
    }
}