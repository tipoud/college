package Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import Evaluations.EvaluationPanel;
import tablesBDD.Domaine;
import tablesBDD.Objectif;

@SuppressWarnings("serial")
public class CheckBoxObjectif extends JCheckBox  {
	private Objectif objectif = new Objectif();
	String nom, label, detailsNiveaux;
	private int nombre = 1;
	private JPopupMenu jpmObj = new JPopupMenu();
	private JMenuItem un = new JMenuItem("1");      
	private JMenuItem deux = new JMenuItem("2");      
	private JMenuItem trois = new JMenuItem("3");
	private JMenuItem quatre = new JMenuItem("4");
	private JMenuItem cinq = new JMenuItem("5");
	
	
	
	
	public CheckBoxObjectif(Objectif obj) {
		super();
		this.objectif = obj;
		Domaine comp = Domaine.get(objectif.getIdDomaine());
		nom = comp.getNumero()+"."+obj.getNumero() + " " + obj.getNom();
		label = nom;
		detailsNiveaux = comp.toString() + "\n\nNiveau 1 : " + obj.getNiveau1()
		+"\nNiveau 2 : " + obj.getNiveau2()
		+"\nNiveau 3 : " + obj.getNiveau3()
		+"\nNiveau 4 : " + obj.getNiveau4();
		
		setText(label);
		
		un.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				label = nom;
				setText(label);
				nombre = 1;
			}
		});
		
		deux.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				label = "(X"+deux.getText()+") " + nom;
				setText(label);
				nombre = 2;
			}
		});
		
		trois.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				label = "(X"+trois.getText()+") " + nom;
				setText(label);
				nombre = 3;
			}
		});
		
		quatre.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				label = "(X"+quatre.getText()+") " + nom;
				setText(label);
				nombre = 4;
			}
		});
		
		cinq.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				label = "(X"+cinq.getText()+") " + nom;
				setText(label);
				nombre = 5;
			}
		});
		

		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				//EvaluationPanel.nouvelleEval.getDetailsObjectif().setText(detailsNiveaux);
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public Objectif getObjectif() {
		return objectif;
	}

	public void setObjectif(Objectif obj) {
		this.objectif = obj;
	}

	public int getNombre() {
		return nombre;
	}

	public void setNombre(int nombre) {
		this.nombre = nombre;
	}

	public JPopupMenu getJpmObj() {
		return jpmObj;
	}

	public void setJpmObj(JPopupMenu jpmObj) {
		this.jpmObj = jpmObj;
	}

	public JMenuItem getUn() {
		return un;
	}

	public void setUn(JMenuItem Un) {
		this.un = un;
		}
	
	public JMenuItem getDeux() {
		return deux;
	}

	public void setDeux(JMenuItem deux) {
		this.deux = deux;
	}

	public JMenuItem getTrois() {
		return trois;
	}

	public void setTrois(JMenuItem trois) {
		this.trois = trois;
	}

	public JMenuItem getQuatre() {
		return quatre;
	}

	public void setQuatre(JMenuItem quatre) {
		this.quatre = quatre;
	}

	public JMenuItem getCinq() {
		return cinq;
	}

	public void setCinq(JMenuItem cinq) {
		this.cinq = cinq;
	}



	
}
