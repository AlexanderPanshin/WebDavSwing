package listiner;

import com.github.sardine.DavResource;
import gui.FooterPanel;
import gui.LeftPanel;
import gui.RightPanel;
import logik.FileDawTableModel;
import logik.FileForDaw;
import logik.StackPathDaw;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

public class SaveToDawListiner implements ActionListener {
    private RightPanel rp;
    private LeftPanel lp;
    private JTable localTable;
    private JTable dawTable;
    public SaveToDawListiner(RightPanel rightPanel, LeftPanel leftPanel) {
        rp = rightPanel;
        lp = leftPanel;
        localTable = lp.getTable();
        dawTable = rp.getTable();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int localSelected = localTable.getSelectedRow();
        if(localSelected != -1 && StackPathDaw.sizeStack()>0){

            FileForDaw localFile = (FileForDaw) localTable.getValueAt(localSelected,0);
            FooterPanel.addText("(LOCAL TO DAW) Попытка сохранения файла - " + localFile.getName() + " - на сервер" + System.lineSeparator());
            InputStream in;
            try {
                FooterPanel.addText("(LOCAL TO DAW) Сформирован поток из - " + localFile.getAbsoluteFile() + System.lineSeparator());
                 in  = new FileInputStream(localFile.getAbsoluteFile());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                FooterPanel.addText("(LOCAL TO DAW) В Директорию на сервере - " + ButtonConnectListiner.home + StackPathDaw.peekPath() + System.lineSeparator());
                System.out.println(ButtonConnectListiner.home + StackPathDaw.peekPath());
                ButtonConnectListiner.sardine.put(ButtonConnectListiner.home + StackPathDaw.peekPath()+localFile.getName(),in);
            } catch (IOException e) {
                FooterPanel.addText("(LOCAL TO DAW) AHTUNG " + e.getMessage() + System.lineSeparator());
                throw new RuntimeException(e);
            }
            FooterPanel.addText("(LOCAL TO DAW) Копирование завершино !" + System.lineSeparator());
            updateTble();
        }

    }
    public void updateTble(){
        List<DavResource> currentList= null;
        try {
            currentList = ButtonConnectListiner.sardine.getResources(ButtonConnectListiner.home + StackPathDaw.peekPath());
            currentList.remove(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileDawTableModel model = new FileDawTableModel();
        FooterPanel.addText("(LOCAL TO DAW) DAW Начинаем заполнять модель " + System.lineSeparator());
        if (!StackPathDaw.isRootFolder()) {
            FooterPanel.addText("(LOCAL TO DAW) DAW Не в корневой директории "+System.lineSeparator());
            model.addRow(new FileForDaw("..UP.."), -1);
            for (DavResource file : currentList) {
                model.addRow(new FileForDaw(file.getName()), (file.getContentLength().intValue()>=0?file.getContentLength().intValue():-2));
            }
        } else {
            FooterPanel.addText("(LOCAL TO DAW) DAW В корневой директории "+System.lineSeparator());
            for (DavResource file : currentList) {
                model.addRow(new FileForDaw(file.getName()), (file.getContentLength().intValue()>=0?file.getContentLength().intValue():-2));
            }
        }
        FooterPanel.addText("(LOCAL TO DAW) DAW Модель заполнена " + System.lineSeparator());

        dawTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                // Проверяем, является ли файл директорией и окрашиваем его в желтый цвет
                Integer fileName = (Integer) table.getValueAt(row, 1);
                if (fileName<0) {
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
        dawTable.setSelectionBackground(Color.gray);
        dawTable.setModel(model);
    }
}
