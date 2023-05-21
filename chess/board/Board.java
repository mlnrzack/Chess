package chess.board;
/**
 * Based on Forestf90 GamePanel.java
 * This creates the chess board, the chess figures, and all the rules to the game
 * Modified it to be able to load a saved game, to communicate with other classes successfully,and to have its variables at least protected
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;

import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import chess.Menu;
import chess.MoveHistory;
import chess.Position;
import chess.Saver;
import chess.SideColor;
import chess.pieces.Bishop;
import chess.pieces.Chess_piece;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public abstract class Board extends JPanel implements Serializable
{
	private static BufferedImage boardImage;
	protected static int SQUARE_SIZE = 100;
	protected static ArrayList<Position> possibleMoves;
	protected static ArrayList<Position> lastMove;
	protected Position checkPosition;
	protected Chess_piece selected;
	
	protected boolean endGame = false;
	protected static Position focus;
	protected static Chess_piece[][] board;
	
	private static BufferedImage[] piecesChess;
	
	protected static boolean whiteMove;
	protected static boolean enabled;
	
	private static int steps;
	
	abstract void oponentTurn();
	
	/**
	 * This is the constructor for the Board
	 * Creates all the necessary thing to be able to play
	 */
	public Board()
	{
		/**
		 * initializes the board, then paint it, then sets the preffered size to it
		 */
		boardImage = new BufferedImage(8 * SQUARE_SIZE, 8 * SQUARE_SIZE, BufferedImage.TYPE_INT_ARGB);
		drawBoard();
		this.setPreferredSize(new Dimension(8 * SQUARE_SIZE, 8 * SQUARE_SIZE));
		/**
		 * initializes the variables
		 */
		board = new Chess_piece[8][8];
		piecesChess = new BufferedImage[12];
		possibleMoves = new ArrayList<Position>();
		lastMove = new ArrayList<Position>();
		focus = new Position(0, 0);
		whiteMove = true;
		enabled = true;
		steps = 0;
		/**
		 * Calls methods @method generatePieces() to generate the pieces to the board, 
		 * @method loadImages() load the images for that, and lastly calls the 
		 * @method MouseListener() to check for any actions on the table
		 */
		generatePieces();
		loadImages();
		MouseListner();
	}
	
	/**
	 * This is the new constructor for the Board
	 * It creates a board with a previous saved standing of the game
	 * @param loadBoard this contains the positions of the figures, and the types of them
	 * @param color this contains the color of the given figure
	 * @param whitemv this is the condition for whether white continues or black continues
	 */
	public Board(Chess_piece[][] loadBoard, SideColor[][] color, boolean whitemv, int stepc) 
	{
		/**
		 * Creates the board background and sets the size for that
		 */
		boardImage = new BufferedImage(8 * SQUARE_SIZE, 8 * SQUARE_SIZE, BufferedImage.TYPE_INT_ARGB);
		drawBoard();
		this.setPreferredSize(new Dimension(8 * SQUARE_SIZE, 8 * SQUARE_SIZE));
		
		/**
		 * Loads the board with the colors of the figures, coz the chess_piece[][] doesn't store that value, just the positions
		 */
		board = new Chess_piece[8][8];		
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				board[j][i] = loadBoard[j][i];
				if(loadBoard[j][i] != null)
					board[j][i].setColor(color[j][i]);
			}
		}
		
		/**
		 * Sets the value for these variables
		 * The whiteMove was loaded from the file to know which player has to move next time when you play
		 */
		piecesChess = new BufferedImage[12];
		possibleMoves = new ArrayList<Position>();
		lastMove = new ArrayList<Position>();
		focus = new Position(0, 0);
		whiteMove = whitemv;
		enabled = true;
		steps = stepc;
		
		/**
		 * Calls methods @method loadPieces() to load the pieces to the board, 
		 * @method loadImages() load the images for that, and lastly calls the 
		 * @method MouseListener() to check for any actions on the table
		 */
		loadPieces();
		loadImages();
		MouseListner();
	}
	
	/**
	 * This colors the active squares of the board
	 */
	public void paintComponent(Graphics g) 
	{
		/**
		 * calls itself when were any modification
		 */
		super.paintComponent(g);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(boardImage, 0, 0, 8 * SQUARE_SIZE, 8 * SQUARE_SIZE, null);
		
		/**
		 * Colors the last move, so you can see which figure stepped where
		 */
		for(Position lm: lastMove)
		{
			g.setColor(new Color(0, 102, 102, 128));
			g.fillRect(lm.getX() * SQUARE_SIZE, lm.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
		}
		
		if(checkPosition != null) 
		{
			g.setColor(new Color(255, 0, 0, 128));
			g.fillRect(checkPosition.getX() * SQUARE_SIZE, checkPosition.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
		}
		
		/**
		 * Colors the square under the cursor
		 */
		g.setColor(Color.decode("#96dcdc"));
		g.fillRect(focus.getX() * SQUARE_SIZE, focus.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
		/**
		 * fills the board with the pictures of the figures
		 */
		for(int i = 0 ; i < board.length; i++) 
		{
			for(int j = 0 ; j < board[i].length; j++) 
			{
				if(board[i][j] == null)
					continue;
				
				else 
				{
					g.drawImage(piecesChess[board[i][j].getImgsrc()],  i * SQUARE_SIZE,
					j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE , null);
				}
			}
		}
		
		/**
		 * Colors the possible moves with a circle
		 */
		g.setColor(Color.decode("#285f5f"));
		if(selected != null) 
		{
			((Graphics2D) g).setStroke(new BasicStroke(4));
			g.drawRect(selected.getPos().getX() * SQUARE_SIZE + 2, selected.getPos().getY() * SQUARE_SIZE + 2, SQUARE_SIZE - 4, SQUARE_SIZE - 4);
		}

		for(Position ch: possibleMoves) 
		{
			if(board[ch.getX()][ch.getY()] != null)
			{
				if(board[ch.getX()][ch.getY()].getColor() != selected.getColor())
				{
					/**
					 * Colors the possible moves, where there is an opposite color figure
					 */
					g.setColor(Color.decode("#962828"));
					g.drawRect(ch.getX() * SQUARE_SIZE, ch.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
				}
			}
			
			else 
			{
				/**
				 * Colors the possible move
				 */
				g.setColor(Color.decode("#285f5f"));
				g.fillOval(ch.getX() * SQUARE_SIZE + SQUARE_SIZE / 4, ch.getY() * SQUARE_SIZE + SQUARE_SIZE / 4,
				SQUARE_SIZE / 2, SQUARE_SIZE / 2);				
			}			
		}
	}
	
	/**
	 * This colors the squares on the board // that's the background
	 */
	private void drawBoard()
	{
		Graphics2D g = (Graphics2D) boardImage.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		/**
		 * this is one type of square colors the whole board
		 */
		g.setColor(Color.decode("#8a8a8a"));
		g.fillRect(0, 0, 8 * SQUARE_SIZE, 8 * SQUARE_SIZE);
		
		/*
		 * this is the other type of square, colors specific squares
		 */
		g.setColor(Color.decode("#ffffff"));
		for(int i = 0; i < 8; i++) 
		{
			for(int j = i % 2; j < 8; j += 2) 
			{
				g.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
			}
		}
	}
	
	/**
	 * This generates the pieces and fill up the 2 dimension array of Chess_piece board
	 */
	private void generatePieces()
	{
		/**
		 * generates the pawns for each color to position
		 */
		for(int i = 0 ; i < 8 ; i++) 
		{
			board[i][6] = new Pawn(SideColor.WHITE, i, 6);
			board[i][1] = new Pawn(SideColor.BLACK, i, 1);
		}		
		/**
		 * generates the rooks for each color, to position	
		 */
		board[0][7] = new Rook(SideColor.WHITE, 0, 7);
		board[7][7] = new Rook(SideColor.WHITE, 7, 7);
		board[0][0] = new Rook(SideColor.BLACK, 0, 0);
		board[7][0] = new Rook(SideColor.BLACK, 7, 0);
		/**
		 * generates the knights for each color, to position	
		 */
		board[1][7] = new Knight(SideColor.WHITE, 1, 7);
		board[6][7] = new Knight(SideColor.WHITE, 6, 7);
		board[1][0] = new Knight(SideColor.BLACK, 1, 0);
		board[6][0] = new Knight(SideColor.BLACK, 6, 0);
		/**
		 * generates the bishops for each color, to position	
		 */
		board[2][7] = new Bishop(SideColor.WHITE, 2, 7);
		board[5][7] = new Bishop(SideColor.WHITE, 5, 7);
		board[2][0] = new Bishop(SideColor.BLACK, 2, 0);
		board[5][0] = new Bishop(SideColor.BLACK, 5, 0);	
		/**
		 * generates the queens for each color, to position	
		 */
		board[3][7] = new Queen(SideColor.WHITE, 3, 7);
		board[3][0] = new Queen(SideColor.BLACK, 3, 0);
		/**
		 * generates the kings for each color, to position	
		 */
		board[4][7] = new King(SideColor.WHITE, 4, 7);
		board[4][0] = new King(SideColor.BLACK, 4, 0);		
	}
	
	/**
	 * This is quite like the @method generatePieces(), but for a loaded game
	 * <-- I wrote it
	 */
	private void loadPieces()
	{
		/**
		 * the 2 fors are for to scan the whole board to fill up with the pieces
		 */
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				/**
				 * this is to test if there isn't any piece, then don't do the followings
				 * also if there is any type of chess piece, then identifies it, also what
				 * color it is and fills the specific board square with that type of chess piece
				 * 
				 * I would liked to do it with switch case, but it was not working properly so I used the secure way
				 */
				if(board[j][i] != null)
				{
					if(board[j][i].getType() == "Bishop" && board[j][i].getColor() == SideColor.WHITE)
						board[j][i] = new Bishop(SideColor.WHITE, j, i);
					else if(board[j][i].getType() == "Bishop" && board[j][i].getColor() == SideColor.BLACK)
						board[j][i] = new Bishop(SideColor.BLACK, j, i);
					
					else if(board[j][i].getType() == "King" && board[j][i].getColor() == SideColor.WHITE)
						board[j][i] = new King(SideColor.WHITE, j, i);
					else if(board[j][i].getType() == "King" && board[j][i].getColor() == SideColor.BLACK)
						board[j][i] = new King(SideColor.BLACK, j, i);
					
					else if(board[j][i].getType() == "Knight" && board[j][i].getColor() == SideColor.WHITE)
						board[j][i] = new Knight(SideColor.WHITE, j, i);
					else if(board[j][i].getType() == "Knight" && board[j][i].getColor() == SideColor.BLACK)
						board[j][i] = new Knight(SideColor.BLACK, j, i);
					
					else if(board[j][i].getType() == "Pawn" && board[j][i].getColor() == SideColor.WHITE)
						board[j][i] = new Pawn(SideColor.WHITE, j, i);
					else if(board[j][i].getType() == "Pawn" && board[j][i].getColor() == SideColor.BLACK)
						board[j][i] = new Pawn(SideColor.BLACK, j, i);
					
					else if(board[j][i].getType() == "Queen" && board[j][i].getColor() == SideColor.WHITE)
						board[j][i] = new Queen(SideColor.WHITE, j, i);
					else if(board[j][i].getType() == "Queen" && board[j][i].getColor() == SideColor.BLACK)
						board[j][i] = new Queen(SideColor.BLACK, j, i);
				
					else if(board[j][i].getType() == "Rook" && board[j][i].getColor() == SideColor.WHITE)
						board[j][i] = new Rook(SideColor.WHITE, j, i);
					else if(board[j][i].getType() == "Rook" && board[j][i].getColor() == SideColor.BLACK)
						board[j][i] = new Rook(SideColor.BLACK, j, i);
					/*
					switch(board[j][i].getType())
					{
						case"Bishop":
							board[j][i] = new Bishop(board[j][i].getColor(), j, i);
						case"King":
							board[j][i] = new King(board[j][i].getColor(), j, i);
						case"Knight":
							board[j][i] = new Knight(board[j][i].getColor(), j, i);
						case"Pawn":
							board[j][i] = new Pawn(board[j][i].getColor(), j, i);
						case"Queen":
							board[j][i] = new Queen(board[j][i].getColor(), j, i);
						case"Rook":
							board[j][i] = new Rook(board[j][i].getColor(), j, i);
					
					}*/
				}
			}
		}
	}
	
	/**
	 * This loads the skin for the chess pieces on the board, so the user could see the figures
	 * <-- I wrote it
	 */
	private void loadImages() 
	{
		/**
		 * tries to load the pictures
		 */
		try 
		{
			piecesChess[0] = ImageIO.read(getClass().getResource("/white_king.png"));
			piecesChess[6] = ImageIO.read(getClass().getResource("/black_king.png"));
			
			piecesChess[1] = ImageIO.read(getClass().getResource("/white_queen.png"));
			piecesChess[7] = ImageIO.read(getClass().getResource("/black_queen.png"));
			
			piecesChess[2] = ImageIO.read(getClass().getResource("/white_rook.png"));
			piecesChess[8] = ImageIO.read(getClass().getResource("/black_rook.png"));
			
			piecesChess[3] = ImageIO.read(getClass().getResource("/white_bishop.png"));
			piecesChess[9] = ImageIO.read(getClass().getResource("/black_bishop.png"));
			
			piecesChess[4] = ImageIO.read(getClass().getResource("/white_knight.png"));
			piecesChess[10] = ImageIO.read(getClass().getResource("/black_knight.png"));
			
			piecesChess[5] = ImageIO.read(getClass().getResource("/white_pawn.png"));
			piecesChess[11] = ImageIO.read(getClass().getResource("/black_pawn.png"));
		}
		/**
		 * catches if it can't
		 */
		catch(IOException e)
		{
			System.err.println("Error");
			e.printStackTrace();
		}
	}
	
	/**
	 * This is for tracking the cursor on the board
	 */
	private void MouseListner() 
	{
		/**
		 * this focuses on the actual clicking action and what to do after the click
		 */
		 addMouseListener(new MouseAdapter()
		 { 
	         public void mousePressed(MouseEvent me)
	         {
	        	 /**
	        	  * gets the cursors position on the board
	        	  */
	        	 int tempX = me.getX() / SQUARE_SIZE;
	        	 int tempY = me.getY() / SQUARE_SIZE;
	        	 if((board[tempX][tempY] == null && selected == null) || !enabled)
	        		 return;
	        	 
	        	 /**
	        	  * if you did not choose a figure to move before, it also tests if you picked the right color figure
	        	  */
	        	 else if(selected == null)
	        	 {
	            	 selected = board[tempX][tempY];
	            	 if(selected.getColor() == SideColor.WHITE && !whiteMove) 
	            	 {
	            		 selected = null;
	            		 return;
	            	 }
	            	 
	            	 else if(selected.getColor() == SideColor.BLACK && whiteMove) 
	            	 {
	            		 selected = null;
	            		 return;
	            	 }
	            	 
	                 possibleMoves = preventCheck(selected.GetMoves(board), board, selected);
	                 repaint();
	        	 }
	        	 
	        	 /**
	        	  * if you did choose before, but changed your mind
	        	  */
	        	 else if(selected != null) 
	        	 {
	        		 if(board[tempX][tempY]!= null && board[tempX][tempY].getColor() == selected.getColor())
	        		 {
	                	 selected = board[tempX][tempY];
	                     possibleMoves = preventCheck(selected.GetMoves(board), board, selected);
	                     repaint();
	        		 }
	        		 
	        		 else
	        		 {	        			 
	        			 checkChess_pieceMove(new Position(tempX, tempY));
	        			 selected = null;
	        			 possibleMoves.clear();
	        			 repaint();
	        		 }	        		 
	        	 }
	         }
	     }); 
		 
		 /**
		  * this focuses on the cursor's movement and repaints the board if it's needed
		  */
		 addMouseMotionListener(new MouseMotionListener() 
		 {
			 @Override
			public void mouseMoved(MouseEvent me) 
			{
	        	 focus.setX(me.getX() / SQUARE_SIZE);
	        	 focus.setY(me.getY() / SQUARE_SIZE);
	        	 repaint();
			}
	
			@Override
			public void mouseDragged(MouseEvent arg0) {}
		 });		
	}

	/**
	 * This checks if you want to move into a possible position 
	 * @param newPosition this is the tested position
	 */
	private void checkChess_pieceMove(Position newPosition) 
	{
		boolean contains = false;
		/**
		 * here checks if you could stand there,...
		 */
		for(Position m : possibleMoves) 
		{			
			if(m.getX() == newPosition.getX() && m.getY() == newPosition.getY()) 
			{
				contains = true;
				break;
			}
		}
		/**
		 *  and if you could, then...
		 */
		if(!contains)
			return;
		/**
		 * ...then it calls @method moveChess_piece to move you into position, then sets the 
		 */
		moveChess_piece(newPosition, selected.getPos());
		oponentTurn();
	}
	
	/**
	 * This method is for castling in chess
	 * @param newPosition
	 * @param piece
	 * @param board
	 * I don't know this one in real life, so I can't say much about it
	 */
	public static void castling(Position newPosition, Chess_piece piece, Chess_piece[][] board) 
	{
		if(newPosition.getX() == 2) 
		{
			Position rookNewposition = new Position(newPosition.getX() + 1, newPosition.getY());
			Position rookOldposition = new Position(0, newPosition.getY());
			
			if(board[rookOldposition.getX()][rookOldposition.getY()].getNotmoved())
			{				
				if(board[piece.getPos().getX()][piece.getPos().getY()].getNotmoved())
				{
					Chess_piece rook = board[rookOldposition.getX()][rookOldposition.getY()];
					board[rookNewposition.getX()][rookNewposition.getY()] = rook;
					board[rookOldposition.getX()][rookOldposition.getY()] = null;
					rook.setPos(rookNewposition);
					rook.setNotmoved(false);
				}
			}
		}
		
		else if(newPosition.getX() == 6) 
		{
			Position rookNewposition = new Position(newPosition.getX() - 1, newPosition.getY());
			Position rookOldposition = new Position(7, newPosition.getY());
			
			if(board[rookOldposition.getX()][rookOldposition.getY()].getNotmoved())
			{				
				if(board[piece.getPos().getX()][piece.getPos().getY()].getNotmoved())
				{
					Chess_piece rook = board[rookOldposition.getX()][rookOldposition.getY()];
					board[rookNewposition.getX()][rookNewposition.getY()] = rook;
					board[rookOldposition.getX()][rookOldposition.getY()] = null;
					rook.setPos(rookNewposition);
					rook.setNotmoved(false);
				}
			}
		}
	}
	
	/**
	 * This moves the chess pieces on the board
	 * @param newPosition this is the selected piece's new position
	 * @param oldPosition this is where it's currently standing
	 */
	public void moveChess_piece(Position newPosition, Position oldPosition) 
	{
		/**
		 * this section clears and then adds the positions
		 * the piece is the wanted to move figure
		 */
		lastMove.clear();
		lastMove.add(oldPosition);
		lastMove.add(newPosition);
		Chess_piece piece = board[oldPosition.getX()][oldPosition.getY()];
		/**
		 * castling call
		 */
		if(piece instanceof King && piece.getNotmoved()) 
		{				
			castling(newPosition, piece, board);
		}
		/**
		 * if the is a pawn and it's gone to the other side of the board, then it transforms into its queen 
		 */
		else if(piece instanceof Pawn) 
		{
			((Pawn) piece).startPosition = false;
			
			if(newPosition.getY() == 7 || newPosition.getY() == 0) 
			{
				Position tempP = piece.getPos();
				SideColor tempC = piece.getColor();
				piece = new Queen(tempC, tempP.getX(), tempP.getY());
			}
		}
		/**
		 * this moves the piece to the new position, before that it inspects the position for any other colored figure
		 * if it does find an other colored figure, sets the captures true and that figures captured to true
		 */
		boolean captures = false;
		String captured = "";
		if(board[newPosition.getX()][newPosition.getY()] != null && piece.getColor() != board[newPosition.getX()][newPosition.getY()].getColor())
		{
			captures = true;
			board[newPosition.getX()][newPosition.getY()].setCapture(true);
			captured = board[newPosition.getX()][newPosition.getY()].getColor().getString() + " " + board[newPosition.getX()][newPosition.getY()].getType();
		}			
		board[newPosition.getX()][newPosition.getY()] = piece;
		/**
		 * this little variable increase sets the players steps, so the pawns can move as they should
		 */
		steps++;
		/**
		 * this section deletes the figure from the old position
		 * gives the piece the new position and sets the notmoved to false
		 */
		board[piece.getPos().getX()][piece.getPos().getY()] = null;
		piece.setPos(newPosition);
		piece.setNotmoved(false);
		SideColor c = piece.getColor().swapColor();
		/**
		 * this section check if there's any check or checkmate
		 */
		boolean isCheck = check(board, c);
		if(!isCheck) 
		{
			checkPosition = null;
			checkStalemate(piece.getColor().swapColor(), board);
		}
		
		else
		{
			checkPosition = findKing(board, c);
			checkmate(piece.getColor().swapColor(), board);
		}
		/**
		 * this calls the move history to print the move
		 */
		MoveHistory.printMoveHistory(piece, oldPosition, newPosition, captures, captured, isCheck);
	}
	
	/**
	 * This method gets all the possible moves from that given figure
	 * @param col is the given color, this colored figures has to be ckecked for moves
	 * @param board is the game actual standing
	 * @return all the possible moves from the whole board
	 */
	public static ArrayList<Position> getAllMoves(SideColor col, Chess_piece[][] board)
	{
		/**
		 * this is where it's stored
		 */
		ArrayList<Position> moves = new ArrayList<Position>();
		/**
		 * this 2 fors scans the whole board for given colored figures and gets those possible moves
		 */
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++) 
			{
				if(board[i][j] != null)
				{				
					if(board[i][j].getColor() == col) 
						moves.addAll(board[i][j].GetMoves(board));
				}			
			}
		}
		/**
		 * return all col colored figures possible moves
		 */
		return moves;
	}
	
	/**
	 * This method gets all the safe moves from that given figure
	 * @param col is the given color, this colored figures has to be ckecked for moves
	 * @param board is the game actual standing
	 * @return all the safe moves from the whole board
	 */
	public static ArrayList<Position> getAllSafeMoves(SideColor col, Chess_piece[][] board)
	{
		/**
		 * this is where it's stored
		 */
		ArrayList<Position> moves = new ArrayList<Position>();
		/**
		 * this 2 fors scans the whole board for given colored figures and gets those safe moves
		 * safe move is when you move and after you move there isn't any check situation because of the move
		 */
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(board[i][j] != null)
				{				
					if(board[i][j].getColor() == col) 
						moves.addAll(preventCheck(board[i][j].GetMoves(board),board,board[i][j]));
				}			
			}
		}
		/**
		 * return all col colored figures possible moves
		 */
		return moves;
	}
	
	/**
	 * This method finds the given colored  figure's king
	 * @param board is the current state of the game
	 * @param col is the given color
	 * @return the king's position
	 */
	public static Position findKing(Chess_piece[][] board, SideColor col) 
	{
		/**
		 * this will store the king's position
		 */
		Position kingPosition = new Position(0,0);
		/**
		 * this scannes the whole table for the col colored king
		 * then sets the king's position
		 */
		for(int i = 0; i < board.length; i++) 
		{
			for(int j = 0 ; j < board[i].length; j++)
			{
				if(board[i][j] != null) 
				{
					if(board[i][j] instanceof King && board[i][j].getColor() == col)
						return new Position(i, j);
				}
			}
		}
		/**
		 * if didn't find the king returns a fake value
		 * this is only for the complier, a king has to be existing
		 */
		return kingPosition;
	}
	
	/**
	 * This method is for to check there's any check going on the board
	 * @param board is current standing of the game
	 * @param col is the player's color
	 * @return it's if there is any possible move that leads to check
	 */
	public static boolean check(Chess_piece[][] board, SideColor col) 
	{
		/**
		 * gets col colored king's position
		 * and sets c to the other color
		 */
		Position kingPosition = findKing(board, col);
		SideColor c = col.swapColor();
		/**
		 * here it stores all the moves that the opposite colored player could move
		 */
		ArrayList<Position> enemyMoves = getAllMoves(c, board);
		/**
		 * check all the possibilities if the king is in their way
		 */
		for(Position p : enemyMoves) 
		{
			if(kingPosition.getX() == p.getX() && kingPosition.getY() == p.getY()) 
				return true;
		}		
		return false;
	}
	
	/**
	 * This method is for the player can not move into a square where it gets checked
	 * @param moves the moves the player can make
	 * @param board the current state of the board
	 * @param piece is the selected figure
	 * @return all the safe moves
	 */
	public static ArrayList<Position> preventCheck(ArrayList<Position> moves, Chess_piece[][] board, Chess_piece piece)
	{
		/**
		 * creates an iterator for the moves
		 */
		Iterator<Position> i = moves.iterator();
		while(i.hasNext()) 
		{
			/**
			 * gets the next possible move
			 */
			Position p = i.next();
			/**
			 * moves the piece into a temporary position
			 * then checks if that position causes any situation for check
			 * if it creates it removes it from the possible moves
			 */
			Chess_piece[][] tempBoard = Arrays.stream(board).map(r -> r.clone()).toArray(Chess_piece[][] :: new);			
			tempBoard[piece.getPos().getX()][piece.getPos().getY()] = null;
			tempBoard[p.getX()][p.getY()] = piece;
			boolean isCheck = check(tempBoard, piece.getColor());
			if(isCheck)
				i.remove();
			/**
			 * checks if the selected piece is a king and it's not moved yet
			 * if it wants to move to position p and it's not safe removes it
			 */
			else if(piece instanceof King && piece.getNotmoved()) 
			{
				if(preventCheckCastling(p, tempBoard, piece))	
					i.remove();
			}			
		}
		/**
		 * returns all the safe moves
		 */
		return moves;
	}

	/**
	 * This method is for the player can not move into a unsafe square by castling
	 * @param p is a position for the king
	 * @param tempBoard is a temporary virtual game standing
	 * @param piece is a king
	 * @return a boolean if it's safe it's true, else it false
	 */
	public static boolean preventCheckCastling(Position p, Chess_piece[][] tempBoard, Chess_piece piece)
	{
		/**
		 * checks for 3rd column
		 */
		if(p.getX() == 2)
		{
			tempBoard[p.getX()][p.getY()] = null;
			tempBoard[p.getX() + 1][p.getY()] = piece;	
			return check(tempBoard, piece.getColor());
		}
		/**
		 * cheks for the 7th column
		 */
		else if(p.getX() == 6)
		{
			tempBoard[p.getX()][p.getY()] = null;
			tempBoard[p.getX() - 1][p.getY()] = piece;
			return check(tempBoard, piece.getColor());
		}
		
		else 
			return false;
	}
	
	/**
	 * This method is for checkmate situations
	 * @param col is the checked king's color
	 * @param board is the current state of the board
	 */
	public void checkmate(SideColor col, Chess_piece[][] board) 
	{
		/**
		 * Gets all the safe moves for the col colored king, and other figures
		 */
		ArrayList<Position> any = getAllSafeMoves(col , board);
		/**
		 * if any is null, the king can't go anywhere or nothing can prevent the check
		 * then it sets the endgame to true, and closes the frame with a message dialog
		 */
		if(any.isEmpty()) 
		{
			endGame = true;
			possibleMoves.clear();
			repaint();
			JOptionPane.showMessageDialog(null, col.getString() + " King is checkmate. " + col.swapColor().getString() + " wins. ", "Checkmate", JOptionPane.INFORMATION_MESSAGE);
			closeFrame();
		}
	}
	
	/**
	 * This method is for check stalemate situations
	 * @param col is the checked king's color
	 * @param board is the current state of the board
	 */
	public void checkStalemate(SideColor col, Chess_piece[][] board)
	{
		/**
		 * Gets all the safe moves for the col colored king
		 */
		ArrayList<Position> any = getAllSafeMoves(col, board);
		/**
		 * if any is null, the king can't go anywhere
		 * then it sets the endgame to true, and closes the frame with a message dialog
		 */
		if(any.isEmpty())
		{
			possibleMoves.clear();
			repaint();
			JOptionPane.showMessageDialog(null, col.getString() + "s have no more available moves. The game ends with a draw. ", "Stalemate", JOptionPane.INFORMATION_MESSAGE);
			closeFrame();			
		}
	}
	
	/**
	 * This method returns the step count to outer classes
	 * @return the step count
	 */
	public static int getStep()
	{
		return steps;
	}
	
	/**
	 * This method provides the board current state to outer classes
	 */
	public static Chess_piece[][] getBoard()
	{
		return board;
	}
	
	/**
	 * This method closes the frame and gets back to the menu
	 */
	public void closeFrame() 
	{
		new Menu();
		SwingUtilities.windowForComponent(this).dispose();
	}
	
	/**
	 * This method provides the board to the Saver class
	 */
	public static void Save()
	{
		Saver.toSaver(board, whiteMove, steps);
	}
}