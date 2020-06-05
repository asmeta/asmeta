package org.asmeta.assertion_catalog;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.asmeta.runtime_container.InvariantData;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;





   

public class AddDialog extends JDialog {

	private JPanel contentPane;
	private JTextField invariant_content;
	String invariant_add;
	String new_invariant;
	String old_invariant;
	JLabel over = new JLabel("over");
	//boolean success;
	//private List<String> over_values;
	private List<JComboBox> jc = new ArrayList<JComboBox>();
	int n = 1;
	int i = 0;
	int j = 0;
	int l = 0;
	int start_value = 1;
	String new_over_invariants = "";
	boolean problem = false;
	private JTextField invariant_name;
	//private JScrollPane scrollPane;
	//private JPanel scrollingPane;
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
	/* public AddDialog(InvariantData inv_manager) {
			setResizable(false);
			setDefaultCloseOperation(AddDialog.DISPOSE_ON_CLOSE);
			setBounds(100, 100, 910, 226);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel invariant = new JLabel("invariant inv_");
			invariant.setBounds(10, 45, 81, 14);
			contentPane.add(invariant);
			
			invariant_content = new JTextField();
			invariant_content.setBounds(373, 42, 452, 20);
			contentPane.add(invariant_content);
			invariant_content.setColumns(10);
			
			this.setTitle("Add Invariant");
			

			start_value=inv_manager.getvariables().size();
			
			JLabel number = new JLabel("Number of over: ");
			SpinnerModel value =  
						new SpinnerNumberModel(start_value, //initial value  
		                1, //minimum value  
		                10, //maximum value  
		                1); //step  
			JSpinner spinner = new JSpinner(value);
			spinner.setBounds(250,5,50,30);    
            contentPane.add(spinner); 
			scrollPane = new JScrollPane();
			scrollPane.setBounds(215, 41, 95, 22);
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
							scrollingPane.setPreferredSize(new Dimension(230,100 + (n)));
						else
							scrollingPane.setPreferredSize(new Dimension(230,100));
						
						i=0;
			            while(i<n)
			            {
			            	jcombo = new JComboBox();
							jc.add(jcombo);
			            	
							String[] population = inv_manager.getvariables().toArray(new String[0]);
			                jcombo.setModel(new DefaultComboBoxModel<String>(population));
			                
			                if(i<start_value)
			                	jcombo.setSelectedItem(inv_manager.getvariables().get(i).toString());
			    		    jcombo.addActionListener(new ActionListener() {
			    	            public void actionPerformed(ActionEvent event) {
			    	               
			    	            }
			    	        });
			    		    jcombo.setBounds(0, i*30, 215, 22);
			    		    scrollingPane.add(jcombo);
			            	i++;
			            }
					
				}
				
			});
			  ((DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
	            
				i = 0;
				while(i<start_value)
				{
					jcombo = new JComboBox();
					jc.add(jcombo);
					String[] population = inv_manager.getvariables().toArray(new String[0]);
		            jcombo.setModel(new DefaultComboBoxModel<String>(population));
		
		            jcombo.setSelectedItem(inv_manager.getvariables().get(i).toString());
		            
				    jcombo.addActionListener(new ActionListener() {
			            public void actionPerformed(ActionEvent event) {
			            	JComboBox comboBox = (JComboBox) event.getSource();
	    	                Object selected = comboBox.getSelectedItem();
	    	                
	    	               
			            }
			        });
		
				    scrollingPane.setLayout(null);
				    jcombo.setBounds(0, i*30, 215, 22);
				    scrollingPane.add(jcombo);
				    i++;
				}
				
				
				
			    scrollPane.setViewportView(scrollingPane);	
			JButton save = new JButton("Save",new ImageIcon(InvariantGraphicsInterface.class.getResource("/org/asmeta/animator/save.png")));
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
							   invariant_add = "invariant over "+jcombo.getSelectedItem().toString().trim()+":"+invariant_content.getText().trim().toString();
						   else
							   invariant_add = "invariant inv_"+invariant_name.getText().trim()+" over "+jcombo.getSelectedItem().toString().trim()+":"+invariant_content.getText().trim().toString();
						   problem = false;
					   }
					   if(problem == false)
						try {
							invariant_add = invariant_add.replaceAll("\\s+", " ");
							//invariant_add = invariant_add.replaceAll(" = ", "=");
							//invariant_add = invariant_add.replaceAll("( ", "(");
							//invariant_add = invariant_add.replaceAll(" )", ")");
							int successCode = InvariantManager.addInvariant(invariant_add,InvariantGraphicsInterface.containerInstance,InvariantGraphicsInterface.currentLoadedID);
							if(successCode>0)
								dispose();
							else if(successCode==-1) {
								InvariantGraphicsInterface.showInvariants();
								dispose();
							}
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				   }
				  });
			save.setBounds(239, 108, 134, 40);
			contentPane.add(save);
			
			JButton cancel = new JButton("Cancel",new ImageIcon(InvariantGraphicsInterface.class.getResource("/org/asmeta/animator/cancel.png")));
			cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					InvariantGraphicsInterface.setAddRefreshEnabled();
					dispose();
				}
			});
			cancel.setBounds(435, 108, 134, 40);
			contentPane.add(cancel);
			
			
			over.setBounds(180, 44, 32, 16);
			contentPane.add(over);
	        
	        invariant_name = new JTextField();
	        invariant_name.setBounds(89, 42, 86, 20);
	        contentPane.add(invariant_name);
	        invariant_name.setColumns(10);
	        
	        JLabel lblNewLabel = new JLabel(":");
	        lblNewLabel.setBounds(347, 45, 26, 14);
	        contentPane.add(lblNewLabel);
			
		}*/
	 
	 
	 
	 
	 
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
			invariant.setBounds(10, 45, 81, 14);
			contentPane.add(invariant);
			
