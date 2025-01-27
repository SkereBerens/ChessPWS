package model;
import chesspresso_OpenSourceCode.Chess;

public class Bishop extends Piece {
	public Bishop(int startingPos, boolean color) {
		position = startingPos;
		isWhite = color;
		directionIndices = bishopDirIndices;
		if(isWhite) {
			stone = Chess.WHITE_BISHOP;
		} else {
			stone = Chess.BLACK_BISHOP;
		}
		
		if(position == -1) {
			captured = true;
		}
		
		Model.pieces.add(this);
	}
}
