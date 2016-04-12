package gui;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import exceptions.InvalidAmountPaidException;
import exceptions.InvalidNumberOfCoursesOrderedException;
import exceptions.InvalidNumberOfDinersException;
import exceptions.InvalidOrderStatusException;
import exceptions.TableHasNotBeenChosenException;
import models.Order;

/**
 * Window which is used to simulate payment for order.
 * 
 * @author TJ Knapik<u1562595@unimail.hud.ac.uk>
 *
 */
public class PaymentDialog extends JDialog {

	private static final long serialVersionUID = 5522846541550447289L;
	private Container cp;
	private OrderScreen parent;
	private Order order;
	private ButtonGroup paymentMethodRadioGroup;
	private JRadioButton cashPaymentRadioButton;
	private JPanel paymentMethodPane, centrePane, paymentFormPane;
	private JLabel errorMessageLabel;
	
	/**
	 * Construct a payment window
	 * @param parent	OrderScreen instance
	 */
	public PaymentDialog(OrderScreen parent) {
		// Make the dialog modal
		super(parent, true);
		
		// Set the default layout
		this.cp = this.getContentPane();
		this.cp.setLayout(new BorderLayout());
		
		// Set the attributes
		this.parent = parent;
		this.order = parent.getOrder();
		
		// Centre pane
		this.centrePane = new JPanel();
		this.centrePane.setLayout(new BoxLayout(this.centrePane, BoxLayout.Y_AXIS));
		
		// Choose payment method
		this.paymentMethodPane = new JPanel();
		this.paymentMethodPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.paymentMethodRadioGroup = new ButtonGroup();
		
		this.cashPaymentRadioButton = new JRadioButton("Cash");
		this.cashPaymentRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PaymentDialog.this.refreshPaymentFormPane();
			}
			
		});
		
		this.paymentMethodRadioGroup.add(this.cashPaymentRadioButton);
		
		this.paymentMethodPane.add(new JLabel("Choose a payment method: "));
		this.paymentMethodPane.add(this.cashPaymentRadioButton);
		
		// Set up error message label
		this.errorMessageLabel = new JLabel();
		
		// Payment form pane
		this.paymentFormPane = new JPanel();
		this.paymentFormPane.setLayout(new BoxLayout(this.paymentFormPane, BoxLayout.Y_AXIS));
		
		
		// Add things to the centre pane
		this.centrePane.add(this.paymentMethodPane);
		this.centrePane.add(this.errorMessageLabel);
		this.centrePane.add(this.paymentFormPane);
		
		// Add things to the main pane
		this.cp.add(this.centrePane, BorderLayout.CENTER);
		
		// Set some basics for JDialog
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("Pay for your order");
		
		// Set size
		this.setSize(500, 200);
	}
	
	/**
	 * Refresh elements on payment form GUI
	 */
	private void refreshPaymentFormPane() {
		this.paymentFormPane.removeAll();
		
		if(this.cashPaymentRadioButton.isSelected()) {
			this.paymentFormPane.add(this.getCashPaymentForm());
		}
		
		this.paymentFormPane.revalidate();
		this.paymentFormPane.repaint();
	}
	
	/**
	 * Get a GUI form for cash payments
	 * @return
	 */
	private JPanel getCashPaymentForm() {
		JPanel form = new JPanel();
		form.setLayout(new BoxLayout(form, BoxLayout.X_AXIS));
		
		// Text field where user types how much they have paid in cash
		JTextField inputAmount = new JTextField();
		inputAmount.setSize(new Dimension(inputAmount.getWidth(), inputAmount.getHeight()));
		
		// Pay button
		JButton payButton = new JButton("Pay");
		payButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				double amountPaid = 0.0;
				
				// Get an amount inserted by customer from the text box
				try {
					 amountPaid = Double.valueOf(inputAmount.getText());
				} catch(NumberFormatException e1) {
					PaymentDialog.this.addPaymentError("Make sure the amount is in correct format.");
					return;
				}
				
				// Try to pay for the order and close the current order window
				try {
						PaymentDialog.this.order.pay(amountPaid);
						PaymentDialog.this.openNewOrderWindow();
				} catch (TableHasNotBeenChosenException | InvalidNumberOfDinersException | InvalidNumberOfCoursesOrderedException | InvalidOrderStatusException e1) {
					// It's unacceptable state of the program, so let's throw runtime exception which should crash the program
					e1.printStackTrace();
					throw new RuntimeException(
								"Things went totaly south - payment dialog has been allowed before one of \n" +
								"following has been checked - table has not been chosen, invalid number of \n" +
								"diners or invalid number of courses or order status is invalid.");
				} catch (InvalidAmountPaidException e1) {
					PaymentDialog.this.addPaymentError("You have not paid enough cash.");
				}
			}
		});
		
		form.add(inputAmount);
		form.add(payButton);
		
		return form;
	}
	
	/**
	 * Add a payment error to the screen
	 * @param error
	 */
	private void addPaymentError(String error) {
		this.errorMessageLabel.setText(error);
	}
	
	/**
	 * Open a brand-new order window ready for a new order
	 */
	private void openNewOrderWindow() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// Dispose order window
				PaymentDialog.this.parent.dispose();
				
				// Create a new order screen ready for a new order
				OrderScreen frame = new OrderScreen(PaymentDialog.this.parent.getMenu());
				frame.setVisible(true);
			}
			
		});
	}
}
