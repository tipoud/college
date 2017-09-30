package tablesBDD;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import Main.Dat;

public class Eleve{
private int idEleve;
private int idClasse;
private String nom;
private String prenom;
private Date dateNaissance;
private String mail1;
private String mail2;
private String pseudo;
private String mdp;
private ArrayList<Note> notes = new ArrayList<>();

public Eleve(){
	super();
	this.nom = "Tous";
	this.prenom="";
}

public Eleve(int idClasse, String nom, String prenom, Date date, String mail1, String mail2,
		String pseudo, String mdp) {
	super();
	this.idClasse = idClasse;
	this.nom = nom;
	this.prenom = prenom;
	this.dateNaissance = date;
	this.mail1 = mail1;
	this.mail2 = mail2;
	this.pseudo = pseudo;
	this.mdp = mdp;
}

public Eleve(int idEleve, int idClasse, String nom, String prenom, Date dateNaissance, String mail1, String mail2,
		String pseudo, String mdp) {
	this(idClasse, nom, prenom, dateNaissance, mail1, mail2, pseudo,  mdp);
	this.idEleve = idEleve;
}

    public Eleve(String nom) {
        this.nom = nom;
    }

    public Note getNotes(Eval eval, Objectif obj) {
	for (Note note : notes) {
		if(note.getIdEval()==eval.getIdEval() && note.getIdObjectif()==obj.getIdObjectif()){
			return note;
		}
	}
	return null;
}

public void setNotes() {
	for (Note note : Dat.arrayNotes) {
		notes.clear();
		if(note.getIdEleve()==idEleve){
			notes.add(note);
		}
	}
}

public static Eleve get(int int1) {
	for (Eleve eleve : Dat.arrayEleves) {
		if(eleve.getIdEleve()==int1){
			return eleve;
		}
	}// TODO Auto-generated method stub
	return null;
}

public static List<Eleve> getByEval(int idEval) {
    List<Eleve> arr = new ArrayList<>();
    Classe classe = Classe.get(idEval);
    if (classe != null){

    }
    return arr;
}





public int getIdEleve() {
	return idEleve;
}

public void setIdEleve(int idEleve) {
	this.idEleve = idEleve;
}

public int getIdClasse() {
	return idClasse;
}

public void setIdClasse(int int1) {
	this.idClasse = int1;
}

public String getPrenom() {
	return prenom;
}

public void setPrenom(String prenom) {
	this.prenom = prenom;
}

public String getNom() {
	return nom;
}

public void setNom(String nom) {
	this.nom = nom;
}

public Date getDateNaissance() {
	return dateNaissance;
}

public void setDateNaissance(Date dateNaissance) {
	this.dateNaissance = dateNaissance;
}

public String getMail1() {
	return mail1;
}

public void setMail1(String mail1) {
	this.mail1 = mail1;
}

public String getMail2() {
	return mail2;
}

public void setMail2(String mail2) {
	this.mail2 = mail2;
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
    if("Aucun élève disponible".toLowerCase().equals(nom.toLowerCase())){
        return nom;
    }
	return nom.toUpperCase() + " " + prenom;
}

public ArrayList<Note> getNotes() {
	return notes;
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + idEleve;
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
	Eleve other = (Eleve) obj;
	if (idEleve != other.idEleve)
		return false;
	return true;
}
}
