package GUITest;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import chesspresso.Chess;

public class PieceGUI extends JPanel {
	public int xposPixels = 78;
	public int yposPixels = 15;
	public int position;
	public int pieceIndex;
	BufferedImage sprite;
	JLabel spriteDisplay = new JLabel();
	//
	String spriteURL;
	String[] sprites = {"images/Images/whiteKnight.png", "images/Images/whiteBishop.png", "images/Images/whiteRook.png", "images/Images/whiteQueen.png", "images/Images/whitePawn.png", "images/Images/whiteKing.png",
						"images/Images/blackKnight.png", "images/Images/blackBishop.png", "images/Images/blackRook.png", "images/Images/blackQueen.png", "images/Images/blackPawn.png", "images/Images/blackKing.png"};
//	String[] sprites = {"/images/whiteKnight.png", "/images/whiteBishop.png", "/images/whiteRook.png", "/images/whiteQueen.png", "/images/whitePawn.png", "/images/whiteKing.png",
//						"/images/blackKnight.png", "/images/blackBishop.png", "/images/blackRook.png", "/images/blackQueen.png", "/images/blackPawn.png", "/images/blackKing.png"};

	
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
			spriteURL = sprites[pieceIndex];
			sprite = ImageIO.read(new FileInputStream(spriteURL));
			spriteDisplay.setIcon(new ImageIcon(sprite));
		}
	}
	
	public PieceGUI(int startPosition, int pieceIndex) throws IOException{
		//this.setPreferredSize(new Dimension(600, 400));
		this.pieceIndex = pieceIndex;
		spriteURL = sprites[pieceIndex];
//		InputStream boardInputStream = this.getClass().getResourceAsStream(spriteURL);
//		sprite = ImageIO.read(boardInputStream);
		
     	sprite = ImageIO.read(new FileInputStream(spriteURL));
		spriteDisplay.setIcon(new ImageIcon(sprite));
		 add(spriteDisplay);
		 position = startPosition;
		 xposPixels = 75 + Chess.sqiToCol(startPosition) * 78;
		 yposPixels = 15 + (7 -Chess.sqiToRow(startPosition)) * 78;
		 this.setOpaque(false);
		 this.setBounds(xposPixels, yposPixels,300,300);
		Guihihi.pieces.add(this);
	}
}
