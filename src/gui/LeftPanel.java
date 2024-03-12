package gui;

import listiner.*;
import logik.FileDawTableModel;
import logik.FileForDaw;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.File;

public class LeftPanel extends JPanel {
    private JTable table;
    public LeftPanel() {
        table = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.addMouseListener(new ListMouseAdapterLocal());
        //table.addMouseListener(new ListMouseAdapter());
        //table.addKeyListener(new AdapterEnter());
        table.addKeyListener(new AdapterEnterLocal());
        createTable(table);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(240,200));
        add(scrollPane);
    }
    public JTable getTable() {
        return table;
    }
    private void createTable(JTable table){
        FileDawTableModel model = new FileDawTableModel();
        FileForDaw currentDirectory = new FileForDaw(new File("").getAbsolutePath());
        FileForDaw [] files = currentDirectory.listFilesDaw();
        model.addRow(new FileForDaw("..UP.."),-1);
        for (FileForDaw file : files) {
            model.addRow(file, (int) file.length());
        }
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                // Проверяем, является ли файл директорией и окрашиваем его в желтый цвет
                String fileName = table.getValueAt(row, 0).toString();
                if (new File(fileName).isDirectory()) {
                    c.setBackground(Color.YELLOW);
                } else {
                    c.setBackground(table.getBackground());
                }
                if(isSelected){
                    c.setBackground(Color.gray);
                }
                return c;
            }
        });
        table.setSelectionBackground(Color.gray);
        table.setModel(model);

    }
}
