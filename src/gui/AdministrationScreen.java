package gui;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import exceptions.EmptyMenuException;
import exceptions.InvalidAdministrationPasswordException;
import exceptions.InvalidCourseTypeException;
import models.Course;
import models.Menu;

import java.awt.Component;

/**
 * Screen used to administer content of the menu.
 * 
 * @author TJ Knapik <u1562595@unimail.hud.ac.uk>
 *
 */
public class AdministrationScreen extends JFrame {

	private static final long serialVersionUID = -3373853423168231406L;
	public final static String PASSWORD = "admin123";
	private DefaultListModel<Course> coursesListModel;
	private JButton closeButton, deleteSelectedCoursesButton, saveCourseButton, addNewCourseButton;
	private JLabel screenHeading;
	private JTextField courseNameTextField, coursePriceTextField, courseCaloriesTextField;
	private JTextArea courseDescriptionTextArea;
	private JCheckBox courseNutFreeCheckBox, courseVeganCheckBox, courseVegetarianCheckBox, courseGlutenFreeCheckBox;
	private JPanel northPane, centrePane, southPane, courseEditPane, courseNamePane, coursePricePane,
					courseCaloriesPane, courseDescriptionPane, courseTypePane;
	private JList<Course> coursesList;
	private Container cp;
	private Menu menu;
	private JComboBox<String> courseTypeComboBox;
	
