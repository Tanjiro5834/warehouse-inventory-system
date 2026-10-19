package com.warehouse.ui;

import com.formdev.flatlaf.FlatLaf;
import com.warehouse.db.Database;
import com.warehouse.ui.theme.ThemeManager;

import javax.swing.*;


public class Main {

    public static void main(String[] args) {
        FlatLaf.registerCustomDefaultsSource("themes");
        Database.init();
        SwingUtilities.invokeLater(() -> {
            ThemeManager.getInstance().initialize();
            new com.warehouse.ui.frames.LoginFrame().setVisible(true);
        });
    }
}
