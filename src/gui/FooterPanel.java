package gui;

import javax.swing.*;

public class FooterPanel extends JPanel {
    private static JTextArea textArea;

    public FooterPanel() {
        textArea = new JTextArea(10,60);
        add(textArea);
    }

    public static JTextArea getTextArea() {
        return textArea;
    }
    public static void addText(String s){
        String temp = textArea.getText();
        textArea.setText(temp + " " + s);
    }
}
