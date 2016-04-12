import javax.swing.SwingUtilities;

import gui.LaunchScreen;
import models.Menu;

/**
 * Class that is used to run a program.
 * It's not a demo version, so it doesn't have any data populated.
 * 
 * @author TJ Knapik <u1562595@unimail.hud.ac.uk>
 *
 */
public class RestaurantMenuSelector {
	
	protected Menu menu;
	
	/**
	 * Run the program
	 * @param args
	 */
	public static void main(String[] args) {
		new RestaurantMenuSelector();
	}
	
	/**
	 * Create instance of the program with empty menu
	 */
	public RestaurantMenuSelector() {
		this.menu = new Menu();
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					LaunchScreen frame = new LaunchScreen(RestaurantMenuSelector.this.menu);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
	}
}
