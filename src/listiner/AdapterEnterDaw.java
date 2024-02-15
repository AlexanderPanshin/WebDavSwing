package listiner;

import com.github.sardine.DavResource;
import gui.FooterPanel;
import logik.FileDawTableModel;
import logik.FileForDaw;
import logik.StackPathDaw;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

public class AdapterEnterDaw extends KeyAdapter {
    private static String HOME;
    private static List<DavResource> currentList;

    public AdapterEnterDaw() {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            JTable table =(JTable) e.getSource();
            FileForDaw daw;
            int row = table.getSelectedRow();
            if(((Integer) table.getModel().getValueAt(row,1))==-1){
                StackPathDaw.popPath();
                FooterPanel.addText("(KEYBOARD) DAW  Переходим на уровень выше -> ");
                if(StackPathDaw.isRootFolder()) {
                    try {
                        FooterPanel.addText(" Корневавая директория : " + ButtonConnectListiner.home + System.lineSeparator());
                        currentList = ButtonConnectListiner.sardine.getResources(HOME);//ahtung
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
            }else {
                daw = (FileForDaw) table.getModel().getValueAt(row,0);
                for (DavResource davResource: currentList){
                    if(daw.getName().equals(davResource.getName())){
                        FooterPanel.addText("(KEYBOARD) DAW Переходим на уровень ниже -> директория : " + daw.getName() + System.lineSeparator());
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
            FooterPanel.addText("(KEYBOARD) DAW Начинаем заполнять модель " + System.lineSeparator());
            if (!StackPathDaw.isRootFolder()) {
                FooterPanel.addText("(KEYBOARD) DAW Не в корневой директории "+System.lineSeparator());
                model.addRow(new FileForDaw("..UP.."), -1);
                for (DavResource file : currentList) {
                    model.addRow(new FileForDaw(file.getName()), (file.getContentLength().intValue()>=0?file.getContentLength().intValue():-2));
                }
            } else {
                FooterPanel.addText("(KEYBOARD) DAW В корневой директории "+System.lineSeparator());
                for (DavResource file : currentList) {
                    model.addRow(new FileForDaw(file.getName()), (file.getContentLength().intValue()>=0?file.getContentLength().intValue():-2));
                }
            }
            FooterPanel.addText("(KEYBOARD) DAW Модель заполнена " + System.lineSeparator());

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
            StackPathDaw.pushPath(HOME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
