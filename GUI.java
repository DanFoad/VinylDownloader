import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.CompoundBorder;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Image;
import java.awt.Frame;
import java.awt.Font;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// https://github.com/DanFoad/JavaFunctions
import danfoad.util.ui.JFrameMaximised;
import danfoad.util.ui.JMotionPanel;
import danfoad.util.ui.JTitlebarButton;

/** 
 * GUI
 * -----
 * Main GUI object for window
 *
 * Requires danfoad.util
 * https://github.com/DanFoad/JavaFunctions
 *
 * @author Dan Foad
 * @version 1.3.0
 */
public class GUI extends JFrameMaximised {
    
    // Globals
    private JPanel mainPanel; // JFrame content pane
    private JList<String> leftList; // Left sidebar list
    
    /** GUI
     * Constructor, set title and initialise GUI
     */
    public GUI() {
        super("VinylDownloader");
        initGUI();
    }
    
    /** GUI::initGUI
     * Initialise the frame settings and add the content pane
     * Set default to maximised
     */
    public void initGUI() {
        
        // Frame settings
        setUndecorated(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200, 800));
        
        // Create main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);
        
        // Set up contents
        initTitlebar();
        initBody();
        
        // Set window visible and maximised
        pack();
        setVisible(true);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }
    
    /** GUI::initTitlebar
     * Initialise the titlebar components and titlebar itself
     */
    public void initTitlebar() {
        // Create and setup titlebar container
        JMotionPanel titlebar = new JMotionPanel(this);
        titlebar.setLayout(new BorderLayout(0, 16));
        titlebar.setPreferredSize(new Dimension(mainPanel.getWidth(), 32));
        titlebar.setBackground(new Color(0xEFEFEF));
        
        // Create and setup titlebar buttons container
        JPanel titlebarButtons = new JPanel(new GridLayout(1, 3));
        titlebarButtons.setBackground(new Color(0xEFEFEF));
        
        // Create and setup titlebar buttons
        JTitlebarButton close = new JTitlebarButton("/img/close.png", JTitlebarButton.CLOSE, this);
        JTitlebarButton maximise = new JTitlebarButton("/img/restore.png", JTitlebarButton.RESTORE, this);
        JTitlebarButton minimise = new JTitlebarButton("/img/minimise.png", JTitlebarButton.MINIMISE, this);
        
        // Add buttons to container
        titlebarButtons.add(minimise);
        titlebarButtons.add(maximise);
        titlebarButtons.add(close);
        
        // Set up button padding
        close.setPadding(8, 16, 0, 16);
        minimise.setPadding(8, 16, 0, 16);
        maximise.setPadding(8, 16, 0, 16);
        
        // Make close button hover colour red
        close.setHoverBackground(new Color(0xD9534F));
        
        // Add components to their parents
        titlebar.add(titlebarButtons, BorderLayout.LINE_END);
        mainPanel.add(titlebar, BorderLayout.NORTH);
    }
    
    /** GUI::initBody
     * Initialise the body components
     */
    public void initBody() {
        // Create and setup body container
        JPanel body = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        body.setBackground(Color.WHITE);
        body.setPreferredSize(new Dimension(mainPanel.getWidth(), mainPanel.getHeight()));
        
        // Main GridBagConstraints
        c.fill = GridBagConstraints.HORIZONTAL; // Stretch horizontally
        c.weighty = 0.5; // Take vertical space
        
        // Setup left panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        c.weightx = 0.2; // Take up 20% of width
        c.fill = GridBagConstraints.BOTH; // Take full height
        c.gridx = 0;
        c.gridy = 0;
        leftPanel.setOpaque(true);
        leftPanel.setBackground(new Color(0xE0E0E0));
        
        JLabel leftTitle = new JLabel("SONG LIST");
        leftTitle.setBackground(new Color(0, 0, 0, 0));
        leftTitle.setBorder(new CompoundBorder(new MatteBorder(0, 0, 2, 0, new Color(0xCCCCCC)), new EmptyBorder(24, 16, 24, 0)));
        leftTitle.setFont(new Font("Roboto", Font.BOLD, 22));
        
        leftList = new JList<>();
        leftList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        leftList.setLayoutOrientation(JList.VERTICAL);
        leftList.setCellRenderer(new JCustomListCellRenderer());
        leftList.setVisibleRowCount(-1);
        JScrollPane leftScrollPane = new JScrollPane(leftList);
        leftScrollPane.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
        );
        leftScrollPane.getVerticalScrollBar().setUI(new JCustomScrollBarUI());
        leftScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        
        leftPanel.add(leftTitle, BorderLayout.NORTH);
        leftPanel.add(leftScrollPane, BorderLayout.CENTER);
        body.add(leftPanel, c);
        
        // Setup centre panel
        JPanel centrePanel = new JPanel();
        c.weightx = 0.6; // Take up 60% space
        c.fill = GridBagConstraints.BOTH; // Take full height
        c.gridx = 1;
        c.gridy = 0;
        centrePanel.setOpaque(true);
        centrePanel.setBackground(Color.BLACK);
        body.add(centrePanel, c);
        
        // Setup right panel
        JPanel rightPanel = new JPanel();
        c.weightx = 0.2; // Take up 20% space
        c.fill = GridBagConstraints.BOTH; // Take full height
        c.gridx = 2;
        c.gridy = 0;
        rightPanel.setOpaque(true);
        rightPanel.setBackground(Color.RED);
        body.add(rightPanel, c);
        
        // Add body to content pane
        mainPanel.add(body, BorderLayout.CENTER);
    }
    
    public void setLeftListData(String[] data) {
        leftList.setListData(data);
    }
    
    /** GUI::close
     * Operations to be performed before exitting system
     */
    public void close() {
        System.out.println("Closing VinylDownloader..");
    }
    
}