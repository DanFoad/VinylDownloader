import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonModel;

import java.awt.Cursor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Image;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * JTitlebarButton
 * ---------------
 * Titlebar button for custom titlebars for undecorated windows
 * Contains actions for closing, maximising, restoring and minimising
 * Requires importing JFrameMaximised even if not using
 *
 * @author Dan Foad
 * @version 1.1.0
 */
public class JTitlebarButton extends JButton implements MouseListener {
    
    // Globals
    private Dimension baseSize = new Dimension(16, 16); // Default size
    private Color hoverColour = new Color(0xE0E0E0); // Default hover colour
    private Color currentColour = new Color(0, 0, 0, 0); // Transparent
    private Image image; // Image for button
    private int[] padding = {0, 0, 0, 0}; // top, left, bottom, right
    
    private int action = -1; // Default action = no action
    private JFrame instance; // Main frame
    
    // Constants
    public final static int CLOSE = 0;
    public final static int MAXIMISE = 1;
    public final static int RESTORE = 2;
    public final static int MINIMISE = 3;
    
    /** JTitlebarButton
     * Constructor, set settings and defaults + add listeners
     * @param String iconURI            Relative path to image to use for button
     * @param int action                Action for click from list of constants
     * @param JFrameMaximised instance  Main frame instance containing button
     */
    public JTitlebarButton(String iconURI, int action, JFrame instance) {
        super();
        
        // Remove default settings
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setPreferredSize(baseSize);
        setOpaque(true);
        
        // Set background image
        image = new ImageIcon(this.getClass().getResource(iconURI)).getImage();
        
        // Pointer cursor on hover
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Set class member variables
        this.action = action;
        this.instance = instance;
        
        // Set self as listener
        addMouseListener(this);
    }
    
    /** JTitlebarButton
     * Constructor, set settings and defaults + add listeners
     * @param Image icon                Image object to use for button
     * @param int action                Action for click from list of constants
     * @param JFrameMaximised instance  Main frame instance containing button
     */
    public JTitlebarButton(Image icon, int action, JFrameMaximised instance) {
        super();
        
        // Remove default settings
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setPreferredSize(baseSize);
        setOpaque(true);
        
        // Set background image
        image = icon;
        
        // Pointer cursor on hover
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Set class member variables
        this.action = action;
        this.instance = instance;
        
        // Set self as listener
        addMouseListener(this);
    }
    
    /** JTitlebarButton::paintComponent
     * Draw button using Graphics2D
     * @param Graphics g    Graphics object to use for drawing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        // Draw button
        g2.setPaint(this.currentColour);
        g2.fillRect(0, 0, getWidth(), getHeight());
        
        // Add background image
        g2.drawImage(image, padding[1], padding[0], this);
    }
    
    /** JTitlebarButton::mouseEntered
     * Set hover background colour
     * @param MouseEvent evt    Mouse event
     */
    public void mouseEntered(MouseEvent evt) {
        this.currentColour = hoverColour;
        this.repaint();
    }

    /** JTitlebarButton::mouseExited
     * Set default background colour
     * @param MouseEvent evt    Mouse event
     */
    public void mouseExited(MouseEvent evt) {
        this.currentColour = new Color(0, 0, 0, 0);
        this.repaint();
    }
    
    /** JTitleBar::mouseClicked
     * Perform action based on action variable
     * @param MouseEvent evt    Mouse event
     */
    public void mouseClicked(MouseEvent evt) {
        switch (this.action) {
            case CLOSE:
                // Close window
                System.exit(0);
                break;
            case MAXIMISE:
                // Set window maximised
                instance.setExtendedState(instance.getExtendedState() | JFrame.MAXIMIZED_BOTH);
                this.action = RESTORE;
                setImage("/img/restore.png");
                break;
            case RESTORE:
                // Set window restored
                instance.setExtendedState(instance.getExtendedState() | JFrame.NORMAL);
                // Set to regular bounds
                if (instance instanceof JFrameMaximised)
                    ((JFrameMaximised)instance).setRestoredBounds();
                this.action = MAXIMISE;
                setImage("/img/maximise.png");
                break;
            case MINIMISE:
                // Set window minimised
                instance.setExtendedState(instance.getExtendedState() | JFrame.ICONIFIED);
                break;
        }
    }
    
    // Unused mouse events
    public void mouseReleased(MouseEvent evt) {}
    public void mousePressed(MouseEvent evt) {}
    
    /** JTitlebarButton::setPadding
     * Set padding around button, e.g. where image is within button
     * @param int top       Top padding in px
     * @param int left      Left padding in px
     * @param int bottom    Bottom padding in px
     * @param int right     Right padding in px
     */
    public void setPadding(int top, int left, int bottom, int right) {
        // Set button size
        setPreferredSize(new Dimension(
            left + baseSize.width + right,
            top + baseSize.height + bottom
        ));
        
        // Store padding for graphics painting
        padding[0] = top;
        padding[1] = left;
        padding[2] = bottom;
        padding[3] = right;
    }
    
    /** JTitlebarButton::setHoverBackground
     * Set background colour when hovering over button
     * @param Color color   Colour of background when hovering
     */
    public void setHoverBackground(Color color) {
        hoverColour = color;
    }
    
    /** JTitlebarButton::setImage
     * Set background image from location
     * @param String iconURI    Relative path to background image
     */
    public void setImage(String iconURI) {
        this.image = new ImageIcon(this.getClass().getResource(iconURI)).getImage();
        repaint();
    }
    
    /** JTitlebarButton::setImage
     * Set background image from image object
     * @param Image icon    Image object to use as background
     */
    public void setImage(Image icon) {
        this.image = icon;
        repaint();
    }
    
    /** JTitlebarButton::setAction
     * Set action to be performed on click from list of constants
     * @param int action    Action to be performed from list of constants
     */
    public void setAction(int action) {
        this.action = action;
    }
}