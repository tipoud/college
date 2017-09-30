package Administration;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.toedter.calendar.JDateChooser;

import Main.Dat;
import Main.DateRenderer;
import tablesBDD.Classe;
import tablesBDD.Eleve;

@SuppressWarnings("serial")
public class ModifEleve extends JPanel {

	private static JTable tableau;
	private static JComboBox<Classe> comboClasse = new JComboBox<Classe>();
	private static JComboBox<Classe> comboClasseTop = new JComboBox<Classe>();
	private JPanel top  = new JPanel(),
			bottom = new JPanel();
	private static JPanel global = new JPanel();

	JScrollPane scrollObjectifs;

	public static Classe classe;
	public static ArrayList<Eleve> MyArrayEleves;

	JDateChooser date = new com.toedter.calendar.JDateChooser();

	Box boxObjectif = Box.createVerticalBox();

    public ModifEleve(){
		this.setLayout(new BorderLayout());
		this.add(top, BorderLayout.NORTH);
		this.add(bottom, BorderLayout.SOUTH);
		this.add(global, BorderLayout.CENTER);
		global.setLayout(new BorderLayout());	
		top.add(comboClasseTop);
		actualizeTop();
		revalidate();

		comboClasseTop.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					classe =Classe.get(((Classe) event.getItem()).getIdClasse());
					actualizeContent();
					revalidate();
				}}});
        JButton ajouter = new JButton("Ajouter un élève");
        ajouter.addActionListener(new AjouterListener());
        JButton valider = new JButton("Valider les changements");
        valider.addActionListener(new ValiderListener());
        JButton retablir = new JButton("Rétablir");
        retablir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GenereTab();
				revalidate();
			}
		});

		bottom.add(ajouter);
		bottom.add(retablir);
		bottom.add(valider);

	}

	public static void actualizeTop() {
		comboClasseTop.removeAllItems();
		for (Classe item: Dat.arrayClasses) {
			comboClasseTop.addItem(item);
		}
		classe = Dat.arrayClasses.get(0);
		actualizeContent();

	}
	
	public static void actualizeTop(Classe clas) {
		actualizeTop();
		classe = clas;
		comboClasseTop.setSelectedItem(clas);
		actualizeContent();
	}

	public static void actualizeContent(){

		comboClasse = new JComboBox<Classe>();
		for (Classe ele : Dat.arrayClasses) {
			comboClasse.addItem(ele);
		}
		classe = Classe.get(((Classe) comboClasseTop.getSelectedItem()).getIdClasse());

		MyArrayEleves = classe.getEleves();
		GenereTab();

	}
	protected static void GenereTab() {
		global.removeAll();
		Object[][] data = new Object[MyArrayEleves.size()][7];
		for (int i = 0; i < MyArrayEleves.size(); i++) {

			data[i][0] = MyArrayEleves.get(i).getNom();
			data[i][1]= MyArrayEleves.get(i).getPrenom();
			data[i][2]= MyArrayEleves.get(i).getDateNaissance();
			data[i][3]= MyArrayEleves.get(i).getMail1();
			data[i][4]= MyArrayEleves.get(i).getMail2();
			data[i][5]= classe;
			data[i][6]= "Supprimer";
		}

		String  title[] = {"Nom", "Prénom", "Date de Naissance", "Mail 1", "Mail 2","Classe","Supprimer"};

		ZModel zModel = new ZModel(data, title);

		tableau = new JTable(zModel);     
		tableau.setRowHeight(30);


		//On définit l'éditeur par défaut pour la cellule
		//en lui spécifiant quel type d'affichage prendre en compte
		tableau.getColumn("Classe").setCellEditor(new DefaultCellEditor(comboClasse));
		DefaultTableCellRenderer dcr = new DefaultTableCellRenderer();
		tableau.getColumn("Classe").setCellRenderer(dcr);
		MyJDateChooserCellEditor jdcce = new MyJDateChooserCellEditor();
		tableau.getColumn("Date de Naissance").setCellRenderer(new DateRenderer());
		tableau.getColumn("Date de Naissance").setCellEditor(jdcce);

		//On définit un éditeur pour la colonne "supprimer"
		tableau.getColumn("Supprimer").setCellEditor(new DeleteEleveButtonEditor(new JCheckBox()));

		//On ajoute le tableau
		global.add(new JScrollPane(tableau), BorderLayout.CENTER);
	}     

	class AjouterListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			Object[] donnee = new Object[]{"Nom", "Prénom", "Date de Naissance", "Mail 1", "Mail 2",classe,"Supprimer"};
			((ZModel)tableau.getModel()).addRow(donnee);
			((ZModel)tableau.getModel()).getRowCount();
			tableau.changeSelection(((ZModel)tableau.getModel()).getRowCount()-1,0,false, false);
		}
	}

	class ValiderListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			for (int i = 0; i < MyArrayEleves.size(); i++) {
				Eleve eleve = MyArrayEleves.get(i);
				eleve.setNom((String)tableau.getValueAt(i, 0));
				eleve.setPrenom((String)tableau.getValueAt(i, 1));
				if (tableau.getValueAt(i, 2)!=null) {
					java.util.Date utilDate = (java.util.Date) tableau.getValueAt(i, 2);
					java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
					eleve.setDateNaissance(sqlDate);
				}

				eleve.setMail1((String)tableau.getValueAt(i, 3));
				eleve.setMail2((String)tableau.getValueAt(i, 4));
				eleve.setIdClasse(((Classe) tableau.getValueAt(i, 5)).getIdClasse());
				Dat.eleveDAO.update(eleve);
			}

			for (int i = MyArrayEleves.size(); i < tableau.getRowCount(); i++) {
				java.util.Date utilDate = (java.util.Date) tableau.getValueAt(i, 2);
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

				Eleve eleve = new Eleve(((Classe)tableau.getValueAt(i, 5)).getIdClasse(),(String)tableau.getValueAt(i, 0),(String)tableau.getValueAt(i, 1),sqlDate,(String)tableau.getValueAt(i, 3),(String)tableau.getValueAt(i, 4),"pseudo","mdp");
				Dat.eleveDAO.create(eleve);
			}
			Dat.eleveDAO.getAll();
			actualizeContent();
			revalidate();
			ModifClasse.actualize();
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}



}