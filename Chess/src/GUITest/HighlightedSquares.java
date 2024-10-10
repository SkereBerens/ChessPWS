package GUITest;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import chesspresso.Chess;

public class HighlightedSquares extends JPanel{
	public int[] legalMoves = {};
	@Override
	protected void paintComponent(Graphics g) {
		Color color = new Color(255, 0, 0 , 127);
	     super.paintComponent(g);
	     for(int legalMove : legalMoves) {
	    	 int xpos = Chess.sqiToCol(legalMove);
	    	 int ypos = 7 - Chess.sqiToRow(legalMove);
	    	 g.drawRect(183 + 79 * xpos, 31 + 80 * ypos, 78, 78);
    		 g.setColor(color);
    		 g.fillRect(183 + 79 * xpos, 31 + 80 * ypos, 78, 78);	 
			 
	     }
	    		 
	      
	}
	
	
	
	
}
