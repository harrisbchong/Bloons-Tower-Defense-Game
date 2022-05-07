package btd;

import java.io.File;
import java.util.Scanner;
/*
 *Class used to read the map layout.
 *Save
 *Harris Chong
 *January 20, 2021
 */
public class Save {
	
	/*
	 * Uses a scanner that scans a file with the map layout and assigns the according value to the images.
	 * Pre: File loadPath
	 * Post: n/a
	 */
	public void loadSave(File loadPath) {
		try {
			Scanner input = new Scanner(loadPath);// import scanner

			// while map is scanned
			while (input.hasNext()) {
				Screen.killsToWin=input.nextInt();// kills to win is next int 
				
				for (int y = 0; y < Screen.Map.tile.length; y++) {
					for (int x = 0; x < Screen.Map.tile[0].length; x++) {
						Screen.Map.tile[y][x].groundID = input.nextInt();// goes through each tile and inputs corresponding groundID from numbers for the correct tiles
					}
				}

				for (int y = 0; y < Screen.Map.tile.length; y++) {
					for (int x = 0; x < Screen.Map.tile[0].length; x++) {
						Screen.Map.tile[y][x].airID = input.nextInt();// goes through each tile and inputs corresponding airID from numbers for the correct tiles
					}
				}

			}
			input.close();
		} catch (Exception e) {
		}
	}
}
