package org.asmeta.assertion_catalog;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.*;

import org.asmeta.runtime_container.InvariantData;
import org.asmeta.simulationUI.SimGUI;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import java.awt.Font;

public class EditDialog extends JDialog {

	private JPanel contentPane;
	private JTextField textField;
	private JScrollPane scrollPane;
	private JComboBox jcombo;
	private JPanel scrollingPane;
	JLabel label;
	//boolean success;
	boolean problem = false;
	String new_invariant;
	String old_invariant;
	String invariant_name;
	private List<String> over_values;
	private List<JComboBox> jc = new ArrayList<JComboBox>();
	int n = 1;
	int i = 0;
	int j = 0;
	int l = 0;
	int start_value = 1;
	String new_over_invariants = "";

	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the frame.
	 */
	public EditDialog(String invariant_value,String type,String full_invariant,InvariantData inv_manager) {
		setModal(true);
		old_invariant = full_invariant;
		new_invariant = full_invariant;
		problem = false;
		setResizable(false);
		setIconImages(SimGUI.icons);
		setDefaultCloseOperation(EditDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(InvariantGUI.getContentPane());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		if(type.equals("NAME"))
		{
			if(invariant_value!="") 
				textField = new JTextField(invariant_value.substring(4));
			else
				textField = new JTextField(invariant_value);
			
			invariant_name = textField.getText().toString();
			textField.setBounds(125, 59, 271, 22);
			contentPane.add(textField);
			textField.setColumns(10);
			this.setTitle("Edit Name"); 
			label = new JLabel("Name: inv_");
			label.setBounds(59, 55, 84, 30);
		}
		else if(type.equals("OVER")) {
			
			over_values = new LinkedList<String>(Arrays.asList(invariant_value.split(",")));
			start_value = over_values.size();
			
			
			JLabel number = new JLabel("Number of over: ");
			SpinnerModel value =  
						new SpinnerNumberModel(start_value, //initial value  
		                1, //minimum value  
		                inv_manager.getvariables().size(), //maximum value  
		                1); //step  
			JSpinner spinner = new JSpinner(value);
			
			scrollPane = new JScrollPane();
			scrollPane.setBounds(149, 60, 250, 120);
			scrollPane.setBorder(null);
			contentPane.add(scrollPane);
			
			scrollingPane = new JPanel();
			
			
			
			spinner.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					i=0;
					while(i<scrollingPane.getComponentCount())
					{
						if(scrollingPane.getComponent(i) instanceof JComboBox)
							scrollingPane.remove(i);
						else
							i++;
					}
					jc.clear();
					
					scrollingPane.revalidate();
					scrollingPane.repaint();
					
					
					n = (Integer) spinner.getValue();
					
					if(n>4)
						scrollingPane.setPreferredSize(new Dimension(230,100 + (22*n)));
					else
						scrollingPane.setPreferredSize(new Dimension(230,100));
					
					i=0;
		            while(i<n)
		            {
		            	jcombo = new JComboBox();
						jc.add(jcombo);
		            	
						String[] population = inv_manager.getvariables().toArray(new String[0]);
		                jcombo.setModel(new DefaultComboBoxModel<String>(population));
		                
		                if(i<over_values.size())
		                	jcombo.setSelectedItem(over_values.get(i).toString());
		    		    jcombo.addActionListener(new ActionListener() {
		    	            public void actionPerformed(ActionEvent event) {
		    	               
		    	            }
		    	        });
		    		    jcombo.setBounds(0, i*30, 230, 22);
		    		    scrollingPane.add(jcombo);
		            	i++;
		            }
		            
				}
				
			});
			
            spinner.setBounds(200,15,50,30);    
            contentPane.add(spinner);  
            ((DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
            
			i = 0;
			while(i<start_value)
			{
				jcombo = new JComboBox();
				jc.add(jcombo);
				String[] population = inv_manager.getvariables().toArray(new String[0]);
	            jcombo.setModel(new DefaultComboBoxModel<String>(population));
	
	            jcombo.setSelectedItem(over_values.get(i).toString());
	            
			    jcombo.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent event) {
		            	JComboBox comboBox = (JComboBox) event.getSource();
    	                Object selected = comboBox.getSelectedItem();
    	                
    	               
		            }
		        });
	
