package com.warehouse.ui.frames;

import com.formdev.flatlaf.FlatClientProperties;
import com.warehouse.ui.theme.ThemeManager;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Login screen. UI-only: any input + Login opens the main window.
 * TODO: wire to backend (real authentication)
 */
public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Warehouse Inventory – Sign In");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 600));
        setSize(1000, 680);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new MigLayout("fill, insets 0", "[grow,center]", "[grow,center]"));
        root.setBackground(UIManager.getColor("Panel.background"));

        // Card
        JPanel card = new JPanel(new MigLayout(
                "wrap 1, insets 40 48 40 48, gapy 16",
                "[grow, fill, 360!]",
                "[]"));
        card.putClientProperty(FlatClientProperties.STYLE, "arc: 16; background: $Panel.background;");
        card.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Component.borderColor"), 1, true));

        JLabel title = new JLabel("Warehouse Inventory");
        title.putClientProperty(FlatClientProperties.STYLE_CLASS, "h1");
        JLabel subtitle = new JLabel("Sign in to your account");
        subtitle.putClientProperty(FlatClientProperties.STYLE_CLASS, "small");

        JTextField username = new JTextField(20);
        username.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Username");
        JPasswordField password = new JPasswordField(20);
        password.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Password");

        JCheckBox remember = new JCheckBox("Remember me");

        JButton loginBtn = new JButton("Sign In");
        loginBtn.putClientProperty(FlatClientProperties.STYLE_CLASS, "default");
        loginBtn.setPreferredSize(new Dimension(360, 40));

        loginBtn.addActionListener(e -> {
            // TODO: wire to backend (real authentication)
            dispose();
            new MainFrame().setVisible(true);
        });

        card.add(title);
        card.add(subtitle, "gapbottom 12");
        card.add(username);
        card.add(password);
        card.add(remember, "gapbottom 4");
        card.add(loginBtn);

        root.add(card);
        setContentPane(root);
    }
}