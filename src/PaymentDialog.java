import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class PaymentDialog extends JDialog {

	private Double totalAmount, changeAmount, amountPaid;
	private Container cp;
	private OrderScreen parent;
	private Order order;
	private ButtonGroup paymentMethodRadioGroup;
	private JRadioButton cardPaymentRadioButton, cashPaymentRadioButton;
	private JPanel paymentMethodPane, centrePane, paymentFormPane;
	
	public PaymentDialog(OrderScreen parent) {
		// Make the dialog modal
		super(parent, true);
		
		// Set the default layout
		this.cp = this.getContentPane();
		this.cp.setLayout(new BorderLayout());
		
		// Set the attributes
		this.parent = parent;
		this.order = parent.getOrder();
		this.totalAmount = this.order.getTotalPrice();
		this.changeAmount = 0.0;
		this.amountPaid = 0.0;
		
		// Centre pane
		this.centrePane = new JPanel();
		this.centrePane.setLayout(new BoxLayout(this.centrePane, BoxLayout.Y_AXIS));
		
		// Choose payment method
		this.paymentMethodPane = new JPanel();
		this.paymentMethodPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.paymentMethodRadioGroup = new ButtonGroup();
		
		this.cardPaymentRadioButton = new JRadioButton("Card");
		this.cardPaymentRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PaymentDialog.this.refreshPaymentFormPane();
			}
			
		});
		
		this.cashPaymentRadioButton = new JRadioButton("Cash");
		this.cashPaymentRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PaymentDialog.this.refreshPaymentFormPane();
			}
			
		});
		
		this.paymentMethodRadioGroup.add(this.cardPaymentRadioButton);
		this.paymentMethodRadioGroup.add(this.cashPaymentRadioButton);
		
		this.paymentMethodPane.add(this.cashPaymentRadioButton);
		this.paymentMethodPane.add(this.cardPaymentRadioButton);
		
		
		// Payment form pane
		this.paymentFormPane = new JPanel();
		this.paymentFormPane.setLayout(new BoxLayout(this.paymentFormPane, BoxLayout.Y_AXIS));
		
		// Add things to the centre pane
		this.centrePane.add(this.paymentMethodPane);
		
		// Add things to the main pane
		this.cp.add(this.centrePane, BorderLayout.CENTER);
		
		// Set some basics for JDialog
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("Pay for your order");
		
		this.pack();
	}
	
	private void refreshPaymentFormPane() {
		if(this.cashPaymentRadioButton.isSelected()) {
			this.paymentFormPane.add(this.getCashPaymentForm());
			
		} else if(this.cardPaymentRadioButton.isSelected()) {
			this.paymentFormPane.add(this.getCardPaymentForm());
		} else {
			this.paymentFormPane.removeAll();
			this.paymentFormPane.revalidate();
		}
	}
	
	private JPanel getCashPaymentForm() {
		JPanel form = new JPanel();
		form.setLayout(new BorderLayout());
		
		JTextField inputAmount = new JTextField("Amount");
		
		JButton payButton = new JButton("Pay");
		payButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PaymentDialog.this.amountPaid = Double.valueOf(inputAmount.getText());
							
				if(PaymentDialog.this.amountPaid < PaymentDialog.this.totalAmount) {
					PaymentDialog.this.addPaymentError("You gave less than you have to pay");
					return;
				}
				
				PaymentDialog.this.changeAmount = PaymentDialog.this.totalAmount - PaymentDialog.this.amountPaid;
				
			}
			
		});
		
		form.add(inputAmount);
		
		return form;
	}
	
	private JPanel getCardPaymentForm() {
		JPanel form = new JPanel();
		form.setLayout(new BorderLayout());
		
		return form;
	}
	
	private void addPaymentError(String error) {
		return;
	}
}
