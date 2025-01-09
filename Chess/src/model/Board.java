package model;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

import chesspresso_OpenSourceCode.Chess;
import controller.Controller;
import view.PieceGUI;

public class Board {
	public static ArrayList<Piece> activePieces = new ArrayList<Piece>();
	
	//bottom right corner is H8 and top left corner is A1
	static int[] positionGrid = {0,0,0,0,0,0,0,0,
						  		 0,0,0,0,0,0,0,0,
						  		 0,0,0,0,0,0,0,0,
						  		 0,0,0,0,0,0,0,0,
						  		 0,0,0,0,0,0,0,0,
						  		 0,0,0,0,0,0,0,0,
						  		 0,0,0,0,0,0,0,0,
						  		 0,0,0,0,0,0,0,0};
	
	public static int[] GetPositionGrid() {
		return positionGrid;
	}
	
	static public void updatePosition() {
		for(int i = 0; i < 64; i ++) {
			positionGrid[i] = 0;
		}
		for(Piece piece : activePieces) {
			
			positionGrid[piece.position] = piece.stone;
		}
	}

	static String[] emptySpaces = {"", "1", "2", "3", "4", "5", "6", "7", "8"};
	
	static int emptySpace = 0;

	static String positionFen = "";
	//FEN is een soort notatie voor een positie: alleen van posities stukken
	static public String GetFENBasic() {
		positionFen = "";
		for(int i = 0; i < 8; i ++) {
			for(int j = 0; j < 8; j++) {
				if(positionGrid[(7-i) * 8 + j] != 0) {
					positionFen += emptySpaces[emptySpace];
					emptySpace = 0;
					positionFen += Chess.stoneToChar(positionGrid[(7-i) * 8 + j]);
				} else {
					emptySpace++;
				}
				
			}
			positionFen += emptySpaces[emptySpace];
			
			emptySpace = 0;
			positionFen += "/";
		}
		emptySpace = 0;
		return positionFen.substring(0, positionFen.length() - 1);
	}
	
	static String positionFenAdvancedAddOn = "";
	static int enpassantAbleSquare = -1;
	public static String GetFENAdvancedAddOn() {
		positionFenAdvancedAddOn = "";
		if(Piece.ply % 2 == 0) {
			positionFenAdvancedAddOn += "b";
		} else {
			positionFenAdvancedAddOn += "w";
		}
		
		positionFenAdvancedAddOn += " ";
		if(GetKing(-1).hasCastlingRightsShort || GetKing(-1).hasCastlingRightsLong || GetKing(1).hasCastlingRightsShort || GetKing(1).hasCastlingRightsLong) {
			if(GetKing(-1).hasCastlingRightsShort) {
				positionFenAdvancedAddOn += "K";
			}
			if(GetKing(-1).hasCastlingRightsLong) {
				positionFenAdvancedAddOn += "Q";
			}
			if(GetKing(1).hasCastlingRightsShort) {
				positionFenAdvancedAddOn += "k";
			}
			if(GetKing(1).hasCastlingRightsLong) {
				positionFenAdvancedAddOn += "q";
			}
		} else {
			positionFenAdvancedAddOn += "-";
		}
		
		
		positionFenAdvancedAddOn += " ";
		for(Piece pawn : GetPiecesOfType(Chess.WHITE_PAWN)) {
			if(	((Pawn)pawn).enpassantableSquare != -1) {
				enpassantAbleSquare = ((Pawn)pawn).enpassantableSquare;
				break;
			}
		}
		if(enpassantAbleSquare == -1) {
			positionFenAdvancedAddOn += "-";
		} else {
			positionFenAdvancedAddOn += "" + Chess.sqiToStr(enpassantAbleSquare);
		}
		enpassantAbleSquare = -1;
		
		positionFenAdvancedAddOn += " " + Piece.movesTilDraw + " " + Piece.fullMoves;
		
		return positionFenAdvancedAddOn;
	}
	
	static String positionFenAdvanced;
	//FEN van posities stukken + wiens beurt het is + castling rights
	static public String GetFENAdvanced() {
		positionFenAdvanced = "";
		positionFenAdvanced = GetFENBasic() + " " + GetFENAdvancedAddOn();
		return positionFenAdvanced;
	}
	
	
	static char[] fenChars;
	static char[] fenCharsBasic;
	
	static String fenAdvancedAddOn;
	
	
	static final int colorToMove = 0;
	static final int castlingRights = 1;
	static final int enpassantSquare = 2;
	static final int movesTilDraw = 3;
	static final int fullMoves = 4;
	static String[] fenAdvancedSections =  new String[5];
	
	static int enpassantableSquare = -1;
	
	static int position = 56;
	
