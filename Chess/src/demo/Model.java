package demo;



import chesspresso.Chess;

public class Model {
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
	public Piece king = new King(Chess.E1, true);
	public Piece king1 = new King(Chess.E8, false);
	public Model() {
		Board.updatePosition();
	}
}
