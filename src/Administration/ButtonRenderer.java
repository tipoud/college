package Administration;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class ButtonRenderer extends JButton implements TableCellRenderer{
 
   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row, int col) {

      setText((value != null) ? value.toString() : "");

      return this;
   }
}