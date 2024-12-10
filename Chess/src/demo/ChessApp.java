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
	
	public static int dothethingbutbulk(int depht) throws IOException {
		int numPositiosnt = 0;
		Piece[] actifPices;
		actifPices = Board.activePieces.toArray(new Piece[Board.activePieces.size()]);
		int moves = 0;
		for(Piece p : actifPices) {
			for(int move : p.GetLegalMoves()) {
				moves++;
			}
		}
		
		
		if(depht == 1) {
			return moves;
		}
		
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
				numPositiosnt += dothethingbutbulk(depht - 1);
		
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
//		Board.LoadPosition("r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10 ");
		
//		System.out.println("CAPTURES: " + Board.captures);
//		System.out.println("CHECKS: " + King.checks);
//		System.out.println("ENPASSANTS: " + Pawn.enpasants);
		
//		System.out.println(dothethingbutbulk(2));

//		MovePieceModel(Chess.H3, Chess.G2);
//		System.out.println(Board.GetFENAdvanced());
		
//		Piece[] ac = Board.activePieces.toArray(new Piece[Board.activePieces.size()]);
//		int num = 0;
//		for(Piece p : ac) {
//			for(int move : p.GetLegalMoves()) {
//				num += testerytest(p.position, move, 5);
//			}
//		}
//		System.out.println(num);
//		
		
		
		
		//testerytest(Chess.B1, Chess.A3);
		
		//bad: c3b1 41
		
//		
//		testerytest(Chess.B1, Chess.C3);
		//testerytest(Chess.G1, Chess.F3);
//		
//		testerytest(Chess.G1, Chess.H3);
		

		
	}
}
