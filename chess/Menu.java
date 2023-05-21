package chess;
/**
 * Based on Forestf90's Menu.java
 * Inspired by Kev1nChen's LaunchFrame.java
 * Rewrited by me the whole class
 * This creates the whole launch panel, and all the bits and pieces that it needs
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import chess.board.BoardAI;
import chess.board.BoardPvP;
import chess.pieces.Chess_piece;

/**
 * This creates the interface for the Menu
 */
public class Menu extends JFrame 
{
	private JPanel eastPanel;
	private JPanel westPanel;
	private JPanel titlePanel;
	
	private JButton ngameButton;
	private JButton lgameButton;
	private JButton exitButton;
	private JButton pvpButton;
	private JButton aiButton;
	private JButton backButton;
	
	private JLabel titleLabel;
	private JLabel logoLabel;
	
	private JFileChooser fileChooser;
	
	private Font f = new Font(Font.DIALOG, Font.PLAIN, 24);
	private Font fi = new Font(Font.DIALOG, Font.ITALIC, 42);
	
	/**
	 * The constructor of this class
	 * Sets the frame's name and icon to a previously seted one
	 * Also calls for @method LoadInterface()
	 */
	public Menu() 
	{
		super("Chess");
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/icon.png"));
		this.setIconImage(icon.getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LoadInterface();
	}
	
	/**
	 * Calls the initialize methods, then  sets them in place on the frame
	 */
	public void LoadInterface()
	{
		/**
		 * initialize methods
		 */
		initializeTitlePanel();
		initializeEastPanel();
		initializeWestPanel();
		/**
		 * setting the panels to their places
		 */
		this.setLayout(new BorderLayout());
		this.add(titlePanel, BorderLayout.NORTH);
		this.add(eastPanel, BorderLayout.EAST);
		this.add(westPanel, BorderLayout.WEST);
		/**
		 * setting the frame around the panels and sets it visible and nonresizable
		 */
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	/**
	 * Initializes the title
	 * Sets the title's text, font, and color, then loads it into a panel
	 */
	public void initializeTitlePanel()
	{
		titleLabel = new JLabel("Choose game mode");
		titleLabel.setFont(fi);
		titleLabel.setForeground(Color.LIGHT_GRAY);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		titlePanel = new JPanel(new GridLayout(1, 1));
		titlePanel.setBackground(Color.decode("#287878"));
		titlePanel.setPreferredSize(new Dimension(800, 80));
		titlePanel.add(titleLabel);
	}
	
	/**
	 * tries to load a logo, adding it to a panel, then setting the panel's parameters
	 */
	public void initializeWestPanel()
	{
		try
		{
			Image logo = new ImageIcon(this.getClass().getResource("/logo.png")).getImage();
			logoLabel = new JLabel();
			logoLabel.setIcon(new ImageIcon(logo));
			
			westPanel = new JPanel();
			westPanel.add(logoLabel);
			westPanel.setPreferredSize(new Dimension(500, 350));
			westPanel.setBackground(Color.decode("#287878"));
		}
		
		catch(Exception e)
		{
			logoLabel = new JLabel("Chess");
			logoLabel.setFont(fi);
			logoLabel.setHorizontalAlignment(SwingConstants.LEFT);
			
			westPanel = new JPanel();
			westPanel.add(logoLabel);
			westPanel.setPreferredSize(new Dimension(500, 350));
			westPanel.setBackground(Color.decode("#287878"));
		}
	}
	
	/**
	 * This method contains all the menu controlling buttons and the action listeners to them
	 */
	public void initializeEastPanel()
	{
		/**
		 * Sets the new game button up, gives a font, color, and size to it, and adds a action listener
		 * Triggers the @method newgameTrigger()
		 */
		ngameButton = new JButton("New Game");
		ngameButton.setFont(f);
		ngameButton.setBackground(Color.decode("#285f5f"));
		ngameButton.setForeground(Color.decode("#289696"));
		ngameButton.setBorder(BorderFactory.createDashedBorder(Color.decode("#287878"), 35, 30, 10, false));
		ngameButton.addActionListener(new ActionListener() 
		{
		    @Override
		    public void actionPerformed(ActionEvent e) 
		    {
		    	newgameTrigger();
		    }
		});
		/**
		 * Sets the load game button up, gives a font, color, and size to it, and adds a action listener
		 * Triggers the @method loadgameTrigger()
		 */
		lgameButton = new JButton("Load Game");
		lgameButton.setFont(f);
		lgameButton.setBackground(Color.decode("#285f5f"));
		lgameButton.setForeground(Color.decode("#289696"));
		lgameButton.setBorder(BorderFactory.createDashedBorder(Color.decode("#287878"), 35, 30, 10, false));
		lgameButton.addActionListener(new ActionListener() 
		{
            @Override
            public void actionPerformed(ActionEvent e) 
            {
            	loadgameTrigger();
            }
        });
		/**
		 * Sets the exit game button up, gives a font, color, and size to it, and adds a action listener
		 * Triggers the program to close
		 */
        exitButton = new JButton("Exit");
        exitButton.setFont(f);
        exitButton.setBackground(Color.decode("#285f5f"));
		exitButton.setForeground(Color.decode("#289696"));
		exitButton.setBorder(BorderFactory.createDashedBorder(Color.decode("#287878"), 35, 30, 10, false));
        exitButton.addActionListener(new ActionListener() 
		{
		    @Override
		    public void actionPerformed(ActionEvent e) 
		    {
		        System.exit(0);
		    }
		});
        /**
		 * Sets the pvp game button up, gives a font, color, and size to it, and adds a action listener
		 * Triggers the @method pvpTrigger()
		 */
        pvpButton = new JButton("PvP");
    	pvpButton.setFont(f);
    	pvpButton.setBackground(Color.decode("#285f5f"));
    	pvpButton.setForeground(Color.decode("#289696"));
    	pvpButton.setBorder(BorderFactory.createDashedBorder(Color.decode("#287878"), 35, 30, 10, false));
    	pvpButton.addActionListener(new ActionListener() 
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    			pvpTrigger();
    		}
    	});
    	/**
		 * Sets the pvm game button up, gives a font, color, and size to it, and adds a action listener
		 * Triggers the @method aiTrigger()
		 */
    	aiButton = new JButton("PvM");
    	aiButton.setFont(f);
    	aiButton.setBackground(Color.decode("#285f5f"));
    	aiButton.setForeground(Color.decode("#289696"));
    	aiButton.setBorder(BorderFactory.createDashedBorder(Color.decode("#287878"), 35, 30, 10, false));
    	aiButton.addActionListener(new ActionListener() 
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    			aiTrigger();
    		}
    	});
    	/**
		 * Sets the back button up, gives a font, color, and size to it, and adds a action listener
		 * Triggers the @method backTrigger()
		 */
    	backButton = new JButton("Back");
    	backButton.setFont(f);
    	backButton.setBackground(Color.decode("#285f5f"));
    	backButton.setForeground(Color.decode("#289696"));
    	backButton.setBorder(BorderFactory.createDashedBorder(Color.decode("#287878"), 35, 30, 10, false));
    	backButton.addActionListener(new ActionListener() 
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    			backTrigger();
    		}
    	});
    	/**
    	 * Here it sets up the panel with the basis buttons, and sets the size
    	 */
		eastPanel = new JPanel(new GridLayout(3, 1));
		eastPanel.setPreferredSize(new Dimension(300, 350));
		eastPanel.setBackground(Color.decode("#287878"));
		eastPanel.add(ngameButton);
		eastPanel.add(lgameButton);
		eastPanel.add(exitButton);
	}

	/**
	 * This method sets the east panel to a new shape
	 * Removes everything from it, and adds back all the buttons needed to start a new game, then repaint it
	 */
	public void newgameTrigger()
	{
		eastPanel.removeAll();
    	eastPanel.add(pvpButton);
    	eastPanel.add(aiButton);
    	eastPanel.add(backButton);
    	eastPanel.revalidate();
    	eastPanel.repaint();
	}

	/**
	 * This method shows an open file dialog to the user
	 * By the response the user makes gets the file's path or do nothing
	 * If the user chose to open a file then tries to load it
	 * With the loaded parameters it tries to call the appropriate methods to create a game
	 */
	public void loadgameTrigger()
	{
		/**
		 * this creates the dialog
		 * sets the filechooser's directory to the working directory
		 * and sets a filter for file types so the user has an easier time finding the game file he or she wants
		 */
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("."));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Serializated Files", "ser");
		fileChooser.setFileFilter(filter);
		/**
		 * stores the response of the user
		 */
		int response = fileChooser.showOpenDialog(null);
		
		if(response == JFileChooser.APPROVE_OPTION)
		{
			/**
			 * gets the file's path from the open dialog
			 */
			File loadfile = new File(fileChooser.getSelectedFile().getAbsolutePath());
			/**
			 * calls for the Loader class's @method loadgame() with the loadfile
			 */
			Loader.loadGame(loadfile);
			/**
			 * temporary variables for the load
			 */
			String mode = "";
	    	Chess_piece[][] loadb = new Chess_piece[8][8];
	    	SideColor[][] color = new SideColor[8][8]; 
	    	boolean whitemv = true;
	    	String mh = "";
	    	int stepc = 0;
	    	/**
	    	 * gets the values from the @class Loader
	    	 */
	    	mode = Loader.getMode();
	    	whitemv = Loader.getWhite();
	    	mh = Loader.getMh();
	    	stepc = Loader.getSteps();
	    	for(int i = 0; i < 8; i++)
	    	{
	    		for(int j = 0; j < 8; j++)
	    		{
	    			loadb[j][i] = Loader.getBoard(j, i);
	    			if(loadb[j][i] != null)
	    				color[j][i] = Loader.getColor(j, i);
	    		}
	    	}
	    	/**
	    	 * checks the mode's value
	    	 * if it's pvp...
	    	 */
	    	if(mode.contains("PvP"))
	    	{
	    		new GameFrame(new BoardPvP(loadb, color, whitemv, stepc), "PvP\n", mh);
	    		setVisible(false);
	    	    dispose();
	    	}
	    	/**
	    	 * ...or if it's ai
	    	 */
	    	else if(mode.contains("AI"))
	    	{
	    		new GameFrame(new BoardAI(loadb, color, whitemv, stepc), "AI\n", mh);
	    		setVisible(false);
	    		dispose();
	    	}
		}
    }

	/**
	 * Calls for a new game in pvp mode, so 2 player play on 1 window
	 * Sets this frame invisible and closes this frame
	 */
	public void pvpTrigger()
	{
		new GameFrame(new BoardPvP(), "PvP\n", "Game start!\n");
        setVisible(false);
        dispose();
	}

	/**
	 * Calls for a new game in pvm mode, so against a computer controlled enemy
	 * Sets this frame invisible and closes this frame
	 */
	public void aiTrigger()
	{
		new GameFrame(new BoardAI(), "AI\n", "Game start!\n");
        setVisible(false);
        dispose();
	}

	/**
	 * This method sets the east panel to its "original" shape
	 * Removes everything from it, and adds back all the buttons needed to the menu, then repaint it
	 */
	public void backTrigger()
	{
		eastPanel.removeAll();
    	eastPanel.add(ngameButton);
    	eastPanel.add(lgameButton);
    	eastPanel.add(exitButton);
    	eastPanel.revalidate();
    	eastPanel.repaint();
	}
}