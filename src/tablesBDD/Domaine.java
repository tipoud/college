package tablesBDD;

import java.util.ArrayList;

import Main.Dat;

public class Domaine {

    private int idDomaine;
    private int numero;
    private String nom;
    private boolean active;
    private ArrayList<Objectif> Objectifs = new ArrayList<Objectif>();


    public Domaine() {
        this.nom = "Tous";
    }

    public Domaine(String nom) {
        this.nom = nom;
    }

    public Domaine(int numero, String nom) {
        super();
        this.numero = numero;
        this.nom = nom;
    }

    public Domaine(int numero, String nom, boolean active) {
        this(numero, nom);
        this.active = active;

    }

    public Domaine(int idCompetence, int numero, String nom, boolean active) {
        this(numero, nom, active);
        this.idDomaine = idCompetence;
    }


    public int getIdDomaine() {
        return idDomaine;
    }

    public void setIdDomaine(int idDomaine) {
        this.idDomaine = idDomaine;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return numero + ". " + nom;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ArrayList<Objectif> getObjectifs() {
        return Objectifs;
    }

    public void setObjectifs() {
        Objectifs.clear();
        for (Objectif objectif : Dat.arrayObjectifs) {
            if (objectif.getIdDomaine() == idDomaine) {
                Objectifs.add(objectif);
            }
        }
    }

    public static Domaine get(int idDomaine) {
        for (Domaine domaine : Dat.arrayDomaines) {
            if (domaine.getIdDomaine() == idDomaine) {
                return domaine;
            }
        }
        return null;
    }

    public static ArrayList<Domaine> get(Matiere matiere) {
        ArrayList<Domaine> arr = new ArrayList<Domaine>();
        for (Objectif obj : Objectif.get(matiere)) {
            if (!arr.contains(Domaine.get(obj.getIdDomaine()))) {
                arr.add(Domaine.get(obj.getIdDomaine()));
            }
        }
        return arr;
    }


}
