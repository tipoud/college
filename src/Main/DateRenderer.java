package Main;
import java.awt.Component;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.toedter.calendar.JDateChooser;

public class DateRenderer extends JDateChooser implements TableCellRenderer{

	private static final long serialVersionUID = 1L;
	Date inDate;

	@Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        // TODO Auto-generated method stub

        if (value instanceof Date){
            this.setDate((Date) value);
        } else if (value instanceof Calendar){
            this.setCalendar((Calendar) value);
        } else
        {
        	return new JDateChooser();
        }
        return this;
        
    }
}