			    scrollingPane.setLayout(null);
			    jcombo.setBounds(0, i*30, 230, 22);
			    scrollingPane.add(jcombo);
			    i++;
			}
			
			
			
		    scrollPane.setViewportView(scrollingPane);	
		    
		    
            contentPane.setSize(300,300);    
            contentPane.setLayout(null);    
            contentPane.setVisible(true); 
            
			number.setBounds(100, 20, 100, 20);
			contentPane.add(number);
			this.setTitle("Edit Over"); 
			label = new JLabel("Over: ");
			label.setBounds(80, 55, 84, 30);
		}
		else {
			textField = new JTextField(invariant_value);
			textField.setBounds(125, 59, 271, 22);
			contentPane.add(textField);
			textField.setColumns(10);
			this.setTitle("Edit Content"); 
			label = new JLabel("Content: ");
			label.setBounds(49, 55, 84, 30);
		}
		contentPane.add(label);
		
		
		
		JButton save = new JButton("Save", new ImageIcon(EditDialog.class.getResource("/org/asmeta/animator/save.png")));
		save.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		save.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent e) {
				   if(type.equals("NAME"))
					{
					   if(old_invariant.startsWith("invariant over"))
						   if(textField.getText().isEmpty())
							   new_invariant = old_invariant;
						   else
							   new_invariant = old_invariant.replaceFirst("invariant over", "invariant inv_"+textField.getText().toString()+" over");
					   else
					   {
						   if(textField.getText().isEmpty()) {
							   new_invariant = old_invariant.replaceFirst("invariant inv_"+invariant_name,"invariant");
						   }
						   else
							   new_invariant = old_invariant.replaceFirst("inv_"+invariant_name, "inv_"+textField.getText().toString());
							  
					   }
					   System.out.println("NEW: "+new_invariant+"\nOLD: "+old_invariant);
					}
					else {
						if(type.equals("OVER"))
						{
							problem = false;
							over_values.clear();
							for(int l=0;l<jc.size() && problem == false;l++)
							{
								if(over_values.isEmpty())
									over_values.add(jc.get(l).getSelectedItem().toString());
								else
								{
									for(int m=0;m<over_values.size() && problem == false;m++)
										if(over_values.get(m).toString().equals(jc.get(l).getSelectedItem().toString()))
												problem = true;
									if(problem == false)
										over_values.add(jc.get(l).getSelectedItem().toString());
									else
										JOptionPane.showMessageDialog(contentPane, "Error: double <over> selected!", "Error", JOptionPane.ERROR_MESSAGE);
								}
							}
							if(problem==false)
							{
								//new_invariant = old_invariant.replaceFirst(invariant_value, textField.getText().toString());
								new_over_invariants = "";
								for(int m=0;m<over_values.size();m++)
								{
									new_over_invariants += over_values.get(m);
									if(m+1!=over_values.size())
										new_over_invariants += ",";
								}
								new_invariant = old_invariant.replaceFirst(old_invariant.substring(old_invariant.indexOf("over")+5, old_invariant.indexOf(':')),new_over_invariants);
							}
						}
						else
						{
							if(textField.getText().isEmpty())
							{
								JOptionPane.showMessageDialog(contentPane, "Error: the field cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
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
					int successCode=0;
					if(problem == false)
					{
					    new_invariant = new_invariant.replaceAll("\\s+", " ");
					    old_invariant = old_invariant.replaceAll("\\s+", " ");
					    successCode = InvariantManager.updateInvariant(new_invariant,old_invariant,InvariantGUI.containerInstance,InvariantGUI.currentLoadedID);
					}
					if(successCode>0&& problem==false)
						dispose();
					else if (successCode==-1&& problem==false) {
						InvariantGUI.showInvariants();
						dispose();
				   }
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			   }
		});
		save.setBounds(49, 200, 134, 40);
		contentPane.add(save);
		
		JButton cancel = new JButton("Cancel",new ImageIcon(InvariantGUI.class.getResource("/org/asmeta/animator/cancel.png")));
		cancel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InvariantGUI.setAddRefreshEnabled();
				dispose();
			}
		});
		cancel.setBounds(244, 200, 134, 40);
		contentPane.add(cancel);
		
		
	}
}
