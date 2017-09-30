package tablesBDD;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import Main.Dat;

public class Eval {
    private int idEval;
    private int idEnseignant;
    private Date date = null;
    private String type;
    private int idClasse;
    private int idMatiere;
    private int idFichier;
    private String nom;
    private String description;


    public Eval(String nom) {
        super();
        this.nom = nom;
    }


    public Eval() {
        super();
        this.nom = "Toutes les évaluations";
        this.type = "";
    }

    public Eval(int idEnseignant, Date date, String type, int idClasse, int idMatiere,
                String nom, String description, int idFichier) {
        super();
        this.idEnseignant = idEnseignant;
        this.date = date;
        this.type = type;
        this.idClasse = idClasse;
        this.idMatiere = idMatiere;
        this.nom = nom;
        this.description = description;
        this.idFichier = idFichier;
    }

    public Eval(int idEval, int idEnseignant, Date date, String type, int idClasse, int idMatiere,
                String nom, String description, int idFichier) {
        this(idEnseignant, date, type, idClasse, idMatiere, nom, description, idFichier);
        this.idEval = idEval;
    }

    public int getIdEval() {
        return idEval;
    }

    public void setIdEval(int idEval) {
        this.idEval = idEval;
    }

    public int getIdEnseignant() {
        return idEnseignant;
    }

    public void setIdEnseignant(int idEnseignant) {
        this.idEnseignant = idEnseignant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIdMatiere() {
        return idMatiere;
    }

    public void setIdMatiere(int idMatiere) {
        this.idMatiere = idMatiere;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(int idClasse) {
        this.idClasse = idClasse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdFichier() {
        return idFichier;
    }

    public void setIdFichier(int idFichier) {
        this.idFichier = idFichier;
    }

    @Override
    public String toString() {
        if (!(date == null)) {
            return Matiere.get(getIdMatiere()).getAbr() + ": " + date + " " + type + ". " + nom;
        } else
            return nom;

    }

    //Méthodes persos
    public static Eval get(int idEval) {
        for (Eval eval : Dat.arrayEvals) {
            if (eval.getIdEval() == idEval) {
                return eval;
            }
        }
        return null;
    }

    public static ArrayList<Eval> get(int idEnseignant, int idClasse) {
        ArrayList<Eval> arr = new ArrayList<>();
        for (Eval eval : Dat.arrayEvals) {
            if (eval.getIdEnseignant() == idEnseignant && eval.getIdClasse() == idClasse) {
                arr.add(eval);
            }
        }
        return arr;
    }

    public static ArrayList<Eval> get(Enseignant enseignant) {
        ArrayList<Eval> arr = new ArrayList<>();
        for (Eval eval : Dat.arrayEvals) {
            if (eval.getIdEnseignant() == enseignant.getIdEnseignant()) {
                arr.add(eval);
            }
        }
        return arr;
    }

    public ArrayList<Objectif> getObjectifs() {
        ArrayList<Objectif> arr = new ArrayList<>();
        ArrayList<Note> notes = Note.get(this);
        Eleve eleve;
        if (!notes.isEmpty()) {
            eleve = Eleve.get(notes.get(0).getIdEleve());
            for (Note note : Note.get(this, eleve)) {
                arr.add(Objectif.get(note.getIdObjectif()));
            }
        }
        return arr;
    }
}
