package com.warehouse.ui.components;

import com.warehouse.ui.theme.ThemeManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * ComboBox with System | Light | Dark options.
 * Fully functional: changes ThemeManager mode (persisted).
 */
public class ThemeSelectorComboBox extends JComboBox<ThemeManager.Mode> {

    public ThemeSelectorComboBox() {
        super(ThemeManager.Mode.values());
        setSelectedItem(ThemeManager.getInstance().getMode());
        setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value == ThemeManager.Mode.SYSTEM) setText("System");
                else if (value == ThemeManager.Mode.LIGHT) setText("Light");
                else setText("Dark");
                return this;
            }
        });
        addActionListener((ActionEvent e) -> {
            ThemeManager.Mode selected = (ThemeManager.Mode) getSelectedItem();
            if (selected != null) {
                ThemeManager.getInstance().setMode(selected);
            }
        });
        // Keep in sync if the theme changes elsewhere (e.g., Settings)
        ThemeManager.getInstance().addThemeChangeListener(() -> {
            ThemeManager.Mode m = ThemeManager.getInstance().getMode();
            if (getSelectedItem() != m) {
                SwingUtilities.invokeLater(() -> setSelectedItem(m));
            }
        });
    }
}