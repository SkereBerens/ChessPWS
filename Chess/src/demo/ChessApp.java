package demo;
import java.io.IOException;
import java.util.Arrays;
import ControllTest.*;
import GUITest.*;

import chesspresso.*;

public class ChessApp {
	
	
	public static void main(String[] args) throws IOException {
		 //Chess is geimporteert van een opensource (chesspresso)
		//Site for putting in FEN
		//http://www.ee.unb.ca/cgi-bin/tervo/fen.pl?select=rnbqkbnr%2Fpp1ppppp%2F8%2F2p5%2F4P3%2F5N2%2FPPPP1PPP%2FRNBQKB1R+b+
		/*
		Board.updatePosition();
		
		System.out.println(Board.GetFEN());
		
		((King)king).CastleLong();
		Board.updatePosition();
		System.out.println(Board.GetFEN());
		((King)king1).CastleShort();
		Board.updatePosition();
		System.out.println(Board.GetFEN());*/
		Guihihi gui = new Guihihi();
		Model model = new Model();
		System.out.println(Controlla.GetPieceOfPieceGUI((PieceGUI)gui.rook));
		System.out.println(((PieceGUI)gui.rook).position);
		//System.out.println(rook1.position);
	}
}
