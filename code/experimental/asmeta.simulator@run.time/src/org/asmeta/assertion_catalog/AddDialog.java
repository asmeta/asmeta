package org.asmeta.assertion_catalog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.asmeta.runtime_container.InvariantData;
import org.asmeta.test.TestSimulationContainer;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;





   

public class AddDialog extends JDialog {

	private JPanel contentPane;
	private JTextField invariant_content;
	String invariant_add;
	String new_invariant;
	String old_invariant;
	JLabel over = new JLabel("over");
	//boolean success;
	private List<String> over_values;
	private List<JComboBox> jc = new ArrayList<JComboBox>();
	int n = 1;
	int i = 0;
	int j = 0;
	int l = 0;
	int start_value = 1;
	String new_over_invariants = "";
	boolean problem = false;
	private JTextField invariant_name;
	private JScrollPane scrollPane;
	private JPanel scrollingPane;
	private JComboBox jcombo;
	String[] population;
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
	
	 
	public AddDialog(InvariantData inv_manager) {
			setModal(true);
			setResizable(false);
			setDefaultCloseOperation(AddDialog.DISPOSE_ON_CLOSE);
			setBounds(100, 100, 710, 226);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel invariant = new JLabel("invariant inv_");
			invariant.setBounds(10, 45, 81, 16);
			contentPane.add(invariant);
			
			invariant_content = new JTextField();
			invariant_content.setBounds(368, 42, 324, 22);
			contentPane.add(invariant_content);
			invariant_content.setColumns(10);
			
			this.setTitle("Add Invariant");
			
			JButton save = new JButton("Save",new ImageIcon(InvariantGUI.class.getResource("/org/asmeta/animator/save.png")));
			save.addActionListener(new ActionListener() {
				   public void actionPerformed(ActionEvent e) {
					   problem = false;
					   if(invariant_content.getText().isEmpty())
					   {
						   JOptionPane.showMessageDialog(null, "THE FIELD 'CONTENT' CANNOT BE EMPTY");
						   problem = true;
					   }
					   else
					   {
						    over_values.clear();
						    for(int j=0;j<jc.size() && problem == false;j++)
							{
								if(over_values.isEmpty())
									over_values.add(jc.get(j).getSelectedItem().toString());
								else
								{
									for(int m=0;m<over_values.size() && problem == false;m++)
										if(over_values.get(m).toString().equals(jc.get(j).getSelectedItem().toString()))
												problem = true;
									if(problem == false)
										over_values.add(jc.get(j).getSelectedItem().toString());
									else
										JOptionPane.showMessageDialog(null, "ERROR - DOUBLE OVER SELECTED");
								}
							}
						    if(problem==false)
							{
						    	for(int m=0;m<over_values.size();m++)
								{
									new_over_invariants += over_values.get(m);
									if(m+1!=over_values.size())
										new_over_invariants += ",";
								}
						    	if(invariant_name.getText().isEmpty())
									invariant_add = "invariant over "+new_over_invariants+":"+invariant_content.getText().trim().toString();
								else
									invariant_add = "invariant inv_"+invariant_name.getText().trim()+" over "+new_over_invariants+":"+invariant_content.getText().trim().toString();
						
						    try {
								invariant_add = invariant_add.replaceAll("\\s+", " ");
								//invariant_add = invariant_add.replaceAll(" = ", "=");
								//invariant_add = invariant_add.replaceAll("( ", "(");
								//invariant_add = invariant_add.replaceAll(" )", ")");
								int successCode = InvariantManager.addInvariant(invariant_add,InvariantGUI.containerInstance,InvariantGUI.currentLoadedID);
								if(successCode>0)
									dispose();
								else if(successCode==-1) {
									InvariantGUI.showInvariants();
									dispose();
								}
						    } catch (Exception e1) {
						    	e1.printStackTrace();
						    }
						}
				   }
				   }
			});
			save.setBounds(135, 108, 134, 40);
			contentPane.add(save);
			
			JButton cancel = new JButton("Cancel",new ImageIcon(InvariantGUI.class.getResource("/org/asmeta/animator/cancel.png")));
			cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					InvariantGUI.setAddRefreshEnabled();
					dispose();
				}
			});
			cancel.setBounds(339, 108, 134, 40);
			contentPane.add(cancel);
			
			
			over.setBounds(180, 44, 32, 18);
			contentPane.add(over);
			String[] population = inv_manager.getvariables().toArray(new String[0]);
	        
	        invariant_name = new JTextField();
	        invariant_name.setBounds(89, 42, 86, 22);
	        contentPane.add(invariant_name);
	        invariant_name.setColumns(10);
	        
	        over_values = inv_manager.getvariables();
	        population = inv_manager.getvariables().toArray(new String[0]);
	        
	        SpinnerModel value =  
					new SpinnerNumberModel(1, //initial value  
	                1, //minimum value  
	                inv_manager.getvariables().size(), //maximum value  
	                1); //step  
	        
	        JSpinner spinner = new JSpinner(value);
	        spinner.setBounds(297, 3, 30, 22);
	        
	        JScrollPane scrollPane = new JScrollPane();
	        scrollPane.setBounds(210, 42, 147, 59);
	        scrollPane.setBorder(null);
	        contentPane.add(scrollPane);
	        
	        scrollingPane = new JPanel();
	        scrollPane.setViewportView(scrollingPane);
	        
	        jcombo = new JComboBox();
	        jc.add(jcombo);
	        
            jcombo.setModel(new DefaultComboBoxModel<String>(population));
            
                        scrollingPane.setLayout(null);
                        jcombo.setBounds(0, 0, 130, 22);
                        scrollingPane.add(jcombo);
	        
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
					
					if(n>2)
						scrollingPane.setPreferredSize(new Dimension(100,40 + (22*n)));
					else
						scrollingPane.setPreferredSize(new Dimension(100,40));
					
					i=0;
		            while(i<n)
		            {
		            	jcombo = new JComboBox();
						jc.add(jcombo);
		            	
						String[] population = inv_manager.getvariables().toArray(new String[0]);
		                jcombo.setModel(new DefaultComboBoxModel<String>(population));
		                
		                /*if(i<over_values.size())
		                	jcombo.setSelectedItem(over_values.get(i).toString());*/
		    		    jcombo.addActionListener(new ActionListener() {
		    	            public void actionPerformed(ActionEvent event) {
		    	               
		    	            }
		    	        });
		    		    jcombo.setBounds(0, i*30, 130, 22);
		    		    scrollingPane.add(jcombo);
		            	i++;
		            }
		            
				}
				
			});
	        
	        contentPane.add(spinner);
	        ((DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
	        
	        JLabel lblNewLabel = new JLabel("Number of Over");
	        lblNewLabel.setBounds(180, 6, 105, 16);
	        contentPane.add(lblNewLabel);
	        
	        JLabel lblNewLabel_1 = new JLabel(":");
	        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
	        lblNewLabel_1.setBounds(355, 44, 14, 18);
	        contentPane.add(lblNewLabel_1);
	        
	        
	        
	        
			
		}
}
