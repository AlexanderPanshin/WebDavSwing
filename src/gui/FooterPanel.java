package gui;

import javax.swing.*;
import java.awt.*;

public class FooterPanel extends JPanel {
    private static JTextArea textArea;
    private static JScrollPane scrollPane;

    public FooterPanel() {
        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(600,200));
        textArea = new JTextArea(10,50);
        textArea.getAccessibleContext().setAccessibleName("Log");
        textArea.setEditable(false);
        scrollPane.setViewportView(textArea);
        add(scrollPane);
    }

    public static JTextArea getTextArea() {
        return textArea;
    }
    public static void addText(String s){
        String temp = textArea.getText();
        textArea.setText(temp + " " + s);
    }
}
