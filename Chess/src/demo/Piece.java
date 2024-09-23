package demo;

import java.util.ArrayList;

import chesspresso.Chess;

public class Piece {
	public int stone;
	public int position;
	public boolean isWhite;
	public boolean isPinned;
	public boolean captured = false;
	public int[] directionIndicesIfPinned;
	public int[] directionIndices;
	static int[] possibleDirections = {7,-7,9,-9,8,-8,1,-1,6,-6,15,-15,10,-10,17,-17};
	
	
	public static final int[] bishopDirIndices =  {0,1,2,3};
	public static final int[] queenDirIndices =  {0,1,2,3,4,5,6,7};
	public static final int[] rookDirIndices =  {4,5,6,7};
	public static final int[] knightDirIndices =  {8,9,10,11,12,13,14,15};
	
	/*
	public static final int[] bishopDir =  {7,-7,9,-9};
	public static final int[] queenDir =  {0,1,2,3,4,5,6,7};
	public static final int[] rookDir =  {8,-8,1,-1};
	public static final int[] knightDir =  {6,-6,15,-15,10,-10,17,-17};
	*/
	//public ArrayList<Integer> attackingSquares = new ArrayList<Integer>();
	//VISUALISATION MOVES
	//
	//	15		17
	//6			  10
	//	  knight
	//-10		  -6
	//	-17		-15
		
	//7		    9
	//   bishop
	//-9	    7
	
	//	    8
	//-1  rook   1      
	//     -8
	
	//7    8     9
	//-1  queen  1
	//-9   -8   -7
	
	//queen also works for king
	King king;
	Piece attackingPiece;
	public void MoveTo(int position) {
		if(!captured) {
			king = ((King)Board.GetKing(stone));
			attackingPiece = king.GetCheckingPiece();
			for(int square : GetLegalMoves()) {
				if(square == position) {
					//-1 is wanneer de kleur van een piece op een square niks is (dus er staat geen piece)
					boolean isOppositeColor = Chess.stoneToColor(Board.GetPositionGrid()[position]) != -1 && Chess.stoneToColor(Board.GetPositionGrid()[position]) != Chess.stoneToColor(stone);
					if(isOppositeColor) {
						Capture(position);
					}
					this.position = position;
					Board.updatePosition();
					king.isInCheck = false;
					//Check if opposite king is in check
					for(Piece opps : Board.pieces) {
						opps.isPinned = false;
					}
					for(Piece slidingPiece : Board.GetSlidingPieces(stone)) {
						System.out.println("oma lord");
						slidingPiece.Pin();
					}
					
					((King) Board.GetKing(-stone)).isInCheck();
					break;
				}
			}
		}
	}
	
	public int[] GetAttackingSquares() {
		ArrayList<Integer> attackSquares = new ArrayList<Integer>();
		if(!captured) {
			for(int directionIndex : directionIndices) {
				for(int i = 1; i < Chess.NumSquaresToEdge[this.position][directionIndex] + 1; i++) {
					attackSquares.add(this.position + possibleDirections[directionIndex] * i);
				}
			}
		}
		return attackSquares.stream().mapToInt(Integer::intValue).toArray();
	}
	
	//for slidey pieces (bishop, rook, queen)
	int[] GetMoveableSquares() {
			ArrayList<Integer> moveableSquares = new ArrayList<Integer>();
		if(!captured && !isPinned) {
			for(int directionIndex : directionIndices) {
				for(int i = 1; i < Chess.NumSquaresToEdge[this.position][directionIndex] + 1; i++) {
						if(Board.GetPositionGrid()[this.position + possibleDirections[directionIndex] * i] == 0) {
							moveableSquares.add(this.position + possibleDirections[directionIndex] * i);
						} else if(Chess.stoneToColor(Board.GetPositionGrid()[this.position + possibleDirections[directionIndex] * i]) == Chess.stoneToColor(stone)){
							break;
						} else {
							moveableSquares.add(this.position + possibleDirections[directionIndex] * i);
							break;
						}
					
				}
			}
		} else if(!captured && isPinned) {
			for(int directionIndex : directionIndicesIfPinned) {
				for(int i = 1; i < Chess.NumSquaresToEdge[this.position][directionIndex] + 1; i++) {
						if(Board.GetPositionGrid()[this.position + possibleDirections[directionIndex] * i] == 0) {
							moveableSquares.add(this.position + possibleDirections[directionIndex] * i);
						} else if(Chess.stoneToColor(Board.GetPositionGrid()[this.position + possibleDirections[directionIndex] * i]) == Chess.stoneToColor(stone)){
							break;
						} else {
							moveableSquares.add(this.position + possibleDirections[directionIndex] * i);
							break;
						}
					
				}
			}
		}
		return moveableSquares.stream().mapToInt(Integer::intValue).toArray();
	}
	//CHECKS
	//
	//
	
