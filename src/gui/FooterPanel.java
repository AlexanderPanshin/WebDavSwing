package gui;

import javax.swing.*;

public class FooterPanel extends JPanel {
    private JTextArea textArea;

    public FooterPanel() {
        textArea = new JTextArea(10,60);
        add(textArea);
    }

    public JTextArea getTextArea() {
        return textArea;
    }
}
