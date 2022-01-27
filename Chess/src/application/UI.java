package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {
	
	public static void clearScreen() {
		System.out.print("0\033[H\033[2J");
		System.out.flush();
	}
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
    public static final String RED_BACKGROUND = "\033[41m";    // RED
    public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
    public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
    public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE
    
    public static ChessPosition readChessPosition(Scanner sc) {
    	
    	try {
    	String s = sc.nextLine();
    	char column = s.charAt(0);
    	int row = Integer.parseInt(s.substring(1));
    	return new ChessPosition(column, row);
    	}
    	catch (RuntimeException e) {
    		throw new InputMismatchException("Error reading ChessPosition.Valid values are from a1 to h8");
    		
    	}
    }
    
    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
    	printBoard(chessMatch.getPieces());
    	System.out.println();
    	printCapturedPieces(captured);
    	System.out.println();
    	System.out.println("Turn: " +  chessMatch.getTurn());
    	if(!chessMatch.getCheckMate()) {
    		System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());
    		if (chessMatch.getCheck()){
    			System.out.println("CHECK!!!");
    		}
    	}
    	else {
    		System.out.println("CHECKMATE!!!");
    		System.out.println("WINNER: " + chessMatch.getCurrentPlayer());
    	}
    }
	
	
	public static void printBoard(ChessPiece[][] pieces) {
		for (int i = 0; i<pieces.length; i++) {
			System.out.print((8 - i) + " ");
			for(int j = 0; j<pieces.length; j++) {
				printPiece(pieces[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  A B C D E F G H");
	}
	
	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
		for (int i = 0; i<pieces.length; i++) {
			System.out.print((8 - i) + " ");
			for(int j = 0; j<pieces.length; j++) {
				printPiece(pieces[i][j], possibleMoves[i][j]);
			}
			System.out.println();
		}
		System.out.println("  A B C D E F G H");
	}
	
	private static void printPiece(ChessPiece piece, boolean background) {
		if(background) {
			System.out.print(BLUE_BACKGROUND);
		}
		if (piece == null) {
			System.out.print("-" + ANSI_RESET);
		}
		else {
			if (piece.getColor() == Color.WHITE) {
				System.out.print(ANSI_WHITE + piece + ANSI_RESET );
			}
			else {
				System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}
		
	private static void printCapturedPieces(List<ChessPiece> captured) {
		List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
		List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
		System.out.println("Captured pieces: " );
		System.out.print("White: ");
		System.out.println(ANSI_WHITE);
		System.out.println(Arrays.toString(white.toArray()));
		System.out.println(ANSI_RESET);
		System.out.print("Black: ");
		System.out.println(ANSI_YELLOW);
		System.out.println(Arrays.toString(black.toArray()));
		System.out.println(ANSI_RESET);
	}

}
