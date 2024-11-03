package demo;
import java.io.IOException;
import java.util.ArrayList;

import ControllTest.Controlla;
import chesspresso.*;

public class Pawn extends Piece{
	
	@Override public void MoveTo(int position) throws IOException {
		if(!captured) {
			king = ((King)Board.GetKing(stone));
			attackingPiece = king.GetCheckingPiece();
			for(int square : GetLegalMoves()) {
				if(square == position) {
					// 0 want dan staat er niemand
					
					boolean isOppositeColor = Board.GetPositionGrid()[position] != 0 && Chess.stoneToColor(Board.GetPositionGrid()[position]) != Chess.stoneToColor(stone);
					boolean isOnDiagonalSquare = ((int) (this.position - Math.signum(stone)* 9) == position || (int) (this.position - Math.signum(stone)* 7) == position);
					
					if(isOppositeColor && isOnDiagonalSquare) {
						Capture(position);
					}
					
					//enpassant
					
					for(Piece pawn :  Board.GetEnemyPiecesOfType(stone)) {
						if(isOnDiagonalSquare && ((Pawn) pawn).enpassantableSquare == position) {
							Capture(pawn.position);
							break;
						}
					}
					
					if(Math.abs(this.position - position) == 16) {
						enpassantableSquare = (int) (position + 8 * Math.signum(stone));
					} else {
						enpassantableSquare = -1;
					}
					
					Controlla.MovePieceGUI(this, position);
					this.position = position;
					//before actually moving cuz otherwise it will select captured piece
					if((Chess.sqiToRow(this.position) == 7 && isWhite) || (Chess.sqiToRow(this.position) == 0 && !isWhite)) {
						Promote((int) Math.signum(stone) * Chess.BLACK_QUEEN);
					}
					//cuz if pawn move then fiftymove rule reset
					movesTilDraw = 0;
					MiscMoveFunctions();
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
	
	public boolean isAttackingEnpassantableSquare(int diagonal) {
		if(!isNotOnBoardEdge9 && diagonal == 9) {
			return false;
		}
		
		if(!isNotOnBoardEdge7 && diagonal == 7) {
			return false;
		}
		
		for(Piece pawn : Board.GetEnemyPiecesOfType(stone)) {
			if(((Pawn)pawn).enpassantableSquare == (int) (this.position - Math.signum(stone)* diagonal)) {
				return true;
			}
		}
		
		return false;
	}

	
	@Override int[] GetMoveableSquares() {
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
			
		//convert een list naar een array (op internet opgezocht)
		moveableSquares = moveableSquaresList.stream().mapToInt(Integer::intValue).toArray();;
		moveableSquaresList.removeAll(moveableSquaresList);
		return moveableSquares;
	}	
	
	@Override int[] GetMoveableSquaresInCheck() {
		int[] moveableSquaresNoCheck = GetMoveableSquares();
		int[] blocksquares = GetBlockSquares();
		
		for(int i : moveableSquaresNoCheck) {
			for(int j : blocksquares) {
				if(i == j) {
					moveableSquaresList.add(i);
				}
			}
		}
		moveableSquares = moveableSquaresList.stream().mapToInt(Integer::intValue).toArray();
		moveableSquaresList.removeAll(moveableSquaresList);
		return moveableSquares;
	}
	@Override public int[] GetAttackingSquares() {
		if(!captured) {
			attackSquaresList.add((int) (this.position - Math.signum(stone)* 7));
			attackSquaresList.add((int) (this.position - Math.signum(stone)* 9));
		}
		attackSquares =  attackSquaresList.stream().mapToInt(Integer::intValue).toArray();
		attackSquaresList.removeAll(attackSquaresList);
		return attackSquares;
	}
	
	Piece PromotionPiece;
	public Piece Promote(int promotionPiece) throws IOException {
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
		Controlla.Promote(promotionPiece, PromotionPiece);
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
		
		Model.pieces.add(this);
	}
}
