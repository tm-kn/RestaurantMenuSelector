import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import exceptions.InvalidAdministrationPasswordException;

public class AdministrationScreen extends JFrame {

	private static final long serialVersionUID = -3373853423168231406L;
	final static String PASSWORD = "admin123";
	private JLabel screenHeading;
	private JPanel centrePane;
	private Container cp;
	private Menu menu;
	
	public AdministrationScreen(Menu menu, String inputtedPassword) {
		super("Administation screen");
		this.checkCredentials(inputtedPassword);
		this.menu = menu;
		this.cp = this.getContentPane();
		this.cp.setLayout(new BorderLayout());
		
		// Centre pane
		this.centrePane = new JPanel();
		this.centrePane.setLayout(new BoxLayout(this.centrePane, BoxLayout.X_AXIS));
		
		this.screenHeading = new JLabel("Administration screen");
		this.screenHeading.setFont(this.screenHeading.getFont().deriveFont(30));
		
		this.centrePane.add(this.screenHeading);
		
		this.cp.add(BorderLayout.CENTER, this.centrePane);
		
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	}
	
	private void checkCredentials(String password) {
		if(!password.equals(AdministrationScreen.PASSWORD)) {
			throw new InvalidAdministrationPasswordException();
		}
	}
}