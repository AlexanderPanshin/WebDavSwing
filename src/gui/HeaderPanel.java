package gui;

import listiner.ButtonConnectListiner;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {
    private JLabel adres;
    private JLabel pass;
    private JLabel login;
    private JTextField loginField;
    private JTextField adresField;
    private JPasswordField passField;
    private JButton connectButon;
    private ImageIcon image;
    private static JLabel lbelIcon;
    public HeaderPanel() {
        adres = new JLabel("Адрес: ");
        adresField = new JTextField(10);
        adresField.getAccessibleContext().setAccessibleName("Введите адрес WebDav сервера");
        login = new JLabel("Логин: ");
        loginField = new JTextField(10);
        loginField.getAccessibleContext().setAccessibleName("Введите логин");
        pass = new JLabel("Пароль");
        passField = new JPasswordField(10);
        connectButon = new JButton("Подключиться");
        connectButon.addActionListener(new ButtonConnectListiner(this));
        image = new ImageIcon("E:\\javasoft\\WebDavSwing\\src\\img\\otkr_tsepi_32.png");
        lbelIcon = new JLabel(image);
        add(adres);
        add(adresField);
        add(login);
        add(loginField);
        add(pass);
        add(passField);
        add(connectButon);
        add(lbelIcon);

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

    public JTextField getLoginField() {
        return loginField;
    }

    public static void setImage(ImageIcon image){
        lbelIcon.setIcon(image);
    }
}
