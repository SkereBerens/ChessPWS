package model;

import java.io.IOException;
import java.util.ArrayList;

import chesspresso_OpenSourceCode.Chess;
import controller.Controller;
import engine.Engine;

public class Piece extends Thread {
	public int stone;
	public int position;
	public boolean isWhite;
	public boolean isPinned = false;
	public boolean captured = false;
	//for castling
	public boolean hasMoved = false;
	public int[] directionIndicesIfPinned;
	public int[] directionIndices;
	static int[] possibleDirections = {7,-7,9,-9,8,-8,1,-1,6,-6,15,-15,10,-10,17,-17};
	
	
	public static final int[] bishopDirIndices =  {0,1,2,3};
	public static final int[] queenDirIndices =  {0,1,2,3,4,5,6,7};
	public static final int[] rookDirIndices =  {4,5,6,7};
	public static final int[] knightDirIndices =  {8,9,10,11,12,13,14,15};
	
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
	
	public King king;
	public Piece attackingPiece;
	
	//TEMPORARY MOVE ORDER THING
	public static int ply = 1;
	public static int fullMoves = 1;
	public int previousPosition = -1;
	public String previousPositionFENAddOn;
	public Piece capturedPiece;
	
	public void MoveTo(int position, boolean modelOnly) throws IOException {
		//previousPosition = null;
		if(!captured) {
			king = ((King)Board.GetKing(stone));
			attackingPiece = king.GetCheckingPiece();
				for(int square : GetLegalMoves()) {
					if(square == position) {
						previousPositionFENAddOn = Board.GetFENAdvancedAddOn();
						// 0 want dan staat er niemand
						boolean isOppositeColor = Board.GetPositionGrid()[position] != 0 && Chess.stoneToColor(Board.GetPositionGrid()[position]) != Chess.stoneToColor(stone);
						if(isOppositeColor) {
							Capture(position, modelOnly);
							//-1 because MiscMoveFunctions adds 1
							movesTilDraw = -1;
						} else {
							capturedPiece = null;
						}
						
						
						
						for(Piece p : Board.activePieces) {
							p.previousPosition = p.position;
						}
						
						previousPosition = this.position;
						
						
						if(!modelOnly) {
							Controller.MovePieceGUI(this, position);
							
						}
						
						this.position = position;
						movesTilDraw++;
						MiscMoveFunctions(modelOnly);
						break;
					}
				}
			
		}
	}
	
	//for testing
	public void RevertMove(int previousPosition, Piece capturedPiece, String previousPositionFENAddOn, int previousPositionRookLong, int previousPositionRookShort) {	
		for(Piece piece : Board.activePieces) {
			piece.isPinned = false;
			piece.king = null;
			piece.attackingPiece = null;
			piece.hasMoved = false;
			if(Chess.stoneToPiece(piece.stone) == 6) {
				((King) piece).isInCheck = false;
				((King) piece).doubleCheck = false;
				((King) piece).attackingPiece = null;
				((King) piece).hasCastlingRightsShort = false;
				((King) piece).hasCastlingRightsLong = false;
			}
			
			if(Chess.stoneToPiece(piece.stone) == 5) {
				((Pawn) piece).enpassantableSquare = -1;
				((Pawn) piece).isPinnedForEnPassant = false;
			}
		}
		Gamemanager.positionsFen.removeAll(Gamemanager.positionsFen);
		
		if(Chess.stoneToPiece(stone) == Chess.KING && previousPositionRookLong != -1 && previousPositionRookShort != -1) {
			((King) this).rookLong.position = previousPositionRookLong;
			((King) this).rookShort.position = previousPositionRookShort;
		}

		//for promotion reverting (collosal failure)
		
		if(Chess.stoneToPiece(stone) == Chess.PAWN && ((Pawn) this).PromotionPiece != null) {
			Board.RemovePiece(((Pawn) this).PromotionPiece.position);
			((Pawn) this).PromotionPiece = null;
			Board.addPiece(this);
		}
		
		this.position = previousPosition;
		
		UnCapturePiece(capturedPiece);
		Board.updatePosition();
		//pin pieces that should be pinned

		Board.LoadFENAdvancedAddOn(previousPositionFENAddOn);
		for(Piece slidingPiece : Board.GetSlidingPieces(stone)) {
			slidingPiece.Pin();
		}
		((King) Board.GetKing(-stone)).isInCheck();
		Board.updatePosition();
	}
	
	//for fifty move rule
	public static int movesTilDraw;
	
