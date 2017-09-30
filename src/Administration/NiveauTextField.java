package Administration;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JTextField;

import DAO.DAO;
import Main.Dat;
import tablesBDD.Objectif;

@SuppressWarnings("serial")
public class NiveauTextField extends JTextField {
	DAO<Objectif> objectifDAO = Dat.objectifDAO;
	private Objectif obj;
	Method maMethodeSet;
	
	NiveauTextField(Objectif objectif, int i){
		super();
		this.obj = objectif;
			String text = getText();
			String get = "getNiveau"+(i+1);
			String set = "setNiveau"+(i+1);
			try {
				Method maMethodeGet = obj.getClass().getDeclaredMethod(get, null);
				text = (String) maMethodeGet.invoke(obj);
				maMethodeSet = obj.getClass().getDeclaredMethod(set, String.class);

			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			this.setText(text);
			
			this.addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent e) {
				try {
					maMethodeSet.invoke(obj,getText());}
				catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

				}
				objectifDAO.update(obj);
				}
				
				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
					setBackground(Color.WHITE);
				}
			});
		
		}
	}
	

