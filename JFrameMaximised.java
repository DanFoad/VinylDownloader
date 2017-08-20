import javax.swing.JFrame;

import java.awt.Rectangle;
import java.awt.Frame;
import java.awt.Insets;

import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;

/**
 * JFrameMaximised
 * ---------------
 * Circumvents bug wherein undecorated windows that are maximised
 * cover the taskbar
 *
 * @author Dan Foad
 * @version 1.2.0
 */
public class JFrameMaximised extends JFrame {
    
    private Rectangle maxBounds; // Max bounds of frame
    private Rectangle restoredBounds; // Regular bounds of frame
    
    /** JFrameMaximised::JFrameMaximised
     * Default constructor, initialise maxBounds as null
     */
    public JFrameMaximised() {
        this("");
    }
    
    /** JFrameMaximised::JFrameMaximised
     * Constructor, initialise maxBounds as null and set title
     * @param String title  Title of window
     */
    public JFrameMaximised(String title) {
        super(title);
        maxBounds = null;
        
        // Defaults for restored window
        restoredBounds = new Rectangle(0, 0, 1200, 800);
        
        // Listen for movement/resize and store new bounds
        addComponentListener(new ComponentListener() {
            @Override
            public void componentHidden(ComponentEvent e) {}
            
            @Override
            public void componentShown(ComponentEvent e) {}
            
            @Override
            public void componentResized(ComponentEvent e) {
                // If not maximised, store new bounds
                if (getExtendedState() == JFrame.NORMAL) {
                    restoredBounds = getBounds();
                }
            }
            
            @Override
            public void componentMoved(ComponentEvent e) {
                // If not maximised, store new bounds
                if (getExtendedState() == JFrame.NORMAL) {
                    restoredBounds = getBounds();
                }
            }
        });
    }
    
    /** JFrameMaximised::getMaximisedBounds
     * Getter for max bounds of screen
     * @return Rectangle    Maximum bounds of window
     */
    public Rectangle getMaximisedBounds() {
        return maxBounds;
    }
    
    /** JFrameMaximised::setMaximisedBounds
     * Setter for max bounds of screen
     * @param Rectangle maxBounds   Maximum bounds of window
     */
    public synchronized void setMaximisedBounds(Rectangle maxBounds) {
        this.maxBounds = maxBounds;
        super.setMaximizedBounds(maxBounds);
    }
    
    /** JFrameMaximised::setRestoredBounds
     * Set window to use restored bounds
     */
    public synchronized void setRestoredBounds() {
        setBounds(restoredBounds);
    }
    
    /** JFrameMaximised::setExtendedState
     * Set the extended state of the frame, e.g. maximised
     * @param int state     State to set
     */
    public synchronized void setExtendedState(int state) {
        
        // If maximised
        if (maxBounds == null && (state & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
            // Get screen sizes
            Insets screenInsets = getToolkit().getScreenInsets(getGraphicsConfiguration());
            Rectangle screenSize = getGraphicsConfiguration().getBounds();
            
            // Set max bounds for maximised window taking into account taskbar
            Rectangle maxBounds = new Rectangle(
                screenInsets.left + screenSize.x,
                screenInsets.top + screenSize.y,
                screenSize.x + screenSize.width - screenInsets.right - screenInsets.left,
                screenSize.y + screenSize.height - screenInsets.bottom - screenInsets.top
            );
            super.setMaximizedBounds(maxBounds);
        }
        
        super.setExtendedState(state);
    }
    
}