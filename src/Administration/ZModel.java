package Administration;

import javax.swing.table.AbstractTableModel;

public class ZModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
private Object[][] data;
   private String[] title;
    
   //Constructeur
   public ZModel(Object[][] data, String[] title){
      this.data = data;
      this.title = title;
   }
    
   //Retourne le titre de la colonne é l'indice spécifié
   public String getColumnName(int col) {
     return this.title[col];
   }
 
   //Retourne le nombre de colonnes
   public int getColumnCount() {
      return this.title.length;
   }
    
   //Retourne le nombre de lignes
   public int getRowCount() {
      return this.data.length;
   }
    
   //Retourne la valeur é l'emplacement spécifié
   public Object getValueAt(int row, int col) {
      return this.data[row][col];
   }
    
   //Définit la valeur é l'emplacement spécifié
   public void setValueAt(Object value, int row, int col) {
	   System.out.println("row " + row);
	   System.out.println("col " + col);
         this.data[row][col] = value;
   }
          
  //Retourne la classe de la donnée de la colonne
   @SuppressWarnings("finally")
public Class getColumnClass(int col){
      try {
		return this.data[0][col].getClass();
	} finally {
		return String.class;
	}
   }
 
   //Méthode permettant de retirer une ligne du tableau
   public void removeRow(int position){
       
      int indice = 0, indice2 = 0;
      int nbRow = this.getRowCount()-1, nbCol = this.getColumnCount();
      Object temp[][] = new Object[nbRow][nbCol];
       
      for(Object[] value : this.data){
         if(indice != position){
            temp[indice2++] = value;
         }
         indice++;
      }
      this.data = temp;
      temp = null;

      this.fireTableDataChanged();
   }
    
   //Permet d'ajouter une ligne dans le tableau
   public void addRow(Object[] data){
      int indice = 0, nbRow = this.getRowCount(), nbCol = this.getColumnCount();
       
      Object temp[][] = this.data;
      this.data = new Object[nbRow+1][nbCol];
       
      for(Object[] value : temp)
         this.data[indice++] = value;
       
          
      this.data[indice] = data;
      temp = null;
      //Cette méthode permet d'avertir le tableau que les données
      //ont été modifiées, ce qui permet une mise é jour compléte du tableau
      this.fireTableDataChanged();
   }
    
    
   public boolean isCellEditable(int row, int col){
      return true;
   }
}