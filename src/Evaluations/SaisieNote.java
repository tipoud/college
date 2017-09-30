package Evaluations;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.*;

import Administration.TableauVolets;
import Main.BoundsPopupMenuListener;
import Main.ComboObjectif;
import Main.Dat;
import tablesBDD.Classe;
import tablesBDD.Eleve;
import tablesBDD.Eval;
import tablesBDD.Note;
import tablesBDD.Objectif;


@SuppressWarnings("serial")
public class SaisieNote extends JPanel {

    private ArrayList<ComboObjectif> comboBoxObjectifs;
    private ArrayList<MyCaseNote> arrNotes;
    private ArrayList<Eleve> eleves;
    private Eval evaluation;

    private JButton upOrRight = new JButton();

    private Boolean bas = true;

    public SaisieNote(Eval evaluation, int numberOfObjectifs, ArrayList<Eleve> elevesEval) {
        super();
        this.setPreferredSize(new Dimension(950, 600));
        this.setLayout(new BorderLayout());

        this.evaluation = evaluation;

        if (elevesEval.isEmpty()) {
            Classe classe = Classe.get(this.evaluation.getIdClasse());
            if (classe != null) {
                eleves = classe.getEleves();
            }
        } else {
            eleves = elevesEval;
        }

        upOrRight.setIcon(Dat.flecheBas2);
        upOrRight.setMargin(new Insets(0, 0, 0, 0));
        upOrRight.setVerticalTextPosition(SwingConstants.CENTER);
        upOrRight.setHorizontalTextPosition(SwingConstants.CENTER);
        upOrRight.setPreferredSize(new Dimension(26, 26));

        TableauVolets tab = getTableauVolets(numberOfObjectifs);


        ///Listeners

        // Valider
        JButton valider = new JButton("Valider");
        valider.addActionListener(arg0 -> {

            SaisieNote.this.evaluation = Dat.evalDAO.create(SaisieNote.this.evaluation);
            for (int i = 0; i < arrNotes.size(); i++) {
                Note note = arrNotes.get(i).getNote();
                note.setIdEval(SaisieNote.this.evaluation.getIdEval());


                int numObjectif = i % numberOfObjectifs;
                Objectif obj = (Objectif) comboBoxObjectifs.get(numObjectif).getSelectedItem();
                if(obj!= null){
                    note.setIdObjectif(obj.getIdObjectif());
                }
                note.setNum(countNumObjectif(numObjectif,obj));
                Dat.noteDAO.create(note);
            }
            Dat.evalDAO.getAll();
            Dat.noteDAO.getAll();
            EvaluationPanel.getInstance().choix();
        });

        //Annuller
        JButton annuler = new JButton("Annuler");
        annuler.addActionListener(e -> {
            EvaluationPanel.getInstance().choix();
        });


        upOrRight.addActionListener(e -> {
            bas = !bas;
            if (bas) {
                upOrRight.setIcon(Dat.flecheBas2);
            } else {
                upOrRight.setIcon(Dat.flecheDroite);
            }
        });

        JPanel control = new JPanel();
        control.add(valider);
        control.add(annuler);


        this.add(tab, BorderLayout.CENTER);
        this.add(new JLabel("Saisie des notes"), BorderLayout.NORTH);
        this.add(control, BorderLayout.SOUTH);
        this.setVisible(true);
    }



    private TableauVolets getTableauVolets(int numberOfObjectifs) {
        arrNotes = new ArrayList<>();
        TableauVolets tab = new TableauVolets(eleves.size(), numberOfObjectifs, 40, 100, 30, 120);
        comboBoxObjectifs = new ArrayList<>();
        for (int i = 0; i < eleves.size(); i++) {
            for (int j = 0; j < numberOfObjectifs; j++) {
                Eleve eleve = eleves.get(i);
                arrNotes.add(new MyCaseNote(eleve, numberOfObjectifs, eleves.size(), arrNotes));
                tab.getTabCenter()[i][j].add(arrNotes.get(arrNotes.size() - 1));
                if (i == 0) {
                    comboBoxObjectifs.add(new ComboObjectif());
                    tab.getTabTop()[j].add(comboBoxObjectifs.get(comboBoxObjectifs.size() - 1));
                }
                if (j == 0) {
                    tab.getTabLeft()[i].add(new JLabel(eleves.get(i).getNom() + " " + eleves.get(i).getPrenom()));
                }

            }
        }
        tab.getCorner().add(upOrRight);
        return tab;
    }

    private int countNumObjectif(int numObjectif, Objectif objectif) {
        int n = 1;
        for (int i = 0; i < numObjectif; i++) {
            if(comboBoxObjectifs != null && comboBoxObjectifs.size()>=i ) {
                if (objectif.getIdObjectif() == ((Objectif) comboBoxObjectifs.get(i).getSelectedItem()).getIdObjectif()) {
                    n++;
                }
            }
        }
        return n;
    }
}
