package btd;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/*
 *Class that is the main game screen, shows the screen by calling and drawing the map, save, and layout screen. Spawns the enemies.
 *Screen
 *Harris Chong
 *January 20, 2021
 */
public class Screen extends JPanel implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Thread in_game = new Thread(this);// runnable thread for when game starts
	
	public static boolean first = true;// boolean that allows everything to draw first before running the game
	public static boolean win=false;// boolean for if you have won
	
	public static int myWidth, myHeight;// dimensions of the frame size
	public static int money;// amount of money
	public static int health;// amount of health
	public static int killed=0, killsToWin;// amount of balloons killed, amount of balloons needed to win
	public int spawnTime=1400, spawnFrame=0;// time it takes to spawn another balloon, variable used to count the time
	
	public static int map;// variable to change map
	
	public static Map Map;// variable to create the map
	public static Save save;// variable to load and scan the map save file
	public static Layout layout;// variable to create the layout
	
	public static Image[] ground = new Image[100];// array of the ground tile images
	public static Image[] air = new Image[100];// array of the air tile images
	public static Image[] button=new Image[100];// array of button images
	public static Image[] bloons=new Image[100];// array of bloon images
	public static Image[] tower=new Image[100];// array of tower images
	
	public static Balloons[] balloons=new Balloons[500];// variable to create balloons
	
	public static Point mouse=new Point(0,0);// variable for mouse location
	
	/*
	* constructor
	* pre: Frame frame
	* post: Starts the game thread and implements the key listeners
	*/
	public Screen(Frame frame) {
		// adds listeners to screen
		frame.addMouseListener(new Keys());
		frame.addMouseMotionListener(new Keys());
		
		in_game.start();// starts game thread
	}
	
	/*
	 * Changes the state of win boolean to true
	 * Pre: n/a
	 * Post: n/a
	 */
	public static void hasWon(){
		// if number of balloons killed equals to number needed to kill to win
		if(killed==killsToWin){
			win=true;// set boolean win true
		}
	}
	
	/*
	 * Creates the map (each tile), the save (map layout), and in game layout. Imports the images from the res folder and creates buttons for icons.
	 * Pre: n/a
	 * Post: n/a
	 */
	public void def() {
		Map = new Map();// creates map
		save = new Save();// creates save
		layout = new Layout();// creates layout
		
		for (int i = 0; i < ground.length; i++) {
			ground[i] = new ImageIcon("res/ground1.png").getImage();// sets each ground tile to ground image
			ground[i] = createImage(new FilteredImageSource(ground[i].getSource(), new CropImageFilter(0, 26 * i, 26, 26)));// crops image
		}

		for (int i = 0; i < air.length; i++) {
			air[i] = new ImageIcon("res/air.png").getImage();// sets each air tile to air image
			air[i] = createImage(new FilteredImageSource(air[i].getSource(), new CropImageFilter(0, 26 * i, 26, 26)));
		}
		
		// tower icons
		button[15]=new ImageIcon("res/dart_monkey.png").getImage();
		button[16]=new ImageIcon("res/ninja_monkey.png").getImage();
		button[17]=new ImageIcon("res/super_monkey.png").getImage();
		button[4]=new ImageIcon("res/trash.png").getImage();
		
		// tower images
		tower[15]=new ImageIcon("res/dartTower.png").getImage();
		tower[16]=new ImageIcon("res/ninjaTower.png").getImage();
		tower[17]=new ImageIcon("res/superTower.png").getImage();
		
		// layout images
		button[6]=new ImageIcon("res/coin.png").getImage();
		button[7]=new ImageIcon("res/health.png").getImage();
		
		// balloon images
		bloons[0]=new ImageIcon("res/red_bloon.png").getImage();
		
		save.loadSave(new File("save/map"+map+".map"));// loads the map
		
		// run for all balloons
		for(int i=0;i<balloons.length;i++){
			balloons[i]=new Balloons();// create new balloon object
		}
	}

	/*
	 * Draws the border and background around the map screen and draws the map and layout on the screen. Also draws the win and lose screen.
	 * Pre: Graphics g
	 * Post: n/a
	 */
	public void paintComponent(Graphics g) {
		Graphics2D ga = (Graphics2D) g;
		
		if (first) {
			myWidth = getWidth();
			myHeight = getHeight();
			def();

			first = false;
		}
	
		ga.setColor(new Color(138, 88, 19));// sets color
		ga.fillRect(0, 0, getWidth(), getHeight());// fills background of layout
		
		// border around map
		ga.setColor(new Color(0,0,0));
		ga.setStroke(new BasicStroke(3));
		ga.drawLine(Map.tile[0][0].x-1, 0,Map.tile[0][0].x-1 , Map.tile[Map.height-1][0].y-1+Map.tileSize+1);
		ga.drawLine(Map.tile[0][Map.width-1].x + Map.tileSize, 0,Map.tile[0][Map.width-1].x + Map.tileSize , Map.tile[Map.height-1][0].y-1+Map.tileSize+1);
		ga.drawLine(Map.tile[0][0].x, Map.tile[Map.height-1][0].y+Map.tileSize,Map.tile[0][Map.width-1].x+Map.tileSize,Map.tile[Map.height-1][0].y+Map.tileSize);
		
		ga.setColor(new Color(87, 52, 5));
		
		Map.draw(ga);// draws map
		
		// for all balloons
		for(int i=0;i<balloons.length;i++){
			// if balloons are in game
			if(balloons[i].in_game){
				balloons[i].draw(ga);// draw balloon on screen
			}
		}
		
		layout.draw(ga);// draws layout
		
		if(health<1){
			// draw game over screen/text
			g.setColor(new Color(0,0,0));
			g.setFont(new Font("Oetztype", Font.BOLD, 30));
			g.drawString("Game Over", 1000/2-75, 750/2-50);

		}
		
		if(win){
			// draw winning screen/text
			g.setColor(new Color(0,0,0));
			g.setFont(new Font("Oetztype", Font.BOLD, 30));
			g.drawString("You Won", 1000/2-75, 750/2-50);
		}
	}

	/*
	 * Spawner that uses a basic timer to periodically spawn balloon objects every few seconds.
	 * Pre: n/a
	 * Post: n/a
	 */
	public void spawner(){
		
		// once counter is greater than time to spawn
		if(spawnFrame>=spawnTime){
			// for each balloon
			for(int i=0;i<balloons.length;i++){
				// if the balloon is not in game
				if(!balloons[i].in_game){
					balloons[i].spawn(Values.bloon);// spawn another balloon
					break;
				}
			}
			
			spawnFrame=0;// reset counter
		}else{
			spawnFrame+=1;// add to counter
		}
	}
	
	/*
	 * Runs the in_game thread, spawner and shooting.
	 * Pre: n/a
	 * Post: n/a
	 */
	public void run() {
		while (true) {
			// if not won and health is greater than 0
			if (!first && health>0 && !win) {
				Map.physic();// runs block physic
				spawner();// starts spawner
				
				for(int i=0;i<balloons.length;i++){
					if(balloons[i].in_game){
						balloons[i].physic();// runs balloon movement when in game
					}
				}
			}

			
			repaint();

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {

			}
		}
	}

}
