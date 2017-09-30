package Administration;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;

import tablesBDD.Domaine;
import tablesBDD.Matiere;
import tablesBDD.Objectif;

@SuppressWarnings("serial")
public class CheckBoxObjectifInMat extends JCheckBox  {
	private Objectif objectif;
	private Matiere matiere;
	String nom, detailsNiveaux;

	public CheckBoxObjectifInMat(Matiere mat, Objectif obj) {
		super();
		this.objectif = obj;
		this.matiere = mat;
		Domaine comp = Domaine.get(objectif.getIdDomaine());
		nom = comp.getNumero()+"."+obj.getNumero() + " " + obj.getNom();
		detailsNiveaux = comp.toString() + "\n\nNiveau 1 : " + obj.getNiveau1()
		+"\nNiveau 2 : " + obj.getNiveau2()
		+"\nNiveau 3 : " + obj.getNiveau3()
		+"\nNiveau 4 : " + obj.getNiveau4();
		this.setFont(new Font("Arial",Font.PLAIN, 12));
		
		
		this.setText(nom);
		
		if(matiere.getObjectifs().contains(objectif)){
			this.setSelected(true);
		}
		
		this.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange()==ItemEvent.SELECTED) {
					ModifMatiere.arrObj.add(objectif);
				}else{
					ModifMatiere.arrObj.remove(objectif);
				}
				
			}});
	}




	
}
