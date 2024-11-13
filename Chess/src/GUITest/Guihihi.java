package GUITest;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.FileInputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ControllTest.Controlla;
import chesspresso.Chess;

public class Guihihi implements MouseListener {
	public static ArrayList<PieceGUI> activePieces = new ArrayList<PieceGUI>();
	public static ArrayList<PieceGUI> pieces = new ArrayList<PieceGUI>();
	BufferedImage img;
	
	public static PieceGUI selectedPiece;
	
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
	
	//FOR LOAD POSITION
	public JPanel queen2 = new PieceGUI(-1, 3);
	public JPanel queen3 = new PieceGUI(-1, 9);
	public JPanel queen4 = new PieceGUI(-1, 3);
	public JPanel queen5 = new PieceGUI(-1, 9);
	public JPanel queen6 = new PieceGUI(-1, 3);
	public JPanel queen7 = new PieceGUI(-1, 9);
	public JPanel queen8 = new PieceGUI(-1, 3);
	public JPanel queen9 = new PieceGUI(-1, 9);
	public JPanel queen10 = new PieceGUI(-1, 3);
	public JPanel queen11 = new PieceGUI(-1, 9);
	public JPanel queen12 = new PieceGUI(-1, 3);
	public JPanel queen13 = new PieceGUI(-1, 9);
	public JPanel queen14 = new PieceGUI(-1, 3);
	public JPanel queen15 = new PieceGUI(-1, 9);
	public JPanel queen16 = new PieceGUI(-1, 3);
	public JPanel queen17 = new PieceGUI(-1, 9);
	
	public JPanel rook4 = new PieceGUI(-1, 2);
	public JPanel rook5 = new PieceGUI(-1, 8);
	public JPanel rook6 = new PieceGUI(-1, 2);
	public JPanel rook7 = new PieceGUI(-1, 8);
	public JPanel rook8 = new PieceGUI(-1, 2);
	public JPanel rook9 = new PieceGUI(-1, 8);
	public JPanel rook10 = new PieceGUI(-1, 2);
	public JPanel rook11 = new PieceGUI(-1, 8);
	public JPanel rook12 = new PieceGUI(-1, 2);
	public JPanel rook13 = new PieceGUI(-1, 8);
	public JPanel rook14 = new PieceGUI(-1, 2);
	public JPanel rook15 = new PieceGUI(-1, 8);
	public JPanel rook16 = new PieceGUI(-1, 2);
	public JPanel rook17 = new PieceGUI(-1, 8);
	public JPanel rook18 = new PieceGUI(-1, 2);
	public JPanel rook19 = new PieceGUI(-1, 8);
	
	public JPanel knight4 = new PieceGUI(-1, 0);
	public JPanel knight5 = new PieceGUI(-1, 6);
	public JPanel knight6 = new PieceGUI(-1, 0);
	public JPanel knight7 = new PieceGUI(-1, 6);
	public JPanel knight8 = new PieceGUI(-1, 0);
	public JPanel knight9 = new PieceGUI(-1, 6);
	public JPanel knight10 = new PieceGUI(-1, 0);
	public JPanel knight11 = new PieceGUI(-1, 6);
	public JPanel knight12 = new PieceGUI(-1, 0);
	public JPanel knight13 = new PieceGUI(-1, 6);
	public JPanel knight14 = new PieceGUI(-1, 0);
	public JPanel knight15 = new PieceGUI(-1, 6);
	public JPanel knight16 = new PieceGUI(-1, 0);
	public JPanel knight17 = new PieceGUI(-1, 6);
	public JPanel knight18 = new PieceGUI(-1, 0);
	public JPanel knight19 = new PieceGUI(-1, 6);
	
	public JPanel bishop4 = new PieceGUI(-1, 1);
	public JPanel bishop5 = new PieceGUI(-1, 7);
	public JPanel bishop6 = new PieceGUI(-1, 1);
	public JPanel bishop7 = new PieceGUI(-1, 7);
	public JPanel bishop8 = new PieceGUI(-1, 1);
	public JPanel bishop9 = new PieceGUI(-1, 7);
	public JPanel bishop10 = new PieceGUI(-1, 1);
	public JPanel bishop11 = new PieceGUI(-1, 7);
	public JPanel bishop12 = new PieceGUI(-1, 1);
	public JPanel bishop13 = new PieceGUI(-1, 7);
	public JPanel bishop14 = new PieceGUI(-1, 1);
	public JPanel bishop15 = new PieceGUI(-1, 7);
	public JPanel bishop16 = new PieceGUI(-1, 1);
	public JPanel bishop17 = new PieceGUI(-1, 7);
	public JPanel bishop18 = new PieceGUI(-1, 1);
	public JPanel bishop19 = new PieceGUI(-1, 7);

