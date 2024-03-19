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
import java.net.URLEncoder;
import java.util.Arrays;

public class AdapterEnterLocal extends KeyAdapter {
    private boolean flagRootCreated;

    public AdapterEnterLocal() {
        this.flagRootCreated = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            StackPathLocal.optimizedStack();
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
            } else {
                daw = (FileForDaw) table.getModel().getValueAt(row, 0);
                StackPathLocal.pushPath(daw.getName());
                FooterPanel.addText("(LOCAL KEYBOARD) Переходим на директорию ниже = " + daw.getName() + System.lineSeparator());
                FooterPanel.addText("(LOCAL KEYBOARD) Директория назад будет  = " + daw.getParent() + System.lineSeparator());
                fillTableModel(daw, model, false);
            }

            table.setModel(model);
            colorTable(table);
        }
    }
    private void fillTableModel(FileForDaw daw, FileDawTableModel model, boolean isRoot) {
        if (!isRoot) {
            if(!flagRootCreated) {
                String newStr = URLEncoder.encode(daw.getName());

                FileForDaw[] files = daw.listFilesDaw();
                if(files!=null) {
                    FooterPanel.addText("(LOCAL KEYBOARD) Не корневая директория получаем список  = " + daw.getName() + System.lineSeparator());
                    model.addRow(new FileForDaw("..UP.."), -1);
                    for (FileForDaw file : files) {
                        model.addRow(file, (int) file.length());
                    }
                }else {
                    files = new FileForDaw(daw.getParent()).listFilesDaw();
                    model.addRow(new FileForDaw("..UP.."), -1);
                    for (FileForDaw file : files) {
                        model.addRow(file, (int) file.length());
                    }
                    return;
                }
            }else {
                String pathDisk = daw.getName() + ":" + File.separator;
                flagRootCreated = false;
                fillTableModel(new FileForDaw(pathDisk),model,false);

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
        flagRootCreated = true;
        return freeSpace;
    }
    private void colorTable(JTable table) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                FileForDaw file = (FileForDaw) table.getValueAt(row, 0);
                if(file != null) {
                    String fileName = file.getAbsolutePath();
                    if (new File(fileName).isDirectory()) {
                        c.setBackground(Color.YELLOW);
                    } else {
                        c.setBackground(table.getBackground());
                    }
                    if (isSelected) {
                        c.setBackground(Color.gray);
                    }
                }
                return c;
            }
        });
        table.setSelectionBackground(Color.gray);
    }
}
