package Main;
import java.awt.Image;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import DAO.ClasseDAO;
import DAO.DAO;
import DAO.DomaineDAO;
import DAO.EleveDAO;
import DAO.EnseignantDAO;
import DAO.EvalDAO;
import DAO.FichierDAO;
import DAO.MatObjDAO;
import DAO.MatiereDAO;
import DAO.MyConnection;
import DAO.NoteDAO;
import DAO.ObjectifDAO;
import tablesBDD.Classe;
import tablesBDD.Domaine;
import tablesBDD.Eleve;
import tablesBDD.Enseignant;
import tablesBDD.Eval;
import tablesBDD.Fichier;
import tablesBDD.MatObj;
import tablesBDD.Matiere;
import tablesBDD.Note;
import tablesBDD.Objectif;

public class Dat {

    public static DAO<Matiere> matiereDAO = new MatiereDAO(MyConnection.getInstance());
    public static DAO<Enseignant> enseignantDAO = new EnseignantDAO(MyConnection.getInstance());
    public static DAO<Eval> evalDAO = new EvalDAO(MyConnection.getInstance());
    public static DAO<Classe> classeDAO = new ClasseDAO(MyConnection.getInstance());
    public static DAO<Eleve> eleveDAO = new EleveDAO(MyConnection.getInstance());
    public static DAO<Objectif> objectifDAO = new ObjectifDAO(MyConnection.getInstance());
    public static DAO<Domaine> domaineDAO = new DomaineDAO(MyConnection.getInstance());
    public static DAO<MatObj> matObjDAO = new MatObjDAO(MyConnection.getInstance());
    public static DAO<Note> noteDAO = new NoteDAO(MyConnection.getInstance());
    public static DAO<Fichier> fichierDAO = new FichierDAO(MyConnection.getInstance());

    public static Enseignant enseignant;

    public static ArrayList<MatObj> arrayMatObj;
    public static ArrayList<Domaine> arrayDomaines;
    public static ArrayList<Enseignant> arrayEnseignants;
    public static ArrayList<Objectif> arrayObjectifs;
    public static ArrayList<Eleve> arrayEleves;
    public static ArrayList<Note> arrayNotes;
    public static ArrayList<Matiere> arrayMatieres;
    public static ArrayList<Classe> arrayClasses;
    public static ArrayList<Eval> arrayEvals;
    public static ArrayList<Fichier> arrayFichiers;
    public static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    public static ImageIcon absent = new ImageIcon(Dat.classLoader.getResource("absent.jpg")),
            cam1 = new ImageIcon(Dat.classLoader.getResource("cam1.jpg")),
            cam2 = new ImageIcon(Dat.classLoader.getResource("cam2.jpg")),
            cam3 = new ImageIcon(Dat.classLoader.getResource("cam3.jpg")),
            cam4 = new ImageIcon(Dat.classLoader.getResource("cam4.jpg")),
            flecheBas = new ImageIcon(Dat.classLoader.getResource("FlecheBas.jpg")),
            flecheHaut = new ImageIcon(Dat.classLoader.getResource("FlecheHaut.jpg")),
            flecheDroite = new ImageIcon(Dat.classLoader.getResource("right_arrow.png")),
            flecheBas2 = new ImageIcon(Dat.classLoader.getResource("bottom_arrow.png")),
            modifier = new ImageIcon(Dat.classLoader.getResource("modifier.png")),
            telechargement = new ImageIcon(new ImageIcon(Dat.classLoader.getResource("téléchargement.png")).getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));


    Dat() {

        Main.prog.bar.setValue(50);
        matObjDAO.getAll();
        Main.prog.bar.setValue(100);
        fichierDAO.getAll();
        Main.prog.bar.setValue(150);
        domaineDAO.getAll();
        Main.prog.bar.setValue(200);
        objectifDAO.getAll();
        Main.prog.bar.setValue(250);
        matiereDAO.getAll();
        Main.prog.bar.setValue(300);
        enseignantDAO.getAll();
        Main.prog.bar.setValue(350);
        classeDAO.getAll();
        Main.prog.bar.setValue(400);
        eleveDAO.getAll();
        Main.prog.bar.setValue(450);
        noteDAO.getAll();
        Main.prog.bar.setValue(500);
        evalDAO.getAll();
		
		
/*		for (Eval eval : arrayEvals) {
			boolean bool = false;
			for(Note note : arrayNotes){
				if (note.getIdEval()==eval.getIdEval())
				{
					bool = true;
				}
			}
			if(!bool){
				evalDAO.delete(eval);
				System.out.println(eval);
			}
		}*/


        Main.prog.bar.setValue(500);

        Main.prog.dispose();
    }






}
