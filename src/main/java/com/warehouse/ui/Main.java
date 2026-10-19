package com.warehouse.ui;

import com.formdev.flatlaf.FlatLaf;
import com.warehouse.ui.theme.ThemeManager;

import javax.swing.*;

/**
 * Application entry point.
 * Launch on the EDT as required by Swing threading rules.
 */
public class Main {

    public static void main(String[] args) {
        // Register custom defaults BEFORE setting the look and feel
        FlatLaf.registerCustomDefaultsSource("themes");

        SwingUtilities.invokeLater(() -> {
            // ThemeManager handles detection + initial setup
            ThemeManager.getInstance().initialize();
            // Show the login window (Phase 1: Login is functional as a shell)
            new com.warehouse.ui.frames.LoginFrame().setVisible(true);
        });
    }
}