	int[] GetMoveableSquaresInCheck() {
		ArrayList<Integer> moveableSquares = new ArrayList<Integer>();
		int[] blocksquares = GetBlockSquares();
		if(!captured && !isPinned) {
			for(int directionIndex : directionIndices) {
				for(int i = 1; i < Chess.NumSquaresToEdge[this.position][directionIndex] + 1; i++) {
					if(Chess.IsInList(blocksquares, this.position +  possibleDirections[directionIndex] * i)) {
						moveableSquares.add(this.position +  possibleDirections[directionIndex] * i);
					}
				}
			}
		}
		return moveableSquares.stream().mapToInt(Integer::intValue).toArray();
	}
	
	//Gets squares where you block or capture the checking piece
	int[] GetBlockSquares() {
		ArrayList<Integer> blocksquares = new ArrayList<Integer>();
		if(attackingPiece.stone == Math.signum(stone) * Chess.WHITE_QUEEN
		|| attackingPiece.stone == Math.signum(stone) * Chess.WHITE_BISHOP
		|| attackingPiece.stone == Math.signum(stone) * Chess.WHITE_ROOK) {
			
			int direction = 0;
			int difference = (attackingPiece.position- king.position);
			
			for(int i = 0; i < 9; i++) {
				//gets direction from where king is attacked
				if(difference % possibleDirections[i] == 0 && (Chess.NumSquaresToEdge[attackingPiece.position][i] != 0)){
					direction = (int) (Math.signum(difference) * possibleDirections[i]);
					break; 
				}
			}
			
			int tempPos = king.position;
			while(tempPos != attackingPiece.position) {
				tempPos += direction;
				blocksquares.add(tempPos);
			}
			tempPos += direction;
			blocksquares.add(tempPos);
			
			
		} else {
			blocksquares.add(attackingPiece.position);
		}
		return blocksquares.stream().mapToInt(Integer::intValue).toArray();
	}
	
	//PINS
	//
	//
	
	public void Pin() {
		if(!captured) {
			for(int directionIndex : directionIndices) {
				for(int i = 1; i < Chess.NumSquaresToEdge[this.position][directionIndex] + 1; i++) {
					if(Board.GetPositionGrid()[this.position + possibleDirections[directionIndex] * i] == 0) {
						//continue, cuz empty
					} else if(Chess.stoneToColor(Board.GetPositionGrid()[this.position + possibleDirections[directionIndex] * i]) == Chess.stoneToColor(stone)){
						
						break;
					} else {
						System.out.println("GAYYY");
						for(int j = i + 1; j < Chess.NumSquaresToEdge[this.position + possibleDirections[directionIndex] * i][directionIndex] + 1; j++) {
							if(this.position + possibleDirections[directionIndex] * i + possibleDirections[directionIndex] * j == Board.GetKing(-stone).position) {
								Board.FindPieceByPosition(this.position + possibleDirections[directionIndex] * i).isPinned = true;
								Board.FindPieceByPosition(this.position + possibleDirections[directionIndex] * i).directionIndicesIfPinned = new int[] {directionIndex, Chess.GetOppositeDirectionIndex(directionIndex)};
								break;
							}
						}
						break;
					}
				}
			}
		}
	}
	
	//still have to add double checks and pins 
	public int[] GetLegalMoves() {
		if(((King)Board.GetKing(stone)).isInCheck) {
			return GetMoveableSquaresInCheck();
		} 
		return GetMoveableSquares();
	}
	
	void Capture(int position) {
		System.out.println("gnininihi");
		Board.RemovePiece(position);
	}
}
