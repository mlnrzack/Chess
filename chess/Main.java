package chess;

/**
 * Based on Forestf90's Main.java
 * Rearranged it fit in my style
 * Didn't needed any change
 */

import java.awt.EventQueue;

//import org.junit.runner.*;

public class Main 
{
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() 
		{
			@Override
			public void run() 
			{
				new Menu();
			}
		});
	} 
}