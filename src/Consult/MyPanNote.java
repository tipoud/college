package Consult;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import Main.Dat;
import Main.NoteToEval;
import tablesBDD.Eval;
import tablesBDD.Matiere;
import tablesBDD.Note;


@SuppressWarnings("serial")
public class MyPanNote extends JButton{
	private Note note;
	private Matiere matiere;

	public MyPanNote(Note note) {
		super();
		this.note = note;
		this.matiere = Matiere.get(Eval.get(note.getIdEval()).getIdMatiere());
		this.setPreferredSize(new Dimension(28, 28));
		this.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent event) {
		new NoteToEval(((MyPanNote)event.getSource()).note);
	}
});
		this.setMargin(new Insets(0, 0, 0, 0));
		this.setBackground(Color.WHITE);
		this.setVerticalTextPosition(SwingConstants.CENTER); 
		this.setHorizontalTextPosition(SwingConstants.CENTER); 
	
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				setText("");
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				setText(matiere.getAbr());

			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});		
		
		if (note.isAbsent()){
			this.setIcon(Dat.absent);
		} else { 
			switch (note.getVal()) {

		case 1 : 
			this.setIcon(Dat.cam1);
		break;
		case 2 : 
			this.setIcon(Dat.cam2);
		break;
		case 3 : 
			this.setIcon(Dat.cam3);
		break;
		case 4 : 
			this.setIcon(Dat.cam4);
		break;		
		default: 
			break;

          }
		}


		this.setFont(new Font("Arial",Font.BOLD, 11));

	}
	
	public MyPanNote(Integer val) {
		super();
		this.setPreferredSize(new Dimension(28, 28));

		this.setMargin(new Insets(0, 0, 0, 0));
		this.setBackground(Color.WHITE);
		this.setVerticalTextPosition(SwingConstants.CENTER); 
		this.setHorizontalTextPosition(SwingConstants.CENTER); 	

			switch (val) {

		case 1 : 
			this.setIcon(Dat.cam1);
		break;
		case 2 : 
			this.setIcon(Dat.cam2);
		break;
		case 3 : 
			this.setIcon(Dat.cam3);
		break;
		case 4 : 
			this.setIcon(Dat.cam4);
		break;		
		default: 
			break;

          }


		this.setFont(new Font("Arial",Font.BOLD, 11));

	}


}
