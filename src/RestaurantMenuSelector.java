import javax.swing.SwingUtilities;

public class RestaurantMenuSelector {
	
	protected Menu menu;
	
	public static void main(String[] args) {
		new RestaurantMenuSelector();
	}
	
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
