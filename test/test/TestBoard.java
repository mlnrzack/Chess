package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import chess.SideColor;
import chess.Position;
import chess.board.*;
import chess.pieces.*;

public class TestBoard
{
	@Test
	public void testConstrucorWithoutParams()
	{
		Board board = new BoardPvP();
		Chess_piece[][] figures = new Chess_piece[8][8];
		for(int i = 0 ; i < 8 ; i++) 
		{
			figures[i][6] = new Pawn(SideColor.WHITE, i, 6);
			figures[i][1] = new Pawn(SideColor.BLACK, i, 1);
		}		
		figures[0][7] = new Rook(SideColor.WHITE, 0, 7);
		figures[7][7] = new Rook(SideColor.WHITE, 7, 7);
		figures[0][0] = new Rook(SideColor.BLACK, 0, 0);
		figures[7][0] = new Rook(SideColor.BLACK, 7, 0);
		figures[1][7] = new Knight(SideColor.WHITE, 1, 7);
		figures[6][7] = new Knight(SideColor.WHITE, 6, 7);
		figures[1][0] = new Knight(SideColor.BLACK, 1, 0);
		figures[6][0] = new Knight(SideColor.BLACK, 6, 0);
		figures[2][7] = new Bishop(SideColor.WHITE, 2, 7);
		figures[5][7] = new Bishop(SideColor.WHITE, 5, 7);
		figures[2][0] = new Bishop(SideColor.BLACK, 2, 0);
		figures[5][0] = new Bishop(SideColor.BLACK, 5, 0);
		figures[3][7] = new Queen(SideColor.WHITE, 3, 7);
		figures[3][0] = new Queen(SideColor.BLACK, 3, 0);
		figures[4][7] = new King(SideColor.WHITE, 4, 7);
		figures[4][0] = new King(SideColor.BLACK, 4, 0);
		
		assertEquals(Board.getBoard()[0][7].getType(), figures[0][7].getType());
		assertEquals(Board.getBoard()[7][7].getType(), figures[7][7].getType());
		assertEquals(Board.getBoard()[1][7].getType(), figures[1][7].getType());
		assertEquals(Board.getBoard()[4][7].getType(), figures[4][7].getType());
		assertEquals(Board.getBoard()[3][0].getClass(), figures[3][0].getClass());
		assertNotEquals(Board.getBoard()[1][7].getType(), figures[0][7].getType());
		assertNotEquals(Board.getBoard()[0][7].getType(), figures[0][6].getType());
	}
	
	@Test
	public void testConstrucorWithParams()
	{
		Chess_piece[][] load = new Chess_piece[8][8];
		load[4][4] = new King(SideColor.WHITE, 4, 4);
		load[4][5] = new Queen(SideColor.WHITE, 4, 4);
		SideColor[][] color = new SideColor[8][8];
		color[4][4] = SideColor.WHITE;
		color[4][5] = SideColor.WHITE;
		
		Board board = new BoardPvP(load, color, true, 3);
		
		assertEquals(Board.getBoard()[4][4].getClass(), load[4][4].getClass());
		assertEquals(Board.getBoard()[4][5].getClass(), load[4][5].getClass());
		assertNotEquals(Board.getBoard()[4][4].getClass(), load[4][5].getClass());
	}
	
	@Test
	public void testFindKing()
	{
		Chess_piece board[][] = new Chess_piece[8][8];
		board[0][0] = new King(SideColor.BLACK, 0, 0);
		
		Position p = Board.findKing(board, SideColor.BLACK);
		assertEquals(p.getX() == 0 && p.getY() == 0 , true);
	}
	
	@Test
	public void testCheck()
	{
		Chess_piece board[][] = new Chess_piece[8][8];
		board[0][0] = new King(SideColor.BLACK, 0, 0);
		board[0][5] = new Rook(SideColor.WHITE, 0, 5);
		
		assertEquals(Board.check(board , SideColor.BLACK), true);
	}

	@Test
	public void testPreventCheck()
	{
		Board board = new BoardPvP();
		Chess_piece[][] figures = new Chess_piece[8][8];
		figures[5][5] = new Queen(SideColor.BLACK , 5 ,5);
		figures[5][0] = new King(SideColor.WHITE , 5,0);
		Bishop bishop =new Bishop(SideColor.WHITE , 5 ,1);
		figures[5][1] = bishop;
		
		assertEquals(Board.preventCheck(bishop.GetMoves(figures), figures, bishop).isEmpty(), true);
	}
	
	@Test
	public void testAllsafemoves()
	{
		Board board = new BoardPvP();
		ArrayList<Position> pos = Board.getAllSafeMoves(SideColor.WHITE, board.getBoard());

		Chess_piece[][] figures = new Chess_piece[8][8];
		for(int i = 0 ; i < 8 ; i++) 
		{
			figures[i][6] = new Pawn(SideColor.WHITE, i, 6);
			figures[i][1] = new Pawn(SideColor.BLACK, i, 1);
		}		
		figures[0][7] = new Rook(SideColor.WHITE, 0, 7);
		figures[7][7] = new Rook(SideColor.WHITE, 7, 7);
		figures[0][0] = new Rook(SideColor.BLACK, 0, 0);
		figures[7][0] = new Rook(SideColor.BLACK, 7, 0);
		figures[1][7] = new Knight(SideColor.WHITE, 1, 7);
		figures[6][7] = new Knight(SideColor.WHITE, 6, 7);
		figures[1][0] = new Knight(SideColor.BLACK, 1, 0);
		figures[6][0] = new Knight(SideColor.BLACK, 6, 0);
		figures[2][7] = new Bishop(SideColor.WHITE, 2, 7);
		figures[5][7] = new Bishop(SideColor.WHITE, 5, 7);
		figures[2][0] = new Bishop(SideColor.BLACK, 2, 0);
		figures[5][0] = new Bishop(SideColor.BLACK, 5, 0);
		figures[3][7] = new Queen(SideColor.WHITE, 3, 7);
		figures[3][0] = new Queen(SideColor.BLACK, 3, 0);
		figures[4][7] = new King(SideColor.WHITE, 4, 7);
		figures[4][0] = new King(SideColor.BLACK, 4, 0);
		
		assertNotEquals(pos, Board.getAllSafeMoves(SideColor.BLACK, figures));
	}
}