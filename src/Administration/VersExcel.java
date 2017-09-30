package Administration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Main.Dat;
import Main.Main;
import Main.Ouvrir;
import tablesBDD.Classe;
import tablesBDD.Domaine;
import tablesBDD.Eleve;
import tablesBDD.Matiere;
import tablesBDD.Note;
import tablesBDD.Objectif;

public class VersExcel {


	Cell cell;
	ArrayList<Matiere> arrayMatieres = new ArrayList<>();
	ArrayList<Domaine> arrayCompetences = new ArrayList<>();
	ArrayList<Objectif> arrayObjectifs = new ArrayList<>();
	int nb = 5, nbObj, nbDomaines, sh = 1, first ;
	Note[][] note;
	private Classe classe;
	XSSFWorkbook workbook;
	XSSFSheet spreadsheet, model;
	DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	Date date = new Date();
	String dateStr = dateFormat.format(date);

	public VersExcel(Classe classe){
		this.classe = classe;
		
		
		InputStream url = Main.class.getResourceAsStream("/model.xlsm");
		
		try {
			workbook = (XSSFWorkbook) WorkbookFactory.create(url);
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			javax.swing.JOptionPane.showMessageDialog(null,e.getMessage()); 

		}

		Set<Objectif> setObjectifs = new HashSet<Objectif>() ;
		Set<Domaine> setDomaines = new HashSet<Domaine>() ;
		for (Matiere mat : Dat.arrayMatieres) {
			if (!mat.getObjectifs().isEmpty()){
				arrayMatieres.add(mat);
			}		 
			setObjectifs.addAll(mat.getObjectifs());    
		}


		XSSFFont fontObj = workbook.createFont();
		fontObj.setFontHeightInPoints((short) 9);
		fontObj.setColor(HSSFColor.BLACK.index);
		fontObj.setFontName("Arial");
		
		XSSFCellStyle styleObj = workbook.createCellStyle();
		styleObj.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
		styleObj.setFillForegroundColor(HSSFColor.WHITE.index);
		//styleObj.setWrapText(true);
		styleObj.setFont(fontObj);
		
		XSSFCellStyle styleDesc = workbook.createCellStyle();
		styleDesc.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
		styleDesc.setFillForegroundColor(HSSFColor.WHITE.index);
		//styleObj.setWrapText(true);
		

		XSSFCellStyle styleObj2 = (XSSFCellStyle) styleObj.clone();
		styleObj2.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);


		XSSFFont fontNote = workbook.createFont();
		fontNote.setFontHeightInPoints((short) 11);
		fontNote.setColor(HSSFColor.BLACK.index);
		fontNote.setFontName("Arial");
		XSSFCellStyle styleNote = (XSSFCellStyle) styleObj.clone();
		XSSFCellStyle styleNote2 = (XSSFCellStyle) styleObj2.clone();
		styleNote.setFont(fontNote);
		styleNote.setAlignment(HorizontalAlignment.CENTER);
		styleNote2.setFont(fontNote);
		styleNote2.setAlignment(HorizontalAlignment.CENTER);

		XSSFFont fontDomaine = workbook.createFont();
		fontDomaine.setFontHeightInPoints((short) 12);
		fontDomaine.setColor(HSSFColor.WHITE.index);
		fontObj.setFontName("Arial");
		XSSFCellStyle styleDomaine = workbook.createCellStyle();
		styleDomaine.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styleDomaine.setFillForegroundColor(HSSFColor.DARK_BLUE.index);
		//styleDomaine.setWrapText(true);
		styleDomaine.setFont(fontDomaine);

		HashSet<Objectif> temp = new HashSet<Objectif>();
		for (Objectif objectif : setObjectifs) {
			boucleEleve:
				for (Eleve ele : classe.getEleves()) {
					for (Note note : ele.getNotes()) {
						if(note.getIdObjectif()==objectif.getIdObjectif()){
							temp.add(objectif);
							break boucleEleve;
						}
					}
				}
		}
		setObjectifs = temp;
		nbObj = setObjectifs.size();
		for (Objectif objectif : setObjectifs) {
			setDomaines.add(Domaine.get(objectif.getIdDomaine())) ;
		}
		nbDomaines = setDomaines.size(); 

		System.out.println(nbObj +" objectifs dans " + nbDomaines +" domaines");

		model = workbook.getSheetAt(0);
		model.setColumnWidth(0, 12000);

		XSSFRow row;			
		row = model.getRow(0);
	    Cell cell = row.getCell(1);
		cell.setCellValue(classe.toString());
		int rowid = 10;
		int cellid = 1;

		XSSFCellStyle styleMat = workbook.createCellStyle();
		styleMat.setAlignment(HorizontalAlignment.CENTER);
		row = model.createRow(9);
		for (Matiere mat : arrayMatieres) {
			cell = row.createCell(cellid);
			model.addMergedRegion(new CellRangeAddress(9,9,cellid,cellid+nb-1));
			cell.setCellValue(mat.getNom());
			cell.setCellStyle(styleMat);
			surround(9,9,cellid,cellid+nb-1);
			cellid=cellid+nb;
		} 

