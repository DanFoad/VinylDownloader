import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

import javax.swing.SwingUtilities;

import danfoad.util.FileUtil;
import danfoad.util.FileUtil.CSVResult;

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
                        
                        CSVResult result = FileUtil.readCSV("test.csv");
                        ArrayList<HashMap<String, String>> data = result.getData();
                        ArrayList<String> headers = result.getHeaders();
                        ArrayList<String> listData = new ArrayList<String>();
                        for (int i = 0; i < data.size(); i++) {
                            String title = data.get(i).get(headers.get(0));
                            String artist = data.get(i).get(headers.get(1));
                            String featured = data.get(i).get(headers.get(2));
                            
                            String datum = artist + " - " + title;
                            
                            if (featured.length() != 0)
                                datum = datum + " ft. " + featured;
                            
                            listData.add(datum);
                        }
                        
                        Object[] rawArray = listData.toArray();
                        gui.setLeftListData(Arrays.copyOf(rawArray, rawArray.length, String[].class));
                        
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