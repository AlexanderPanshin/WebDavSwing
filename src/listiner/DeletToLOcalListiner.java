package listiner;

import gui.FooterPanel;
import gui.LeftPanel;
import gui.RightPanel;
import logik.FileForDaw;
import logik.StackPathDaw;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeletToLOcalListiner implements ActionListener {

    private RightPanel rp;
    private LeftPanel lp;
    private JTable localTable;
    private JTable dawTable;

    public DeletToLOcalListiner(RightPanel rightPanel, LeftPanel leftPanel) {
        rp = rightPanel;
        lp = leftPanel;
        localTable = lp.getTable();
        dawTable = rp.getTable();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int localSelected = localTable.getSelectedRow();
        if (localSelected != -1) {
            FileForDaw localFile = (FileForDaw) localTable.getValueAt(localSelected,0);
            FooterPanel.addText("(Local) Попытка удалить файл " + localFile.getName() + System.lineSeparator());
            FooterPanel.addText("(LOCAL) Файл удален = " + localFile.delete());
        }
    }
    public void updateTable(){

    }
}
