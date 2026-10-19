package com.warehouse.ui.panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.warehouse.ui.components.FormRow;
import com.warehouse.ui.components.ThemeSelectorComboBox;
import com.warehouse.ui.components.Toast;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * Settings screen.
 * Theme selector is fully functional (delegates to ThemeManager).
 * Company info and reorder level are UI-only.
 * TODO: wire to backend
 */
public class SettingsPanel extends JPanel {

    public SettingsPanel() {
        setLayout(new MigLayout("fill, insets 24, gapy 16", "[grow,fill]", "[][][grow]"));

        add(section("Appearance", appearanceForm()), "growx");
        add(section("Company Information", companyForm()), "growx");
        add(section("Inventory Defaults", defaultsForm()), "growx, push");
    }

    private JPanel section(String title, JPanel content) {
        JPanel wrap = new JPanel(new MigLayout("wrap 1, insets 20, gapy 12", "[grow,fill]", "[]"));
        wrap.putClientProperty(FlatClientProperties.STYLE,
                "arc: 12; background: $Panel.background; border: 1,1,1,1,$Component.borderColor,10");
        JLabel t = new JLabel(title);
        t.putClientProperty(FlatClientProperties.STYLE_CLASS, "h3");
        wrap.add(t);
        wrap.add(content);
        return wrap;
    }

    private JPanel appearanceForm() {
        JPanel form = new JPanel(new MigLayout("insets 0, gap 12", "[140!][grow,fill]", "[center]"));
        form.setOpaque(false);

        // Theme selector is functional
        ThemeSelectorComboBox themeCombo = new ThemeSelectorComboBox();
        themeCombo.setPreferredSize(new java.awt.Dimension(220, 30));

        JCheckBox followSystem = new JCheckBox("Follow OS theme changes live", true);
        followSystem.setEnabled(false); // since ThemeManager already polls

        form.add(new JLabel("Theme"));
        form.add(themeCombo, "wrap");
        form.add(new JLabel(""));
        form.add(followSystem);
        return form;
    }

    private JPanel companyForm() {
        JPanel form = new JPanel(new MigLayout("wrap 1, insets 0, gapy 8", "[grow,fill]", "[]"));
        form.setOpaque(false);
        form.add(new FormRow("Company Name", new JTextField("ACME HVAC Services Inc.")));
        form.add(new FormRow("Address", new JTextField("123 Industrial Ave., Quezon City")));
        form.add(new FormRow("Phone", new JTextField("(02) 8555-1234")));
        form.add(new FormRow("Email", new JTextField("ops@acmehvac.ph")));

        JPanel actions = new JPanel(new MigLayout("insets 0, gap 8", "[grow,fill][]", "[center]"));
        actions.setOpaque(false);
        JButton save = new JButton("Save Company Info");
        save.putClientProperty(FlatClientProperties.STYLE_CLASS, "default");
        save.addActionListener(e -> {
            // TODO: wire to backend
            Toast.show(this, "Company info saved (UI placeholder).");
        });
        actions.add(new JLabel(""), "growx");
        actions.add(save);
        form.add(actions);
        return form;
    }

    private JPanel defaultsForm() {
        JPanel form = new JPanel(new MigLayout("wrap 1, insets 0, gapy 8", "[grow,fill]", "[]"));
        form.setOpaque(false);
        form.add(new FormRow("Default Reorder Level", new JTextField("10")));

        JPanel actions = new JPanel(new MigLayout("insets 0, gap 8", "[grow,fill][]", "[center]"));
        actions.setOpaque(false);
        JButton save = new JButton("Save Defaults");
        save.putClientProperty(FlatClientProperties.STYLE_CLASS, "default");
        save.addActionListener(e -> {
            // TODO: wire to backend
            Toast.show(this, "Defaults saved (UI placeholder).");
        });
        actions.add(new JLabel(""), "growx");
        actions.add(save);
        form.add(actions);
        return form;
    }
}