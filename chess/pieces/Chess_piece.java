package chess.pieces;

/**
 * Based on Forestf90's Chessman.java
 * It's base class for the chess pieces
 */

import java.util.ArrayList;

import chess.Position;
import chess.SideColor;

public abstract class Chess_piece 
{
	protected SideColor color;
	protected boolean notMoved;
	protected Position pos;
	protected int Value;
	protected boolean capture;
	protected short imgSrc;
	
	abstract void loadImage();
	
	public abstract ArrayList<Position> GetMoves(Chess_piece[][] board);
	
	public abstract String getType();
	
	/**
	 * These following methods enables this class's variables to be protected
	 * get their value, or sets their value depending on the situation and call
	 */
	
	public SideColor getColor()
	{
		return color;
	}
	
	public void setColor(SideColor color)
	{
		this.color = color;
	}
	
	public boolean getNotmoved()
	{
		return notMoved;
	}
	
	public void setNotmoved(boolean yn)
	{
		notMoved = yn;
	}
	
	public Position getPos()
	{
		return pos;
	}
	
	public void setPos(Position newpos)
	{
		pos = newpos;
	}
	
	public int getValue()
	{
		return Value;
	}
	
	public void setCapture(boolean isCaptured) 
	{
        this.capture = isCaptured;
    }

    public boolean getCapture()
    {
        return this.capture;
    }
    
    public short getImgsrc()
    {
    	return imgSrc;
    }
}