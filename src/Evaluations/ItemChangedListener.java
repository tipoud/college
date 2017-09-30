package Evaluations;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import DAO.DAO;
import Main.Dat;
import tablesBDD.Note;

class ItemChangedListener implements ItemListener{

    @Override
    public void itemStateChanged(ItemEvent event) {

    	if (event.getStateChange()==ItemEvent.SELECTED) {
    	((MyCaseNote) event.getSource()).getNote().setAbsent(false);
    	switch ((String) event.getItem()) {
		case "1" : ((MyCaseNote) event.getSource()).setBackground(Color.RED);
		((MyCaseNote) event.getSource()).getNote().setVal(1);
		break;
		case "2" : ((MyCaseNote) event.getSource()).setBackground(Color.ORANGE);
		((MyCaseNote) event.getSource()).getNote().setVal(2);
		break;
		case "3" : ((MyCaseNote) event.getSource()).setBackground(Color.YELLOW);
		((MyCaseNote) event.getSource()).getNote().setVal(3);
		break;
		case "4" : ((MyCaseNote) event.getSource()).setBackground(Color.GREEN);
		((MyCaseNote) event.getSource()).getNote().setVal(4);
		break;
		case "Absent" : ((MyCaseNote) event.getSource()).setBackground(Color.WHITE);
		((MyCaseNote) event.getSource()).getNote().setVal(0);
		((MyCaseNote) event.getSource()).getNote().setAbsent(true);
		break;
		case "NE" : ((MyCaseNote) event.getSource()).setBackground(Color.WHITE);
		((MyCaseNote) event.getSource()).getNote().setVal(0);
		break;

		default:
			break;

          }

       }
    }
}