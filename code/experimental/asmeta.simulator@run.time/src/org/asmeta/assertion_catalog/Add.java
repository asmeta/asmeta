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





   

public class Add extends JFrame {

	private JPanel contentPane;
	private JTextField invariant_content;
	String invariant_add;
	String new_invariant;
	String old_invariant;
	private JTextField invariant_name;
	JLabel over = new JLabel("over");
	boolean success;
	boolean problem = false;

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
	public Add() {
		setResizable(false);
		setDefaultCloseOperation(Add.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 719, 226);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel invariant = new JLabel("invariant inv_");
		invariant.setBounds(31, 45, 81, 14);
		contentPane.add(invariant);
		
		invariant_content = new JTextField();
		invariant_content.setBounds(221, 42, 452, 20);
		contentPane.add(invariant_content);
		invariant_content.setColumns(10);
		
		this.setTitle("Add Invariant");
		
		JButton save = new JButton("Save",new ImageIcon(Frame.class.getResource("/org/asmeta/animator/save.png")));
		save.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent e) {
				   if(invariant_content.getText().isEmpty())
				   {
					   JOptionPane.showMessageDialog(null, "THE FIELD 'CONTENT' CANNOT BE EMPTY");
					   problem = true;
				   }
				   else
				   {
					   if(invariant_name.getText().isEmpty())
						   invariant_add = "invariant over "+invariant_content.getText().trim().toString();
					   else
						   invariant_add = "invariant inv_"+invariant_name.getText().trim().toString()+" over "+invariant_content.getText().trim().toString();
					   problem = false;
				   }
				   if(problem == false)
					try {
						invariant_add = invariant_add.replaceAll("\\s+", " ");
						success = GuiTest.addInvariant(invariant_add);
						if(success)
							dispose();
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
		
		
		over.setBounds(184, 44, 35, 16);
		contentPane.add(over);
		
		invariant_name = new JTextField();
		invariant_name.setBounds(113, 41, 59, 22);
		contentPane.add(invariant_name);
		invariant_name.setColumns(10);
	}
}
