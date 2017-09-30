package Evaluations;

import Administration.TableauVolets;
import DAO.DAO;
import DAO.NoteDAO;
import Main.ComboObjectif;
import Main.Dat;
import Main.MyNoteList;
import Main.Ouvrir;
import com.toedter.calendar.JDateChooser;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import tablesBDD.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ModifEval extends JPanel {

    private JComboBox<Object> comboEvalModif;
    private JPanel global = new JPanel(),
            top = new JPanel();
    private JPanel content = new JPanel();
    private JPanel bottom = new JPanel();
    private DAO<Eval> evalDAO = Dat.evalDAO;

    public Eval eval;
    public Matiere matiere;
    public Classe classe;
    private List<Eleve> eleves;
    private Fichier fichier = new Fichier();
    private String adresseFichierOrigine;
    private JTextField nom = new JTextField();
    private JDateChooser date = new JDateChooser();
    private JTextArea description = new JTextArea();
    private JButton upOrRight;
    private NoteDAO noteDAO = (NoteDAO) Dat.noteDAO;
    private ArrayList<Classe> arrayClasse = Dat.arrayClasses;
    private JPanel control = new JPanel();
    private JButton okBouton, supprimer;
    private JPopupMenu jpm = new JPopupMenu();
    private JMenuItem ouvrir = new JMenuItem("Ouvrir le fichier");
    private JMenuItem vider = new JMenuItem("Supprimer le fichier");
    private Boolean changementFichier = false;
    private ArrayList<ComboObjectif> arrCombo;

    private ArrayList<Objectif> objectifs;
    private List<MyCaseNote> arrNotes;

    ModifEval() {
        super();
        this.setLayout(new BorderLayout());
        this.initComponents();
    }

    private void initComponents() {
        global.setLayout(new BorderLayout());
        content.setPreferredSize(new Dimension(950, 550));
        content.setBackground(Color.white);
        content.setLayout(new BorderLayout());

        JPanel panClasse = createClassePan();
        updateComboEvalModif(classe);
        JPanel panEval = createEvalPan();



        top.removeAll();
        top.add(panClasse);
        top.add(panEval);

        updateUpOrRight();

        this.add(global, BorderLayout.CENTER);
        global.add(content, BorderLayout.CENTER);
        global.add(top, BorderLayout.NORTH);
        bottom.setLayout(new BorderLayout());
        bottom.add(control(), BorderLayout.CENTER);
        bottom.add(supprimer, BorderLayout.WEST);
        supprimer.setVisible(false);

        supprimer.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(
                    null,
                    "Etes-vous sûr de vouloir supprimer cette évalutation et toutes les notes qui lui sont associées?",
                    "Suppression de l'évaluation",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                evalDAO.delete(eval);
                Dat.noteDAO.getAll();
                Dat.evalDAO.getAll();
                EvaluationPanel.getInstance().choix();
            }
        });
        this.add(bottom, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    private void updateUpOrRight() {
        upOrRight = new JButton();
        if (EvaluationPanel.bas) {
            upOrRight.setIcon(Dat.flecheBas2);
        } else {
            upOrRight.setIcon(Dat.flecheDroite);
        }
        upOrRight.setMargin(new Insets(0, 0, 0, 0));
        upOrRight.setVerticalTextPosition(SwingConstants.CENTER);
        upOrRight.setHorizontalTextPosition(SwingConstants.CENTER);
        upOrRight.setPreferredSize(new Dimension(24, 24));
        upOrRight.addActionListener(e -> {
            EvaluationPanel.bas = !EvaluationPanel.bas;
            if (EvaluationPanel.bas) {
                upOrRight.setIcon(Dat.flecheBas2);
            } else {
                upOrRight.setIcon(Dat.flecheDroite);
            }
        });
    }

    private void updateComboEvalModif(Classe classe) {
        comboEvalModif = new JComboBox<>();
        comboEvalModif.addItem("Sélectionner une évaluation");
        ArrayList<Eval> evals = Eval.get(Dat.enseignant.getIdEnseignant(), classe.getIdClasse());
        for (Eval eval : evals) {
            comboEvalModif.addItem(eval);
        }
        eval = null;

        comboEvalModif.addItemListener(event -> {
                    if (event.getStateChange() == ItemEvent.SELECTED) {
                        if (event.getItem() instanceof String) {
                            eval = null;
                            okBouton.setEnabled(false);
                            supprimer.setVisible(false);
                        } else {
                            eval = (Eval) event.getItem();
                            okBouton.setEnabled(true);
                            supprimer.setVisible(true);
                        }
                        update();
                    }
                }
        );
    }

    private JPanel createClassePan() {
        JPanel panClasse = new JPanel();
        panClasse.setBackground(Color.white);
        panClasse.setBorder(BorderFactory.createTitledBorder(""));
        JComboBox<Classe> comboClasses = new JComboBox<>();
        for (Classe ele : arrayClasse) {
            comboClasses.addItem(ele);
        }
        comboClasses.addItemListener(event -> {
                    if (event.getStateChange() == ItemEvent.SELECTED) {
                        classe = (Classe) event.getItem();
                        updateComboEvalModif(classe);
                    }
                }
        );
        JLabel classeLabel = new JLabel("Classe : ");
        panClasse.add(classeLabel);
        panClasse.add(comboClasses);
        classe = (Classe) comboClasses.getSelectedItem();
        return panClasse;
    }

    private JPanel createEvalPan() {
        JPanel panEval = new JPanel();
        panEval.setBackground(Color.white);
        panEval.setBorder(BorderFactory.createTitledBorder(""));
        JLabel EvalLabel = new JLabel("Evaluations : ");
        panEval.add(EvalLabel);
        panEval.add(comboEvalModif);
        return panEval;
    }

    private JPanel intitule() {
        JPanel panNom = new JPanel();
        panNom.setBackground(Color.white);
        //panNom.setPreferredSize(new Dimension(444, 40));
        nom.setPreferredSize(new Dimension(300, 25));
        nom.setText(eval.getNom());
        panNom.setBorder(BorderFactory.createTitledBorder(""));
        JLabel nomLabel = new JLabel("Intitulé de l'évaluation :");
        panNom.add(nomLabel);
        panNom.add(nom);
        return panNom;
    }

    private JPanel description() {
        JPanel panDescription = new JPanel();
        panDescription.setBackground(Color.white);
        panDescription.setBorder(BorderFactory.createTitledBorder(""));
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setPreferredSize(new Dimension(300, 25));
        description.setText(eval.getDescription());
        description.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
        JLabel descriptionLabel = new JLabel("Description :");
        panDescription.add(descriptionLabel);
        panDescription.add(description);
        description.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
                description.setPreferredSize(new Dimension(300, 25));
                panDescription.revalidate();
            }

            @Override
            public void focusGained(FocusEvent e) {
                description.setPreferredSize(new Dimension(300, 100));
                panDescription.revalidate();
            }
        });
        return panDescription;
    }

    private JPanel fichier() {
        JPanel panFichier = new JPanel();
        panFichier.setBackground(Color.white);
        panFichier.setBorder(BorderFactory.createTitledBorder(""));
        panFichier.setPreferredSize(new Dimension(300, 38));
        JTextField nomFichier = new JTextField();
        nomFichier.setEditable(false);
        nomFichier.setPreferredSize(new Dimension(230, 25));
        JButton fichierBtn = new JButton(Dat.telechargement);
        if (eval.getIdFichier() != 0) {
            fichier = Dat.fichierDAO.find(eval.getIdFichier());
            nomFichier.setText(fichier.getNom());
            adresseFichierOrigine = fichier.getAdresse();
        } else {
            adresseFichierOrigine = "";
            nomFichier.setText("");
        }

        nomFichier.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent event) {
                //Seulement s'il s'agit d'un clic droit
                //if(event.getButton() == MouseEvent.BUTTON3)
                if (event.isPopupTrigger()) {
                    jpm.add(ouvrir);
                    jpm.add(vider);
                    //La m?thode qui va afficher le menu
                    jpm.show(nomFichier, event.getX(), event.getY());
                }
            }
        });

        ouvrir.addActionListener(e -> {
            if (adresseFichierOrigine != null) {
                new Ouvrir(adresseFichierOrigine);
            }
        });
        vider.addActionListener(e -> {
            Dat.fichierDAO.delete(fichier);
            adresseFichierOrigine = null;
            fichier = new Fichier();
            nomFichier.setText("");
            Dat.fichierDAO.delete(fichier);
        });

        panFichier.add(fichierBtn);
        panFichier.add(nomFichier);
        fichierBtn.addActionListener(e -> {
            JFileChooser choix = new JFileChooser();
            int retour = choix.showDialog(new JFrame(), "Joindre");
            if (retour == JFileChooser.APPROVE_OPTION) {
                String nom = choix.getSelectedFile().getName();
                String type = FilenameUtils.getExtension(nom);
                nomFichier.setText(nom);
                fichier.setNom(nom);
                fichier.setType(type);
                fichier.setAdresse("C:\\Evaluations\\" + fichier.getIdFichier() + " - " + nom);
                adresseFichierOrigine = choix.getSelectedFile().getAbsolutePath();
                changementFichier = true;

            }
        });
        return panFichier;
    }

    private JPanel date() {
        JPanel panDate = new JPanel();
        panDate.setBackground(Color.white);
        date = new JDateChooser();
        date.setPreferredSize(new Dimension(100, 25));
        date.setDate(eval.getDate());
        panDate.setBorder(BorderFactory.createTitledBorder(""));
        JLabel dateLabel = new JLabel("Date : ");
        panDate.add(dateLabel);
        panDate.add(date);
        return panDate;
    }

    public void update() {
        content.removeAll();
        if (eval != null) {
            JPanel topContent = new JPanel();
            content.add(topContent, BorderLayout.NORTH);
            JPanel panNom = intitule(), panDate = date(), panDescription = description(), panFichier = fichier();
            topContent.add(panNom);
            topContent.add(panDate);
            topContent.add(panDescription);
            topContent.add(panFichier);
            MyNoteList arrayNotes = new MyNoteList();
            arrayNotes.addAll(Note.get(eval));
            arrCombo = new ArrayList<>();

            classe = Classe.get(eval.getIdClasse());
            if (classe != null) {
                eleves = new ArrayList<>();
                for (Eleve eleve : classe.getEleves()) {
                    for (Note note : eleve.getNotes())
                        if (note.getIdEval() == eval.getIdEval()) {
                            eleves.add(eleve);
                            break;
                        }
                }
            }

            objectifs = eval.getObjectifs();
            TableauVolets tab = new TableauVolets(eleves.size(), objectifs.size(), 40, 100, 30, 120);
            int z = 0;
            for (int i = 0; i < eleves.size(); i++) {
                for (int j = 0; j < objectifs.size(); j++) {
                    Eleve eleve = eleves.get(i);
                    Objectif objectif = objectifs.get(j);
                    ArrayList<Note> notes = arrayNotes.get(eleve, eval, objectif);

                    if (j == 0) {
                        tab.getTabLeft()[i].add(new JLabel(eleve.getNom() + " " + eleves.get(i).getPrenom()));
                    }

                    for (Note note : notes) {
                        arrNotes = new ArrayList<>();
                        arrNotes.add(new MyCaseNote(note, z));
                        z++;
                        tab.getTabCenter()[i][j].add(arrNotes.get(arrNotes.size() - 1));

                        if (i == 0) {
                            arrCombo.add(new ComboObjectif(objectifs.get(j)));
                            tab.getTabTop()[j].add(arrCombo.get(arrCombo.size() - 1));
                        }
                        j++;
                    }
                    j--;
                }
            }
            EvaluationPanel.bas = false;
            tab.getCorner().add(upOrRight);
            content.add(tab, BorderLayout.CENTER);
        }
        global.revalidate();
        global.repaint();
    }

    private JPanel control() {
        okBouton = new JButton("Valider");
        supprimer = new JButton("Supprimer l'évaluation");
        okBouton.setEnabled(false);
        okBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eval.setNom(nom.getText());
                eval.setDescription(description.getText());
                java.util.Date utilDate = date.getDate();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                eval.setDate(sqlDate);
                evalDAO.update(eval);

                //sauvegarde du fichier
                if (!fichier.getNom().equals("") && changementFichier) {
                    fichier = Dat.fichierDAO.create(fichier);
                    Dat.fichierDAO.update(fichier);
                    File destination = new File(fichier.getAdresse());
                    Path pathToFile = Paths.get(destination.getPath());
                    try {
                        Files.createDirectories(pathToFile.getParent());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        destination.createNewFile();
                        InputStream input = new FileInputStream(adresseFichierOrigine);
                        OutputStream output = new FileOutputStream(destination);
                        IOUtils.copy(input, output);

                    } catch (FileNotFoundException e1) {

                        e1.printStackTrace();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                eval.setIdFichier(fichier.getIdFichier());
                evalDAO.update(eval);
                for (MyCaseNote ele : arrNotes) {
                    int numObjectif = ele.getId() % objectifs.size();
                    Objectif obj = (Objectif) arrCombo.get(numObjectif).getSelectedItem();
                    ele.getNote().setIdObjectif(obj.getIdObjectif());
                    ele.getNote().setNum(countNumObjectif(arrCombo, numObjectif));
                    noteDAO.update(ele.getNote());
                }
                Dat.noteDAO.getAll();
                Dat.evalDAO.getAll();
                EvaluationPanel.getInstance().choix();

            }

            private int countNumObjectif(ArrayList<ComboObjectif> arrCombo, int numObjectif) {
                Objectif obj2 = (Objectif) arrCombo.get(numObjectif).getSelectedItem();
                int n = 1;
                for (int i = 0; i < numObjectif; i++) {
                    if (obj2 == arrCombo.get(i).getSelectedItem()) {
                        n++;
                    }
                }
                return n;
            }
        });

        JButton cancelBouton = new JButton("Annuler");
        cancelBouton.addActionListener(arg0 -> {
            Dat.noteDAO.getAll();
            EvaluationPanel.getInstance().choix();
        });

        control.add(okBouton);
        control.add(cancelBouton);
        return control;
    }
}

