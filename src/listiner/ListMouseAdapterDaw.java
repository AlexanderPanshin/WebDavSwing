package listiner;

import com.github.sardine.DavResource;
import gui.FooterPanel;
import logik.DawPath;
import logik.FileDawTableModel;
import logik.FileForDaw;
import logik.StackPathDaw;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

public class ListMouseAdapterDaw extends MouseAdapter {
    private static String HOME;
    private static StackPathDaw stackPathDaw;
    private static List<DavResource> currentList;
    private FileForDaw davNameFolder;

    public ListMouseAdapterDaw() {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JTable table = (JTable) e.getSource();
        DawPath current;
        Point point = e.getPoint();
        int row = table.rowAtPoint(point);
        if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
            if (((Integer) table.getModel().getValueAt(row, 1)) == -1) {//если -1 переходим на уровень выще
                StackPathDaw.popPath();
                FooterPanel.addText("(MOUSE) DAW  Переходим на уровень выше -> ");
                FooterPanel.addText(" Длина стека "+ StackPathDaw.sizeStack() + " - >");
                if(StackPathDaw.isRootFolder()){
                    try {
                        FooterPanel.addText(" Корневавая директория : " + ButtonConnectListiner.home + System.lineSeparator());
                        currentList = ButtonConnectListiner.sardine.getResources(ButtonConnectListiner.home);
                        currentList.remove(0);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }else {
                    try {
                        FooterPanel.addText("Не корневая директория : " + StackPathDaw.peekPath() + System.lineSeparator());
                        currentList = ButtonConnectListiner.sardine.getResources(HOME + StackPathDaw.peekPath());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            } else {//если нет получаем текущую диркеторию
                davNameFolder = (FileForDaw) table.getModel().getValueAt(row,0);
                for (DavResource davResource: currentList){
                    if(davNameFolder.getName().equals(davResource.getName())){
                        FooterPanel.addText("(MOUSE) DAW Переходим на уровень ниже -> директория : " + davNameFolder.getName() + System.lineSeparator());
                        StackPathDaw.pushPath(davResource.getHref().toString());
                        try {
                            currentList = ButtonConnectListiner.sardine.getResources(HOME + davResource.getHref().toString());
                            currentList.remove(0);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
            //Заполняем модель
            FileDawTableModel model = new FileDawTableModel();
            FooterPanel.addText("(MOUSE) DAW Начинаем заполнять модель " + System.lineSeparator());
            if (!StackPathDaw.isRootFolder()) {
                FooterPanel.addText("(MOUSE) DAW Не в корневой директории "+System.lineSeparator());
                model.addRow(new FileForDaw("..UP.."), -1);
                for (DavResource file : currentList) {
                    model.addRow(new FileForDaw(file.getName()), (file.getContentLength().intValue()>=0?file.getContentLength().intValue():-2));
                }
            } else {
                FooterPanel.addText("(MOUSE) DAW В корневой директории "+System.lineSeparator());
                for (DavResource file : currentList) {
                    model.addRow(new FileForDaw(file.getName()), (file.getContentLength().intValue()>=0?file.getContentLength().intValue():-2));
                }
            }
            FooterPanel.addText("(MOUSE) DAW Модель заполнена " + System.lineSeparator());
//Засунь модель в таблицу
            table.setModel(model);
            table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
            table.setSelectionBackground(Color.gray);
        }
    }
    public static void init(){
        try {
            HOME = ButtonConnectListiner.home.trim();
            currentList = ButtonConnectListiner.sardine.getResources(HOME);
            stackPathDaw = StackPathDaw.STACK_PATH_DAW;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
