package Administration;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Consult.Consult;
import Main.Dat;
import tablesBDD.Domaine;
import tablesBDD.MatObj;
import tablesBDD.Matiere;
import tablesBDD.Objectif;

@SuppressWarnings("serial")
public class ModifMatiere  extends JPanel{
	JPanel top  = new JPanel(),
			panObjectifs = new JPanel(),
			global = new JPanel(),
			bottom = new JPanel();

	
	public static Matiere matiere = Dat.arrayMatieres.get(0);
	
	public static ArrayList<Objectif> arrObj = new ArrayList<>(); 
	Box boxCompetence = Box.createVerticalBox();
	
	JButton valider = new JButton("Valider les changements"),
			retablir = new JButton("Rétablir");
	
	ModifMatiere(){
		super();
	this.setLayout(new BorderLayout());
	this.setBackground(Color.GRAY);
	this.add(top, BorderLayout.NORTH);
	top.add(new ComboMatiereAdmin());
	global.setLayout(new BorderLayout());
	boxCompetence.setBackground(Color.white);
	global.setBackground(Color.white);
	this.add(global,BorderLayout.CENTER);
	this.add(bottom,BorderLayout.SOUTH);
	arrObj.clear();
	for (Objectif objectif : matiere.getObjectifs()) {
		arrObj.add(objectif);
	}
	bottom.add(valider);	
	bottom.add(retablir);
	
	retablir.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			actualize();
		}
	});
	
	valider.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			for (Objectif objectif : arrObj) {
				if(!matiere.getObjectifs().contains(objectif));{
					Dat.matObjDAO.create(new MatObj(matiere.getIdMatiere(), objectif.getIdObjectif()));
				}
			}
			for (Objectif objectif : matiere.getObjectifs()) {
				if(!arrObj.contains(objectif));{
					Dat.matObjDAO.delete(MatObj.get(matiere, objectif));
				}
			}
			Dat.matObjDAO.getAll();
			for (Matiere mat : Dat.arrayMatieres) {
				mat.setObjectifs();
			}
			actualize();
			//Consult.getInstance().updateComboMatiere();
			//Consult.getInstance().updateAllContent();

		}
	});
	
	actualize();
	}

	/**
	 * 
	 */
	protected void actualize() {
		global.removeAll();
		boxCompetence.removeAll();
		for (Domaine comp : Dat.arrayDomaines) {
			boxCompetence.add(new JLabel(" "));
			Box boxObjectif = Box.createVerticalBox();
			JPanel panObj = new JPanel();
			panObj.setLayout(new BorderLayout());
			panObj.setBackground(Color.white);
			panObj.setBorder(BorderFactory.createTitledBorder(comp.toString()));
			panObj.add(boxObjectif);
			if(comp.getObjectifs().isEmpty()){
				JLabel lab = new JLabel("        Aucun objectif n'est associé é cette compétence.");
				lab.setFont(new Font("Arial",Font.PLAIN, 12));
				boxObjectif.add(lab);
			}
			for (Objectif obj : comp.getObjectifs()) {
				CheckBoxObjectifInMat cb = new CheckBoxObjectifInMat(matiere, obj);
				cb.setBackground(Color.WHITE);
				boxObjectif.add(cb);
			}
			boxCompetence.add(panObj);
		}
		JScrollPane scrollObjectifs = new JScrollPane(boxCompetence);
		scrollObjectifs.setOpaque(false);
		scrollObjectifs.getViewport().setOpaque(false);
		global.add(scrollObjectifs, BorderLayout.CENTER);
		revalidate();
	}


}
