package Main;

import java.awt.Dimension;

import javax.swing.JComboBox;

import tablesBDD.Objectif;

public class ComboObjectif extends JComboBox<Objectif> {

	private static final long serialVersionUID = 1L;

	public ComboObjectif(){
		super();
		this.setPreferredSize(new Dimension(80, 20));
		for (tablesBDD.Objectif objectif : Dat.arrayObjectifs ) {
			this.addItem(objectif);
		}
		addPopupMenuListener( new BoundsPopupMenuListener(true, false));
	}

	
	public ComboObjectif(Objectif objectif){
		this();
        for (int i = 0; i < getItemCount(); i++) {
            if (getItemAt(i).getIdObjectif() == objectif.getIdObjectif()){
                setSelectedIndex(i);
                return;
            }
        }
	}
}
