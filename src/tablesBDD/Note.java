package tablesBDD;

import java.util.ArrayList;

import Main.Dat;

public class Note {

private int idNote;
private int idEleve;
private int idEval;
private int val;
private int idObjectif;
private boolean absent;
private int num;

public Note(){
	super();
	val = 9;
	}

public Note(int idEleve, int idEval, int val, int idComp, boolean absent, int nivSup) {
	super();
	this.idEleve = idEleve;
	this.idEval = idEval;
	this.val = val;
	this.idObjectif = idComp;
	this.absent = absent;
	this.num = nivSup;
}
@Override
public String toString() {
	return "Note [idNote=" + idNote + ", idEleve=" + idEleve + ", idEval=" + idEval + ", val=" + val + ", idComp="
			+ idObjectif + ", absent=" + absent + ", nivSup=" + num + "]";
}

public Note(int idNote, int idEleve, int idEval, int val, int idComp, boolean absent, int nivSup) {
	this(idEleve,idEval,val,idComp,absent,nivSup);
	this.idNote = idNote;
}

public int getIdNote() {
	return idNote;
}

public void setIdNote(int idNote) {
	this.idNote = idNote;
}

public int getIdEleve() {
	return idEleve;
}

public void setIdEleve(int idEleve) {
	this.idEleve = idEleve;
}

public int getIdEval() {
	return idEval;
}

public void setIdEval(int idEval) {
	this.idEval = idEval;
}

public int getVal() {
	return val;
}

public void setVal(int val) {
	this.val = val;
}

public int getIdObjectif() {
	return idObjectif;
}

public void setIdObjectif(int idComp) {
	this.idObjectif = idComp;
}

public boolean isAbsent() {
	return absent;
}

public void setAbsent(boolean absent) {
	this.absent = absent;
}

public int getNum() {
	return num;
}

public void setNum(int nivSup) {
	this.num = nivSup;
}

//Méthodes persos
public static Note get(int idNote){
	for (Note note : Dat.arrayNotes) {
		if (note.getIdNote()==idNote){
			return note;
		}
	}
	return null;
}

public static ArrayList<Note> get(Eleve eleve, Objectif objectif) {
	
	ArrayList<Note> arr = new ArrayList<>();
	for (Note note : eleve.getNotes()) {
		if (note.getIdObjectif() == objectif.getIdObjectif() ){
				arr.add(note);	
		}
	}
	return arr;

}

public static ArrayList<Note> get(Eleve eleve, Objectif objectif, Matiere mat) {
	ArrayList<Note> arr = new ArrayList<>();
	for (Note note : eleve.getNotes()) {
		Matiere mat1 = Matiere.get(Eval.get(note.getIdEval()).getIdMatiere());
		if (note.getIdObjectif() == objectif.getIdObjectif() && mat1== mat){
				arr.add(note);	
		}
	}
	return arr;
}

public static ArrayList<Note> get(Eleve eleve, Objectif objectif, Eval eval) {
	
	ArrayList<Note> arr = new ArrayList<Note>();
	for (Note note : eleve.getNotes()) {
		if (note.getIdObjectif() == objectif.getIdObjectif() && note.getIdEval() == eval.getIdEval()){
				arr.add(note);
		}
	}
	return arr;
}

public static ArrayList<Note> get(Eleve eleve, Objectif objectif, Matiere mat, Eval eval) {
	ArrayList<Note> arr = new ArrayList<Note>();
	for (Note note : eleve.getNotes()) {
		Matiere mat1 = Matiere.get(Eval.get(note.getIdEval()).getIdMatiere());
		if (note.getIdObjectif() == objectif.getIdObjectif() && note.getIdEval() == eval.getIdEval() && mat1== mat){
				arr.add(note);		
		}
	}
	return arr;
}

public static ArrayList<Note> get(Eval eval) {
	ArrayList<Note> arr = new ArrayList<>();
	for (Note note : Dat.arrayNotes) {
		if(note.getIdEval()==eval.getIdEval()){
			arr.add(note);
		}
	}
	return arr;
}

public static ArrayList<Note> get(Eval eval, Eleve eleve) {
	ArrayList<Note> arr = new ArrayList<>();
	for (Note note : Dat.arrayNotes) {
		if(note.getIdEval()==eval.getIdEval() && note.getIdEleve()==eleve.getIdEleve() ){
			arr.add(note);
		}
	}
	return arr;
}



}
