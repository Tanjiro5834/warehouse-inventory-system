package com.warehouse.ui.components;

import javax.swing.*;
import java.awt.*;

/**
 * Visual-only search field. No filtering logic attached.
 * TODO: wire to backend (search/filter logic later)
 */
public class SearchField extends JTextField {

    public SearchField(int columns) {
        super(columns);
        putClientProperty("JTextField.placeholderText", "Search…");
        putClientProperty("JTextField.showClearButton", true);
        setPreferredSize(new Dimension(220, 32));
    }
}