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
import tablesBDD.Eleve;
import tablesBDD.Note;

public class DeleteEleveButtonEditor extends DefaultCellEditor {

	private static final long serialVersionUID = 1L;
	DAO<Eleve> eleveDAO = Dat.eleveDAO;
	DAO<Note> noteDAO = Dat.noteDAO;
	protected JButton button;
	private DeleteButtonListener bListener = new DeleteButtonListener();

	public DeleteEleveButtonEditor(JCheckBox checkBox) {
		//Par défaut, ce type d'objet travaille avec un JCheckBox
		super(checkBox);
		//On crée é nouveau notre bouton
		button = new JButton();
		button.setOpaque(true);
		//On lui attribue un listener
		button.addActionListener(bListener);
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		//On définit le numéro de ligne é notre listener
		bListener.setRow(row);
		//On passe aussi en paramétre le tableau pour des actions potentielles
		bListener.setTable(table);
		//On réaffecte le libellé au bouton
		button.setText( (value ==null) ? "" : value.toString() );
		//On renvoie le bouton
		return button;
	}

	class DeleteButtonListener implements ActionListener {

		private int row;
		private JTable table;

		public void setRow(int row){this.row = row;}
		public void setTable(JTable table){this.table = table;}

		public void actionPerformed(ActionEvent event) {		
			int option = JOptionPane.showConfirmDialog(null, "Etes-vous sér de vouloir supprimer cet éléve ainsi que toutes ses notes?", "Suppression de l'éléve", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

			if(option == JOptionPane.OK_OPTION) {
				if (row<ModifEleve.MyArrayEleves.size()) {
					Eleve eleve = ModifEleve.MyArrayEleves.get(row);
					eleveDAO.delete(eleve);
					Dat.eleveDAO.getAll();
					ModifEleve.actualizeContent();
					Administration.myModifEleve.revalidate();
				}
			}



		}
	}        
}