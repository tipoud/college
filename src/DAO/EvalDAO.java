package DAO;
	import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Main.Dat;
import tablesBDD.Eval;

	public class EvalDAO extends DAO<Eval> {
		public EvalDAO(Connection conn){
			
			super(conn);
		}

		@Override
		public Eval create (Eval eval) {
			Eval evalOUT = eval;
			try {
			      String query =" INSERT INTO eval (id_enseignant, date, type, id_classe, id_matiere, nom, description, id_fichier) Values (?,?,?,?,?,?,?,?)";
			      PreparedStatement prepare = connect.prepareStatement(query);
			      prepare.setInt(1, eval.getIdEnseignant());
			      prepare.setDate(2,eval.getDate());
			      prepare.setString(3, eval.getType());
			      prepare.setInt(4,eval.getIdClasse());
			      prepare.setInt(5,eval.getIdMatiere());
			      prepare.setString(6, eval.getNom());
			      prepare.setString(7, eval.getDescription());
			      prepare.setInt(8,eval.getIdFichier());
			      prepare.execute();
			      String query2= "SELECT id_eval FROM eval ORDER BY id_eval DESC LIMIT 1";
			      ResultSet result =this.connect.createStatement(
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY).executeQuery(query2);
			      if(result.first()){
						evalOUT.setIdEval(result.getInt("id_eval"));
			      }
			      
				}catch (SQLException e){
					e.printStackTrace();
				}
			return evalOUT;
		}

		@Override
		public boolean delete(Eval eval) {
			try {
				String query ="DELETE FROM eval WHERE id_eval = ?";
			      PreparedStatement prepare = connect.prepareStatement(query);
			      prepare.setInt(1, eval.getIdEval());
			      prepare.execute();
				}catch (SQLException e){
					e.printStackTrace();
				}
			return false;
		}

		@Override
		public boolean update(Eval eval) {
			try {
			      String query ="UPDATE eval SET id_enseignant = ?, date = ?, type = ?, id_classe = ?, id_matiere = ? , nom = ?, description = ?, id_fichier = ? WHERE id_eval = ?";
			      PreparedStatement prepare = connect.prepareStatement(query);
			      prepare.setInt(1, eval.getIdEnseignant());
			      prepare.setDate(2,eval.getDate());
			      prepare.setString(3, eval.getType());
			      prepare.setInt(4,eval.getIdClasse());
			      prepare.setInt(5,eval.getIdMatiere());
			      prepare.setString(6, eval.getNom());
			      prepare.setString(7, eval.getDescription());
			      prepare.setInt(8, eval.getIdFichier());
			      prepare.setInt(9, eval.getIdEval());
			      
			      prepare.execute();
				}catch (SQLException e){
					e.printStackTrace();
				}

			return false;
		}

		@Override
		public Eval find(int id) {
			Eval eval = new Eval();
			try {
			ResultSet result =this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM eval WHERE id_eval ="+ id);
			if(result.first()){
			eval = new Eval(
						id,
						result.getInt("id_enseignant"),
						result.getDate("date"),
						result.getString("type"),
						result.getInt("id_classe"),
						result.getInt("id_matiere"),
						result.getString("nom"),
						result.getString("description"),
						result.getInt("id_fichier")
						);
			}
			}catch (SQLException e){
				e.printStackTrace();
			}
			return eval;
		}
		@Override
		public void getAll(){
			ArrayList<Eval> arr = new ArrayList<>();
			try {
				ResultSet result =this.connect.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM eval ORDER BY date DESC");
				Eval eval;
				while(result.next()){
					eval = new Eval(
							result.getInt("id_eval"),
							result.getInt("id_enseignant"),
							result.getDate("date"),
							result.getString("type"),
							result.getInt("id_classe"),
							result.getInt("id_matiere"),
							result.getString("nom"),
							result.getString("description"),
							result.getInt("id_fichier")
							);
	       		 arr.add(eval);      		 
	       	 }
				
				}catch (SQLException e){
					e.printStackTrace();
				}
			Dat.arrayEvals = arr;
		}

		public ArrayList<Eval> getByEnseignant(int idEnseignant, int idClasse) {
				ArrayList<Eval> arr = new ArrayList<>();
				try {
					ResultSet result =this.connect.createStatement(
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM eval WHERE id_enseignant = " + idEnseignant + " AND id_classe = " + idClasse + " ORDER BY date");
					int id_eval;
					while(result.next()){
		       		 id_eval = (int) result.getObject("id_eval");
		       		 arr.add(this.find(id_eval));      		 
		       	 }
					
					}catch (SQLException e){
						e.printStackTrace();
					}
				return arr;
		}
	}
