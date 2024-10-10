package GUITest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ControllTest.Controlla;
import chesspresso.Chess;

public class Guihihi {
	ArrayList<PieceGUI> pieces = new ArrayList<PieceGUI>();
	BufferedImage img;
	
	PieceGUI selectedPiece;
	
	public JPanel bishop = new PieceGUI(Chess.C1, 1);
	public JPanel bishop1 = new PieceGUI(Chess.F1, 1);
	public JPanel bishop2 = new PieceGUI(Chess.C8, 7);
	public JPanel bishop3 = new PieceGUI(Chess.F8, 7);
	public JPanel knight = new PieceGUI(Chess.B1, 0);
	public JPanel knight1 = new PieceGUI(Chess.G1, 0);
	public JPanel knight2 = new PieceGUI(Chess.B8, 6);
	public JPanel knight3 = new PieceGUI(Chess.G8, 6);
	public JPanel rook = new PieceGUI(Chess.A1, 2);
	public JPanel rook1 = new PieceGUI(Chess.H1, 2);
	public JPanel rook2 = new PieceGUI(Chess.A8, 8);
	public JPanel rook3 = new PieceGUI(Chess.H8, 8);
	public JPanel pawn = new PieceGUI(Chess.H7, 10);
	public JPanel pawn1 = new PieceGUI(Chess.A2, 4);
	public JPanel pawn2 = new PieceGUI(Chess.B2, 4);
	public JPanel pawn3 = new PieceGUI(Chess.C2, 4);
	public JPanel pawn4 = new PieceGUI(Chess.D2, 4);
	public JPanel pawn5 = new PieceGUI(Chess.E2, 4);
	public JPanel pawn6 = new PieceGUI(Chess.F2, 4);
	public JPanel pawn7 = new PieceGUI(Chess.G2, 4);
	public JPanel pawn8 = new PieceGUI(Chess.H2, 4);
	public JPanel pawn9 = new PieceGUI(Chess.A7, 10);
	public JPanel pawn10 = new PieceGUI(Chess.B7, 10);
	public JPanel pawn11 = new PieceGUI(Chess.C7, 10);
	public JPanel pawn12 = new PieceGUI(Chess.D7, 10);
	public JPanel pawn13 = new PieceGUI(Chess.E7, 10);
	public JPanel pawn14 = new PieceGUI(Chess.F7, 10);
	public JPanel pawn15 = new PieceGUI(Chess.G7, 10);
	public JPanel queen = new PieceGUI(Chess.D1, 3);
	public JPanel queen1 = new PieceGUI(Chess.D8, 9);
	public JPanel king = new PieceGUI(Chess.E1, 5);
	public JPanel king1 = new PieceGUI(Chess.E8, 11);
	
	public PieceGUI findPieceByPosition(int position) {
		for(PieceGUI piece : pieces) {
			if(piece.position == position) {
				return piece;
			}
		}
		return null;
	}
	
	public void removePiece(PieceGUI piece) {
		
	}
	
