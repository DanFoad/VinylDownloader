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

public class JTitlebarButton extends JButton implements MouseListener {
    
    private Dimension baseSize = new Dimension(16, 16);
    private Color hoverColour = new Color(0xE0E0E0);
    private Color currentColour = new Color(0, 0, 0, 0);
    private Image image;
    private int[] padding = {0, 0, 0, 0};
    
    private int action = -1;
    private GUI instance;
    
    public final static int CLOSE = 0;
    public final static int MAXIMISE = 1;
    public final static int RESTORE = 2;
    public final static int MINIMISE = 3;
    
    
    public JTitlebarButton(String iconURI, int action, GUI instance) {
        super();
        
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setPreferredSize(baseSize);
        setOpaque(true);
        image = new ImageIcon(this.getClass().getResource(iconURI)).getImage();
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        this.action = action;
        this.instance = instance;
        addMouseListener(this);
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setPaint(this.currentColour);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.drawImage(image, padding[1], padding[0], this);
    }
    
    public void mouseEntered(MouseEvent evt) {
        this.currentColour = hoverColour;
        this.repaint();
    }

    public void mouseExited(MouseEvent evt) {
        this.currentColour = new Color(0, 0, 0, 0);
        this.repaint();
    }
    
    public void mouseClicked(MouseEvent evt) {
        switch (this.action) {
            case CLOSE:
                instance.close();
                System.exit(0);
                break;
            case MAXIMISE:
                instance.setExtendedState(instance.getExtendedState() | JFrame.MAXIMIZED_BOTH);
                this.action = RESTORE;
                setImage("/img/restore.png");
                break;
            case RESTORE:
                instance.setExtendedState(instance.getExtendedState() | JFrame.NORMAL);
                this.action = MAXIMISE;
                setImage("/img/maximise.png");
                break;
            case MINIMISE:
                instance.setExtendedState(instance.getExtendedState() | JFrame.ICONIFIED);
                break;
        }
    }
    
    public void mouseReleased(MouseEvent evt) {}
    public void mousePressed(MouseEvent evt) {}
    
    public void setPadding(int top, int left, int bottom, int right) {
        setPreferredSize(new Dimension(
            left + baseSize.width + right,
            top + baseSize.height + bottom
        ));
        padding[0] = top;
        padding[1] = left;
        padding[2] = bottom;
        padding[3] = right;
    }
    
    public void setHoverBackground(Color color) {
        hoverColour = color;
    }
    
    public void setImage(String iconURI) {
        this.image = new ImageIcon(this.getClass().getResource(iconURI)).getImage();
        repaint();
    }
    
    public void setAction(int action) {
        this.action = action;
    }
}