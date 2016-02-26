import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class PaymentDialog extends JDialog {

	private Container cp;
	private OrderScreen parent;
	private Order order;
	private ButtonGroup paymentMethodRadioGroup;
	private JRadioButton cardPaymentRadioButton, cashPaymentRadioButton;
	private JPanel paymentMethodPane;
	
	public PaymentDialog(OrderScreen parent) {
		// Make the dialog modal
		super(parent, true);
		
		// Set the default layout
		this.cp = this.getContentPane();
		this.cp.setLayout(new BorderLayout());
		
		// Set the attributes
		this.parent = parent;
		this.order = parent.getOrder();
		
		this.paymentMethodRadioGroup = new ButtonGroup();
		this.cardPaymentRadioButton = new JRadioButton("Card");
		this.cashPaymentRadioButton = new JRadioButton("Cash");
		this.paymentMethodRadioGroup.add(this.cardPaymentRadioButton);
		this.paymentMethodRadioGroup.add(this.cashPaymentRadioButton);
		
		this.paymentMethodPane = new JPanel();
		this.paymentMethodPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.paymentMethodPane.add(this.cashPaymentRadioButton);
		this.paymentMethodPane.add(this.cardPaymentRadioButton);
		
		this.cp.add(this.paymentMethodPane, BorderLayout.CENTER);
		
		// Set some basics for JDialog
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("Pay for your order");
		
		this.pack();
	}
}
