package com.warehouse.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Rounded status pill: e.g. "In Stock" (green), "Low Stock" (amber), "Out of Stock" (red).
 * Colors resolved from UIManager keys (Inventory.inStockColor etc.) and stay readable in both themes.
 */
public class StatusBadge extends JLabel {

    public enum Status { IN_STOCK, LOW_STOCK, OUT_OF_STOCK, NEUTRAL }

    private Status status = Status.NEUTRAL;
    private String text;

    public StatusBadge(String text, Status status) {
        super(text, SwingConstants.CENTER);
        this.text = text;
        this.status = status;
        setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
        setFont(getFont().deriveFont(Font.BOLD, 11f));
        setOpaque(false);
    }

    public static StatusBadge inStock()   { return new StatusBadge("In Stock", Status.IN_STOCK); }
    public static StatusBadge lowStock()  { return new StatusBadge("Low Stock", Status.LOW_STOCK); }
    public static StatusBadge outOfStock(){ return new StatusBadge("Out of Stock", Status.OUT_OF_STOCK); }
    public static StatusBadge neutral(String t) { return new StatusBadge(t, Status.NEUTRAL); }

    private Color resolveBackground() {
        switch (status) {
            case IN_STOCK:    return UIManager.getColor("Inventory.inStockColor");
            case LOW_STOCK:   return UIManager.getColor("Inventory.lowStockColor");
            case OUT_OF_STOCK:return UIManager.getColor("Inventory.outOfStockColor");
            default:          return UIManager.getColor("Component.borderColor");
        }
    }

    private Color resolveForeground() {
        Color bg = resolveBackground();
        if (bg == null) return UIManager.getColor("Label.foreground");
        // Luminance check: pick readable text color
        double lum = (0.299 * bg.getRed() + 0.587 * bg.getGreen() + 0.114 * bg.getBlue()) / 255.0;
        return lum > 0.6 ? Color.BLACK : Color.WHITE;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color bg = resolveBackground();
        if (bg != null) {
            g2.setColor(bg);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), getHeight(), getHeight()));
        }
        g2.dispose();
        setForeground(resolveForeground());
        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        d.height = Math.max(d.height + 2, 22);
        return d;
    }
}