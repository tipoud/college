package Administration;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Main.Dat;

@SuppressWarnings("serial")
public abstract class ClicOpen extends JPanel{
	
	protected boolean open = false;
	protected JPanel content, principal,controlNumero ;
	protected JLabel  principalLabel;
	protected JButton btn, bas, haut;
	
	
	
	public ClicOpen(){
		super();
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		content = new JPanel();
		principal = new JPanel();
		controlNumero = new JPanel();
		
		btn = new JButton();
		btn.setPreferredSize(new Dimension(400,30));
		bas = new JButton();
		haut = new JButton();
		
		principalLabel = new JLabel();
		
		this.add(content,BorderLayout.CENTER);
		
		content.setLayout(new BorderLayout());
		principal.setLayout(new BorderLayout());
		
		haut.setIcon(Dat.flecheHaut);
		haut.setMargin(new Insets(0, 0, 0, 0));
		bas.setIcon(Dat.flecheBas);
		bas.setMargin(new Insets(0, 0, 0, 0));
		
		btn.add(principalLabel);
		
		controlNumero.setLayout(new BorderLayout());
		controlNumero.add(haut,BorderLayout.NORTH);
		controlNumero.add(bas,BorderLayout.SOUTH);
			
		principal.add(controlNumero,BorderLayout.WEST);
		principal.add(btn,BorderLayout.CENTER);
		content.add(principal,BorderLayout.NORTH);

	}



}
