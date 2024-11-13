/*
 * Copyright (C) Bernhard Seybold. All rights reserved.
 *
 * This software is published under the terms of the LGPL Software License,
 * a copy of which has been included with this distribution in the LICENSE.txt
 * file.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *
 * $Id: Chess.java,v 1.2 2002/12/18 19:47:58 BerniMan Exp $
 */

package chesspresso;


/**
 * General chess-specific definition.
 *
 * <p>The following methods are used often throughout the higher-level classes
 * and are therefore implemented as simple as possible. Sometimes, checking for
 * illegal arguments is left to the caller for optimized performance.
 *
 * <p>To del with squares, the concepts of square, column, and row index are
 * introduced. A square index is a number our of [0..63], a column (file) and
 * row (rank) are out of [0..7].
 *
 * <p>A piece is an uncolored type of chessman, a stone is a piece plus a colored.
 * For instance, possible pieces would be: a knight, a pawn, a king. Possible stones
 * are: a white queen, a black pawn. Possible colors (or players) are: white, black
 * and nobody.
 *
 * @author  Bernhard Seybold
 * @version $Revision: 1.2 $
 *
 */
public abstract class Chess
{
    
    /*========== squares, coordinates ==========*/
    
	/*
	 * IMPORTATNE VAN TISSA!!!!! @Ahad!!!!!!
	 * 
	 * 
	 * 
	 * Dit is geimporteertt van open source.
	 * Behalve de edges en getsquarestoEdge heb is allemaal open source 
	 * 
	 * 
	 * 
	 */
    public static final int
        NUM_OF_COLS = 8,
        NUM_OF_ROWS = 8,
        NUM_OF_SQUARES = NUM_OF_COLS * NUM_OF_ROWS;
    
    public static final int
        RES_WHITE_WINS = 0, RES_DRAW = 1, RES_BLACK_WINS = 2, RES_NOT_FINISHED = 3,
        NO_RES = -1;
    
    public static final int
        A8 = 56, B8 = 57, C8 = 58, D8 = 59, E8 = 60, F8 = 61, G8 = 62, H8 = 63,
        A7 = 48, B7 = 49, C7 = 50, D7 = 51, E7 = 52, F7 = 53, G7 = 54, H7 = 55,
        A6 = 40, B6 = 41, C6 = 42, D6 = 43, E6 = 44, F6 = 45, G6 = 46, H6 = 47,
        A5 = 32, B5 = 33, C5 = 34, D5 = 35, E5 = 36, F5 = 37, G5 = 38, H5 = 39,
        A4 = 24, B4 = 25, C4 = 26, D4 = 27, E4 = 28, F4 = 29, G4 = 30, H4 = 31,
        A3 = 16, B3 = 17, C3 = 18, D3 = 19, E3 = 20, F3 = 21, G3 = 22, H3 = 23,
        A2 =  8, B2 =  9, C2 = 10, D2 = 11, E2 = 12, F2 = 13, G2 = 14, H2 = 15,
        A1 =  0, B1 =  1, C1 =  2, D1 =  3, E1 =  4, F1 =  5, G1 =  6, H1 =  7;
    
    public static final int
        NO_COL = -1, NO_ROW = -1, NO_SQUARE = -1;
    
    //zelf geschreven
    public static final int[] EdgeSquares = {0,1,2,3,4,5,6,7,8,16,24,32,40,48,56,57,58,59,60,61,62,63,55,47,39,31,23,15};
    
    //zelf geschreven
    public static final int[]
    	NorthEdge  = {56,57,58,59,60,61,62,63},
    	EastEdge   = {7,15,23,31,39,47,55,63},
    	SouthEdge =  {0,1,2,3,4,5,6,7},
    	WestEdge =   {0,8,16,24,32,40,48,56};
    	
    /**
     * Converts coordinates to square index.
     *
     *@param col the column (file)
     *@param row the row (rank)
     *@return the square index
     */
    public static final int coorToSqi(int col, int row)
    {
        return row * NUM_OF_COLS + col;
    }
    
    /**
     * Extract the row of a square index.
     *
     *@param sqi the square index
     *@return the row
     */
    public static final int sqiToRow(int sqi)
    {
        return sqi / NUM_OF_COLS;
    }
    
    /**
     * Extract the column of a square index.
     *
     *@param sqi the square index
     *@return the column
     */
    public static final int sqiToCol(int sqi)
    {
        return sqi % NUM_OF_COLS;
    }
    
    /**
     * Returns the row difference from one square index to the other.
     *
     *@param sqi1 the one square index
     *@param sqi2 the other square index
     *@return the row difference from sqi1 to sqi2
     */
    public static final int deltaRow(int sqi1, int sqi2)
    {
        return (sqi2 / NUM_OF_COLS) - (sqi1 / NUM_OF_COLS);
    }
    
