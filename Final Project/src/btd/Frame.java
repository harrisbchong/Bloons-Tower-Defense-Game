package btd;

import javax.swing.JFrame;

import java.awt.*;
/*
 *Class that sets up the frame/window for the game and menu.
 *Frame
 *Harris Chong
 *January 20, 2021
 */
public class Frame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	* constructor
	* pre: n/a
	* post: Frame and menu screen created
	*/
	public Frame() {
		setTitle("Bloons Tower Defense");// name of the frame
		setSize(1000, 750);// size of the frame
		setResizable(false);// frame isn't resizable
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// exits frame when close is pressed
	
		Game game = new Game();// creates new game (menu screen0
		add(game);// adds game to frame
		setVisible(true);// makes it visible
		
	}
	
	/*
	* constructor
	* pre: int a
	* post: Frame and in game screen created
	*/
	public Frame(int a) {
		setTitle("Bloons Tower Defense");// name of the frame
		setSize(1000, 750);// size of the frame
		setResizable(false);// frame isn't resizable
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// exits frame when close is pressed

		setLayout(new GridLayout(1, 1, 0, 0));// sets layout for the new screen
		Screen screen = new Screen(this);// creates new screen (in game)
		add(screen);// adds game to frame
		setVisible(true);// makes it visible
	}

}
