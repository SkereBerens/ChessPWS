package demo;

import java.util.ArrayList;

import chesspresso.Chess;

public class Queen extends Piece {
	Queen(int startingPos, boolean color) {
		position = startingPos;
		isWhite = color;
		directionIndices = queenDirIndices;
		if(isWhite) {
			stone = Chess.WHITE_QUEEN;
		} else {
			stone = Chess.BLACK_QUEEN;
		}
		
		Board.addPiece(this);
		//to update attacking squares
		GetMoveableSquares();
	}
}
