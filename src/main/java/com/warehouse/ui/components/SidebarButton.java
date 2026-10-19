package com.warehouse.ui.components;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;

/**
 * Sidebar navigation button: icon + label, full-width, left-aligned.
 * Visual only; navigation is wired by the MainFrame.
 */
public class SidebarButton extends JButton {

    public SidebarButton(String text, String svgIconPath) {
        super(text);
        setHorizontalAlignment(SwingConstants.LEFT);
        setIconTextGap(12);
        setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        setFocusPainted(false);
        // Flat (toolbar-style) button: no grey box, hover/press handled by FlatLaf
        putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_TOOLBAR_BUTTON);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Icon recolors per theme via FlatSVGIcon + UIManager key
        // The SVG should use #000000 as placeholder; FlatSVGIcon.ColorFilter maps it.
        // TODO: wire to backend (no backend for icons; this is UI-only)
        // FlatSVGIcon never throws on a missing file; it paints a red box instead.
        // So check the classpath explicitly before using it.
        if (svgIconPath != null && SidebarButton.class.getClassLoader().getResource(svgIconPath) != null) {
            try {
                FlatSVGIcon icon = new FlatSVGIcon(svgIconPath, 18, 18);
                // Map black placeholder to the button foreground color (theme-aware)
                icon.setColorFilter(new FlatSVGIcon.ColorFilter(
                        color -> UIManager.getColor("Button.foreground")));
                setIcon(icon);
            } catch (Exception ex) {
                // Icon resource missing: fall back to text-only
                System.err.println("Icon not found: " + svgIconPath);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        boolean selected = getModel().isSelected();
        if (selected) {
            // Selected background (JButton itself has no "selected" look)
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color bg = UIManager.getColor("Button.toolbar.selectedBackground");
            g2.setColor(bg != null ? bg : UIManager.getColor("Component.borderColor"));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            g2.dispose();
        }
        super.paintComponent(g);
        // Accent bar on the left for the selected page
        if (selected) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(UIManager.getColor("Inventory.accentColor"));
            g2.fillRoundRect(0, 6, 4, getHeight() - 12, 4, 4);
            g2.dispose();
        }
    }
}