	public Guihihi() throws IOException {
		JFrame frame = new JFrame("JIgger");
		JPanel panelSouth = new JPanel();
		
		JLabel selectInstruction = new JLabel("Select Piece");
		JTextField selectText = new JTextField(10);
		JButton selectButton = new JButton("Select");
		
		JLabel moveInstruction = new JLabel("                 Move Piece");
		JTextField moveText = new JTextField(10);
		JButton moveButton = new JButton("Move");
		
		
		
		JPanel panelNorth = new JPanel();
		
		JLabel Title = new JLabel("Chess.com!!! (but worse)");
		
		
		
		JPanel panelWest = new JPanel();
		
		JLabel explanation = new JLabel("<html>To select piece, type in piece position. example: e4 UNCAPITALIZED!!!. <br/>For moving its the same: type in piece position</html>");
		
		
		JLayeredPane bigcenterPanel = new JLayeredPane();
		JPanel chessBoard = new JPanel();
		chessBoard.setBounds(0,0,1000,1000);
	    chessBoard.setBackground(Color.black);
	    
	    
	    
		pieces.add((PieceGUI) bishop);
		pieces.add((PieceGUI) bishop1);
		pieces.add((PieceGUI) bishop2);
		pieces.add((PieceGUI) bishop3);
		pieces.add((PieceGUI) knight);
		pieces.add((PieceGUI) knight1);
		pieces.add((PieceGUI) knight2);
		pieces.add((PieceGUI) knight3);
		pieces.add((PieceGUI) rook);
		pieces.add((PieceGUI) rook1);
		pieces.add((PieceGUI) rook2);
		pieces.add((PieceGUI) rook3);
		pieces.add((PieceGUI) pawn);
		pieces.add((PieceGUI) pawn1);
		pieces.add((PieceGUI) pawn2);
		pieces.add((PieceGUI) pawn3);
		pieces.add((PieceGUI) pawn4);
		pieces.add((PieceGUI) pawn5);
		pieces.add((PieceGUI) pawn6);
		pieces.add((PieceGUI) pawn7);
		pieces.add((PieceGUI) pawn8);
		pieces.add((PieceGUI) pawn9);
		pieces.add((PieceGUI) pawn10);
		pieces.add((PieceGUI) pawn11);
		pieces.add((PieceGUI) pawn12);
		pieces.add((PieceGUI) pawn13);
		pieces.add((PieceGUI) pawn14);
		pieces.add((PieceGUI) pawn15);
		pieces.add((PieceGUI) queen);
		pieces.add((PieceGUI) queen1);
		pieces.add((PieceGUI) king);
		pieces.add((PieceGUI) king1);
		
		HighlightedSquares h = new HighlightedSquares();
		h.setBounds(0,0,1000,1000);
		h.setOpaque(false);
		
		//JPanel chessBoard = new JPanel();
		img = ImageIO.read(getClass().getResource("/images/board.jpg"));
		JLabel board = new JLabel(new ImageIcon(img));
		//textField.setSize(50, 500);
		//panel.setBorder(BorderFactory.createEmptyBorder(10,30,30,10));
		//panel.setLayout(new GridLayout(0,1));
		frame.setSize(2000,1000);
		
		panelSouth.add(selectInstruction);
		panelSouth.add(selectText);
		panelSouth.add(selectButton);
		
		panelSouth.add(moveInstruction);
		panelSouth.add(moveText);
		panelSouth.add(moveButton);
		
		panelNorth.add(Title);
		
		panelWest.add(explanation);
		
		chessBoard.add(board);
		
		bigcenterPanel.add(chessBoard,JLayeredPane.DEFAULT_LAYER);
		bigcenterPanel.add(h, JLayeredPane.PALETTE_LAYER);
		
		for(PieceGUI piece : pieces) {
			bigcenterPanel.add(piece, JLayeredPane.MODAL_LAYER);
		}
		
		frame.add(panelSouth, BorderLayout.SOUTH);
		frame.add(panelNorth, BorderLayout.NORTH);
		frame.add(panelWest, BorderLayout.WEST);
		frame.add(bigcenterPanel, BorderLayout.CENTER);
		
		
		
		//SELECT
		selectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectedPiece = findPieceByPosition(Chess.strToSqi(selectText.getText()));
				if(selectedPiece == null) {
					return;
				}
				h.legalMoves = Controlla.GetLegalMoves(selectedPiece);
				h.repaint();
			}
			
		});
		
		//MOVE
		moveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedPiece == null) {
					return;
				}
				int newPosition = Chess.strToSqi(moveText.getText());
				int MoveablePosition = Controlla.GetMoveSquare(selectedPiece, newPosition);
				
				if(MoveablePosition == selectedPiece.position) {
					System.out.println("suptid jgiar");
					System.out.println(Chess.sqiToStr(selectedPiece.position));
					return;
				}
				
				
				PieceGUI CapturablePiece = findPieceByPosition(newPosition);
				
				//Capture piece
				if(CapturablePiece != null) {
					pieces.remove(CapturablePiece);
					bigcenterPanel.remove(CapturablePiece);
				}
				selectedPiece.MoveTo(newPosition);
				selectedPiece = null;
				h.legalMoves = new int[0];
				h.repaint();
			}
			
		});
		//frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		//panel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
	}
	
}


