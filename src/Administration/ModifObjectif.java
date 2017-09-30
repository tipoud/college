package Administration;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import DAO.DomaineDAO;
import Main.Dat;
import tablesBDD.Domaine;


@SuppressWarnings("serial")
public class ModifObjectif extends JPanel{
	
	private JPanel top = new JPanel();
	private static JPanel global = new JPanel();
	private JButton ajoutDomaine, ajoutObjectif;
	public static Box boxComp;
	public static ArrayList<PanDomaine> arrPanDomaine = new ArrayList<>();
	public static ArrayList<PanObjectif> arrPanObjectif = new ArrayList<>();

	private static JScrollPane scroll;
	
	ModifObjectif(){
		super();
	this.setLayout(new BorderLayout());
	global.setLayout(new BorderLayout());
	this.setBackground(Color.GRAY);
	ajoutDomaine = new JButton("Ajouter un domaine");
	ajoutObjectif = new JButton("Ajouter un objectif");
	
	ajoutDomaine.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			UIManager.put("OptionPane.minimumSize",new Dimension(700,100));
			String nom;
		    nom = JOptionPane.showInputDialog(null, "Entrer le nom du nouveau domaine");
		    UIManager.put("OptionPane.minimumSize",JOptionPane.DEFAULT_OPTION);
		    if (!(nom==null)){
		    	Domaine domaine = new Domaine(Dat.arrayDomaines.size()+1, nom);
		    	domaine = Dat.domaineDAO.create(domaine);
		    	((DomaineDAO) Dat.domaineDAO).activate(domaine);
		    	Dat.domaineDAO.getAll();
		    	actualize();
		    	}

		}
	});
	
	ajoutObjectif.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			new NouvelObjectif();
		}
	});
	
	top.add(ajoutDomaine);
	top.add(ajoutObjectif);
	
	actualize();
	
	this.add(global,BorderLayout.CENTER);
	this.add(top, BorderLayout.NORTH);
	
	}

	/**
	 * 
	 */
	public static void actualize() {
		global.removeAll();
		boxComp = Box.createVerticalBox();
		for (Domaine comp : Dat.arrayDomaines) {
			arrPanDomaine.add(new PanDomaine(comp));
			boxComp.add(arrPanDomaine.get(arrPanDomaine.size()-1));
		}
		scroll = new JScrollPane(boxComp);		
		global.add(scroll,BorderLayout.CENTER);
	}


}
