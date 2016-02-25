import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class OrderScreen extends JFrame {

	static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");
	private Order order = new Order();
	private Container cp;
	private	JScrollPane dinersScrollPane;
	private JButton tableChoiceButton, addDinerButton, payButton;
	private JPanel northPane, southPane, centrePane, dinersHeadingPane, orderHeadingPane, dinersPane, tableChoicePane;
	private JLabel orderHeadingLabel, tableNumberLabel, dinersHeadingLabel, totalPriceLabel;

	/**
	 * Create the dialog.
	 */
	public OrderScreen() {
		super("Restaurant Menu Selector - Order Screen");
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
				try {
					TableChoiceDialog dialog = new TableChoiceDialog(OrderScreen.this);
					dialog.setLocationRelativeTo(OrderScreen.this);
					dialog.pack();
					dialog.setVisible(true);
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}

		});
		
		this.tableChoicePane = new JPanel();
		this.tableChoicePane.setLayout(new FlowLayout(FlowLayout.LEFT));

		this.tableChoicePane.add(this.tableNumberLabel);
		this.tableChoicePane.add(this.tableChoiceButton);
		
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
				System.out.println("Pressed pay button");
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
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}

	private void insertDataToWindowFromOrderObject() {
		String tableNumber = "Not chosen";

		if (this.order.getTable() != null) {
			tableNumber = String.valueOf(this.order.getTable().getNumber());
		}

		this.tableNumberLabel.setText("Table number: " + tableNumber);
		this.orderHeadingLabel.setText("Order no. " + this.order.getNumber());
		this.totalPriceLabel.setText("Total: £" + DECIMAL_FORMAT.format(this.order.getTotalPrice()));

		// Remove all the diners from the screen
		this.dinersPane.removeAll();
		this.dinersPane.repaint();
		this.dinersPane.revalidate();

		// Generate all the diners again
		if (this.order.getDiners().size() > 0) {
			int i = 1;
			for (Diner diner : this.order.getDiners()) {
				this.dinersPane.add(this.generateDinerRow(diner, i));
				i++;
			}
		} else {
			this.dinersPane.add(new JLabel("No diners defined."));
		}
		
		if(this.order.getTotalPrice() > 0.0) {
			this.payButton.setEnabled(true);
		}
		else
		{
			this.payButton.setEnabled(false);
		}

	}

	private JPanel generateDinerRow(Diner diner, int dinerNumber) {
		// Create a panel for the row
		JPanel dinerRow = new JPanel();
		dinerRow.setLayout(new BoxLayout(dinerRow, BoxLayout.Y_AXIS));
		
		// Create a panel for the heading of the row
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
						try {
							AddCourseToDinerScreen dialog = new AddCourseToDinerScreen(OrderScreen.this, diner);
							dialog.setLocationRelativeTo(OrderScreen.this);
							dialog.setVisible(true);
						} catch (Exception exception) {
							exception.printStackTrace();
						}
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
						OrderScreen.this.order.deleteDiner(diner);
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
	 * Gets an Order instance for the current screen.
	 * 
	 * @return Order instance
	 */
	public Order getOrder() {
		return this.order;
	}

	/**
	 * Refreshes data in the window after they have been altered.
	 */
	public void refreshData() {
		this.insertDataToWindowFromOrderObject();
	}

	public static void main(String[] args) {
		try {
			OrderScreen frame = new OrderScreen();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
