package Administration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Main.Dat;
import tablesBDD.Domaine;
import tablesBDD.Objectif;

public class ObjDeleteActionListener implements ActionListener {

	Objectif objectif;
	
	public ObjDeleteActionListener(Objectif objectif) {
		this.objectif = objectif;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane jop = new JOptionPane();			
		@SuppressWarnings("static-access")
		int option = jop.showConfirmDialog(null, "Voulez-vous supprimer cet objectif?\nToutes les notes qui lui sont associées seront également supprimées","Suppression", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					
		if(option == JOptionPane.OK_OPTION){	
			
		 for (Objectif obj : Domaine.get(objectif.getIdDomaine()).getObjectifs()) {
			if (obj.getNumero()>objectif.getNumero()) {
				obj.setNumero(obj.getNumero()-1);
				Dat.objectifDAO.update(obj);	
			}
		}
		 
		 Dat.objectifDAO.delete(objectif); 
		 
		 Dat.matObjDAO.getAll();
		 Dat.domaineDAO.getAll();
		 Dat.noteDAO.getAll();
		 
		 Domaine domaine = Domaine.get(objectif.getIdDomaine());
		 domaine.setObjectifs();
		 System.out.println(domaine);
		 PanDomaine.getByDomaine(domaine).actualize();
		 
		}
		
	}

}
