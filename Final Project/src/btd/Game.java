package btd;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
/*
 *Class with main menu set up with menu buttons and backgrounds. 
 *Has instructions page and buttons to start the game in different difficulties.
 *Game
 *Harris Chong
 *January 20, 2021
 */
public class Game extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel pnl = new JPanel();// panel for main menu buttons
	JPanel backButton = new JPanel();// panel for the back button on instructions screen
	Image mainbackground, instructions;// variables for backgrounds
	
	/*
	 * Performs functions for when each button is pressed
	 * Pre: ActionEvent event
	 * Post: n/a
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		String eventName = event.getActionCommand();// takes the event action (button pressed)
		
		// if user selects instructions button
		if (eventName.equals("Instructions")) {
			state=STATE.INSTRUCTIONS;// changes game state to instructions
			repaint();// repaints the screen
		}

		// if user selects easy button
		if (eventName.equals("Easy")) {
			
			Screen.map=1;// sets the map to map1.map file
			Screen.money=650;// sets user money to 650
			Screen.health=100;// sets user health to 100
			
			backButton.setVisible(false);// sets it so you cant see the back button
			pnl.setVisible(false);// sets it so you cant see the menu buttons
			
			Frame screen = new Frame(1);// runs the in game screen
		}

		// if user selects medium button
		if (eventName.equals("Medium")) {
			
			Screen.map=2;// sets the map to map2.map file
			Screen.money=400;// sets user money to 400
			Screen.health=75;// sets user health to 75
			
			backButton.setVisible(false);
			pnl.setVisible(false);
			
			Frame screen = new Frame(1);
		}
		
		// if user selects hard button
		if (eventName.equals("Hard")) {
			Screen.map=3;// sets the map to map3.map file
			Screen.money=200;// sets user money to 200
			Screen.health=50;// sets user health to 50
			
			backButton.setVisible(false);// sets it so you cant see the back button
			pnl.setVisible(false);// sets it so you cant see the menu buttons
			
			Frame screen = new Frame(1);// runs the in game screen
		}
		
		// if user selects back button
		if (eventName.equals("Back")) {
			state=STATE.MENU;// sets game state to MENU
			repaint();// repaints the screen
		}
	}
	
	// initializes three game states
	public static enum STATE{
		MENU,// menu state 
		INSTRUCTIONS,// instructions state 
		GAME// game state 
	}

	public static STATE state;// sets state variable to hold one of the states above
	
	/*
	* constructor
	* pre: n/a
	* post: Creates the menu and back buttons, adds them to the corresponding panels
	*/
	public Game() {
		state=STATE.MENU;//default state when frame is ran is menu so it starts with menu screen
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		mainbackground = kit.getImage("res/background.png");// sets the image gotten to variable
		instructions = kit.getImage("res/instructions.jpg");
		
		// button for instructions
		JButton instruction = new JButton("INSTRUCTIONS");
		instruction.setBounds(1000 / 2 - 100, 750 / 2 + 180, 200, 60);// sets button position
		instruction.setActionCommand("Instructions");// sets action listener
		instruction.addActionListener(this);// adds action listener

		// map buttons
		// easy button
		JButton easy = new JButton("EASY");
		easy.setBounds(1000 / 2 - 325, 750 / 2 + 260, 500, 560);// sets button position
		easy.setActionCommand("Easy");// sets action listener
		easy.addActionListener(this);// adds action listener
		
		// medium button
		JButton medium = new JButton("MEDIUM");
		medium.setBounds(1000 / 2 - 100, 750 / 2 + 260, 500, 560);// sets button position
		medium.setActionCommand("Medium");// sets action listener
		medium.addActionListener(this);// adds action listener

		//hard button
		JButton hard = new JButton("HARD");
		hard.setBounds(1000 / 2 + 125, 750 / 2 + 260, 500, 560);// sets button position
		hard.setActionCommand("Hard");// sets action listener
		hard.addActionListener(this);// adds action listener
		
		//adds buttons to the panel
		pnl.add(instruction);
		pnl.add(easy);
		pnl.add(medium);
		pnl.add(hard);
		add(pnl);
		
		
		//back button
		JButton back = new JButton("BACK");
		back.setBounds(1000 / 2 - 325, 750 / 2 + 260,500, 560);
		back.setActionCommand("Back");
		back.addActionListener(this);
					
		backButton.add(back);
		add(backButton);
	}
	
	/*
	 * Draws the menu backgrounds and buttons created in the Game method depending on the state. 
	 * Pre: Graphics g
	 * Post: n/a
	 */
	public void paintComponent(Graphics g) {
		
		// if MENU is the current state
		if (state==STATE.MENU) {
			g.drawImage(mainbackground, 0, 0, this);// draw main background image
			pnl.setVisible(true);// sets menu buttons to visible
			backButton.setVisible(false);// sets back button not visible
		}
		
		// if INSTRUCTIONS is the current state
		if(state==STATE.INSTRUCTIONS) {
			
			g.drawImage(instructions, 0, 0, this);// draw instructions background

			// Draws How to Play text
			g.setFont(new Font("Oetztype", Font.PLAIN, 17));// sets font, font type, and font size
			g.setColor(Color.BLACK);
			g.drawString(
					"Welcome to Bloons Tower Defense, made by Harris Chong. In this game, you lead a team of monkeys with the one main goal",
					10, 70);
			g.drawString(
					"in mind, popping the enemy bloons. Bloons will spawn on a path, and your job is to place monkeys strategically",
					10, 90);
			g.drawString(
					"throughout the map in order to not let the bloons reach the end of the path. Popping bloons will drop money in order",
					10, 110);
			g.drawString(
					
					"to help you buy more monkeys later on. Depending on the difficulty, you have have to pop 50, 75, and 100 bloons for the",
					10, 130);
			g.drawString(
					"easy, medium, and hard maps. The difficulty also changes your starting health to 100,75, and 50, and money to 650, 400 and 200.",
					10, 150);
			
			g.setFont(new Font("Oetztype", Font.BOLD, 17));

			// Tower descriptions
			g.setColor(Color.BLACK);
			g.drawString("Monkey - Does one damage per second. Cost: $" + 150, 70, 210);

			g.setColor(Color.BLACK);
			g.drawString("Ninja Monkey - Does three damage per second. Cost: $" + 450, 70, 270);
			
			g.setColor(Color.BLACK);
			g.drawString("Super Monkey - Does eight damage per second. Cost: $" + 2000, 70, 330);
			
			// Ending text
			g.setFont(new Font("Oetztype", Font.PLAIN, 17));
			g.drawString(
					"Everytime a bloon reaches the end you will lose one health. Can you save the monkeys from the bloon apocalypse? ",
					10, 390);
			
			backButton.setVisible(true);
			pnl.setVisible(false);
			setVisible(true);
		}
		
		// if GAME is the current state
		if(state==STATE.GAME){
			// set all buttons to not visible
			pnl.setVisible(false);
			backButton.setVisible(false);
		}
	}
	
	public static void main(String[] args) {
		Frame frame = new Frame();
		
	}
}