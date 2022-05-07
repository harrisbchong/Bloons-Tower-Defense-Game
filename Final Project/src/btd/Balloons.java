package btd;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
/*
 *Class for the enemy/mob of the game. Calls balloons to spawn on the screen and moves them. 
 *This will be called within levels to have multiple spawn on the screen. Also holds methods to lose player health, balloon health, gain money, death/delete.
 *Balloons
 *Harris Chong
 *January 20, 2021
 */
public class Balloons extends Rectangle{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int xcoord,ycoord;// coordinates of balloons
	public int walk=0;// move counter for each tile
	public int up=0, down=1,right=2;// movement directions
	public int direction =right;// default direction
	public int bloonID=Values.bloonAir;// ID for each balloon
	public int health;// balloon health
	public int walkFrame=0,walkSpeed=40;// how fast balloons move
	
	public boolean in_game=false;// default balloons are not in game 
	public boolean hasUp=false;// up direction for turning
	public boolean hasDown=false;// down direction for turning
	
	/*
	 * Sets the spawn requirements for a balloon to spawn on the path.
	 * Pre: int bloonID
	 * Post: n/a
	 */
	public void spawn(int bloonID){
		for(int y=0;y<Screen.Map.tile.length;y++){
			if(Screen.Map.tile[y][0].groundID==Values.path){
				setBounds(Screen.Map.tile[y][0].x-30,Screen.Map.tile[y][0].y-20, 100, 100);// where the balloon spawns
				
				xcoord=0;
				ycoord=y;
			}
		}
		
		this.bloonID=bloonID;
		this.health=100;// balloon health
		
		in_game=true;
	}
	
	/*
	 * Deletes the balloon from the game when called.
	 * Pre: n/a
	 * Post: n/a
	 */
	public void delete(){
		in_game=false;// takes balloon out of the game
		direction=right;// set direction back to default
		walk=0;// balloon no longer moves
	}
	
	/*
	 * Removes one from your health when called.
	 * Pre: n/a
	 * Post: n/a
	 */
	public void loseHealth(){
		Screen.health-=1;// lose one player health
	}
	
	/*
	 * Allows the balloons to move and follow the path. Once balloons reach the end they are deleted and they make you lose one health.
	 * Pre: n/a
	 * Post: n/a
	 */
	public void physic(){
		// if counter greater than timer to move
		if(walkFrame>=walkSpeed){
			if(direction==right){
				x+=2;// move 2 right
			}else if(direction==up){
				y-=2;// move 2 up
			}else if(direction==down){
				y+=2;//move 2 down
			}
			
		walk+=2;//add to move counter

		//if move counter equal tile size (move past whole tile)
		if(walk== Screen.Map.tileSize){
			if(direction==right){
				xcoord+=1;// move balloon x coordinate to next tile right
			}else if(direction==up){
				ycoord-=1;// move balloon y coordinate to next tile up
				hasUp=true;// set current direction up
			}else if(direction==down){
				ycoord+=1;// move balloon y coordinate to next tile down
				hasDown=true;// set current direction down
			}
			
			
			try{
				// if tile above is path
				if(Screen.Map.tile[ycoord-1][xcoord].groundID==Values.path){
					direction=up;// set direction up
				}
			}catch (Exception e){}
			
			// if not going up
			if(!hasUp){
				try{
					// if tile below is path
					if(Screen.Map.tile[ycoord+1][xcoord].groundID==Values.path){
						direction=down;//set direction down
						}
				}catch (Exception e){}
			}
			
			//if not going down or up
			if(!hasDown||!hasUp){
				try{
					// if tile to the right is path
					if(Screen.Map.tile[ycoord][xcoord+1].groundID==Values.path){
						direction=right;// set direction right
					}
				}catch (Exception e){}
			}
			
			//if balloon reaches end
			if(x>786){
				delete();// delete balloon
				loseHealth();// lose player health
			}
			
			// reset sequence for next tile
			hasUp=false;
			hasDown=false;
			walk=0;
		}
		
		// reset walk timer
		walkFrame=0;
		}else{
			// add to walk counter
			walkFrame+=1;
		}
	}
	
	/*
	 * Removes an amount of health from the balloon depending on which tower is damaging it and checks if it is dead once it takes damage.
	 * Pre: int amount
	 * Post: n/a
	 */
	public void loseHealth(int amount){
		health -=amount;
		checkDeath();
	}
	
	/*
	 * Checks if balloon health is under 0 then deletes the balloon if true.
	 * Pre: n/a
	 * Post: n/a
	 */
	public void checkDeath(){
		if(health<=0){
			delete();
		}
	}
	
	/*
	 * Checks if the balloon is dead (in_game or deleted) and returns a boolean.
	 * Pre: n/a
	 * Post: boolean true or false
	 */
	public boolean isDead(){
		if(in_game){
			return false;
		}else{
			return true;
		}
	}

	/*
	 * If the thread is in_game, draw the balloons on the screen. 
	 * Pre: Graphics g
	 * Post: n/a
	 */
	public void draw(Graphics g){
		g.drawImage(Screen.bloons[bloonID], x, y, width, height, null);
		
		//health bar
		g.setColor(new Color(180,50,50));
		g.fillRect(x,y,width,3);
		
		g.setColor(new Color(50,180,50));
		g.fillRect(x,y,health,3);
		
	}
}