			invariant_content = new JTextField();
			invariant_content.setBounds(209, 42, 452, 20);
			contentPane.add(invariant_content);
			invariant_content.setColumns(10);
			
			this.setTitle("Add Invariant");
			
			JButton save = new JButton("Save",new ImageIcon(InvariantGraphicsInterface.class.getResource("/org/asmeta/animator/save.png")));
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
							   invariant_add = "invariant inv_"+invariant_name.getText().trim()+" over "+invariant_content.getText().trim().toString();
						   problem = false;
					   }
					   if(problem == false)
						try {
							invariant_add = invariant_add.replaceAll("\\s+", " ");
							//invariant_add = invariant_add.replaceAll(" = ", "=");
							//invariant_add = invariant_add.replaceAll("( ", "(");
							//invariant_add = invariant_add.replaceAll(" )", ")");
							int successCode = InvariantManager.addInvariant(invariant_add,InvariantGraphicsInterface.containerInstance,InvariantGraphicsInterface.currentLoadedID);
							if(successCode>0)
								dispose();
							else if(successCode==-1) {
								InvariantGraphicsInterface.showInvariants();
								dispose();
							}
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				   }
				  });
			save.setBounds(135, 108, 134, 40);
			contentPane.add(save);
			
			JButton cancel = new JButton("Cancel",new ImageIcon(InvariantGraphicsInterface.class.getResource("/org/asmeta/animator/cancel.png")));
			cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					InvariantGraphicsInterface.setAddRefreshEnabled();
					dispose();
				}
			});
			cancel.setBounds(339, 108, 134, 40);
			contentPane.add(cancel);
			
			
			over.setBounds(180, 44, 32, 16);
			contentPane.add(over);
			//String[] population = inv_manager.getvariables().toArray(new String[0]);
	        
	        invariant_name = new JTextField();
	        invariant_name.setBounds(89, 42, 86, 20);
	        contentPane.add(invariant_name);
	        invariant_name.setColumns(10);
			
		}
}
