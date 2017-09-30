package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Main.Dat;
import tablesBDD.Matiere;

public class MatiereDAO extends DAO<Matiere> {
	public MatiereDAO(Connection conn){
		super(conn);
	}

	@Override
	public Matiere create(Matiere matiere) {
		Matiere matiereOUT = matiere;
		try {
		      String query =" INSERT INTO matiere (nom,abr) Values (?,?)";
		      PreparedStatement prepare = connect.prepareStatement(query);
		      prepare.setString(1, matiere.getNom());
		      prepare.setString(2,matiere.getAbr());
		      prepare.execute();
		      
		      String query2= "SELECT id_matiere FROM matiere ORDER BY id_matiere DESC LIMIT 1";
		      ResultSet result =this.connect.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY).executeQuery(query2);
		      if(result.first()){
					matiereOUT.setIdMatiere(result.getInt("id_matiere"));
		      }
			}catch (SQLException e){
				e.printStackTrace();
			}
		return matiereOUT;
	}

	@Override
	public boolean delete(Matiere matiere) {
		try {
			String query ="DELETE FROM matiere WHERE id_matiere = ?";
		      PreparedStatement prepare = connect.prepareStatement(query);
		      prepare.setInt(1, matiere.getIdMatiere());
		      prepare.execute();
			}catch (SQLException e){
				e.printStackTrace();
			}
		return false;
	}

	@Override
	public boolean update(Matiere matiere) {
		try {
		      String query ="UPDATE matiere SET nom = ?, abr = ? WHERE id_matiere = ?";
		      PreparedStatement prepare = connect.prepareStatement(query);
		      
		      prepare.setString(1, matiere.getNom());
		      prepare.setString(2,matiere.getAbr());
		      prepare.setInt(3,matiere.getIdMatiere());
		      prepare.execute();
			}catch (SQLException e){
				e.printStackTrace();
			}
		return false;
	}

	@Override
	public Matiere find(int id) {
		Matiere matiere = new Matiere();
		try {
		ResultSet result =this.connect.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM matiere WHERE id_matiere ="+ id);
		if(result.first()){
		matiere = new Matiere(
					id,
					result.getString("nom"),
					result.getString("abr"),
					result.getBoolean("active")
					);
		}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return matiere;
	}
	@Override
	public void getAll(){
		ArrayList<Matiere> arr = new ArrayList<>();
		try {
			ResultSet result =this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM matiere ORDER BY nom");

			while(result.next()){
			Matiere	matiere = new Matiere(
					    result.getInt("id_matiere"),
						result.getString("nom"),
						result.getString("abr"),
						result.getBoolean("active")
						);
       		 arr.add(matiere);      		 
       	 }
			
			}catch (SQLException e){
				e.printStackTrace();
			}
		Dat.arrayMatieres = arr;
	}
}