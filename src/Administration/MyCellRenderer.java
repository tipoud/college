package Administration;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class MyCellRenderer extends DefaultTableCellRenderer {
 
        private static final long serialVersionUID = 1L;
        
    	public MyCellRenderer() {
    		setHorizontalAlignment(SwingConstants.CENTER);
    		setVerticalAlignment(SwingConstants.CENTER);
    	}
        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {
 
            if (value instanceof JComboBox) {
                return (JComboBox) value;
            }
            if (value instanceof Boolean) {
                JCheckBox cb = new JCheckBox();
                cb.setSelected(((Boolean) value).booleanValue());
                return cb;
            }
            if (value instanceof JCheckBox) {
                return (JCheckBox) value;
            }
            if (value instanceof JButton) {
            	return (JButton) value;
            }
                
            return new JTextField(value.toString());
            
        }
 
    }