package tablesBDD;

import java.util.ArrayList;

import Main.Dat;

public class Classe {
    private int idClasse;
    private int niveau;
    private String nom;
    private ArrayList<Eleve> arrayEleves = new ArrayList<>();
    private boolean active = true;

    public Classe(){
    }

    public Classe(String nom) {
        this.nom = nom;
    }

    public Classe(int niveau, String nom) {
        super();
        this.niveau = niveau;
        this.nom = nom;
    }
    public Classe(int niveau, String nom, boolean active) {
        this(niveau,nom);
        this.active = active;
    }

    public Classe(int idClasse, int niveau, String nom, boolean active) {
        this(niveau,nom,active);
        this.idClasse = idClasse;
    }

    public int getIdClasse() {
        return idClasse;
    }
    public void setIdClasse(int idClasse) {
        this.idClasse = idClasse;
    }
    public int getNiveau() {
        return niveau;
    }
    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    @Override
    public String toString() {
        return niveau + " " + nom;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public ArrayList<Eleve> getEleves() {
        return arrayEleves;
    }



    //Méthodes persos
    public void setEleves() {
        for (Eleve eleve : Dat.arrayEleves) {
            if (eleve.getIdClasse()==idClasse){
                arrayEleves.add(eleve);
            }
        }
    }

    public static Classe get(int idClasse){
        for (Classe classe : Dat.arrayClasses) {
            if (classe.getIdClasse()==idClasse){
                return classe;
            }
        }
        return null;
    }

}
