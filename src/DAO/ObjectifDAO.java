package DAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Main.Dat;
import tablesBDD.Domaine;
import tablesBDD.Eval;
import tablesBDD.Objectif;

public class ObjectifDAO extends DAO<Objectif> {
	public ObjectifDAO(Connection conn){
		super(conn);
	}

	@Override
	public Objectif create(Objectif objectif) {
		Objectif objectifOUT = objectif;
		try {
		      String query =" INSERT INTO objectif (nom,numero,niveau1,niveau2,niveau3,niveau4,active,datefin,id_domaine) Values (?,?,?,?,?,?,?,?,?)";
		      PreparedStatement prepare = connect.prepareStatement(query);
		      prepare.setString(1, objectif.getNom());
		      prepare.setInt(2, objectif.getNumero());
		      prepare.setString(3, objectif.getNiveau1());
		      prepare.setString(4, objectif.getNiveau2());
		      prepare.setString(5, objectif.getNiveau3());
		      prepare.setString(6, objectif.getNiveau4());
		      prepare.setBoolean(7, objectif.isActive());
		      prepare.setDate(8,objectif.getDateFin());
		      prepare.setInt(9, objectif.getIdDomaine());
		      prepare.execute();
		      String query2= "SELECT id_objectif FROM objectif ORDER BY id_objectif DESC LIMIT 1";
		      ResultSet result =this.connect.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY).executeQuery(query2);
		      if(result.first()){
					objectifOUT.setIdObjectif(result.getInt("id_objectif"));
		      }
			}catch (SQLException e){
				e.printStackTrace();
			}
		return objectifOUT;
	}

	@Override
	public boolean delete(Objectif objectif) {
		try {
			String query ="DELETE FROM objectif WHERE id_objectif = ?";
		      PreparedStatement prepare = connect.prepareStatement(query);
		      prepare.setInt(1, objectif.getIdObjectif());
		      prepare.execute();
			}catch (SQLException e){
				e.printStackTrace();
			}
		return false;
	}

	@Override
	public boolean update(Objectif objectif) {
		try {
		      String query ="UPDATE objectif SET nom = ?, numero = ?, niveau1 = ?, niveau2 = ?, niveau3 = ?, niveau4 = ?, active = ?, datefin = ?, id_domaine = ? WHERE id_objectif = ?";
		      PreparedStatement prepare = connect.prepareStatement(query);
		      
		      prepare.setString(1, objectif.getNom());
		      prepare.setInt(2, objectif.getNumero());
		      prepare.setString(3, objectif.getNiveau1());
		      prepare.setString(4, objectif.getNiveau2());
		      prepare.setString(5, objectif.getNiveau3());
		      prepare.setString(6, objectif.getNiveau4());
		      prepare.setBoolean(7, objectif.isActive());
		      prepare.setDate(8,objectif.getDateFin());
		      prepare.setInt(9, objectif.getIdDomaine());
		      prepare.setInt(10,objectif.getIdObjectif());
		  
		      prepare.execute();
			}catch (SQLException e){
				e.printStackTrace();
			}
		return false;
	}

	@Override
	public Objectif find(int id) {
		Objectif objectif = new Objectif();
		try {
		ResultSet result =this.connect.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM objectif WHERE id_objectif ="+ id);
		if(result.first()){
		objectif = new Objectif(
					id,
					result.getString("nom"),
					result.getInt("numero"),
					result.getString("niveau1"),
					result.getString("niveau2"),
					result.getString("niveau3"),
					result.getString("niveau4"),
					result.getBoolean("active"),
					result.getDate("dateFin"),
					result.getInt("id_domaine")
					);
		}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return objectif;
	}
	@Override
	public void getAll(){
		ArrayList<Objectif> arr = new ArrayList<>();
		try {
			ResultSet result =this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT objectif.*, domaine.numero FROM objectif INNER JOIN domaine ON objectif.id_domaine = domaine.id_domaine ORDER BY domaine.numero, objectif.numero");
			int idObjectif, numero, iddomaine;
			String nom, niveau1, niveau2, niveau3, niveau4;
			boolean active;
			Date dateFin;
			
			for (Domaine domaine : Dat.arrayDomaines) {
				domaine.getObjectifs().clear();
			}
			
			while(result.next()){
       		 idObjectif = (int) result.getObject("id_objectif");
       		 nom = (String) result.getObject("nom");
       		 numero = (int) result.getObject("objectif.numero");
       		niveau1 = (String) result.getObject("niveau1");
       		niveau2 = (String) result.getObject("niveau2");
       		niveau3 = (String) result.getObject("niveau3");
       		niveau4 = (String) result.getObject("niveau4");
       		active = (boolean) result.getObject("active");
       		dateFin = (Date) result.getObject("datefin");
       		iddomaine = (int) result.getObject("id_domaine");
       		Objectif objectif = new Objectif(idObjectif, nom, numero, niveau1, niveau2, niveau3, niveau4, active, dateFin, iddomaine);
       		 arr.add(objectif);
       		 Domaine.get(result.getInt("id_domaine")).getObjectifs().add(objectif);
       	 }
			
			}catch (SQLException e){
				e.printStackTrace();
			}
		Dat.arrayObjectifs = arr;
	}
	
	public ArrayList<Objectif> getAllActive(){
		ArrayList<Objectif> arr = new ArrayList<>();
		try {
			ResultSet result =this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT objectif.*, objectif.numero, domaine.numero FROM objectif INNER JOIN domaine ON objectif.id_domaine = domaine.id_domaine WHERE active = TRUE ORDER BY domaine.numero, objectif.numero");
			int idObjectif, numero, iddomaine;
			String nom, niveau1, niveau2, niveau3, niveau4;
			boolean active;
			Date dateFin;
			while(result.next()){
       		 idObjectif = (int) result.getObject("id_objectif");
       		 nom = (String) result.getObject("nom");
       		 numero = (int) result.getObject("objectif.numero");
       		niveau1 = (String) result.getObject("niveau1");
       		niveau2 = (String) result.getObject("niveau2");
       		niveau3 = (String) result.getObject("niveau3");
       		niveau4 = (String) result.getObject("niveau4");
       		active = (boolean) result.getObject("active");
       		dateFin = (Date) result.getObject("datefin");
       		iddomaine = (int) result.getObject("id_domaine");

       		 arr.add(new Objectif(idObjectif, nom, numero, niveau1, niveau2, niveau3, niveau4, active, dateFin, iddomaine));    		 
       	 }
			
			}catch (SQLException e){
				e.printStackTrace();
			}
		return arr;
	}
	
	

	public ArrayList<Objectif> get(Eval eval) {
		ArrayList<Objectif> arr = new ArrayList<>();
		try {
			String str = "SELECT DISTINCT objectif.*, domaine.numero FROM objectif"
					+ " INNER JOIN domaine "
					+ "ON objectif.id_domaine = domaine.id_domaine "
					+ "INNER JOIN note "
					+ "ON objectif.id_objectif = note.id_objectif "
					+ "INNER JOIN eval "
					+ "ON note.id_eval = eval.id_eval "
					+ "WHERE eval.id_eval = "+ eval.getIdEval()+" ORDER BY domaine.numero, objectif.numero ;";
			ResultSet result =this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(str);
			int idObjectif, numero, iddomaine;
			String nom, niveau1, niveau2, niveau3, niveau4;
			boolean active;
			Date dateFin;
			while(result.next()){
       		 idObjectif = (int) result.getObject("id_objectif");
       		 nom = (String) result.getObject("nom");
       		 numero = (int) result.getObject("objectif.numero");
       		niveau1 = (String) result.getObject("niveau1");
       		niveau2 = (String) result.getObject("niveau2");
       		niveau3 = (String) result.getObject("niveau3");
       		niveau4 = (String) result.getObject("niveau4");
       		active = (boolean) result.getObject("active");
       		dateFin = (Date) result.getObject("datefin");
       		iddomaine = (int) result.getObject("id_domaine");

       		 arr.add(new Objectif(idObjectif, nom, numero, niveau1, niveau2, niveau3, niveau4, active, dateFin, iddomaine));  
       	 }
			
			}catch (SQLException e){
				e.printStackTrace();
			}
		return arr;
	}
	
	public ArrayList<Objectif> getByEvalAndMat(int idEval, int idMat) {
		ArrayList<Objectif> arr = new ArrayList<>();
		try {
			String str = "SELECT DISTINCT objectif.*, domaine.numero FROM objectif INNER JOIN domaine ON objectif.id_domaine = domaine.id_domaine INNERJOIN matobj ON objectif.id_objectif = matobj.id_objectif INNER JOIN note ON objectif.id_objectif = note.id_objectif INNER JOIN eval ON note.id_eval = eval.id_eval WHERE eval.id_eval = "+ idEval +" AND matobj.id_matiere = "+ idMat + " ORDER BY domaine.numero, objectif.numero ;";
			ResultSet result =this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(str);
			int idObjectif, numero, iddomaine;
			String nom, niveau1, niveau2, niveau3, niveau4;
			boolean active;
			Date dateFin;
			while(result.next()){
       		 idObjectif = (int) result.getObject("id_objectif");
       		 nom = (String) result.getObject("nom");
       		 numero = (int) result.getObject("objectif.numero");
       		niveau1 = (String) result.getObject("niveau1");
       		niveau2 = (String) result.getObject("niveau2");
       		niveau3 = (String) result.getObject("niveau3");
       		niveau4 = (String) result.getObject("niveau4");
       		active = (boolean) result.getObject("active");
       		dateFin = (Date) result.getObject("datefin");
       		iddomaine = (int) result.getObject("id_domaine");

       		 arr.add(new Objectif(idObjectif, nom, numero, niveau1, niveau2, niveau3, niveau4, active, dateFin, iddomaine));  
       	 }
			
			}catch (SQLException e){
				e.printStackTrace();
			}
		return arr;
	}
	
	public ArrayList<Objectif> getByMat(int idMat) {
		ArrayList<Objectif> arr = new ArrayList<>();
		try {
			String str = "SELECT DISTINCT objectif.*, domaine.numero FROM objectif INNER JOIN domaine ON objectif.id_domaine = domaine.id_domaine INNER JOIN matobj ON objectif.id_objectif = matobj.id_objectif WHERE matobj.id_matiere = "+ idMat +" ORDER BY domaine.numero, objectif.numero ;";
			ResultSet result =this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(str);
			int idObjectif, numero, iddomaine;
			String nom, niveau1, niveau2, niveau3, niveau4;
			boolean active;
			Date dateFin;
			while(result.next()){
       		 idObjectif = (int) result.getObject("id_objectif");
       		 nom = (String) result.getObject("nom");
       		 numero = (int) result.getObject("objectif.numero");
       		niveau1 = (String) result.getObject("niveau1");
       		niveau2 = (String) result.getObject("niveau2");
       		niveau3 = (String) result.getObject("niveau3");
       		niveau4 = (String) result.getObject("niveau4");
       		active = (boolean) result.getObject("active");
       		dateFin = (Date) result.getObject("datefin");
       		iddomaine = (int) result.getObject("id_domaine");

       		 arr.add(new Objectif(idObjectif, nom, numero, niveau1, niveau2, niveau3, niveau4, active, dateFin, iddomaine)); 
       	 }
			
			}catch (SQLException e){
				e.printStackTrace();
			}
		return arr;
	}
	
	public ArrayList<Objectif> getByMatAndComp(int idMat, int idComp) {
		ArrayList<Objectif> arr = new ArrayList<>();
		try {
			String str = "SELECT DISTINCT objectif.*, domaine.numero FROM objectif INNER JOIN domaine ON objectif.id_domaine = domaine.id_domaine INNER JOIN matobj ON objectif.id_objectif = matobj.id_objectif WHERE matobj.id_matiere = "+ idMat +" AND domaine.id_domaine = "+ idComp+" ORDER BY domaine.numero, objectif.numero ;";
			ResultSet result =this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(str);
			int idObjectif, numero, iddomaine;
			String nom, niveau1, niveau2, niveau3, niveau4;
			boolean active;
			Date dateFin;
			while(result.next()){
       		 idObjectif = (int) result.getObject("id_objectif");
       		 nom = (String) result.getObject("nom");
       		 numero = (int) result.getObject("objectif.numero");
       		niveau1 = (String) result.getObject("niveau1");
       		niveau2 = (String) result.getObject("niveau2");
       		niveau3 = (String) result.getObject("niveau3");
       		niveau4 = (String) result.getObject("niveau4");
       		active = (boolean) result.getObject("active");
       		dateFin = (Date) result.getObject("datefin");
       		iddomaine = (int) result.getObject("id_domaine");

       		 arr.add(new Objectif(idObjectif, nom, numero, niveau1, niveau2, niveau3, niveau4, active, dateFin, iddomaine)); 
       	 }
			
			}catch (SQLException e){
				e.printStackTrace();
			}
		return arr;
	}

	public ArrayList<Objectif> getByComp(int idComp) {
		ArrayList<Objectif> arr = new ArrayList<>();
		try {
			ResultSet result =this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM objectif WHERE id_domaine = " + idComp + " ORDER BY numero ");
			int idObjectif, numero, iddomaine;
			String nom, niveau1, niveau2, niveau3, niveau4;
			boolean active;
			Date dateFin;
			while(result.next()){
       		 idObjectif = (int) result.getObject("id_objectif");
       		 nom = (String) result.getObject("nom");
       		 numero = (int) result.getObject("objectif.numero");
       		niveau1 = (String) result.getObject("niveau1");
       		niveau2 = (String) result.getObject("niveau2");
       		niveau3 = (String) result.getObject("niveau3");
       		niveau4 = (String) result.getObject("niveau4");
       		active = (boolean) result.getObject("active");
       		dateFin = (Date) result.getObject("datefin");
       		iddomaine = (int) result.getObject("id_domaine");

       		 arr.add(new Objectif(idObjectif, nom, numero, niveau1, niveau2, niveau3, niveau4, active, dateFin, iddomaine)); 
       	 }
			
			}catch (SQLException e){
				e.printStackTrace();
			}
		return arr;
	}
	
	public ArrayList<Objectif> getByCompActive(int idComp) {
		ArrayList<Objectif> arr = new ArrayList<>();
		try {
			ResultSet result =this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM objectif WHERE active = TRUE AND id_domaine = " + idComp + " ORDER BY numero" );
			int idObjectif, numero, idDomaine;
			String nom, niveau1, niveau2, niveau3, niveau4;
			boolean active;
			Date dateFin;
			while(result.next()){
       		idObjectif = (int) result.getObject("id_objectif");
       		nom = (String) result.getObject("nom");
       		numero = (int) result.getObject("objectif.numero");
       		niveau1 = (String) result.getObject("niveau1");
       		niveau2 = (String) result.getObject("niveau2");
       		niveau3 = (String) result.getObject("niveau3");
       		niveau4 = (String) result.getObject("niveau4");
       		active = (boolean) result.getObject("active");
       		dateFin = (Date) result.getObject("datefin");
       		idDomaine = (int) result.getObject("id_domaine");
       		Objectif objectif = new Objectif(idObjectif, nom, numero, niveau1, niveau2, niveau3, niveau4, active, dateFin, idDomaine);
       		arr.add(objectif);
       	 }
			
			}catch (SQLException e){
				e.printStackTrace();
			}
		return arr;
	}

	public void deleteByComp(int idDomaine) {
		try {
			String query ="DELETE FROM objectif WHERE id_domaine = " + idDomaine;
		      PreparedStatement prepare = connect.prepareStatement(query);
		      prepare.execute();
			}catch (SQLException e){
				e.printStackTrace();
			}
	}
	
	public boolean activate(Objectif objectif) {
		ArrayList<Objectif> arr = getByCompActive(objectif.getIdDomaine());
		ArrayList<Objectif> arr2 = getByComp(objectif.getIdDomaine());
		int nb = arr.size() + 1 ;
		for (Objectif obj: arr2) {
			if (!obj.isActive() && obj.getNumero()<objectif.getNumero()){
				int num = obj.getNumero()+1;
				obj.setNumero(num);
				update(obj);
			}
		}
		try {
		      String query ="UPDATE objectif SET numero = " + nb  +" , active = TRUE WHERE id_objectif = " + objectif.getIdObjectif() ;
		      PreparedStatement prepare = connect.prepareStatement(query); 
		      prepare.execute();
			}catch (SQLException e){
				e.printStackTrace();
			}
		Dat.objectifDAO.getAll();
		return false;	
	}
	
	public boolean desactivate(Objectif objectif) {
		ArrayList<Objectif> arr = getByComp(objectif.getIdDomaine());
		int nb = arr.size() ;
		
		for (Objectif obj: arr) {
			if (obj.getNumero()>objectif.getNumero()){
				obj.setNumero(obj.getNumero()-1);
				update(obj);
			}
		}
		try {
		      String query ="UPDATE objectif SET numero = " +nb+ " , active = FALSE WHERE id_objectif = " + objectif.getIdObjectif() ;
		      PreparedStatement prepare = connect.prepareStatement(query); 
		      prepare.execute();
			}catch (SQLException e){
				e.printStackTrace();
			}
		Dat.objectifDAO.getAll();
		return false;
	}
}