package btd;

import java.awt.Graphics;
import java.awt.Graphics2D;
/*
 *Class that uses the Block Class objects and draws the produced corresponding tiles as a 2-D array, for a grid like map. This draws each individual tile together to form the final map.
 *Save
 *Harris Chong
 *January 20, 2021
 */
public class Map {
	public int width = 12;// width of map
	public int height = 8;// height of map
	public int tileSize = 64;// size of each tile

	public Block[][] tile;// 2d array of tiles created
	
	/*
	* constructor
	* pre: n/a
	* post: tiles with correct dimension and image
	*/
	public Map() {
		tile = new Block[height][width];

		for (int y = 0; y < tile.length; y++) {
			for (int x = 0; x < tile[0].length; x++) {
				tile[y][x] = new Block(((Screen.myWidth/2)-(width*tileSize)/2)+(x * tileSize), y * tileSize, tileSize, tileSize, Values.grass, Values.air);// sets each tile in position 
			}
		}
	}
	
	/*
	 * Calls the physic (Block class) to check if there is shooting for each tile
	 * Pre: n/a
	 * Post: n/a
	 */
	public void physic() {
		for(int y=0;y<tile.length;y++){
			for(int x=0;x<tile[0].length;x++){
				tile[y][x].physic();// updates shooting for each tile 
			}
		}
	}

	/*
	 * Draws each tile created by calling the draw (Block class) method and draws the laser shot calling the shoot (Block class) method for each tower.
	 * Pre: Graphics g
	 * Post: n/a
	 */
	public void draw(Graphics g) {
		Graphics2D ga = (Graphics2D) g;
		for (int y = 0; y < tile.length; y++) {
			for (int x = 0; x < tile[0].length; x++) {
				tile[y][x].draw(ga);// calls to draw each tile
			}
		}
		
		for (int y = 0; y < tile.length; y++) {
			for (int x = 0; x < tile[0].length; x++) {
				tile[y][x].shoot(ga);// calls shoot to draw the tower laser shot
			}
		}
	}
}
