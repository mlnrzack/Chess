package chess.pieces;

/**
 * Based on Forestf90's Bishop.java
 * Modified it a little bit
 */

import java.io.Serializable;

/**
 * Bishop.java
 */

import java.util.ArrayList;

import chess.Position;
import chess.SideColor;

public class Bishop extends Chess_piece implements Serializable
{
	/**
	 * This is the constructor for this class
	 * @param col is the color of the bishop
	 * @param x is the x value for the position
	 * @param y is the y value for the position
	 * Sets up the variables with these and other values and loads the image for the figure
	 */
	public Bishop(SideColor col, int x, int y)
	{
		color = col;
		pos = new Position(x, y);
		Value = 350;
		loadImage();	
	}
	
	/**
	 * This method loads the skin on the figure
	 */
	public void loadImage()
	{
		if(this.color == SideColor.WHITE)
			imgSrc = 3;
		else
			imgSrc = 9;
	}
	
	/**
	 * This method gets all the possible type of moves for the bishop
	 * @param board is the current standing of the game
	 */
	public ArrayList<Position> GetMoves(Chess_piece[][] board) 
	{
		/**
		 * this stores the possible moves
		 */
		ArrayList<Position> moves = new ArrayList<Position>();
		
		/**
		 * this checks the north east movement
		 */
		int i = 1;		
		while(this.pos.getX() + i <= 7 && this.pos.getY() - i >=0 ) 
		{		
			if(board[pos.getX() + i][pos.getY() - i] == null)
				moves.add(new Position(this.pos.getX() + i, this.pos.getY() - i));
			
			if(board[pos.getX() + i][pos.getY() - i] != null) 
			{							
				if(board[this.pos.getX() + i][this.pos.getY() - i].color != this.color)
				{
					moves.add(new Position(this.pos.getX() + i, this.pos.getY() - i));
					break;				
				}
				
				else 
					break;				
			}	
			i++;
		}
		/**
		 * this checks the north west movement
		 */
		i = 1;
		while(this.pos.getX() - i >= 0 && this.pos.getY() - i >=0) 
		{
			if(board[pos.getX() - i][pos.getY() - i] == null)
				moves.add(new Position(this.pos.getX() - i, this.pos.getY() - i));				
				
			if(board[pos.getX() - i][pos.getY() - i] != null) 
			{							
				if(board[this.pos.getX() - i][this.pos.getY() - i].color != this.color) 
				{
					moves.add(new Position(this.pos.getX() - i, this.pos.getY() - i));
					break;				
				}
				
				else 
					break;				
			}	
			i++;
		}
		/**
		 * this checks the south east movement
		 */
		i = 1;
		while(this.pos.getX() + i <= 7 && this.pos.getY() + i <=7 )
		{	
			if(board[pos.getX() + i][pos.getY() + i] == null)
				moves.add(new Position(this.pos.getX() + i, this.pos.getY() + i));
			
			if(board[pos.getX() + i][pos.getY() + i] != null)
			{							
				if(board[this.pos.getX() + i][this.pos.getY() + i].color != this.color)
				{
					moves.add(new Position(this.pos.getX() + i, this.pos.getY() + i));
					break;				
				}
				
				else 
					break;				
			}
			i++;
		}
		/**
		 * this checks the south west movement
		 */
		i = 1;
		while(this.pos.getX() - i >= 0 && this.pos.getY() + i <= 7)
		{
			if(board[pos.getX() - i][pos.getY() + i] == null)
				moves.add(new Position(this.pos.getX() - i, this.pos.getY() + i));
			
			if(board[pos.getX() - i][pos.getY() + i] != null)
			{		
				if(board[this.pos.getX() - i][this.pos.getY() + i].color != this.color)
				{
					moves.add(new Position(this.pos.getX() - i, this.pos.getY() + i));
					break;				
				}
				
				else 
					break;				
			}
			i++;
		}
		/**
		 * in the end it returns the possible moves
		 */
		return moves;
	}
	
	/**
	 * This method shows the type as string in other classes
	 */
	public String getType()
	{
		return "Bishop";
	}
}