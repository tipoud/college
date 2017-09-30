package Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Accueil.Accueil;
import tablesBDD.Enseignant;

@SuppressWarnings("serial")
public class Login extends JFrame {

    private JPanel global = new JPanel(), champs = new JPanel(), pan = new JPanel() , bottom = new JPanel();
    JPanel tabPan[][];
    Enseignant enseignant;
    public static Progress prog2;

    private JTextField login = new JTextField("douay"),
            mdp = new JPasswordField("mickey");

    private JButton ok = new JButton("Connexion"),
            annuler = new JButton("Annuler");

    private JLabel utilisateur = new JLabel("Utilisateur "),
            motdepasse = new JLabel("Mot de passe "),
            adv = new JLabel("");

    boolean exist = false;

    public Login(){
        super();
        this.setTitle("Authentification");
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setUndecorated(true);
        global.setLayout(new BorderLayout());
        champs.setLayout(new GridLayout(2, 2));
        tabPan = new JPanel[2][2];

        adv.setForeground(Color.RED);
        adv.setPreferredSize(new Dimension(60, 20));

        pan.setLayout(new BoxLayout(pan, BoxLayout.PAGE_AXIS));
        pan.add(adv);
        pan.add(champs);

        tabPan[0][0]= new JPanel();
        tabPan[0][1]= new JPanel();
        tabPan[1][0]= new JPanel();
        tabPan[1][1]= new JPanel();

        champs.add(tabPan[0][0]);
        champs.add(tabPan[0][1]);
        champs.add(tabPan[1][0]);
        champs.add(tabPan[1][1]);

        tabPan[0][0].setOpaque(false);
        tabPan[0][1].setOpaque(false);
        tabPan[1][0].setOpaque(false);
        tabPan[1][1].setOpaque(false);

        tabPan[0][0].setLayout(new FlowLayout(FlowLayout.RIGHT));
        tabPan[0][1].setLayout(new FlowLayout(FlowLayout.LEFT));
        tabPan[1][0].setLayout(new FlowLayout(FlowLayout.RIGHT));
        tabPan[1][1].setLayout(new FlowLayout(FlowLayout.LEFT));

        tabPan[0][0].add(utilisateur);
        tabPan[1][0].add(motdepasse);
        tabPan[0][1].add(login);
        tabPan[1][1].add(mdp);

        utilisateur.setBorder(BorderFactory.createEmptyBorder(7,0,0,0));
        motdepasse.setBorder(BorderFactory.createEmptyBorder(7,0,0,0));
        login.setPreferredSize(new Dimension(120, 30));
        mdp.setPreferredSize(new Dimension(120, 30));
        bottom.add(ok);
        bottom.add(annuler);

        try {
            global.add(new JLabel(new ImageIcon(Dat.classLoader.getResource("fondlogin.jpg"))), BorderLayout.CENTER);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        global.add(pan, BorderLayout.SOUTH);
        this.getContentPane().add(global, BorderLayout.CENTER);
        this.getContentPane().add(bottom, BorderLayout.SOUTH);
        this.setVisible(true);

        annuler.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

            }
        });

        ok.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (Enseignant ens : Dat.arrayEnseignants) {
                    if (ens.getPseudo().equals(login.getText()) && ens.getMdp().equals(mdp.getText())){
                        exist = true;
                        enseignant=ens;
                        break;
                    }
                }
                if(exist){
                    dispose();
                    new Accueil();


                } else {
                    adv.setText("Mauvais identifiant ou mot de passe.");
                    adv.setForeground(Color.RED);
                }
            }

        });

    }



}
