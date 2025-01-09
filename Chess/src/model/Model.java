package model;



import java.util.ArrayList;

import chesspresso_OpenSourceCode.Chess;

public class Model {
	public static ArrayList<Piece> pieces = new ArrayList<Piece>();
	public Piece bishop = new Bishop(Chess.C1, true);
	public Piece bishop1 = new Bishop(Chess.F1, true);
	public Piece bishop2 = new Bishop(Chess.C8, false);
	public Piece bishop3 = new Bishop(Chess.F8, false);
	public Piece knight = new Knight(Chess.B1, true);
	public Piece knight1 = new Knight(Chess.G1, true);
	public Piece knight2 = new Knight(Chess.B8, false);
	public Piece knight3 = new Knight(Chess.G8, false);
    public Piece rook = new Rook(Chess.A1, true);
	public Piece rook1 = new Rook(Chess.H1, true);
	public Piece rook2 = new Rook(Chess.A8, false);
	public Piece rook3 = new Rook(Chess.H8, false);
	public Piece pawn = new Pawn(Chess.H7, false);
	public Piece pawn1 = new Pawn(Chess.A2, true);
	public Piece pawn2 = new Pawn(Chess.B2, true);
	public Piece pawn3 = new Pawn(Chess.C2, true);
	public Piece pawn4 = new Pawn(Chess.D2, true);
	public Piece pawn5 = new Pawn(Chess.E2, true);
	public Piece pawn6 = new Pawn(Chess.F2, true);
	public Piece pawn7 = new Pawn(Chess.G2, true);
	public Piece pawn8 = new Pawn(Chess.H2, true);
	public Piece pawn9 = new Pawn(Chess.A7, false);
	public Piece pawn10 = new Pawn(Chess.B7, false);
	public Piece pawn11 = new Pawn(Chess.C7, false);
	public Piece pawn12 = new Pawn(Chess.D7, false);
	public Piece pawn13 = new Pawn(Chess.E7, false);
	public Piece pawn14 = new Pawn(Chess.F7, false);
	public Piece pawn15 = new Pawn(Chess.G7, false);
	public Piece queen = new Queen(Chess.D1, true);
	public Piece queen1 = new Queen(Chess.D8, false);
	
	public Piece queen2 = new Queen(-1, true);
	public Piece queen3 = new Queen(-1, false);
	public Piece queen4 = new Queen(-1, true);
	public Piece queen5 = new Queen(-1, false);
	public Piece queen6 = new Queen(-1, true);
	public Piece queen7 = new Queen(-1, false);
	public Piece queen8 = new Queen(-1, true);
	public Piece queen9 = new Queen(-1, false);
	public Piece queen10 = new Queen(-1, true);
	public Piece queen11 = new Queen(-1, false);
	public Piece queen12 = new Queen(-1, true);
	public Piece queen13 = new Queen(-1, false);
	public Piece queen14 = new Queen(-1, true);
	public Piece queen15 = new Queen(-1, false);
	public Piece queen16 = new Queen(-1, true);
	public Piece queen17 = new Queen(-1, false);
	
	public Piece rook4 = new Rook(-1, true);
	public Piece rook5 = new Rook(-1, false);
	public Piece rook6 = new Rook(-1, true);
	public Piece rook7 = new Rook(-1, false);
	public Piece rook8 = new Rook(-1, true);
	public Piece rook9 = new Rook(-1, false);
	public Piece rook10 = new Rook(-1, true);
	public Piece rook11 = new Rook(-1, false);
	public Piece rook12 = new Rook(-1, true);
	public Piece rook13 = new Rook(-1, false);
	public Piece rook14 = new Rook(-1, true);
	public Piece rook15 = new Rook(-1, false);
	public Piece rook16 = new Rook(-1, true);
	public Piece rook17 = new Rook(-1, false);
	public Piece rook18 = new Rook(-1, true);
	public Piece rook19 = new Rook(-1, false);
	
	public Piece bishop4 = new Bishop(-1, true);
	public Piece bishop5 = new Bishop(-1, false);
	public Piece bishop6 = new Bishop(-1, true);
	public Piece bishop7 = new Bishop(-1, false);
	public Piece bishop8 = new Bishop(-1, true);
	public Piece bishop9 = new Bishop(-1, false);
	public Piece bishop10 = new Bishop(-1, true);
	public Piece bishop11 = new Bishop(-1, false);
	public Piece bishop12 = new Bishop(-1, true);
	public Piece bishop13 = new Bishop(-1, false);
	public Piece bishop14 = new Bishop(-1, true);
	public Piece bishop15 = new Bishop(-1, false);
	public Piece bishop16 = new Bishop(-1, true);
	public Piece bishop17 = new Bishop(-1, false);
	public Piece bishop18 = new Bishop(-1, true);
	public Piece bishop19 = new Bishop(-1, false);
	
	public Piece knight4 = new Knight(-1, true);
	public Piece knight5 = new Knight(-1, false);
	public Piece knight6 = new Knight(-1, true);
	public Piece knight7 = new Knight(-1, false);
	public Piece knight8 = new Knight(-1, true);
	public Piece knight9 = new Knight(-1, false);
	public Piece knight10 = new Knight(-1, true);
	public Piece knight11 = new Knight(-1, false);
	public Piece knight12 = new Knight(-1, true);
	public Piece knight13 = new Knight(-1, false);
	public Piece knight14 = new Knight(-1, true);
	public Piece knight15 = new Knight(-1, false);
	public Piece knight16 = new Knight(-1, true);
	public Piece knight17 = new Knight(-1, false);
	public Piece knight18 = new Knight(-1, true);
	public Piece knight19 = new Knight(-1, false);
	
	
	public Model() {
		for(Piece piece : pieces) {
			if(piece.position != -1) {
				Board.addPiece(piece);
			}
		}
		Piece king = new King(Chess.E1, true);
		Piece king1 = new King(Chess.E8, false);
		Board.addPiece(king);
		Board.addPiece(king1);
		Board.updatePosition();
	}
}
