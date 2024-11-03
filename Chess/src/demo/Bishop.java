package demo;
import java.util.Arrays;
import chesspresso.Chess;

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
		
		Model.pieces.add(this);
	}
}
