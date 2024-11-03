package demo;

import java.util.ArrayList;

import chesspresso.Chess;

public class Knight extends Piece{
	@Override int[] GetMoveableSquares() {
		
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
		moveableSquares = moveableSquaresList.stream().mapToInt(Integer::intValue).toArray();;
		moveableSquaresList.removeAll(moveableSquaresList);
		return moveableSquares;
	}
	
	@Override int[] GetMoveableSquaresInCheck() {
		int[] blocksquares = GetBlockSquares();
		if(!captured && !isPinned) {
			for(int directionIndex : directionIndices) {
					if(Chess.IsInList(blocksquares, this.position + possibleDirections[directionIndex] )) {
						moveableSquaresList.add(this.position + possibleDirections[directionIndex] );
					}
			}
		}
		moveableSquares = moveableSquaresList.stream().mapToInt(Integer::intValue).toArray();;
		moveableSquaresList.removeAll(moveableSquaresList);
		return moveableSquares;
	}
	
	@Override public int[] GetAttackingSquares() {
		for(int directionIndex : directionIndices) {
			if(Chess.NumSquaresToEdge[position][directionIndex] != 0) {
				attackSquaresList.add(this.position + possibleDirections[directionIndex]);
			}			
		}
		attackSquares = attackSquaresList.stream().mapToInt(Integer::intValue).toArray();
		attackSquaresList.removeAll(attackSquaresList);
		return attackSquares;
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
		
		Model.pieces.add(this);
	}
}
