package gui;

import javax.swing.*;

public class CenterPanel extends JPanel {
    private static JButton saveToDaw;
    private static JButton loadTolocal;
    private static JButton deletToDaw;
    private static JButton deletToLocal;

    public CenterPanel() {
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        saveToDaw = new JButton("Сохранить в Daw ->");
        saveToDaw.setAlignmentX(CENTER_ALIGNMENT);
        loadTolocal = new JButton("<- Загрузить на ПК");
        loadTolocal.setAlignmentX(CENTER_ALIGNMENT);
        deletToDaw = new JButton("Удалить с Daw ->");
        deletToDaw.setAlignmentX(CENTER_ALIGNMENT);
        deletToLocal = new JButton("<- Удалить с ПК");
        deletToLocal.setAlignmentX(CENTER_ALIGNMENT);
        add(saveToDaw);
        add(loadTolocal);
        add(deletToDaw);
        add(deletToLocal);
    }
}
