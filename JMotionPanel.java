import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.Point;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * JMotionPanel
 * ----------------
 * JPanel derivative that allows moving containing frame by
 * dragging on the JMotionPanel
 *
 * @author Dan Foad
 * @version 1.0.0
 */
public class JMotionPanel extends JPanel {
    
    private Point initialClick; // Location of initial click of drag
    private JFrame instance; // JFrame that contains the JMotionPanel

    /** JMotionPanel
     * Constructor to set parent instance and add listeners
     * @param JFrame instance     Instance of containing JFrame
     */
    public JMotionPanel(JFrame instance) {
        this.instance = instance;

        // Catch initial click of the drag
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                initialClick = evt.getPoint();
            }
        });

        // Catch dragging motion and calculate frame movement
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent evt) {

                // Get current window location
                int thisX = instance.getLocation().x;
                int thisY = instance.getLocation().y;

                // Get mouse delta since initial click
                int xMoved = (thisX + evt.getX()) - (thisX + initialClick.x);
                int yMoved = (thisY + evt.getY()) - (thisY + initialClick.y);

                // Move window accordingly
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                instance.setLocation(X, Y);
            }
        });
    }
}