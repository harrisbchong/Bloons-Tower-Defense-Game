package btd;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
/*
 *Class for each tile of the map background/stage. Draws corresponding tile using a tile id in the constructor when called as an object. 
 *Also sets up range for towers placed and has the methods/mechanics for tower shooting.
 *Block
 *Harris Chong
 *January 20, 2021
 */
public class Block extends Rectangle {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Rectangle towerSquare;// tower shoot radius rectangle
	public int groundID, airID;// value of ground and air tile

	public int shot = -1;// which balloon is being shot
	public boolean shooting = false;// shooting boolean default false (not shooting)
	public int shotTime = 50, shotFrame = 0;// time it takes to shoot, counter to determine when to shoot
	
	/*
	* constructor
	* pre: int x,y,width,height,ground,air
	* post: tiles in the correct spot and dimensions with correct image
	*/
	public Block(int x, int y, int width, int height, int ground, int air) {
		setBounds(x, y, width, height);// sets tile position
		towerSquare = new Rectangle(x - 70, y - 70, width + 120, height + 120);// creates the shooting radius around the tile
		this.groundID = ground;
		this.airID = air;
	}
	
	/*
	 * Draws the individual ground and air tiles.
	 * Pre: Graphics g
	 * Post: n/a
	 */
	public void draw(Graphics g) {

		g.drawImage(Screen.ground[groundID], x, y, width, height, null);// draws ground tiles

		if (airID != Values.air) {
			g.drawImage(Screen.ground[airID], x, y, width, height, null);// draws air tiles

		}
	}
	
	/*
	 * Sets up the shooting mechanics of the tower and the balloons. Uses the tower range and intersects with the balloon to shoot and make the balloons lose health, 
	 * as well as gain money and update progress on the number of balloons killed to win.
	 * Pre: n/a
	 * Post: n/a
	 */
	public void physic() {
		// if not shooting the balloon and tower radius intersects the balloon
		if (shot != -1 && towerSquare.intersects(Screen.balloons[shot])) {
			shooting = true;// shooting true
		} else {
			shooting = false;// default not shooting
		}

		// if not shooting
		if (!shooting) {
			// if tower is in place of the air
			if (airID == Values.dartMonkey || airID == Values.ninjaMonkey || airID == Values.superMonkey) {
				for (int i = 0; i < Screen.balloons.length; i++) {
					if (Screen.balloons[i].in_game) {
						if (towerSquare.intersects(Screen.balloons[i])) {
							shooting = true;
							shot = i;// shoot the first balloon entered
						}
					}
				}
			}
		}

		// if shooting
		if (shooting) {
			// if dart monkey is shooting 
			if (airID == Values.dartMonkey) {
				// counter is over time to shoot
				if (shotFrame >= shotTime) {
					Screen.balloons[shot].loseHealth(1);// damage
					shotFrame = 0;// reset timer
				} else {
					shotFrame += 1;// add to counter
				}
				
			}
			
			// ninja monkey shooting damage
			if (airID == Values.ninjaMonkey) {
				if (shotFrame >= shotTime) {
					Screen.balloons[shot].loseHealth(3);
					shotFrame = 0;
				} else {
					shotFrame += 1;
				}
				
			}
			
			// super monkey shooting damage
			if (airID == Values.superMonkey) {
				if (shotFrame >= shotTime) {
					Screen.balloons[shot].loseHealth(8);
					shotFrame = 0;
				} else {
					shotFrame += 1;
				}
				
			}
			
			// if balloon is dead
			if (Screen.balloons[shot].isDead()) {
				getMoney(Screen.balloons[shot].bloonID);// add money

				shooting = false;// stop shooting that balloon
				shot = -1;
				
				Screen.killed +=1;// add to kill counter
				
				Screen.hasWon();// check if enough balloons have been killed to win
			}
		}
		
	}

	/*
	 * Adds money every time a method is called (when a balloon dies).
	 * Pre: int mob
	 * Post: n/a
	 */
	public void getMoney(int mob) {
		Screen.money += Values.moneyDrop;// add money drop to current money
	}

	/*
	 * Draws the shooting animation whenever a tower is shooting (Line from the tower to the middle of the balloon).
	 * Pre: Graphics g
	 * Post: n/a
	 */
	public void shoot(Graphics g) {
		if (shooting) {
			g.setColor(new Color(180, 50, 50));
			g.drawLine(x + width / 2, y + height / 2 + 10, Screen.balloons[shot].x + width / 2 + 10,Screen.balloons[shot].y + height / 2);
		}
	}
}
