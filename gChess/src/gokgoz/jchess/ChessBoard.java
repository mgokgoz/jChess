package gokgoz.jchess;

import java.util.StringTokenizer;

/*
* TODO promoting piece -> asking piece to promote (ex. to queen)
* checking illegal moves,
* -check status,
* -if a piece protecting king,
* lots of other stuff :)
* 
* -FEN initializing missing captured pieces part
*/

// One thing about constructor: Board Directly initialize in class not in the constructor
// and can be separated from constructor by using initBoard function taking board as a parameter 
// if default constructor is used this initial setup of board can be send and if board is given by user 
// that value also can be sent to function so it makes the class flexible 
//ex.
/*
 * public class ChessBoard{
 * 
 * private static final char [][]InitialBoard{};
 * private static final char [][]EmptyBoard{};
 * 
 * 	public ChessBoard(){
 * 		initBoard(InitialBoard);
 * 	}
 * 	public ChessBoard(char[][]Board){
 * 		
 * 		if(Legal(Board)){initBoard(InitialBoard);}
 * 		else{
 * 			//One option is exception throw or just ignore input and initialize board with InitialBoard
 * 			initBoard(InitialBoard);
 * 		}
 * 	} 
 * 
 * }
 * 
 *  
 * PGN
 * http://en.wikipedia.org/wiki/Portable_Game_Notation
 * 
 * */

public class ChessBoard {
	private char[][] board= {{'r','n','b','q','k','b','n','r'},
			{'p','p','p','p','p','p','p','p'},
			{'-','-','-','-','-','-','-','-'},
			{'-','-','-','-','-','-','-','-'},
			{'-','-','-','-','-','-','-','-'},
			{'-','-','-','-','-','-','-','-'},
			{'P','P','P','P','P','P','P','P'},
			{'R','N','B','Q','K','B','N','R'}
	};
	private String cwpieces=""; 	//holds captured white pieces
	private String cbpieces=""; 	//holds captured black pieces
	private boolean canW00;  //Castle King Side White
	private boolean canW000; //Castle Queen Side White
	private boolean canB00; // Castle  King Side Black
	private boolean canB000; //Castle Queen Side Black
	private boolean isWPlaying; //white's turn or not
	private int halfmove;
	private int fullmove;
	String enpassant="";	//holds En passant target square
	public ChessBoard(){
		canW00=true;
		canW000=true;
		canB00=true;
		canB000=true;
		halfmove=0;
		fullmove=0;
		isWPlaying=true;
	}
	public ChessBoard(String FEN)
	{
		StringTokenizer st = new StringTokenizer(FEN,"/"); //to get lines
		String status="";
		int i=0;
		while(st.hasMoreTokens()){
			String s = st.nextToken();
			if(s.length()>8){	//means that no more line
				status=s.substring(8); //so get game status to this string
				s=s.substring(0, 8);
			}
			board[i] = makeLine(s);
			i++;
		}
		StringTokenizer st2 = new StringTokenizer(status," "); //split it
		String ns; //to manupulate tokens
		
		if(st2.nextToken().equals("w")){ // get whose turn
			isWPlaying = true;
		}
		else{
			isWPlaying = false;
		}
		
		ns=st2.nextToken();		//get castling
		System.out.println(ns);
		if(ns.contains("K")){
			canW00=true;
		}
		else{
			canW00=false;
		}
		if(ns.contains("Q")){
			canW000=true;
		}
		else{
			canW000=false;
		}
		if(ns.contains("k")){
			canB00=true;
		}
		else{
			canB00=false;
		}
		if(ns.contains("q")){
			canB000=true;
		}
		else{
			canB000=false;
		}
		//rest is self explanatory
		enpassant = st2.nextToken();	
		
		halfmove=Integer.parseInt(st2.nextToken());
		fullmove=Integer.parseInt(st2.nextToken());
	}

	public void printBoard(){
		int i;
		System.out.println();
		for(i=0;i<8;i++){

			System.out.println(board[i]);

		}
		System.out.println();
	}
	
