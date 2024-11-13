package demo;

import java.util.ArrayList;

import chesspresso.Chess;

public class Rook extends Piece{
	Rook(int startingPos, boolean color) {
		position = startingPos;
		isWhite = color;
		directionIndices = rookDirIndices;
		if(isWhite) {
			stone = Chess.WHITE_ROOK;
		} else {
			stone = Chess.BLACK_ROOK;
		}
		
		if(position == -1) {
			captured = true;
		}
		
		Model.pieces.add(this);
	}
}
