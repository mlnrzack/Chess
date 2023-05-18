package test;

import static org.junit.Assert.*;

import org.junit.Test;

import chess.Position;
import chess.SideColor;
import chess.pieces.*;

public class TestChesspiece
{
	@Test
	public void testPosition()
	{
		Chess_piece king = new King(SideColor.WHITE, 0, 0);
		Position pos = new Position(0,0);
		Position pos1 = new Position(1,1);
		
		assertEquals(king.getPos().getX(), pos.getX());
		assertEquals(king.getPos().getY(), pos.getY());
		assertNotEquals(king.getPos().getX(), pos1.getX());
		assertNotEquals(king.getPos().getY(), pos1.getY());
	}
	
	@Test
	public void testColor()
	{
		Chess_piece pawn = new Pawn(SideColor.WHITE, 0, 0);
		SideColor white = SideColor.WHITE;
		SideColor black = SideColor.BLACK;
		
		assertEquals(pawn.getColor().toString().toLowerCase(), white.getString().toLowerCase());
		assertNotEquals(pawn.getColor().toString().toLowerCase(), black.getString().toLowerCase());
	}
	
	@Test
	public void testValue()
	{
		Chess_piece queen = new Queen(SideColor.BLACK, 0, 0);
		Chess_piece knight = new Knight(SideColor.WHITE, 1, 0);
		
		assertEquals(queen.getValue(), 1000);
		assertNotEquals(knight.getValue(), 1000);
	}
}
