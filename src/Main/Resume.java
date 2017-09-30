package Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Accueil.Accueil;
import Administration.TableauVolets;
import Consult.MyPanNote;
import tablesBDD.Classe;
import tablesBDD.Domaine;
import tablesBDD.Note;
import tablesBDD.Objectif;

public class Resume extends JPanel {

	private static final long serialVersionUID = 1L;
	public static ArrayList<Domaine> arrayDomaines = Dat.arrayDomaines;

	public static Classe classe;

	public static JComboBox<Classe> JcClasse;

	private static JPanel bordure = new JPanel();
	private static JPanel global = new JPanel();
	private static TableauVolets tab;

	public Resume(){
		super();
		this.setPreferredSize(new Dimension(900,600));
		this.setLayout(new BorderLayout());
		initBorder();

		global.setLayout(new BorderLayout());

		this.add(bordure,BorderLayout.NORTH);
		this.add(global,BorderLayout.CENTER);
		this.setVisible(true);
	}


	public void initBorder() {
		bordure.removeAll();

		//Classe
		JcClasse = new JComboBox<>();
		JPanel panClasse = new JPanel();
		panClasse.setBackground(Color.white);
		panClasse.setBorder(BorderFactory.createTitledBorder(""));
		JcClasse.removeAllItems();
		for (Classe classe : Dat.arrayClasses) {
			if (!classe.getEleves().isEmpty()) {
				JcClasse.addItem(classe);
			}
		}
		classe = (Classe) JcClasse.getSelectedItem();
		JLabel classeLabel = new JLabel("Classe : ");
		panClasse.add(classeLabel);
		panClasse.add(JcClasse);

		bordure.add(panClasse);

		JcClasse.addItemListener(event -> {
            if (event.getStateChange()==ItemEvent.SELECTED) {
                classe = (Classe) event.getItem();
                actualizeContent();
                Accueil.myResume.revalidate();
            }
            Accueil.myConsultNote.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        });
		actualizeContent();
	}
	public void actualizeContent() {
		global.removeAll();
		if(classe == null) return;

		tab = new TableauVolets(classe.getEleves().size(), arrayDomaines.size() + 1, 32, 110, 30, 150);

		for (int i = 0; i < classe.getEleves().size(); i++) {
			double sommeDernieresNotes = 0d;
			int nombreNotes = 0;
			for (int j = 0; j < arrayDomaines.size(); j++) {

				if(i==0){
					tab.getTabTop()[j].add(new JLabel("Domaine "+arrayDomaines.get(j).getNumero()));
				}

				if(j==0){
					tab.getTabLeft()[i].add(new JLabel(classe.getEleves().get(i).getNom() +" ",SwingConstants.LEFT));
					tab.getTabLeft()[i].add(new JLabel(classe.getEleves().get(i).getPrenom(),SwingConstants.LEFT));
					tab.getTabLeft()[i].setLayout(new FlowLayout(FlowLayout.LEFT));
					((JLabel) tab.getTabLeft()[i].getComponent(0)).setVerticalAlignment(SwingConstants.CENTER);
					((JLabel) tab.getTabLeft()[i].getComponent(1)).setVerticalAlignment(SwingConstants.CENTER);
				}
				Double moyenneDomaine = 0d;
				ArrayList<Double> lesMoyennes = new ArrayList<>();
				for (Objectif objectif : arrayDomaines.get(j).getObjectifs()) {
					double moyenneObjectif = 0d;
					MyNoteList arr = new MyNoteList();
					arr.addAll(classe.getEleves().get(i).getNotes()) ;
					ArrayList<Note> arr2 = arr.getNonNul(objectif);

					double sum = 0d;
					if(arr2.size()<4 && arr2.size()!=0){
						for (Note note : arr2) {
							sommeDernieresNotes += getNoteBac(note.getVal());
							nombreNotes++;
							sum+=note.getVal();	
						}
						moyenneObjectif = sum/arr2.size();
					} else if (arr2.size()!=0) {
						for (int k = 0; k < 4 ; k++) {
							sum+=arr2.get(arr2.size()-1-k).getVal();
							sommeDernieresNotes += getNoteBac(arr2.get(arr2.size()-1-k).getVal());
							nombreNotes++;
						}
						moyenneObjectif = sum/4;
					}


					if (moyenneObjectif!=0){
						lesMoyennes.add(moyenneObjectif);
					}
				}
				double sum2 = 0d;
				for (double val : lesMoyennes) {
					sum2 += val;
				}
				if(!lesMoyennes.isEmpty()){
					moyenneDomaine = sum2/lesMoyennes.size();
				}

				if(moyenneDomaine==0){
				} else {
					int arrondi = (int) (moyenneDomaine + 0.5f);
					tab.getTabCenter()[i][j].add(new MyPanNote(arrondi));
				}


			}
			double moyenneGlobale = 0;
			try {
				moyenneGlobale= sommeDernieresNotes/nombreNotes;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DecimalFormat df = new DecimalFormat("#.00"); 
			tab.getTabCenter()[i][arrayDomaines.size()].add(new JLabel(df.format(moyenneGlobale*20/50)));
		}
		tab.getTabTop()[arrayDomaines.size()].add(new JLabel("Globale"));
		global.add(tab, BorderLayout.CENTER);


	}


	private static double getNoteBac(int val) {
		int res;
		switch (val) {
		case 1:
			res=0;
			break;
		case 2 :
			res=25;
			break;
		case 3 :
			res = 40;
			break;
		case 4 :
			res = 50;
			break;
		default:
			res = 0;
			break;
		}
		return res;
	}
}