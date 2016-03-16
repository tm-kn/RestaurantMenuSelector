import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import exceptions.InvalidAdministrationPasswordException;

public class LaunchScreen extends JFrame {

	private Container cp;
	private JButton openOrderScreenButton, openAdministrationScreenButton, exitProgrammeButton;
	private JPanel mainPanelCentre;
	private Menu menu;

	/**
	 * Create the frame.
	 */
	public LaunchScreen(Menu menu) {
		super("Restaurant Menu Selector - Launch Screen");
		this.menu = menu;
		
		this.cp = this.getContentPane();
		this.cp.setLayout(new BorderLayout());

		this.openOrderScreenButton = new JButton("Open order window");
		this.openAdministrationScreenButton = new JButton("Open administration screen");
		this.exitProgrammeButton = new JButton("Exit programme");

		this.openOrderScreenButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						try {
							JFrame orderScreen = new OrderScreen(LaunchScreen.this.menu);
							orderScreen.setVisible(true);
						} catch (Exception exception) {
							exception.printStackTrace();
						}
					}
				});

			}

		});

		this.openAdministrationScreenButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				String inputValue = (String) JOptionPane.showInputDialog(LaunchScreen.this,
						"Please enter administration password", "Enter password", JOptionPane.WARNING_MESSAGE);

				// If user clicks cancel, do nothing
				if (inputValue == null) {
					return;
				}

				// Open administration window
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						try {
							AdministrationScreen frame = new AdministrationScreen(LaunchScreen.this.menu, inputValue);
							frame.setLocationRelativeTo(LaunchScreen.this);
							frame.setVisible(true);
						} catch(InvalidAdministrationPasswordException e) {
							JOptionPane.showMessageDialog(LaunchScreen.this, "You have entered wrong password.",
									"Wrong password", JOptionPane.ERROR_MESSAGE);
						}					
					}
					
				});
				
			}
		});

		this.exitProgrammeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}

		});

		this.mainPanelCentre = new JPanel();

		this.mainPanelCentre.add(openOrderScreenButton);
		this.mainPanelCentre.add(openAdministrationScreenButton);
		this.mainPanelCentre.add(exitProgrammeButton);

		this.cp.add("Center", mainPanelCentre);
		this.setResizable(false);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		this.pack();

	}

}