	void MiscMoveFunctions(boolean modelOnly) throws IOException {
		Board.updatePosition();
		king.isInCheck = false;
		hasMoved = true;
		
		for(Piece opps : Board.activePieces) {
			//unpin all pieces
			if(Math.signum(opps.stone) == -Math.signum(stone)) {

				opps.isPinned = false;
			}
			
			//make enemy piece not enpassantable
			if(opps.stone ==  Math.signum(stone) * Chess.WHITE_PAWN) {
				((Pawn) opps).isPinnedForEnPassant = false;
				((Pawn) opps).enpassantableSquare = -1;
			}
		}
		
		//pin pieces that should be pinned
		for(Piece slidingPiece : Board.GetSlidingPieces(stone)) {
			slidingPiece.Pin();
		}
		
		
		//Check if opposite king is in check
		((King) Board.GetKing(-stone)).isInCheck();
		king.CheckLongCastlingRights();
		king.CheckShortCastlingRights();
		((King) Board.GetKing(-stone)).CheckLongCastlingRights();
		((King) Board.GetKing(-stone)).CheckShortCastlingRights();
		ply++;
		
		if(!isWhite) {
			fullMoves++;
		}
		
		Board.updatePosition();
		if(!modelOnly) {
			outputGameOutcome(Gamemanager.GetGameOutcome());
		}
		
		//to fix bug where gui didnt update until engine had finished calculating
		if(!modelOnly) {
			new Piece().start();
		}
	}
	
	public static boolean engineOn = true;
	
