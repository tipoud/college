package Administration;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import Main.Dat;
import tablesBDD.Classe;

@SuppressWarnings("serial")
public class ModifClasse  extends JPanel{
	
	private JPanel top  = new JPanel(),
			bottom = new JPanel();
	private static JPanel global = new JPanel();
	
	JScrollPane scrollObjectifs;

	JButton ajouter = new JButton("Ajouter une classe"),
			retablir = new JButton("Rétablir"),
			valider = new JButton("Valider les changements");

	private static JTable tableau;
	
	int[] niveaux = new int[]{6,5,4,3};
	private static JComboBox<Integer> comboNiveau = new JComboBox<Integer>();

	ModifClasse(){
		super();
		this.setLayout(new BorderLayout());
		this.setBackground(Color.GRAY);
		this.add(top, BorderLayout.NORTH);
		this.add(bottom, BorderLayout.SOUTH);
		this.add(global, BorderLayout.CENTER);
		global.setLayout(new BorderLayout());

		bottom.add(ajouter);
		bottom.add(retablir);
		bottom.add(valider);
		
		for (int i : niveaux) {
			comboNiveau.addItem(i);
		}
		
		ajouter.addActionListener(new AjouterListener());
		valider.addActionListener(new ValiderListener());

		retablir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualize();
			}
		});
		
		GenereTab();

	}

	protected static void GenereTab() {

		global.removeAll();
		Object[][] data = new Object[Dat.arrayClasses.size()][6];
		for (int i = 0; i < Dat.arrayClasses.size(); i++) {

			data[i][0] = Dat.arrayClasses.get(i).getNiveau();
			data[i][1]= Dat.arrayClasses.get(i).getNom();
			data[i][2]=  Dat.arrayClasses.get(i).getEleves().size();
			data[i][4]= Dat.arrayClasses.get(i).isActive();
			data[i][5]= "Supprimer";
		}

		String  title[] = {"Niveau", "Nom", "Nombre d'éléves","","Utilisation", "Supprimer"};

		ZModel zModel = new ZModel(data, title);

		tableau = new JTable(zModel);     
		tableau.setRowHeight(30);

		DefaultTableCellRenderer dcr = new MyCellRenderer();
		tableau.getColumn("Utilisation").setCellEditor(new DefaultCellEditor(new JCheckBox()));
		tableau.getColumn("Utilisation").setCellRenderer(dcr);

		tableau.getColumn("Niveau").setCellEditor(new DefaultCellEditor(comboNiveau));
		DefaultTableCellRenderer dcr2 = new DefaultTableCellRenderer();
		tableau.getColumn("Niveau").setCellRenderer(dcr2);
		
		//On définit un éditeur pour la colonne "supprimer"
		tableau.getColumn("Supprimer").setCellEditor(new DeleteClasseButtonEditor(new JCheckBox()));
		tableau.getColumn("Supprimer").setCellRenderer(new ButtonRenderer());
		JTableUtilities.setCellsAlignment(tableau, 2 ,SwingConstants.CENTER);
		JTableUtilities.setCellsAlignment(tableau, 0 ,SwingConstants.CENTER);

		tableau.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableau.getColumn("Niveau").setPreferredWidth(50);
		tableau.getColumn("Nom").setPreferredWidth(200);
		tableau.getColumn("Nombre d'éléves").setPreferredWidth(100);
		tableau.getColumn("Utilisation").setPreferredWidth(75);
		tableau.getColumn("Supprimer").setPreferredWidth(100);
		tableau.getColumn("").setPreferredWidth(200);

		//On ajoute le tableau
		global.add(new JScrollPane(tableau), BorderLayout.CENTER);

	}    


	protected static void actualize() {
		GenereTab();
		Administration.myModifClasse.revalidate();
	}

	class AjouterListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			Object[] donnee = new Object[]{6, "Nom", "00-00-0000","",true, "Supprimer"};
			((ZModel)tableau.getModel()).addRow(donnee);
			((ZModel)tableau.getModel()).getRowCount();
			tableau.changeSelection(((ZModel)tableau.getModel()).getRowCount()-1,0,false, false);
		}
	}

	class ValiderListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < Dat.arrayClasses.size(); i++) {
				Classe classe = Dat.arrayClasses.get(i);
				classe.setNiveau((Integer)tableau.getValueAt(i, 0));
				classe.setNom((String)tableau.getValueAt(i, 1));
				classe.setActive((boolean)tableau.getValueAt(i, 4));
				Dat.classeDAO.update(classe);
			}

			for (int i = Dat.arrayClasses.size(); i < tableau.getRowCount(); i++) {
				System.out.println(tableau.getRowCount());
				Classe classe = new Classe((Integer)tableau.getValueAt(i, 0), (String)tableau.getValueAt(i, 1));
				Dat.classeDAO.create(classe);
			}
			Dat.classeDAO.getAll();
			actualize();
			ModifEleve.actualizeTop();

		}

	}
}