package GUITest;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import chesspresso.Chess;

public class PieceGUI extends JPanel {
	public int xposPixels = 78;
	public int yposPixels = 15;
	public int position;
	int pieceIndex;
	BufferedImage sprite;
	JLabel spriteDisplay = new JLabel();
	//
	String[] sprites = {"/images/whiteKnight.png", "/images/whiteBishop.png", "/images/whiteRook.png", "/images/whiteQueen.png", "/images/whitePawn.png", "/images/whiteKing.png",
						"/images/blackKnight.png", "/images/blackBishop.png", "/images/blackRook.png", "/images/blackQueen.png", "/images/blackPawn.png", "/images/blackKing.png"};
	
	public void MoveTo(int position) {
		this.position = position;
		xposPixels = 75 + Chess.sqiToCol(position) * 78;
		yposPixels = 15 + (7 -Chess.sqiToRow(position)) * 78;
		this.setLocation(xposPixels, yposPixels);
	}
	
	//Only for pawns
	public void Promote(int pieceIndexPromotion) throws IOException {
		if(pieceIndex == 4 || pieceIndex == 10) {
			pieceIndex = pieceIndexPromotion;
			sprite = ImageIO.read(getClass().getResource(sprites[pieceIndex]));
			spriteDisplay.setIcon(new ImageIcon(sprite));
		}
	}
	
	public PieceGUI(int startPosition, int pieceIndex) throws IOException{
		//this.setPreferredSize(new Dimension(600, 400));
		this.pieceIndex = pieceIndex;
		sprite = ImageIO.read(getClass().getResource(sprites[this.pieceIndex]));
		spriteDisplay.setIcon(new ImageIcon(sprite));
		 add(spriteDisplay);
		 position = startPosition;
		 xposPixels = 75 + Chess.sqiToCol(startPosition) * 78;
		 yposPixels = 15 + (7 -Chess.sqiToRow(startPosition)) * 78;
		 this.setOpaque(false);
		 this.setBounds(xposPixels, yposPixels,300,300);
	}
}
