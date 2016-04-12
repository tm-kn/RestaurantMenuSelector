package gui;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import models.Course;
import models.Diner;
import models.Menu;

/**
 * Window which displays a range of food which can be added to an order.
 * 
 * @author TJ Knapik <u1562595@unimail.hud.ac.uk>
 *
 */
public class AddCourseToDinerScreen extends JDialog {

	private static final long serialVersionUID = -7029260598028241556L;
	final static Font FONT_30 = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
	private JPanel buttonPane, menuPane, filterPane;
	private Container cp;
	private OrderScreen parent;
	private Diner diner;
	private	JScrollPane scrollPane;
	private Menu menu;
	private JCheckBox nutFreeCheckBox, glutenFreeCheckBox, vegetarianCheckBox, veganCheckBox;
	private JButton cancelButton;

	/**
	 * Create the dialog.
	 */
	public AddCourseToDinerScreen(OrderScreen parent, Diner diner) {
		// Make that dialog modal
		super(parent, "Choose a course", true);

		// Get data from the constructor
		this.parent = parent;
		this.diner = diner;
		
		// Some basics
		this.menu = this.parent.getMenu();
		this.cp = this.getContentPane();		
		this.cp.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		// Filter pane
		this.filterPane = new JPanel();
		this.refreshFilterPane();

		// Create menu pane
		this.menuPane = new JPanel();
		this.menuPane.setLayout(new BorderLayout());
		this.refreshMenuPane();
		this.scrollPane = new JScrollPane(this.menuPane);
		
		// Button pane
		this.buttonPane = new JPanel();
		this.buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		this.cancelButton = new JButton("Cancel");
		this.cancelButton.setActionCommand("Cancel");
		this.cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddCourseToDinerScreen.this.dispose();
			}

		});
		this.buttonPane.add(this.cancelButton);
		
		// Add everything to the content pane
		this.cp.add(this.filterPane, BorderLayout.NORTH);
		this.cp.add(this.scrollPane, BorderLayout.CENTER);
		this.cp.add(this.buttonPane, BorderLayout.SOUTH);
		
		this.pack();

	}
	
	/**
	 * Generate GUI for menu pane
	 * @return JPanel menu pane
	 */
	private JPanel getMenuPane() {
		List<Course> coursesList = this.getFilteredCoursesList();
		
		// Sort courses according to their category, e.g. main courses, desserts, fish courses, drinks, etc.
		Map<String, List<Course>> sortedCoursesList = Menu.groupByCourseType(coursesList);
		
		JPanel courseRowsList = new JPanel();
		courseRowsList.setLayout(new BoxLayout(courseRowsList, BoxLayout.Y_AXIS));

		for (Map.Entry<String, List<Course>> entry : sortedCoursesList.entrySet()) {
			String key = entry.getKey();
			List<Course> value = entry.getValue();
			
			JPanel courseTypePane = new JPanel();
			courseTypePane.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
			
			JLabel courseTypeLabel = new JLabel(key);
			courseTypeLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
			
			courseTypePane.add(courseTypeLabel);
			
			courseRowsList.add(courseTypePane);

			for (Course course : value) {
				JPanel courseRow = new JPanel();
				courseRow.setLayout(new BoxLayout(courseRow, BoxLayout.X_AXIS));
				
				// Left side
				JPanel courseLeftRow = new JPanel();
				courseLeftRow.setLayout(new GridLayout(2, 1));
				
				// Name, price and kilocalories
				JPanel courseLeftRowHeadingPane = new JPanel();
				courseLeftRowHeadingPane.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
				
				JLabel nameLabel = new JLabel(course.getName());
				nameLabel.setFont(FONT_30);
				
				JLabel priceLabel = new JLabel("£" + OrderScreen.DECIMAL_FORMAT.format(course.getPrice()));
				priceLabel.setFont(FONT_30);
				
				JLabel kiloCaloriesLabel = new JLabel(course.getKiloCalories() + "kcal");
				kiloCaloriesLabel.setFont(FONT_30);
				
				courseLeftRowHeadingPane.add(nameLabel);
				courseLeftRowHeadingPane.add(priceLabel);
				courseLeftRowHeadingPane.add(kiloCaloriesLabel);
				
				// Description
				JTextArea descriptionText = new JTextArea(course.getDescription());
				descriptionText.setLineWrap(true);
				descriptionText.setWrapStyleWord(true);
				descriptionText.setOpaque(false);
				descriptionText.setEditable(false);
				descriptionText.setMargin(new Insets(0, 40, 0, 0));
				
				if(course.getNutFree() || course.getVegan() || course.getVegetarian() || course.getGlutenFree()) {
					descriptionText.append("\n");
					descriptionText.append("\n");
					
					if(course.getNutFree()) {
						descriptionText.append(" nut-free");
					}
					
					if(course.getVegan()) {
						descriptionText.append(" vegan");
					}
					
					if(course.getVegetarian()) {
						descriptionText.append(" vegetarian");
					}
					
					if(course.getGlutenFree()) {
						descriptionText.append(" gluten-free");
					}
				}
				
				courseLeftRow.add(courseLeftRowHeadingPane);
				courseLeftRow.add(descriptionText);
				
				// Right side
				JPanel courseRightRow = new JPanel();
				courseRightRow.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 0));
				
				JButton courseButton = new JButton("Add to the order");
				courseButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						AddCourseToDinerScreen.this.diner.addCourse(course);
						AddCourseToDinerScreen.this.parent.refreshData();
						AddCourseToDinerScreen.this.dispose();
					}
				});
				
				courseRightRow.add(courseButton);

				courseRow.add(courseLeftRow);
				courseRow.add(courseRightRow);
				courseRowsList.add(courseRow);
				courseRowsList.add(Box.createRigidArea(new Dimension(0, 50)));
			}

		}

		this.menuPane.add(courseRowsList, BorderLayout.CENTER);

		return this.menuPane;
	}

	/**
	 * Filters course list according to the criteria specified by user
	 * @return List<Course>
	 */
	private List<Course> getFilteredCoursesList() {
		return this.menu.filterCoursesList(this.nutFreeCheckBox.isSelected(), this.veganCheckBox.isSelected(),
				this.vegetarianCheckBox.isSelected(), this.glutenFreeCheckBox.isSelected());
	}
	
	/**
	 * Return GUI for checkboxes used to filter menu
	 * @return
	 */
	private JPanel getFilterPane() {
		this.filterPane = new JPanel();

		ActionListener defaultFilterCheckBoxActionListener = (new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddCourseToDinerScreen.this.refreshMenuPane();
			}

		});

		this.nutFreeCheckBox = new JCheckBox("Nut free");
		this.nutFreeCheckBox.addActionListener(defaultFilterCheckBoxActionListener);

		this.glutenFreeCheckBox = new JCheckBox("Gluten free");
		this.glutenFreeCheckBox.addActionListener(defaultFilterCheckBoxActionListener);

		this.veganCheckBox = new JCheckBox("Vegan");
		this.veganCheckBox.addActionListener(defaultFilterCheckBoxActionListener);

		this.vegetarianCheckBox = new JCheckBox("Vegetarian");
		this.vegetarianCheckBox.addActionListener(defaultFilterCheckBoxActionListener);

		this.filterPane.add(this.nutFreeCheckBox);
		this.filterPane.add(this.glutenFreeCheckBox);
		this.filterPane.add(veganCheckBox);
		this.filterPane.add(vegetarianCheckBox);
		return this.filterPane;
	}

	/**
	 * Refresh filter pane
	 */
	public void refreshFilterPane() {
		this.filterPane.removeAll();
		this.filterPane.revalidate();
		this.getFilterPane();
	}
	
	/**
	 * Reflect changes in the course list in the GUI
	 */
	public void refreshMenuPane() {
		this.menuPane.removeAll();
		this.menuPane.revalidate();
		this.getMenuPane();
		this.revalidate();
		this.pack();
		
		// Make the scroll pane to start at the top
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				AddCourseToDinerScreen.this.scrollPane.getVerticalScrollBar().setValue(0);
			}
		});
	}

}