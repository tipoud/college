package Administration;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import DAO.DAO;
import Main.Dat;
import tablesBDD.Domaine;
import tablesBDD.Objectif;

@SuppressWarnings("serial")
public class MoveObjectif extends JFrame  {
	JPanel global = new JPanel(), top = new JPanel(), bottom = new JPanel();
	DAO<Objectif> objectifDAO = Dat.objectifDAO;
	private JLabel nom = new JLabel();
	JComboBox<Domaine> comboCompetences = new JComboBox<>();
	JButton valider = new JButton("Valider");
	JButton annuler = new JButton("Annuler");
	private Objectif objectif;
	
	public MoveObjectif(Objectif objectif){
		super();
		this.setTitle("Déplacer l'objectif");
		this.setSize(800, 200);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setLayout(new BorderLayout());
		this.objectif=objectif;
		global.setLayout(new BorderLayout());
		nom.setText(objectif.getNom());
		global.add(comboCompetences,BorderLayout.CENTER);
		
		comboCompetences.setBorder(BorderFactory.createTitledBorder("Déplacer vers quel domaine??"));
		
		for (Domaine comp : Dat.arrayDomaines) {
			comboCompetences.addItem(comp);
		}
	
		bottom.add(valider);
		bottom.add(annuler);
		
		annuler.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		valider.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Domaine domaine = (Domaine) comboCompetences.getSelectedItem();
				
				for (Objectif obj : Domaine.get(objectif.getIdDomaine()).getObjectifs()) {
					if (obj.getNumero()>objectif.getNumero()) {
						obj.setNumero(obj.getNumero()-1);
						Dat.objectifDAO.update(obj);	
					}
				}
				 objectif.setIdCompetence(domaine.getIdDomaine());
				 objectif.setNumero(domaine.getObjectifs().size()+1);
				 Dat.objectifDAO.update(objectif); 		 
				 Dat.matObjDAO.getAll();
				 Dat.objectifDAO.getAll();
				 ModifObjectif.actualize();
				 dispose();
			}
		});
		global.add(nom, BorderLayout.NORTH);
		global.add(bottom,BorderLayout.SOUTH);
 	    this.getContentPane().add(global);
 	    this.setVisible(true);
	}
}
