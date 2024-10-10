package ControllTest;
import demo.*;
import GUITest.*;
import chesspresso.Chess;

import java.io.IOException;

public class Controlla {
	
	public static Piece GetPieceOfPieceGUI(PieceGUI piecegui) {
		for(Piece piece : Board.pieces) {
			if(piece.position == piecegui.position) {
				return piece;
			}
		}
		return null;
	}
	
	public static int GetMoveSquare(PieceGUI piecegui, int movePosition) {
		Piece piece = GetPieceOfPieceGUI(piecegui);
		
		piece.MoveTo(movePosition);
		System.out.println(Chess.sqiToStr(piece.position));
		return piece.position;
	}
	
	public static int[] GetLegalMoves(PieceGUI piecegui) {
		return GetPieceOfPieceGUI(piecegui).GetLegalMoves();
		}
}
