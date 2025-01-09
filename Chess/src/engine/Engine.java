package engine;

import model.*;

import java.io.IOException;

import chesspresso_OpenSourceCode.Chess;

public class Engine {
	
	//square tables from https://www.chessprogramming.org/Simplified_Evaluation_Function
	
	//BLACK
	//
	//
	//
	//
	//
	
	static int [] kingMidSquareTableBlack = {-30,-40,-40,-50,-50,-40,-40,-30,
									 -30,-40,-40,-50,-50,-40,-40,-30,
									 -30,-40,-40,-50,-50,-40,-40,-30,
									 -30,-40,-40,-50,-50,-40,-40,-30,
									 -20,-30,-30,-40,-40,-30,-30,-20,
									 -10,-20,-20,-20,-20,-20,-20,-10,
									  20, 20,  0,  0,  0,  0, 20, 20,
									  20, 30, 10,  0,  0, 10, 30, 20};
	
	static int[] kingEndSquaretableBlack =  {-50,-40,-30,-20,-20,-30,-40,-50,
									  -30,-20,-10,  0,  0,-10,-20,-30,
									  -30,-10, 20, 30, 30, 20,-10,-30,
									  -30,-10, 30, 40, 40, 30,-10,-30,
									  -30,-10, 30, 40, 40, 30,-10,-30,
									  -30,-10, 20, 30, 30, 20,-10,-30,
									  -30,-30,  0,  0,  0,  0,-30,-30,
									  -50,-30,-30,-30,-30,-30,-30,-50}; 
	
	static int[] pawnSquareTableBlack = {0,  0,  0,  0,  0,  0,  0,  0,
								  50, 50, 50, 50, 50, 50, 50, 50,
								  10, 10, 20, 30, 30, 20, 10, 10,
								  5,  5, 10, 25, 25, 10,  5,  5,
								  0,  0,  0, 20, 20,  0,  0,  0,
								  5, -5,-10,  0,  0,-10, -5,  5,
								  5, 10, 10,-20,-20, 10, 10,  5,
								  0,  0,  0,  0,  0,  0,  0,  0};
	
	static int[] knightSquareTableBlack = {-50,-40,-30,-30,-30,-30,-40,-50,
									-40,-20,  0,  0,  0,  0,-20,-40,
									-30,  0, 10, 15, 15, 10,  0,-30,
									-30,  5, 15, 20, 20, 15,  5,-30,
									-30,  0, 15, 20, 20, 15,  0,-30,
									-30,  5, 10, 15, 15, 10,  5,-30,
									-40,-20,  0,  5,  5,  0,-20,-40,
									-50,-40,-30,-30,-30,-30,-40,-50};
	
	static int[] bishopSquareTableBlack = {-20,-10,-10,-10,-10,-10,-10,-20,
							  		-10,  0,  0,  0,  0,  0,  0,-10,
							  		-10,  0,  5, 10, 10,  5,  0,-10,
							  		-10,  5,  5, 10, 10,  5,  5,-10,
							  		-10,  0, 10, 10, 10, 10,  0,-10,
							  		-10, 10, 10, 10, 10, 10, 10,-10,
							  		-10,  5,  0,  0,  0,  0,  5,-10,
							  		-20,-10,-10,-10,-10,-10,-10,-20};
	
	static int[] rookSquareTableBlack = { 0,  0,  0,  0,  0,  0,  0,  0,
							  	   5, 10, 10, 10, 10, 10, 10,  5,
							  	   -5,  0,  0,  0,  0,  0,  0, -5,
							  	   -5,  0,  0,  0,  0,  0,  0, -5,
							  	   -5,  0,  0,  0,  0,  0,  0, -5,
							  	   -5,  0,  0,  0,  0,  0,  0, -5,
							  	   -5,  0,  0,  0,  0,  0,  0, -5,
							  	    0,  0,  0,  5,  5,  0,  0,  0};
	
	static int[] queenSquareTableBlack = {-20,-10,-10, -5, -5,-10,-10,-20,
								   -10,  0,  0,  0,  0,  0,  0, -10,
								   -10,  0,  5,  5,  5,  5,  0, -10,
								   -5,  0,  5,  5,  5,  5,  0, -5,
								    0,  0,  5,  5,  5,  5,  0, -5,
								   -10,  5,  5,  5,  5,  5,  0,-10,
								   -10,  0,  5,  0,  0,  0,  0,-10,
								   -20,-10,-10, -5, -5,-10,-10,-20};
	//WHITE
	//
	//
	//
	//
	
	static int [] kingMidSquareTableWhite = {20, 30, 10,  0,  0, 10, 30, 20,
									  20, 20,  0,  0,  0,  0, 20, 20,
									 -10,-20,-20,-20,-20,-20,-20,-10,
									 -20,-30,-30,-40,-40,-30,-30,-20,
									 -30,-40,-40,-50,-50,-40,-40,-30,
									 -30,-40,-40,-50,-50,-40,-40,-30,
									 -30,-40,-40,-50,-50,-40,-40,-30,
									 -30,-40,-40,-50,-50,-40,-40,-30};
	
