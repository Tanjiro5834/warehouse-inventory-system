package com.warehouse.ui.frames;

import com.formdev.flatlaf.FlatClientProperties;
import com.warehouse.ui.components.SidebarButton;
import com.warehouse.ui.components.SearchField;
import com.warehouse.ui.components.ThemeSelectorComboBox;
import com.warehouse.ui.theme.ThemeManager;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Main application window: sidebar + top bar + CardLayout content area.
 * All navigation is functional. Page content is placeholder for Phase 1.
 * TODO: wire to backend (replace placeholder panels with real screen panels)
 */
public class MainFrame extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentArea = new JPanel(cardLayout);
    private final JPanel sidebar;
    private final JLabel pageTitleLabel = new JLabel("Dashboard");
    private final Map<String, SidebarButton> navButtons = new LinkedHashMap<>();

    private boolean sidebarCollapsed = false;

    public MainFrame() {
        setTitle("Warehouse Inventory Management");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 700));
        setSize(1280, 800);
        setLocationRelativeTo(null);

        // Root: sidebar | (topbar + content)
        JPanel root = new JPanel(new MigLayout("fill, insets 0, gap 0", "[][grow]", "[grow]"));
        root.setBackground(UIManager.getColor("Panel.background"));

        // ---------- Sidebar ----------
        sidebar = buildSidebar();
        root.add(sidebar, "growy, w 220!");

        // ---------- Right side: top bar + content ----------
        JPanel right = new JPanel(new MigLayout("fill, insets 0, gap 0", "[grow]", "[][grow]"));
        right.add(buildTopBar(), "growx, h 56!");
        right.add(contentArea, "grow");

        root.add(right, "grow");
        setContentPane(root);

        // Build placeholder pages
        buildPlaceholderPages();

        // Show dashboard by default
        navigateTo("Dashboard");
    }

    // ---------- Sidebar ----------

    private JPanel buildSidebar() {
        JPanel panel = new JPanel(new MigLayout(
                "wrap 1, insets 12 8 12 8, gapy 4",
                "[grow, fill]",
                "[]"));
        panel.setBackground(UIManager.getColor("Inventory.sidebarBackground"));
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, UIManager.getColor("Component.borderColor")));

        // Brand / collapse toggle
        JPanel brandRow = new JPanel(new MigLayout("insets 0, gap 8", "[][grow][]", "[center]"));
        brandRow.setOpaque(false);
        JLabel brand = new JLabel("WI");
        brand.putClientProperty(FlatClientProperties.STYLE_CLASS, "h3");
        brand.setForeground(UIManager.getColor("Inventory.accentColor"));
        JButton collapseBtn = new JButton("≡");
        collapseBtn.setFocusPainted(false);
        collapseBtn.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_TOOLBAR_BUTTON);
        collapseBtn.addActionListener(e -> toggleSidebar());
        brandRow.add(brand);
        brandRow.add(new JLabel(""), "growx");
        brandRow.add(collapseBtn);
        panel.add(brandRow, "growx, gapbottom 12");

        // Navigation items (order matches screens)
        addNav(panel, "Dashboard",    "icons/dashboard.svg");
        addNav(panel, "Inventory",    "icons/inventory.svg");
        addNav(panel, "Stock In",     "icons/stock-in.svg");
        addNav(panel, "Stock Out",    "icons/stock-out.svg");
        addNav(panel, "Tool Checkout","icons/tools.svg");
        addNav(panel, "History",      "icons/history.svg");
        addNav(panel, "Suppliers & Technicians", "icons/people.svg");
        addNav(panel, "Reports",      "icons/reports.svg");
        addNav(panel, "Settings",     "icons/settings.svg");

        return panel;
    }

    private void addNav(JPanel sidebar, String label, String iconPath) {
        SidebarButton btn = new SidebarButton(label, iconPath);
        btn.addActionListener(e -> navigateTo(label));
        navButtons.put(label, btn);
        sidebar.add(btn, "growx, h 40!");
    }

    private void toggleSidebar() {
        sidebarCollapsed = !sidebarCollapsed;
        sidebar.setVisible(!sidebarCollapsed);
        // Simple width toggle: in a full impl you'd animate; for UI-only this is fine
        // The layout will reflow; minimum size is preserved.
        revalidate();
    }

    // ---------- Top bar ----------

    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new MigLayout(
                "fill, insets 0 16 0 16, gap 12",
                "[][grow,fill][][][]",
                "[center]"));
        bar.setBackground(UIManager.getColor("Panel.background"));
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UIManager.getColor("Component.borderColor")));

        pageTitleLabel.putClientProperty(FlatClientProperties.STYLE_CLASS, "h2");
        bar.add(pageTitleLabel);

        bar.add(new SearchField(20), "width 240!");

        ThemeSelectorComboBox themeCombo = new ThemeSelectorComboBox();
        themeCombo.setPreferredSize(new Dimension(110, 32));
        bar.add(themeCombo);

        // User avatar placeholder (circle with initial)
        JLabel avatar = new JLabel("A", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UIManager.getColor("Inventory.accentColor"));
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        avatar.setForeground(Color.WHITE);
        avatar.setPreferredSize(new Dimension(32, 32));
        avatar.setHorizontalAlignment(SwingConstants.CENTER);
        bar.add(avatar);

        return bar;
    }

    // ---------- Content / navigation ----------

    private void buildPlaceholderPages() {
        for (String name : navButtons.keySet()) {
            switch (name) {
                case "Dashboard":
                    contentArea.add(new com.warehouse.ui.panels.DashboardPanel(), name);
                    break;
                case "Inventory":
                    contentArea.add(new com.warehouse.ui.panels.InventoryPanel(), name);
                    break;
                case "Stock In":
                    contentArea.add(new com.warehouse.ui.panels.StockInPanel(), name);
                    break;
                case "Stock Out":
                    contentArea.add(new com.warehouse.ui.panels.StockOutPanel(), name);
                    break;
                case "Tool Checkout":
                    contentArea.add(new com.warehouse.ui.panels.ToolCheckoutPanel(), name);
                    break;
                case "History":
                    contentArea.add(new com.warehouse.ui.panels.TransactionHistoryPanel(), name);
                    break;
                case "Suppliers & Technicians":
                    contentArea.add(new com.warehouse.ui.panels.ContactsPanel(), name);
                    break;
                case "Reports":
                    contentArea.add(new com.warehouse.ui.panels.ReportsPanel(), name);
                    break;
                case "Settings":
                    contentArea.add(new com.warehouse.ui.panels.SettingsPanel(), name);
                    break;
                default:
                    contentArea.add(buildPlaceholderPanel(name), name);
            }
        }
    }

    private JPanel buildPlaceholderPanel(String name) {
        JPanel p = new JPanel(new MigLayout("fill, insets 24", "[grow,center]", "[grow,center]"));
        p.setBackground(UIManager.getColor("Panel.background"));
        JLabel lbl = new JLabel(name + " – coming in next phase");
        lbl.putClientProperty(FlatClientProperties.STYLE_CLASS, "h3");
        lbl.setForeground(UIManager.getColor("Label.disabledForeground"));
        p.add(lbl);
        return p;
    }

    private void navigateTo(String pageName) {
        cardLayout.show(contentArea, pageName);
        pageTitleLabel.setText(pageName);
        // Update selected state on sidebar buttons
        navButtons.forEach((k, btn) -> btn.getModel().setSelected(k.equals(pageName)));
    }
}