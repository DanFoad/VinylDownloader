import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.Component;

public class JCustomListCellRenderer extends JLabel implements ListCellRenderer<Object> {
    
    public JCustomListCellRenderer() {
        setOpaque(true);
    }
    
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocuse) {
        setText(value.toString());
        setForeground(new Color(0x444444));
        setFont(new Font("Roboto", Font.PLAIN, 18));
        setBorder(new EmptyBorder(8, 16, 8, 0));
        
        if (isSelected) {
            setBackground(new Color(0xE0E0E0));
        } else {
            setBackground(new Color(0, 0, 0, 0));
        }
        
        return this;
    }
    
}