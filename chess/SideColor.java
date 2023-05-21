package chess;

/**
 *  Based on Forestf90 SideColor.java
 *  This class is one of the bases for the pieces.
 *  It contains an enum which determines the color of the figure on the board.
 */

public enum SideColor
{
	/**
	 * These are the values of this enum
	 */
	WHITE, BLACK, NOCHESSPIECE;
 
	/**
	 * This method returns the opposite color
	 * @return
	 */
	public SideColor swapColor()
	{
		if(this == SideColor.WHITE) 
			return SideColor.BLACK;
		return  SideColor.WHITE;
	}
	
	/**
	 * This method returns the appropriate string by the color
	 * @return
	 */
	public String getString() 
	{
		if(this == SideColor.WHITE) 
			return "White";
		else if(this == SideColor.BLACK)
			return  "Black";
		return "No chess piece";
	}
}