	/**
	 * Constructor for AdministrationScreen.
	 * @param menu		Menu instance to be edited
	 * @param password	Password inputed to access AdministrationScreen
	 * @throws InvalidAdministrationPasswordException 
	 */
	public AdministrationScreen(Menu menu, String inputtedPassword) throws InvalidAdministrationPasswordException {
		super("Administation screen");
		
		// Check if credentials are right before creating the instance of AdministrationScreen.
		// It may throw an unchecked exception.
		this.checkCredentials(inputtedPassword);
		
		// Get the menu instance with information about all the courses
		this.menu = menu;
		
		// Get the content pane as a class property
		this.cp = this.getContentPane();
		this.cp.setLayout(new BorderLayout());
		
		// North pane
		this.northPane = new JPanel();
		this.northPane.setLayout(new BoxLayout(this.northPane, BoxLayout.X_AXIS));
		
		// Screen heading
		this.screenHeading = new JLabel("Administration screen");
		screenHeading.setAlignmentY(Component.TOP_ALIGNMENT);
		this.screenHeading.setFont(this.screenHeading.getFont().deriveFont(30));
		
		// Add components to the north pane
		this.northPane.add(this.screenHeading);
		
		
		// Centre pane
		this.centrePane = new JPanel();
		
		this.coursesListModel = new DefaultListModel<Course>();	
		this.coursesList = new JList<Course>(this.coursesListModel);
		
		this.coursesList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				Course selectedCourse = (Course) AdministrationScreen.this.coursesList.getSelectedValue();
				
				if(selectedCourse != null) {
					// If course has been selected, fill the fields with data and enable them
					AdministrationScreen.this.courseNameTextField.setEnabled(true);
					AdministrationScreen.this.courseNameTextField.setText(selectedCourse.getName());
					
					AdministrationScreen.this.courseTypeComboBox.setEnabled(true);
					AdministrationScreen.this.courseTypeComboBox.setSelectedItem(selectedCourse.getCourseType());
					
					AdministrationScreen.this.courseCaloriesTextField.setEnabled(true);
					AdministrationScreen.this.courseCaloriesTextField.setText(String.valueOf(selectedCourse.getCalories()));
					
					AdministrationScreen.this.courseDescriptionTextArea.setEnabled(true);
					AdministrationScreen.this.courseDescriptionTextArea.setText(selectedCourse.getDescription());
					
					AdministrationScreen.this.coursePriceTextField.setEnabled(true);
					AdministrationScreen.this.coursePriceTextField.setText(String.valueOf(selectedCourse.getPrice()));
					
					AdministrationScreen.this.courseGlutenFreeCheckBox.setEnabled(true);
					AdministrationScreen.this.courseGlutenFreeCheckBox.setSelected(selectedCourse.getGlutenFree());
					
					AdministrationScreen.this.courseNutFreeCheckBox.setEnabled(true);
					AdministrationScreen.this.courseNutFreeCheckBox.setSelected(selectedCourse.getNutFree());
					
					AdministrationScreen.this.courseVeganCheckBox.setEnabled(true);
					AdministrationScreen.this.courseVeganCheckBox.setSelected(selectedCourse.getVegan());
					
					AdministrationScreen.this.courseVegetarianCheckBox.setEnabled(true);
					AdministrationScreen.this.courseVegetarianCheckBox.setSelected(selectedCourse.getVegetarian());
					
					AdministrationScreen.this.saveCourseButton.setEnabled(true);
					AdministrationScreen.this.deleteSelectedCoursesButton.setEnabled(true);
				} else {
					// No course has been selected, so switch off all of the fields and clear them
					AdministrationScreen.this.courseNameTextField.setEnabled(false);
					AdministrationScreen.this.courseNameTextField.setText("");
					
					AdministrationScreen.this.courseTypeComboBox.setEnabled(false);
					AdministrationScreen.this.courseTypeComboBox.setSelectedItem("");
					
					AdministrationScreen.this.courseCaloriesTextField.setEnabled(false);
					AdministrationScreen.this.courseCaloriesTextField.setText("");
					
					AdministrationScreen.this.courseDescriptionTextArea.setEnabled(false);
					AdministrationScreen.this.courseDescriptionTextArea.setText("");
					
					AdministrationScreen.this.coursePriceTextField.setEnabled(false);
					AdministrationScreen.this.coursePriceTextField.setText("");
					
					AdministrationScreen.this.courseGlutenFreeCheckBox.setEnabled(false);
					AdministrationScreen.this.courseGlutenFreeCheckBox.setSelected(false);
					
					AdministrationScreen.this.courseNutFreeCheckBox.setEnabled(false);
					AdministrationScreen.this.courseNutFreeCheckBox.setSelected(false);
					
					AdministrationScreen.this.courseVeganCheckBox.setEnabled(false);
					AdministrationScreen.this.courseVeganCheckBox.setSelected(false);
					
					AdministrationScreen.this.courseVegetarianCheckBox.setEnabled(false);
					AdministrationScreen.this.courseVegetarianCheckBox.setSelected(false);
					
					AdministrationScreen.this.saveCourseButton.setEnabled(false);
					AdministrationScreen.this.deleteSelectedCoursesButton.setEnabled(false);
				}
				
				// Resize window to fit all of the elements
				AdministrationScreen.this.pack();
			}
			
		});
		
		// Add a new course button
		this.addNewCourseButton = new JButton("Add a new course");
		this.addNewCourseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Course newCourse = new Course(Course.MAIN, "New course name", 0.0, 0, null, false, false, false, false);
				AdministrationScreen.this.coursesListModel.addElement(newCourse);
				AdministrationScreen.this.coursesList.setSelectedValue(newCourse, true);
			}
			
		});
		
		// Delete courses button
		this.deleteSelectedCoursesButton = new JButton("Delete selected courses");
		this.deleteSelectedCoursesButton.setEnabled(false);
		this.deleteSelectedCoursesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				List<Course> selectedCourses = AdministrationScreen.this.coursesList.getSelectedValuesList();
				
				// Delete every selected course from menu and the JList
				for(Course course : selectedCourses) {
					AdministrationScreen.this.menu.removeCourse(course);
					AdministrationScreen.this.coursesListModel.removeElement(course);
				}
			}
			
		});
		
		// Course edit pane (containing all the text fields and checkboxes)
		this.courseEditPane = new JPanel();
		this.courseEditPane.setLayout(new BoxLayout(this.courseEditPane, BoxLayout.Y_AXIS));
		
		// Course name
		this.courseNamePane = new JPanel();
		this.courseNamePane.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.courseNameTextField = new JTextField();
		this.courseNameTextField.setEnabled(false);
		this.courseNameTextField.setColumns(20);
		
		this.courseNamePane.add(new JLabel("Name"));
		this.courseNamePane.add(this.courseNameTextField);
		
		// Course type
		this.courseTypePane = new JPanel();
		this.courseTypePane.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.courseTypeComboBox = new JComboBox<String>(Course.COURSE_TYPES);
		this.courseTypeComboBox.setEnabled(false);
		
		this.courseTypePane.add(new JLabel("Type"));
		this.courseTypePane.add(this.courseTypeComboBox);
		
		
		// Calories
		this.courseCaloriesPane = new JPanel();
		this.courseCaloriesPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.courseCaloriesTextField = new JTextField();
		this.courseCaloriesTextField.setEnabled(false);
		this.courseCaloriesTextField.setColumns(20);
		
		this.courseCaloriesPane.add(new JLabel("Calories"));
		this.courseCaloriesPane.add(this.courseCaloriesTextField);
		this.courseCaloriesPane.add(new JLabel("cal (!! NOT KCAL !!)"));
		
		// Course description
		this.courseDescriptionPane = new JPanel();
		this.courseDescriptionPane.setLayout(new BoxLayout(this.courseDescriptionPane, BoxLayout.Y_AXIS));
		
		this.courseDescriptionTextArea = new JTextArea();
		this.courseDescriptionTextArea.setColumns(5);
		this.courseDescriptionTextArea.setLineWrap(true);
		this.courseDescriptionTextArea.setRows(5);
		this.courseDescriptionTextArea.setEnabled(false);
		
		this.courseDescriptionPane.add(new JLabel("Description"));
		this.courseDescriptionPane.add(this.courseDescriptionTextArea);
		
		// Price
		this.coursePricePane = new JPanel();
		this.coursePricePane.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.coursePriceTextField = new JTextField();
		this.coursePriceTextField.setEnabled(false);
		this.coursePriceTextField.setColumns(20);
		
		this.coursePricePane.add(new JLabel("Price £"));
		this.coursePricePane.add(this.coursePriceTextField);
		
		// Gluten-free checkbox
		this.courseGlutenFreeCheckBox = new JCheckBox("Gluten free");
		this.courseGlutenFreeCheckBox.setEnabled(false);
		
		// Nut-free checkbox
		this.courseNutFreeCheckBox = new JCheckBox("Nut free");
		this.courseNutFreeCheckBox.setEnabled(false);
		
		// Vegan checkbox
		this.courseVeganCheckBox = new JCheckBox("Vegan");
		this.courseVeganCheckBox.setEnabled(false);
		
		// Vegetarian checkbox
		this.courseVegetarianCheckBox = new JCheckBox("Vegetarian");
		this.courseVegetarianCheckBox.setEnabled(false);
		
		// Save course button
		this.saveCourseButton = new JButton("Save changes");
		this.saveCourseButton.setEnabled(false);
		this.saveCourseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Get selected course
				Course selectedCourse = (Course) AdministrationScreen.this.coursesList.getSelectedValue();
				
				// Check if any selected course is selected
				if(selectedCourse == null) {
					return;
				}
				
				// Set attributes of selected course to the ones provided by user
				try {
					selectedCourse.setName(AdministrationScreen.this.courseNameTextField.getText());
				} catch(IllegalArgumentException e1) {
					JOptionPane.showMessageDialog(AdministrationScreen.this, e1.getMessage());
				}
				
				try {
					selectedCourse.setCalories(Integer.valueOf(AdministrationScreen.this.courseCaloriesTextField.getText()));
				} catch(NumberFormatException e1) {
					JOptionPane.showMessageDialog(AdministrationScreen.this, "Calories amount is not in the right format.");
				} catch(IllegalArgumentException e1) {
					JOptionPane.showMessageDialog(AdministrationScreen.this, e1.getMessage());
				}
				
				try {
				selectedCourse.setPrice(Double.valueOf(AdministrationScreen.this.coursePriceTextField.getText()));
				} catch(NumberFormatException e1) {
					JOptionPane.showMessageDialog(AdministrationScreen.this, "Price is not in the right format.");
				} catch(IllegalArgumentException e1) {
					JOptionPane.showMessageDialog(AdministrationScreen.this, e1.getMessage());
				}
				
				try {
					selectedCourse.setCourseType((String) AdministrationScreen.this.courseTypeComboBox.getSelectedItem());
				} catch(InvalidCourseTypeException e1) {
					JOptionPane.showMessageDialog(AdministrationScreen.this, "Chosen course type is not correct");
				}
				
				try {
					selectedCourse.setDescription(AdministrationScreen.this.courseDescriptionTextArea.getText());
				} catch(IllegalArgumentException e1) {
					JOptionPane.showMessageDialog(AdministrationScreen.this, e1.getMessage());
				}
				
				selectedCourse.setGlutenFree(AdministrationScreen.this.courseGlutenFreeCheckBox.isSelected());
				selectedCourse.setNutFree(AdministrationScreen.this.courseNutFreeCheckBox.isSelected());
				selectedCourse.setVegan(AdministrationScreen.this.courseVeganCheckBox.isSelected());
				selectedCourse.setVegetarian(AdministrationScreen.this.courseVegetarianCheckBox.isSelected());
				
				// If it's a new course, it needs to be added to menu instance too
				try {
					if(!AdministrationScreen.this.menu.getCourses().contains(selectedCourse)) {
						AdministrationScreen.this.menu.addCourse(selectedCourse);
					}
				} catch(EmptyMenuException e1) {
					AdministrationScreen.this.menu.addCourse(selectedCourse);
				}
				
				// Select it again in order to refresh values
				AdministrationScreen.this.coursesList.clearSelection();
				AdministrationScreen.this.coursesList.setSelectedValue(selectedCourse, true);
			}
			
		});
		
		// Add things to the edit pane
		this.courseEditPane.add(this.courseNamePane);
		this.courseEditPane.add(this.coursePricePane);
		this.courseEditPane.add(this.courseTypePane);
		this.courseEditPane.add(this.courseCaloriesPane);
		this.courseEditPane.add(this.courseDescriptionPane);
		this.courseEditPane.add(this.courseGlutenFreeCheckBox);
		this.courseEditPane.add(this.courseNutFreeCheckBox);
		this.courseEditPane.add(this.courseVeganCheckBox);
		this.courseEditPane.add(this.courseVegetarianCheckBox);
		this.courseEditPane.add(this.saveCourseButton);
		
		// Add components to the centre pane
		this.centrePane.add(this.coursesList);
		this.centrePane.add(this.deleteSelectedCoursesButton);
		this.centrePane.add(this.addNewCourseButton);
		this.centrePane.add(this.courseEditPane);
		
		
		// South pane
		this.southPane = new JPanel();
		
		// Close button
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
		
		// Add components to the south pane
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
		try {
			for(Course course : this.menu.getCourses()) {
				this.coursesListModel.addElement(course);
			}
		} catch(EmptyMenuException e) {
			// Ignore this exception as list can be empty
		}
	}

	private void checkCredentials(String password) throws InvalidAdministrationPasswordException {
		if(!password.equals(AdministrationScreen.PASSWORD)) {
			throw new InvalidAdministrationPasswordException();
		}
	}
}