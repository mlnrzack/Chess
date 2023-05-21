package chess.pieces;

/**
 * Based on Forestf90's King.java
 * Modified it a little bit
 */

import java.io.Serializable;

/**
 * King.java
 */

import java.util.ArrayList;

import chess.Position;
import chess.SideColor;

public class King extends Chess_piece implements Serializable
{
	/**
	 * This is the constructor for this class
	 * @param col is the color of the bishop
	 * @param x is the x value for the position
	 * @param y is the y value for the position
	 * Sets up the variables with these and other values and loads the image for the figure
	 */
	public King(SideColor col, int x, int y) 
	{
		color = col;
		pos = new Position(x, y);
		notMoved = true;
		Value = 10000;
		loadImage();
	}
	
	/**
	 * This method loads the skin on the figure
	 */
	public void loadImage() 
	{
		if(this.color == SideColor.WHITE) 
			imgSrc=0;
		else
			imgSrc=6;
	}
	
	/**
	 * This method gets all the possible type of moves for the bishop
	 * @param board is the current standing of the game
	 */
	public ArrayList<Position> GetMoves(Chess_piece[][] board) 
	{
		ArrayList<Position> moves = new ArrayList<Position>();

		if (pos.getX() <= 7 && pos.getY() + 1 < 8) 
		{
			if (board[pos.getX()][pos.getY() + 1] == null)
				moves.add(new Position(this.pos.getX(), this.pos.getY() + 1));
			
			  if(board[this.pos.getX()][this.pos.getY() + 1] != null) 
			  {
				  if(board[this.pos.getX()][this.pos.getY() + 1].color != this.color) 
					  moves.add(new Position(this.pos.getX() ,this.pos.getY() + 1)); 
			  }			 
		}
		
		if (pos.getX() <= 7 && pos.getY() - 1 >= 0) 
		{
			if (board[pos.getX()][pos.getY() - 1] == null)
				moves.add(new Position(this.pos.getX(), this.pos.getY() - 1));
			
			if(board[this.pos.getX()][this.pos.getY() - 1] != null) 
			{
				if(board[this.pos.getX()][this.pos.getY() - 1].color != this.color) 
					moves.add(new Position(this.pos.getX() ,this.pos.getY() - 1)); 
			}			 
		}

		if (pos.getX() + 1 <= 7 && pos.getY() + 1 < 8) 
		{
			if (board[pos.getX() + 1][pos.getY() + 1] == null)
				moves.add(new Position(this.pos.getX() + 1, this.pos.getY() + 1));

			  if(board[this.pos.getX() + 1][this.pos.getY() + 1] != null) 
			  {
				  if(board[this.pos.getX() + 1][this.pos.getY() + 1].color != this.color)
					  moves.add(new Position(this.pos.getX() + 1 ,this.pos.getY() + 1));
			  }			 
		}

		if (pos.getX() + 1 <= 7 && pos.getY() - 1 >= 0)
		{
			if (board[pos.getX() + 1][pos.getY() - 1] == null)
				moves.add(new Position(this.pos.getX() + 1, this.pos.getY() - 1));

			  if(board[this.pos.getX() + 1][this.pos.getY() - 1] != null)
			  {
				  if(board[this.pos.getX() + 1][this.pos.getY() - 1].color != this.color) 
					  moves.add(new Position(this.pos.getX() + 1 ,this.pos.getY() - 1));
			  }
		}

		if (pos.getX() - 1 >= 0 && pos.getY() + 1 < 8) 
		{
			if (board[pos.getX() - 1][pos.getY() + 1] == null)
				moves.add(new Position(this.pos.getX() - 1, this.pos.getY() + 1));
			
			  if(board[this.pos.getX() - 1][this.pos.getY() + 1] != null) 
			  {
				  if(board[this.pos.getX() - 1][this.pos.getY() + 1].color != this.color)
					  moves.add(new Position(this.pos.getX() - 1 ,this.pos.getY() + 1));
			  }			 
		}

		if (pos.getX() - 1 >= 0 && pos.getY() - 1 >= 0) {

			if (board[pos.getX() - 1][pos.getY() - 1] == null)
				moves.add(new Position(this.pos.getX() - 1, this.pos.getY() - 1));

			  if(board[this.pos.getX() - 1][this.pos.getY() - 1] != null)
			  {
				  if(board[this.pos.getX() - 1][this.pos.getY() - 1].color != this.color)
					  moves.add(new Position(this.pos.getX() - 1 ,this.pos.getY() - 1)); 
			  }
		}

		if (pos.getX() + 1 <= 7 && pos.getY() < 8) {

			if (board[pos.getX() + 1][pos.getY()] == null)
				moves.add(new Position(this.pos.getX() + 1, this.pos.getY()));

			  if(board[this.pos.getX() + 1][this.pos.getY()] != null) 
			  {
				  if(board[this.pos.getX() + 1][this.pos.getY()].color != this.color) 
					  moves.add(new Position(this.pos.getX() + 1 ,this.pos.getY())); 
			  }
		}

		if (pos.getX() - 1 >= 0 && pos.getY() < 8)
		{
			if (board[pos.getX() - 1][pos.getY()] == null)
				moves.add(new Position(this.pos.getX() - 1, this.pos.getY()));

			  if(board[this.pos.getX() - 1][this.pos.getY()] != null) 
			  {
				  if(board[this.pos.getX() - 1][this.pos.getY()].color != this.color)
					  moves.add(new Position(this.pos.getX() - 1 ,this.pos.getY()));
			  }
		}
		
		if (this.notMoved == true)
		{
			int pom = 1;
			
			while(this.pos.getX() - pom >= 0 )
			{
				if(board[pos.getX() - pom][pos.getY()] != null)
				{
					if(board[pos.getX() - pom][pos.getY()] instanceof Rook)
					{
						if(board[pos.getX() - pom][pos.getY()].notMoved == true)
						{
							moves.add(new Position(this.pos.getX() - 2, this.pos.getY()));						
							break;
						}
					}
					else 					
						break;					
				}
				pom++;
			}		
		}
		
		if (this.notMoved == true)
		{
			int pom = 1;
			
			while(this.pos.getX() + pom < 8)
			{
				if(board[pos.getX() + pom][pos.getY()] != null)
				{
					if(board[pos.getX() + pom][pos.getY()] instanceof Rook)
					{
						if(board[pos.getX() + pom][pos.getY()].notMoved == true)
						{
							moves.add(new Position(this.pos.getX() + 2, this.pos.getY()));						
							break;
						}
					}
					
					else 					
						break;					
				}
				pom++;
			}			
		}		
		return moves;
	}
	
	/**
	 * This method shows the type as string in other classes
	 */
	public String getType()
	{
		return "King";
	}
}