	static JFrame frame = new JFrame("Chess.com");
	JPanel panelSouth = new JPanel();
	
	JLabel selectInstruction = new JLabel("Select Piece");
	JTextField selectText = new JTextField(10);
	JButton selectButton = new JButton("Select");
	
	JLabel moveInstruction = new JLabel("                 Move Piece");
	JTextField moveText = new JTextField(10);
	JButton moveButton = new JButton("Move");
	
	
	
	JPanel panelNorth = new JPanel();
	
	JLabel Title = new JLabel("Chess.com!!! (but worse)");
	
	JLayeredPane panelWest = new JLayeredPane();
	
	JLabel explanation = new JLabel("<html>Select piece by clicking it. <br/>Click red highlighted square to move there <br/>Type in FEN string to load position</html>");
	JLabel fenText = new JLabel("Load FEN: ");
	JTextField fenTextField = new JTextField("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", 40);
	JButton fenButton = new JButton("Load");
	
	public static JLayeredPane bigcenterPanel = new JLayeredPane();
	JPanel chessBoard = new JPanel();

	public static HighlightedSquares h = new HighlightedSquares();
	JLabel board;
	
	public static PieceGUI findPieceByPosition(int position) {
		for(PieceGUI piece : activePieces) {
			if(piece.position == position) {
				return piece;
			}
		}
		return null;
	}
	
	public static void MovePiece(int movePosition, PieceGUI piece) throws IOException
	{
		if(piece == null) {
			return;
		}
		Controlla.MovePiece(piece, movePosition);
		
		selectedPiece = null;
		h.legalMoves = new int[0];
		h.repaint();
		//h.HighlightSquares();
		
	}
	
	public static void SelectPiece(PieceGUI piece) {
		selectedPiece = piece;
		if(selectedPiece == null) {
			return;
		}
		h.legalMoves = Controlla.GetLegalMoves(selectedPiece);
		h.repaint();
		//h.HighlightSquares();
	}
	
	public static void CapturePiece(PieceGUI capturedPiece) {
		if(capturedPiece == null) {
			return;
		}
		activePieces.remove(capturedPiece);
		bigcenterPanel.remove(capturedPiece);
	}
	
	static char[] fenChars;
	static int position = 56;
	public static void LoadPosition(String fen) {
		fenChars = fen.toCharArray();
		for(PieceGUI p : activePieces) {
			bigcenterPanel.remove(p);
		}
		bigcenterPanel.repaint();
		activePieces.removeAll(activePieces);
		for(char ch2 : fenChars) {
			System.out.println(ch2);
		}
		System.out.println(Controlla.ConvertCharToIndex('n'));
		for(char ch : fenChars) {
			if(ch == ' ') {
				
			} else if(Chess.IsInListChr(Chess.pieceChars, ch)) {
				for(PieceGUI piecegui : pieces) {
					if(piecegui.pieceIndex == Controlla.ConvertCharToIndex(ch) && !activePieces.contains(piecegui)) {
						piecegui.MoveTo(position);
						System.out.println("ITS THIS PIECE: " + ch + " ON THIS POSITION " + piecegui.position);
						activePieces.add(piecegui);
						bigcenterPanel.add(piecegui, JLayeredPane.MODAL_LAYER);
						position += 1;
						break;
					}
				}
			} else if(ch == '/') {
				position -= 16;
			} else if(ch == 'w' || ch == 'b'){
				break;
			}  else {
				position +=  Integer.parseInt(String.valueOf(ch));
			}
			
		}
		bigcenterPanel.repaint();
		position = 56;
	}
	
