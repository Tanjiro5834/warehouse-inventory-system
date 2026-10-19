package com.warehouse.ui.components;

import com.warehouse.ui.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Simple two-series bar chart drawn with Graphics2D.
 * Uses static sample data; colors resolved from UIManager per theme.
 * Repaints automatically when the theme changes.
 */
public class StockChart extends JComponent {

    // Sample data: last 6 months, stock in vs stock out
    private static final String[] MONTHS = {"May", "Jun", "Jul", "Aug", "Sep", "Oct"};
    private static final int[] STOCK_IN  = { 42, 58, 34, 71, 63, 88 };
    private static final int[] STOCK_OUT = { 30, 44, 39, 52, 55, 61 };

    public StockChart() {
        setPreferredSize(new Dimension(560, 240));
        setMinimumSize(new Dimension(320, 180));
        ThemeManager.getInstance().addThemeChangeListener(this::repaint);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();
        int padLeft = 44, padRight = 16, padTop = 16, padBottom = 32;
        int chartW = w - padLeft - padRight;
        int chartH = h - padTop - padBottom;

        // Background
        Color bg = UIManager.getColor("Panel.background");
        g2.setColor(bg != null ? bg : getBackground());
        g2.fillRect(0, 0, w, h);

        // Grid lines + y-axis labels
        Color grid = UIManager.getColor("Inventory.chartGridColor");
        if (grid == null) grid = new Color(220, 220, 220);
        Color labelFg = UIManager.getColor("Label.disabledForeground");
        if (labelFg == null) labelFg = Color.GRAY;

        g2.setFont(getFont().deriveFont(10f));
        int maxVal = 100;
        int steps = 4;
        for (int i = 0; i <= steps; i++) {
            int val = maxVal * i / steps;
            int y = padTop + chartH - (chartH * i / steps);
            g2.setColor(grid);
            g2.drawLine(padLeft, y, padLeft + chartW, y);
            g2.setColor(labelFg);
            String lbl = String.valueOf(val);
            int lblW = g2.getFontMetrics().stringWidth(lbl);
            g2.drawString(lbl, padLeft - 8 - lblW, y + 4);
        }

        // Bars
        Color inColor = UIManager.getColor("Inventory.chartBarInColor");
        Color outColor = UIManager.getColor("Inventory.chartBarOutColor");
        if (inColor == null) inColor = new Color(47, 111, 237);
        if (outColor == null) outColor = new Color(255, 112, 67);

        int n = MONTHS.length;
        int groupW = chartW / n;
        int barW = Math.max(10, groupW / 4);
        int gapBetweenBars = 4;

        for (int i = 0; i < n; i++) {
            int cx = padLeft + i * groupW + groupW / 2;
            int inH = (int) (chartH * (STOCK_IN[i] / (double) maxVal));
            int outH = (int) (chartH * (STOCK_OUT[i] / (double) maxVal));

            int inX = cx - barW - gapBetweenBars / 2;
            int outX = cx + gapBetweenBars / 2;

            g2.setColor(inColor);
            g2.fill(new RoundRectangle2D.Float(inX, padTop + chartH - inH, barW, inH, 6, 6));
            g2.setColor(outColor);
            g2.fill(new RoundRectangle2D.Float(outX, padTop + chartH - outH, barW, outH, 6, 6));

            // Month label
            g2.setColor(labelFg);
            String month = MONTHS[i];
            int mW = g2.getFontMetrics().stringWidth(month);
            g2.drawString(month, cx - mW / 2, padTop + chartH + 18);
        }

        g2.dispose();
    }

    /** Optional: title rendered above the chart. */
    public JLabel createLegend(String text, String uiKey) {
        JLabel l = new JLabel("● " + text);
        l.setForeground(UIManager.getColor(uiKey));
        return l;
    }
}