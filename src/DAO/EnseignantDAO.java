package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Main.Dat;
import tablesBDD.Enseignant;

public class EnseignantDAO extends DAO<Enseignant> {
	public EnseignantDAO(Connection conn){
		super(conn);
	}

	@Override
	public Enseignant create(Enseignant enseignant) {
		Enseignant enseignantOUT = enseignant; 
		try {
		      String query =" INSERT INTO enseignant (nom,prenom,mail,pseudo,mdp) Values (?,?,?,?,?)";
		      PreparedStatement prepare = connect.prepareStatement(query);
		      prepare.setString(1,enseignant.getNom());
		      prepare.setString(2, enseignant.getPrenom());
		      prepare.setString(3, enseignant.getMail());
		      prepare.setString(4, enseignant.getPseudo());
		      prepare.setString(5, enseignant.getMdp());
		      prepare.execute();
		      
		      String query2= "SELECT id_enseignant FROM enseignant ORDER BY id_enseignant DESC LIMIT 1";
		      ResultSet result =this.connect.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY).executeQuery(query2);
		      if(result.first()){
					enseignantOUT.setIdEnseignant(result.getInt("id_enseignant"));
				}
		      
			}catch (SQLException e){
				e.printStackTrace();
			}
		return enseignantOUT;
	}

	@Override
	public boolean delete(Enseignant enseignant) {
		try {
			String query ="DELETE FROM enseignant WHERE id_enseignant = ?";
		      PreparedStatement prepare = connect.prepareStatement(query);
		      prepare.setInt(1, enseignant.getIdEnseignant());
		      prepare.execute();
			}catch (SQLException e){
				e.printStackTrace();
			}
		return false;
	}

	@Override
	public boolean update(Enseignant enseignant) {
		try {
		      String query ="UPDATE enseignant SET nom = ?, prenom = ?, mail = ?, pseudo = ? , mdp = ? WHERE id_enseignant = ?";
		      PreparedStatement prepare = connect.prepareStatement(query);
		      
		      prepare.setString(1,enseignant.getNom());
		      prepare.setString(2, enseignant.getPrenom());
		      prepare.setString(3, enseignant.getMail());
		      prepare.setString(4, enseignant.getPseudo());
		      prepare.setString(5, enseignant.getMdp());
		      prepare.setInt(6,enseignant.getIdEnseignant());
		      prepare.execute();
			}catch (SQLException e){
				e.printStackTrace();
			}

		return false;
	}


	@Override
	public Enseignant find(int id) {
		Enseignant enseignant = new Enseignant();
		try {
		ResultSet result =this.connect.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM enseignant WHERE id_enseignant ="+ id);
		if(result.first()){
			enseignant = new Enseignant(
					id,
					result.getString("nom"),
					result.getString("prenom"),
					result.getString("mail"),
					result.getString("pseudo"),
					result.getString("mdp")
					);
		}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return enseignant;
	}
	@Override
	public void getAll(){
		ArrayList<Enseignant> arr = new ArrayList<>();
		try {
			ResultSet result =this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM enseignant ORDER BY nom, prenom");
			int idEnseignant;
			String nom, prenom, mail,pseudo,mdp;
			while(result.next()){
       		idEnseignant = (int) result.getObject("id_enseignant");
       		nom = (String) result.getObject("nom");
       		prenom = (String) result.getObject("prenom");
       		mail = (String) result.getObject("mail");
       		pseudo = (String) result.getObject("pseudo"); 
       		mdp = (String) result.getObject("mdp");
       		arr.add(new Enseignant(idEnseignant, nom, prenom, mail, pseudo, mdp));      		 
       	 }
			
			}catch (SQLException e){
				e.printStackTrace();
			}
		Dat.arrayEnseignants=arr;
	}
}
