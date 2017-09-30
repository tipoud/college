package Evaluations;

import Accueil.Accueil;
import DAO.DAO;
import Main.CheckBoxEleves;
import Main.Dat;
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
import java.sql.Date;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class NouvelleEval extends JPanel {

    //SWING
    private JComboBox<Matiere> comboMatieres;
    private JComboBox<String> comboTypes;
    private JComboBox<Classe> comboClasses;
    private JTextArea description;
    private JScrollPane scrollEleves;
    private JTextField nom, nomFichier;
    private JDateChooser date;
    private JPanel global = new JPanel();
    private JPanel content = new JPanel();
    private String strEnseignant, adresseFichierOrigine, nomFichierStr;
    private Box boxEleves;

    //DAO
    private DAO<Fichier> fichierDAO = Dat.fichierDAO;


    private Classe classe;
    private Fichier fichier = new Fichier();
    protected Enseignant enseignant;
    private JPopupMenu jpm = new JPopupMenu();
    private JMenuItem ouvrir = new JMenuItem("Ouvrir le fichier");
    private JMenuItem vider = new JMenuItem("Supprimer le fichier");
    private JComboBox<Integer> objectifComboBox;

    NouvelleEval() {
        super();
        this.enseignant = Dat.enseignant;
        strEnseignant = "Enseignant(e): " + enseignant.getPrenom() + " " + enseignant.getNom();
        this.setLayout(new BorderLayout());
        this.initComponent();

    }

    private void initComponent() {
        JLabel enseignantLabel = new JLabel(strEnseignant);
        enseignantLabel.setHorizontalAlignment(SwingConstants.CENTER);
        enseignantLabel.setPreferredSize(new Dimension(444, 30));

        content.setPreferredSize(new Dimension(950, 550));
        content.setBackground(Color.white);
        content.add(intitule());
        content.add(createMatierePan());
        content.add(classe());
        content.add(createObjectifsPan());
        content.add(type());
        content.add(date());
        content.add(fichier());
        content.add(description());
        content.add(eleves());

        JPanel control = control();
        global.add(content, BorderLayout.CENTER);
        this.add(new JScrollPane(global), BorderLayout.CENTER);
        this.add(enseignantLabel, BorderLayout.NORTH);
        this.add(control, BorderLayout.SOUTH);
        this.setVisible(true);

    }

    private JPanel description() {
        JPanel panDescription = new JPanel();
        description = new JTextArea();
        panDescription.setBackground(Color.white);
        panDescription.setPreferredSize(new Dimension(444, 200));
        panDescription.setBorder(BorderFactory.createTitledBorder("Description de l'évaluation"));
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setPreferredSize(new Dimension(420, 170));
        panDescription.add(description);
        return panDescription;
    }

    private JPanel control() {
        JPanel control = new JPanel();
        JButton okBouton = new JButton("Ajouter");

        okBouton.addActionListener(arg0 -> {
            Classe classe = (Classe) comboClasses.getSelectedItem();
            Matiere matiere = (Matiere) comboMatieres.getSelectedItem();
            Date date = getDate(this.date.getDate());
            Integer numberOfObjectifs = (int) objectifComboBox.getSelectedItem();
            String type = null;
            if (comboTypes.getSelectedItem() != null) {
                type = comboTypes.getSelectedItem().toString();
            }
            ArrayList<Eleve> elevesEval = new ArrayList<>();
            for (int i = 0; i < boxEleves.getComponentCount(); i++) {
                CheckBoxEleves cb = (CheckBoxEleves) boxEleves.getComponent(i);
                if (cb.isSelected()) {
                    elevesEval.add(cb.getEleve());
                }
            }

            saveFile();

            if (classe != null && matiere != null && type != null) {
                Eval eval = new Eval(enseignant.getIdEnseignant(),
                        date,
                        comboTypes.getSelectedItem().toString(),
                        classe.getIdClasse(),
                        matiere.getIdMatiere(),
                        nom.getText(),
                        description.getText(),
                        fichier.getIdFichier());

                global.removeAll();
                global.add(new SaisieNote(eval, numberOfObjectifs, elevesEval));
                global.revalidate();
                global.repaint();
            }

        });

        JButton cancelBouton = new JButton("Annuler");
        cancelBouton.addActionListener(e -> EvaluationPanel.getInstance().choix());

        control.add(okBouton);
        control.add(cancelBouton);
        return control;
    }

    private void saveFile() {
        if (!nomFichier.getText().equals("")) {
            fichier = fichierDAO.create(fichier);
            fichier.setAdresse("C:\\Evaluations\\" + fichier.getIdFichier() + " - " + nomFichierStr);
            fichierDAO.update(fichier);
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
    }

    private JPanel createObjectifsPan() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setPreferredSize(new Dimension(444, 40));
        panel.setBorder(BorderFactory.createTitledBorder(""));
        panel.add(new JLabel("Nombre d'objectifs évalués :"));
        objectifComboBox = new JComboBox<>();
        for (int i = 1; i < 11; i++) {
            objectifComboBox.addItem(i);
        }
        panel.add(objectifComboBox);
        return panel;
    }

    private JPanel eleves() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setPreferredSize(new Dimension(444, 40));
        panel.setBorder(BorderFactory.createTitledBorder(""));

        JCheckBox choixEleves = new JCheckBox("Choisir les élèves");

        choixEleves.addActionListener(e -> {
            if (((JCheckBox) e.getSource()).isSelected()) {
                panel.setPreferredSize(new Dimension(444, 200));
                scrollEleves.setVisible(true);
                content.revalidate();
            } else {
                panel.setPreferredSize(new Dimension(444, 40));
                scrollEleves.setVisible(false);
                content.revalidate();
            }
        });
        panel.add(choixEleves, BorderLayout.NORTH);

        boxEleves = Box.createVerticalBox();
        if (classe != null) {
            ArrayList<Eleve> arrayEleves = classe.getEleves();
            for (Eleve ele : arrayEleves) {
                JCheckBox cbc = new CheckBoxEleves(ele);
                boxEleves.add(cbc);
            }
        }

        scrollEleves = new JScrollPane(boxEleves);
        scrollEleves.setPreferredSize(new Dimension(440, 160));
        scrollEleves.setVisible(false);
        panel.add(scrollEleves);

        return panel;
    }

    private JPanel date() {
        JPanel panDate = new JPanel();
        panDate.setBackground(Color.white);
        panDate.setPreferredSize(new Dimension(220, 40));
        date = new JDateChooser();
        date.setPreferredSize(new Dimension(100, 25));
        panDate.setBorder(BorderFactory.createTitledBorder(""));
        JLabel dateLabel = new JLabel("Date : ");
        panDate.add(dateLabel);
        panDate.add(date);
        return panDate;
    }

    private JPanel type() {
        JPanel panType = new JPanel();
        panType.setBackground(Color.white);
        panType.setPreferredSize(new Dimension(220, 40));
        panType.setBorder(BorderFactory.createTitledBorder(""));
        comboTypes = new JComboBox<>();
        comboTypes.addItem("Activité");
        comboTypes.addItem("DS");
        comboTypes.addItem("DM");
        comboTypes.addItem("IE");
        comboTypes.addItem("TP");
        JLabel typeLabel = new JLabel("Type : ");
        panType.add(typeLabel);
        panType.add(comboTypes);
        return panType;
    }

    private JPanel classe() {
        JPanel panClasse = new JPanel();
        panClasse.setBackground(Color.white);
        panClasse.setPreferredSize(new Dimension(220, 40));
        panClasse.setBorder(BorderFactory.createTitledBorder(""));
        comboClasses = new JComboBox<>();
        for (Classe ele : Dat.arrayClasses) {
            comboClasses.addItem(ele);
        }
        comboClasses.addItemListener(event -> {
                    if (event.getStateChange() == ItemEvent.SELECTED) {
                        classe = (Classe) event.getItem();
                        boxEleves.removeAll();
                        if (classe != null) {
                            for (Eleve ele : classe.getEleves()) {
                                JCheckBox cbc = new CheckBoxEleves(ele);
                                boxEleves.add(cbc);
                            }
                        }
                        content.revalidate();
                    }
                }
        );
        classe = (Classe) comboClasses.getSelectedItem();
        JLabel classeLabel = new JLabel("Classe : ");
        panClasse.add(classeLabel);
        panClasse.add(comboClasses);
        return panClasse;
    }

    private JPanel createMatierePan() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setPreferredSize(new Dimension(220, 40));
        panel.setBorder(BorderFactory.createTitledBorder(""));
        comboMatieres = new JComboBox<>();
        for (Matiere ele : Dat.arrayMatieres) {
            if (!ele.getObjectifs().isEmpty()) {
                comboMatieres.addItem(ele);
            }
        }
        JLabel matiereLabel = new JLabel("Matière : ");
        panel.add(matiereLabel);
        panel.add(comboMatieres);
        return panel;
    }

    private JPanel intitule() {
        JPanel panNom = new JPanel();
        panNom.setBackground(Color.white);
        panNom.setPreferredSize(new Dimension(900, 40));
        nom = new JTextField();
        nom.setPreferredSize(new Dimension(300, 25));
        panNom.setBorder(BorderFactory.createTitledBorder(""));
        JLabel nomLabel = new JLabel("Intitulé de l'évaluation :");
        panNom.add(nomLabel);
        panNom.add(nom);
        return panNom;
    }

    private JPanel fichier() {
        JPanel panFichier = new JPanel();
        panFichier.setBackground(Color.white);
        panFichier.setPreferredSize(new Dimension(444, 40));
        nomFichier = new JTextField();
        nomFichier.setEditable(false);
        nomFichier.setPreferredSize(new Dimension(300, 30));

        JButton parcourir = new JButton("Parcourir");
        panFichier.add(nomFichier);
        panFichier.add(parcourir);

        nomFichier.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent event) {
                //Seulement s'il s'agit d'un clic droit
                //if(event.getButton() == MouseEvent.BUTTON3)
                if (event.isPopupTrigger()) {
                    jpm.add(ouvrir);
                    jpm.add(vider);
                    //La méthode qui va afficher le menu
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
            adresseFichierOrigine = null;
            fichier = new Fichier();
            nomFichier.setText("");
        });


        parcourir.addActionListener(e -> {
            JFileChooser choix = new JFileChooser();
            int retour = choix.showDialog(new JFrame(), "Joindre");
            if (retour == JFileChooser.APPROVE_OPTION) {
                nomFichierStr = choix.getSelectedFile().getName();
                String type = FilenameUtils.getExtension(nomFichierStr);
                nomFichier.setText(nomFichierStr);
                fichier.setNom(nomFichierStr);
                fichier.setType(type);
                adresseFichierOrigine = choix.getSelectedFile().getAbsolutePath();

            }
        });
        return panFichier;
    }

    private Date getDate(java.util.Date date) {
        return new Date(date.getTime());
    }

}