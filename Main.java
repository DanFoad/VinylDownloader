import javax.swing.SwingUtilities;

/**
 * Main
 * ------
 * Main class for VinylDownloader
 *
 * @author Dan Foad
 * @version 1.0.0
 */
public class Main {
    public static void main(String[] args) {
        
        // Create GUI on EDT
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                // Main GUI object
                GUI gui = new GUI();
                
                // Activate main execution code on separate thread
                final Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        // ADD CODE
                        
                        // Shutdown hook
                        Runtime.getRuntime().addShutdownHook(new Thread() {
                            @Override
                            public void run() {
                                gui.close();
                            }
                        });
                    }
                };
                new Thread(r).start();
            }
        });
    }
}