package listiner;

import gui.BasePanel;
import logik.FileForDaw;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.io.File;

public class ListSelect implements ListSelectionListener {
    private String lastFolder;
    @Override
    public void valueChanged(ListSelectionEvent e) {
        System.out.println("Event ->>>" + e.toString());
        // Проверяем, что выбранная строка не пустая и является папкой
        FileForDaw currentDirectory = new FileForDaw(new File("").getAbsolutePath());
        if (!e.getValueIsAdjusting() && BasePanel.getLeftPanel().getTable().getSelectedRow() != -1) {
            int selectedRow = BasePanel.getLeftPanel().getTable().getSelectedRow();
            String fileName = (String) BasePanel.getLeftPanel().getTable().getValueAt(selectedRow, 0);

            // Получаем информацию о выбранной папке
            FileForDaw selectedFile;
            if(lastFolder==null) {
                selectedFile = new FileForDaw(currentDirectory.getAbsolutePath() + File.separator + fileName);
                lastFolder = selectedFile.getAbsolutePath();
            }else {
                selectedFile = new FileForDaw(lastFolder+File.separator+fileName);
                lastFolder = selectedFile.getAbsolutePath();
            }
            System.out.println(selectedFile.getAbsolutePath());
            FileForDaw[] subFiles = selectedFile.listFilesDaw();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Имя файла/папки");
            model.addColumn("Размер файла");


            // Очищаем модель данных таблицы
            model.setRowCount(0);

            // Заполняем модель данными выбранной папки
            for (File file : subFiles) {
                model.addRow(new Object[]{file.getName(), file.length()});
            }
            BasePanel.getLeftPanel().getTable().setModel(model);
        }
    }
    }
