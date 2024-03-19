package gui;


import listiner.AdapterEnterDaw;
import listiner.ListMouseAdapterDaw;
import listiner.TabAdapter;
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
        table.addKeyListener(new TabAdapter());
        table.setModel(new FileDawTableModel());
        table.getAccessibleContext().setAccessibleName("Daw файлы");
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(240,200));
        add(scrollPane);
    }

    public JTable getTable() {
        return table;
    }
}
