package Administration;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;

import Main.Dat;
import tablesBDD.Matiere;

@SuppressWarnings("serial")
public class ComboMatiereAdmin extends JComboBox<Matiere> {

public ComboMatiereAdmin() {
	super();

	for (Matiere mat : Dat.arrayMatieres) {
		this.addItem(mat);
	}
	this.addItemListener(new ItemListener() {
		
		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange()==ItemEvent.SELECTED) {
				ModifMatiere.matiere = (Matiere) event.getItem();		
				Administration.myModifMatiere.actualize();
				Administration.myModifMatiere.revalidate();
			}	// TODO Auto-generated method stub
			
		}
	});

}

}

