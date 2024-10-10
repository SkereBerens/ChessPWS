package demo;

import java.util.ArrayList;
import chesspresso.Chess;

public class King extends Piece{
	boolean isInCheck = false;
	boolean doubleCheck = false;
	Piece attackingPiece;
	Piece rookShort;
	Piece rookLong;
	
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
	
	int[] GetAllOpponentAttackSquares() {
		ArrayList<Integer> squares = new ArrayList<Integer>();
		for(Piece piece : Board.GetPiecesOfColor(!isWhite)) {
			for(int square : piece.GetAttackingSquares()) {
				squares.add(square);
			}
		}
		return squares.stream().mapToInt(Integer::intValue).toArray();
	}
	
	public void isInCheck() {
		ArrayList<Integer> squares = new ArrayList<Integer>();
		for(Piece piece : Board.GetPiecesOfColor(!isWhite)) {
			for(int square : piece.GetAttackingSquares()) {
				if(square == position) {
					squares.add(square);
					isInCheck = true;
					attackingPiece = piece;
				}
			}
		}
		
		if(squares.size() > 1) {
			isInCheck = true;
			doubleCheck = true;
			return;
		} else if(squares.size( )== 1) {
			isInCheck = true;
			return;
		}
		doubleCheck = false;
		isInCheck = false;
		attackingPiece= null;
		return;
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
	
	//
	//CASTLING
	//
	
	//SHORT CASTLING
	
	public boolean hasCastlingRightsShort = true;
	
	public void CastleShort() {
		CheckShortCastlingRights();
		if(hasCastlingRightsShort) {
			int[] OppsAttackingSquares = GetAllOpponentAttackSquares();
			for(int square : OppsAttackingSquares) {
				if(isWhite && (square == Chess.E1 || square == Chess.F1 || square == Chess.G1)) {
					return;
				} else if(!isWhite && (square == Chess.E8 || square == Chess.F8 || square == Chess.G8)) {
					return;
				}
			}
			
			if(isWhite && (Board.GetPositionGrid()[Chess.F1] != 0 || Board.GetPositionGrid()[Chess.G1] != 0)) {
				return;
			} else if(!isWhite && (Board.GetPositionGrid()[Chess.F8] != 0 || Board.GetPositionGrid()[Chess.G8] != 0)) {
				return;
			}
			
			if(isWhite) {
				this.hasMoved = true;
				rookShort.position = Chess.F1;
				this.position = Chess.G1;
				return;
			}
			this.hasMoved = true;
			rookShort.position = Chess.F8;
			this.position = Chess.G8;
		}
	}
	

	
	void CheckShortCastlingRights() {
	
		if(!hasMoved && !rookShort.hasMoved && !rookShort.captured) {
			return;
		}
		
		hasCastlingRightsShort = false;
	}
	
	//LONG CASTLING
	public boolean hasCastlingRightsLong = true;
	
	public void CastleLong() {
		CheckLongCastlingRights();
		if(hasCastlingRightsLong) {
			int[] OppsAttackingSquares = GetAllOpponentAttackSquares();
			for(int square : OppsAttackingSquares) {
				if(isWhite && (square == Chess.E1 || square == Chess.D1 || square == Chess.C1)) {
					return;
				} else if(!isWhite && (square == Chess.E8 || square == Chess.D8 || square == Chess.C8)) {
					return;
				}
			}
			
			if(isWhite && (Board.GetPositionGrid()[Chess.D1] != 0|| Board.GetPositionGrid()[Chess.C1] != 0)) {
				return;
			} else if(!isWhite && (Board.GetPositionGrid()[Chess.D8] != 0|| Board.GetPositionGrid()[Chess.C8] != 0)) {
				return;
			}
			
			if(isWhite) {
				this.hasMoved = true;
				rookLong.position = Chess.D1;
				this.position = Chess.C1;
				return;
			}
			this.hasMoved = true;
			rookLong.position = Chess.D8;
			this.position = Chess.C8;
		}
	}
	
	void CheckLongCastlingRights() {
		if(!hasMoved && !rookLong.hasMoved && !rookLong.captured) {
			return;
		}
		
		hasCastlingRightsLong = false;
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
		//for castling
		if(isWhite) {
			rookShort = Board.FindPieceByPosition(Chess.H1);
			rookLong = Board.FindPieceByPosition(Chess.A1);
		} else {
			rookShort = Board.FindPieceByPosition(Chess.H8);
			rookLong = Board.FindPieceByPosition(Chess.A8);
		}
		
		//to update attacking squares
		Board.addPiece(this);
		GetMoveableSquares();
	}
}
