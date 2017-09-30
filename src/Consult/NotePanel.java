package Consult;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import tablesBDD.Eleve;
import tablesBDD.Eval;
import tablesBDD.Matiere;
import tablesBDD.Note;
import tablesBDD.Objectif;

@SuppressWarnings("serial")
public class NotePanel extends JPanel {
	ArrayList<Note> arr = new ArrayList<>() ;
	
	public NotePanel(){
		super();
		this.setPreferredSize(new Dimension(800,30));
		this.setOpaque(false);
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
			
		}

	public NotePanel(Eleve eleve, Objectif objectif) {
			this();
			arr = Note.get(eleve,objectif);
			for (Note note : arr) {
				JButton panNote = new MyPanNote(note);
				this.add(panNote);	
			}
		

	
}
	
	public NotePanel(Eleve eleve, Objectif objectif, Matiere mat) {
		this();

		arr = Note.get(eleve,objectif,mat);
		for (Note note : arr) {
			JButton panNote = new MyPanNote(note);
			this.add(panNote);
			
		}
}
	public NotePanel(Eleve eleve, Objectif objectif, Eval eval) {
		this();

		arr = Note.get(eleve,objectif,eval);
		for (Note note : arr) {
			JButton panNote = new MyPanNote(note);
			this.add(panNote);
			
		}
}	
	public NotePanel(Eleve eleve, Objectif objectif, Matiere mat, Eval eval) {
	this();

	arr = Note.get(eleve,objectif,mat,eval);
	for (Note note : arr) {
		JButton panNote = new MyPanNote(note);
		this.add(panNote);
		
	}
}
	
}