	static int[] kingEndSquaretableWhite =  {-50,-30,-30,-30,-30,-30,-30,-50,
									  -30,-30,  0,  0,  0,  0,-30,-30,
									  -30,-10, 20, 30, 30, 20,-10,-30,
									  -30,-10, 30, 40, 40, 30,-10,-30,
									  -30,-10, 30, 40, 40, 30,-10,-30,
									  -30,-10, 20, 30, 30, 20,-10,-30,
									  -30,-20,-10,  0,  0,-10,-20,-30,
									  -50,-40,-30,-20,-20,-30,-40,-50}; 
	
	static int[] pawnSquareTableWhite = {0,  0,  0,  0,  0,  0,  0,  0,
			 					  5, 10, 10,-20,-20, 10, 10,  5,
			 					  5, -5,-10,  0,  0,-10, -5,  5,
			 					  0,  0,  0, 20, 20,  0,  0,  0,
			 					  5,  5, 10, 25, 25, 10,  5,  5,
			 					  10, 10, 20, 30, 30, 20, 10, 10,
			 					  50, 50, 50, 50, 50, 50, 50, 50,
			 					  0,  0,  0,  0,  0,  0,  0,  0};
	
	static int[] knightSquareTableWhite = {-50,-40,-30,-30,-30,-30,-40,-50,
									-40,-20,  0,  5,  5,  0,-20,-40,
									-30,  5, 10, 15, 15, 10,  5,-30,
									-30,  0, 15, 20, 20, 15,  0,-30,
									-30,  5, 15, 20, 20, 15,  5,-30,
									-30,  0, 10, 15, 15, 10,  0,-30,
									-40,-20,  0,  0,  0,  0,-20,-40,
									-50,-40,-30,-30,-30,-30,-40,-50};
	
	static int[] bishopSquareTableWhite = {-20,-10,-10,-10,-10,-10,-10,-20,
									-10,  5,  0,  0,  0,  0,  5,-10,
									-10, 10, 10, 10, 10, 10, 10,-10,
									-10,  0, 10, 10, 10, 10,  0,-10,
									-10,  5,  5, 10, 10,  5,  5,-10,
									-10,  0,  5, 10, 10,  5,  0,-10,
									-10,  0,  0,  0,  0,  0,  0,-10,
									-20,-10,-10,-10,-10,-10,-10,-20};
	
	static int[] rookSquareTableWhite = { 0,  0,  0,  0,  0,  0,  0,  0,
								  -5,  0,  0,  0,  0,  0,  0, -5,
								  -5,  0,  0,  0,  0,  0,  0, -5,
								  -5,  0,  0,  0,  0,  0,  0, -5,
								  -5,  0,  0,  0,  0,  0,  0, -5,
								  -5,  0,  0,  0,  0,  0,  0, -5,
								   5, 10, 10, 10, 10, 10, 10,  5,
								   0,  0,  0,  5,  5,  0,  0,  0};
	
	static int[] queenSquareTableWhite = {-20,-10,-10, -5, -5,-10,-10,-20,
								   -10,  0,  5,  0,  0,  0,  0,-10,
								   -10,  5,  5,  5,  5,  5,  0,-10,
								   	0,  0,  5,  5,  5,  5,  0, -5,
								   -5,  0,  5,  5,  5,  5,  0, -5,
								   -10,  0,  5,  5,  5,  5,  0, -10,
								   -10,  0,  0,  0,  0,  0,  0, -10,
								   -20,-10,-10, -5, -5,-10,-10,-20};
	
	
	static int[][] squareTablesWhite = {knightSquareTableWhite, bishopSquareTableWhite, rookSquareTableWhite, queenSquareTableWhite, pawnSquareTableWhite, kingMidSquareTableWhite, kingEndSquaretableWhite};
	static int[][] squareTablesBlack = {knightSquareTableBlack, bishopSquareTableBlack, rookSquareTableBlack, queenSquareTableBlack, pawnSquareTableBlack, kingMidSquareTableBlack, kingEndSquaretableBlack};
	//piecevalues[0] is niks
	static int[] PieceValues = {-690000, 320, 330, 500, 900, 100, 0};
	static public int Evaluate() {
		int eval = 0;
		int boardState = 0;
		
		if(Gamemanager.BlackMated()) {
			return 100000000;
		}
		
		if(Gamemanager.WhiteMated()) {
			return -100000000;
		}
		
		if(isEndGame()) {
			boardState = 1;
		}
		
		for(Piece piece : Board.activePieces) {
			if(piece.isWhite) {
				if(Chess.stoneToPiece(piece.stone) == Chess.KING) {
					eval += PieceValues[Chess.stoneToPiece(piece.stone)] + squareTablesWhite[Chess.stoneToPiece(piece.stone) + boardState - 1][piece.position];
				} else {
					eval += PieceValues[Chess.stoneToPiece(piece.stone)] + squareTablesWhite[Chess.stoneToPiece(piece.stone) - 1][piece.position];
				}
				
			} else {
				if(Chess.stoneToPiece(piece.stone) == Chess.KING) {
					eval += -(PieceValues[Chess.stoneToPiece(piece.stone)] + squareTablesBlack[Chess.stoneToPiece(piece.stone) + boardState - 1][piece.position]);
				} else {
					eval += -(PieceValues[Chess.stoneToPiece(piece.stone)] + squareTablesBlack[Chess.stoneToPiece(piece.stone) - 1][piece.position]);
				}
			}
			
		}
		
		
		return eval;
	}
	