		for (Domaine domaine : Dat.arrayDomaines) {
			if (setDomaines.contains(domaine))
			{
			row = model.createRow(rowid);  
			cell = row.createCell(0);
			model.addMergedRegion(new CellRangeAddress(rowid,rowid,0,nb*arrayMatieres.size()));
			cell.setCellValue(domaine.getNumero()+". "+domaine.getNom());
			cell.setCellStyle(styleDomaine);
			surround(rowid,rowid,0,nb*arrayMatieres.size());
			rowid++;

			boolean bleu = false;
			first = rowid;
			for (Objectif obj : domaine.getObjectifs() ) {
				if(setObjectifs.contains(obj)){
					row = model.createRow(rowid++);	
					cell = row.createCell(0);
					if (bleu) {
						cell.setCellStyle(styleObj);
					}
					if (!bleu) {
						cell.setCellStyle(styleObj2);	
					}

					cell.setCellValue(obj.getNumero()+". "+obj.getNom());


					for (int i = 1; i < nb*arrayMatieres.size()+1; i++) {
						model.setColumnWidth(i, 900);
						cell = row.createCell(i);
						if (bleu) {
							cell.setCellStyle(styleNote);
						}
						if (!bleu) {
							cell.setCellStyle(styleNote2);	
						}	
					}
					bleu=!bleu;
				}
			 }
			}
			for (int i = 0; i < arrayMatieres.size(); i++) {
				surround(first,rowid-1,0,0);
				surround(first,rowid-1,1+i*5,(i+1)*5);
			}
		}
		

			
		for (int i = rowid + 1 ;  i <= rowid + 4 ; i++){
			row = model.createRow(i);
			for (int j = 0 ; j <= nb*arrayMatieres.size() ; j++){
				cell = row.createCell(j);
				cell.setCellStyle(styleDesc);
			}
		}
		model.addMergedRegion(new CellRangeAddress(rowid+1,rowid+4,0,nb*arrayMatieres.size()));
		surround(rowid+1,rowid+4,0,nb*arrayMatieres.size());
		row = model.createRow(rowid+5);
		cell = row.createCell(0);
		cell.setCellValue(dateStr);
		
		workbook.setPrintArea(0,0,nb*arrayMatieres.size(),0,rowid+5);

		for (Eleve eleve : classe.getEleves()) {

			note = new Note[nbObj+nbDomaines][nb*arrayMatieres.size()];
			for (int i = 0; i < nbObj+nbDomaines; i++) {
				for (int j = 0; j < nb*arrayMatieres.size(); j++) {
					note[i][j]=new Note();
				}
			}
		
			
			int lig = 0;
			for (Domaine domaine : Dat.arrayDomaines) {
				{
				if(setDomaines.contains(domaine))
				lig++;
				for(Objectif objectif : domaine.getObjectifs()){
					if(setObjectifs.contains(objectif)){
						int col = 0;
						for (Matiere matiere : arrayMatieres) {
							ArrayList<Note> notes = Note.get(eleve, objectif, matiere);
							ArrayList<Note> notes2 = new ArrayList<>();
							for(int i = notes.size()-1 ; i>=0 && i>notes.size()-1-nb ; i--){
								notes2.add(notes.get(i));
							}							
							for (int j = notes2.size()-1; j >= 0 ; j--) {
								try {
									note[lig][col]=notes2.get(j);
								} catch (Exception e) {	
								}
								col++;
							}
						}
						lig++;
					}
				}
			}
			}

			spreadsheet = workbook.cloneSheet(0);
			workbook.setSheetName(sh++, eleve.toString());			

			spreadsheet.getRow(0).getCell(0).setCellValue(eleve.toString());
			
			rowid = 9;

			for (Domaine domaine : Dat.arrayDomaines) {
				if(setDomaines.contains(domaine))
				{
				rowid++;
				boolean bleu = false;
				for (Objectif obj : domaine.getObjectifs() ) {
					if(setObjectifs.contains(obj)){
						rowid++;	
						for (int i = 1; i < nb*arrayMatieres.size()+1; i++) {
							cell = spreadsheet.getRow(rowid).getCell(i);
							int val = note[rowid-10][i-1].getVal();	
							if (val!=9){
								if(val==0){
									if(note[rowid-10][i-1].isAbsent()){
										cell.setCellValue("A");
									}
									else{
										cell.setCellValue("NE");
									}
								}
								else{
									cell.setCellValue(val);
								}
							}	

						}
						bleu=!bleu;
					}
				}

			}
			}

		}
		//Write the workbook in file system
		
		try {
			FileOutputStream out = new FileOutputStream (classe.toString().toUpperCase() +"  "+dateStr+".xlsm");
			workbook.write(out);
			out.close();
			new Ouvrir(classe.toString().toUpperCase() +"  "+dateStr+".xlsm");
			System.out.println( "Writesheet.xlsx written successfully" );
			javax.swing.JOptionPane.showMessageDialog(null,"Fichier créé é l'emplacement de l'exécutable.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			javax.swing.JOptionPane.showMessageDialog(null,e.getMessage()); 
		}
		
		
	}

	private void surround(int firstRow, int lastRow, int firstCol, int lastCol) {
		CellRangeAddress cellRangeAddress = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);

		RegionUtil.setBorderBottom(BorderStyle.THIN, cellRangeAddress, model);
		RegionUtil.setBorderTop(BorderStyle.THIN, cellRangeAddress, model);
		RegionUtil.setBorderLeft(BorderStyle.THIN, cellRangeAddress, model);
		RegionUtil.setBorderRight(BorderStyle.THIN, cellRangeAddress, model);
	}
}
