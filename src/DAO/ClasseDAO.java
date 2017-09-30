package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Main.Dat;
import tablesBDD.Classe;

public class ClasseDAO extends DAO<Classe> {
	public ClasseDAO(Connection conn){
		super(conn);
	}

	@Override
	public Classe create(Classe classe) {
		Classe classeOUT = classe;
		try {
		      String query =" INSERT INTO classe (niveau,nom) Values (?,?)";
		      PreparedStatement prepare = connect.prepareStatement(query);
		      prepare.setInt(1, classe.getNiveau());
		      prepare.setString(2,classe.getNom());
		      prepare.execute();
		      String query2= "SELECT id_classe FROM classe ORDER BY id_classe DESC LIMIT 1";
		      ResultSet result =this.connect.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY).executeQuery(query2);
		      if(result.first()){
					classeOUT.setIdClasse(result.getInt("id_classe"));
		      }
			}catch (SQLException e){
				e.printStackTrace();
			}
		return classeOUT;
	}

	@Override
	public boolean delete(Classe classe) {
		try {
			String query ="DELETE FROM classe WHERE id_classe = ?";
		      PreparedStatement prepare = connect.prepareStatement(query);
		      prepare.setInt(1, classe.getIdClasse());
		      prepare.execute();
			}catch (SQLException e){
				e.printStackTrace();
			}
		return false;
	}

	@Override
	public boolean update(Classe classe) {
		try {
		      String query ="UPDATE classe SET niveau = ?, nom = ?, active = ? WHERE id_classe = ?";
		      PreparedStatement prepare = connect.prepareStatement(query);
		      
		      prepare.setInt(1, classe.getNiveau());
		      prepare.setString(2,classe.getNom());
		      prepare.setBoolean(3, classe.isActive());
		      prepare.setInt(4,classe.getIdClasse());
		      prepare.execute();
			}catch (SQLException e){
				e.printStackTrace();
			}

		return false;
	}

	@Override
	public Classe find(int id) {
		Classe classe = new Classe();
		try {
		ResultSet result =this.connect.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM classe WHERE id_classe ="+ id);
		if(result.first()){
		classe = new Classe(
					id,
					result.getInt("niveau"),
					result.getString("nom"),
					result.getBoolean("active")
					);
		}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return classe;
	}
	
	@Override
	public void getAll(){
		ArrayList<Classe> arr = new ArrayList<>();
		try {
			ResultSet result =this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM classe ORDER BY niveau DESC, nom ASC");
			int idClasse, niveau;
			String nom;
			boolean active;
			while(result.next()){
       		 idClasse = (int) result.getObject("id_classe");
       		 niveau = (int) result.getObject("niveau");
       		 nom = (String) result.getObject("nom");
       		 active = (boolean) result.getObject("active");
       		 arr.add(new Classe(idClasse, niveau, nom, active));      		 
       	 }
			
			}catch (SQLException e){
				e.printStackTrace();
			}
		Dat.arrayClasses = arr;
		Dat.eleveDAO.getAll();
	}
	
	public ArrayList<Classe> getAllActive(){
		ArrayList<Classe> arr = new ArrayList<>();
		try {
			ResultSet result =this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM classe WHERE active = TRUE ORDER BY niveau DESC, nom ASC");
			int idClasse, niveau;
			String nom;
			boolean active;
			while(result.next()){
       		 idClasse = (int) result.getObject("id_classe");
       		 niveau = (int) result.getObject("niveau");
       		 nom = (String) result.getObject("nom");
       		 active = (boolean) result.getObject("active");
       		 arr.add(new Classe(idClasse, niveau, nom, active));   	 
       	 }
			
			}catch (SQLException e){
				e.printStackTrace();
			}
		return arr;
	}
	
	
}