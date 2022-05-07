package btd;
import java.awt.event.*;
import java.awt.*;
/*
 *Class allows for the other class to receive events (mouseReleased, mouseDragged, and mouseClicked)
 *Keys
 *Harris Chong
 *January 20, 2021
 */
public class Keys implements MouseMotionListener, MouseListener{

	@Override
	// unused excess events
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}
	
	/*
	 * Updates the screen layout when the mouse is clicked.
	 * Pre: MouseEvent e
	 * Post: n/a
	 */
	public void mousePressed(MouseEvent e) {
		Screen.layout.click(e.getButton());
	}

	/*
	 * Updates the position of the mouse if it's being dragged within the screen.
	 * Pre: MouseEvent e
	 * Post: n/a
	 */
	public void mouseDragged(MouseEvent e) {
		Screen.mouse=new Point((e.getX()) + ((1000 - Screen.myWidth)/2),(e.getY()) + ((750 - Screen.myHeight)/2) - ((1000 - Screen.myWidth)/2));// updates mouse with position within screen
		
	}

	/*
	 * Updates the position of the mouse if it's being moved within the screen.
	 * Pre: MouseEvent e
	 * Post: n/a
	 */
	public void mouseMoved(MouseEvent e) {
		Screen.mouse=new Point((e.getX()) - ((1000 - Screen.myWidth)/2),(e.getY()) - ((750 - Screen.myHeight)/2) - ((1000 - Screen.myWidth)/2));
		
	}

}
