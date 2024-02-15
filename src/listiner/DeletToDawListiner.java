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
import java.io.IOException;
import java.util.List;

public class DeletToDawListiner implements ActionListener {
    private RightPanel rp;
    private LeftPanel lp;
    private JTable localTable;
    private JTable dawTable;
    public DeletToDawListiner(RightPanel rightPanel, LeftPanel leftPanel) {
        rp = rightPanel;
        lp = leftPanel;
        localTable = lp.getTable();
        dawTable = rp.getTable();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int selectedDawRow = dawTable.getSelectedRow();
        if(selectedDawRow!=-1){
            FileForDaw dawFile = (FileForDaw) dawTable.getValueAt(selectedDawRow,0);
            FooterPanel.addText("(DELETE DAW) Пытаемься удалить файл -> " + dawFile.getName() + System.lineSeparator());
            String deletLink = ButtonConnectListiner.home + searchUrlDaw(dawFile);
            try {
                ButtonConnectListiner.sardine.delete(deletLink);
            } catch (IOException e) {
                FooterPanel.addText("(DELETE DAW) AHTUNG -> " + e.getMessage() + System.lineSeparator());
                throw new RuntimeException(e);
            }
            FooterPanel.addText("(DELETE DAW) Удаление успешно -> " + dawFile.getName() + System.lineSeparator());
            updateTble();
        }

    }
    private String searchUrlDaw(FileForDaw daw){
        List<DavResource> currentList= null;
        try {
            currentList = ButtonConnectListiner.sardine.getResources(ButtonConnectListiner.home + StackPathDaw.peekPath());
            currentList.remove(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FooterPanel.addText("(DELETE DAW) Ищем файл для удаление" + System.lineSeparator());
        for (DavResource currentDav: currentList){
            if(daw.getName().equals(currentDav.getName())){
                FooterPanel.addText("(DELETE DAW) Файл найден по адресу " + currentDav.getHref() + System.lineSeparator());
                return currentDav.getHref().toString();
            }
        }
        FooterPanel.addText("(DELETE DAW) Файл не найден " + System.lineSeparator());
        return "пусто";
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
        FooterPanel.addText("(DELETE DAW) DAW Начинаем заполнять модель " + System.lineSeparator());
        if (!StackPathDaw.isRootFolder()) {
            FooterPanel.addText("(DELETE DAW) DAW Не в корневой директории "+System.lineSeparator());
            model.addRow(new FileForDaw("..UP.."), -1);
            for (DavResource file : currentList) {
                model.addRow(new FileForDaw(file.getName()), (file.getContentLength().intValue()>=0?file.getContentLength().intValue():-2));
            }
        } else {
            FooterPanel.addText("(DELETE DAW) DAW В корневой директории "+System.lineSeparator());
            for (DavResource file : currentList) {
                model.addRow(new FileForDaw(file.getName()), (file.getContentLength().intValue()>=0?file.getContentLength().intValue():-2));
            }
        }
        FooterPanel.addText("(DELETE DAW) DAW Модель заполнена " + System.lineSeparator());

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
