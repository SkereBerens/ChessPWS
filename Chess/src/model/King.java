package model;

import java.io.IOException;
import java.util.ArrayList;

import chesspresso_OpenSourceCode.Chess;
import controller.Controller;

public class King extends Piece{
	boolean isInCheck = false;
	boolean doubleCheck = false;
	public Piece rookShort;
	public Piece rookLong;
	
	@Override
	public void MoveTo(int position, boolean modelOnly) throws IOException {
		if(!captured) {
			for(int square : GetLegalMoves()) {
				if(square == position) {
					previousPositionFENAddOn = Board.GetFENAdvancedAddOn();
					// 0 want dan staat er niemand
					boolean isOppositeColor = Board.GetPositionGrid()[position] != 0 && Chess.stoneToColor(Board.GetPositionGrid()[position]) != Chess.stoneToColor(stone);
					if(isOppositeColor) {
						Capture(position, modelOnly);
						movesTilDraw = -1;
					} else {
						capturedPiece = null;
					}
					
					
					for(Piece p : Board.activePieces) {
						p.previousPosition = p.position;
					}
					
					previousPosition = this.position;
					
					
					if(CanCastleShort() && (position == Chess.G8 || position == Chess.G1)) {
						if(!modelOnly) {
							Controller.CastleShort(isWhite);
						}
						
						CastleShort();
					} else if(CanCastleLong() && (position == Chess.C8 || position == Chess.C1)) {
						if(!modelOnly) {
							Controller.CastleLong(isWhite);
						}
						CastleLong();
					} else {
						if(!modelOnly) {
							Controller.MovePieceGUI(this, position);
						}
						
						this.position = position;
					}
					movesTilDraw++;
					MiscMoveFunctions(modelOnly);
					break;
				}
			}
			
		}
	}
	
	
	//Chess.getNumSquares to edge heb ik zelf geschreven: zie onderaan chesspresso_OpenSourceCode.Chess.
	ArrayList<Integer> attackedSquaresTotalList = new ArrayList<Integer>();
	int[] attackedSquaresTotal;
	@Override int[] GetMoveableSquares() {
		moveableSquaresList.removeAll(moveableSquaresList);
		attackedSquaresTotalList.removeAll(attackedSquaresTotalList);
		for(Piece piece : Board.GetPiecesOfColor(!isWhite)) {
			for(int square : piece.GetAttackingSquares()) {
				attackedSquaresTotalList.add(square);
			}
		}
		attackedSquaresTotal = attackedSquaresTotalList.stream().mapToInt(Integer::intValue).toArray();
		
		if(CanCastleShort()) {
			if(isWhite) {
				moveableSquaresList.add(Chess.G1);
			} else {
				moveableSquaresList.add(Chess.G8);
			}
		}
		
		if(CanCastleLong()) {
			if(isWhite) {
				moveableSquaresList.add(Chess.C1);
			} else {
				moveableSquaresList.add(Chess.C8);
			}
		}
		
		for(int directionIndex : directionIndices) {
			if(Chess.NumSquaresToEdge[position][directionIndex] != 0 && !Chess.IsInList(attackedSquaresTotal, this.position + possibleDirections[directionIndex])) {
				if(Board.GetPositionGrid()[this.position + possibleDirections[directionIndex]] == 0) {
					moveableSquaresList.add(this.position + possibleDirections[directionIndex]);
				} else if(Chess.stoneToColor(Board.GetPositionGrid()[this.position + possibleDirections[directionIndex]]) != Chess.stoneToColor(stone)){
					moveableSquaresList.add(this.position + possibleDirections[directionIndex]);
				}
			}			
		}
		
		return moveableSquaresList.stream().mapToInt(Integer::intValue).toArray();
	}
	
	@Override public int[] GetMoveableSquaresInCheck() {
		return GetMoveableSquares();
	}
	
	
	@Override public int[] GetAttackingSquares() {
		attackSquaresList.removeAll(attackSquaresList);
		for(int directionIndex : directionIndices) {
			if(Chess.NumSquaresToEdge[position][directionIndex] != 0) {
				attackSquaresList.add(this.position + possibleDirections[directionIndex]);
			}			
		}
		return attackSquaresList.stream().mapToInt(Integer::intValue).toArray();
	}
	
