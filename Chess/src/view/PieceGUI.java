package view;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import chesspresso_OpenSourceCode.Chess;

public class PieceGUI extends JPanel {
	public int xposPixels = 78;
	public int yposPixels = 15;
	public int position;
	public int pieceIndex;
	Rectangle2D area;
	BufferedImage sprite;
	JLabel spriteDisplay = new JLabel();
	//
	String spriteURL;
	String[] sprites = {"images/Images/whiteKnight.png", "images/Images/whiteBishop.png", "images/Images/whiteRook.png", "images/Images/whiteQueen.png", "images/Images/whitePawn.png", "images/Images/whiteKing.png",
						"images/Images/blackKnight.png", "images/Images/blackBishop.png", "images/Images/blackRook.png", "images/Images/blackQueen.png", "images/Images/blackPawn.png", "images/Images/blackKing.png"};
	
	
	public void MoveTo(int position) {
		this.position = position;
		xposPixels = 184 + Chess.sqiToCol(position) * 79;
		yposPixels = 36 + (7 -Chess.sqiToRow(position)) * 78;
		area = new Rectangle2D.Double(xposPixels, yposPixels,78,78);
		this.setLocation(xposPixels, yposPixels);
		SoundEffect.play();
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
		this.pieceIndex = pieceIndex;
		spriteURL = sprites[pieceIndex];
     	sprite = ImageIO.read(new FileInputStream(spriteURL));
		spriteDisplay.setIcon(new ImageIcon(sprite));
		add(spriteDisplay);
		position = startPosition;
		xposPixels = 184 + Chess.sqiToCol(startPosition) * 79;
		yposPixels = 36 + (7 -Chess.sqiToRow(startPosition)) * 78;
		this.setOpaque(false);
		this.setBounds(xposPixels, yposPixels,78,78);
		area = new Rectangle2D.Double(xposPixels, yposPixels,78,78);
		GUI.pieces.add(this);
	}
}
