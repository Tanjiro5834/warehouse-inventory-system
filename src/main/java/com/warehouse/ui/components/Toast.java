package com.warehouse.ui.components;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Simple toast notification that auto-dismisses.
 * Visual only; safe to call from any screen.
 */
public final class Toast {

    private Toast() {}

    public static void show(Component parent, String message) {
        Window owner = SwingUtilities.getWindowAncestor(parent);
        if (owner == null) return;

        JWindow toast = new JWindow(owner);
        JLabel label = new JLabel(message);
        label.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        label.setOpaque(true);
        label.setBackground(UIManager.getColor("Panel.background"));
        label.setForeground(UIManager.getColor("Label.foreground"));
        label.putClientProperty(FlatClientProperties.STYLE, "arc: 20;");

        toast.getContentPane().add(label);
        toast.pack();

        // Position centered horizontally, 80px above bottom of owner
        Point ownerLoc = owner.getLocationOnScreen();
        Dimension ownerSize = owner.getSize();
        Dimension toastSize = toast.getSize();
        int x = ownerLoc.x + (ownerSize.width - toastSize.width) / 2;
        int y = ownerLoc.y + ownerSize.height - toastSize.height - 80;
        toast.setLocation(x, y);
        toast.setVisible(true);

        // Auto-dismiss after 2s
        new Timer(2000, e -> {
            toast.dispose();
        }) {{ setRepeats(false); }}.start();
    }
}