package listiner;

import gui.FooterPanel;
import logik.FileDawTableModel;
import logik.FileForDaw;
import logik.StackPathLocal;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

public class AdapterEnterLocal extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            JTable table =(JTable) e.getSource();
            FileDawTableModel model = new FileDawTableModel();
            FileForDaw daw;
            int row = table.getSelectedRow();
            if(((Integer) table.getModel().getValueAt(row,1))==-1) {// если на уровень выше
                StackPathLocal.popPath();
                FooterPanel.addText("(LOCAL KEYBOARD) Переходим на уровень выше нажата кнопка UP." + System.lineSeparator());
                if (StackPathLocal.isRootFolder()) {

                    FooterPanel.addText("(LOCAL KEYBOARD) Нет родителей, корневая директория !" + System.lineSeparator());
                    daw = new FileForDaw(StackPathLocal.stringPath());
                    fillTableModel(daw, model, true);
                } else {
                    daw = new FileForDaw(StackPathLocal.stringPath());
                    fillTableModel(daw, model, false);
                }
            }

            table.setModel(model);
            colorTable(table);
        }
    }
    private void fillTableModel(FileForDaw daw, FileDawTableModel model, boolean isRoot) {
        if (!isRoot) {
            FileForDaw[] files = daw.listFilesDaw();
            FooterPanel.addText("(LOCAL KEYBOARD) Не корневая директория получаем список  = " + daw.getName() + System.lineSeparator());
            model.addRow(new FileForDaw("..UP.."), -1);
            for (FileForDaw file : files) {
                model.addRow(file, (int) file.length());
            }
        } else {
            FooterPanel.addText("(LOCAL KEYBOARD) Корневая директория получаем список  = " + daw.getName() + System.lineSeparator());
            String [] [] rootMass = rootDawsMass();
            for (int i = 0; i < rootMass.length; i++) {
                model.addRow(new FileForDaw(rootMass[i][0]), Integer.parseInt(rootMass[i][1]));
            }
        }
        FooterPanel.addText("(LOCAL KEYBOARD) Список заплолнен = " + daw.getName() + System.lineSeparator());
    }
    private String [] [] rootDawsMass(){
        String [][] freeSpace = new String[File.listRoots().length][2];
        File [] fmass = File.listRoots();
        for (int i = 0; i < File.listRoots().length;i++){
            freeSpace[i][0] = fmass[i].toString().substring(0,1);
            freeSpace[i][1] = Integer.toString((int)fmass[i].getFreeSpace());
        }
        return freeSpace;
    }
    private void colorTable(JTable table) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                // Проверяем, является ли файл директорией и окрашиваем его в желтый цвет
                FileForDaw file = (FileForDaw) table.getValueAt(row, 0);
                String fileName = file.getAbsolutePath();
                if (new File(fileName).isDirectory()) {
                    c.setBackground(Color.YELLOW);
                } else {
                    c.setBackground(table.getBackground());
                }
                if (isSelected) {
                    c.setBackground(Color.gray);
                }
                return c;
            }
        });
        table.setSelectionBackground(Color.gray);
    }
}
