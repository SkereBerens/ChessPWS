package controller;
import model.*;
import view.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

import chesspresso_OpenSourceCode.Chess;

public class Controller {
	static Piece piece;
	public static Piece GetPieceOfPieceGUI(PieceGUI piecegui) {
		for(Piece piece : Board.activePieces) {
			if(piece.position == piecegui.position) {
				return piece;
			}
		}
		return null;
	}
	
	public static PieceGUI GetPieceGUIOfPiece(Piece piece) {
		for(PieceGUI piecegui : GUI.activePieces) {
			if(piece.position == piecegui.position) {
				return piecegui;
			}
		}
		return null;
	}
	
	public static void MovePiece(PieceGUI piecegui, int movePosition) throws IOException {
		piece = GetPieceOfPieceGUI(piecegui);
		piece.MoveTo(movePosition, false);
	}
	
	static PieceGUI piecegui;
	public static void MovePieceGUI(Piece piece, int movePosition) {
		piecegui = GetPieceGUIOfPiece(piece);
		piecegui.MoveTo(movePosition);
	}
	
	public static void CapturePiece(Piece capturedPiece) {
		GUI.CapturePiece(GetPieceGUIOfPiece(capturedPiece));
	}
	
	public static int[] GetLegalMoves(PieceGUI piecegui) {
		return GetPieceOfPieceGUI(piecegui).GetLegalMoves();
	}
	
	
	public static int ConvertStoneToIndex(int stone) {
		if(Math.signum(stone) < 0) {
			return (Math.abs(stone) -1);
		} else if(Math.signum(stone) > 0) {
			return (stone + 5);
		}
		else {
			return 0;
		}
	}
	
	public static void ToggleEngine(boolean on) {
		Piece.engineOn = on;
	}
	
	public static int ConvertCharToIndex(char ch) {
		for(int piece = 0; piece < Chess.pieceChars.length; piece++) {
			if(Chess.pieceChars[piece] == ch) {
				return (piece - 1);
			}
		}
		return 0;
	}
	
	public static void CastleShort(boolean isWhite) {
		if(isWhite) {
			GUI.findPieceByPosition(Chess.E1).MoveTo(Chess.G1);
			GUI.findPieceByPosition(Chess.H1).MoveTo(Chess.F1);
		} else {
			GUI.findPieceByPosition(Chess.E8).MoveTo(Chess.G8);
			GUI.findPieceByPosition(Chess.H8).MoveTo(Chess.F8);
		}
	}
	
	public static void CastleLong(boolean isWhite) {
		if(isWhite) {
			GUI.findPieceByPosition(Chess.E1).MoveTo(Chess.C1);
			GUI.findPieceByPosition(Chess.A1).MoveTo(Chess.D1);
		} else {
			GUI.findPieceByPosition(Chess.E8).MoveTo(Chess.C8);
			GUI.findPieceByPosition(Chess.A8).MoveTo(Chess.D8);
		}
	}
	
	static PieceGUI pawn;
	public static void Promote(int promotionPiece, Piece piece) throws IOException {
		pawn = GetPieceGUIOfPiece(piece);
		pawn.Promote(ConvertStoneToIndex(promotionPiece));
	}
	
	static ArrayList<PieceGUI> pieceguisUpdated = new ArrayList<PieceGUI>();
	public static void UpdateAllPieceGUIPosition() {
		for(PieceGUI piecegui : GUI.pieces) {
			GUI.CapturePiece(piecegui);
		}
		
		for(Piece piece : Board.activePieces) {
			for(PieceGUI piecegui : GUI.pieces) {
				if(!pieceguisUpdated.contains(piecegui) && piecegui.pieceIndex == ConvertStoneToIndex(piece.stone)) {
					pieceguisUpdated.add(piecegui);
					GUI.bigcenterPanel.add(piecegui, JLayeredPane.MODAL_LAYER);
					GUI.activePieces.add(piecegui);
					piecegui.MoveTo(piece.position);
					break;
				}
			
			}
		}
		
		
		pieceguisUpdated.removeAll(pieceguisUpdated);
	}
	

	public static void LoadPosition(String fen) {
		Board.LoadPosition(fen);
		
	}
}
