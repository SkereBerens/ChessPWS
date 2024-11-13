package demo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import ControllTest.*;
import GUITest.*;
import java.util.concurrent.TimeUnit;
import chesspresso.*;

public class ChessApp {
	
	static int dothetjngytest(int depht) throws Exception {
		int numPositiosnt = 0;
		if(depht == 0) {
			return 1;
		}
		Piece[] actifPices;
		actifPices = Board.activePieces.toArray(new Piece[Board.activePieces.size()]);
		

		
		for(Piece p : actifPices) {
			for(int legalMove : p.GetLegalMoves()) {
				String previousPositionFENAddOn;
				int previousPosition;
				Piece capturedPiece;
				//Board.FindPieceByPosition(p.position).MoveTo(legalMove);
				//System.out.println(Board.GetFENAdvanced() + " BEFO");
				
				p.MoveTo(legalMove);
				//System.out.println(Board.GetFENAdvanced() + " AFTA");
				previousPosition = p.previousPosition;
				int previousPositionRookLong = -1; 
				int previousPositionRookShort = -1;
				
				if(Chess.stoneToPiece(p.stone) == 6) {
					previousPositionRookLong = ((King) p).rookLong.previousPosition; 
					previousPositionRookShort = ((King) p).rookShort.previousPosition; 
				}
				previousPositionFENAddOn = p.previousPositionFENAddOn;
				capturedPiece = p.capturedPiece;
				//System.out.println(Board.GetFENAdvanced());
				//TimeUnit.SECONDS.sleep(1);
				numPositiosnt += dothetjngytest(depht - 1);
		
//				System.out.println(Board.GetPositionGrid()[Chess.A3]);
				p.RevertMove(previousPosition , capturedPiece , previousPositionFENAddOn, previousPositionRookLong, previousPositionRookShort);
			}
		}	
			
		
		return numPositiosnt;
	}
	
	
	static int testerytest(int position, int move, int depth) throws Exception {
		int numPositiosnt = 0;
		Piece p;
		p = Board.FindPieceByPosition(position);
		Board.OrderActivePieces();
		
		p.MoveTo(move);
		
		int previousPosition = p.previousPosition;
		
		int previousPositionRookLong = -1; 
		int previousPositionRookShort = -1;
		
		if(Chess.stoneToPiece(p.stone) == 6) {
			previousPositionRookLong = ((King) p).rookLong.previousPosition; 
			previousPositionRookShort = ((King) p).rookShort.previousPosition; 
		}
		String previousPositionFENAddOn = p.previousPositionFENAddOn;
		Piece capturedPiece = p.capturedPiece;
		numPositiosnt += dothetjngytest(depth -1);
		
		p.RevertMove(previousPosition, capturedPiece , previousPositionFENAddOn, previousPositionRookLong, previousPositionRookShort);
		System.out.println(" " + Chess.sqiToStr(position) + Chess.sqiToStr(move) + ": " + numPositiosnt);
		
		return numPositiosnt;
	}
	
