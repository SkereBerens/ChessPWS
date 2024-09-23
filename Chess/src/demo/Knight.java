package demo;

import java.util.ArrayList;

import chesspresso.Chess;

public class Knight extends Piece{
	@Override int[] GetMoveableSquares() {
		ArrayList<Integer> moveableSquares = new ArrayList<Integer>();
		if(!captured && !isPinned) {
			for(int directionIndex : directionIndices) {
				if(Chess.NumSquaresToEdge[position][directionIndex] != 0) {
					if(Board.GetPositionGrid()[this.position + possibleDirections[directionIndex]] == 0) {
						moveableSquares.add(this.position + possibleDirections[directionIndex]);
					} else if(Chess.stoneToColor(Board.GetPositionGrid()[this.position + possibleDirections[directionIndex] ]) != Chess.stoneToColor(stone)){
						moveableSquares.add(this.position + possibleDirections[directionIndex]);
					} 
				}			
			}
		}
		return moveableSquares.stream().mapToInt(Integer::intValue).toArray();
	}
	
	@Override int[] GetMoveableSquaresInCheck() {
		ArrayList<Integer> moveableSquares = new ArrayList<Integer>();
		int[] blocksquares = GetBlockSquares();
		if(!captured && !isPinned) {
			for(int directionIndex : directionIndices) {
					if(Chess.IsInList(blocksquares, this.position + possibleDirections[directionIndex] )) {
						moveableSquares.add(this.position + possibleDirections[directionIndex] );
					}
			}
		}
		return moveableSquares.stream().mapToInt(Integer::intValue).toArray();
	}
	
	@Override public int[] GetAttackingSquares() {
		ArrayList<Integer> attackSquares = new ArrayList<Integer>();
		for(int directionIndex : directionIndices) {
			if(Chess.NumSquaresToEdge[position][directionIndex] != 0) {
				attackSquares.add(this.position + possibleDirections[directionIndex]);
			}			
		}
		return attackSquares.stream().mapToInt(Integer::intValue).toArray();
	}
	
	Knight(int startingPos, boolean color) {
		position = startingPos;
		isWhite = color;
		directionIndices = knightDirIndices;
		if(isWhite) {
			stone = Chess.WHITE_KNIGHT;
		} else {
			stone = Chess.BLACK_KNIGHT;
		}
		//to update attacking squares
		Board.addPiece(this);
		GetMoveableSquares();
	}
}
