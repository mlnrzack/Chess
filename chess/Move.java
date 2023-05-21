package chess;

/**
 * Based on Forestf90's Move.java
 * Modified it to have private variables
 */

public class Move 
{
	private Position start;
	private Position end;
	
	/**
	 * This is the constructor for this class
	 * @param oldPos is a Position where the given figure was standing
	 * @param newPos is a Position where the given figure is going to stand
	 */
	public Move(Position oldPos, Position newPos) 
	{
		start = oldPos;
		end = newPos;
	}
	
	/**
	 * Gives the start position to outer classes
	 * @return is the old position of that given figure
	 */
	public Position getStart()
	{
		return start;
	}

	/**
	 * Gives the end position to outer classes
	 * @return is the new position of that given figure
	 */
	public Position getEnd()
	{
		return end;
	}
}