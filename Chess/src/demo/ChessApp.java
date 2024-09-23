package demo;
import java.util.Arrays;

import chesspresso.*;

public class ChessApp {

	public static void main(String[] args) {
		// Chess is geimporteert van een opensource (chesspresso)
		//Site for putting in FEN
		//http://www.ee.unb.ca/cgi-bin/tervo/fen.pl?select=rnbqkbnr%2Fpp1ppppp%2F8%2F2p5%2F4P3%2F5N2%2FPPPP1PPP%2FRNBQKB1R+b+
		
		Board board = new Board();
		Piece rook = new Rook(Chess.E1, true);
		Piece queen = new Queen(Chess.G6, false);
		Piece pawn = new Pawn(Chess.B3, false);
		Piece king = new King(Chess.C8, true);
		Piece king1 = new King(Chess.A8, false);
		Piece knight = new Knight(Chess.A1, true);
		
		Board.updatePosition();
		System.out.println(board.GetFEN());
		((King) Board.GetKing(1)).isInCheck();
		queen.MoveTo(Chess.G4);
		
		Board.updatePosition();
		System.out.println(board.GetFEN());
		System.out.println(((King) king).isInCheck);
		rook.MoveTo(Chess.E6);
		Board.updatePosition();
		System.out.println(board.GetFEN());
		pawn.MoveTo(Chess.B2);
		
		Board.updatePosition();
		System.out.println(board.GetFEN());
		rook.MoveTo(Chess.E8);
		Board.updatePosition();
		System.out.println(board.GetFEN());
	}
}
