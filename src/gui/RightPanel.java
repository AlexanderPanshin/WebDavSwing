package gui;

import javax.swing.*;

public class RightPanel extends JPanel {
    private JTable table;

    public RightPanel() {
        table= new JTable(4,2);
        add(table);
    }

    public JTable getTable() {
        return table;
    }
}
