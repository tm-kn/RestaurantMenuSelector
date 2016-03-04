import javax.swing.SwingUtilities;

/**
 * Start point for the programme.
 * 
 * @author Tomasz Knapik <u1562595@unimail.hud.ac.uk>
 *
 */
public class RestaurantMenuSelector {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					LaunchScreen frame = new LaunchScreen();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

	}

}
