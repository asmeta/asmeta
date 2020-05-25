package org.asmeta.assertion_catalog;

import java.awt.EventQueue;

import org.asmeta.runtime_container.MyTimerTask;
import org.asmeta.test.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.UIManager;

public class Frame {

	private JFrame frame;
	static List<String> list_invariant = new ArrayList<String>();
	static DefaultListModel<String> model = new DefaultListModel<>();
	static GuiTest StartGui = new GuiTest();
	static String selected_invariant;
	boolean success;
	Font font = new Font(Font.DIALOG, 15, 15);
	
	//////////////COMPONENTS//////////////////////
	static JList<String> list = new JList<>();
	static JButton edit = new JButton("EDIT",new ImageIcon(Frame.class.getResource("/org/asmeta/animator/edit.png")));
	static JButton refresh = new JButton("REFRESH",new ImageIcon(Frame.class.getResource("/org/asmeta/animator/refresh.png")));
	static JTextPane modelpath = new JTextPane();
	static JButton remove = new JButton("REMOVE",new ImageIcon(Frame.class.getResource("/org/asmeta/animator/remove.png")));
	static JButton add = new JButton("ADD",new ImageIcon(Frame.class.getResource("/org/asmeta/animator/add.png")));
	static JButton upload = new JButton("UPLOAD MODEL",new ImageIcon(Frame.class.getResource("/org/asmeta/animator/upload.png")));
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame window = new Frame();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Frame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 752, 490);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Invariant Manager");
		
		/////////SET VISIBILITY OF BUTTONS
		setAllEnabled(0);
		
		
		/////////SET COMPONENTS ON THE FRAME
		modelpath.setEditable(false);
		modelpath.setBounds(35, 25, 458, 28);
		modelpath.setFont(font);
		frame.getContentPane().add(modelpath);
		refresh.setEnabled(false);
		refresh.setBounds(557, 352, 134, 40);
		frame.getContentPane().add(refresh);
		remove.setBounds(212, 352, 134, 40);
		frame.getContentPane().add(remove);
		
		
		add.setBounds(35, 352, 134, 40);
		
		frame.getContentPane().add(add);
		
		
		
		edit.setBounds(387, 352, 134, 40);
		frame.getContentPane().add(edit);
		list.setToolTipText("");
		list.setBounds(25, 80, 676, 247);
		frame.getContentPane().add(list);
		upload.setBounds(505, 13, 186, 54);
		frame.getContentPane().add(upload);
		
		
		
		/////////SET BUTTONS ACTIONS
		upload.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent e) {
			    try {
			     StartGui.chooseModel();
			     String checkmodel = StartGui.getModel();
			     //modelpath.setText(StartGui.getModel());
			     if(!checkmodel.isEmpty() && checkmodel.indexOf(".asm")!=-1 && showInvariants())
			     {
			      add.setEnabled(true);
			      refresh.setEnabled(true);
			      modelpath.setText(StartGui.getModel());
			     }
			     if(checkmodel.indexOf(".asm")==-1 && !checkmodel.isEmpty())
			    	JOptionPane.showMessageDialog(null, "WRONG EXTENSION");
			    }
			    catch (Exception ex) {
			     if(ex instanceof java.io.FileNotFoundException)
			      JOptionPane.showMessageDialog(null, "NO FILE SELECTED");
			     else if(ex instanceof java.lang.NullPointerException) 
			    		JOptionPane.showMessageDialog(null, "AN ERROR HAS OCCURRED");  	
			     else{
			      ex.printStackTrace();
			     }
			    }
			   }
			  });
		
		
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {			
					new AddUpdateDialog("").setVisible(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new Removedialog(selected_invariant).setVisible(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new AddUpdateDialog(selected_invariant).setVisible(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showInvariants();
			}
		});

	}
	
	public static boolean showInvariants() {
		boolean success = true;
		try {
			setAllEnabled(1);
			model.removeAllElements();
			list_invariant = StartGui.refreshInvariants();
			System.out.println(list_invariant);
			if(list_invariant==null)
			{
				success=false;
				add.setEnabled(false);
				refresh.setEnabled(false);
			}
			
			list.setModel(model);
			
			int i=0;
			while(i<list_invariant.size())
			{
				model.addElement(list_invariant.get(i));
				i++;
			}
			list.getSelectionModel().addListSelectionListener(new ListSelectionListener () {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					// TODO Auto-generated method stub
					selected_invariant = list.getSelectedValue();
					edit.setEnabled(true);
					remove.setEnabled(true);
				}
				});
			setAllEnabled(1);
		     } catch (Exception ex) {
				if(ex instanceof java.io.FileNotFoundException)
					JOptionPane.showMessageDialog(null, "NO FILE SELECTED");
				else if(ex instanceof java.lang.NullPointerException) {
					success=false;
					if(!modelpath.getText().isEmpty())
						JOptionPane.showMessageDialog(null, "AN ERROR HAS OCCURRED");
					modelpath.setText("");
					setAllEnabled(1);
				}
				else {
					ex.printStackTrace();
				}
			}
		return success;
	}
	
	public static void setAllEnabled(int val) {
		edit.setEnabled(false);
		if(val==0) {
			refresh.setEnabled(false);
			add.setEnabled(false);
		}
		remove.setEnabled(false);
	}
	
	
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
