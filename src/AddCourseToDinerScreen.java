import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AddCourseToDinerScreen extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private OrderScreen parent;
	private Diner diner;
	private Menu menu = Menu.getInstance();
	private JCheckBox nutFreeCheckBox, glutenFreeCheckBox, vegetarianCheckBox, veganCheckBox;
	private JPanel menuPane;

	/**
	 * Create the dialog.
	 */
	public AddCourseToDinerScreen(OrderScreen parent, Diner diner) {
		// Make that dialog modal
		super(parent, "Choose a course", true);
		
		// Get data from the constructor
		this.parent = parent;
		this.diner = diner;
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
				
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		getContentPane().add(this.getFilterPane(), BorderLayout.NORTH);
		
		this.menuPane = new JPanel();
		this.menuPane.setLayout(new BorderLayout());
		this.refreshMenuPane();
		
		getContentPane().add(this.menuPane, BorderLayout.EAST);
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{			
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						AddCourseToDinerScreen.this.dispose();
					}
					
				});
				buttonPane.add(cancelButton);
			}
		}
	}
	
	private JPanel getMenuPane() {
		List<Course> coursesList = this.getFilteredCoursesList();
		
		Map<Class<? extends Course>, List<Course>> sortedCoursesList = Menu.groupByCourseType(coursesList);
		
		JPanel courseRowsList = new JPanel();
		courseRowsList.setLayout(new BoxLayout(courseRowsList, BoxLayout.Y_AXIS));
		
		for (Map.Entry<Class<? extends Course>, List<Course>> entry : sortedCoursesList.entrySet()) {
		    Class<? extends Course> key = entry.getKey();
		    List<Course> value = entry.getValue();
		    
		    courseRowsList.add(new JLabel(Course.getCourseTypeNameOfClassCourseType(key)));
		    
		    for(Course course : value) {
		    	JPanel courseRow = new JPanel();
		    	courseRow.setLayout(new BoxLayout(courseRow, BoxLayout.X_AXIS));
		    			
		    	System.out.println(course.getName());
		    	JButton courseButton = new JButton(course.getName());
		    	courseButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						AddCourseToDinerScreen.this.diner.addCourse(course);
						AddCourseToDinerScreen.this.parent.refreshData();
						AddCourseToDinerScreen.this.dispose();
					}
				});
		    	
		    	courseRow.add(courseButton);
		    	courseRowsList.add(courseRow);
		    }
		    
		}
		
		this.menuPane.add(courseRowsList, BorderLayout.CENTER);
		
		return this.menuPane;
	}
	
	private List<Course> getFilteredCoursesList() {
		return this.menu.filterCoursesList(
				this.nutFreeCheckBox.isSelected(),
				this.veganCheckBox.isSelected(),
				this.vegetarianCheckBox.isSelected(),
				this.glutenFreeCheckBox.isSelected()
		);
	}
	
	private JPanel getFilterPane() {
		JPanel filterPane = new JPanel();
		
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
		
		
		filterPane.add(this.nutFreeCheckBox);
		filterPane.add(this.glutenFreeCheckBox);
		filterPane.add(veganCheckBox);
		filterPane.add(vegetarianCheckBox);
		return filterPane;
	}
	
	public void refreshMenuPane() {
		this.menuPane.removeAll();
		this.menuPane.revalidate();
		this.getMenuPane();
	}

}