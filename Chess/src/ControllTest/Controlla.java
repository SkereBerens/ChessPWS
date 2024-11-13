package ControllTest;
import demo.*;
import GUITest.*;
import chesspresso.Chess;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

public class Controlla {
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
		for(PieceGUI piecegui : Guihihi.activePieces) {
			if(piece.position == piecegui.position) {
				return piecegui;
			}
		}
		return null;
	}
	
	public static void MovePiece(PieceGUI piecegui, int movePosition) throws IOException {
		piece = GetPieceOfPieceGUI(piecegui);
		piece.MoveTo(movePosition);
	}
	
	static PieceGUI piecegui;
	public static void MovePieceGUI(Piece piece, int movePosition) {
		piecegui = GetPieceGUIOfPiece(piece);
		piecegui.MoveTo(movePosition);
	}
	
	public static void CapturePiece(Piece capturedPiece) {
		Guihihi.CapturePiece(GetPieceGUIOfPiece(capturedPiece));
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
			Guihihi.findPieceByPosition(Chess.E1).MoveTo(Chess.G1);
			Guihihi.findPieceByPosition(Chess.H1).MoveTo(Chess.F1);
		} else {
			Guihihi.findPieceByPosition(Chess.E8).MoveTo(Chess.G8);
			Guihihi.findPieceByPosition(Chess.H8).MoveTo(Chess.F8);
		}
	}
	
	public static void CastleLong(boolean isWhite) {
		if(isWhite) {
			Guihihi.findPieceByPosition(Chess.E1).MoveTo(Chess.C1);
			Guihihi.findPieceByPosition(Chess.A1).MoveTo(Chess.D1);
		} else {
			Guihihi.findPieceByPosition(Chess.E8).MoveTo(Chess.C8);
			Guihihi.findPieceByPosition(Chess.A8).MoveTo(Chess.D8);
		}
	}
	
	static PieceGUI pawn;
	public static void Promote(int promotionPiece, Piece piece) throws IOException {
		pawn = GetPieceGUIOfPiece(piece);
		pawn.Promote(ConvertStoneToIndex(promotionPiece));
	}
	
	static ArrayList<PieceGUI> pieceguisUpdated = new ArrayList<PieceGUI>();
	public static void UpdateAllPieceGUIPosition() {
		for(PieceGUI piecegui : Guihihi.pieces) {
			Guihihi.CapturePiece(piecegui);
		}
		
		for(Piece piece : Board.activePieces) {
			for(PieceGUI piecegui : Guihihi.pieces) {
				if(!pieceguisUpdated.contains(piecegui) && piecegui.pieceIndex == ConvertStoneToIndex(piece.stone)) {
					pieceguisUpdated.add(piecegui);
					Guihihi.bigcenterPanel.add(piecegui, JLayeredPane.MODAL_LAYER);
					Guihihi.activePieces.add(piecegui);
					piecegui.MoveTo(piece.position);
					break;
				}
			
			}
		}
		
		
		pieceguisUpdated.removeAll(pieceguisUpdated);
	}
	
//	public static void UndoMove() {
//		Piece.RevertMove();
//	}

	public static void LoadPosition(String fen) {
		Board.LoadPosition(fen);
		
	}
}
