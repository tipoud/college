package Administration;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

public class JTableUtilities
{
    public static void setCellsAlignment(JTable table,int i, int alignment)
    {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(alignment);

        TableModel tableModel = table.getModel();

            table.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);

    }
}