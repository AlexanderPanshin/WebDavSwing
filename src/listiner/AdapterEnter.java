package listiner;

import logik.FileDawTableModel;
import logik.FileForDaw;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

public class AdapterEnter extends KeyAdapter {
    private FileForDaw back ;
    private boolean isRoot;

    public AdapterEnter() {
        FileForDaw t = new FileForDaw(new File("").getAbsolutePath());
        back = new FileForDaw(new File(t.getParent()).toURI());
        isRoot = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            JTable table =(JTable) e.getSource();
            FileForDaw daw;
            int row = table.getSelectedRow();
                if(((Integer) table.getModel().getValueAt(row,1))==-1){
                    daw = back;
                    if(daw.getParent()!=null) {
                        isRoot = false;
                        back = new FileForDaw(new File(daw.getParent()).toURI());
                    }else {
                        isRoot = true;
                    }
                }else {

                    daw = (FileForDaw) table.getModel().getValueAt(row,0);
                    back = new FileForDaw(new File(daw.getParent()).toURI());

                }
                FileDawTableModel model = new FileDawTableModel();
                FileForDaw [] files = daw.listFilesDaw();

                if (!isRoot) {
                    model.addRow(new FileForDaw("..UP.."),-1);
                    for (FileForDaw file : files) {
                        model.addRow(file, (int) file.length());
                    }
                }else {
                    for (FileForDaw file : rootDawsMass()) {
                        model.addRow(file, (int) file.length());
                    }
                }

                table.setModel(model);
                table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
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
    private FileForDaw [] rootDawsMass(){
        FileForDaw [] ffdm = new FileForDaw[File.listRoots().length];
        File [] fmass = File.listRoots();
        for (int i = 0; i < File.listRoots().length;i++){
            ffdm[i]= new FileForDaw(fmass[i].toString().substring(0,1));
        }

        return ffdm;
    }
}
