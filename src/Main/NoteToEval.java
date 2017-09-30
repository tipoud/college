package Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import DAO.ObjectifDAO;
import tablesBDD.Classe;
import tablesBDD.Enseignant;
import tablesBDD.Eval;
import tablesBDD.Fichier;
import tablesBDD.Matiere;
import tablesBDD.Note;
import tablesBDD.Objectif;

@SuppressWarnings("serial")
public class NoteToEval extends JFrame {

    private Eval eval = new Eval();
	private String strEnseignant;
	protected Enseignant enseignant;
	private Matiere matiere;
	private Classe classe;
	public Objectif objectif = new Objectif();
	private JPanel panDescription;


	public NoteToEval(Note note){
		super();
		this.setTitle("Evaluations : ");
		this.setSize(700, 450);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.eval = Eval.get(note.getIdEval());
		this.enseignant = Dat.enseignant;
		strEnseignant = "Enseignant(e): " + this.enseignant.getPrenom() +" "+ this.enseignant.getNom();
		matiere = Matiere.get(eval.getIdMatiere());
		classe=Classe.get(Eval.get(note.getIdEval()).getIdClasse());
		this.initComponent();

	}

	private void initComponent(){
        JLabel enseignantLabel = new JLabel(strEnseignant);
		enseignantLabel.setHorizontalAlignment(SwingConstants.CENTER);
		enseignantLabel.setPreferredSize(new Dimension(444, 30));

		
		//Le nom
		JPanel panNom = new JPanel();
		panNom.setBackground(Color.white);
		panNom.setPreferredSize(new Dimension(444, 40));
        JLabel nom = new JLabel(eval.getNom());
		nom.setPreferredSize(new Dimension(300, 25));
		panNom.setBorder(BorderFactory.createTitledBorder(""));
        JLabel nomLabel = new JLabel("Nom : ");
		panNom.add(nomLabel);
		panNom.add(nom);

		//Matiere
		JPanel panMatiere = new JPanel();
		panMatiere.setBackground(Color.white);
		panMatiere.setPreferredSize(new Dimension(220, 40));
		panMatiere.setBorder(BorderFactory.createTitledBorder(""));
        JLabel matiereNom = new JLabel(matiere.getNom());
        JLabel matiereLabel = new JLabel("Matière : ");
		panMatiere.add(matiereLabel);
		panMatiere.add(matiereNom);

		//Classe
		JPanel panClasse = new JPanel();
		panClasse.setBackground(Color.white);
		panClasse.setPreferredSize(new Dimension(220, 40));
		panClasse.setBorder(BorderFactory.createTitledBorder(""));
        JLabel classeNom = new JLabel(classe.toString());
        JLabel classeLabel = new JLabel("Classe : ");
		panClasse.add(classeLabel);
		panClasse.add(classeNom);

		//Type
		JPanel panType = new JPanel();
		panType.setBackground(Color.white);
		panType.setPreferredSize(new Dimension(220, 40));
		panType.setBorder(BorderFactory.createTitledBorder(""));
		JLabel type = new JLabel(eval.getType());
        JLabel typeLabel = new JLabel("Type : ");
		panType.add(typeLabel);
		panType.add(type);

		//Date
		JPanel panDate = new JPanel();
		panDate.setBackground(Color.white);
		panDate.setPreferredSize(new Dimension(220, 40));
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String dateParsed = format.format(eval.getDate());
        JLabel date = new JLabel(dateParsed);
		date.setPreferredSize(new Dimension(100, 25));
		panDate.setBorder(BorderFactory.createTitledBorder(""));
        JLabel dateLabel = new JLabel("Date : ");
		panDate.add(dateLabel);
		panDate.add(date);

		//Description
		JPanel panDescription = new JPanel();
		panDescription.setBackground(Color.white);
		panDescription.setPreferredSize(new Dimension(220, 200));
		panDescription.setBorder(BorderFactory.createTitledBorder("Détails objectifs"));

		//Competences
		JPanel panCompetence = new JPanel();
		
		panCompetence.setPreferredSize(new Dimension(444, 200));

		Box box = Box.createVerticalBox();
			for (Objectif objectif : ((ObjectifDAO) Dat.objectifDAO).get(eval)) {
				box.add(new JLabel(objectif.toString()));
			}

		JScrollPane scroll = new JScrollPane(box);
		scroll.setPreferredSize(new Dimension(444, 200));
		scroll.setBackground(Color.white);
		scroll.setBorder(BorderFactory.createTitledBorder("Objectifs évalués"));
		JPanel content = new JPanel();
		content.setBackground(Color.white);
		content.add(panNom);
		content.add(panMatiere);
		content.add(panClasse);
		content.add(panType);
		content.add(panDate);
		content.add(scroll);
		content.add(panDescription);
		
		//Fichier
		if(eval.getIdFichier() != 0){
			Fichier fichier = Dat.fichierDAO.find(eval.getIdFichier());
			JLabel fichierLabel = new JLabel(fichier.getNom());
			JButton fichierBtn = new JButton("Voir l'évalutation");
			fichierBtn.addActionListener(e -> {
                System.out.println(fichier.getAdresse());
                new Ouvrir(fichier.getAdresse());
            });
			content.add(fichierBtn);
			content.add(fichierLabel);
		} else {
			JLabel fichierLabel = new JLabel("Aucun fichier joint");
			content.add(fichierLabel);
		}
		

		JPanel control = new JPanel();

		JButton cancelBouton = new JButton("Ok");
		cancelBouton.addActionListener(arg0 -> setVisible(false));

		control.add(cancelBouton);

		this.getContentPane().add(content, BorderLayout.CENTER);
		this.getContentPane().add(enseignantLabel, BorderLayout.NORTH);
		this.getContentPane().add(control, BorderLayout.SOUTH);

		this.setVisible(true);


	}

	public JPanel getPanDescription() {
		return panDescription;
	}


}