package org.asmeta.assertion_catalog;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.asmeta.test.GuiTest;
import org.asmeta.test.TestRuntimeModelContainer;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;





   

public class AddUpdateDialog extends JFrame {

	private JPanel contentPane;
	private JTextField text_invariant;
	String Invariant_add;
	String new_invariant;
	String old_invariant;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	 public static void infoBox(String infoMessage, String titleBar)
	 {
	    JOptionPane.showMessageDialog(null, infoMessage, "ERROR: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	 }
	public AddUpdateDialog(String invariant_value) {
		setResizable(false);
		setDefaultCloseOperation(AddUpdateDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 719, 226);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel invariant_over = new JLabel("invariant over");
		invariant_over.setBounds(31, 45, 81, 14);
		contentPane.add(invariant_over);
		
		text_invariant = new JTextField();
		text_invariant.setBounds(124, 42, 549, 20);
		contentPane.add(text_invariant);
		text_invariant.setColumns(10);
		if(invariant_value!="")
		{
			text_invariant.setText(invariant_value.substring(invariant_value.indexOf("over")+5));
			old_invariant=invariant_over.getText()+" ";
			old_invariant=old_invariant+text_invariant.getText();
			this.setTitle("Edit Invariant");
		}
		else
			this.setTitle("Add Invariant");
		JButton save = new JButton("Save",new ImageIcon(Frame.class.getResource("/org/asmeta/animator/save.png")));
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(text_invariant.getText().isEmpty())
					{
						infoBox("The field is empty","");
					}
					else
					{
						if(invariant_value=="")
						{
							Invariant_add=invariant_over.getText()+" ";
							Invariant_add=Invariant_add+text_invariant.getText();
							GuiTest.addInvariant(Invariant_add);
						}
						else
						{
							new_invariant=invariant_over.getText()+" ";
							new_invariant=new_invariant+text_invariant.getText();
							GuiTest.updateInvariant(new_invariant,old_invariant);
						}
						dispose();
					}				
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		save.setBounds(184, 108, 134, 40);
		contentPane.add(save);
		
		JButton cancel = new JButton("Cancel",new ImageIcon(Frame.class.getResource("/org/asmeta/animator/cancel.png")));
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancel.setBounds(383, 108, 134, 40);
		contentPane.add(cancel);
	}
}
