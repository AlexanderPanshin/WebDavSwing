package listiner;

import com.github.sardine.DavResource;
import gui.FooterPanel;
import logik.DawPath;
import logik.FileDawTableModel;
import logik.FileForDaw;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

public class ListMouseAdapterDaw extends MouseAdapter {
    private static String HOME;
    private static Stack<String> stackPath;
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
                FooterPanel.addText("DAW  Переходим на уровень выше -> ");
                FooterPanel.addText(" Длина стека "+ stackPath.size() + " - >");
                if(stackPath.size()==2){
                    try {
                        stackPath.pop();
                        FooterPanel.addText(" Корневавая директория : " + ButtonConnectListiner.home + System.lineSeparator());
                        currentList = ButtonConnectListiner.sardine.getResources(ButtonConnectListiner.home);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }else {
                    try {
                        FooterPanel.addText("Не корневая директория : " + stackPath.peek() + System.lineSeparator());
                        currentList = ButtonConnectListiner.sardine.getResources(HOME+stackPath.pop());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            } else {//если нет получаем текущую диркеторию
                davNameFolder = (FileForDaw) table.getModel().getValueAt(row,0);
                for (DavResource davResource: currentList){
                    if(davNameFolder.getName().equals(davResource.getName())){
                        FooterPanel.addText("DAW Переходим на уровень ниже -> директория : " + davNameFolder.getName() + System.lineSeparator());
                        stackPath.push(davResource.getHref().toString());
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
            FooterPanel.addText("DAW Начинаем заполнять модель " + System.lineSeparator());
            if (stackPath.size()>=2) {
                FooterPanel.addText("DAW Не в корневой директории "+System.lineSeparator());
                model.addRow(new FileForDaw("..UP.."), -1);
                for (DavResource file : currentList) {
                    model.addRow(new FileForDaw(file.getName()), (file.getContentLength().intValue()>=0?file.getContentLength().intValue():22));
                }
            } else {
                FooterPanel.addText("DAW В корневой директории "+System.lineSeparator());
                for (DavResource file : currentList) {
                    model.addRow(new FileForDaw(file.getName()), (file.getContentLength().intValue()>=0?file.getContentLength().intValue():22));
                }
            }
            FooterPanel.addText("DAW Модель заполнена " + System.lineSeparator());
//Засунь модель в таблицу
            table.setModel(model);
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
    }
    private String getUnicodeCodes(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++)
            result.append("\\u" + Integer.toHexString(str.charAt(i) | 0x10000).substring(1));

        return result.toString();
    }
    public static void init(){
        try {
            HOME = ButtonConnectListiner.home.substring(0,ButtonConnectListiner.home.length()-1);
            stackPath = new Stack<>();
            stackPath.push(HOME);
            currentList = ButtonConnectListiner.sardine.getResources(HOME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
