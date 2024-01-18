package logik;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class FileDawTableModel  extends AbstractTableModel {
    private String[] columnNames;
    private LinkedHashMap<FileForDaw,Integer> data;


    public FileDawTableModel(LinkedHashMap<FileForDaw,Integer> data) {
        columnNames = new String[]{"Имя файла/папки","Размер файла"};
        this.data = data;
    }

    public FileDawTableModel() {
        columnNames = new String[]{"Имя файла/папки","Размер файла"};
        this.data = new LinkedHashMap<>();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        List<FileForDaw> list = new ArrayList<FileForDaw>(data.keySet());
        FileForDaw file = list.get(i);
        if(i1 == 0) {
            return file;
        }else {
            return data.get(file);
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void addRow(FileForDaw file, Integer lenght){
        data.put(file,lenght);
    }
}