	static boolean isEndGame() {
		int pieceCountBlack = 0;
		int pieceCountWhite = 0;
		
		for(Piece piece : Board.GetPiecesOfColor(false)) {
			if(Chess.stoneToPiece(piece.stone) != Chess.PAWN && Chess.stoneToPiece(piece.stone) != Chess.KING) {
				pieceCountBlack++;
			}
		}
		
		for(Piece piece : Board.GetPiecesOfColor(true)) {
			if(Chess.stoneToPiece(piece.stone) != Chess.PAWN && Chess.stoneToPiece(piece.stone) != Chess.KING) {
				pieceCountWhite++;
			}
		}
		
		if(pieceCountWhite > 3 && pieceCountBlack > 3) {
			return false;
		}
			
		return true;
			
		
	}
	
	
	
	static public int[] minimax(int depth, boolean isWhiteTurn, int alpha, int beta) throws IOException {
		int max = -2000000000;
		
		int min = 2000000000;
		
		int curEval;
		int[] bestMove = {-2, -2};
		
		if(depth == 0 || Gamemanager.GetGameOutcome() < 2) {
			return new int[]{Evaluate(), 0, 0};
		}
		
		Piece[] activePieces;
		activePieces = Board.activePieces.toArray(new Piece[Board.activePieces.size()]);
		
		if(isWhiteTurn) {
			
			for(Piece p : Board.GetPiecesOfColor(isWhiteTurn)) {
				for(int legalMove : p.GetLegalMoves()) {
					String previousPositionFENAddOn;
					int previousPosition;
					Piece capturedPiece;
					int[] positionAndMove = {p.position, legalMove};
					p.MoveTo(legalMove, true);
					int previousPositionRookLong = -1; 
					int previousPositionRookShort = -1;
					if(p.stone == Chess.WHITE_KING) {
						previousPositionRookLong = ((King) p).rookLong.previousPosition; 
						previousPositionRookShort = ((King) p).rookShort.previousPosition; 
					}
					previousPosition = p.previousPosition;
					
					
					
					previousPositionFENAddOn = p.previousPositionFENAddOn;
					capturedPiece = p.capturedPiece;
					
					
					curEval = minimax(depth - 1, false, alpha, beta)[0];
					
					if(curEval > max) {
						bestMove = positionAndMove;
						max = curEval;
					}
					
					if(curEval > alpha) {
						alpha = curEval;
					}
					
					p.RevertMove(previousPosition , capturedPiece , previousPositionFENAddOn, previousPositionRookLong, previousPositionRookShort);
					
					if(curEval >= beta) {
						return new int[] {curEval , 0, 0};
					}
				}
			}
			
			return new int[] {max, bestMove[0], bestMove[1]};
		} else {
			
			
			for(Piece p : Board.GetPiecesOfColor(isWhiteTurn)) {
				
				for(int legalMove : p.GetLegalMoves()) {
					String previousPositionFENAddOn;
					int previousPosition;
					Piece capturedPiece;
					int[] positionAndMove = {p.position, legalMove};
					
					p.MoveTo(legalMove, true);
					
					int previousPositionRookLong = -1; 
					int previousPositionRookShort = -1;
					if(p.stone == Chess.BLACK_KING) {
						previousPositionRookLong = ((King) p).rookLong.previousPosition; 
						previousPositionRookShort = ((King) p).rookShort.previousPosition; 
					}
					previousPosition = p.previousPosition;
					
					previousPositionFENAddOn = p.previousPositionFENAddOn;
					capturedPiece = p.capturedPiece;
					curEval = minimax(depth - 1, true, alpha, beta)[0];
					if(curEval < min) {
						bestMove = positionAndMove;
						min = curEval;
					}
					
					if(curEval < beta) {
						beta = curEval;
					}
					
					p.RevertMove(previousPosition , capturedPiece , previousPositionFENAddOn, previousPositionRookLong, previousPositionRookShort);
					
					if(curEval <= alpha) {
						return new int[] {curEval , 0, 0};
					}
				}
			}
			return new int[] {min, bestMove[0], bestMove[1]};
		}
		
	}
	
	
	//first is orig pos, then move pos
	static public int[] GetBestMove() throws IOException {
		boolean isWhiteTurn;
		
		if(Piece.ply %2 != 0) isWhiteTurn = true; else isWhiteTurn = false;
		
		return minimax(3, isWhiteTurn, -999999999, 999999999);
	}
	
}
