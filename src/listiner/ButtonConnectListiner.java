package listiner;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;
import gui.BasePanel;
import gui.HeaderPanel;
import logik.FileDawTableModel;
import logik.FileForDaw;
import logik.FileForRes;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class ButtonConnectListiner implements ActionListener {
    public static Sardine sardine;
    public static String home;
    private HeaderPanel panel;
    private JTable table;

    public ButtonConnectListiner(HeaderPanel panel) {
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String URL = panel.getAdresField().getText();
        home = URL;
        String login = panel.getLoginField().getText();
        String pswd = new String(panel.getPassField().getPassword());
        sardine = SardineFactory.begin(login, pswd);
        table = BasePanel.getRightPanel().getTable();

        FileDawTableModel model = new FileDawTableModel();
        try {
            List<DavResource> resource = sardine.getResources(URL);
            for (DavResource res : resource) {
                if(!res.isDirectory()) {
                    model.addRow(new FileForDaw(res.getName()), bytesToMegabytes(res.getContentLength()));
                }else {
                    model.addRow(new FileForDaw(res.getName()), -2);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        table.setModel(model);
        ListMouseAdapterDaw.init();
    }
    public int bytesToMegabytes(long bytes) {
        int megabytes = (int) (bytes / (1024 * 1024));
        return megabytes;
    }
}
