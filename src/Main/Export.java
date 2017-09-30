package Main;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Accueil.Accueil;
import Administration.VersExcel;
import tablesBDD.Classe;
import tablesBDD.Enseignant;

@SuppressWarnings("serial")
public class Export extends JPanel {
	Enseignant enseignant = Dat.enseignant;
	JPanel global = new JPanel(), bottom = new JPanel();
	JButton btn = new JButton("Envoyer une sauvegarde"), btn2 = new JButton("Exporter sous excel");
	Classe classe;
	JComboBox<Classe> jcomboClasse = new JComboBox<>();
	
 	public Export(){
 		super();
	    this.setLayout(new BorderLayout());
		this.add(global, BorderLayout.NORTH);
		this.add(bottom, BorderLayout.SOUTH);
		bottom.add(btn);
		for (Classe classe : Dat.arrayClasses) {
			if(!classe.getEleves().isEmpty()){
				jcomboClasse.addItem(classe);
			}
		}
		JLabel lab = new JLabel("Sélectionner une classe : ");
		global.add(lab);
		global.add(jcomboClasse);
		global.add(btn2);
		
		btn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VersExcel(classe);
				
			}
		});
		classe = (Classe) jcomboClasse.getSelectedItem();
		jcomboClasse.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				Accueil.myConsultNote.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				if (event.getStateChange()==ItemEvent.SELECTED) {
					classe = (Classe) event.getItem();
				}
			}
		});
		
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
				Date date = new Date();
				String date2 = dateFormat.format(date);
				String adresse = "C:\\BackUp\\"+date2+" - college.zip";
				new ZipUtils("C:\\wamp64\\bin\\mysql\\mysql5.7.14\\data\\college",adresse);
				new EmailAttachmentSender(adresse);
				
			}
		});
		
 	}
 	

}