	public void printBoardwithInfo(){//will print board and all information
		printBoard();
		String p="";
		if(isWPlaying())
			p="White";
		else
			p="Black";
		System.out.println("Turn: " + p + "\t\tMove: " + fullmove + "\t\tHalf Move: "+ halfmove);
		System.out.println("Can White 0-0: " + canW00 + "\tCan White 0-0-0: " + canW000);
		System.out.println("Can Black 0-0: " + canB00 + "\tCan Black 0-0-0: " + canB000);
		System.out.println("En passant target square:"+enpassant);
	}

	public void setBoard(char[][] b){
		board=b;
	}

	public boolean isCanW00() {
		return canW00;
	}

	public void setCanW00(boolean canW00) {
		this.canW00 = canW00;
	}

	public boolean isCanW000() {
		return canW000;
	}

	public void setCanW000(boolean canW000) {
		this.canW000 = canW000;
	}

	public boolean isCanB00() {
		return canB00;
	}

	public void setCanB00(boolean canB00) {
		this.canB00 = canB00;
	}

	public boolean isCanB000() {
		return canB000;
	}

	public void setCanB000(boolean canB000) {
		this.canB000 = canB000;
	} 

	public char[][] getBoard() {
		return board;
	}

	public char getPiece(int ch, int num){
		//getting piece at place to move
		return board[num][ch];
	}

	public void setPiece(int ch,int num,char piece){
		//not checking if move is legal or not, just placing
		board[num][ch] = piece;
	}
	public boolean isWPlaying() {
		return isWPlaying;
	}

	public void setWPlaying(boolean isWPlaying) {
		this.isWPlaying = isWPlaying;
	}

	public String getCwpieces() {
		return cwpieces;
	}
	public void setCwpieces(String cwpieces) {
		this.cwpieces = cwpieces;
	}
	public String getCbpieces() {
		return cbpieces;
	}
	public void setCbpieces(String cbpieces) {
		this.cbpieces = cbpieces;
	}
	public int getHalfmove() {
		return halfmove;
	}
	public void setHalfmove(int halfmove) {
		this.halfmove = halfmove;
	}
	public int getFullmove() {
		return fullmove;
	}
	public void setFullmove(int fullmove) {
		this.fullmove = fullmove;
	}
	public String getEnpassant() {
		return enpassant;
	}
	public void setEnpassant(String enpassant) {
		this.enpassant = enpassant;
	}
	//!!!
	//Why don't we ask first to user which piece to promote and give the result as a parameter to this functions
	//!!! ans: we will ask, as it has already noted right there as "will change". it just for testing now
	public void promote_black(int ch,int num){//will change
		char promote_to = 'q'; //test value
		board[num][ch]=promote_to;
	}