    /**
     * Returns the column difference from one square index to the other.
     *
     *@param sqi1 the one square index
     *@param sqi2 the other square index
     *@return the column difference from sqi1 to sqi2
     */
    public static final int deltaCol(int sqi1, int sqi2)
    {
        return (sqi2 % NUM_OF_COLS) - (sqi1 % NUM_OF_COLS);
    }
    
    /**
     * Returns the character of a column (file): 'a'..'h'.
     *
     *@param col the column
     *@return the character representing the column
     */
    public static final char colToChar(int col)
    {
        final char c[] = {'-', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        return c[col+1];
        //return String.valueOf('a' + (char)col);
    }
    
    /**
     * Returns the character of a row (rank): '1'..'8'.
     *
     *@param col the column
     *@return the character representing the column
     */
    public static final char rowToChar(int row)
    {
        final char r[] = {'-', '1', '2', '3', '4', '5', '6', '7', '8'};
        return r[row+1];
        //return String.valueOf('1' + (char)row);
    }
    
    /**
     * Returns the algebraic representation of a square "a1".."h8".
     *
     *@param sqi the square
     *@return the algebraic representation
     */
    public static final String sqiToStr(int sqi)
    {
        return new StringBuffer().append(colToChar(sqiToCol(sqi))).append(rowToChar(sqiToRow(sqi))).toString();
    }
    
    /**
     * Returns whether the square is white.
     *
     *@param sqi the square
     *@return whether sqi is a white square
     */
    public static final boolean isWhiteSquare(int sqi)
    {
        return ((sqiToCol(sqi) + sqiToRow(sqi)) % 2) != 0;
    }
    
    /**
     * Returns the column represented by the character.
     *
     *@param ch the column character ('a'..'h')
     *@return the column, or <code>NO_COL</code> if an illegal character is passed
     */
    public static final int charToCol(char ch)
    {
        if ((ch >= 'a') && (ch <= 'h')) {
            return (int)(ch - 'a');
        } else {
            return NO_COL;
        }
    }
    
    /**
     * Returns the row represented by the character.
     *
     *@param ch the row character ('1'..'8')
     *@return the column, or <code>NO_ROW</code> if an illegal character is passed
     */
    public static final int charToRow(char ch)
    {
        if ((ch >= '1') && (ch <= '8')) {
            return (int)(ch - '1');
        } else {
            return NO_ROW;
        }
    }
    
    /**
     * Converts a square representation to a square index.
     *
     *@param s the algebraic square representation
     *@return the square index, or <code>NO_SQUARE</code> if an illegal string is passed
     */
    public static final int strToSqi(String s)
    {
        if (s == null || s.length() != 2) return NO_SQUARE;
        int col = charToCol(s.charAt(0)); if (col == NO_COL) return NO_SQUARE;
        int row = charToRow(s.charAt(1)); if (row == NO_ROW) return NO_SQUARE;
        return coorToSqi(col, row);
    }
    
    /**
     * Converts a col and row character pair to a square index.
     *
     *@param col the row character
     *@param row the column character
     *@return the square index, or <code>NO_SQUARE</code> if an illegal character is passed
     */
    public static final int strToSqi(char colCh, char rowCh)
    {
        int col = charToCol(colCh); if (col == NO_COL) return NO_SQUARE;
        int row = charToRow(rowCh); if (row == NO_ROW) return NO_SQUARE;
        return coorToSqi(col, row);
    }
    
   /*========== pieces and stones ==========*/
    
    public static final short
        MIN_PIECE = 0, MAX_PIECE = 6,
        // promotion pieces are from 0 to 4 to allow compact coding of moves
        KING = 6, PAWN = 5, QUEEN = 4, ROOK = 3, BISHOP = 2, KNIGHT = 1,
        NO_PIECE = 0;
    
    public static final short
        MIN_STONE = -6, MAX_STONE = 6,
        WHITE_KING   = -6, WHITE_PAWN   = -5, WHITE_QUEEN  = -4, WHITE_ROOK   = -3,
        WHITE_BISHOP = -2, WHITE_KNIGHT = -1, 
        BLACK_KING   =  6, BLACK_PAWN   =  5, BLACK_QUEEN  =  4, BLACK_ROOK   =  3,
        BLACK_BISHOP =  2, BLACK_KNIGHT =  1, 
        NO_STONE  = NO_PIECE;
    
    public static final char pieceChars[] =
        {' ', 'N', 'B', 'R', 'Q', 'P', 'K', 'n', 'b', 'r', 'q', 'p', 'k'};
    
    /**
     * Extracts the color of a stone.
     *
     *@param stone the colored piece
     *@return the color of the stone
     */
    public static final int stoneToColor(int stone)
    {
        if      (stone < 0) {return WHITE;}
        else if (stone > 0) {return BLACK;}
        else                {return NOBODY;}
    }
    
    /**
     * Check whether the stone is of a certain color.
     *
     *@param stone the colored piece
     *@param color the color to test for
     *@return the true iff the stone is of the given color
     */
    public static final boolean stoneHasColor(int stone, int color)
    {
        return (color == WHITE && stone < 0) || (color == BLACK && stone > 0);
    }
    
    /**
     * Converts a stone to a piece (remove color info).
     *
     *@param stone the colored piece
     *@return the piece
     */
    public static final int stoneToPiece(int stone)
    {
        if (stone < 0) return -stone; else return stone;
    }
    
    /**
     * Change the color of the stone.
     *
     *@param stone the colored piece
     *@return the stone with inverse color
     */
    public static final int getOpponentStone(int stone)
    {
        return -stone;
    }
    
    /**
     * Converts a character to a piece.
     *
     *@param ch a piece character
     *@return the piece represented by the character, or <code>NO_PIECE</code> if illegal
     */
    public static final int charToPiece(char ch)
    {
        for (int piece=0; piece < pieceChars.length - 6; piece++) {
            if (pieceChars[piece] == ch) return piece;
        }
        return NO_PIECE;
    }
    
    public static final int charToStone(char ch) {
    	for(int stone = -6; stone <= 6; stone++) {
    		if(stone < 0 && pieceChars[stone * -1] == ch) return stone;
    		if(stone > 0 && pieceChars[stone + 6] == ch) return stone;
    	}
    	return NO_STONE;
    }
    
    /**
     * Returns a character representing the piece.
     *
     *@param piece the piece
     *@return the character representing the piece, or '?' if an illegal piece is passed
     */
    public static final char pieceToChar(int piece)
    {
        if (piece < 0 || piece > MAX_PIECE) return '?';
        return pieceChars[piece];
    }
    
    /**
     * Returns a character representing the stone.
     *
     *@param stone the stone
     *@return the character representing the stone, or '?' if an illegal piece is passed
     */
    //ge edit door mij hihi
    public static final char stoneToChar(int stone)
    {
        if (stone < 0) return pieceChars[-stone]; else return pieceChars[stone + 6];
    }
    
    /**
     * Converts a piece, color pair to a stone.
     *
     *@param piece the piece
     *@param color the color
     *@return the stone, or <code>NO_PIECE</code> if illegal
     */
    public static final int pieceToStone(int piece, int color)
    {
        if      (color == WHITE) {return -piece;}
        else if (color == BLACK) {return  piece;}
        else                     {return  NO_PIECE;}
    }
 
    
    /*========== players ==========*/
    
    public static final int
        WHITE = 0, BLACK = 1, NOBODY = -1;
    
    /**
     * Returns the opposite player.
     *
     *@param player the player (or color)
     *@return the opposite player (color respectively)
     */
    public static final int otherPlayer(int player)
    {
        return 1-player;
    }
    
    /*========== plies, moves ==========*/
    
    /**
     * Returns whether it is white move at the given ply.
     *
     *@param plyNumber the ply number, starting at 0
     */
    public static boolean isWhitePly(int plyNumber)
    {
        return plyNumber % 2 == 0;
    }
    
    /**
     * Converts a ply to a move number
     *
     *@param plyNumber the ply number, starting at 0
     */
    public static int plyToMoveNumber(int plyNumber)
    {
        return plyNumber / 2 + 1;
    }
    
    //
    //
    //
    //
    //
    //For getting legal moves: zelf geschreven
    static int[] possibleDirections = {7,-7,9,-9,8,-8,1,-1,6,-6,15,-15,10,-10,17,-17};
    public static int[][] NumSquaresToEdge = GetTotalNumSquaresToEdge();
    
    static int[][] GetTotalNumSquaresToEdge() {
    	int[][] NumSquaresToEdge = new int[64][possibleDirections.length];
    	for(int pos = 0; pos < 64; pos++) {
    		for(int dirIndex = 0; dirIndex < 16; dirIndex++) {
    			NumSquaresToEdge[pos][dirIndex] = GetNumSquaresToEdge(pos, possibleDirections[dirIndex]);
    		}
    	}
    	return NumSquaresToEdge;
    }
    
     public static int GetNumSquaresToEdge(int Pos, int direction) {
		int num = 0;
		int tempPos = Pos;
		if(direction == 7) {
			while(!IsInList(Chess.NorthEdge, tempPos) && !IsInList(Chess.WestEdge, tempPos)) {
				tempPos += direction;
				num++;
			}
		} else
		if(direction == -7) {
			while(!IsInList(Chess.SouthEdge, tempPos) && !IsInList(Chess.EastEdge, tempPos)) {
				tempPos += direction;
				num++;
			}
		} else
		if(direction == 9) {
			while(!IsInList(Chess.NorthEdge, tempPos) && !IsInList(Chess.EastEdge, tempPos)) {
				tempPos += direction;
				num++;
			}
		} else
		if(direction == -9) {
			while(!IsInList(Chess.SouthEdge, tempPos) && !IsInList(Chess.WestEdge, tempPos)) {
				tempPos += direction;
				num++;
			}
		} else
		if(direction == 1) {
			while(!IsInList(Chess.EastEdge, tempPos)) {
				tempPos += direction;
				num++;
			}
		} else
		if(direction == -1) {
			while(!IsInList(Chess.WestEdge, tempPos)) {
				tempPos += direction;
				num++;
			}
		} else
		if(direction == 8) {
			while(!IsInList(Chess.NorthEdge, tempPos)) {
				tempPos += direction;
				num++;
			}
		} else
		if(direction == -8) {
			while(!IsInList(Chess.SouthEdge, tempPos)) {
				tempPos += direction;
				num++;
			}
		} else 
		
		//for knight
		//only can return 1 or 0 for knight, so maybe problem for future
		
			//	15		17
			//6			  10
			//	  knight
			//-10		  -6
			//	-17		-15
		
		if(direction == 15) {
			if(!IsInList(Chess.NorthEdge, tempPos) && !IsInList(Chess.WestEdge, tempPos) && !IsInList(Chess.NorthEdge, tempPos + 8)) {
				num++;
			}
		} else if(direction == 6) {
			if(!IsInList(Chess.NorthEdge, tempPos) && !IsInList(Chess.WestEdge, tempPos) && !IsInList(Chess.WestEdge, tempPos - 1)) {
				num++;
			}
		} else if(direction == -10) {
			if(!IsInList(Chess.SouthEdge, tempPos) && !IsInList(Chess.WestEdge, tempPos) && !IsInList(Chess.WestEdge, tempPos -1)) {
				num++;
			}
		} else if(direction == -17) {
			if(!IsInList(Chess.SouthEdge, tempPos) && !IsInList(Chess.WestEdge, tempPos) && !IsInList(Chess.SouthEdge, tempPos -8)) {
				num++;
			}
		} else if(direction == -15) {
			if(!IsInList(Chess.SouthEdge, tempPos) && !IsInList(Chess.EastEdge, tempPos) && !IsInList(Chess.SouthEdge, tempPos - 8)) {
				num++;
			}
		} else if(direction == -6) {
			if(!IsInList(Chess.SouthEdge, tempPos) && !IsInList(Chess.EastEdge, tempPos) && !IsInList(Chess.EastEdge, tempPos + 1)) {
				num++;
			}
		} else if(direction == 10) {
			if(!IsInList(Chess.NorthEdge, tempPos) && !IsInList(Chess.EastEdge, tempPos) && !IsInList(Chess.EastEdge, tempPos + 1)) {
				num++;
			}
		} else if(direction == 17) {
			if(!IsInList(Chess.NorthEdge, tempPos) && !IsInList(Chess.EastEdge, tempPos) && !IsInList(Chess.NorthEdge, tempPos + 8)) {
				num++;
			}
		} 
		
		
		return num;
	}
	
    //zelf geschreven, used for detecting if on edge and if position is in position array voor integers
	public static boolean IsInList(int[] list, int Int) {
		for(int edge : list) {
			if(Int == edge) {
				return true;
			}
		}
		return false;
	}
	
	//voor characters lol
	public static boolean IsInListChr(char[] list, char ch) {
		for(char chr : list) {
			if(ch == chr) {
				return true;
			}
		}
		return false;
	}
	
	//{7,-7,9,-9,8,-8,1,-1,6,-6,15,-15,10,-10,17,-17}
	//zelf geschreven
	public static int GetOppositeDirectionIndex(int directionIndex) {
		if(directionIndex == 0) {
			return 1;
		} else if(directionIndex == 1) {
			return 0;
		} else if(directionIndex == 2) {
			return 3;
		} else if(directionIndex == 3) {
			return 2;
		} else if(directionIndex == 4) {
			return 5;
		} else if(directionIndex == 5) {
			return 4;
		} else if(directionIndex == 6) {
			return 7;
		} else if(directionIndex == 7){
			return 6;
		} 
		return -1;
	}
    
}