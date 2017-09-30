package tablesBDD;

import Main.Dat;

public class MatObj {

	private int idMatObj;
	private int idMat;
	private int idObj;
	
	public MatObj(){
		super();
		idMatObj = 0;
	}
	
	public MatObj(int idMat, int idObj) {
		super();
		this.idMat = idMat;
		this.idObj = idObj;
	}
	
	public MatObj(int idMatObj, int idMat, int idObj) {
		this(idMat,idObj);
		this.idMatObj = idMatObj;

	}

	public int getIdMatObj() {
		return idMatObj;
	}

	public void setIdMatObj(int idMatObj) {
		this.idMatObj = idMatObj;
	}

	public int getIdMat() {
		return idMat;
	}

	public void setIdMat(int idMat) {
		this.idMat = idMat;
	}

	public int getIdObj() {
		return idObj;
	}

	public void setIdObj(int idObj) {
		this.idObj = idObj;
	}

	//Méthodes persos
	public static MatObj get(int idMatObj){
		for (MatObj matObj : Dat.arrayMatObj) {
			if (matObj.getIdMatObj()==idMatObj){
				return matObj;
			}
		}
		return null;
	}
	
	public static MatObj get(Matiere matiere, Objectif objectif){
		for (MatObj matObj : Dat.arrayMatObj) {
			if (matObj.getIdMat()==matiere.getIdMatiere() && matObj.getIdObj() == objectif.getIdObjectif()){
				return matObj;
			}
		}
		return null;
	}

	public static boolean get(Objectif objectif) {
		for (MatObj matObj : Dat.arrayMatObj) {
			if(matObj.getIdObj()==objectif.getIdObjectif()){
				return true;
			}
		}// TODO Auto-generated method stub
		return false;
	}
}