	public static void LoadPosition(String fen) {
		fenChars = fen.toCharArray();
		for(Piece piece : activePieces) {
			piece.captured = true;
			piece.isPinned = false;
			piece.hasMoved = false;
			piece.king = null;
			piece.attackingPiece = null;
			if(Chess.stoneToPiece(piece.stone) == 6) {
				((King) piece).isInCheck = false;
				((King) piece).doubleCheck = false;
				((King) piece).attackingPiece = null;
				((King) piece).hasCastlingRightsShort = false;
				((King) piece).hasCastlingRightsLong = false;
			}
			
			if(Chess.stoneToPiece(piece.stone) == 5) {
				((Pawn) piece).enpassantableSquare = -1;
				((Pawn) piece).isPinnedForEnPassant = false;
			}
		}
		Gamemanager.positionsFen.removeAll(Gamemanager.positionsFen);
		
		activePieces.removeAll(activePieces);
		fenCharsBasic = fen.substring(0 ,new String(fenChars).indexOf(' ')).toCharArray();
		fenAdvancedAddOn = fen.substring(new String(fenChars).indexOf(' ') , fen.length()).trim();
		
		for(char ch : fenCharsBasic) {
			if(Chess.IsInListChr(Chess.pieceChars, ch)) {
				for(Piece piece : Model.pieces) {
					if(piece.stone ==  Chess.charToStone(ch) && !activePieces.contains(piece)) {
						addPiece(piece);
						piece.position = position;
						piece.captured = false;
						position += 1;
						
						break;
					}
				}
			} else if(ch == '/') {
				position -= 16;
			}  else {
				position +=  Integer.parseInt(String.valueOf(ch));
			}
		}
		Controller.UpdateAllPieceGUIPosition();
		position = 56;

		updatePosition();
		
		
		fenAdvancedAddOn.split(" ");
		for(int i = 0; i < fenAdvancedAddOn.split(" ").length; i++) {
			fenAdvancedSections[i] = fenAdvancedAddOn.split(" ")[i];
		}
		
		if(fenAdvancedSections[colorToMove].equals("w")) {
			Piece.ply = 1;
			for(Piece slidingPiece : Board.GetSlidingPieces(1)) {
				slidingPiece.Pin();
			}
			GetKing(-1).isInCheck();
		} else if(fenAdvancedSections[colorToMove].equals("b")){
			Piece.ply = 2;
			for(Piece slidingPiece : Board.GetSlidingPieces(-1)) {
				slidingPiece.Pin();
			}
			GetKing(1).isInCheck();
		} 
		
		for(char ch : fenAdvancedSections[castlingRights].toCharArray()) {
			if(ch == 'K') {
				GetKing(-1).hasCastlingRightsShort = true;
			} else if(ch == 'Q') {
				GetKing(-1).hasCastlingRightsLong = true;
			} else if(ch == 'k') {
				GetKing(1).hasCastlingRightsShort = true;
			} else if(ch == 'q') {
				GetKing(1).hasCastlingRightsLong = true;
			}
 		}
		
		enpassantableSquare = -1;
		if(!fenAdvancedSections[enpassantSquare].equals("-")) {
			enpassantableSquare = Chess.strToSqi(fenAdvancedSections[enpassantSquare]);
			if(Chess.sqiToRow(enpassantableSquare) == 5) {
				((Pawn)FindPieceByPosition(enpassantableSquare - 8)).enpassantableSquare = enpassantableSquare;
			} else if(Chess.sqiToRow(enpassantableSquare) == 2) {
				((Pawn)FindPieceByPosition(enpassantableSquare + 8)).enpassantableSquare = enpassantableSquare;
			}
		}
	
		Piece.movesTilDraw = Integer.parseInt(fenAdvancedSections[movesTilDraw]);
		Piece.fullMoves = Integer.parseInt(fenAdvancedSections[fullMoves]);
		
		updatePosition();
	}
	
	
	
	
	
	
	static String fenAdvancedAddOn1;
	
	static String[] fenAdvancedSections1 =  new String[5];
	
