package tablesBDD;

import java.util.ArrayList;

import Main.Dat;

public class Matiere {

	private int idMatiere;
	private String nom;
	private String abr;
	private boolean active;
	private ArrayList<Objectif> objectifs = new ArrayList<Objectif>();
	
	public Matiere(){
		this.nom = "Toutes";}

    public Matiere(String nom){
	    this.nom = nom;
    }
	
	public Matiere(String nom, String abr) {
		super();
		this.nom = nom;
		this.abr = abr;
	}
	
	public Matiere(String nom, String abr, boolean active) {
		this(nom,abr);
		this.active = active;
	}
	public Matiere(int idMatiere, String nom, String abr, boolean active) {
		this(nom,abr,active);
		this.idMatiere = idMatiere;
		setObjectifs();
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
	public String getAbr() {
		return abr;
	}
	public void setAbr(String abr) {
		this.abr = abr;
	}
	@Override
	public String toString() {
		return nom;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public ArrayList<Objectif> getObjectifs() {
		return objectifs;
	}

	//M?thodes persos
	public void setObjectifs() {
		objectifs.clear();
		for (Objectif objectif : Dat.arrayObjectifs) {
			if (!(MatObj.get(this, objectif)==null)){
				objectifs.add(objectif);
				
			}
		}
	}
	
	public static Matiere get(int idMatiere){
		for (Matiere matiere : Dat.arrayMatieres) {
			if (matiere.getIdMatiere()==idMatiere){
				return matiere;
			}
		}
		return null;
	}

	
	
}
