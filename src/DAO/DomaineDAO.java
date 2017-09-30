package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Administration.Administration;
import Main.Dat;
import tablesBDD.Domaine;

public class DomaineDAO extends DAO<Domaine> {
	public DomaineDAO(Connection conn){
		super(conn);
	}

	@Override
public Domaine create(Domaine domaine) {
		Domaine comptenceOUT = domaine;
		try {
		      String query =" INSERT INTO domaine (numero,nom,active) Values (?,?,?)";
		      PreparedStatement prepare = connect.prepareStatement(query);
		      prepare.setInt(1, domaine.getNumero());
		      prepare.setString(2,domaine.getNom());
		      prepare.setBoolean(3,false);

		      prepare.execute();
		      
		      String query2= "SELECT id_domaine FROM domaine ORDER BY id_domaine DESC LIMIT 1";
		      ResultSet result =this.connect.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY).executeQuery(query2);
		      if(result.first()){
					comptenceOUT.setIdDomaine(result.getInt("id_domaine"));
		      }
			}catch (SQLException e){
				e.printStackTrace();
			}
		return comptenceOUT;
	}

	@Override
	public boolean delete(Domaine domaine) {
		try {
			String query ="DELETE FROM domaine WHERE id_domaine = ?";
		      PreparedStatement prepare = connect.prepareStatement(query);
		      prepare.setInt(1, domaine.getIdDomaine());
		      prepare.execute();
			}catch (SQLException e){
				e.printStackTrace();
			}
		return false;
	}

	@Override
	public boolean update(Domaine domaine) {
		try {
		      String query ="UPDATE domaine SET numero = ?, nom = ? WHERE id_domaine = ?";
		      PreparedStatement prepare = connect.prepareStatement(query);
		      prepare.setInt(1, domaine.getNumero());
		      prepare.setString(2, domaine.getNom());
		      prepare.setInt(3,domaine.getIdDomaine());
		      prepare.execute();
			}catch (SQLException e){
				e.printStackTrace();
			}
		return false;
	}

	@Override
	public Domaine find(int id) {
		Domaine domaine = new Domaine();
		try {
		ResultSet result =this.connect.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM domaine WHERE id_domaine ="+ id);
		if(result.first()){
		domaine = new Domaine(
					id,
					result.getInt("numero"),
					result.getString("nom"),
					result.getBoolean("active")
					);
		}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return domaine;
	}
	@Override
	public void getAll(){
		ArrayList<Domaine> arr = new ArrayList<>();
		try {
			ResultSet result =this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM domaine ORDER BY numero");
			int iddomaine, numero;
			String nom;
			boolean active;
			while(result.next()){
       		 iddomaine = (int) result.getObject("id_domaine");
       		 numero = (int) result.getObject("numero");
      		 nom = (String) result.getObject("nom");
      		 active = (boolean) result.getObject("active");
       		 arr.add(new Domaine(iddomaine, numero, nom, active));      		 
       	 }
			
			}catch (SQLException e){
				e.printStackTrace();
			}
		Dat.arrayDomaines = arr;
		Dat.objectifDAO.getAll();
	}
	
	public ArrayList<Domaine> getAllActive(){
		ArrayList<Domaine> arr = new ArrayList<>();
		try {
			ResultSet result =this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM domaine WHERE active = true ORDER BY numero");
			int iddomaine, numero;
			String nom;
			boolean active;
			while(result.next()){
       		 iddomaine = (int) result.getObject("id_domaine");
       		 numero = (int) result.getObject("numero");
      		 nom = (String) result.getObject("nom");
      		 active = (boolean) result.getObject("active");
       		 arr.add(new Domaine(iddomaine, numero, nom, active));          		 
       	 }
			
			}catch (SQLException e){
				e.printStackTrace();
			}
		return arr;
	}
	
	public ArrayList<Domaine> getByMat(int idMat) {
		ArrayList<Domaine> arr = new ArrayList<>();
		try {
			String str = "SELECT DISTINCT domaine.* FROM objectif INNER JOIN domaine ON objectif.id_domaine = domaine.id_domaine INNER JOIN matobj ON objectif.id_objectif = matobj.id_objectif WHERE matobj.id_matiere = "+ idMat +" ORDER BY domaine.numero;";
			ResultSet result =this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(str);
			int iddomaine, numero;
			String nom;
			boolean active;
			while(result.next()){
       		 iddomaine = (int) result.getObject("id_domaine");
       		 numero = (int) result.getObject("numero");
      		 nom = (String) result.getObject("nom");
      		 active = (boolean) result.getObject("active");
       		 arr.add(new Domaine(iddomaine, numero, nom, active));       
       	 }
			
			}catch (SQLException e){
				e.printStackTrace();
			}
		return arr;
	}
	
	public boolean activate(Domaine domaine) {
		ArrayList<Domaine> arr = getAllActive();
		ArrayList<Domaine> arr2 = Dat.arrayDomaines;
		int nb = arr.size() + 1 ;
		for (Domaine comp: arr2) {
			if (!comp.isActive() && comp.getNumero()<domaine.getNumero()){
				int num = comp.getNumero()+1;
				comp.setNumero(num);
				update(comp);
			}
		}
		try {
		      String query ="UPDATE domaine SET numero = " + nb  +" , active = TRUE WHERE id_domaine = " + domaine.getIdDomaine() ;
		      PreparedStatement prepare = connect.prepareStatement(query); 
		      prepare.execute();
			}catch (SQLException e){
				e.printStackTrace();
			}
		Dat.objectifDAO.getAll();
		Dat.domaineDAO.getAll();
		Administration.myModifObjectif.revalidate();
		return false;
	}
	
	public boolean desactivate(Domaine domaine) {
		int nb = Dat.arrayDomaines.size() ;
		
		for (Domaine comp: Dat.arrayDomaines) {
			if (comp.getNumero()>domaine.getNumero()){
				comp.setNumero(comp.getNumero()-1);
				update(comp);
			}
		}
		try {
		      String query ="UPDATE domaine SET numero = " +nb+ " , active = FALSE WHERE id_domaine = " + domaine.getIdDomaine() ;
		      PreparedStatement prepare = connect.prepareStatement(query); 
		      prepare.execute();
			}catch (SQLException e){
				e.printStackTrace();
			}
		Dat.objectifDAO.getAll();
		Dat.domaineDAO.getAll();
		return false;
	}
	
}