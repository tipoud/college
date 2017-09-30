package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Main.Dat;
import tablesBDD.MatObj;

public class MatObjDAO extends DAO<MatObj> {
	public MatObjDAO(Connection conn){
		super(conn);
	}

	@Override
	public MatObj create(MatObj matObj) {
		MatObj MatObjOUT = matObj;
		try {
		      String query =" INSERT INTO matobj (id_matiere, id_objectif) Values (?,?)";
		      PreparedStatement prepare = connect.prepareStatement(query);
		      prepare.setInt(1, matObj.getIdMat());
		      prepare.setInt(2,matObj.getIdObj());
		      prepare.execute();
		      
		      String query2= "SELECT id_matobj FROM matobj ORDER BY id_MatObj DESC LIMIT 1";
		      ResultSet result =this.connect.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY).executeQuery(query2);
		      if(result.first()){
					MatObjOUT.setIdMatObj(result.getInt("id_matobj"));
		      }
			}catch (SQLException e){
				e.printStackTrace();
			}
		return MatObjOUT;
	}

	@Override
	public boolean delete(MatObj matObj) {
		try {
			String query ="DELETE FROM matobj WHERE id_matobj = ?";
		      PreparedStatement prepare = connect.prepareStatement(query);
		      prepare.setInt(1, matObj.getIdMatObj());
		      prepare.execute();
			}catch (SQLException e){
				e.printStackTrace();
			}
		return false;
	}

	@Override
	public boolean update(MatObj matObj) {
		try {
		      String query ="UPDATE matobj SET id_matiere ?, id_objectif = ? WHERE id_matobj = ?";
		      PreparedStatement prepare = connect.prepareStatement(query);
		      
		      prepare.setInt(1, matObj.getIdMat());
		      prepare.setInt(2,matObj.getIdObj());
		      prepare.setInt(3,matObj.getIdMatObj());
		      prepare.execute();
			}catch (SQLException e){
				e.printStackTrace();
			}
		return false;
	}

	@Override
	public MatObj find(int id) {
		MatObj MatObj = new MatObj();
		try {
		ResultSet result =this.connect.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM matobj WHERE id_matobj ="+ id);
		if(result.first()){
		MatObj = new MatObj(
					id,
					result.getInt("id_matiere"),
					result.getInt("id_objectif")
					);
		}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return MatObj;
	}
	@Override
	public void getAll(){
		ArrayList<MatObj> arr = new ArrayList<>();
		try {
			ResultSet result =this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM matobj");
			MatObj matObj;
			while(result.next()){
				matObj = new MatObj(
						result.getInt("id_matobj"),
						result.getInt("id_matiere"),
						result.getInt("id_objectif")
						);
       		 arr.add(matObj);      		 
       	 }
			
			}catch (SQLException e){
				e.printStackTrace();
			}
		Dat.arrayMatObj = arr;
	}
	
	public MatObj findOne(int idMat, int idObj) {
		MatObj MatObj = new MatObj();
		try {
		ResultSet result =this.connect.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM matobj WHERE id_matiere ="+ idMat + " AND id_objectif = "+idObj);
		if(result.first()){
		MatObj = new MatObj(
					result.getInt("id_matobj"),
					idMat,
					idObj
					);
		}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return MatObj;
	}
	
	public void deleteByObj(int idObj) {
		try {
			String query ="DELETE FROM matobj WHERE id_objectif = "+ idObj;
		      PreparedStatement prepare = connect.prepareStatement(query);
		      prepare.execute();
			}catch (SQLException e){
				e.printStackTrace();
			}// TODO Auto-generated method stub
		
	}
	
	public void deleteByComp(int idComp) {
		try {
			String query ="DELETE FROM matobj WHERE id_objectif IN (SELECT id_objectif FROM objectif WHERE id_competence = " + idComp + ")";
		      PreparedStatement prepare = connect.prepareStatement(query);
		      prepare.execute();
			}catch (SQLException e){
				e.printStackTrace();
			}// TODO Auto-generated method stub
		
	}
	
}