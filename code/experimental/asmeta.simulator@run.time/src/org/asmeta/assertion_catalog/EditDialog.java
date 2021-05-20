package org.asmeta.assertion_catalog;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.*;

import org.asmeta.runtime_container.InvariantData;
import org.asmeta.simulationUI.SimGUI;

import javax.swing.JSpinner.DefaultEditor;
import java.awt.Font;

public class EditDialog extends JDialog {
	private static JPanel contentPane;
	static JTextField textField;
	static JTextArea textArea;
	static JScrollPane scrollPane;
	static JComboBox<String> jcombo;
	static JPanel scrollingPane;
	static JLabel label;
	static JLabel number;
	static JSpinner spinner;
	static JButton cancel;
	static JButton save;
	
	private boolean problem = false;
	private String newInvariant;
	private String oldInvariant;
	private String invariantName;
	private List<String> overValues;
	private List<JComboBox<?>> jc = new ArrayList<JComboBox<?>>();
	private int n = 1;
	private int i = 0;
	private int j = 0;
	private int l = 0;
	private int startingValue = 1;
	private String newOverInvariants;
	private String[] population;
	
	/**
	 * Create the frame.
	 */
	public EditDialog(String invariantValue,String type,String full_invariant,InvariantData inv_manager) {
		UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		setModal(true);
		oldInvariant = full_invariant;
		newInvariant = full_invariant;
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
			if(invariantValue!="") 
				textField = new JTextField(invariantValue.substring(4));
			else
				textField = new JTextField(invariantValue);
			
			invariantName = textField.getText().toString();
			textField.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
			textField.setBounds(125, 59, 271, 22);
			contentPane.add(textField);
			textField.setColumns(10);
			this.setTitle("Edit Name"); 
			label = new JLabel("Name: inv_");
			label.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			switch(SimGUI.fontSize) {
				case 14: label.setBounds(29, 55, 114, 30); break;
				case 16: label.setBounds(19, 55, 114, 30); break;
				case 18: label.setBounds(9, 55, 114, 30); break;
				default: label.setBounds(39, 55, 84, 30);
			}
		}
		else if(type.equals("OVER")) {
			
			overValues = new LinkedList<String>(Arrays.asList(invariantValue.split(",")));
			startingValue = overValues.size();
			
			number = new JLabel("Number of over: ");
			number.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
			SpinnerModel value =  
						new SpinnerNumberModel(startingValue, //initial value  
		                1, //minimum value  
		                inv_manager.getvariables().size(), //maximum value  
		                1); //step  
			spinner = new JSpinner(value);
			spinner.setFont(new Font("Segoe UI", Font.PLAIN, Math.min(SimGUI.fontSize, 14)));
			
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
		            	jcombo = new JComboBox<String>();
		            	jcombo.setFont(new Font("Segoe UI", Font.PLAIN, Math.min(SimGUI.fontSize, 14)));
						jc.add(jcombo);
		            	
						population = inv_manager.getvariables().toArray(new String[0]);
		                jcombo.setModel(new DefaultComboBoxModel<String>(population));
		                
		                if(i<overValues.size())
		                	jcombo.setSelectedItem(overValues.get(i).toString());
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
			while(i<startingValue)
			{
				jcombo = new JComboBox<String>();
				jcombo.setFont(new Font("Segoe UI", Font.PLAIN, Math.min(SimGUI.fontSize, 14)));
				jc.add(jcombo);
				String[] population = inv_manager.getvariables().toArray(new String[0]);
	            jcombo.setModel(new DefaultComboBoxModel<String>(population));
	
	            jcombo.setSelectedItem(overValues.get(i).toString());
	            
			    jcombo.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent event) {
		            	JComboBox<?> comboBox = (JComboBox<?>) event.getSource();
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
            
            switch(SimGUI.fontSize) {
            	case 14: number.setBounds(90, 20, 130, 20); break;
            	case 16: number.setBounds(70, 20, 130, 20); break;
            	case 18: number.setBounds(50, 20, 140, 20); break;
            	default: number.setBounds(100, 20, 100, 20);
            }
            number.setHorizontalAlignment(SwingConstants.CENTER);
			contentPane.add(number);
			this.setTitle("Edit Over"); 
			label = new JLabel("Over: ");
			label.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(80, 55, 84, 30);
		}
		else {
			//textField = new JTextField(invariantValue);
			//textField.setBounds(125, 59, 271, 22);
			//contentPane.add(textField);
			//textField.setColumns(10);
			textArea = new JTextArea(invariantValue);
			textArea.setLineWrap(true);
			textArea.setBounds(125, 19, 271, 160);
			textArea.setFont(new Font("Consolas", Font.PLAIN, Math.min(SimGUI.fontSize + 1, 15)));
			contentPane.add(textArea);
			this.setTitle("Edit Content"); 
			label = new JLabel("Content: ");
			label.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
			label.setBounds(49, 55, 84, 30);
		}
		contentPane.add(label);
		
		
		
		save = new JButton("Save", new ImageIcon(EditDialog.class.getResource("/org/asmeta/animator/save.png")));
		save.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
		save.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent e) {
				   if(type.equals("NAME"))
					{
					   if(oldInvariant.startsWith("invariant over"))
						   if(textField.getText().isEmpty())
							   newInvariant = oldInvariant;
						   else
							   newInvariant = oldInvariant.replaceFirst("invariant over", "invariant inv_"+textField.getText().toString()+" over");
					   else
					   {
						   if(textField.getText().isEmpty()) {
							   newInvariant = oldInvariant.replaceFirst("invariant inv_"+invariantName,"invariant");
						   }
						   else
							   newInvariant = oldInvariant.replaceFirst("inv_"+invariantName, "inv_"+textField.getText().toString());
							  
					   }
					   System.out.println("NEW: "+newInvariant+"\nOLD: "+oldInvariant);
					}
					else {
						if(type.equals("OVER"))
						{
							problem = false;
							overValues.clear();
							for(int l=0;l<jc.size() && problem == false;l++)
							{
								if(overValues.isEmpty())
									overValues.add(jc.get(l).getSelectedItem().toString());
								else
								{
									for(int m=0;m<overValues.size() && problem == false;m++)
										if(overValues.get(m).toString().equals(jc.get(l).getSelectedItem().toString()))
												problem = true;
									if(problem == false)
										overValues.add(jc.get(l).getSelectedItem().toString());
									else
										JOptionPane.showMessageDialog(contentPane, "Error: double <over> selected!", "Error", JOptionPane.ERROR_MESSAGE);
								}
							}
							if(problem==false)
							{
								//newInvariant = oldInvariant.replaceFirst(invariantValue, textField.getText().toString());
								newOverInvariants = "";
								for(int m=0;m<overValues.size();m++)
								{
									newOverInvariants += overValues.get(m);
									if(m+1!=overValues.size())
										newOverInvariants += ",";
								}
								newInvariant = oldInvariant.replaceFirst(oldInvariant.substring(oldInvariant.indexOf("over")+5, oldInvariant.indexOf(':')),newOverInvariants);
							}
						}
						else
						{
							if(textArea.getText().isEmpty())
							{
								JOptionPane.showMessageDialog(contentPane, "Error: the field cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
								problem = true;
							}
							else
							{
								newInvariant = full_invariant.replace(invariantValue, textArea.getText().toString());
								if(problem)
									problem = false;
							}
						}
					}
				   try {
					int successCode=0;
					if(problem == false)
					{
					    newInvariant = newInvariant.replaceAll("\\s+", " ");
					    oldInvariant = oldInvariant.replaceAll("\\s+", " ");
					    successCode = InvariantManager.updateInvariant(newInvariant,oldInvariant,InvariantGUI.containerInstance,InvariantGUI.currentLoadedID);
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
		
		cancel = new JButton("Cancel",new ImageIcon(InvariantGUI.class.getResource("/org/asmeta/animator/cancel.png")));
		cancel.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
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
