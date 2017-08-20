import javax.swing.plaf.basic.BasicScrollBarUI;

import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JComponent;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Dimension;

public class JCustomScrollBarUI extends BasicScrollBarUI {
    
    public JCustomScrollBarUI() {
        super();
    }
    
    public void paintThumb(Graphics g, JComponent c, Rectangle bounds) {
        Graphics2D g2 = (Graphics2D)g;
        
        int width = bounds.width;
        int height = bounds.height;
        
        g.translate(bounds.x, bounds.y);
        g2.setColor(new Color(0x444444));
        g2.fillRect(2, 2, width - 4, height - 4);
        g2.drawRect(2, 2, width - 4, height - 4);
    }
    
    public void paintTrack(Graphics g, JComponent c, Rectangle bounds) {
        super.paintTrack(g, c, bounds);
        
    }
    
    @Override
    protected JButton createDecreaseButton(int orientation) {
        return emptyButton();
    }
    
    @Override
    protected JButton createIncreaseButton(int orientation) {
        return emptyButton();
    }
    
    private JButton emptyButton() {
        JButton button = new JButton();
        
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
    }
}