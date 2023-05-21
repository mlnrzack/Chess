package chess.pieces;

/**
 * Based on Forestf90's Knight.java
 * Modified it a little bit
 */

import java.io.Serializable;

/**
 * Knight.java
 */

import java.util.ArrayList;

import chess.Position;
import chess.SideColor;

public class Knight extends Chess_piece implements Serializable
{
	/**
	 * This is the constructor for this class
	 * @param col is the color of the bishop
	 * @param x is the x value for the position
	 * @param y is the y value for the position
	 * Sets up the variables with these and other values and loads the image for the figure
	 */
	public Knight(SideColor col, int x, int y)
	{
		color = col;
		pos = new Position(x ,y);
		Value = 350;
		loadImage();
	}
	
	/**
	 * This method loads the skin on the figure
	 */
	public void loadImage() 
	{
		if(this.color==SideColor.WHITE)
			imgSrc=4;
		else 
			imgSrc=10;
	}
	
	/**
	 * This method gets all the possible type of moves for the bishop
	 * @param board is the current standing of the game
	 */
	public ArrayList<Position> GetMoves(Chess_piece[][] board)
	{
		ArrayList<Position> moves = new ArrayList<Position>();
				
		if(pos.getX() + 1 < 8 && pos.getY() + 2 < 8)
		{
			if(board[pos.getX() + 1][pos.getY() + 2] == null)
				moves.add(new Position(this.pos.getX() + 1, this.pos.getY() + 2));

			if(board[pos.getX() + 1][pos.getY() + 2] != null)
			{
				if(board[pos.getX() + 1][pos.getY() + 2].color != this.color)
					moves.add(new Position(this.pos.getX() + 1, this.pos.getY() + 2));
			}			
		}
		
		if(pos.getX() + 2 < 8 && pos.getY() + 1 < 8)
		{
			if(board[pos.getX() + 2][pos.getY() + 1] == null)
				moves.add(new Position(this.pos.getX() + 2, this.pos.getY() + 1));		
			
			if(board[pos.getX() + 2][pos.getY() + 1] != null)
			{
				if(board[pos.getX() + 2][pos.getY() + 1].color != this.color)
					moves.add(new Position(this.pos.getX() + 2, this.pos.getY() + 1));
			}		
		}
		
		if(pos.getX() + 1 < 8 && pos.getY() - 2 >= 0 )
		{
			if(board[pos.getX() + 1][pos.getY() - 2] == null)
				moves.add(new Position(this.pos.getX() + 1, this.pos.getY() - 2));
			
			if(board[pos.getX() + 1][pos.getY() - 2] != null)
			{
				if(board[pos.getX() + 1][pos.getY() - 2].color != this.color)
					moves.add(new Position(this.pos.getX() + 1, this.pos.getY() - 2));
			}		
		}	
				
		if(pos.getX() + 2 < 8 && pos.getY() - 1 >= 0)
		{
			if(board[pos.getX() + 2][pos.getY() - 1] == null)
				moves.add(new Position(this.pos.getX() + 2, this.pos.getY() - 1));
			
			if(board[pos.getX() + 2][pos.getY() - 1] != null)
			{
				if(board[pos.getX() + 2][pos.getY() - 1].color != this.color)
					moves.add(new Position(this.pos.getX() + 2, this.pos.getY() - 1));
			}		
		}
	
		
		if(pos.getX() - 1 >= 0 && pos.getY() + 2 < 8)
		{
			if(board[pos.getX() - 1][pos.getY() + 2] == null)
				moves.add(new Position(this.pos.getX() - 1, this.pos.getY() + 2));
			
			if(board[pos.getX() - 1][pos.getY() + 2] != null)
			{
				if(board[pos.getX() - 1][pos.getY() + 2].color != this.color)
					moves.add(new Position(this.pos.getX() - 1, this.pos.getY() + 2));
			}					
		}
		
		
		if(pos.getX() - 2 >= 0 && pos.getY() + 1 < 8)
		{
			if(board[pos.getX() - 2][pos.getY() + 1] == null)
				moves.add(new Position(this.pos.getX() - 2, this.pos.getY() + 1));
			
			if(board[pos.getX() - 2][pos.getY() + 1] != null)
			{
				if(board[pos.getX() - 2][pos.getY() + 1].color != this.color)
					moves.add(new Position(this.pos.getX() - 2, this.pos.getY() + 1));
			}					
		}
		
		if(pos.getX() - 1 >= 0 && pos.getY() - 2 >= 0)
		{
			if(board[pos.getX() - 1][pos.getY() - 2] == null)
				moves.add(new Position(this.pos.getX() - 1, this.pos.getY() - 2));			
			
			if(board[pos.getX() - 1][pos.getY() - 2] != null)
			{
				if(board[pos.getX() - 1][pos.getY() - 2].color != this.color)
					moves.add(new Position(this.pos.getX() - 1, this.pos.getY() - 2));
			}		
		}
			
		if(pos.getX() - 2 >= 0 && pos.getY() - 1 >= 0)
		{		
			if(board[pos.getX() - 2][pos.getY() - 1] == null)
				moves.add(new Position(this.pos.getX() - 2, this.pos.getY() - 1));						
			
			if(board[pos.getX() - 2][pos.getY() - 1] != null)
			{
				if(board[pos.getX() - 2][pos.getY() - 1].color != this.color)
					moves.add(new Position(this.pos.getX() - 2, this.pos.getY() - 1));
			}		
		}
		return moves;
	}
	
	/**
	 * This method shows the type as string in other classes
	 */
	public String getType()
	{
		return "Knight";
	}
}