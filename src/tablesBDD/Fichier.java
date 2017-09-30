package tablesBDD;

public class Fichier {

	private int idFichier;
	private String nom ;
	private String adresse;
	private String type;
	
	public Fichier(){
		idFichier = 0 ;
		nom = "";
		adresse = "";
		type = "";
	}
	
	public Fichier(int idFichier, String nom, String type, String adresse) {
		this.idFichier = idFichier;
		this.nom = nom;
		this.adresse = adresse;
		this.type = type;
	}

	public Fichier(String nom, String type, String adresse) {
		this.nom = nom;
		this.adresse = adresse;
		this.type = type;
	}

	public int getIdFichier() {
		return idFichier;
	}

	public void setIdFichier(int idFichier) {
		this.idFichier = idFichier;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adresse == null) ? 0 : adresse.hashCode());
		result = prime * result + idFichier;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fichier other = (Fichier) obj;
		if (adresse == null) {
			if (other.adresse != null)
				return false;
		} else if (!adresse.equals(other.adresse))
			return false;
		if (idFichier != other.idFichier)
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
}
