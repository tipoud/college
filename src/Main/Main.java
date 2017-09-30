package Main;

import Accueil.Accueil;
import tablesBDD.Enseignant;

public class Main {

	public static Progress prog;
	public static void main(String[] args) {
		prog = new Progress();
		new Dat();
		new Accueil();
		//new VersExcel(Dat.arrayClasses.get(4));
	}
}
