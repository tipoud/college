package Main;
import javax.swing.JCheckBox;

import tablesBDD.Eleve;

@SuppressWarnings("serial")
public class CheckBoxEleves extends JCheckBox  {
	private Eleve eleve;
	
	
	public CheckBoxEleves(Eleve eleve) {
		super();
		this.eleve = eleve;
		this.setText(eleve.toString());
	}


	public Eleve getEleve() {
		return eleve;
	}


	public void setEleve(Eleve eleve) {
		this.eleve = eleve;
	}
		
	
}
