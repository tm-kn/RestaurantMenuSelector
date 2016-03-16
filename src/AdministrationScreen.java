import javax.swing.JFrame;

import exceptions.InvalidAdministrationPasswordException;

public class AdministrationScreen extends JFrame {
	final static String PASSWORD = "admin123";
	private Menu menu;
	
	public AdministrationScreen(Menu menu, String inputtedPassword) {
		super("Administation screen");
		this.checkCredentials(inputtedPassword);
		this.menu = menu;
	}
	
	private void checkCredentials(String password) {
		if(!password.equals(AdministrationScreen.PASSWORD)) {
			throw new InvalidAdministrationPasswordException();
		}
	}
}
