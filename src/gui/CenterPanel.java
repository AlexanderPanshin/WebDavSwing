package gui;

import listiner.DeletToDawListiner;
import listiner.DeletToLOcalListiner;
import listiner.SaveToDawListiner;

import javax.swing.*;

public class CenterPanel extends JPanel {
    private static JButton saveToDaw;
    private static JButton loadTolocal;
    private static JButton deletToDaw;
    private static JButton deletToLocal;

    public CenterPanel(LeftPanel leftPanel, RightPanel rightPanel) {
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        saveToDaw = new JButton("Сохранить в Daw ->");
        saveToDaw.setAlignmentX(CENTER_ALIGNMENT);
        saveToDaw.addActionListener(new SaveToDawListiner(rightPanel,leftPanel));
        loadTolocal = new JButton("<- Загрузить на ПК");
        loadTolocal.setAlignmentX(CENTER_ALIGNMENT);
        deletToDaw = new JButton("Удалить с Daw ->");
        deletToDaw.addActionListener(new DeletToDawListiner(rightPanel,leftPanel));
        deletToDaw.setAlignmentX(CENTER_ALIGNMENT);
        deletToLocal = new JButton("<- Удалить с ПК");
        deletToLocal.addActionListener(new DeletToLOcalListiner(rightPanel,leftPanel));
        deletToLocal.setAlignmentX(CENTER_ALIGNMENT);
        add(saveToDaw);
        add(loadTolocal);
        add(deletToDaw);
        add(deletToLocal);
    }
}