	public void promote_white(int ch,int num){//will change
		char promote_to = 'Q'; //test value
		board[num][ch]=promote_to;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isWhite(int ch,int num){
		char p=getPiece(ch,num);
		if(p=='K' || p=='Q' || p=='R' || p=='N' || p=='B' || p=='P')
			return true;
		else
			return false;
	}

	public boolean isWhite(char p){
		if(p=='K' || p=='Q' || p=='R' || p=='N' || p=='B' || p=='P')
			return true;
		else
			return false;
	}


	/*
	 * The algorithm that I think is as follows
	 * 
	 *  movePiece(current_ch, current_num ,new_ch, new_num)
	 *  check Turn (White's Black's)
	 *  check Picked Piece (Black or White)
	 *  check whether the move is legal or not for the piece // capturing own piece can be added here to check legality and 
	 *  check whether the move captures piece or not
	 *  
	 *  If all true then capture piece if necessary then switch the turn
	 *  else keep the turn as it is and wait for new move 
	 */
	public void movePiece(int current_ch,int current_num,int new_ch,int new_num){
		char piece = getPiece(current_ch,current_num);

		boolean isWhite = isWhite(piece);

		if(piece == 'p')
		{
			System.out.println("got piece");
			//TODO
			//capturing piece part
			//
			//
			//
			// Bana Bunu bi anlat ya :D -- piyon bi çýkýþý ile iki çýkýþý kontorlü :D bu arada piyonun oynadýðý nokta dolumu deðil mi
			// capture kýsmý yok daha. onun için bi else if çekicez. the amazing mustafa's movement algorithm adý 
			//biz daha iyisini yapana kadar, daha iyisini yaparlar :D
			if(new_ch==current_ch && (getPiece(new_ch,new_num)=='-' && ((new_num == current_num+1) || ((new_num == current_num+2) && (current_num==1) && getPiece(current_ch,current_num+1)=='-'))))
			{
				board[new_num][new_ch]=board[current_num][current_ch];
				board[current_num][current_ch]='-';
				if(new_num == current_num+2)
					makeEnPassantTarget(current_ch,current_num+1);
				if(new_num==7){
					promote_black(new_ch,new_num);
				}
			}

			else
			{
				System.out.println("illegal");
			}

		}
		else if(piece== 'P')
		{
			System.out.println("got Piece");
			//TODO
			//capturing piece part
			//

			if(new_ch==current_ch && (getPiece(new_ch,new_num)=='-' && ((new_num == current_num-1) || ((new_num == current_num-2) && (current_num==6) && getPiece(current_ch,current_num-1)=='-'))))
			{

				board[new_num][new_ch]=board[current_num][current_ch];
				board[current_num][current_ch]='-';
				if(new_num == current_num-2)
					makeEnPassantTarget(current_ch,current_num-1);
				if(new_num==0){
					promote_white(new_ch,new_num);
				}
			}

			else
			{
				System.out.println("illegal");
			}
		}
		else if(piece=='r' || piece=='R')
		{
			System.out.println("got rock");
		}
		else if(piece=='n' || piece=='N')
		{
			System.out.println("got knight");
		}
		else if(piece=='b' || piece=='B')
		{
			System.out.println("got bishop");
		}
		else if(piece=='q' || piece=='Q')
		{
			System.out.println("got Queen");
		}
		else if(piece=='k' || piece=='K')
		{
			System.out.println("got Knight");
		}
		else
		{
			System.out.println("got nothing");
		}
	}

	private void makeEnPassantTarget(int ch, int num) {
		enpassant=convertToNotation('p',ch,num,false,false,false,false);
	}
	
	private String convertToNotation(char piece, int ch, int num, boolean captured, boolean castling, boolean check, boolean checkmate) {
		String s="";
		if(checkmate){
			if(isWPlaying){
				s="1-0";
			}
			else{
				s="0-1";
			}
		}
		else{
			s=s.concat(pieceToString(piece));
			if(captured){
				s=s.concat("x");
			}
			s=s.concat(indexToNotation(ch,num));
			if(check){
				s=s.concat("+");
			}
		}
		return s;
	}
	private String indexToNotation(int ch, int num) {
		String s="";
		int i=97,j=56; //97 => a   56 => 8
		i=i+ch;
		j=j-num;
		s=s.concat(String.valueOf((char)i));
		s=s.concat(String.valueOf((char)j));
		return s;
	}
	private String pieceToString(char piece) {
		String s="";
		if(piece=='p' ||piece== 'P'){
			s="";
		}
		else if(piece=='r' ||piece== 'R'){
			s="R";
		}
		else if(piece=='n' ||piece== 'N'){
			s="N";
		}
		else if(piece=='b' ||piece== 'B'){
			s="B";
		}
		else if(piece=='q' ||piece== 'Q'){
			s="Q";
		}
		else if(piece=='k' ||piece== 'K'){
			s="K";
		}
		return s;
	}
	private char[] makeLine(String s){
		char[] line;
		String ns = "";
		int i;	
		for(i=0;i<s.length();i++)
		{
			if(s.charAt(i)=='8'){
				ns=ns.concat("--------");
			}
			else if(s.charAt(i)=='7'){
				ns=ns.concat("-------");
			}
			else if(s.charAt(i)=='6'){
				ns=ns.concat("------");
			}
			else if(s.charAt(i)=='5'){
				ns=ns.concat("-----");
			}
			else if(s.charAt(i)=='4'){
				ns=ns.concat("----");
			}
			else if(s.charAt(i)=='3'){
				ns=ns.concat("---");
			}
			else if(s.charAt(i)=='2'){
				ns=ns.concat("--");
			}
			else if(s.charAt(i)=='1'){
				ns=ns.concat("-");
			}
			else{
				ns=ns.concat(String.valueOf(s.charAt(i)));
			}
		}
		line=ns.toCharArray();
		return line;
	}
} 