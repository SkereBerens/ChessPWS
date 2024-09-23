package demo;

import java.util.ArrayList;
import chesspresso.Chess;

public class King extends Piece{
	boolean isInCheck = false;
	Piece attackingPiece;
	
	//Chess.getNumSquares to edge heb ik zelf geschreven: zie onderaan chesspresso.Chess.
	@Override int[] GetMoveableSquares() {
		ArrayList<Integer> moveableSquares = new ArrayList<Integer>();
		ArrayList<Integer> attackedSquares = new ArrayList<Integer>();
		for(Piece piece : Board.GetPiecesOfColor(!isWhite)) {
			for(int square : piece.GetAttackingSquares()) {
				attackedSquares.add(square);
			}
		}
		for(int directionIndex : directionIndices) {
			if(Chess.NumSquaresToEdge[position][directionIndex] != 0 && !Chess.IsInList(attackedSquares.stream().mapToInt(Integer::intValue).toArray(), this.position + possibleDirections[directionIndex])) {
				if(Board.GetPositionGrid()[this.position + possibleDirections[directionIndex]] == 0) {
					moveableSquares.add(this.position + possibleDirections[directionIndex]);
				} else if(Chess.stoneToColor(Board.GetPositionGrid()[this.position + possibleDirections[directionIndex]]) != Chess.stoneToColor(stone)){
					moveableSquares.add(this.position + possibleDirections[directionIndex]);
				}
			}			
		}
		return moveableSquares.stream().mapToInt(Integer::intValue).toArray();
	}
	
	@Override public int[] GetMoveableSquaresInCheck() {
		return GetMoveableSquares();
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
	
	//knight directions and queen directions combined
	static int[] possibleAttackDiagonals = {7,-7,9,-9};
	static int[] possibleAttackStraits = {1,-1,8,-8};
	static int[] possibleAttackKnights = {15,6,17,10,-10,-17,-15,-6};
	
	//NOG NIET KLAAR FIX LATER
	//Monster: check alle squares waar een attacker zou kunnen zijn
	//Opps: checkt alle squares die de opponent attackt
	public Piece GetCheckingPiece() {
		//return isInCheckMonster();
		return GetCheckingPieceOpps();
	}
	
	public int isInCheck() {
		for(Piece piece : Board.GetPiecesOfColor(!isWhite)) {
			for(int square : piece.GetAttackingSquares()) {
				if(square == position) {
					isInCheck = true;
					attackingPiece = piece;
					return 0;
				}
			}
		}
		isInCheck = false;
		attackingPiece= null;
		return 0;
	}
	/*
	public boolean isInCheckMonster() {
		for(int direction : possibleAttackDiagonals) {
			if(Chess.GetNumSquaresToEdge(position, direction) != 0) {
				if(Board.GetPositionGrid()[this.position + direction] == Math.signum(stone) * Chess.WHITE_PAWN) {
					System.out.println("you in check stupid jigha (from pawn even dumber)");
					return true;
				} else if(Chess.stoneToColor(Board.GetPositionGrid()[this.position + direction ]) == Chess.stoneToColor(stone)){
					break;
				}
			}			
		}
		
		
		for(int direction : possibleAttackDiagonals) {
			for(int i = 1; i < Chess.GetNumSquaresToEdge(this.position, direction) + 1; i++) {
					if(Board.GetPositionGrid()[this.position + direction * i] == Math.signum(stone) * Chess.WHITE_QUEEN || Board.GetPositionGrid()[this.position + direction] == Math.signum(stone) * Chess.WHITE_BISHOP) {
						System.out.println("you in check stupid jigha (by queen or bishop)");
						return true;
					} else if(Chess.stoneToColor(Board.GetPositionGrid()[this.position + direction * i]) == Chess.stoneToColor(stone)){
						break;
					}
			}
		}
		
		for(int direction : possibleAttackStraits) {
			for(int i = 1; i < Chess.GetNumSquaresToEdge(this.position, direction) + 1; i++) {
					if(Board.GetPositionGrid()[this.position + direction * i] == Math.signum(stone) * Chess.WHITE_QUEEN || Board.GetPositionGrid()[this.position + direction] == Math.signum(stone) * Chess.WHITE_ROOK) {
						System.out.println("you in check stupid jigha (by queen or rook)");
						return true;
					} else if(Chess.stoneToColor(Board.GetPositionGrid()[this.position + direction * i]) == Chess.stoneToColor(stone)){
						break;
					} 
			}
		}
		
		
		for(int direction : possibleAttackKnights) {
			for(int i = 1; i < Chess.GetNumSquaresToEdge(this.position, direction) + 1; i++) {
					if(Board.GetPositionGrid()[this.position + direction * i] == Math.signum(stone) * Chess.WHITE_KNIGHT) {
						System.out.println("you in check stupid jigha (by knight)");
						return true;
					} else if(Chess.stoneToColor(Board.GetPositionGrid()[this.position + direction * i]) == Chess.stoneToColor(stone)){
						break;
					}
			}
		}
		
		return false;
	}
	*/
	
	//also serves as a way to detect when king is in check
	public Piece GetCheckingPieceOpps() {
		return attackingPiece;
	}
	
	King(int startingPos, boolean color) {
		position = startingPos;
		isWhite = color;
		directionIndices = queenDirIndices;
		if(isWhite) {
			stone = Chess.WHITE_KING;
		} else {
			stone = Chess.BLACK_KING;
		}
		//to update attacking squares
		Board.addPiece(this);
		GetMoveableSquares();
	}
}
