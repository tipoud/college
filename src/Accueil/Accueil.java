package Accueil;

import javax.swing.*;

import Administration.Administration;
import Consult.Consult;
import Evaluations.EvaluationPanel;
import Main.*;

@SuppressWarnings("serial")
public class Accueil extends JFrame implements IAccueil {
	public static Consult myConsultNote;
	public static Resume myResume;
	private static EvaluationPanel saisie;
	public static Administration administration;
	private static Export export;
	private JTabbedPane onglet;

	public Accueil(){
		super();
		AccueilPresenter presenter = new AccueilPresenter(this);
		this.setTitle("Consultation des notes");
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

		presenter.loadEnseignant();

		Bonjour bonjour = new Bonjour();
		saisie = EvaluationPanel.getInstance();
		myConsultNote = Consult.getInstance();
		myResume = new Resume();
		administration = new  Administration();
		export = new Export ();
		

		onglet = new JTabbedPane();
		onglet.add("Accueil",bonjour);
		onglet.add("Consulter les notes", myConsultNote);
		onglet.add("Résumé", myResume);
		onglet.add("Evaluations", saisie);
		onglet.add("Administration", administration);
		onglet.add("Exporter", export);
		onglet.setIconAt(0,new ImageIcon(Dat.classLoader.getResource("accueil.jpg")));
		onglet.setIconAt(1, new ImageIcon(Dat.classLoader.getResource("consult.jpg")));
		onglet.setIconAt(3, new ImageIcon(Dat.classLoader.getResource("saisienote.jpg")));
		onglet.setIconAt(4, new ImageIcon(Dat.classLoader.getResource("admin.jpg")));

		onglet.addChangeListener(e -> {
            if (e.getSource() instanceof JTabbedPane) {
                JTabbedPane pane = (JTabbedPane) e.getSource();
                if(pane.getSelectedIndex()==1){
                    Consult.getInstance().initBorder();
                } else if(pane.getSelectedIndex()==2){
					myResume.actualizeContent();
				}
            }
        });

		this.getContentPane().add(onglet);
		revalidate();
	}
}
