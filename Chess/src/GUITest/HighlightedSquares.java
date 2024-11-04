package GUITest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import chesspresso.Chess;
import demo.Piece;

public class HighlightedSquares extends JPanel {
	public int[] legalMoves = {};

	Color color = new Color(255, 0, 0 , 127);
	int xpos;
	int ypos;
	

	Rectangle2D[][] squares = new Rectangle2D[8][8];
	ArrayList<Rectangle2D> clickableSquares = new ArrayList<Rectangle2D>();
	Graphics2D g2d;
	@Override
	protected void paintComponent(Graphics g) {
	     super.paintComponent(g);
	     for(Rectangle2D rect : GetClickableSquares()) {
	    	 g2d = (Graphics2D) g;
	    	 g2d.setColor(color);
	    	 g2d.fill(rect); 
	     }
	    		 
	      
	}
	
	public boolean MoveToHighlightedSquare(MouseEvent e) {
		for(int legalMove : legalMoves) {
			xpos = Chess.sqiToCol(legalMove);
	    	ypos = 7 - Chess.sqiToRow(legalMove);
	    	if(squares[xpos][ypos].contains(e.getX(), e.getY())) {
					try {
						Guihihi.MovePiece(legalMove ,Guihihi.selectedPiece);
						return true;
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
	    }
		return false;
	}
	
	public Rectangle2D[] GetClickableSquares() {
		clickableSquares.removeAll(clickableSquares);
		for(int legalMove : legalMoves) {
	    	 xpos = Chess.sqiToCol(legalMove);
	    	 ypos = 7 - Chess.sqiToRow(legalMove);
	    	 clickableSquares.add(squares[xpos][ypos]);
		}
		return clickableSquares.toArray(new Rectangle2D[clickableSquares.size()]);
	}
	
	public HighlightedSquares() {
		for(int col = 0; col <= 7; col++) {
			for(int row = 0; row <= 7; row++) {
				squares[row][col] = new Rectangle2D.Double(183 + 79 * (row), 31 + 80 * (col), 78, 78);
			}
		}
		
		 
	}
}
