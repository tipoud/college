package Administration;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import DAO.DAO;
import Main.Dat;
import tablesBDD.Classe;
import tablesBDD.Eleve;
import tablesBDD.Note;

public class DeleteClasseButtonEditor extends DefaultCellEditor {
   

	private static final long serialVersionUID = 1L;
	DAO<Eleve> eleveDAO = Dat.eleveDAO;
	DAO<Note> noteDAO = Dat.noteDAO;
    protected JButton button;
    private DeleteButtonListener bListener = new DeleteButtonListener();
    
   public DeleteClasseButtonEditor(JCheckBox checkBox) {
      super(checkBox);
      button = new JButton();
      button.setOpaque(true);
       button.addActionListener(bListener);
   }
 
   public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
      bListener.setRow(row);
      bListener.setTable(table);
      button.setText( (value ==null) ? "" : value.toString() );
       return button;
   }
    
   class DeleteButtonListener implements ActionListener {
         
        private int row;
        private JTable table;
         
        public void setRow(int row){this.row = row;}
        public void setTable(JTable table){this.table = table;}
         
        public void actionPerformed(ActionEvent event) {	
        	int option = JOptionPane.showConfirmDialog(null, "Etes-vous sér de vouloir supprimer cette classe ainsi que tous ces éléves?", "Suppression de l'éléve", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        	if(option == JOptionPane.OK_OPTION) {
                if(table.getRowCount() > 0){
                    ((ZModel)table.getModel()).removeRow(this.row);
                    if (row<Dat.arrayClasses.size()) {
					Classe classe =Dat.arrayClasses.get(row);
					Dat.classeDAO.delete(classe);
					Dat.classeDAO.getAll();
					Dat.evalDAO.getAll();

					}
                    
                 }
        	}
        	
        	

      }
   }        
}