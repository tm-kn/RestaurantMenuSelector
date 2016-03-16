import javax.swing.JFrame;

import exceptions.InvalidAdministrationPasswordException;

public class AdministrationScreen extends JFrame {

	private static final long serialVersionUID = -3373853423168231406L;
	final static String PASSWORD = "admin123";
	private Menu menu;
	
	public AdministrationScreen(Menu menu, String inputtedPassword) {
		super("Administation screen");
		this.checkCredentials(inputtedPassword);
		this.menu = menu;
		
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	}
	
	private void checkCredentials(String password) {
		if(!password.equals(AdministrationScreen.PASSWORD)) {
			throw new InvalidAdministrationPasswordException();
		}
	}
}
