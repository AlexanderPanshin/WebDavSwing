package listiner;

import com.github.sardine.DavResource;
import gui.FooterPanel;
import gui.HeaderPanel;
import gui.LeftPanel;
import gui.RightPanel;
import logik.StackPathDaw;
import logik.StackPathLocal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class LoadToLOcalListiner implements ActionListener {
    private RightPanel rp;
    private LeftPanel lp;
    private JTable localTable;
    private JTable dawTable;
    public LoadToLOcalListiner(RightPanel rightPanel, LeftPanel leftPanel) {
        rp = rightPanel;
        lp = leftPanel;
        localTable = lp.getTable();
        dawTable = rp.getTable();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(dawTable.getSelectedRow() != -1){
            String dawNameString = dawTable.getModel().getValueAt(dawTable.getSelectedRow(), 0).toString();
            String dawPathString = ButtonConnectListiner.home + StackPathDaw.peekPath() + dawNameString;
            String localPathString = StackPathLocal.stringPath() + '\\' + dawNameString;
            FooterPanel.addText("(LOAD TO LOCAL) Попытка сохранить файл из " + dawPathString + " в " + localPathString + System.lineSeparator());
            downloadFile(dawPathString,localPathString);
            FooterPanel.addText("(LOAD TO LOCAL) Файл успешно сохранен в : " + localPathString + System.lineSeparator());
        }
    }

    private void downloadFile(String dawPath, String localPath) {
        try {
            InputStream in = ButtonConnectListiner.sardine.get(dawPath);
            OutputStream out = new FileOutputStream(localPath);
            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
            in.close();
            out.close();
        } catch(Exception ex) {
           FooterPanel.addText("(LOAD TO LOCAL) Ошибка записи : " + ex.getMessage() + System.lineSeparator());
        }
    }
}
