package listiner;

import gui.FooterPanel;
import logik.FileDawTableModel;
import logik.FileForDaw;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class ListMouseAdapter extends MouseAdapter {
    private FileForDaw back ;
    private boolean isRoot;
    private boolean isDisk;

    public ListMouseAdapter() {
        FileForDaw t = new FileForDaw(new File("").getAbsolutePath());
        back = new FileForDaw(new File(t.getParent()).toURI());
        isRoot = false;
        isDisk = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JTable table =(JTable) e.getSource();
        FileForDaw daw;
        Point point = e.getPoint();
        int row = table.rowAtPoint(point);
        if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
            if(((Integer) table.getModel().getValueAt(row,1))==-1){
                FooterPanel.addText("(LOCAL MOUSE) Переходим на уровень выше нажата кнопка UP." + System.lineSeparator());
                daw = back;
                if(!daw.isRootFolder()) {
                    FooterPanel.addText("(LOCAL MOUSE) Текущая директория = " + daw.getName() + System.lineSeparator());
                    FooterPanel.addText("(LOCAL MOUSE) Не корневая директория выше = " + daw.getParent() + System.lineSeparator());
                    isRoot = false;
                    if(daw.getParent()!=null) {
                        back = new FileForDaw(new File(daw.getParent()).toURI());
                    }
                }else {
                    FooterPanel.addText("(LOCAL MOUSE) Нет родителей, корневая директория !" + System.lineSeparator());
                    isRoot = true;
                }
            }else {
                daw = (FileForDaw) table.getModel().getValueAt(row,0);
                back = new FileForDaw(new File(daw.getParent()).toURI());
                FooterPanel.addText("(LOCAL MOUSE) Переходим на иректорию ниже = " + daw.getName() + System.lineSeparator());
                FooterPanel.addText("(LOCAL MOUSE) Директория назад будет  = " + daw.getParent() + System.lineSeparator());
            }
            FileDawTableModel model = new FileDawTableModel();
            FileForDaw [] files = daw.listFilesDaw();

            if (!isRoot) { //пока не корень
                    upTableModel(daw,model,files);
            }else { // если корень
                if (!isDisk){ // первый раз не показываем диск
                    upTableModel(daw,model,files);
                }else {
                    for (FileForDaw file : rootDawsMass()) {
                        model.addRow(file, (int) file.length());
                    }
                    isDisk =false;
                }
                isDisk = true;

            }

            table.setModel(model);
            colorTable(table);
        }
    }
    private FileForDaw [] rootDawsMass(){
        FileForDaw [] ffdm = new FileForDaw[File.listRoots().length];
        File [] fmass = File.listRoots();
        for (int i = 0; i < File.listRoots().length;i++){
            ffdm[i]= new FileForDaw(fmass[i].toString().substring(0,1));
        }

        return ffdm;
    }
    private void colorTable(JTable table){
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
                if(isSelected){
                    c.setBackground(Color.gray);
                }
                return c;
            }
        });
        table.setSelectionBackground(Color.gray);
    }
    private void upTableModel(FileForDaw daw,FileDawTableModel model, FileForDaw [] files){
        FooterPanel.addText("(LOCAL MOUSE) Не корневая директория получаем список  = " + daw.getName() + System.lineSeparator());
        model.addRow(new FileForDaw("..UP.."), -1);
        for (FileForDaw file : files) {
            model.addRow(file, (int) file.length());
        }
        FooterPanel.addText("(LOCAL MOUSE) Список заплолнен = " + daw.getName() + System.lineSeparator());
    }
}
