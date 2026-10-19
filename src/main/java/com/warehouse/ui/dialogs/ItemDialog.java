package com.warehouse.ui.dialogs;

import com.formdev.flatlaf.FlatClientProperties;
import com.warehouse.ui.components.FormRow;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Add/Edit Item dialog. Form layout only; Save just closes the dialog.
 * TODO: wire to backend (persist item)
 */
public class ItemDialog extends JDialog {

    public ItemDialog(Window owner) {
        super(owner, "Add / Edit Item", ModalityType.APPLICATION_MODAL);
        setLayout(new MigLayout("fill, insets 20, gapy 10", "[grow,fill]", "[]"));
        setMinimumSize(new Dimension(520, 460));
        setLocationRelativeTo(owner);

        JPanel form = new JPanel(new MigLayout("wrap 1, insets 0, gapy 10", "[grow,fill]", "[]"));
        form.setOpaque(false);

        JComboBox<String> category = new JComboBox<>(new String[]{"Unit", "Consumable", "Tool"});
        form.add(new FormRow("Category", category));

        JTextField brand = new JTextField();
        JTextField model = new JTextField();
        JTextField capacity = new JTextField();
        JComboBox<String> type = new JComboBox<>(new String[]{"Window", "Split", "Inverter"});
        JTextField serial = new JTextField();

        JTextField itemName = new JTextField();
        JTextField quantity = new JTextField();
        JComboBox<String> unitOfMeasure = new JComboBox<>(new String[]{"pcs", "roll", "kg", "box"});
        JTextField reorder = new JTextField();
        JComboBox<String> toolStatus = new JComboBox<>(
                new String[]{"Available", "Checked Out", "Under Repair", "Lost"});
        JTextField technician = new JTextField();

        // Unit-only rows
        FormRow brandRow = new FormRow("Brand", brand);
        FormRow modelRow = new FormRow("Model", model);
        FormRow capRow   = new FormRow("Capacity (HP)", capacity);
        FormRow typeRow  = new FormRow("Type", type);
        FormRow serialRow= new FormRow("Serial No.", serial);

        // Consumable-only rows
        FormRow nameRow   = new FormRow("Item Name", itemName);
        FormRow qtyRow    = new FormRow("Quantity", quantity);
        FormRow uomRow    = new FormRow("Unit of Measure", unitOfMeasure);
        FormRow reorderRow= new FormRow("Reorder Level", reorder);

        // Tool-only rows
        FormRow tStatusRow = new FormRow("Status", toolStatus);
        FormRow techRow    = new FormRow("Assigned Technician", technician);

        form.add(brandRow);
        form.add(modelRow);
        form.add(capRow);
        form.add(typeRow);
        form.add(serialRow);
        form.add(nameRow);
        form.add(qtyRow);
        form.add(uomRow);
        form.add(reorderRow);
        form.add(tStatusRow);
        form.add(techRow);

        // Show/hide rows based on category
        Runnable updateVisibility = () -> {
            String cat = (String) category.getSelectedItem();
            boolean isUnit = "Unit".equals(cat);
            boolean isConsumable = "Consumable".equals(cat);
            boolean isTool = "Tool".equals(cat);

            brandRow.setVisible(isUnit);
            modelRow.setVisible(isUnit);
            capRow.setVisible(isUnit);
            typeRow.setVisible(isUnit);
            serialRow.setVisible(isUnit);

            nameRow.setVisible(!isUnit);
            qtyRow.setVisible(!isUnit);
            uomRow.setVisible(isConsumable);
            reorderRow.setVisible(isConsumable);

            tStatusRow.setVisible(isTool);
            techRow.setVisible(isTool);

            form.revalidate();
            form.repaint();
        };
        category.addActionListener(e -> updateVisibility.run());
        updateVisibility.run();

        JScrollPane scroll = new JScrollPane(form);
        scroll.putClientProperty(FlatClientProperties.STYLE, "arc: 8;");
        scroll.setBorder(BorderFactory.createEmptyBorder());
        add(scroll, "grow, push");

        JPanel buttons = new JPanel(new MigLayout("insets 0, gap 8", "[grow][]", "[center]"));
        buttons.setOpaque(false);
        JButton cancel = new JButton("Cancel");
        JButton save = new JButton("Save");
        save.putClientProperty(FlatClientProperties.STYLE_CLASS, "default");
        cancel.addActionListener(e -> dispose());
        save.addActionListener(e -> {
            // TODO: wire to backend
            JOptionPane.showMessageDialog(this, "Item saved (UI placeholder).");
            dispose();
        });
        buttons.add(new JLabel(""), "growx");
        buttons.add(cancel);
        buttons.add(save);
        add(buttons, "growx, gaptop 12");
    }
}