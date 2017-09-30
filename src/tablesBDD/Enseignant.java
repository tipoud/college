package tablesBDD;

import Main.Dat;

public class Enseignant {

	private int idEnseignant;
	private String nom;
	private String prenom;
	private String mail;
	private String pseudo;
	private String mdp;
	
	public Enseignant(){
	}
	
	public Enseignant(String nom, String prenom, String mail, String pseudo, String mdp) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.pseudo = pseudo;
		this.mdp = mdp;
	}
	
	public Enseignant(int idEnseignant, String nom, String prenom, String mail, String pseudo, String mdp) {
		this(nom, prenom, mail, pseudo, mdp);
		this.idEnseignant = idEnseignant;
	}
	
	public int getIdEnseignant() {
		return idEnseignant;
	}
	public void setIdEnseignant(int idEnseignant) {
		this.idEnseignant = idEnseignant;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	@Override
	public String toString() {
		return prenom +" "+ nom;
	}
	
	//Méthodes persos
	public Enseignant get(int idEnseignant){
		for (Enseignant enseignant : Dat.arrayEnseignants) {
			if (enseignant.getIdEnseignant()==idEnseignant){
				return enseignant;
			}
		}
		return null;
	}
	
	
}
