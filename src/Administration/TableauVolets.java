package Administration;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class TableauVolets extends JPanel {

    private JPanel corner = new JPanel();
    private JPanel tabCenter[][], tabTop[],tabLeft[];
	private JScrollPane scrollCenter;

    private static final Color color1 = Color.white, color2 = new Color(217,226,243), color3 = new Color (180,198,231);

	public TableauVolets(int nbLignes, int nbColonnes, int hauteurLignes, int largeurColonnes, int hauteurTop, int largeurLeft){
		super();
        int hauteurView = hauteurLignes * nbLignes;
        int largeurView = largeurColonnes * nbColonnes;
		
		tabCenter = new JPanel[nbLignes][nbColonnes];
		tabTop = new JPanel[nbColonnes];
		tabLeft = new JPanel[nbLignes];

        JPanel globalTotal = new JPanel();
        globalTotal.setLayout(new BorderLayout());
        JPanel globalV = new JPanel();
        globalV.setLayout(new BorderLayout());
        JPanel leftTotal = new JPanel();
        leftTotal.setLayout(new BorderLayout());

        JPanel center = new JPanel();
        center.setPreferredSize(new Dimension(largeurView, hauteurView));
        JPanel top = new JPanel();
        top.setPreferredSize(new Dimension(nbColonnes*largeurColonnes,hauteurTop));
		corner.setPreferredSize(new Dimension(largeurLeft, hauteurTop));
        JPanel left = new JPanel();
        left.setPreferredSize(new Dimension(largeurLeft, hauteurView));

        JPanel centerOut = new JPanel();
        centerOut.setMinimumSize(new Dimension(1200, 1200));
		centerOut.add(center);
		centerOut.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel topOut = new JPanel();
        topOut.setMinimumSize(new Dimension(1200, hauteurTop));
		topOut.setLayout(new FlowLayout(FlowLayout.LEFT));
		topOut.add(top);

        JPanel leftOut = new JPanel();
        leftOut.setMinimumSize(new Dimension(largeurLeft,1200));
		leftOut.add(left);

        JPanel cornerOut = new JPanel();
        cornerOut.setMinimumSize(new Dimension(largeurLeft, hauteurTop));
		cornerOut.add(corner);

		center.setLayout(new GridLayout(nbLignes,nbColonnes));
		top.setLayout(new GridLayout(1,nbColonnes));
		left.setLayout(new GridLayout(nbLignes,1));

		for (int i = 0; i < nbLignes; i++) {
			for (int j = 0; j < nbColonnes; j++) {

				if (j==0){
					tabLeft[i] = new JPanel();
					if(Math.floorMod(i, 2) == 0){
					tabLeft[i].setBackground(color1);
					} else {
					tabLeft[i].setBackground(color2);
					}
					left.add(tabLeft[i]);
				}
				
				if (i==0){
					tabTop[j] = new JPanel();
					if(Math.floorMod(j, 2) == 0){
						tabTop[j].setBackground(color1);
						} else {
						tabTop[j].setBackground(color2);
						}
					top.add(tabTop[j]);
				}
				
				tabCenter[i][j]= new JPanel();
				tabCenter[i][j].setBackground(Color.WHITE);
				tabCenter[i][j].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
				center.add(tabCenter[i][j]);
				
				if(Math.floorMod(i, 2) == 0 && Math.floorMod(j, 2) ==0)
				{
					tabCenter[i][j].setBackground(color1);
				}else{
					if(Math.floorMod(i, 2) == 1 && Math.floorMod(j, 2) ==1){
					tabCenter[i][j].setBackground(color3);	
					}else{
						tabCenter[i][j].setBackground(color2);
					}
				}
				
				
				
			}
		}

        JScrollPane scrollTop = new JScrollPane(topOut);
		scrollCenter = new JScrollPane(centerOut);
        JScrollPane scrollCorner = new JScrollPane(cornerOut);
        JScrollPane scrollLeft = new JScrollPane(leftOut);
		
		globalTotal.add(leftTotal, BorderLayout.WEST);
		globalTotal.add(globalV, BorderLayout.CENTER);
		
		globalV.add(scrollTop,BorderLayout.NORTH);	
		globalV.add(scrollCenter, BorderLayout.CENTER);
		
		leftTotal.add(scrollCorner, BorderLayout.NORTH);
		leftTotal.add(scrollLeft, BorderLayout.WEST);
		
		scrollTop.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollTop.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollCorner.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollCorner.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollLeft.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollLeft.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollTop.setOpaque(false);
		scrollTop.getViewport().setOpaque(false);
		scrollCorner.setOpaque(false);
		scrollCorner.getViewport().setOpaque(false);
		scrollLeft.setOpaque(false);
		scrollLeft.getViewport().setOpaque(false);
		scrollCenter.setOpaque(false);
		scrollCenter.getViewport().setOpaque(false);

	    scrollCenter.getHorizontalScrollBar().setModel(scrollTop.getHorizontalScrollBar().getModel());
	    scrollCenter.getVerticalScrollBar().setModel(scrollLeft.getVerticalScrollBar().getModel());
	    scrollCenter.getVerticalScrollBar().setUnitIncrement(16);
		
		this.setLayout(new BorderLayout());
		this.add(globalTotal,BorderLayout.CENTER);  
	}

	public JPanel[][] getTabCenter() {
		return tabCenter;
	}

	public JPanel[] getTabTop() {
		return tabTop;
	}

	public JPanel[] getTabLeft() {
		return tabLeft;
	}

	public JScrollPane getScrollCenter() {
		return scrollCenter;
	}

	public void setScrollCenter(JScrollPane scrollCenter) {
		this.scrollCenter = scrollCenter;
	}

	public JPanel getCorner() {
		return corner;
	}

	public void setCorner(JPanel corner) {
		this.corner = corner;
	}
	
}
