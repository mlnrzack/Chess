package chess;

/**
 * Based on Forestf90's Position.java
 * Modified it to have private variables
 */

public class Position
{
	private int x;
	private int y;
	
	/**
	 * This is the constructor for this class
	 * @param x is the value for this.x
	 * @param y is the value for this.y
	 */
	public Position(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * This method gives the outer classes its x variable's value
	 * @return the value of x
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * This method gives the outer classes its y variable's value
	 * @return the value of y
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * This method is used for the move history
	 * @return a string based on the x's value
	 */
	public String getXString()
	{
		switch(x)
		{
			case 0:
				return "a";
			case 1:
				return "b";
			case 2:
				return "c";
			case 3:
				return "d";
			case 4:
				return "e";
			case 5:
				return "f";
			case 6:
				return "g";
			case 7:
				return "h";
			default:
				return "";
		}
	}
	
	/**
	 * This method is used for the move history
	 * @return a string based on the y's value
	 */
	public String getYString()
	{
		switch(y)
		{
			case 0:
				return "8";
			case 1:
				return "7";
			case 2:
				return "6";
			case 3:
				return "5";
			case 4:
				return "4";
			case 5:
				return "3";
			case 6:
				return "2";
			case 7:
				return "1";
			default:
				return "";
		}
	}
	
	/**
	 * This method sets this classes x variable with the x parameter
	 * @param x
	 */
	public void setX(int x)
	{
		this.x = x;
	}
	
	/**
	 * This method sets this classes y variable with the y parameter
	 * @param y
	 */
	public void setY(int y)
	{
		this.y = y;
	}
}