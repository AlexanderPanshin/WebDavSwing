package gui;

import javax.swing.*;
import java.awt.*;

public class BasePanel extends JFrame {
    private static HeaderPanel headerPanel;
    private static LeftPanel leftPanel;
    private static  CenterPanel centerPanel;
    private static RightPanel rightPanel;
    private static FooterPanel footerPanel;
    public BasePanel() throws HeadlessException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("WebDavConnector");
        setSize(new Dimension(700,500));
        setLayout(new BorderLayout());
        setVisible(true);
        headerPanel = new HeaderPanel();
        add(headerPanel,BorderLayout.NORTH);
        leftPanel = new LeftPanel();
        rightPanel = new RightPanel();
        add(leftPanel, BorderLayout.WEST);
        centerPanel = new CenterPanel(leftPanel,rightPanel);
        add(centerPanel,BorderLayout.CENTER);
        add(rightPanel,BorderLayout.EAST);
        footerPanel = new FooterPanel();
        add(footerPanel,BorderLayout.SOUTH);
    }

    public static HeaderPanel getHeaderPanel() {
        return headerPanel;
    }

    public static LeftPanel getLeftPanel() {
        return leftPanel;
    }

    public static CenterPanel getCenterPanel() {
        return centerPanel;
    }

    public static RightPanel getRightPanel() {
        return rightPanel;
    }

    public static FooterPanel getFooterPanel() {
        return footerPanel;
    }
}
