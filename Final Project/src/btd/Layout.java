package btd;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
/*
 *Class that sets up the layout for the in game screen, including the store buttons and health/money. Also sets up the placement of the towers.
 *Layout
 *Harris Chong
 *January 20, 2021
 */
public class Layout {
	public static int shopWidth = 5;// shop width
	public Rectangle[] button = new Rectangle[5];// array of shop buttons
	public static int buttonSize = 64; //size of shop buttons
	public static int heldID;// id of held item
	public static int realID;// id of button price
	public static int[] buttonID={Values.dartMonkey,Values.ninjaMonkey,Values.superMonkey,3,Values.airTrash};// array for button shop icon
	
	public static int[] buttonPrice={150,450,2000,0,0};// array for shop prices
	
	public Rectangle healthButton;// variable to create health button
	public Rectangle coinButton;// variable to create coin button
	
	public boolean holdsItem=false;// default boolean not holding item
	
	/*
	* constructor
	* pre: n/a
	* post: Tower shop, health, and coin buttons
	*/
	public Layout() {
		for (int i = 0; i < button.length; i++) {
			button[i] = new Rectangle((Screen.myWidth / 2 - (shopWidth * (buttonSize + 4)) / 2) + ((buttonSize + 4) * i), 550, buttonSize,buttonSize);// creates button array list
			}
		
		healthButton=new Rectangle(140,560,20,20);// creates health button
		coinButton=new Rectangle(140,600,20,20);// create coin button
	}
	
	/*
	 * Takes the mouse event listeners and allows you to hold the tower clicked. While holding, you are also able to place down the tower if you have enough money.
	 * Pre: int mouseButton
	 * Post: n/a
	 */
	public void click(int mouseButton){
		// if left click 
		if(mouseButton==1){
			for(int i=0; i<button.length;i++){
				
				// if mouse on button
				if(button[i].contains(Screen.mouse)){
					heldID=buttonID[i];// hold id is the button
					realID=i;// real id is now the old button
					holdsItem=true;// holding item is true
					
					// if hold id is the air trash
					if(heldID==Values.airTrash){
						holdsItem=false;// stop holding
					}
				}			
			}
			
			if(holdsItem){
				// if money is greater than price of button
				if(Screen.money>=buttonPrice[realID]){
					for(int y=0;y<Screen.Map.tile.length;y++){
						for(int x=0;x<Screen.Map.tile[0].length;x++){
							if(Screen.Map.tile[y][x].contains(Screen.mouse)){
								// if tile is not path and tower is not already place in air slot
								if(Screen.Map.tile[y][x].groundID!=Values.path && Screen.Map.tile[y][x].airID == Values.air){
									Screen.Map.tile[y][x].airID=heldID;// change air id to tower
			
									Screen.money -=buttonPrice[realID];// subtract money used
								}
							}
						}
					}
				}
			}
		}
	}
	
	/*
	 * Draws the tower icon buttons, shows the button hover mechanic and draws the health/coin buttons and layout. Also draws the tower over your mouse as you are holding a tower.
	 * Pre: Graphics g
	 * Post: n/a
	 */
	public void draw(Graphics g) {
		
		//button hover
		for (int i = 0; i < button.length; i++) {
			// draw tower icon for button
			g.drawImage(Screen.button[buttonID[i]], button[i].x, button[i].y, 64, 64,null);
			//if button has mouse
			if (button[i].contains(Screen.mouse)) {
				// draw hover effect (white screen)
				g.setColor(new Color(255,255,255, 150));
				g.fillRect(button[i].x, button[i].y, 64, 64);

			}
			
			//button border
			g.setColor(new Color(87, 52, 5));
			g.drawRect(button[i].x, button[i].y, 64, 64);
			
			//tower prices
			if(buttonPrice[i]>0){
				g.setColor(new Color(0,0,0));
				g.setFont(new Font("Oetztype",Font.BOLD,14));
				g.drawString("$" + buttonPrice[i], button[i].x+5, button[i].y+14);
			}
		}
		
		//health layout
		g.drawImage(Screen.button[7],140,550,20,20,null);
		g.setColor(new Color(255,0,0));
		g.setFont(new Font("Oetztype",Font.BOLD,16));
		g.drawString("Health: "+Screen.health,180,565);
		
		//coin layout
		g.drawImage(Screen.button[6],140,590,20,20,null);
		g.setColor(new Color(235, 210, 52));
		g.setFont(new Font("Oetztype",Font.BOLD,16));
		g.drawString("Money: "+Screen.money,180,605);
		
		// when holding item
		if(holdsItem){
			g.drawImage(Screen.tower[heldID], Screen.mouse.x-32, Screen.mouse.y-32, 64,64,null);// draws tower icon around mouse
		}
	}
	
	
}
