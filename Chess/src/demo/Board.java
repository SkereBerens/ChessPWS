package demo;
import java.util.ArrayList;

import chesspresso.Chess;

public class Board {
	static ArrayList<Piece> pieces = new ArrayList<Piece>();
	
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
		for(Piece piece : pieces) {
			
			positionGrid[piece.position] = piece.stone;
		}
	}
	
	//FEN is een soort notatie voor een positie
	public String GetFEN() {
		String positionFen = "";
		int emptySpace = 0;
		String[] emptySpaces = {"", "1", "2", "3", "4", "5", "6", "7", "8"};
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
		
		return positionFen.substring(0, positionFen.length() - 1);
	}
	
	public static void addPiece(Piece p) {
		pieces.add(p);
	}
	
	public static void RemovePiece(int position) {
		Piece piece = FindPieceByPosition(position);
		if(piece != null) {
			piece.captured = true;
			pieces.remove(piece);
		}
	}
	
	public static Piece FindPieceByPosition(int position) {
		for(Piece piece : pieces) {
			if(piece.position == position) {
				return piece;
			}
		}
		return null;
	}
	
	public static Piece[] GetPiecesOfColor(boolean color) {
		ArrayList<Piece> p = new ArrayList<Piece>();
		for(Piece piece : pieces) {
			if(piece.isWhite == color) {
				p.add(piece);
			}
		}
		return p.toArray(new Piece[p.size()]);
	}
	
	public static Piece[] GetSlidingPieces(int color) {
		ArrayList<Piece> slidingPieces = new ArrayList<Piece>();
		for(Piece piece : pieces) {
			if(piece.stone == Math.signum(color) * Chess.BLACK_QUEEN || piece.stone == Math.signum(color) * Chess.BLACK_BISHOP || piece.stone == Math.signum(color) * Chess.BLACK_ROOK) {
				slidingPieces.add(piece);
			}
		}
		
		return slidingPieces.toArray(new Piece[slidingPieces.size()]);
	}
	
	//for color you input the stone
	public static Piece GetKing(int color) {
		for(Piece piece : pieces) {
			if(piece.stone == Math.signum(color) * Chess.BLACK_KING) {
				return piece;
			}
		}
		return null;
	}
}