	static void MovePieceModel(int position, int move) throws IOException {
		for(Piece p : Board.activePieces) {
			if(p.position == position) {
				p.MoveTo(move);
				break;
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		 //Chess is geimporteert van een opensource (chesspresso)
		//Site for putting in FEN
		//http://www.ee.unb.ca/cgi-bin/tervo/fen.pl?select=rnbqkbnr%2Fpp1ppppp%2F8%2F2p5%2F4P3%2F5N2%2FPPPP1PPP%2FRNBQKB1R+b+
		Guihihi gui = new Guihihi();
		Model model = new Model();
		Board.LoadPosition("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1");
		//System.out.println(dothetjngytest(1));
//		System.out.println("CAPTURES: " + Board.captures);
//		System.out.println("CHECKS: " + King.checks);
//		System.out.println("ENPASSANTS: " + Pawn.enpasants);
		
		
//
//		MovePieceModel(Chess.F2, Chess.F4);
//		MovePieceModel(Chess.D7, Chess.D5);
//		MovePieceModel(Chess.E1, Chess.F2);
//		MovePieceModel(Chess.D5, Chess.D4);
//		System.out.println(Board.GetFENAdvanced());
		
//		
		
//		for(int i : Board.FindPieceByPosition(Chess.H1).GetLegalMoves()) {
//			System.out.println(Chess.sqiToStr(i));
//		}
		
		Piece[] ac = Board.activePieces.toArray(new Piece[Board.activePieces.size()]);
		int num = 0;
		for(Piece p : ac) {
			for(int move : p.GetLegalMoves()) {
				num += testerytest(p.position, move, 3);
			}
		}
		System.out.println(num);
		
		
		
		
		//testerytest(Chess.B1, Chess.A3);
		
		//bad: d2d4, d2d3, e2e4, e2e3, f2f4, f2f3, 
		//worse: d7d5 1, e7e5 2, g7g5 1,
		// worst: e1, f1
		
//		
//		testerytest(Chess.B1, Chess.C3);
		//testerytest(Chess.G1, Chess.F3);
//		
//		testerytest(Chess.G1, Chess.H3);
		
		
//		System.out.println("CAPTURES: " + Piece.captures);
//		System.out.println("CHECKS: " + King.checks);
		
		
		
		
		
		
		
		
		
		
//		gui.MovePiece(Chess.E4,gui.findPieceByPosition(Chess.E2));
//		gui.MovePiece(Chess.F5,gui.findPieceByPosition(Chess.F7));
//		gui.MovePiece(Chess.F5,gui.findPieceByPosition(Chess.E4));
//		gui.MovePiece(Chess.D6,gui.findPieceByPosition(Chess.D7));
//		gui.MovePiece(Chess.F6,gui.findPieceByPosition(Chess.F5));
//		gui.MovePiece(Chess.D7,gui.findPieceByPosition(Chess.E8));
//		gui.MovePiece(Chess.F7,gui.findPieceByPosition(Chess.F6));
//		gui.MovePiece(Chess.A6,gui.findPieceByPosition(Chess.A7));
		
//		gui.MovePiece(Chess.E4,gui.findPieceByPosition(Chess.E2));
//		gui.MovePiece(Chess.D5,gui.findPieceByPosition(Chess.D7));
//		gui.MovePiece(Chess.F3,gui.findPieceByPosition(Chess.G1));
//		gui.MovePiece(Chess.D6,gui.findPieceByPosition(Chess.D7));
//		gui.MovePiece(Chess.C6,gui.findPieceByPosition(Chess.B8));
//		gui.MovePiece(Chess.B5,gui.findPieceByPosition(Chess.F1));
//		gui.MovePiece(Chess.F5,gui.findPieceByPosition(Chess.C8));
		
//		gui.MovePiece(Chess.E4,gui.findPieceByPosition(Chess.E2));
//		gui.MovePiece(Chess.D5,gui.findPieceByPosition(Chess.D7));
//		gui.MovePiece(Chess.D5,gui.findPieceByPosition(Chess.E4));
//		gui.MovePiece(Chess.E5,gui.findPieceByPosition(Chess.E7));
		
		
//		gui.MovePiece(Chess.E4,Guihihi.findPieceByPosition(Chess.E2));
//		gui.MovePiece(Chess.E5,Guihihi.findPieceByPosition(Chess.E7));
//		
//		gui.MovePiece(Chess.E2,Guihihi.findPieceByPosition(Chess.E1));
//		gui.MovePiece(Chess.E7,Guihihi.findPieceByPosition(Chess.E8));
//		
//		gui.MovePiece(Chess.E1,Guihihi.findPieceByPosition(Chess.E2));
//		gui.MovePiece(Chess.E8,Guihihi.findPieceByPosition(Chess.E7));
		
		
//		gui.MovePiece(Chess.E4,gui.findPieceByPosition(Chess.E2));
//		gui.MovePiece(Chess.F5,gui.findPieceByPosition(Chess.F7));
//		gui.MovePiece(Chess.F5,gui.findPieceByPosition(Chess.E4));
//		gui.MovePiece(Chess.G5,gui.findPieceByPosition(Chess.G7));
//		gui.MovePiece(Chess.G6,gui.findPieceByPosition(Chess.F5));
//		gui.MovePiece(Chess.A5,gui.findPieceByPosition(Chess.A7));
//		gui.MovePiece(Chess.H7,gui.findPieceByPosition(Chess.G6));
//		gui.MovePiece(Chess.A4,gui.findPieceByPosition(Chess.A5));
		
	}
}
