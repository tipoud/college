package Administration;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import DAO.DAO;
import DAO.ObjectifDAO;
import Main.Dat;
import tablesBDD.Domaine;
import tablesBDD.Objectif;

@SuppressWarnings("serial")
public class NouvelObjectif extends JFrame  {
	JPanel global = new JPanel(), top = new JPanel(), bottom = new JPanel();
	JPanel panTousNiveaux = new JPanel();
	private Box boxNiveaux = Box.createVerticalBox();
	DAO<Objectif> objectifDAO = Dat.objectifDAO;
	private JTextField nom = new JTextField("Entrer ici le nom du nouvel objectif");
	private ArrayList<JTextField> niveaux = new ArrayList<>();
	JComboBox<Domaine> comboCompetences = new JComboBox<>();
	JButton valider = new JButton("Valider");
	JButton annuler = new JButton("Annuler");
	
	public NouvelObjectif(){
		super();
		this.setTitle("Cr?ation d'un nouvel objectif");
		this.setSize(800, 240);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setLayout(new BorderLayout());
		global.setLayout(new BorderLayout());
		panTousNiveaux.setLayout(new BorderLayout());
		
		global.add(comboCompetences,BorderLayout.NORTH);
		
		nom.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				if(nom.getText().equals("Entrer ici le nom du nouvel objectif")){
					nom.setText("");
				}
				
			}
		});
		
		comboCompetences.setBorder(BorderFactory.createTitledBorder("Comp?tence concern?e"));
		
		for (Domaine comp : Dat.arrayDomaines) {
			comboCompetences.addItem(comp);
		}
		
		
		for (int i = 0; i < 4; i++) {
			niveaux.add(new JTextField());
			JPanel panNiveau = new JPanel();
			panNiveau.setLayout(new BorderLayout());
			panNiveau.add(new JLabel("Niveau "+(i+1)+" : "), BorderLayout.WEST);
			panNiveau.add(niveaux.get(i), BorderLayout.CENTER);
			boxNiveaux.add(panNiveau);
		}
		panTousNiveaux.add(nom,BorderLayout.NORTH);
		panTousNiveaux.add(boxNiveaux,BorderLayout.SOUTH);
		panTousNiveaux.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
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
				Objectif objectif = new Objectif(nom.getText(),
						Dat.arrayObjectifs.size()+1,
						niveaux.get(0).getText(),niveaux.get(1).getText(),niveaux.get(2).getText(),niveaux.get(3).getText(),
						false, null, domaine.getIdDomaine());
				objectif = objectifDAO.create(objectif);
				((ObjectifDAO) objectifDAO).activate(objectif);
				Dat.objectifDAO.getAll();
				Domaine.get(domaine.getIdDomaine()).setObjectifs();
				PanDomaine.getByDomaine(domaine).actualize();
				dispose();
			}
		});
		
		
		global.add(panTousNiveaux, BorderLayout.CENTER);
		global.add(bottom,BorderLayout.SOUTH);
 	    this.getContentPane().add(global);
 	    this.setVisible(true);
	}
}
