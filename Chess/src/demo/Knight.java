package demo;

import java.util.ArrayList;

import chesspresso.Chess;

public class Knight extends Piece{
	@Override int[] GetMoveableSquares() {
		moveableSquaresList.removeAll(moveableSquaresList);
		if(!captured && !isPinned) {
			for(int directionIndex : directionIndices) {
				if(Chess.NumSquaresToEdge[position][directionIndex] != 0) {
					if(Board.GetPositionGrid()[this.position + possibleDirections[directionIndex]] == 0) {
						moveableSquaresList.add(this.position + possibleDirections[directionIndex]);
					} else if(Chess.stoneToColor(Board.GetPositionGrid()[this.position + possibleDirections[directionIndex] ]) != Chess.stoneToColor(stone)){
						moveableSquaresList.add(this.position + possibleDirections[directionIndex]);
					} 
				}			
			}
		}
		return moveableSquaresList.stream().mapToInt(Integer::intValue).toArray();
	}
	
	@Override int[] GetMoveableSquaresInCheck() {
		moveableSquaresList.removeAll(moveableSquaresList);
		int[] blocksquares = GetBlockSquares();
		if(!captured && !isPinned) {
			for(int directionIndex : directionIndices) {
				if(Chess.NumSquaresToEdge[position][directionIndex] != 0) {
					if(Chess.IsInList(blocksquares, this.position + possibleDirections[directionIndex] )) {
						moveableSquaresList.add(this.position + possibleDirections[directionIndex] );
					}
				}	
			}
		}
		return moveableSquaresList.stream().mapToInt(Integer::intValue).toArray();
	}
	
	@Override public int[] GetAttackingSquares() {
		attackSquaresList.removeAll(attackSquaresList);
		for(int directionIndex : directionIndices) {
			if(Chess.NumSquaresToEdge[position][directionIndex] != 0) {
				attackSquaresList.add(this.position + possibleDirections[directionIndex]);
			}			
		}
		return  attackSquaresList.stream().mapToInt(Integer::intValue).toArray();
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
		
		if(position == -1) {
			captured = true;
		}
		
		Model.pieces.add(this);
	}
}
