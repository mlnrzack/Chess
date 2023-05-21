package chess.board;

/**
 * Based on Forestf90 GamePanelAI.java
 * This creates the chess game to play in PvM mode
 * Modified it to be able to load a saved game
 */

import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.util.Random;

import chess.AI;
import chess.Move;
import chess.Position;
import chess.SideColor;
import chess.pieces.Chess_piece;

public class BoardAI extends Board
{
	public SideColor playerAI;
	
	/**
	 * Creates a new chess board with a machine controlled enemy
	 * Calls the chooseColor() method and then
	 * Calls the super's constructor
	 */
	public BoardAI()
	{
		chooseColor();
	}
	
	/**
	 * Creates a board with a saved game standing with machine controlled enemy
	 * @param load to be loaded to board
	 * @param color to be loaded to board
	 * @param whitemv to be loaded to board, it determines who is going to move, when the game startsű
	 * @param stepc is the step count
	 * the load and color parameters are for the board, they contains all of standing's needed information, which figure is where, and which figure has what color
	 * calls the super's constructor with the @param
	 */	
	public BoardAI(Chess_piece[][] load, SideColor[][] color, boolean whitemv, int stepc)
	{
		/**
		 * this is call for the super's constructor with the params
		 */
		super(load, color, whitemv, stepc);
		
		/**
		 * This part is for the AI color decision
		 * When you load back a game you don't load back the color of the AI
		 * So if you choose the color white when you start the game, you can only save the game when you're the one
		 * who has to move, so the whiteMove gonna have a value that points on you and vica verza
		 */
		if(whiteMove == true)
			playerAI = SideColor.BLACK;
		
		if(whiteMove == false)
			playerAI = SideColor.WHITE;
	}
	
	/**
	 * This creates a dialog between the program and the user to decide whether the player wants to play with the white figures, or the black ones
	 */
	private void chooseColor() 
	{
		/**
		 * This is the option button's values on the JOptionPane
		 */
		Object[] options = { "White", "Black"};
		/**
		 * This is the response value stored in an int to be used that value later
		 */
		int response = JOptionPane.showOptionDialog(null, "Chose the color you want to play with", "Chose your color",
					   JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		
		/**
		 * This 2 ifs sets the AI's color by your choice
		 */
		if (response == JOptionPane.YES_OPTION) 
		{
			playerAI = SideColor.BLACK;  
			whiteMove ^= false;
	    }
		
		else if (response == JOptionPane.NO_OPTION) 
		{
			playerAI = SideColor.WHITE; 
	        whiteMove ^= true;
	        oponentTurn();
	    }
	}
	
	/**
	 * Determines whose turn will be
	 */
	@Override
	void oponentTurn() 
	{
		if(endGame)
			return;
		
		enabled = false;
		newAI(playerAI);
		enabled = true;
	}
	
	/**
	 * This retruns a random index of the array
	 * @param array
	 * @return
	 */
	public static int getRandom(int[] array) 
	{
	    int rnd = new Random().nextInt(array.length);
	    return array[rnd];
	}
	
	/**
	 * 
	 * @param col
	 */
	void AI(SideColor col) 
	{
		ArrayList<Position> pieceMoves = new ArrayList<Position>();
		
		Position newposition = new Position(0, 0);
		Position oldposition = new Position(0, 0);
		int max = 0;
		int random = 0;

		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(board[i][j] != null)
				{				
					if(board[i][j].getColor() == col)
					{
						if(preventCheck(board[i][j].GetMoves(board), board, board[i][j]).size() > 0)
						{			
							random = new Random().nextInt(100);
							if(random > max)
							{								
								max = random;
								oldposition.setX(i);
								oldposition.setY(j);
								pieceMoves = preventCheck(board[i][j].GetMoves(board), board, board[i][j]);
								random = new Random().nextInt(preventCheck(board[i][j].GetMoves(board), board, board[i][j]).size());
								newposition = pieceMoves.get(random);									
							}	
						}
					}
				}			
			}
		}		
		moveChess_piece(newposition, oldposition);							
	}
	
	/**
	 * Creates a move, then calls it on the table, so it moves on that as well
	 * @param col it's AI's color in the game
	 */
	public void newAI(SideColor col)
	{		
		Move move = AI.minMax(board, col, -100000, +100000);
		
		if(move != null)
			moveChess_piece(move.getEnd(), move.getStart());

		else
			AI(col);
	}
}