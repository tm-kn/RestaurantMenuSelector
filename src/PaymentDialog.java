import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class PaymentDialog extends JDialog {

	private static final long serialVersionUID = 5522846541550447289L;
	private Double totalAmount, changeAmount, amountPaid;
	private Container cp;
	private OrderScreen parent;
	private Order order;
	private ButtonGroup paymentMethodRadioGroup;
	private JRadioButton cardPaymentRadioButton, cashPaymentRadioButton;
	private JPanel paymentMethodPane, centrePane, paymentFormPane;
	private JLabel errorMessageLabel;
	
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
		
		this.pack();
	}
	
	private void refreshPaymentFormPane() {
		this.paymentFormPane.removeAll();
		
		if(this.cashPaymentRadioButton.isSelected()) {
			this.paymentFormPane.add(this.getCashPaymentForm());
			
		} else if(this.cardPaymentRadioButton.isSelected()) {
			this.paymentFormPane.add(this.getCardPaymentForm());
		} else {
			this.paymentFormPane.removeAll();
			this.paymentFormPane.revalidate();
		}
		
		this.paymentFormPane.revalidate();
		this.paymentFormPane.repaint();
		this.pack();
	}
	
	private JPanel getCashPaymentForm() {
		JPanel form = new JPanel();
		form.setLayout(new FlowLayout());
		
		JTextField inputAmount = new JTextField("Amount");
		
		JButton payButton = new JButton("Pay");
		payButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PaymentDialog.this.amountPaid = Double.valueOf(inputAmount.getText());
							
				if(PaymentDialog.this.amountPaid < PaymentDialog.this.totalAmount) {
					PaymentDialog.this.addPaymentError("You gave less than you have to pay. Please pay at least £" + PaymentDialog.this.totalAmount + ".");
					return;
				}
				
				
				PaymentDialog.this.changeAmount = PaymentDialog.this.amountPaid - PaymentDialog.this.totalAmount;
				
				if(PaymentDialog.this.changeAmount < 0.01) {
					PaymentDialog.this.changeAmount = 0.0;
				}
				
				PaymentDialog.this.printReceipt();
				PaymentDialog.this.openNewOrderWindow();
				
			}
			
		});
		
		form.add(inputAmount);
		form.add(payButton);
		
		return form;
	}
	
	private JPanel getCardPaymentForm() {
		JPanel form = new JPanel();
		form.setLayout(new BorderLayout());
		
		return form;
	}
	
	private void addPaymentError(String error) {
		this.errorMessageLabel.setText(error);
	}
	
	private void openNewOrderWindow() {
		this.parent.dispose();
		
		try {
			OrderScreen frame = new OrderScreen(this.parent.getMenu());
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void printReceipt() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		System.out.println("RECEIPT");
		System.out.println(dateFormat.format(date));
		System.out.println("Order #" + this.order.getNumber());
		System.out.println("Table number: " + this.order.getTable().getNumber());
		System.out.println("--------------------");
		
		int i = 1;
		for(Diner diner : this.order.getDiners()) {
			System.out.println("-- DINER " + i + " --");
			
			for(Course course : diner.getCourses()) {
				System.out.println(course.getName() + " - " + course.getKiloCalories() + "kcal - £" + "" + course.getPrice());
			}
			
			System.out.println();
			System.out.println("Total cost for diner " + i + ": £" + OrderScreen.DECIMAL_FORMAT.format(diner.getTotalPrice()));
			System.out.println("Total kilocalories for diner " + i + ": " + diner.getTotalKiloCalories() + "kcal");
			System.out.println();
			
			i++;
		}
		
		System.out.println("--------------------");
		System.out.println("Total: £" + OrderScreen.DECIMAL_FORMAT.format(this.totalAmount));
		System.out.println("Paid: £" + OrderScreen.DECIMAL_FORMAT.format(this.amountPaid));
		System.out.println("Change: £" + OrderScreen.DECIMAL_FORMAT.format(this.changeAmount));
		System.out.println("--------------------");
		System.out.println("Thank you for your custom");
	}
}
