package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Main.Dat;
import tablesBDD.Eleve;
import tablesBDD.Eval;
import tablesBDD.Note;
import tablesBDD.Objectif;


public class NoteDAO extends DAO<Note> {
	PreparedStatement prepareCreate;
	
	public NoteDAO(Connection conn){
		super(conn);
		
		String queryCreate =" INSERT INTO note (id_eleve,id_eval,val,id_objectif,absent,num) Values (?,?,?,?,?,?)";
	    try {
			prepareCreate = connect.prepareStatement(queryCreate);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Note create(Note note) {
		Note noteOUT = note;
		try {	      
		      prepareCreate.setInt(1, note.getIdEleve());
		      prepareCreate.setInt(2,note.getIdEval());
		      prepareCreate.setInt(3, note.getVal());
		      prepareCreate.setInt(4,note.getIdObjectif());
		      prepareCreate.setBoolean(5, note.isAbsent());
		      prepareCreate.setInt(6, note.getNum());
		      prepareCreate.execute();
			}catch (SQLException e){
				e.printStackTrace();
			}
		return noteOUT;
	}

	@Override
	public boolean delete(Note note) {
		try {
			String query ="DELETE FROM note WHERE id_note = ?";
		      PreparedStatement prepare = connect.prepareStatement(query);
		      prepare.setInt(1, note.getIdNote());
		      prepare.execute();
			}catch (SQLException e){
				e.printStackTrace();
			}
		return false;
	}

	@Override
	public boolean update(Note note) {
		try {
		      String query ="UPDATE note SET id_eleve =? ,id_eval =? ,val = ? ,id_objectif = ? ,absent = ? ,num = ? WHERE id_note = ?";
		      PreparedStatement prepare = connect.prepareStatement(query);
		      prepare.setInt(1, note.getIdEleve());
		      prepare.setInt(2,note.getIdEval());
		      prepare.setInt(3, note.getVal());
		      prepare.setInt(4,note.getIdObjectif());
		      prepare.setBoolean(5, note.isAbsent());
		      prepare.setInt(6, note.getNum());
		      prepare.setInt(7,note.getIdNote());
		      prepare.execute();
			}catch (SQLException e){
				e.printStackTrace();
			}

		return false;
	}

	@Override
	public Note find(int id) {
		Note note = new Note();
		try {
		ResultSet result =this.connect.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM note WHERE id_note ="+ id);
		if(result.first()){
		note = new Note(
					id,
					result.getInt("id_eleve"),
					result.getInt("id_eval"),
					result.getInt("val"),
					result.getInt("id_objectif"),
					result.getBoolean("absent"),
					result.getInt("num")
					);
		}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return note;
	}
	@Override
	public void getAll(){
		ArrayList<Note> arr = new ArrayList<>();
		try {
			ResultSet result =this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
							"SELECT note.* FROM note"
							+ " INNER JOIN objectif ON note.id_objectif = objectif.id_objectif"
							+ " INNER JOIN domaine ON objectif.id_domaine = domaine.id_domaine"
							+ " INNER JOIN eval ON note.id_eval = eval.id_eval ORDER BY eval.date, domaine.numero, objectif.numero, note.num ASC");
			for (Eleve eleve : Dat.arrayEleves) {
				eleve.getNotes().clear();
			}		
			for (Objectif objectif : Dat.arrayObjectifs) {
				objectif.getNotes().clear();
			}
			Note note;
			while(result.next()){
				note = new Note(
						result.getInt("id_note"),
						result.getInt("id_eleve"),
						result.getInt("id_eval"),
						result.getInt("val"),
						result.getInt("id_objectif"),
						result.getBoolean("absent"),
						result.getInt("num")
						);
       		arr.add(note);
			Eleve.get(result.getInt("id_eleve")).getNotes().add(note);
			Objectif.get(result.getInt("id_objectif")).getNotes().add(note);
       	 }
			}catch (SQLException e){
				e.printStackTrace();
			}
		Dat.arrayNotes = arr;
	}
	
	public void delete(Eval eval) {
		try {
			String query ="DELETE FROM note WHERE id_eval = "+ eval.getIdEval();
		      PreparedStatement prepare = connect.prepareStatement(query);
		      prepare.execute();
			}catch (SQLException e){
				e.printStackTrace();
			}// TODO Auto-generated method stub
		
	}
	

}