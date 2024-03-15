package gui;


import listiner.AdapterEnterDaw;
import listiner.ListMouseAdapter;
import listiner.ListMouseAdapterDaw;
import logik.FileDawTableModel;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {
    private JTable table;

    public RightPanel() {
        table = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.addMouseListener(new ListMouseAdapterDaw());
        table.addKeyListener(new AdapterEnterDaw());
        table.setModel(new FileDawTableModel());
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(240,200));
        add(scrollPane);


        //table= new JTable(4,2);
        //add(table);
    }

    public JTable getTable() {
        return table;
    }
}
