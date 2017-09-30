package Consult;

import Administration.TableauVolets;
import Main.BoundsPopupMenuListener;
import javafx.scene.control.Alert;
import tablesBDD.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;

public class Consult extends JPanel implements IConsult {

    private static final long serialVersionUID = 1L;

    public boolean updating = false;

    // ComboBox
    private JComboBox<Eleve> elevesComboBox;
    private JComboBox<Eval> evaluationsComboBox;
    private JComboBox<Classe> classesComboBox;
    private JComboBox<Matiere> matieresComboBox;
    private JComboBox<Domaine> domainesCombobox;
    private JCheckBox evaluatedOnlyCheckBox = new JCheckBox();

    //JPanel
    private JPanel bordure = new JPanel();
    private JPanel centre = new JPanel();


    private static Consult INSTANCE = null;
    private ConsultPresenter presenter;

    public Consult() {
        super();
        this.setPreferredSize(new Dimension(900, 600));
        this.setLayout(new BorderLayout());
        this.add(bordure, BorderLayout.NORTH);
        this.add(centre, BorderLayout.CENTER);
        centre.setLayout(new BorderLayout());
        presenter = new ConsultPresenter(this);
        this.setVisible(true);
    }

    public static Consult getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Consult();
        }
        return INSTANCE;
    }


    public void initBorder() {
        bordure.removeAll();

        matieresComboBox = new JComboBox<>();
        matieresComboBox.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                presenter.update((Matiere) event.getItem());
            }
        });

        elevesComboBox = new JComboBox<>();
        elevesComboBox.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                presenter.update((Eleve) event.getItem());
            }
        });

        evaluationsComboBox = new JComboBox<>();
        evaluationsComboBox.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                presenter.update((Eval) event.getItem());
            }
        });

        classesComboBox = new JComboBox<>();
        classesComboBox.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                presenter.update((Classe) event.getItem());
            }
        });

        domainesCombobox = new JComboBox<>();
        domainesCombobox.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                presenter.update((Domaine) event.getItem());
            }
        });

        JPanel panMatieres = createBorderPan("Matières", matieresComboBox);
        JPanel panClasses = createBorderPan("Classes", classesComboBox);
        JPanel panEleves = createBorderPan("Elèves", elevesComboBox);
        JPanel panEval = createBorderPan("Evaluations", evaluationsComboBox);
        JPanel panDomaines = createBorderPan("Domaines", domainesCombobox);

        bordure.add(panMatieres);
        bordure.add(panClasses);
        bordure.add(panEleves);
        bordure.add(panEval);
        bordure.add(panDomaines);

        evaluatedOnlyCheckBox.setSelected(presenter.evaluatedOnly);
        evaluatedOnlyCheckBox.addActionListener(e -> presenter.udpateEvaluatedOnly(evaluatedOnlyCheckBox.isSelected()));

        presenter.initBorder();

    }

    private BoundsPopupMenuListener listener = new BoundsPopupMenuListener(true, false);

    private JPanel createBorderPan(String title, JComboBox comboBox) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createTitledBorder(""));
        panel.add(new JLabel(title + " : "));
        comboBox.setPreferredSize(new Dimension(100,20));
        comboBox.addPopupMenuListener( listener );
        panel.add(comboBox);
        return panel;
    }

    public void showComboMatieres(List<Matiere> matieres) {
        matieresComboBox.removeAllItems();
        for (Matiere matiere : matieres) {
            matieresComboBox.addItem(matiere);
        }
    }

    public void showComboEvaluations(List<Eval> evaluations) {
        evaluationsComboBox.removeAllItems();
        for (Eval eval : evaluations) {
            evaluationsComboBox.addItem(eval);
        }
        evaluationsComboBox.setLightWeightPopupEnabled(true);

    }

    public void showComboEleves(List<Eleve> eleves) {
        elevesComboBox.removeAllItems();
        for (Eleve eleve : eleves) {
            elevesComboBox.addItem(eleve);
        }
    }

    public void showComboClasses(List<Classe> classes) {
        classesComboBox.removeAllItems();
        for (Classe classe : classes) {
            classesComboBox.addItem(classe);
        }
    }

    public void showComboDomaines(List<Domaine> domaines) {
        domainesCombobox.removeAllItems();
        for (Domaine domaine : domaines) {
            domainesCombobox.addItem(domaine);
        }
    }


    public void updateAllContent(List<Objectif> arrayObjectifs, Eleve eleve) {
        updating = true;
        centre.removeAll();
        TableauVolets tabsolo = createTableauSolo(arrayObjectifs, eleve);
        centre.add(tabsolo, BorderLayout.CENTER);
        updating = false;
    }

    public void updateAllContent(List<Objectif> arrayObjectifs, Classe classe) {
        updating = true;
        centre.removeAll();
        TableauVolets tab = createTableauClasse(arrayObjectifs, classe);
        centre.add(tab, BorderLayout.CENTER);
        updating = false;
    }


    @Override
    public void nothingToShow() {
        JOptionPane.showMessageDialog(null, "Problème imprévu.");
    }

    @Override
    public void setUpdating(boolean updating) {
        this.updating = updating;
    }

    public boolean isUpdating() {
        return this.updating;
    }

    private TableauVolets createTableauSolo(List<Objectif> arrayObjectifs, Eleve eleve) {
        TableauVolets tabsolo = new TableauVolets(arrayObjectifs.size(), 1, 40, 800, 60, 400);
        tabsolo.getTabTop()[0].add(new JLabel(eleve.toString()));
        for (int k = arrayObjectifs.size() - 1; k > -1; k--) {
            JTextArea txt = new JTextArea(" " + arrayObjectifs.get(k).getNom());
            txt.setOpaque(false);
            txt.setLineWrap(true);
            txt.setWrapStyleWord(true);
            txt.setEditable(false);
            txt.setFont(new Font("Arial", Font.PLAIN, 12));
            txt.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
            NotePanel np;
            if (presenter.allMatieres) {
                if (presenter.allEvaluations) {
                    np = new NotePanel(eleve, arrayObjectifs.get(k));
                } else {
                    np = new NotePanel(eleve, arrayObjectifs.get(k), presenter.getEvaluation());
                }
            } else {
                if (presenter.allEvaluations) {
                    np = new NotePanel(eleve, arrayObjectifs.get(k), presenter.getMatiere());
                } else {
                    np = new NotePanel(eleve, arrayObjectifs.get(k), presenter.getMatiere(), presenter.getEvaluation());
                }
            }
            int numComp = Domaine.get(arrayObjectifs.get(k).getIdDomaine()).getNumero();

            tabsolo.getTabLeft()[k].setLayout(new BorderLayout());
            tabsolo.getTabLeft()[k].add(new JLabel("D" + numComp + "  " + arrayObjectifs.get(k).getNumero() + ".  "), BorderLayout.WEST);
            tabsolo.getTabLeft()[k].add(txt, BorderLayout.CENTER);

            np.setPreferredSize(null);
            tabsolo.getTabCenter()[k][0].setLayout(new FlowLayout(FlowLayout.LEFT, 2, 4));
            tabsolo.getTabCenter()[k][0].add(np);
        }
        tabsolo.getCorner().add(evaluatedOnlyCheckBox);
        return tabsolo;
    }

    private TableauVolets createTableauClasse(List<Objectif> arrayObjectifs, Classe classe) {
        ArrayList<Eleve> arrayEleve = classe.getEleves();
        TableauVolets tab = new TableauVolets(arrayEleve.size(), arrayObjectifs.size(), 32, 150, 90, 150);
        ArrayList<JScrollBar> miniscrolls = new ArrayList<>();
        NotePanel np ;
        for (int i = 0; i < arrayEleve.size(); i++) {
            for (int j = 0; j < arrayObjectifs.size(); j++) {
                if (i == 0) {
                    int k = arrayObjectifs.size() - 1 - j;
                    JTextArea txt = new JTextArea(" " + arrayObjectifs.get(k).getNom());
                    txt.setOpaque(false);
                    txt.setLineWrap(true);
                    txt.setWrapStyleWord(true);
                    txt.setEditable(false);
                    txt.setFont(new Font("Arial", Font.PLAIN, 10));
                    JScrollBar miniscroll1 = new JScrollBar();
                    miniscroll1.setOrientation(Adjustable.HORIZONTAL);
                    miniscroll1.setPreferredSize(new Dimension(140, 10));
                    miniscroll1.setMaximumSize(new Dimension(300, 10));
                    miniscrolls.add(0, miniscroll1);
                    tab.getTabTop()[k].setLayout(new BorderLayout());
                    int numComp = Domaine.get(arrayObjectifs.get(k).getIdDomaine()).getNumero();
                    tab.getTabTop()[k].add(new JLabel(" D" + numComp + ".  " + arrayObjectifs.get(k).getNumero()), BorderLayout.NORTH);
                    tab.getTabTop()[k].add(txt, BorderLayout.CENTER); //FAIT SCROLLER A DROITE
                    tab.getTabTop()[k].add(miniscroll1, BorderLayout.SOUTH);
                }
            }
            for (int j = 0; j < arrayObjectifs.size(); j++) {

                if (j == 0) {
                    tab.getTabLeft()[i].add(new JLabel(arrayEleve.get(i).getNom() + " ", SwingConstants.LEFT));
                    tab.getTabLeft()[i].add(new JLabel(arrayEleve.get(i).getPrenom(), SwingConstants.LEFT));
                    tab.getTabLeft()[i].setLayout(new FlowLayout(FlowLayout.LEFT));
                    ((JLabel) tab.getTabLeft()[i].getComponent(0)).setVerticalAlignment(SwingConstants.CENTER);
                    ((JLabel) tab.getTabLeft()[i].getComponent(1)).setVerticalAlignment(SwingConstants.CENTER);


                }

                if (presenter.allMatieres) {
                    if (presenter.allEvaluations) {
                        np = new NotePanel(arrayEleve.get(i), arrayObjectifs.get(j));
                    } else {
                        np = new NotePanel(arrayEleve.get(i), arrayObjectifs.get(j), presenter.getEvaluation());
                    }
                } else {
                    if (presenter.allEvaluations) {
                        np = new NotePanel(arrayEleve.get(i), arrayObjectifs.get(j), presenter.getMatiere());
                    } else {
                        np = new NotePanel(arrayEleve.get(i), arrayObjectifs.get(j), presenter.getMatiere(), presenter.getEvaluation());
                    }
                }

                np.setLayout(new FlowLayout(FlowLayout.RIGHT, 3, 2));
                JScrollPane miniscroll = new JScrollPane(np);
                miniscroll.setPreferredSize(new Dimension(140, 35));
                miniscroll.setMaximumSize(new Dimension(300, 35));
                miniscroll.getHorizontalScrollBar().setModel(miniscrolls.get(j).getModel());
                miniscroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                miniscroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
                miniscroll.getHorizontalScrollBar().setValue(miniscroll.getHorizontalScrollBar().getMaximum());
                miniscroll.setOpaque(false);
                miniscroll.getViewport().setOpaque(false);
                miniscroll.setBorder(null);
                tab.getTabCenter()[i][j].add(miniscroll);

            }
        }
        for (JScrollBar miniscroll : miniscrolls) {
            miniscroll.setValue(miniscroll.getMaximum());
        }
        tab.getCorner().add(evaluatedOnlyCheckBox);
        return tab;
    }
}