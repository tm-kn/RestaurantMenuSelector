package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 * JDialog displaying a receipt to a customer.
 * 
 * @author TJ Knapik <u1562595@unimail.hud.ac.uk>
 *
 */
public class ReceiptDialog extends JDialog {

	private static final long serialVersionUID = 2044296553041850972L;
	private String receiptContent;
	private Container cp;
	private JTextArea receiptTextArea;
	private JButton closeButton;
	
	/**
	 * Construct the frame
	 * @param parent			parent frame, in this case PaymentDialog instance
	 * @param receiptContent	content of order receipt to be displayed in the dialog window
	 */
	public ReceiptDialog (PaymentDialog parent, String receiptContent) {
		super(parent, true);
		this.setTitle("Your receipt");
		
		this.cp = this.getContentPane();
		this.cp.setLayout(new BorderLayout());
		
		this.receiptContent = receiptContent;
		
		this.receiptTextArea = new JTextArea(this.receiptContent);
		this.receiptTextArea.setEnabled(false);
		
		this.closeButton = new JButton("Close receipt and start a new order");
		this.closeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ReceiptDialog.this.dispose();
			}
			
		});
		
		this.cp.add(new JLabel("Thank you for your custom"), BorderLayout.NORTH);
		this.cp.add(this.receiptTextArea, BorderLayout.CENTER);
		this.cp.add(this.closeButton, BorderLayout.SOUTH);
		
		this.pack();
		this.setResizable(false);
		
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
}
