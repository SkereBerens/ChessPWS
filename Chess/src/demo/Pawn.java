package demo;
import java.util.ArrayList;

import chesspresso.*;

public class Pawn extends Piece{
	
	@Override public void MoveTo(int position) {
		if(!captured) {
			king = ((King)Board.GetKing(stone));
			attackingPiece = king.GetCheckingPiece();
			for(int square : GetLegalMoves()) {
				if(square == position) {
					boolean isOppositeColor = Chess.stoneToColor(Board.GetPositionGrid()[position]) != -1 && Chess.stoneToColor(Board.GetPositionGrid()[position]) != Chess.stoneToColor(stone);
					boolean isOnDiagonalSquare = ((int) (this.position - Math.signum(stone)* 9) == position || (int) (this.position - Math.signum(stone)* 7) == position);
					//-1 is wanneer de kleur van een piece op een square niks is (dus er staat geen piece)
					
					if(isOppositeColor && isOnDiagonalSquare) {
						Capture(position);
					}
					this.position = position;
					Board.updatePosition();
					king.isInCheck = false;
					//Check if opposite king is in check
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
	
	@Override int[] GetMoveableSquares() {
		ArrayList<Integer> moveableSquares = new ArrayList<Integer>();
		if(!captured && !isPinned) {
			if(Chess.sqiToRow(this.position) == 1 && isWhite || Chess.sqiToRow(this.position) == 6 && !isWhite) {
				if(Board.GetPositionGrid()[(int) (this.position - Math.signum(stone)* 16)] == 0 && Board.GetPositionGrid()[(int) (this.position - Math.signum(stone)* 8)] == 0) {
					moveableSquares.add((int) (this.position - Math.signum(stone)* 16));
				}
				if(Board.GetPositionGrid()[(int) (this.position - Math.signum(stone)* 8)] == 0) {
					moveableSquares.add((int) (this.position - Math.signum(stone)* 8));
				}
			} else {
				if(Board.GetPositionGrid()[(int) (this.position - Math.signum(stone)* 8)] == 0) {
					moveableSquares.add((int) (this.position - Math.signum(stone)* 8));
				}
			}
			
			//diagonal captures
			if((Chess.stoneToColor(Board.GetPositionGrid()[(int) (this.position - Math.signum(stone)* 9)]) != Chess.NOBODY && Chess.stoneToColor(Board.GetPositionGrid()[(int) (this.position - Math.signum(stone)* 9)]) != Chess.stoneToColor(stone))) {
				moveableSquares.add((int) (this.position - Math.signum(stone)* 9));
			}
			if((Chess.stoneToColor(Board.GetPositionGrid()[(int)(this.position  - Math.signum(stone)* 7)]) != Chess.NOBODY && Chess.stoneToColor(Board.GetPositionGrid()[(int) (this.position - Math.signum(stone)* 7)]) != Chess.stoneToColor(stone))) {
				moveableSquares.add((int) (this.position - Math.signum(stone)* 7));
			}
		} else if(!captured && isPinned) {
			if(directionIndicesIfPinned[0] == 8 || directionIndicesIfPinned[0] == -8 ) {
				if((Chess.sqiToRow(this.position) == 1 && isWhite) || (Chess.sqiToRow(this.position) == 6 && !isWhite)) {
					if(Board.GetPositionGrid()[(int) (this.position - Math.signum(stone)* 16)] == 0 && Board.GetPositionGrid()[(int) (this.position - Math.signum(stone)* 8)] == 0) {
						moveableSquares.add((int) (this.position - Math.signum(stone)* 16));
					}
					if(Board.GetPositionGrid()[(int) (this.position - Math.signum(stone)* 8)] == 0) {
						moveableSquares.add((int) (this.position - Math.signum(stone)* 8));
					}
				} else {
					if(Board.GetPositionGrid()[(int) (this.position - Math.signum(stone)* 8)] == 0) {
						moveableSquares.add((int) (this.position - Math.signum(stone)* 8));
					}
				}
			}
		}
			//move foreward 1 or 2 depending on row
			//signum geeft het teken van iets: dus  +1 of  -1
			
			//convert een list naar een array (op internet opgezocht)
		return moveableSquares.stream().mapToInt(Integer::intValue).toArray();
	}	
	
	@Override int[] GetMoveableSquaresInCheck() {
		ArrayList<Integer> moveableSquares = new ArrayList<Integer>();
		int[] moveableSquaresNoCheck = GetMoveableSquares();
		int[] blocksquares = GetBlockSquares();
		
		for(int i : moveableSquaresNoCheck) {
			for(int j : blocksquares) {
				if(i == j) {
					moveableSquares.add(i);
				}
			}
		}
		
		return moveableSquares.stream().mapToInt(Integer::intValue).toArray();
	}
	
	@Override public int[] GetAttackingSquares() {
		int[] attackSquares = {(int) (this.position - Math.signum(stone)* 7),(int) (this.position - Math.signum(stone)* 9)};
		return attackSquares;
	}
	/*
	void Capture(int position) {
		Board.RemovePiece(position);
	}*/
	
	public Piece Promote(int promotionPiece) {
		Piece piece;
		if(promotionPiece == Math.signum(stone) * Chess.BLACK_QUEEN) {
			piece = new Queen(this.position, this.isWhite);
		} else if(promotionPiece == Math.signum(stone) * Chess.BLACK_ROOK) {
			piece = new Rook(this.position, this.isWhite);
		} else if(promotionPiece == Math.signum(stone) * Chess.BLACK_BISHOP) {
			piece = new Bishop(this.position, this.isWhite);
		} else if(promotionPiece == Math.signum(stone) * Chess.BLACK_KNIGHT) {
			piece = new Knight(this.position, this.isWhite);
		} else {
			System.out.println("Error with promoting piece");
			piece = null;
		}
		
		Board.addPiece(piece);
		Board.RemovePiece(this.position);
		this.captured = true;
		return piece;
	}
	
	Pawn(int startingPos, boolean color) {
		position = startingPos;
		isWhite = color;
		if(isWhite) {
			stone = Chess.WHITE_PAWN;
		} else {
			stone = Chess.BLACK_PAWN;
		}
		//to update attacking squares
		Board.addPiece(this);
		GetMoveableSquares();
	}
}
