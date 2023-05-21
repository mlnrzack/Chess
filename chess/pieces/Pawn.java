package chess.pieces;

/**
 * Based on Forestf90's Pawn.java
 * Modified it a little bit more than the other figure types
 */

import java.io.Serializable;

import java.util.ArrayList;

import chess.Position;
import chess.SideColor;

import chess.board.Board;

public class Pawn extends Chess_piece implements Serializable
{
	public boolean startPosition = true;

	/**
	 * This is the constructor for this class
	 * @param col is the color of the bishop
	 * @param x is the x value for the position
	 * @param y is the y value for the position
	 * Sets up the variables with these and other values and loads the image for the figure
	 */
	public Pawn(SideColor col, int x, int y)
	{
		color = col;
		pos = new Position(x ,y);
		Value = 100;
		loadImage();
	}

	/**
	 * This method loads the skin on the figure
	 */
	public void loadImage()
	{
		if(this.color == SideColor.WHITE)
			imgSrc = 5;
		else
			imgSrc = 11;
	}

	/**
	 * This method gets all the possible type of moves for the bishop
	 * @param board is the current standing of the game
	 */
	public ArrayList<Position> GetMoves(Chess_piece[][] board)
	{		
		ArrayList<Position> moves = new ArrayList<Position>();
		
		int help = 0;
		if(color == SideColor.WHITE)
			help = -1;
		else 
			help = 1;
		
		if(board[pos.getX()][pos.getY() + help] == null) 
		{
			moves.add(new Position(this.pos.getX(), this.pos.getY() + help));
			
			if(startPosition && board[pos.getX()][pos.getY() + (help * 2)] == null)
			{
				/**
				 * this is the little bit more part
				 * gets the step count from the Board
				 * if it's below 2 then the 2 colors didn't made their first moves
				 * if it's over 2 then each side made their first move so they are not beneficiary anymore
				 */
				if(Board.getStep() < 2)
					moves.add(new Position(this.pos.getX(), this.pos.getY() + (help * 2)));
				moves.add(new Position(this.pos.getX(), this.pos.getY() + help));
			}
				
		}
			
		
		if(this.pos.getX() > 0)
		{
			if(board[this.pos.getX() - 1][this.pos.getY() + help] != null)
			{
				if(board[this.pos.getX() - 1][this.pos.getY() + help].color != this.color)
					moves.add(new Position(this.pos.getX() - 1, this.pos.getY() + help));
			}
		}
		if(this.pos.getX() < 7)
		{
			if(board[this.pos.getX() + 1][this.pos.getY() + help] != null)
			{
				if(board[this.pos.getX() + 1][this.pos.getY() + help].color != this.color)
					moves.add(new Position(this.pos.getX() + 1, this.pos.getY() + help));
			}
		}
		return moves;		
	}
	
	/**
	 * This method shows the type as string in other classes
	 */
	public String getType()
	{
		return "Pawn";
	}
}