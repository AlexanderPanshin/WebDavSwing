package listiner;

import logik.FileForDaw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListMouseAdapter extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        JTable table =(JTable) e.getSource();
        Point point = e.getPoint();
        int row = table.rowAtPoint(point);
        if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
            FileForDaw daw = (FileForDaw) table.getModel().getValueAt(row,0);
            System.out.println("Dawwass - >>> " + daw.getAbsolutePath());
        }
    }
}
