package org.asmeta.assertion_catalog;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.asmeta.runtime_container.InvariantData;
import org.asmeta.simulationUI.SimGUI;

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
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Font;
import javax.swing.JTextArea;


public class AddDialog extends JDialog {
	private static JPanel contentPane;
	private String invariantAdd;
	private List<String> overValues;
	private List<JComboBox<?>> jc = new ArrayList<JComboBox<?>>();
	private int spinnerValue = 1;
	private int i = 0;
	private int j = 0;
	private int l = 0;
	private String newOverInvariants;
	private boolean problem = false;
	private String[] population;
	
	static JLabel over;
	static JTextField invariantName;
	static JScrollPane scrollPane;
	static JPanel scrollingPane;
	static JComboBox<String> jcombo;
	static JLabel invariant;
	static JTextArea invariantContent;
	static JButton save;
	static JButton cancel;
	static JSpinner spinner;
	static JLabel lblNumOver;
	static JLabel lblGeneric;
	
	 
	public AddDialog(InvariantData inv_manager) {
			UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
			UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
			setModal(true);
			setIconImages(SimGUI.icons);
			setResizable(false);
			setDefaultCloseOperation(AddDialog.DISPOSE_ON_CLOSE);
			setBounds(100, 100, 710, 226);
			setLocationRelativeTo(InvariantGUI.getContentPane());
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			if(SimGUI.fontSize > 12) {
				invariant = new JLabel("New inv. inv_");
			} else {
				invariant = new JLabel("New invariant inv_");
			}
			invariant.setHorizontalAlignment(SwingConstants.CENTER);
			invariant.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
			invariant.setBounds(10, 42, 105, 22);
			contentPane.add(invariant);
			setTitle("Add Invariant");
			
			invariantContent = new JTextArea();
			invariantContent.setLineWrap(true);
			invariantContent.setFont(new Font("Consolas", Font.PLAIN, SimGUI.fontSize + 1));
		    invariantContent.setBounds(367, 11, 317, 86);
		    contentPane.add(invariantContent);
			
			save = new JButton("Save",new ImageIcon(InvariantGUI.class.getResource("/org/asmeta/animator/save.png")));
			save.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
			save.addActionListener(new ActionListener() {
				   public void actionPerformed(ActionEvent e) {
					   problem = false;
					   if(invariantContent.getText().isEmpty())
					   {
						   JOptionPane.showMessageDialog(contentPane, "Error: the <content> field cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
						   problem = true;
					   }
					   else
					   {
						    overValues.clear();
						    for(int j=0;j<jc.size() && problem == false;j++)
							{
								if(overValues.isEmpty())
									overValues.add(jc.get(j).getSelectedItem().toString());
								else
								{
									for(int m=0;m<overValues.size() && problem == false;m++)
										if(overValues.get(m).toString().equals(jc.get(j).getSelectedItem().toString()))
												problem = true;
									if(problem == false)
										overValues.add(jc.get(j).getSelectedItem().toString());
									else
										JOptionPane.showMessageDialog(contentPane, "Error: double <over> selected!", "Error", JOptionPane.ERROR_MESSAGE);
								}
							}
						    if(problem==false)
							{
						    	for(int m=0;m<overValues.size();m++)
								{
									newOverInvariants += overValues.get(m);
									if(m+1!=overValues.size())
										newOverInvariants += ",";
								}
						    	if(invariantName.getText().isEmpty())
									invariantAdd = "invariant over "+newOverInvariants+":"+invariantContent.getText().trim().toString();
								else
									invariantAdd = "invariant inv_"+invariantName.getText().trim()+" over "+newOverInvariants+":"+invariantContent.getText().trim().toString();
						
						    try {
								invariantAdd = invariantAdd.replaceAll("\\s+", " ");
								//invariantAdd = invariantAdd.replaceAll(" = ", "=");
								//invariantAdd = invariantAdd.replaceAll("( ", "(");
								//invariantAdd = invariantAdd.replaceAll(" )", ")");
								int successCode = InvariantManager.addInvariant(invariantAdd,InvariantGUI.containerInstance,InvariantGUI.currentLoadedID);
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
			save.setBounds(166, 108, 134, 40);
			contentPane.add(save);
			
			cancel = new JButton("Cancel",new ImageIcon(InvariantGUI.class.getResource("/org/asmeta/animator/cancel.png")));
			cancel.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
			cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					InvariantGUI.setAddRefreshEnabled();
					dispose();
				}
			});
			cancel.setBounds(400, 108, 134, 40);
			contentPane.add(cancel);
			
			if(SimGUI.fontSize > 12) {
				over = new JLabel("on");
			} else {
				over = new JLabel("over");
			}
			over.setHorizontalAlignment(SwingConstants.CENTER);
			over.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
			over.setBounds(174, 42, 32, 22);
			contentPane.add(over);
	        
	        invariantName = new JTextField();
	        invariantName.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
	        invariantName.setBounds(118, 42, 52, 22);
	        contentPane.add(invariantName);
	        invariantName.setColumns(10);
	        
	        overValues = inv_manager.getvariables();
	        population = inv_manager.getvariables().toArray(new String[0]);
	        
	        SpinnerModel value =  
					new SpinnerNumberModel(1, //initial value  
	                1, //minimum value  
	                inv_manager.getvariables().size(), //maximum value  
	                1); //step  
	        
	        spinner = new JSpinner(value);
	        spinner.setFont(new Font("Segoe UI", Font.PLAIN, Math.min(SimGUI.fontSize, 14)));
	        spinner.setBounds(275, 3, 52, 22);
	        
	        scrollPane = new JScrollPane();
	        scrollPane.setBounds(210, 42, 147, 59);
	        scrollPane.setBorder(null);
	        contentPane.add(scrollPane);

	        scrollingPane = new JPanel();
	        scrollPane.setViewportView(scrollingPane);
	        
	        jcombo = new JComboBox<String>();
	        jcombo.setFont(new Font("Segoe UI", Font.PLAIN, Math.min(SimGUI.fontSize, 14)));
	        jc.add(jcombo);
            jcombo.setModel(new DefaultComboBoxModel<String>(population));
            
            scrollingPane.setLayout(null);
            jcombo.setBounds(0, 0, 147, 22);
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
					
					spinnerValue = (Integer) spinner.getValue();
					
					if(spinnerValue>2)
						scrollingPane.setPreferredSize(new Dimension(100,40 + (22*spinnerValue)));
					else
						scrollingPane.setPreferredSize(new Dimension(100,40));
					
					i=0;
		            while(i<spinnerValue)
		            {
		            	jcombo = new JComboBox<String>();
		            	jcombo.setFont(new Font("Segoe UI", Font.PLAIN, Math.min(SimGUI.fontSize, 14)));
						jc.add(jcombo);
		            	
						population = inv_manager.getvariables().toArray(new String[0]);
		                jcombo.setModel(new DefaultComboBoxModel<String>(population));
		    		    jcombo.setBounds(0, i*30, 130, 22);
		    		    scrollingPane.add(jcombo);
		            	i++;
		            }
		            
				}
				
			});
	        
	        contentPane.add(spinner);
	        ((DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
	        
	        lblNumOver = new JLabel("Number of over:");
	        lblNumOver.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
	        switch(SimGUI.fontSize) {
	        	case 14: lblNumOver.setBounds(154, 3, 105, 22); break;
	        	case 16: lblNumOver.setBounds(134, 3, 135, 22); break;
	        	case 18: lblNumOver.setBounds(124, 3, 150, 22); break;
	        	default: lblNumOver.setBounds(184, 3, 89, 22);
	        }
	        contentPane.add(lblNumOver);
	        
	        lblGeneric = new JLabel(":");
	        lblGeneric.setFont(new Font("Segoe UI", Font.PLAIN, SimGUI.fontSize));
	        lblGeneric.setHorizontalAlignment(SwingConstants.CENTER);
	        lblGeneric.setBounds(355, 44, 14, 18);
	        contentPane.add(lblGeneric);      
		}
}