	public Guihihi() throws IOException {
		
		chessBoard.setBounds(0,0,1000,1000);
	    chessBoard.setBackground(Color.black);
	    
		activePieces.add((PieceGUI) bishop);
		activePieces.add((PieceGUI) bishop1);
		activePieces.add((PieceGUI) bishop2);
		activePieces.add((PieceGUI) bishop3);
		activePieces.add((PieceGUI) knight);
		activePieces.add((PieceGUI) knight1);
		activePieces.add((PieceGUI) knight2);
		activePieces.add((PieceGUI) knight3);
		activePieces.add((PieceGUI) rook);
		activePieces.add((PieceGUI) rook1);
		activePieces.add((PieceGUI) rook2);
		activePieces.add((PieceGUI) rook3);
		activePieces.add((PieceGUI) pawn);
		activePieces.add((PieceGUI) pawn1);
		activePieces.add((PieceGUI) pawn2);
		activePieces.add((PieceGUI) pawn3);
		activePieces.add((PieceGUI) pawn4);
		activePieces.add((PieceGUI) pawn5);
		activePieces.add((PieceGUI) pawn6);
		activePieces.add((PieceGUI) pawn7);
		activePieces.add((PieceGUI) pawn8);
		activePieces.add((PieceGUI) pawn9);
		activePieces.add((PieceGUI) pawn10);
		activePieces.add((PieceGUI) pawn11);
		activePieces.add((PieceGUI) pawn12);
		activePieces.add((PieceGUI) pawn13);
		activePieces.add((PieceGUI) pawn14);
		activePieces.add((PieceGUI) pawn15);
		activePieces.add((PieceGUI) queen);
		activePieces.add((PieceGUI) queen1);
		activePieces.add((PieceGUI) king);
		activePieces.add((PieceGUI) king1);
		
		h.setBounds(0,0,1000,1000);
		h.setOpaque(false);
		
		img = ImageIO.read(new FileInputStream("images/Images/board.jpg"));
		board = new JLabel(new ImageIcon(img));
		
		frame.setSize(2000,1000);
		
		panelSouth.add(selectInstruction);
		panelSouth.add(selectText);
		panelSouth.add(selectButton);
		
		panelSouth.add(moveInstruction);
		panelSouth.add(moveText);
		panelSouth.add(moveButton);
		
		panelNorth.add(Title);
		
		panelWest.setPreferredSize(new Dimension(800,0));
		JPanel testr = new JPanel();
		JPanel testr2 = new JPanel();
		testr.setBounds(0,0,500,100);
		testr2.setBounds(0,100,800,100);
		testr.setBackground(Color.RED);
		testr.add(explanation);
		testr2.add(fenText);
		testr2.add(fenTextField);
		testr2.add(fenButton);
		panelWest.add(testr);
		panelWest.add(testr2);
//		panelWest.add(explanation);
//		panelWest.add(fenText);
		
		chessBoard.add(board);
		
		bigcenterPanel.add(chessBoard,JLayeredPane.DEFAULT_LAYER);
		bigcenterPanel.add(h, JLayeredPane.PALETTE_LAYER);
		
		
		for(PieceGUI piece : activePieces) {
			bigcenterPanel.add(piece, JLayeredPane.MODAL_LAYER);
		}
		
		frame.add(panelSouth, BorderLayout.SOUTH);
		frame.add(panelWest, BorderLayout.WEST);
		frame.add(panelNorth, BorderLayout.NORTH);
		frame.add(bigcenterPanel, BorderLayout.CENTER);
		
		//SELECT
		selectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Controlla.UndoMove();
			}
			
		});
		
		//MOVE
		moveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					MovePiece(Chess.strToSqi(moveText.getText()), selectedPiece);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			};
			
		});
		
		fenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controlla.LoadPosition(fenTextField.getText());
			};
		});
		
		for(PieceGUI piece : pieces) {
			piece.addMouseListener(this);
		}
		h.addMouseListener(this);
		
		//frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		//panel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource().getClass() == HighlightedSquares.class) {
			h.MoveToHighlightedSquare(e);
		} else if(e.getSource().getClass() == PieceGUI.class) {
			for(int legalMove : h.legalMoves) {
				if(legalMove == ((PieceGUI) e.getSource()).position) {
					try {
						MovePiece(legalMove , selectedPiece);
						return;
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			SelectPiece(((PieceGUI) e.getSource()));
		}
		
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}


