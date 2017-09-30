package tablesBDD;

import java.sql.Date;
import java.util.ArrayList;

import Main.Dat;

public class Objectif {
    private int idObjectif, idCompetence;
    private String nom, niveau1, niveau2, niveau3, niveau4;
    private int numero;
    private boolean active;
    private Date dateFin;
    private ArrayList<Note> notes = new ArrayList<>();

    public Objectif() {
        this.nom = "";
    }

    public Objectif(String nom, int numero, String niveau1, String niveau2, String niveau3, String niveau4, boolean active, Date dateFin, int idCompetence) {
        super();
        this.nom = nom;
        this.numero = numero;
        this.niveau1 = niveau1;
        this.niveau2 = niveau2;
        this.niveau3 = niveau3;
        this.niveau4 = niveau4;
        this.active = active;
        this.dateFin = dateFin;
        this.idCompetence = idCompetence;
    }

    public Objectif(int idObjectif, String nom, int numero, String niveau1, String niveau2, String niveau3, String niveau4, boolean active, Date dateFin, int idCompetence) {
        this(nom, numero, niveau1, niveau2, niveau3, niveau4, active, dateFin, idCompetence);
        this.idObjectif = idObjectif;
    }


    public int getIdObjectif() {
        return idObjectif;
    }

    public void setIdObjectif(int idObjectif) {
        this.idObjectif = idObjectif;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNiveau1() {
        return niveau1;
    }

    public void setNiveau1(String niveau1) {
        this.niveau1 = niveau1;
    }

    public String getNiveau2() {
        return niveau2;
    }

    public void setNiveau2(String niveau2) {
        this.niveau2 = niveau2;
    }

    public String getNiveau3() {
        return niveau3;
    }

    public void setNiveau3(String niveau3) {
        this.niveau3 = niveau3;
    }

    public String getNiveau4() {
        return niveau4;
    }

    public void setNiveau4(String niveau4) {
        this.niveau4 = niveau4;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public int getIdDomaine() {
        return idCompetence;
    }

    public void setIdCompetence(int idCompetence) {
        this.idCompetence = idCompetence;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    //Méthodes persos
    public static Objectif get(int idObjectif) {
        for (Objectif objectif : Dat.arrayObjectifs) {
            if (objectif.getIdObjectif() == idObjectif) {
                return objectif;
            }
        }
        return null;
    }

    public static ArrayList<Objectif> get(Matiere matiere) {
        ArrayList<Objectif> arr = new ArrayList<>();
        for (Objectif objectif : Dat.arrayObjectifs) {
            if (!(MatObj.get(matiere, objectif) == null)) {
                arr.add(objectif);
            }
        }
        return arr;
    }

    public static ArrayList<Objectif> get(Eval eval) {
        ArrayList<Objectif> arr = new ArrayList<>();
        for (Note note : Dat.arrayNotes) {
            if (note.getIdEval() == eval.getIdEval()) {
                if (!arr.contains(get(note.getIdObjectif()))) {
                    arr.add(get(note.getIdObjectif()));
                }

            }
        }
        return arr;

    }

    public static ArrayList<Objectif> get(Matiere matiere, Domaine comp) {
        ArrayList<Objectif> arr = new ArrayList<>();
        for (Objectif objectif : comp.getObjectifs()) {
            if (!(MatObj.get(matiere, objectif) == null)) {
                arr.add(objectif);
            }
        }
        return arr;
    }

    @Override
    public String toString() {
        int numDomaine = Domaine.get(idCompetence).getNumero();
        return "D" + numDomaine + " " + numero + ". " + nom;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + idObjectif;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Objectif other = (Objectif) obj;
        if (idObjectif != other.idObjectif)
            return false;
        return true;
    }
}