	@Override
	public void run() {
		try {
			MoveForEngine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void MoveForEngine() throws IOException {
		if(ply % 2 == 0 && engineOn) {
			for(Piece p : Board.activePieces) {
				p.previousPosition = p.position;
			}
			
			int[] bestMove = Engine.GetBestMove();
			
			if(bestMove[1] == -2) {
				for(Piece p2 : Board.activePieces) {
					for(int move : p2.GetLegalMoves()) {
						p2.MoveTo(move, false);
					}
				}
			}
			
			if(bestMove[1] == 0 && bestMove[2] == 0) {
				System.out.println("You beat him!!");
			}
			
			Board.FindPieceByPosition(bestMove[1]).MoveTo(bestMove[2], false);
		}
	}
	
	void outputGameOutcome(int gameOutcome) {
		if(gameOutcome == 1) {
			System.out.println("Black won");
		}
		
		if(gameOutcome == -1) {
			System.out.println("White won");
		}
		
		if(gameOutcome == 0) {
			System.out.println("Draw");
		}
	}
	
	ArrayList<Integer> attackSquaresList = new ArrayList<Integer>();
	public int attackDirectionIndex;
	public int[] GetAttackingSquares() {
		attackSquaresList.removeAll(attackSquaresList);
		if(!captured) {
			for(int directionIndex : directionIndices) {
				for(int i = 1; i < Chess.NumSquaresToEdge[this.position][directionIndex] + 1; i++) {
					attackSquaresList.add(this.position + possibleDirections[directionIndex] * i);
					if(this.position + possibleDirections[directionIndex] * i == Board.GetKing(-stone).position) {
						attackDirectionIndex = directionIndex;
					}
					if(Board.GetPositionGrid()[this.position + possibleDirections[directionIndex] * i] != 0 && this.position + possibleDirections[directionIndex] * i != Board.GetKing(-stone).position) {
						break;
					} 
				}
			}
		}
		
		return attackSquaresList.stream().mapToInt(Integer::intValue).toArray();
	}
	
	//for slidey pieces (bishop, rook, queen)
	ArrayList<Integer> moveableSquaresList = new ArrayList<Integer>();
	int[] GetMoveableSquares() {
		moveableSquaresList.removeAll(moveableSquaresList);
		if(!captured && !isPinned) {
			for(int directionIndex : directionIndices) {
				for(int i = 1; i < Chess.NumSquaresToEdge[this.position][directionIndex] + 1; i++) {
						if(Board.GetPositionGrid()[this.position + possibleDirections[directionIndex] * i] == 0) {
							moveableSquaresList.add(this.position + possibleDirections[directionIndex] * i);
						} else if(Chess.stoneToColor(Board.GetPositionGrid()[this.position + possibleDirections[directionIndex] * i]) == Chess.stoneToColor(stone)){
							break;
						} else {
							moveableSquaresList.add(this.position + possibleDirections[directionIndex] * i);
							break;
						}
					
				}
			}
		} else if(!captured && isPinned) {
			for(int directionIndex : directionIndicesIfPinned) {
				for(int i = 1; i < Chess.NumSquaresToEdge[this.position][directionIndex] + 1; i++) {
						if(Board.GetPositionGrid()[this.position + possibleDirections[directionIndex] * i] == 0) {
							moveableSquaresList.add(this.position + possibleDirections[directionIndex] * i);
						} else if(Chess.stoneToColor(Board.GetPositionGrid()[this.position + possibleDirections[directionIndex] * i]) == Chess.stoneToColor(stone)){
							break;
						} else {
							moveableSquaresList.add(this.position + possibleDirections[directionIndex] * i);
							break;
						}
					
				}
			}
		}
		
		return moveableSquaresList.stream().mapToInt(Integer::intValue).toArray();
	}
	//CHECKS
	//
	//
	int[] blocksquaresCheck;
	int[] GetMoveableSquaresInCheck() {
		moveableSquaresList.removeAll(moveableSquaresList);
		blocksquaresCheck = GetBlockSquares();
		if(!captured && !isPinned) {
			for(int directionIndex : directionIndices) {
				for(int i = 1; i < Chess.NumSquaresToEdge[this.position][directionIndex] + 1; i++) {
					if(Math.signum(Board.GetPositionGrid()[this.position + possibleDirections[directionIndex] * i]) == Math.signum(stone)) {
						break;
					}
					if(Math.signum(Board.GetPositionGrid()[this.position + possibleDirections[directionIndex] * i]) == -Math.signum(stone)) {
						if(this.position + possibleDirections[directionIndex] * i != attackingPiece.position) {
							break;
						}
						moveableSquaresList.add(this.position +  possibleDirections[directionIndex] * i);
						break;
					}
					if(Chess.IsInList(blocksquaresCheck, this.position +  possibleDirections[directionIndex] * i)) {
						moveableSquaresList.add(this.position +  possibleDirections[directionIndex] * i);
					}
				}
			}
		}
		return moveableSquaresList.stream().mapToInt(Integer::intValue).toArray();
	}
	
	
	ArrayList<Integer> blockSquaresList = new ArrayList<Integer>();
	int tempPos;
	int attackDirection;
	//Gets squares where you block or capture the checking piece
	int[] GetBlockSquares() {
		blockSquaresList.removeAll(blockSquaresList);
		if(attackingPiece.stone == Math.signum(stone) * Chess.WHITE_QUEEN
		|| attackingPiece.stone == Math.signum(stone) * Chess.WHITE_BISHOP
		|| attackingPiece.stone == Math.signum(stone) * Chess.WHITE_ROOK) {
			
			tempPos = king.position;
			attackDirection = possibleDirections[Chess.GetOppositeDirectionIndex(attackingPiece.attackDirectionIndex)];
			while(tempPos != attackingPiece.position) {
				tempPos += attackDirection;
				blockSquaresList.add(tempPos);
			}
			
		} else {
			blockSquaresList.add(attackingPiece.position);
		}
		
		return blockSquaresList.stream().mapToInt(Integer::intValue).toArray();
	}
	
	//PINS
	//
	//
	Piece pinnedPiece;
	boolean isHorizontalDir;
	boolean isNotAtBoardEdge;
	boolean seesFriendPThenEnemyP;
	boolean seesEnemyPThenFriendP;
	boolean seesEmpty;
	boolean seesFriend;
	
	public void Pin() {
		if(!captured) {
			for(int directionIndex : directionIndices) {
				for(int i = 1; i < Chess.NumSquaresToEdge[this.position][directionIndex] + 1; i++) {
					
					isHorizontalDir = directionIndex == 6 || directionIndex == 7;
					isNotAtBoardEdge = Chess.NumSquaresToEdge[this.position + possibleDirections[directionIndex] * i][directionIndex] != 0;
					seesEnemyPThenFriendP = isNotAtBoardEdge && Board.GetPositionGrid()[this.position + possibleDirections[directionIndex] * i] == Math.signum(stone) * Chess.WHITE_PAWN && Board.GetPositionGrid()[this.position + possibleDirections[directionIndex] * (i + 1)] == Math.signum(stone) * Chess.BLACK_PAWN;
					seesFriendPThenEnemyP = isNotAtBoardEdge && Board.GetPositionGrid()[this.position + possibleDirections[directionIndex] * i] == Math.signum(stone) * Chess.BLACK_PAWN &&  Board.GetPositionGrid()[this.position + possibleDirections[directionIndex] * (i + 1)] == Math.signum(stone) * Chess.WHITE_PAWN;
					seesEmpty = Board.GetPositionGrid()[this.position + possibleDirections[directionIndex] * i] == 0;
					seesFriend = Chess.stoneToColor(Board.GetPositionGrid()[this.position + possibleDirections[directionIndex] * i]) == Chess.stoneToColor(stone);
					
					if(seesEmpty) {
						//continue, because empty
					} else if (isHorizontalDir && seesEnemyPThenFriendP){
						PinEnPassantPawn(directionIndex, i, 0);
						break;
					} else if(isHorizontalDir && seesFriendPThenEnemyP) {
						PinEnPassantPawn(directionIndex, i, 1);
						break;
					} else if(seesFriend){
						break;
					} else {
						if(IsNextPieceKing(directionIndex, i)) {
							pinnedPiece = Board.FindPieceByPosition(this.position + possibleDirections[directionIndex] * i);
							pinnedPiece.isPinned = true;
							pinnedPiece.directionIndicesIfPinned = new int[] {directionIndex, Chess.GetOppositeDirectionIndex(directionIndex)};
							HandleBishopPin();
							HandleRookPin();
						}
						break;
					}
				}
			}
		}
	}
	
	//friendlyfirst: 0 if encounter enemy, else 1
	void PinEnPassantPawn(int directionIndex, int i, int friendlyFirst) {
		for(int j = 1; j < Chess.NumSquaresToEdge[this.position + possibleDirections[directionIndex] * i][directionIndex] + 1; j++) {
			if(this.position + possibleDirections[directionIndex] * (i + 1) + possibleDirections[directionIndex] * j == Board.GetKing(-stone).position) {
				((Pawn) Board.FindPieceByPosition(this.position + possibleDirections[directionIndex] * (i + friendlyFirst))).isPinnedForEnPassant = true;
			} else if(Board.GetPositionGrid()[this.position + possibleDirections[directionIndex] * (i +1) + possibleDirections[directionIndex] * j] != 0) {
				break;
			}
		}
	}
	
	boolean IsNextPieceKing(int directionIndex, int i) {
		
		for(int j = 1; j < Chess.NumSquaresToEdge[this.position + possibleDirections[directionIndex] * i][directionIndex] + 1; j++) {
			if(this.position + possibleDirections[directionIndex] * i + possibleDirections[directionIndex] * j == Board.GetKing(-stone).position) {
				return true;
			} else if(Board.GetPositionGrid()[this.position + possibleDirections[directionIndex] * i + possibleDirections[directionIndex] * j] != 0) {
				return false;
			}
		}
		return false;
	}
	
	void HandleBishopPin() {
		if(pinnedPiece.stone == Chess.BLACK_BISHOP || pinnedPiece.stone == Chess.WHITE_BISHOP) {
			if(pinnedPiece.directionIndicesIfPinned[0] == 4 || pinnedPiece.directionIndicesIfPinned[0] == 5 || pinnedPiece.directionIndicesIfPinned[0] == 6 || pinnedPiece.directionIndicesIfPinned[0] == 7) {
				pinnedPiece.directionIndicesIfPinned = new int[] {};
			}
		}
	}
	
	void HandleRookPin() {
		if(pinnedPiece.stone == Chess.BLACK_ROOK || pinnedPiece.stone == Chess.WHITE_ROOK) {
			if(pinnedPiece.directionIndicesIfPinned[0] == 0 || pinnedPiece.directionIndicesIfPinned[0] == 1 || pinnedPiece.directionIndicesIfPinned[0] == 2 || pinnedPiece.directionIndicesIfPinned[0] == 3) {
				pinnedPiece.directionIndicesIfPinned = new int[] {};
			}
		}
	}
	
	//still have to add double checks and pins 
	public int[] GetLegalMoves() {
		if(ply % 2 == 0 && !isWhite || ply % 2 != 0 && isWhite) {
			king = ((King)Board.GetKing(stone));
			attackingPiece = king.GetCheckingPiece();
			if(((King)Board.GetKing(stone)).doubleCheck && this.stone != (int) Math.signum(stone) * Chess.BLACK_KING) {
				return new int[] {};
			}  else if(((King)Board.GetKing(stone)).isInCheck) {
				return GetMoveableSquaresInCheck();
			}
			return GetMoveableSquares();
		}
		return new int[] {};
	}
	
	
	void Capture(int position, boolean modelOnly) {
		capturedPiece = Board.FindPieceByPosition(position);
		//because after taking, all previous pos no longer possible to replicate
		Gamemanager.positionsFen.removeAll(Gamemanager.positionsFen);
		
		if(!modelOnly) {
			Controller.CapturePiece(Board.FindPieceByPosition(position));
		}
		
		Board.RemovePiece(position);
	}
	
	void UnCapturePiece(Piece p) {
		if(p == null) {
			return;
		}
		Board.addPiece(p);
		p.captured = false;
	}
}
