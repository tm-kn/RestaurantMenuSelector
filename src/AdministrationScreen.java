import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import exceptions.InvalidAdministrationPasswordException;
import java.awt.Component;

/**
 * Screen used to administer content of the menu.
 * 
 * @author TJ Knapik <u1562595@unimail.hud.ac.uk>
 *
 */
public class AdministrationScreen extends JFrame {

	private static final long serialVersionUID = -3373853423168231406L;
	final static String PASSWORD = "admin123";
	private DefaultListModel<Course> coursesListModel;
	private JButton closeButton, deleteSelectedCoursesButton, saveCourseButton;
	private JLabel screenHeading;
	private JPanel northPane, centrePane, southPane;
	private JList<Course> coursesList;
	private Container cp;
	private Menu menu;
	
	public AdministrationScreen(Menu menu, String inputtedPassword) {
		super("Administation screen");
		this.checkCredentials(inputtedPassword);
		this.menu = menu;
		this.cp = this.getContentPane();
		this.cp.setLayout(new BorderLayout());
		
		// North pane
		this.northPane = new JPanel();
		this.northPane.setLayout(new BoxLayout(this.northPane, BoxLayout.X_AXIS));
		
		this.screenHeading = new JLabel("Administration screen");
		screenHeading.setAlignmentY(Component.TOP_ALIGNMENT);
		this.screenHeading.setFont(this.screenHeading.getFont().deriveFont(30));
		
		this.northPane.add(this.screenHeading);
		
		
		// Centre pane
		this.centrePane = new JPanel();
		
		this.coursesListModel = new DefaultListModel<Course>();	
		this.coursesList = new JList<Course>(this.coursesListModel);
		
		this.deleteSelectedCoursesButton = new JButton("JButton");
		this.deleteSelectedCoursesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				List<Course> selectedCourses = AdministrationScreen.this.coursesList.getSelectedValuesList();
				
				for(Course course : selectedCourses) {
					AdministrationScreen.this.menu.removeCourse(course);
					AdministrationScreen.this.coursesListModel.removeElement(course);
				}
			}
			
		});
		
		this.centrePane.add(this.coursesList);
		this.centrePane.add(this.deleteSelectedCoursesButton);
		
		
		// South pane
		this.southPane = new JPanel();
		
		this.closeButton = new JButton("Close");
		this.closeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						AdministrationScreen.this.dispose();
					}
					
				});
				
			}
			
		});
		
		this.southPane.add(this.closeButton);
		
		
		// Add panes to the content pane
		this.cp.add(this.northPane, BorderLayout.NORTH);
		this.cp.add(this.centrePane, BorderLayout.CENTER);
		this.cp.add(this.southPane, BorderLayout.SOUTH);
		
		
		// Load data into the GUI
		this.loadDataIntoGUI();
		
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		
		this.pack();
	}
	
	private void loadDataIntoGUI() {
		for(Course course : this.menu.getCourses()) {
			this.coursesListModel.addElement(course);
		}
	}

	private void checkCredentials(String password) {
		if(!password.equals(AdministrationScreen.PASSWORD)) {
			throw new InvalidAdministrationPasswordException();
		}
	}
}