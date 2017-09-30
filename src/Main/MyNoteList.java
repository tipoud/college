package Main;

import java.util.ArrayList;

import tablesBDD.Eleve;
import tablesBDD.Eval;
import tablesBDD.Note;
import tablesBDD.Objectif;

public class MyNoteList extends ArrayList<Note> {

	private static final long serialVersionUID = 1L;

	public MyNoteList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Note> get(Eleve eleve, Eval eval, Objectif objectif) {
		ArrayList<Note> arr =new ArrayList<>();
		for (Note note : this) {
			if (note.getIdEleve()==eleve.getIdEleve() 
					&& note.getIdEval() == eval.getIdEval() 
					&& note.getIdObjectif()==objectif.getIdObjectif()){
				arr.add(note);	
			}
		}
		return arr;
	}

	public ArrayList<Note> getNonNul(Objectif objectif) {
		ArrayList<Note> arr =new ArrayList<>();
		for (Note note : this) {
			if (note.getIdObjectif()==objectif.getIdObjectif() && note.getVal()!=0){
				arr.add(note);
			}
		}
		return arr;
	}


}
