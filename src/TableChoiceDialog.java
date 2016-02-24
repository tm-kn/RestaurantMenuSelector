import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class TableChoiceDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private TableList tableList = TableList.getInstance();
	private JComboBox tableChoiceComboBox;
	private OrderScreen parent;

	/**
	 * Create the dialog.
	 */
	public TableChoiceDialog(OrderScreen parent) {
		super(parent, "Choose a table", true);
		this.parent = parent;
		
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel choicePane = new JPanel();
			
			choicePane.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			getContentPane().add(choicePane, BorderLayout.CENTER);
			{ 
				tableChoiceComboBox = new JComboBox(this.tableList.getTables().toArray());
				Table selectedTable = TableChoiceDialog.this.parent.getOrder().getTable();
				
				if(selectedTable != null) {
					tableChoiceComboBox.setSelectedItem(selectedTable);
				}
				
				choicePane.add(new JLabel("Choose a table you're sat at."));
				choicePane.add(tableChoiceComboBox);	
			}
			
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						Table selectedTable = (Table) TableChoiceDialog.this.tableChoiceComboBox.getSelectedItem();
						TableChoiceDialog.this.parent.getOrder().setTable(selectedTable);
						TableChoiceDialog.this.parent.refreshData();
						TableChoiceDialog.this.dispose();
					}
					
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent event) {
						TableChoiceDialog.this.dispose();
					}
					
				});
				buttonPane.add(cancelButton);
			}
		}
	}

}
