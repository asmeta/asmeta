package org.asmeta.assertion_catalog;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.asmeta.parser.ParseException;
import org.asmeta.runtime_container.FullMapException;
import org.asmeta.runtime_container.IModelExecution;
import org.asmeta.simulator.main.AsmModelNotFoundException;
import org.asmeta.simulator.main.MainRuleNotFoundException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import java.awt.Font;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class LoadDialog extends JDialog {
	static InvariantManager StartGui = new InvariantManager();
	private JPanel contentPane;
	static JButton btnLoad = new JButton("Load");
	/**
	 * Create the frame.
	 */
	private LoadComboItem ret=null;
	public LoadComboItem showDialog() {
		setVisible(true);
		return ret;
	}

	public LoadDialog(IModelExecution containerInstance,Map<Integer, String> ids) {
		setResizable(false);
		setModal(true);
		setTitle("Load simulation");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 212);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox.setBounds(36, 56, 360, 30);
		for(Map.Entry<Integer, String> i : ids.entrySet()) {
		   comboBox.addItem(new LoadComboItem(i.getKey(),i.getValue()));
	    }
		contentPane.add(comboBox);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		btnCancel.setBounds(182, 127, 97, 25);
		contentPane.add(btnCancel);
		

		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ret=(LoadComboItem)comboBox.getSelectedItem();
				setVisible(false);
				dispose();
			}
		});
		btnLoad.setBounds(56, 127, 97, 25);
		contentPane.add(btnLoad);
		
		JLabel lblLabel = new JLabel("Loaded simulations:");
		lblLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblLabel.setBounds(36, 26, 153, 16);
		contentPane.add(lblLabel);
		
		JButton upload = new JButton("Upload");
		upload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					StartGui.chooseModel();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    String checkmodel = StartGui.getModel();
			    if(!checkmodel.isEmpty() && checkmodel.indexOf(".asm")!=-1)
			     {
			    	int id=-99;
					try {
						id = containerInstance.startExecution(checkmodel);
					} catch (MainRuleNotFoundException | AsmModelNotFoundException | FullMapException
							| ParseException e1) {
						JOptionPane.showMessageDialog(null, "DEBUG: Something went wrong with Exceptions management"); 
						/*
						e1.printStackTrace();
						*/
					}
			    	if(id>0){
			    		comboBox.addItem(new LoadComboItem(id,checkmodel));
			    		btnLoad.setEnabled(true);
			    	}else if (id==-2)
			    			//TODO GESTIONE ERRORI
			    		JOptionPane.showMessageDialog(null, "Error: Main rule not found"); 
			    	else if (id==-3)
			    		JOptionPane.showMessageDialog(null, "Error: The model doesn't exist"); 
			    	else if (id==-4)
			    		JOptionPane.showMessageDialog(null, "Error: The simulator map is full"); 
			    	else if (id==-5)
			    		JOptionPane.showMessageDialog(null, "Error: The model contains errors");
			    	else
			    		JOptionPane.showMessageDialog(null, "Error"); 
			    	
			    	//JOptionPane.showMessageDialog(null, checkmodel);
			     }
			     if(checkmodel.indexOf(".asm")==-1 && !checkmodel.isEmpty()) {
			    	JOptionPane.showMessageDialog(null, "Error: Wrong extension");
			    }
			     
			    
			}
		});
		upload.setBounds(317, 128, 89, 23);
		contentPane.add(upload);
		
	}
	public void disablebutton() {
		btnLoad.setEnabled(false);
	}
	
}


