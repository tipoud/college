package DAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Main.Dat;
import tablesBDD.Classe;
import tablesBDD.Eleve;
import tablesBDD.Eval;

public class EleveDAO extends DAO<Eleve> {
	public EleveDAO(Connection conn){
		super(conn);
	}

	@Override
	public Eleve create(Eleve eleve) {
		Eleve eleveOUT = eleve ;
		try {
		      String query =" INSERT INTO eleve (nom,prenom,datenaissance,id_classe,mail1,mail2,pseudo,mdp) Values (?,?,?,?,?,?,?,?)";
		      PreparedStatement prepare = connect.prepareStatement(query);
		      
		      prepare.setString(1,eleve.getNom());
		      prepare.setString(2, eleve.getPrenom());
		      prepare.setDate(3, eleve.getDateNaissance());
		      prepare.setInt(4, eleve.getIdClasse());
		      prepare.setString(5, eleve.getMail1());
		      prepare.setString(6, eleve.getMail2());
		      prepare.setString(7, eleve.getPseudo());
		      prepare.setString(8, eleve.getMdp());
		      prepare.execute();
		      String query2= "SELECT id_eleve FROM eleve ORDER BY id_eleve DESC LIMIT 1";
		      ResultSet result =this.connect.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY).executeQuery(query2);
		      if(result.first()){
					eleveOUT.setIdEleve(result.getInt("id_eleve"));
		      }
			}catch (SQLException e){
				e.printStackTrace();
			}
		return eleveOUT;
	}

	@Override
	public boolean delete(Eleve eleve) {
		try {
			String query ="DELETE FROM eleve WHERE id_eleve = ?";
		      PreparedStatement prepare = connect.prepareStatement(query);
		      prepare.setInt(1, eleve.getIdEleve());
		      prepare.execute();
			}catch (SQLException e){
				e.printStackTrace();
			}
		return false;
	}

	@Override
	public boolean update(Eleve eleve) {
		try {
		      String query ="UPDATE eleve SET nom = ?, prenom = ?,datenaissance = ?,id_classe = ?,mail1 = ?,mail2 = ?, pseudo = ? , mdp = ? WHERE id_eleve = ?";
		      PreparedStatement prepare = connect.prepareStatement(query);
		      
		      prepare.setString(1,eleve.getNom());
		      prepare.setString(2, eleve.getPrenom());
		      prepare.setDate(3, eleve.getDateNaissance());
		      prepare.setInt(4, eleve.getIdClasse());
		      prepare.setString(5, eleve.getMail1());
		      prepare.setString(6, eleve.getMail2());
		      prepare.setString(7, eleve.getPseudo());
		      prepare.setString(8, eleve.getMdp());
		      prepare.setInt(9,eleve.getIdEleve());
		      prepare.execute();
			}catch (SQLException e){
				e.printStackTrace();
			}

		return false;
	}

	@Override
	public Eleve find(int id) {
		Eleve eleve = new Eleve();
		try {
		ResultSet result =this.connect.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM eleve WHERE id_eleve ="+ id);
		if(result.first()){
			eleve = new Eleve(
					id,
					result.getInt("id_classe"),
					result.getString("nom"),
					result.getString("prenom"),
					result.getDate("datenaissance"),
					result.getString("mail1"),
					result.getString("mail2"),
					result.getString("pseudo"),
					result.getString("mdp")
					);
		}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return eleve;
	}
	@Override
	public void getAll(){
		ArrayList<Eleve> arr = new ArrayList<>();
		try {
			
			ResultSet result =this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM eleve ORDER BY nom, prenom");
			
			int idEleve, idClasse;
			String nom, prenom, mail1, mail2,pseudo,mdp;
			Date dateNaissance;
			
			for (Classe classe : Dat.arrayClasses) {
				classe.getEleves().clear();
			}
			
			while(result.next()){
       		idEleve = (int) result.getObject("id_eleve");
       		nom = (String) result.getObject("nom");
       		prenom = (String) result.getObject("prenom");
       		dateNaissance = (Date) result.getObject("datenaissance");
       		mail1 = (String) result.getObject("mail1");
       		mail2 = (String) result.getObject("mail2"); 
       		pseudo = (String) result.getObject("pseudo"); 
       		mdp = (String) result.getObject("mdp");
       		idClasse = (int) result.getObject("id_classe");
       		Eleve eleve = new Eleve(idEleve, idClasse, nom, prenom, dateNaissance, mail1, mail2, pseudo, mdp);
       		arr.add(eleve);
			Classe.get(result.getInt("id_classe")).getEleves().add(eleve);
       	 }
			
			}catch (SQLException e){
				e.printStackTrace();
			}
		Dat.arrayEleves = arr;
		Dat.noteDAO.getAll(); 
	}
	
	public ArrayList<Eleve> get(Eval eval){
		ArrayList<Eleve> arr = new ArrayList<>();
		try {
			ResultSet result =this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT DISTINCT eleve.* FROM eleve INNER JOIN note ON eleve.id_eleve = note.id_eleve INNER JOIN eval ON note.id_eval = eval.id_eval WHERE eval.id_eval = "+eval.getIdEval()+" ORDER BY eleve.nom, eleve.prenom ASC");
			
			int idEleve, idClasse;
			String nom, prenom, mail1, mail2,pseudo,mdp;
			Date dateNaissance;
			while(result.next()){
       		idEleve = (int) result.getObject("id_eleve");
       		nom = (String) result.getObject("nom");
       		prenom = (String) result.getObject("prenom");
       		idClasse = (int) result.getObject("id_classe");
       		dateNaissance = (Date) result.getObject("datenaissance");
       		mail1 = (String) result.getObject("mail1");
       		mail2 = (String) result.getObject("mail2"); 
       		pseudo = (String) result.getObject("pseudo"); 
       		mdp = (String) result.getObject("mdp");
       		arr.add(new Eleve(idEleve, idClasse, nom, prenom, dateNaissance, mail1, mail2, pseudo, mdp));      		 
       	 }
			
			}catch (SQLException e){
				e.printStackTrace();
			}
		return arr;
	}
}