	static int enpassantableSquare1 = -1;
	
	
	public static void LoadFENAdvancedAddOn(String fen) {
		fenAdvancedAddOn1 = fen;
		fenAdvancedAddOn1.split(" ");
		for(int i = 0; i < fenAdvancedAddOn1.split(" ").length; i++) {
			fenAdvancedSections1[i] = fenAdvancedAddOn1.split(" ")[i];
		}
		
		if(fenAdvancedSections1[colorToMove].equals("w")) {
			Piece.ply = 1;
			for(Piece slidingPiece : Board.GetSlidingPieces(1)) {
				slidingPiece.Pin();
			}
			GetKing(-1).isInCheck();
		} else if(fenAdvancedSections1[colorToMove].equals("b")){
			Piece.ply = 2;
			for(Piece slidingPiece : Board.GetSlidingPieces(-1)) {
				slidingPiece.Pin();
			}
			GetKing(1).isInCheck();
		} 
		
		for(char ch : fenAdvancedSections1[castlingRights].toCharArray()) {
			if(ch == 'K') {
				GetKing(-1).hasCastlingRightsShort = true;
			} else if(ch == 'Q') {
				GetKing(-1).hasCastlingRightsLong = true;
			} else if(ch == 'k') {
				GetKing(1).hasCastlingRightsShort = true;
			} else if(ch == 'q') {
				GetKing(1).hasCastlingRightsLong = true;
			}
 		}
		
		enpassantableSquare1 = -1;
		if(!fenAdvancedSections1[enpassantSquare].equals("-")) {
			enpassantableSquare1 = Chess.strToSqi(fenAdvancedSections1[enpassantSquare]);
			if(Chess.sqiToRow(enpassantableSquare1) == 5) {
				((Pawn)FindPieceByPosition(enpassantableSquare1 - 8)).enpassantableSquare = enpassantableSquare1;
			} else if(Chess.sqiToRow(enpassantableSquare1) == 2) {
				((Pawn)FindPieceByPosition(enpassantableSquare1 + 8)).enpassantableSquare = enpassantableSquare1;
			}
		}
	
		Piece.movesTilDraw = Integer.parseInt(fenAdvancedSections1[movesTilDraw]);
		Piece.fullMoves = Integer.parseInt(fenAdvancedSections1[fullMoves]);
		
		updatePosition();
	}
	

	
	
	public static void addPiece(Piece p) {
		p.captured = false;
		activePieces.add(p);
	}
	
	public static void RemovePiece(int position) {
		Piece piece = FindPieceByPosition(position);
		if(piece != null) {
			piece.captured = true;
			activePieces.remove(piece);
		}
	}
	
	public static Piece FindPieceByPosition(int position) {
		for(Piece piece : activePieces) {
			if(piece.position == position) {
				return piece;
			}
		}
		return null;
	}
	
	static ArrayList<Piece> pList = new ArrayList<Piece>();
	static Piece[] p;
	public static Piece[] GetPiecesOfColor(boolean color) {
		
		for(Piece piece : activePieces) {
			if(piece.isWhite == color) {
				pList.add(piece);
			}
		}
		p = pList.toArray(new Piece[pList.size()]);
		pList.removeAll(pList);
		return p;
	}
	
	static ArrayList<Piece> slidingPiecesList = new ArrayList<Piece>();
	static Piece[] slidingPieces;
	public static Piece[] GetSlidingPieces(int color) {
		
		for(Piece piece : activePieces) {
			if(piece.stone == Math.signum(color) * Chess.BLACK_QUEEN || piece.stone == Math.signum(color) * Chess.BLACK_BISHOP || piece.stone == Math.signum(color) * Chess.BLACK_ROOK) {
				slidingPiecesList.add(piece);
			}
		}
		
		slidingPieces = slidingPiecesList.toArray(new Piece[slidingPiecesList.size()]);
		slidingPiecesList.removeAll(slidingPiecesList);
		return slidingPieces;
	}
	
	static ArrayList<Piece> enemyPiecesOfTypeList = new ArrayList<Piece>();
	static Piece[] enemyPiecesOfType;
	public static Piece[] GetEnemyPiecesOfType(int stone) {
		for(Piece piece : activePieces) {
			if(piece.stone == -stone) {
				enemyPiecesOfTypeList.add(piece);
			}
		}
		enemyPiecesOfType = enemyPiecesOfTypeList.toArray(new Piece[enemyPiecesOfTypeList.size()]);
		enemyPiecesOfTypeList.removeAll(enemyPiecesOfTypeList);
		return enemyPiecesOfType;
	}
	
	static ArrayList<Piece> piecesOfTypeList = new ArrayList<Piece>();
	static Piece[] piecesOfType;
	public static Piece[] GetPiecesOfType(int stone) {
		for(Piece piece : activePieces) {
			if(Chess.stoneToPiece(piece.stone) == Chess.stoneToPiece(stone)) {
				piecesOfTypeList.add(piece);
			}
		}
		piecesOfType = piecesOfTypeList.toArray(new Piece[piecesOfTypeList.size()]);
		piecesOfTypeList.removeAll(piecesOfTypeList);
		return piecesOfType;
	}
	
	//for color you input the stone
	public static King GetKing(int color) {
		for(Piece piece : activePieces) {
			if(piece.stone == Math.signum(color) * Chess.BLACK_KING) {
				return (King) piece;
			}
		}
		return null;
	}
}


