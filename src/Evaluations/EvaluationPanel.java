package Evaluations;
import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import Main.Dat;
import tablesBDD.Eleve;
import tablesBDD.Eval;
import tablesBDD.Objectif;

@SuppressWarnings("serial")
public class EvaluationPanel extends JPanel {
	public  NouvelleEval nouvelleEval;
	private ModifEval modifEval;

	public static boolean bas = false;
	public JPanel global = new JPanel();
	public ArrayList<Eleve> arrayEleves;
	public ArrayList<Objectif> arrayObjectifs;
	public ArrayList<MyCaseNote> arrNotes;

	private static EvaluationPanel INSTANCE;

 	public EvaluationPanel(){
 		super();
 		this.setLayout(new BorderLayout());
 		global.setLayout(new BorderLayout());

 		choix();
 		
 		this.add(global,BorderLayout.CENTER);

 	}

    public static EvaluationPanel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EvaluationPanel();
        }
        return INSTANCE;
    }

	 void choix() {
		global.removeAll();
		JPanel top = new JPanel();
        JButton btnNouvelleEval = new JButton("Nouvelle évaluation");
        JButton btnModifEval = new JButton("Modifier une évaluation");
 		top.add(btnNouvelleEval);
 		top.add(btnModifEval);
 		if (Eval.get(Dat.enseignant).isEmpty()){
 			btnModifEval.setEnabled(false);
 		}
 		else{
 			btnModifEval.setEnabled(true);
 		}

         btnNouvelleEval.addActionListener(arg0 -> {
             global.removeAll();
             nouvelleEval = new NouvelleEval();
             global.add(nouvelleEval);
         });

         btnModifEval.addActionListener(arg0 -> {
             modifEval = new ModifEval();
             global.removeAll();
             global.add(modifEval);
         });

 		global.add(top,BorderLayout.CENTER);
 		global.revalidate();
 		global.repaint();
	}
}
