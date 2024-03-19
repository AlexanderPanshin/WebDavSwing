package listiner;

import gui.FooterPanel;
import logik.FileDawTableModel;
import logik.FileForDaw;
import logik.StackPathDaw;
import logik.StackPathLocal;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;


public class ListMouseAdapterLocal extends MouseAdapter {
    private StackPathLocal stackPathLocal;
    private boolean flagRootCreated;
    public ListMouseAdapterLocal() {
        stackPathLocal = new StackPathLocal();
        StackPathLocal.insertPath(new File("").getAbsolutePath());
        StackPathLocal.stringPath();
        flagRootCreated = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        StackPathLocal.optimizedStack();
        JTable table = (JTable) e.getSource();
        FileDawTableModel model = new FileDawTableModel();
        FileForDaw daw;
        Point point = e.getPoint();
        int row = table.rowAtPoint(point);
        if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
            if (((Integer) table.getModel().getValueAt(row, 1)) == -1) {
                StackPathLocal.popPath();
                FooterPanel.addText("(LOCAL MOUSE) Переходим на уровень выше нажата кнопка UP." + System.lineSeparator());
                if (StackPathLocal.isRootFolder()) {

                    FooterPanel.addText("(LOCAL MOUSE) Нет родителей, корневая директория !" + System.lineSeparator());
                    daw = new FileForDaw(StackPathLocal.stringPath());

                    fillTableModel(daw, model, true);
                } else {
                    daw = new FileForDaw(StackPathLocal.stringPath());
                    fillTableModel(daw, model, false);
                }
            } else {
                daw = (FileForDaw) table.getModel().getValueAt(row, 0);
                if(daw.isDirectory()) {
                    StackPathLocal.pushPath(daw.getName());
                    FooterPanel.addText("(LOCAL MOUSE) Переходим на иректорию ниже = " + daw.getName() + System.lineSeparator());
                    FooterPanel.addText("(LOCAL MOUSE) Директория назад будет  = " + daw.getParent() + System.lineSeparator());
                    fillTableModel(daw, model, false);
                }else {
                    if(!StackPathLocal.isRootFolder()) {
                        return;
                    }else {
                        StackPathLocal.pushPath(daw.getName());
                        FooterPanel.addText("(LOCAL MOUSE) Переходим на иректорию ниже = " + daw.getName() + System.lineSeparator());
                        FooterPanel.addText("(LOCAL MOUSE) Директория назад будет  = " + daw.getParent() + System.lineSeparator());
                        fillTableModel(daw, model, false);
                    }
                }
            }
            table.setModel(model);
            colorTable(table);
        }
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

    private void fillTableModel(FileForDaw daw, FileDawTableModel model, boolean isRoot) {
        if (!isRoot) {
            if(!flagRootCreated) {
                FileForDaw[] files = daw.listFilesDaw();
                if(files!=null) {
                    FooterPanel.addText("(LOCAL MOUSE) Не корневая директория получаем список  = " + daw.getName() + System.lineSeparator());
                    model.addRow(new FileForDaw("..UP.."), -1);
                    for (FileForDaw file : files) {
                        model.addRow(file, (int) file.length());
                    }
                }else {
                    return;
                }
            }else {
                String pathDisk = daw.getName() + ":" + File.separator;
                flagRootCreated = false;
                fillTableModel(new FileForDaw(pathDisk),model,false);

            }
        } else {
            FooterPanel.addText("(LOCAL MOUSE) Корневая директория получаем список  = " + daw.getName() + System.lineSeparator());
            for (FileForDaw file : rootDawsMass()) {
                model.addRow(file, (int) file.length());
            }
        }
        FooterPanel.addText("(LOCAL MOUSE) Список заплолнен = " + daw.getName() + System.lineSeparator());
    }
    private FileForDaw [] rootDawsMass(){
        FileForDaw [] ffdm = new FileForDaw[File.listRoots().length];
        File [] fmass = File.listRoots();
        for (int i = 0; i < File.listRoots().length;i++){
            ffdm[i]= new FileForDaw(fmass[i].toString().substring(0,1));
        }
        flagRootCreated = true;
        return ffdm;
    }

}
