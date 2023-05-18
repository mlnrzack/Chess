package test;

import static org.junit.Assert.*;
import org.junit.Test;

import chess.Position;

public class TestPosition
{
	@Test
	public void testPosition()
	{
		Position pos = new Position(0, 0);
		
		assertEquals(pos.getX(), 0);
		assertEquals(pos.getY(), 0);
		assertNotEquals(pos.getX(), 1);
	}
	
	@Test
	public void testPositionString()
	{
		Position pos = new Position(0, 0);
		String[] s = {"a","b","c","d","e","f","g","h"};
		
		assertEquals(pos.getXString(), s[pos.getY()]);
		assertEquals(pos.getYString(), "8");
		assertNotEquals(pos.getXString(), 1);
		assertNotEquals(pos.getYString(), "0");
	}
}
