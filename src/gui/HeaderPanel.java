package gui;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {
    private JLabel adres;
    private JLabel pass;
    private JTextField adresField;
    private JPasswordField passField;
    private JButton connectButon;
    private ImageIcon image;
    public HeaderPanel() {
        adres = new JLabel("Адрес: ");
        adresField = new JTextField(10);
        pass = new JLabel("Пароль");
        passField = new JPasswordField(10);
        connectButon = new JButton("Подключиться");
        image = new ImageIcon("E:\\javasoft\\WebDavSwing\\src\\img\\otkr_tsepi_32.png");
        add(adres);
        add(adresField);
        add(pass);
        add(passField);
        add(connectButon);
        add(new JLabel(image));

    }

    public JLabel getAdres() {
        return adres;
    }

    public JLabel getPass() {
        return pass;
    }

    public JTextField getAdresField() {
        return adresField;
    }

    public JPasswordField getPassField() {
        return passField;
    }

    public JButton getConnectButon() {
        return connectButon;
    }

    public ImageIcon getImage() {
        return image;
    }
}
