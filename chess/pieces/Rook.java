package chess.pieces;

/**
 * Based on Forestf90's Rook.java
 * Modified it a little bit
 */

import java.io.Serializable;
import java.util.ArrayList;

import chess.Position;
import chess.SideColor;

public class Rook extends Chess_piece implements Serializable
{
	/**
	 * This is the constructor for this class
	 * @param col is the color of the bishop
	 * @param x is the x value for the position
	 * @param y is the y value for the position
	 * Sets up the variables with these and other values and loads the image for the figure
	 */
	public Rook(SideColor col, int x, int y) 
	{
		color = col;
		pos = new Position(x, y);
		notMoved = true;
		Value = 525;
		loadImage();		
	}
	
	/**
	 * This method loads the skin on the figure
	 */
	public void loadImage() 
	{
		if(this.color == SideColor.WHITE)
			imgSrc = 2;
		else 
			imgSrc = 8;
	}
	
	/**
	 * This method gets all the possible type of moves for the bishop
	 * @param board is the current standing of the game
	 */
	public ArrayList<Position> GetMoves(Chess_piece[][] board) 
	{		
		ArrayList<Position> moves = new ArrayList<Position>();
		
		int movesRight = 7 - this.pos.getX();
		int movesLeft = this.pos.getX();
		int movesUp = this.pos.getY();
		int movesDown = 7 - this.pos.getY();
		
		int i = 0;
		while(movesUp > 0) 
		{			
			movesUp--;
			i++;
			
			if(board[pos.getX()][pos.getY() - i] == null)
				moves.add(new Position(this.pos.getX(), this.pos.getY() - i));
			
			if(board[pos.getX()][pos.getY() - i] != null)
			{							
				if(board[this.pos.getX()][this.pos.getY() - i].color != this.color)
				{
					moves.add(new Position(this.pos.getX(), this.pos.getY() - i));
					break;				
				}
				
				else 
					break;				
			}		
		}
				
		i = 0;
		while(movesDown > 0 )
		{
			movesDown--;
			i++;
			
			if(board[pos.getX()][pos.getY() + i] == null)
				moves.add(new Position(this.pos.getX(), this.pos.getY() + i));
			
			if(board[pos.getX()][pos.getY() + i] != null)
			{							
				if(board[this.pos.getX()][this.pos.getY() + i].color != this.color)
				{
					moves.add(new Position(this.pos.getX(), this.pos.getY() + i));
					break;				
				}
				
				else 
					break;				
			}		
		}
			
		i = 0;
		while(movesLeft > 0)
		{
			movesLeft--;
			i++;
			
			if(board[pos.getX() - i][pos.getY()] == null) 
				moves.add(new Position(this.pos.getX() - i, this.pos.getY()));				
			
			if(board[pos.getX() - i][pos.getY()] != null) {							
				if(board[this.pos.getX() - i][this.pos.getY()].color != this.color)
				{
					moves.add(new Position(this.pos.getX() - i, this.pos.getY()));
					break;				
				}
				
				else 
					break;				
			}		
		}
				
		i = 0;				
		while(movesRight > 0)
		{
			movesRight--;
			i++;
			
			if(board[pos.getX() + i][pos.getY()] == null)
				moves.add(new Position(this.pos.getX() + i, this.pos.getY()));			
			
			if(board[pos.getX() + i][pos.getY()] != null)
			{							
				if(board[this.pos.getX() + i][this.pos.getY()].color != this.color)
				{
					moves.add(new Position(this.pos.getX() + i, this.pos.getY()));
					break;				
				}
				
				else 
					break;				
			}		
		}
		return moves;		
	}
	
	/**
	 * This method shows the type as string in other classes
	 */
	public String getType()
	{
		return "Rook";
	}
}