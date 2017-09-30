package Administration;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import DAO.ObjectifDAO;
import Main.Dat;
import tablesBDD.Domaine;
import tablesBDD.Objectif;

@SuppressWarnings("serial")
public class PanObjectif extends ClicOpen {
	private JPanel panTousNiveaux = new JPanel(),
			panDomaines = new JPanel(),
			panBoutons = new JPanel();		
	
	private boolean open = false;
	private Objectif objectif;
	private Domaine domaine;
	
	private JButton renommer = new JButton("Renommer l'objectif"),
			suppress = new JButton("Supprimer"),
			active = new JButton(),
			deplacer = new JButton("Déplacer vers un autre domaine");
	
	private Box boxNiveaux = Box.createVerticalBox(),
			boxDetails = Box.createVerticalBox();
	
	public PanObjectif(){
	}
	
	public PanObjectif(Objectif obj, Domaine comp){
	super();
	this.objectif = obj;
	this.domaine = comp;
	
	principalLabel.setText(domaine.getNumero()+"." +objectif.getNumero()+".  "+objectif.getNom());
	btn.setPreferredSize(new Dimension(400,25));
	btn.setBackground(new Color(222,234,246));
	panTousNiveaux.setLayout(new BorderLayout());
		
	boxDetails.add(panTousNiveaux);
	boxDetails.add(panDomaines);
	boxDetails.add(panBoutons);
	
	for (int i = 0; i < 4; i++) {
		JPanel panNiveau = new JPanel();
		panNiveau.setLayout(new BorderLayout());
		panNiveau.add(new JLabel("Niveau "+(i+1)+" : "), BorderLayout.WEST);
		panNiveau.add(new NiveauTextField(objectif,i), BorderLayout.CENTER);
		boxNiveaux.add(panNiveau);
	}
	panTousNiveaux.add(boxNiveaux);

	if(objectif.isActive()){
		active.setText("Désactiver");
		}else{
			btn.setBackground(Color.gray);
			active.setText("Activer");
			}
	
	panBoutons.add(deplacer);
	panBoutons.add(renommer);
	panBoutons.add(active);
	panBoutons.add(suppress);
	
	active.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			objectif.setActive(!objectif.isActive());
			if(objectif.isActive()){
				((ObjectifDAO) Dat.objectifDAO).activate(objectif);
				}else{
					((ObjectifDAO)  Dat.objectifDAO).desactivate(objectif);
					}
			PanDomaine.getByDomaine(domaine).actualize();			
		}
	});
	
	renommer.addActionListener(new ActionListener() {	
		@Override
		public void actionPerformed(ActionEvent e) {
			UIManager.put("OptionPane.minimumSize",new Dimension(700,100));
			String nom = objectif.getNom();
		    nom = JOptionPane.showInputDialog(null, "Modification de l'intitulé de l'objectif!\nAttention cette modification affectera toutes les références é cet objectif.\n En cas de modifications significatives, préférez désactiver cet objectif et en créer un nouvel.", objectif.getNom());
		    UIManager.put("OptionPane.minimumSize",JOptionPane.DEFAULT_OPTION);
		    if (!(nom==null)){objectif.setNom(nom);}
		    Dat.objectifDAO.update(objectif);
		    principalLabel.setText(domaine.getNumero()+"." +objectif.getNumero()+".  "+objectif.getNom());
		    revalidate();
		}
	});
	
	btn.addActionListener(new ActionListener() {	
		@Override
		public void actionPerformed(ActionEvent e) {
			for (PanObjectif panObjectif : ModifObjectif.arrPanObjectif) {
				if (panObjectif.getObj() == objectif && !open)
				{
					content.add(boxDetails,BorderLayout.CENTER);
					open = true;
				} else {
					panObjectif.setOpen(false);
					panObjectif.getContent().remove(panObjectif.boxDetails);
				}
				panObjectif.getContent().revalidate();
			}
			
	}});
	

	if(objectif.getNumero()==1 || !objectif.isActive()){
		haut.setEnabled(false);
	}
	if(objectif.getNumero()==((ObjectifDAO)Dat.objectifDAO).getByCompActive(domaine.getIdDomaine()).size() || !objectif.isActive()){
		bas.setEnabled(false);
	}
	
	haut.addActionListener(new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			int num = objectif.getNumero();
			objectif.setNumero(objectif.getNumero()-1);
			Objectif precedent = domaine.getObjectifs().get(num-2);
			precedent.setNumero(num);
			 Dat.objectifDAO.update(precedent);
			 Dat.objectifDAO.update(objectif);
			 Dat.objectifDAO.getAll();
			 PanDomaine.getByDomaine(domaine).actualize();
			 
			 setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	});
	bas.addActionListener(new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			int num = objectif.getNumero();
			objectif.setNumero(objectif.getNumero()+1);
			Objectif suivant = domaine.getObjectifs().get(num);
			suivant.setNumero(num);
			 Dat.objectifDAO.update(suivant);
			 Dat.objectifDAO.update(objectif);
			 Dat.objectifDAO.getAll();
			 PanDomaine.getByDomaine(domaine).actualize();
			 
			 setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	});
	
	suppress.addActionListener(new ObjDeleteActionListener(objectif));
	
	deplacer.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			new MoveObjectif(objectif);
		}
	});

}

	public JPanel getContent() {
		return content;
	}

	public void setContent(JPanel content) {
		this.content = content;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public Objectif getObj() {
		return objectif;
	}

	public void setObj(Objectif objectif) {
		this.objectif = objectif;
	}
}