	//knight directions and queen directions combined
	static int[] possibleAttackDiagonals = {7,-7,9,-9};
	static int[] possibleAttackStraits = {1,-1,8,-8};
	static int[] possibleAttackKnights = {15,6,17,10,-10,-17,-15,-6};
	
	public Piece GetCheckingPiece() {
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
	
	
	ArrayList<Integer> squaresList = new ArrayList<Integer>();
	int[] squares;
	public void isInCheck() {
		squaresList.removeAll(squaresList);
		for(Piece piece : Board.GetPiecesOfColor(!isWhite)) {
			piece.attackSquaresList.removeAll(attackSquaresList);
			for(int square : piece.GetAttackingSquares()) {
				if(square == position) {
					squaresList.add(square);
					isInCheck = true;
					attackingPiece = piece;
				}
			}
		}
		squares = squaresList.stream().mapToInt(Integer::intValue).toArray();
		if(squares.length > 1) {
			isInCheck = true;
			doubleCheck = true;
			return;
		} else if(squares.length== 1) {
			isInCheck = true;
			return;
		}
		doubleCheck = false;
		isInCheck = false;
		attackingPiece= null;
		return;
	}
	
	public Piece GetCheckingPieceOpps() {
		return attackingPiece;
	}
	
	//
	//CASTLING
	//
	
	//SHORT CASTLING
	
	
	public boolean hasCastlingRightsShort = true;
	
	public void CastleShort() {
		if(isWhite) {
			this.hasMoved = true;
			rookShort.position = Chess.F1;
			rookShort.hasMoved = true;
			this.position = Chess.G1;
			return;
		}
		this.hasMoved = true;
		rookShort.position = Chess.F8;
		rookShort.hasMoved = true;
		this.position = Chess.G8;
		
	}
	
	
	
	public void CheckShortCastlingRights() {
	
		if(!hasMoved && !rookShort.hasMoved && !rookShort.captured) {
			return;
		}
		
		hasCastlingRightsShort = false;
	}
	
	boolean CanCastleShort() {
		CheckShortCastlingRights();
		if(hasCastlingRightsShort) {
			int[] OppsAttackingSquares = GetAllOpponentAttackSquares();
			for(int square : OppsAttackingSquares) {
				if(isWhite && (square == Chess.E1 || square == Chess.F1 || square == Chess.G1)) {
					return false;
				} else if(!isWhite && (square == Chess.E8 || square == Chess.F8 || square == Chess.G8)) {
					return false;
				}
			}
			
			if(isWhite && (Board.GetPositionGrid()[Chess.F1] != 0 || Board.GetPositionGrid()[Chess.G1] != 0)) {
				return false;
			} else if(!isWhite && (Board.GetPositionGrid()[Chess.F8] != 0 || Board.GetPositionGrid()[Chess.G8] != 0)) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	
	//LONG CASTLING
	public boolean hasCastlingRightsLong = true;
	
	public void CastleLong() {
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
	
	public void CheckLongCastlingRights() {
		if(!hasMoved && !rookLong.hasMoved && !rookLong.captured) {
			return;
		}
		
		hasCastlingRightsLong = false;
	}
	
	boolean CanCastleLong() {
		CheckLongCastlingRights();
		if(hasCastlingRightsLong) {
			int[] OppsAttackingSquares = GetAllOpponentAttackSquares();
			for(int square : OppsAttackingSquares) {
				if(isWhite && (square == Chess.E1 || square == Chess.D1 || square == Chess.C1)) {
					return false;
				} else if(!isWhite && (square == Chess.E8 || square == Chess.D8 || square == Chess.C8)) {
					return false;
				}
			}
			
			if(isWhite && (Board.GetPositionGrid()[Chess.D1] != 0 || Board.GetPositionGrid()[Chess.C1] != 0 || Board.GetPositionGrid()[Chess.B1] != 0)) {
				return false;
			} else if(!isWhite && (Board.GetPositionGrid()[Chess.D8] != 0 || Board.GetPositionGrid()[Chess.C8] != 0 || Board.GetPositionGrid()[Chess.B8] != 0)) {
				return false;
			}
			return true;
		}
		return false;
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
		
		if(position == -1) {
			captured = true;
		}
		
		Model.pieces.add(this);
	}
}
