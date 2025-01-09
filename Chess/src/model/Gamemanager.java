package model;

import java.util.ArrayList;

import chesspresso_OpenSourceCode.Chess;

public class Gamemanager {
	static King blackKing;
	static King whiteKing;
	
	static public boolean IsCheckMate() {
		if(Piece.ply % 2 == 0) {
			return BlackMated();
		} else {
			return WhiteMated();
		}
	}
	
	public static boolean BlackMated() {
		blackKing = (King)(Board.GetKing(1));
		for(Piece blackpiece : Board.GetPiecesOfColor(false)) {
			if(blackpiece.GetLegalMoves().length != 0 || Piece.ply % 2 != 0) {
				return false;
			}
		}
		
		if(blackKing.isInCheck) {
			return true;
		} 
		return false;	
		
	}
	
	public static boolean WhiteMated() {
		whiteKing = (King)(Board.GetKing(-1));
		for(Piece whitepiece : Board.GetPiecesOfColor(true)) {
			if(whitepiece.GetLegalMoves().length != 0 || Piece.ply % 2 == 0) {
				return false;
			}	
		}
		
		if(whiteKing.isInCheck) {
			return true;
		}
		return false;	
		
	}
	
	public static ArrayList<String> positionsFen = new ArrayList<String>();
	static int equalPositions = 0;
	static String currentPositionFen;
	public static boolean ThreefoldRepetition() {
		currentPositionFen = Board.GetFENBasic();
		positionsFen.add(currentPositionFen);
		
		for(String fen : positionsFen) {
			if(fen.equals(currentPositionFen)) {
				equalPositions++;
			}
		}
		
		
		if(equalPositions >= 3) {
			equalPositions = 0;
			return true;
		}
		equalPositions = 0;
		return false;
	}
	
	public static boolean FiftyMoveRule() {
		if(Piece.movesTilDraw >= 100) {
			return true;
		}
		return false;
	}
	
	static ArrayList<Piece> whitePieces = new ArrayList<Piece>();
	static ArrayList<Piece> blackPieces = new ArrayList<Piece>();
	public static boolean InsufficientMaterial() {
		for(Piece whitePiece : Board.GetPiecesOfColor(true)) {
			whitePieces.add(whitePiece);
		}
		
		for(Piece blackPiece : Board.GetPiecesOfColor(false)) {
			blackPieces.add(blackPiece);
		}
		
		if(whitePieces.size() == 2) {
			for(Piece whitePiece : whitePieces) {
				if(whitePiece.stone == Chess.WHITE_KNIGHT || whitePiece.stone == Chess.WHITE_BISHOP) {
					return CheckInsufficientBlack();
				}
			}
		} else if(whitePieces.size() == 1) {
			return CheckInsufficientBlack();
		}
		blackPieces.removeAll(blackPieces);
		whitePieces.removeAll(whitePieces);
		return false;
		
	}
	
	static boolean CheckInsufficientBlack() {
		if(blackPieces.size() == 2) {
			for(Piece blackPiece : blackPieces) {
				if(blackPiece.stone == Chess.BLACK_KNIGHT || blackPiece.stone == Chess.BLACK_BISHOP) {
					blackPieces.removeAll(blackPieces);
					whitePieces.removeAll(whitePieces);
					return true;
				}
			}
		} else if(blackPieces.size() == 1) {
			return true;
		}
		return false;
	}
	
	public static int GetGameOutcome() {
		if(WhiteMated()) {
			return 1;
		}
		
		if(BlackMated()) {
			return -1;
		}
		
		if(FiftyMoveRule()) {
			return 0;
		}
		
		if(ThreefoldRepetition()) {
			return 0;
		}
		
		if(InsufficientMaterial()) {
			return 0;
		}
		
		return 100;
	}
}
