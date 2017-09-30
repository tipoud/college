package Administration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Main.Dat;
import tablesBDD.Domaine;

public class CompDeleteActionListener implements ActionListener {

	private Domaine domaine;
	
	public CompDeleteActionListener(Domaine domaine) {
		this.domaine = domaine;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane jop = new JOptionPane();			
		@SuppressWarnings("static-access")
		int option = jop.showConfirmDialog(null, "Voulez-vous vraiment supprimer cette compétence ?\nToutes les notes qui lui sont associées seront également supprimées", "Suppression de la compétence", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					
		if(option == JOptionPane.OK_OPTION){
		 
		 for (Domaine comp : Dat.arrayDomaines) {
			if (comp.getNumero()>domaine.getNumero()) {
				comp.setNumero(comp.getNumero()-1);
				Dat.domaineDAO.update(comp);		
			}
		}
		 Dat.domaineDAO.delete(domaine);
		 Dat.matObjDAO.getAll();
		 Dat.domaineDAO.getAll();
		 Dat.evalDAO.getAll();
		 Dat.noteDAO.getAll();
		
		 Administration.actualize();
		}
		
	}
}
