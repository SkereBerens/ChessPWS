package demo;

import java.util.ArrayList;

import chesspresso.Chess;

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
		System.out.println("Stupid jigha white stalemated him lmao");
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
		System.out.println("Stupid jigha black stalemated him lmao");
		return false;	
		
	}
	
	static ArrayList<String> positionsFen = new ArrayList<String>();
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
		
		System.out.println(equalPositions + " Repetitions");
		System.out.println("/////////////////////////");
		
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
	
	public static void GetGameOutcome() {
		if(WhiteMated()) {
			System.out.println("White lost lmao");
		}
		
		if(BlackMated()) {
			System.out.println("Black lost lmao");
		}
		
		if(FiftyMoveRule()) {
			System.out.println("Draw by fifty move rule");
		}
		
		if(ThreefoldRepetition()) {
			System.out.println("Draw by repetition");
		}
		
		if(InsufficientMaterial()) {
			System.out.println("Draw by insufficient material");
		}
	}
}
