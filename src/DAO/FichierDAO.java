package DAO;
	import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Main.Dat;
import tablesBDD.Fichier;

	public class FichierDAO extends DAO<Fichier> {
		public FichierDAO(Connection conn){
			
			super(conn);
		}

		@Override
		public Fichier create(Fichier fichier) {
			Fichier fichierOUT = fichier;
			try {
			      String query =" INSERT INTO fichier (nom, type, adresse) Values (?,?,?)";
			      PreparedStatement prepare = connect.prepareStatement(query);
			      //prepare.setInt(1, fichier.getIdFichier());
			      prepare.setString(1, fichier.getNom());
			      prepare.setString(2,fichier.getType());
			      prepare.setString(3, fichier.getAdresse());
			      prepare.execute();
			      String query2= "SELECT id_fichier FROM fichier ORDER BY id_fichier DESC LIMIT 1";
			      ResultSet result =this.connect.createStatement(
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY).executeQuery(query2);
			      if(result.first()){
						fichierOUT.setIdFichier(result.getInt("id_fichier"));
			      }
			      
				}catch (SQLException e){
					e.printStackTrace();
				}
			return fichierOUT;
		}

		@Override
		public boolean delete(Fichier fichier) {
			try {
				String query ="DELETE FROM fichier WHERE id_fichier = ?";
			      PreparedStatement prepare = connect.prepareStatement(query);
			      prepare.setInt(1, fichier.getIdFichier());
			      prepare.execute();
				}catch (SQLException e){
					e.printStackTrace();
				}
			return false;
		}

		@Override
		public boolean update(Fichier fichier) {
			try {
			      String query ="UPDATE fichier SET nom = ?, type = ?, adresse = ? WHERE id_fichier = ?";
			      PreparedStatement prepare = connect.prepareStatement(query);
			      prepare.setString(1, fichier.getNom());
			      prepare.setString(2,fichier.getType());
			      prepare.setString(3, fichier.getAdresse());
			      prepare.setInt(4,fichier.getIdFichier());
			      prepare.execute();
				}catch (SQLException e){
					e.printStackTrace();
				}

			return false;
		}

		@Override
		public Fichier find(int id) {
			Fichier fichier = new Fichier();
			try {
			ResultSet result =this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM fichier WHERE id_fichier ="+ id);
			if(result.first()){
			fichier = new Fichier(
						id,
						result.getString("nom"),
						result.getString("type"),
						result.getString("adresse")
						);
			}
			}catch (SQLException e){
				e.printStackTrace();
			}
			return fichier;
		}
		@Override
		public void getAll(){
			ArrayList<Fichier> arr = new ArrayList<>();
			try {
				ResultSet result =this.connect.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM fichier");
				Fichier fichier;
				while(result.next()){
					fichier = new Fichier(
							result.getInt("id_fichier"),
							result.getString("nom"),
							result.getString("type"),
							result.getString("adresse")
							);
	       		 arr.add(fichier);      		 
	       	 }
				
				}catch (SQLException e){
					e.printStackTrace();
				}
			Dat.arrayFichiers = arr;
		}

	}
