package Accueil;

import Main.Dat;

import javax.swing.*;
import java.awt.*;

class Bonjour extends JPanel {

 	Bonjour(){
 		super();
	    this.setLayout(new BorderLayout());
		this.add(new JLabel("Bonjour "+ Dat.enseignant.toString()), BorderLayout.NORTH);
 	}
 	

}
