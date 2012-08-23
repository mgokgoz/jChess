package dummy;

import gokgoz.jchess.ChessBoard;

public class Test {
	public static void main(String args[]){
		ChessBoard b = new ChessBoard();
		//0-->a,1-->b.... , 0-->8,1-->7...
//		b.movePiece(0, 7, 0, 0);
		b.movePiece(2, 1, 2, 3);
//		b.movePiece(2, 3, 2, 4);
//		b.movePiece(2, 4, 2, 5);
//		b.movePiece(2, 5, 2, 6);
//		b.movePiece(2, 6, 2, 7);
		ChessBoard c = new ChessBoard("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1");
		c.printBoardwithInfo();
		b.printBoardwithInfo();
	}
}
