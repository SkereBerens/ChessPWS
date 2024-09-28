package demo;
import java.util.Arrays;

import chesspresso.*;

public class ChessApp {

	public static void main(String[] args) {
		// Chess is geimporteert van een opensource (chesspresso)
		//Site for putting in FEN
		//http://www.ee.unb.ca/cgi-bin/tervo/fen.pl?select=rnbqkbnr%2Fpp1ppppp%2F8%2F2p5%2F4P3%2F5N2%2FPPPP1PPP%2FRNBQKB1R+b+
		
		Board board = new Board();
		Piece rook = new Rook(Chess.H1, true);
		Piece bishop = new Bishop(Chess.C4, false);
		Piece king = new King(Chess.E1, true);
		Piece king1 = new King(Chess.A8, false);
		Board.updatePosition();
		System.out.println(board.GetFEN());
		bishop.MoveTo(Chess.B3);
		
		((King)king).CastleShort();
		Board.updatePosition();
		System.out.println(board.GetFEN());
	}
}
