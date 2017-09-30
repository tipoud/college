package Administration;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Main.Dat;
import tablesBDD.Enseignant;

@SuppressWarnings("serial")
public class Administration extends JPanel {
	
	Enseignant enseignant = Dat.enseignant;
	public static JTabbedPane onglet;
	public static ModifObjectif myModifObjectif;
	public static ModifMatiere myModifMatiere;
	public static ModifClasse myModifClasse;
	public static ModifEleve myModifEleve;
	
 
	
 	public Administration(){
 		super();
 		this.setLayout(new BorderLayout());
 		onglet = new JTabbedPane(JTabbedPane.LEFT);		
 		actualize();
 		this.add(onglet, BorderLayout.CENTER);
 
 	}

public static void actualize(){

	Administration.myModifObjectif = new ModifObjectif();
	Administration.myModifMatiere = new ModifMatiere();
	Administration.myModifClasse = new ModifClasse();
	Administration.myModifEleve = new ModifEleve();
	Administration.onglet.removeAll();
	Administration.onglet.add("Objectifs",Administration.myModifObjectif);
	Administration.onglet.add("Matiéres",Administration.myModifMatiere );
	Administration.onglet.add("Classes",Administration.myModifClasse);
	Administration.onglet.add("Eléves",Administration.myModifEleve);
	onglet.addChangeListener(new ChangeListener() {
		
		@Override
		public void stateChanged(ChangeEvent e) {
			if (e.getSource() instanceof JTabbedPane) {
                JTabbedPane pane = (JTabbedPane) e.getSource();
                if(pane.getSelectedIndex()==3){
                	ModifEleve.actualizeTop(ModifEleve.classe);
                }
            }
		}
	});
}

}