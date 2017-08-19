import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Image;
import java.awt.Frame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI extends JFrameMaximised {
    
    public GUI() {
        super("VinylDownloader");
        initGUI();
    }
    
    private JPanel mainPanel;
    
    public void initGUI() {
        
        // Frame settings
        setUndecorated(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200, 800));
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);
        
        initTitlebar();
        initBody();
        
        pack();
        setVisible(true);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }
    
    public void initTitlebar() {
        JMotionPanel titlebar = new JMotionPanel(this);
        titlebar.setLayout(new BorderLayout(0, 16));
        titlebar.setPreferredSize(new Dimension(mainPanel.getWidth(), 32));
        titlebar.setBackground(new Color(0xEFEFEF));
        
        JPanel titlebarButtons = new JPanel(new GridLayout(1, 3));
        titlebarButtons.setBackground(new Color(0xEFEFEF));
        
        JTitlebarButton close = new JTitlebarButton("/img/close.png", JTitlebarButton.CLOSE, this);
        JTitlebarButton maximise = new JTitlebarButton("/img/restore.png", JTitlebarButton.RESTORE, this);
        JTitlebarButton minimise = new JTitlebarButton("/img/minimise.png", JTitlebarButton.MINIMISE, this);
        
        titlebarButtons.add(minimise);
        titlebarButtons.add(maximise);
        titlebarButtons.add(close);
        
        close.setPadding(8, 16, 0, 16);
        minimise.setPadding(8, 16, 0, 16);
        maximise.setPadding(8, 16, 0, 16);
        
        close.setHoverBackground(new Color(0xD9534F));
        
        titlebar.add(titlebarButtons, BorderLayout.LINE_END);
        mainPanel.add(titlebar, BorderLayout.NORTH);
    }
    
    public void initBody() {
        JPanel body = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        body.setBackground(Color.WHITE);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.5;
        
        JLabel label = new JLabel("testo");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        label.setOpaque(true);
        label.setBackground(Color.BLUE);
        body.add(label, c);
        
        JLabel label2 = new JLabel("testo");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 0;
        c.ipady = 30;
        c.gridwidth = 3;
        label2.setOpaque(true);
        label2.setBackground(Color.BLACK);
        body.add(label2, c);
        
        mainPanel.add(body, BorderLayout.CENTER);
    }
    
    public void close() {
        
    }
    
}