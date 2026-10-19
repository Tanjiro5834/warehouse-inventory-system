package com.warehouse.ui.panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.warehouse.ui.components.SearchField;
import com.warehouse.ui.components.StatCard;
import com.warehouse.ui.components.StatusBadge;
import com.warehouse.ui.components.StockChart;
import com.warehouse.ui.sampledata.TransactionTableModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import java.awt.*;

/**
 * Dashboard screen: KPI cards, stock in/out chart, recent transactions, low-stock list.
 * All data is static sample data.
 * TODO: wire to backend
 */
public class DashboardPanel extends JPanel {

    public DashboardPanel() {
        setLayout(new MigLayout(
                "fill, insets 24, gapy 20, gapx 20",
                "[grow][grow][grow][grow]",
                "[][grow]"));

        // ---------- KPI Row ----------
        StatCard totalUnits    = new StatCard("Total Units", "142", "+12 this month", null);
        StatCard totalSKUs     = new StatCard("Consumable SKUs", "84", "across 6 categories", null);
        StatCard toolsOut      = new StatCard("Tools Checked Out", "7", "2 overdue", "Inventory.lowStockColor");
        StatCard lowStock      = new StatCard("Low-Stock Alerts", "9", "3 critical", "Inventory.outOfStockColor");

        add(totalUnits, "growx");
        add(totalSKUs, "growx");
        add(toolsOut, "growx");
        add(lowStock, "growx, wrap");

        // ---------- Left column: chart + recent transactions ----------
        JPanel left = new JPanel(new MigLayout("fill, wrap, insets 0, gapy 16", "[grow]", "[][grow]"));
        left.setOpaque(false);

        left.add(buildChartCard(), "growx");
        left.add(buildRecentTransactionsCard(), "grow");

        add(left, "span 3, grow");

        // ---------- Right column: low stock list ----------
        add(buildLowStockCard(), "growy");
    }

    private JPanel buildChartCard() {
        JPanel card = new JPanel(new MigLayout(
                "fill, wrap, insets 16 20 16 20, gapy 8",
                "[grow,fill]",
                "[][grow]"));
        card.putClientProperty(FlatClientProperties.STYLE,
                "arc: 12; background: $Panel.background; border: 1,1,1,1,$Component.borderColor,1,12");

        JLabel title = new JLabel("Stock Movement – Last 6 Months");
        title.putClientProperty(FlatClientProperties.STYLE_CLASS, "h3");
        card.add(title, "split 2");

        JPanel legend = new JPanel(new MigLayout("insets 0, gap 16", "[][][][]", "[center]"));
        legend.setOpaque(false);
        JLabel inLbl = new JLabel("■ Stock In");
        inLbl.setForeground(UIManager.getColor("Inventory.chartBarInColor"));
        JLabel outLbl = new JLabel("■ Stock Out");
        outLbl.setForeground(UIManager.getColor("Inventory.chartBarOutColor"));
        legend.add(inLbl);
        legend.add(outLbl);
        card.add(legend, "right");

        StockChart chart = new StockChart();
        card.add(chart, "grow, h 220:260");

        return card;
    }

    private JPanel buildRecentTransactionsCard() {
        JPanel card = new JPanel(new MigLayout(
                "fill, wrap, insets 16 20 16 20, gapy 8",
                "[grow,fill]",
                "[][grow]"));
        card.putClientProperty(FlatClientProperties.STYLE,
                "arc: 12; background: $Panel.background; border: 1,1,1,1,$Component.borderColor,1,12");

        JLabel title = new JLabel("Recent Transactions");
        title.putClientProperty(FlatClientProperties.STYLE_CLASS, "h3");
        card.add(title, "split 2");

        JButton viewAll = new JButton("View All");
        viewAll.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_TOOLBAR_BUTTON);
        card.add(viewAll, "right");
        // TODO: wire to backend (navigate to Transaction History)

        JTable table = new JTable(new TransactionTableModel());
        table.setRowHeight(UIManager.getInt("Table.rowHeight"));
        table.setAutoCreateRowSorter(true);
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        table.setFillsViewportHeight(true);
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean focus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, v, sel, focus, row, col);
                if (!sel) c.setForeground(UIManager.getColor("Label.foreground"));
                return c;
            }
        });

        JScrollPane sp = new JScrollPane(table);
        card.add(sp, "grow");

        return card;
    }

    private JPanel buildLowStockCard() {
        JPanel card = new JPanel(new MigLayout(
                "fill, insets 16 20 16 20, gapy 10",
                "[grow,fill]",
                "[][grow]"));
        card.putClientProperty(FlatClientProperties.STYLE,
                "arc: 12; background: $Panel.background; border: 1,1,1,1,$Component.borderColor,1,12");

        JLabel title = new JLabel("Low-Stock Alerts");
        title.putClientProperty(FlatClientProperties.STYLE_CLASS, "h3");
        card.add(title, "wrap");

        JPanel list = new JPanel(new MigLayout("wrap 1, insets 0, gapy 8", "[grow,fill]", "[]"));
        list.setOpaque(false);

        list.add(lowStockRow("R22 Refrigerant", "0 / 5 kg", StatusBadge.Status.OUT_OF_STOCK));
        list.add(lowStockRow("PVC Tape (white)", "35 / 40 roll", StatusBadge.Status.LOW_STOCK));
        list.add(lowStockRow("R32 Refrigerant", "6 / 10 kg", StatusBadge.Status.LOW_STOCK));
        list.add(lowStockRow("Foam Insulation", "4 / 8 roll", StatusBadge.Status.LOW_STOCK));
        list.add(lowStockRow("Panasonic CS-PN 1.0", "0 units", StatusBadge.Status.OUT_OF_STOCK));
        list.add(lowStockRow("LG Dual Cool 1.5", "2 units", StatusBadge.Status.LOW_STOCK));

        JScrollPane sp = new JScrollPane(list);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        card.add(sp, "grow");

        return card;
    }

    private JPanel lowStockRow(String name, String qty, StatusBadge.Status status) {
        JPanel row = new JPanel(new MigLayout(
                "insets 8 4 8 4, gap 8",
                "[grow,fill][][]",
                "[center]"));
        row.putClientProperty(FlatClientProperties.STYLE,
                "arc: 8; background: $Panel.background; border: 1,1,1,1,$Component.borderColor,1,8");

        JLabel n = new JLabel(name);
        n.putClientProperty(FlatClientProperties.STYLE_CLASS, "small");
        JLabel q = new JLabel(qty);
        q.putClientProperty(FlatClientProperties.STYLE_CLASS, "small");
        q.setForeground(UIManager.getColor("Label.disabledForeground"));

        StatusBadge badge = new StatusBadge(
                status == StatusBadge.Status.OUT_OF_STOCK ? "Out" : "Low", status);

        row.add(n);
        row.add(q);
        row.add(badge);
        return row;
    }
}