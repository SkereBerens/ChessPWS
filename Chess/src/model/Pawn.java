package model;
import java.io.IOException;
import java.util.ArrayList;

import chesspresso_OpenSourceCode.*;
import controller.Controller;

public class Pawn extends Piece{
	
	@Override public void MoveTo(int position, boolean modelOnly) throws IOException {
		//previousPosition = null;
		if(!captured) {
			king = ((King)Board.GetKing(stone));
			attackingPiece = king.GetCheckingPiece();
			for(int square : GetLegalMoves()) {
				if(square == position) {
					// 0 want dan staat er niemand
					previousPositionFENAddOn = Board.GetFENAdvancedAddOn();
					boolean isOppositeColor = Board.GetPositionGrid()[position] != 0 && Chess.stoneToColor(Board.GetPositionGrid()[position]) != Chess.stoneToColor(stone);
					boolean isOnDiagonalSquare = ((int) (this.position - Math.signum(stone)* 9) == position || (int) (this.position - Math.signum(stone)* 7) == position);
					
					if(isOnDiagonalSquare && isOppositeColor) {
						Capture(position, modelOnly);
					} else if(isOnDiagonalSquare && isAttackingEnpassantableSquare(position - this.position)) {
						//signum stone because if white (minus stone), need to count down to get black pawn and other way around
						Capture((int) (position + Math.signum(stone) * 8), modelOnly);
					} else {
						capturedPiece = null;
					}
					
					//enpassant
					
					
					
					if(Math.abs(this.position - position) == 16) {
						enpassantableSquare = (int) (position + 8 * Math.signum(stone));
					} else {
						enpassantableSquare = -1;
					}
					
					
					for(Piece p : Board.activePieces) {
						p.previousPosition = p.position;
					}
					
					previousPosition = this.position;
					
					if(!modelOnly) {
						Controller.MovePieceGUI(this, position);
					}
					this.position = position;
					
					if((Chess.sqiToRow(this.position) == 7 && isWhite) || (Chess.sqiToRow(this.position) == 0 && !isWhite)) {
						try {
							Promote((int) Math.signum(stone) * Chess.BLACK_QUEEN, modelOnly);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					//cuz if pawn move then fiftymove rule reset
					movesTilDraw = 0;
					MiscMoveFunctions(modelOnly);
					break;
				}
			}
			
		}
	} 
	
	public int enpassantableSquare = -1;
	public boolean enemyPieceIsOnDiagonal7;
	boolean enemyPieceIsOnDiagonal9;
	boolean isNotOnBoardEdge7;
	boolean isNotOnBoardEdge9;
	public boolean isPinnedForEnPassant;
	Pawn enpassantablePawn;
	
	public boolean isAttackingEnpassantableSquare(int diagonal) {
		int diagonalAbs = Math.abs(diagonal);
		if(!isNotOnBoardEdge9 && diagonalAbs == 9) {
			return false;
		}
		
		if(!isNotOnBoardEdge7 && diagonalAbs == 7) {
			return false;
		}
		
		if(isPinnedForEnPassant) {
			return false;
		}
		
		if(Board.GetPositionGrid()[(int) (position -1 * Math.signum(stone) * (diagonalAbs - 8))] == Chess.WHITE_PAWN * Math.signum(stone)) 
		{
			enpassantablePawn = (Pawn) Board.FindPieceByPosition((int) (position -1 * Math.signum(stone) * (diagonalAbs - 8)));
			if(enpassantablePawn.enpassantableSquare == (int) (this.position - Math.signum(stone)* diagonalAbs)) {
				return true;
			}
		}
		return false;
	}

	
	@Override int[] GetMoveableSquares() {
		moveableSquaresList.removeAll(moveableSquaresList);
		isNotOnBoardEdge9 = (Chess.NumSquaresToEdge[position][2] != 0 && isWhite) || (Chess.NumSquaresToEdge[position][3] != 0 && !isWhite);
		isNotOnBoardEdge7 = (Chess.NumSquaresToEdge[position][0] != 0 && isWhite) || (Chess.NumSquaresToEdge[position][1] != 0 && !isWhite);
		enemyPieceIsOnDiagonal9 = false;
		enemyPieceIsOnDiagonal7 = false;
		
		if(isNotOnBoardEdge9) {
			enemyPieceIsOnDiagonal9 = (Chess.stoneToColor(Board.GetPositionGrid()[(int) (this.position - Math.signum(stone)* 9)]) != Chess.NOBODY && Chess.stoneToColor(Board.GetPositionGrid()[(int) (this.position - Math.signum(stone)* 9)]) != Chess.stoneToColor(stone));
		}
		
		if(isNotOnBoardEdge7) {
			enemyPieceIsOnDiagonal7 = (Chess.stoneToColor(Board.GetPositionGrid()[(int) (this.position - Math.signum(stone)* 7)]) != Chess.NOBODY && Chess.stoneToColor(Board.GetPositionGrid()[(int) (this.position - Math.signum(stone)* 7)]) != Chess.stoneToColor(stone));

		}
		
		
		
		if(!captured && !isPinned) {
			if(Chess.sqiToRow(this.position) == 1 && isWhite || Chess.sqiToRow(this.position) == 6 && !isWhite) {
				if(Board.GetPositionGrid()[(int) (this.position - Math.signum(stone)* 16)] == 0 && Board.GetPositionGrid()[(int) (this.position - Math.signum(stone)* 8)] == 0) {
					moveableSquaresList.add((int) (this.position - Math.signum(stone)* 16));
				}
				if(Board.GetPositionGrid()[(int) (this.position - Math.signum(stone)* 8)] == 0) {
					moveableSquaresList.add((int) (this.position - Math.signum(stone)* 8));
				}
			} else {
				if(Board.GetPositionGrid()[(int) (this.position - Math.signum(stone)* 8)] == 0) {
					moveableSquaresList.add((int) (this.position - Math.signum(stone)* 8));
				}
			}
			
			//diagonal captures
			if(enemyPieceIsOnDiagonal9 || isAttackingEnpassantableSquare(9)) {
				moveableSquaresList.add((int) (this.position - Math.signum(stone)* 9));
			}
			if((enemyPieceIsOnDiagonal7 || isAttackingEnpassantableSquare(7))) {
				moveableSquaresList.add((int) (this.position - Math.signum(stone)* 7));
			}
		} 
		
		else if(!captured && isPinned) {
			if(directionIndicesIfPinned[0] == 4 || directionIndicesIfPinned[0] == 5) {
				if((Chess.sqiToRow(this.position) == 1 && isWhite) || (Chess.sqiToRow(this.position) == 6 && !isWhite)) {
					if(Board.GetPositionGrid()[(int) (this.position - Math.signum(stone)* 16)] == 0 && Board.GetPositionGrid()[(int) (this.position - Math.signum(stone)* 8)] == 0) {
						moveableSquaresList.add((int) (this.position - Math.signum(stone)* 16));
					}
					if(Board.GetPositionGrid()[(int) (this.position - Math.signum(stone)* 8)] == 0) {
						moveableSquaresList.add((int) (this.position - Math.signum(stone)* 8));
					}
				} else {
					if(Board.GetPositionGrid()[(int) (this.position - Math.signum(stone)* 8)] == 0) {
						moveableSquaresList.add((int) (this.position - Math.signum(stone)* 8));
					}
				}
			}
			
			//diagonal captures
			
			if(directionIndicesIfPinned[0] == 0 || directionIndicesIfPinned[0] == 1) {
				if((enemyPieceIsOnDiagonal7 || isAttackingEnpassantableSquare(7))) {
					moveableSquaresList.add((int) (this.position - Math.signum(stone)* 7));
				}
				
			}
			
			if(directionIndicesIfPinned[0] == 2 || directionIndicesIfPinned[0] == 3) {
				if((enemyPieceIsOnDiagonal9 || isAttackingEnpassantableSquare(9))) {
					moveableSquaresList.add((int) (this.position - Math.signum(stone)* 9));
				}
			}
		}
		//move foreward 1 or 2 depending on row
		//signum geeft het teken van iets: dus  +1 of  -1
			
		//convert een list naar een array
		
		return moveableSquaresList.stream().mapToInt(Integer::intValue).toArray();
	}	

	int[] moveableSquaresNoCheck;
	int[] blocksquares;
	boolean IsAttackingAttackingPieceEnPassantSquare7;
	boolean IsAttackingAttackingPieceEnPassantSquare9;
	@Override int[] GetMoveableSquaresInCheck() {
		moveableSquaresList.removeAll(moveableSquaresList);
		moveableSquaresNoCheck = GetMoveableSquares();
		moveableSquaresList.removeAll(moveableSquaresList);
		blocksquares = GetBlockSquares();
		for(int i : moveableSquaresNoCheck) {
			for(int j : blocksquares) {
				if(i == j) {
					moveableSquaresList.add(i);
				}
			}
		}
		
		IsAttackingAttackingPieceEnPassantSquare9 = attackingPiece.stone == Math.signum(stone) * Chess.WHITE_PAWN && ((Pawn) attackingPiece).enpassantableSquare != -1 && (isAttackingEnpassantableSquare(9));
		IsAttackingAttackingPieceEnPassantSquare7=  attackingPiece.stone == Math.signum(stone) * Chess.WHITE_PAWN && ((Pawn) attackingPiece).enpassantableSquare != -1 && (isAttackingEnpassantableSquare(7));
		if(IsAttackingAttackingPieceEnPassantSquare7) {
			moveableSquaresList.add((int) (this.position - Math.signum(stone)* 7));
		} else if(IsAttackingAttackingPieceEnPassantSquare9) {
			moveableSquaresList.add((int) (this.position - Math.signum(stone)* 9));
		}
		return moveableSquaresList.stream().mapToInt(Integer::intValue).toArray();
	}
	
	//{7,-7,9,-9,8,-8,1,-1,6,-6,15,-15,10,-10,17,-17}
	@Override public int[] GetAttackingSquares() {
		attackSquaresList.removeAll(attackSquaresList);
		isNotOnBoardEdge9 = (Chess.NumSquaresToEdge[position][2] != 0 && isWhite) || (Chess.NumSquaresToEdge[position][3] != 0 && !isWhite);
		isNotOnBoardEdge7 = (Chess.NumSquaresToEdge[position][0] != 0 && isWhite) || (Chess.NumSquaresToEdge[position][1] != 0 && !isWhite);
		if(!captured && isNotOnBoardEdge9) {
			
			attackSquaresList.add((int) (this.position - Math.signum(stone)* 9));
		} 
		if(!captured && isNotOnBoardEdge7) {
			
			attackSquaresList.add((int) (this.position - Math.signum(stone)* 7));
		}
		return attackSquaresList.stream().mapToInt(Integer::intValue).toArray();
		
	}
	
	Piece PromotionPiece = null;
	public Piece Promote(int promotionPiece, boolean modelOnly) throws IOException {
		Board.RemovePiece(this.position);
		for(Piece p : Model.pieces) {
			if(p.stone == promotionPiece && !Board.activePieces.contains(p)) {
				PromotionPiece = p;
				PromotionPiece.captured = false;
				PromotionPiece.position = this.position;
				break;
			}
		}
		Board.addPiece(PromotionPiece);
		if(!modelOnly) {
			Controller.Promote(promotionPiece, PromotionPiece);
		}
		return PromotionPiece;
	}
	
	 Pawn(int startingPos, boolean color) {
		position = startingPos;
		isWhite = color;
		if(isWhite) {
			stone = Chess.WHITE_PAWN;
		} else {
			stone = Chess.BLACK_PAWN;
		}
		
		if(position == -1) {
			captured = true;
		}
		
		Model.pieces.add(this);
	}
}
