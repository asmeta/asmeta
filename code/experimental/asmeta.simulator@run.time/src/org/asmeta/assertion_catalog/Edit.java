package org.asmeta.assertion_catalog;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.asmeta.test.GuiTest;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class Edit extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	JLabel label;
	int success;
	boolean problem = false;
	String new_invariant;
	String old_invariant;

	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the frame.
	 */
	public Edit(String invariant_value,String type,String full_invariant) {
		old_invariant = full_invariant;
		new_invariant = full_invariant;
		problem = false;
		setResizable(false);
		setDefaultCloseOperation(Add.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		if(type.equals("NAME"))
		{
			this.setTitle("Edit Name"); 
			label = new JLabel("Name: ");
		}
		else if(type.equals("OVER")) {
			this.setTitle("Edit Over"); 
			label = new JLabel("Over: ");
		}
		else {
			this.setTitle("Edit Content"); 
			label = new JLabel("Content: ");
		}
		label.setBounds(49, 55, 84, 30);
		contentPane.add(label);
		
		textField = new JTextField(invariant_value);
		textField.setBounds(125, 59, 271, 22);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton save = new JButton("Save", new ImageIcon(Edit.class.getResource("/org/asmeta/animator/save.png")));
		save.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent e) {
				   if(type.equals("NAME"))
					{
					   if(old_invariant.startsWith("invariant over"))
						   new_invariant = old_invariant.replaceFirst("invariant over", "invariant "+textField.getText().toString()+" over");
					   else
					   {
						   if(textField.getText().isEmpty()) {
							   System.out.println("BOH VEDIAMO: "+"invariant "+invariant_value);
							   new_invariant = old_invariant.replaceFirst("invariant "+invariant_value,"invariant");
						   }
						   else
							   new_invariant = old_invariant.replaceFirst(invariant_value, textField.getText().toString());
					   }
					}
					else {
						if(type.equals("OVER"))
						{
							if(textField.getText().isEmpty())
							{
								JOptionPane.showMessageDialog(null, "THE FIELD CANNOT BE EMPTY");
								problem = true;
							}
							else
							{
								new_invariant = old_invariant.replaceFirst(invariant_value, textField.getText().toString());
								if(problem)
									problem = false;
							}
						}
						else
						{
							if(textField.getText().isEmpty())
							{
								JOptionPane.showMessageDialog(null, "THE FIELD CANNOT BE EMPTY");
								problem = true;
							}
							else
							{
								new_invariant = full_invariant.replace(invariant_value, textField.getText().toString());
								if(problem)
									problem = false;
							}
						}
					}
				   try {
					  // System.out.println("NEW INVARIANT: "+new_invariant);
					  // System.out.println("OLD INVARIANT: "+old_invariant+"\n");
					if(problem == false)
					{
					    new_invariant = new_invariant.replaceAll("\\s+", " ");
					    old_invariant = old_invariant.replaceAll("\\s+", " ");
						success = GuiTest.updateInvariant(new_invariant,old_invariant);
					}
					if(success == 0 && problem==false)
						dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			   }
		});
		save.setBounds(49, 159, 134, 40);
		contentPane.add(save);
		
		JButton cancel = new JButton("Cancel",new ImageIcon(Frame.class.getResource("/org/asmeta/animator/cancel.png")));
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancel.setBounds(244, 159, 134, 40);
		contentPane.add(cancel);
	}
}
