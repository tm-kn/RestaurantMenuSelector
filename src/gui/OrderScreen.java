package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import exceptions.EmptyMenuException;
import exceptions.InvalidNumberOfDinersException;
import models.Course;
import models.Diner;
import models.Menu;
import models.Order;

/**
 * Window responsible for creating a new order. It displays information about order and
 * allows to open payment window or add a new course to the order.
 * 
 * @author TJ Knapik <u1562595@unimail.hud.ac.uk>
 *
 */
public class OrderScreen extends JFrame {

	private static final long serialVersionUID = 1744313213682203695L;
	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");
	private Order order = new Order();
	private Menu menu;
	private Container cp;
	private	JScrollPane dinersScrollPane;
	private JButton tableChoiceButton, addDinerButton, payButton;
	private JPanel northPane, southPane, centrePane, dinersHeadingPane, orderHeadingPane, dinersPane, tableChoicePane;
	private JLabel orderHeadingLabel, tableNumberLabel, tableChoiceWarning, dinersHeadingLabel, totalPriceLabel;

	/**
	 * Create the order screen.
	 * @param menu		Menu instance you want to use with this order
	 */
	public OrderScreen(Menu menu) {
		super("Restaurant Menu Selector - Order Screen");
		this.menu = menu;
		
		// Check if menu is empty and close window if it is
		try {
			this.menu.getCourses();
		} catch(EmptyMenuException e) {
			JOptionPane.showMessageDialog(this,
				    "The menu is empty. Cannot choose a dish.\nAsk staff for assistance.",
				    "The menu is empty",
				    JOptionPane.ERROR_MESSAGE);
			
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					OrderScreen.this.dispose();
				}
				
			});
			
			return;
		}
		
		this.cp = this.getContentPane();
		this.cp.setLayout(new BorderLayout());

		// North pane
		this.northPane = new JPanel();
		this.northPane.setLayout(new BoxLayout(this.northPane, BoxLayout.Y_AXIS));
		
		this.orderHeadingPane = new JPanel();
		this.orderHeadingPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.orderHeadingLabel = new JLabel();
		this.orderHeadingLabel.setFont(this.orderHeadingLabel.getFont().deriveFont((float) 40.0));
		this.orderHeadingPane.add(this.orderHeadingLabel);

		this.tableNumberLabel = new JLabel();
		this.tableChoiceButton = new JButton("Change");
		this.tableChoiceButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				TableChoiceDialog dialog = new TableChoiceDialog(OrderScreen.this);
				dialog.setLocationRelativeTo(OrderScreen.this);
				dialog.setVisible(true);
			}

		});
		
		this.tableChoiceWarning = new JLabel("Please, choose a table to continue with the order.");
		this.tableChoiceWarning.setFont(this.tableChoiceWarning.getFont().deriveFont(Font.BOLD));
		this.tableChoiceWarning.setForeground(Color.RED);
		
		this.tableChoicePane = new JPanel();
		this.tableChoicePane.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.tableChoicePane.add(this.tableNumberLabel);
		this.tableChoicePane.add(this.tableChoiceButton);
		this.tableChoicePane.add(this.tableChoiceWarning);
		
		this.dinersHeadingPane = new JPanel();
		this.dinersHeadingPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.dinersHeadingLabel = new JLabel("Diners");
		this.dinersHeadingLabel.setFont(this.orderHeadingLabel.getFont().deriveFont((float) 30.0));
		
		this.dinersHeadingPane.add(this.dinersHeadingLabel);

		this.northPane.add(this.orderHeadingPane);
		this.northPane.add(this.tableChoicePane);
		this.northPane.add(this.dinersHeadingPane);
		
		// Centre pane
		this.centrePane = new JPanel();
		this.centrePane.setLayout(new BoxLayout(this.centrePane, BoxLayout.Y_AXIS));
		
		this.dinersPane = new JPanel();
		this.dinersPane.setLayout(new BoxLayout(this.dinersPane, BoxLayout.Y_AXIS));
		
		this.dinersScrollPane = new JScrollPane(this.dinersPane);
		
		this.centrePane.add(this.dinersScrollPane);
		
		// South pane
		this.southPane = new JPanel();
		this.southPane.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
		
		this.totalPriceLabel = new JLabel();
		this.totalPriceLabel.setFont(this.totalPriceLabel.getFont().deriveFont((float) 25.0));
		
		this.addDinerButton = new JButton("Add another diner");
		this.addDinerButton.setFont(this.totalPriceLabel.getFont());
		this.addDinerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				OrderScreen.this.order.addDiner(new Diner());
				OrderScreen.this.refreshData();
			}

		});
		
		this.payButton = new JButton("Pay for your order");
		this.payButton.setFont(this.totalPriceLabel.getFont());
		this.payButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PaymentDialog dialog = new PaymentDialog(OrderScreen.this);
				dialog.setLocationRelativeTo(OrderScreen.this);
				dialog.setVisible(true);
			}

		});
		
		this.southPane.add(this.totalPriceLabel);
		this.southPane.add(this.addDinerButton);
		this.southPane.add(this.payButton);
		
		// Content pane
		this.cp.add(this.northPane, BorderLayout.NORTH);
		this.cp.add(this.centrePane, BorderLayout.CENTER);
		this.cp.add(this.southPane, BorderLayout.SOUTH);
		
		this.refreshData();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.pack();
	}

	/**
	 * Get data from the order instance and fill the GUI with it
	 */
	private void insertDataToWindowFromOrderObject() {
		String tableNumber = "Not chosen";

		if (this.order.getTable() != null) {
			tableNumber = String.valueOf(this.order.getTable().getNumber());
			this.tableChoiceWarning.setVisible(false);
		}

		this.tableNumberLabel.setText("Table number: " + tableNumber);
		this.orderHeadingLabel.setText("Order no. " + this.order.getNumber());
		this.totalPriceLabel.setText("Total: £" + DECIMAL_FORMAT.format(this.order.getTotalPrice()));

		// Remove all the diners from the screen
		this.dinersPane.removeAll();
		

		// Generate all the diners again
		if (this.order.getDiners().size() > 0) {
			int i = 1;
			for (Diner diner : this.order.getDiners()) {
				this.dinersPane.add(this.generateDinerRow(diner, i));
				this.dinersPane.add(Box.createRigidArea(new Dimension(0, 50)));
				i++;
			}
		} else {
			this.dinersPane.add(new JLabel("No diners defined."));
		}
		
		this.dinersPane.repaint();
		this.dinersPane.revalidate();
		this.pack();
		
		// Pay button
		if(this.order.isReadyToPay()) {
			this.payButton.setEnabled(true);
		}
		else
		{
			this.payButton.setEnabled(false);
		}

	}

	/**
	 * Generate GUI row displaying information about diner
	 * @param diner			Diner instance
	 * @param dinerNumber	Diner number in the order
	 * @return
	 */
	private JPanel generateDinerRow(Diner diner, int dinerNumber) {
		// Create a panel for the row
		JPanel dinerRow = new JPanel();
		dinerRow.setLayout(new BoxLayout(dinerRow, BoxLayout.Y_AXIS));
		
		// Create a panel for three heading of the row
		JPanel dinerHeading = new JPanel();
		dinerHeading.setLayout(new BoxLayout(dinerHeading, BoxLayout.X_AXIS));

		// Create a heading label for the row
		JPanel dinerHeadingLeftPane = new JPanel();
		dinerHeadingLeftPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel dinerHeadingLabel = new JLabel("Diner no. " + dinerNumber + "(total: £"
				+ DECIMAL_FORMAT.format(diner.getTotalPrice()) + ", " + diner.getTotalKiloCalories() + "kcal)");
		dinerHeadingLabel.setFont(this.orderHeadingLabel.getFont().deriveFont((float) 30.0));
		dinerHeadingLeftPane.add(dinerHeadingLabel);
		
		// Create a button to add a new course
		JButton addCourseButton = new JButton("Add a course for the diner");
		addCourseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						AddCourseToDinerScreen dialog = new AddCourseToDinerScreen(OrderScreen.this, diner);
						dialog.setLocationRelativeTo(OrderScreen.this);
						dialog.setVisible(true);
					}

				});

			}

		});
		dinerHeadingLeftPane.add(addCourseButton);
		
		
		dinerHeading.add(dinerHeadingLeftPane);
		
		// Right pane
		JPanel dinerHeadingRightPane = new JPanel();
		dinerHeadingRightPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		if (this.order.getDiners().size() > 1) {
			
			
			JButton deleteDinerButton = new JButton("Delete a diner");
			deleteDinerButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					int response = JOptionPane.showConfirmDialog(null, "Do you want to delete diner no. " + dinerNumber,
							"Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (response == JOptionPane.YES_OPTION) {
						try {
							OrderScreen.this.order.deleteDiner(diner);
						} catch (InvalidNumberOfDinersException e1) {
							System.err.println("Diner cannot be deleted because there's only one.");
						}
						OrderScreen.this.refreshData();
					}
				}

			});
			dinerHeadingRightPane.add(deleteDinerButton);
		}
		
		dinerHeading.add(dinerHeadingRightPane);
		
		dinerRow.add(dinerHeading);
		
		JPanel coursesPane = new JPanel();
		coursesPane.setLayout(new BoxLayout(coursesPane, BoxLayout.Y_AXIS));
		
		// Go through the courses and add them to our panel
		if (diner.getCourses().size() > 0) {
			for (Course course : diner.getCourses()) {
				coursesPane.add(this.generateDinerCourseRow(course, diner));
			}
		} else {
			JPanel noCoursesMessagePane = new JPanel();
			noCoursesMessagePane.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			noCoursesMessagePane.add(new JLabel("No courses have been added yet."));
			coursesPane.add(noCoursesMessagePane);
		}
		
		dinerRow.add(coursesPane);
		return dinerRow;
	}

	/**
	 * Generate GUI for a course row
	 * @param course	course which you want to be displayed
	 * @param diner		under which diner the course should be displayed
	 * @return
	 */
	private JPanel generateDinerCourseRow(Course course, Diner diner) {
		JPanel courseRow = new JPanel();
		courseRow.setLayout(new BoxLayout(courseRow, BoxLayout.Y_AXIS));
		
		// Heading pane
		JPanel courseRowHeadingPane = new JPanel();
		courseRowHeadingPane.setLayout(new BoxLayout(courseRowHeadingPane, BoxLayout.X_AXIS));
		
		JPanel leftPane = new JPanel();
		leftPane.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 0));
		
		Font headingFont = new Font(Font.SANS_SERIF, Font.PLAIN, 30);
		
		JLabel nameLabel = new JLabel(course.getName());
		nameLabel.setFont(headingFont);
		JLabel priceLabel = new JLabel("£" + DECIMAL_FORMAT.format(course.getPrice()));
		priceLabel.setFont(headingFont);
		JLabel kiloCaloriesLabel = new JLabel(course.getKiloCalories() + "kcal");
		kiloCaloriesLabel.setFont(headingFont);
		
		leftPane.add(nameLabel);
		leftPane.add(priceLabel);
		leftPane.add(kiloCaloriesLabel);
		
		JPanel rightPane = new JPanel();
		rightPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JButton deleteButton = new JButton("Delete a course");
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				diner.deleteCourse(course);
				OrderScreen.this.refreshData();

			}
		});
		
		rightPane.add(deleteButton);
		
		courseRowHeadingPane.add(leftPane);
		courseRowHeadingPane.add(rightPane);
		
		courseRow.add(courseRowHeadingPane);

		return courseRow;
	}

	/**
	 * Get an Order instance for the current screen.
	 * 
	 * @return Order instance
	 */
	public Order getOrder() {
		return this.order;
	}

	/**
	 * Refresh data in the window after they have been altered.
	 */
	public void refreshData() {
		this.insertDataToWindowFromOrderObject();
	}

	/**
	 * Get menu instance used with this order
	 * @return Menu instance
	 */
	public Menu getMenu() {
		return menu;
	}

}
