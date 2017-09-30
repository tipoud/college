package Administration;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import DAO.DomaineDAO;
import DAO.ObjectifDAO;
import Main.Dat;
import tablesBDD.Domaine;
import tablesBDD.Objectif;

@SuppressWarnings("serial")
public class PanDomaine extends ClicOpen {
	private JPanel panDomaines = new JPanel(), panObj = new JPanel();
	private Domaine domaine = new Domaine();
	private Box boxObjectif = Box.createVerticalBox();
			Box boxDetails = Box.createVerticalBox();

	private ArrayList<Objectif> arrayObjectifs;
	private JButton btn = new JButton();
	
	
	public PanDomaine(){
	}
	
	public PanDomaine(Domaine comp){
	super();
	this.domaine = comp;
	panObj.setLayout(new BorderLayout());
	boxDetails.add(panObj);
	
	actualize();
	
	
	JPanel panBoutons = new JPanel(); 
	
	JButton renommer = new JButton("Renommer le domaine");
	JButton active = new JButton("Désactiver");
	JButton deleteBtn= new JButton("Supprimer");
	
	if(domaine.isActive()){
		active.setText("Désactiver");
		btn.setBackground(new Color(180,198,231));
		}else{
			btn.setBackground(Color.gray);
			active.setText("Activer");
			}
	panBoutons.add(renommer);
	panBoutons.add(active);
	panBoutons.add(deleteBtn);
	
	active.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			domaine.setActive(!domaine.isActive());
			if(domaine.isActive()){
				for (Objectif obj : arrayObjectifs ) {
					((ObjectifDAO) Dat.objectifDAO).activate(obj);
				}
				((DomaineDAO) Dat.domaineDAO).activate(domaine);
				}else{
					for (Objectif obj : arrayObjectifs ) {
						((ObjectifDAO) Dat.objectifDAO).desactivate(obj);
					}
					((DomaineDAO) Dat.domaineDAO).desactivate(domaine);
					}
			ModifObjectif.actualize();
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	});

	
	deleteBtn.addActionListener(new CompDeleteActionListener(domaine));
	boxObjectif.setBorder(BorderFactory.createTitledBorder(""));
	boxDetails.setBorder(BorderFactory.createTitledBorder(""));
	boxDetails.add(panDomaines);
	boxDetails.add(panBoutons);

	principal.add(btn,BorderLayout.CENTER);
	btn.add(principalLabel);
	
	renommer.addActionListener(new ActionListener() {	
		@Override
		public void actionPerformed(ActionEvent e) {
			UIManager.put("OptionPane.minimumSize",new Dimension(700,100));
			String nom = domaine.getNom();
		    nom = JOptionPane.showInputDialog(null, "Modification de l'intitulé du domaine!\nAttention cette modification "
		    		+ "affectera toutes les références é ce domaine.\n En cas de modifications significatives,"
		    		+ " préférez désactiver ce domaine et en créer un nouvel.", domaine.getNom());
		    UIManager.put("OptionPane.minimumSize",JOptionPane.DEFAULT_OPTION);
		    if (!(nom==null)){domaine.setNom(nom);}
		    Dat.domaineDAO.update(domaine);
		    principalLabel.setText(domaine.getNumero()+". "+domaine.getNom());
		    revalidate();
		}
	});
	
	btn.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			for (PanDomaine panDomaine : ModifObjectif.arrPanDomaine) {
				if (panDomaine.getDomaine() == domaine && !open)
				{
					content.add(boxDetails,BorderLayout.CENTER);
					open = true;
					
				} else {
					panDomaine.setOpen(false);
					panDomaine.getContent().remove(panDomaine.boxDetails);
				}
				panDomaine.getContent().revalidate();
			}
			
	}});
	if(domaine.getNumero()==1 || !domaine.isActive()){
		haut.setEnabled(false);
	}
	if(domaine.getNumero()==((DomaineDAO) Dat.domaineDAO).getAllActive().size() || !domaine.isActive()){
		bas.setEnabled(false);
	}
	
	
	haut.addActionListener(new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			int num = domaine.getNumero();
			domaine.setNumero(domaine.getNumero()-1);
			Dat.arrayDomaines.get(num-2).setNumero(num);
			Dat.domaineDAO.update(Dat.arrayDomaines.get(num-2));
			Dat.domaineDAO.update(domaine);
			Dat.domaineDAO.getAll();
			ModifObjectif.actualize();
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	});
	bas.addActionListener(new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			int num = domaine.getNumero();
			domaine.setNumero(domaine.getNumero()+1);
			Dat.arrayDomaines.get(num).setNumero(num);
			Dat.domaineDAO.update(Dat.arrayDomaines.get(num));
			Dat.domaineDAO.update(domaine);
			Dat.domaineDAO.getAll();
			ModifObjectif.actualize();
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	});
}


	public void actualize() {
		panObj.removeAll();
		boxObjectif.removeAll();
		domaine = Domaine.get(domaine.getIdDomaine());
		arrayObjectifs = domaine.getObjectifs();
		principalLabel.setText(domaine.getNumero()+". "+domaine.getNom());
		boxObjectif.setBorder(BorderFactory.createEmptyBorder(0, 20,0, 0));
		for (Objectif objectif : arrayObjectifs) {
			ModifObjectif.arrPanObjectif.add(new PanObjectif(objectif, domaine));
			boxObjectif.add(ModifObjectif.arrPanObjectif.get(ModifObjectif.arrPanObjectif.size()-1));
		}
		panObj.add(boxObjectif);
		
		revalidate();
	}
	
	public static PanDomaine getByDomaine(Domaine domaine){
		for (PanDomaine panDomaine : ModifObjectif.arrPanDomaine) {
			if (panDomaine.getDomaine().getIdDomaine() == domaine.getIdDomaine()){
				return panDomaine;
			}
		}
		return null;
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

	public Domaine getDomaine() {
		return domaine;
	}

	public void setObj(Domaine domaine) {
		this.domaine = domaine;
	}


}
