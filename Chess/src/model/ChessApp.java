package model;
import java.io.IOException;
import engine.*;
import view.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import chesspresso_OpenSourceCode.*;
import controller.*;

public class ChessApp {
	
	static int Perft(int depth) throws Exception {
		int numPositions = 0;
		if(depth == 0) {
			return 1;
		}
		Piece[] activePieces;
		activePieces = Board.activePieces.toArray(new Piece[Board.activePieces.size()]);
		
		for(Piece p : activePieces) {
			for(int legalMove : p.GetLegalMoves()) {
				String previousPositionFENAddOn;
				int previousPosition;
				Piece capturedPiece;
				
				p.MoveTo(legalMove, true);
				previousPosition = p.previousPosition;
				int previousPositionRookLong = -1; 
				int previousPositionRookShort = -1;
				
				if(Chess.stoneToPiece(p.stone) == 6) {
					previousPositionRookLong = ((King) p).rookLong.previousPosition; 
					previousPositionRookShort = ((King) p).rookShort.previousPosition; 
				}
				previousPositionFENAddOn = p.previousPositionFENAddOn;
				capturedPiece = p.capturedPiece;
				
				numPositions += Perft(depth - 1);

				p.RevertMove(previousPosition , capturedPiece , previousPositionFENAddOn, previousPositionRookLong, previousPositionRookShort);
				
			}
		}	
			
		
		return numPositions;
	}
	
	
	public static int PerftBulk(int depth) throws IOException, InterruptedException {
		int numPositions = 0;
		Piece[] activePieces;
		activePieces = Board.activePieces.toArray(new Piece[Board.activePieces.size()]);
		int moves = 0;
		for(Piece p : activePieces) {
			for(int move : p.GetLegalMoves()) {
				moves++;
			}
		}
		
		
		if(depth == 1) {
			return moves;
		}
		
		for(Piece p : activePieces) {
			for(int legalMove : p.GetLegalMoves()) {
				String previousPositionFENAddOn;
				int previousPosition;
				Piece capturedPiece;
				
				p.MoveTo(legalMove, true);
				previousPosition = p.previousPosition;
				int previousPositionRookLong = -1; 
				int previousPositionRookShort = -1;
				
				if(Chess.stoneToPiece(p.stone) == 6) {
					previousPositionRookLong = ((King) p).rookLong.previousPosition; 
					previousPositionRookShort = ((King) p).rookShort.previousPosition; 
				}
				previousPositionFENAddOn = p.previousPositionFENAddOn;
				capturedPiece = p.capturedPiece;
				numPositions += PerftBulk(depth - 1);
		
				p.RevertMove(previousPosition , capturedPiece , previousPositionFENAddOn, previousPositionRookLong, previousPositionRookShort);
			}
		}	
		
		return numPositions;
	}
	
	
	//displays number of positions after a certain move in current position
	//used for debugging better with stockfish
	static int TestNumPositions(int position, int move, int depth) throws Exception {
		int numPositions = 0;
		Piece p;
		p = Board.FindPieceByPosition(position);
		
		p.MoveTo(move, true);
		
		int previousPosition = p.previousPosition;
		
		int previousPositionRookLong = -1; 
		int previousPositionRookShort = -1;
		
		if(Chess.stoneToPiece(p.stone) == 6) {
			previousPositionRookLong = ((King) p).rookLong.previousPosition; 
			previousPositionRookShort = ((King) p).rookShort.previousPosition; 
		}
		String previousPositionFENAddOn = p.previousPositionFENAddOn;
		Piece capturedPiece = p.capturedPiece;
		numPositions += Perft(depth -1);
		
		
		p.RevertMove(previousPosition, capturedPiece , previousPositionFENAddOn, previousPositionRookLong, previousPositionRookShort);
		
		System.out.println(" " + Chess.sqiToStr(position) + Chess.sqiToStr(move) + ": " + numPositions);
		
		System.out.println(Board.GetFENAdvanced());
		return numPositions;
	}
	
	
	static void MovePieceModel(int position, int move) throws IOException, InterruptedException {
		for(Piece p : Board.activePieces) {
			if(p.position == position) {
				p.MoveTo(move, true);
				break;
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		 //Chess is geimporteert van een opensource (chesspresso)
		//Site for putting in FEN
		//http://www.ee.unb.ca/cgi-bin/tervo/fen.pl?select=rnbqkbnr%2Fpp1ppppp%2F8%2F2p5%2F4P3%2F5N2%2FPPPP1PPP%2FRNBQKB1R+b+
		GUI gui = new GUI();
		Model model = new Model();
		//Board.LoadPosition("8/8/8/2k5/K6p/8/8/1q6 w - - 15 75");
		
		System.out.println("Started");
	

		for(Piece p : Board.activePieces) {
			p.previousPosition = p.position;
		}
		
	}
}
