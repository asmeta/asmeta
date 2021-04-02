package org.asmeta.simulationUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;

import org.asmeta.assertion_catalog.InvariantGUI;
import org.asmeta.assertion_catalog.LoadComboItem;
import org.asmeta.assertion_catalog.LoadDialog;
import org.asmeta.parser.ASMParser;
import org.asmeta.runtime_container.SimulationContainer;
import org.asmeta.runtime_container.Esit;
import org.asmeta.runtime_container.RunOutput;

import asmeta.AsmCollection;
import asmeta.definitions.impl.MonitoredFunctionImpl;

import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import java.awt.event.WindowFocusListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.awt.event.WindowEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

/**
 * @author Federico Rebucini, Hernan Altamirano, Daniele Troiano
 */
public class SimGUI extends JFrame {

	private JPanel contentPane;
	private JTextPane textPaneID;
	private JTextPane textPaneModel;
	private JButton btnStop;
	private JButton btnRunstep;
	private JButton btnRunstepTimeout;
	private JButton btnRununtilempty;
	private JButton rununtilemptytimeout;
	static SimulationContainer containerInstance;
	static int currentLoadedID;
	static int currentMaxInstances;
	static String currentLoadedModel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		SimulationContainer contInstance = new SimulationContainer();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimGUI frame = new SimGUI(contInstance);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SimGUI(SimulationContainer contInstance) {
		setResizable(false);
		initialize();
		enableLoadSimButtons(false);
		containerInstance=contInstance;
		currentLoadedID=-99;
		currentLoadedModel="";
		currentMaxInstances=0;
		}
	
	
	private void initialize()
	{
		PrintStream previousConsole = System.out;
		 
        // Set the standard output to use newConsole.
        ByteArrayOutputStream newConsole = new ByteArrayOutputStream();
		
		
		setTitle("Simulator Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 862, 545);
		
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent arg0) {
				if (currentLoadedID>0) {
					boolean changed=true;
					Map<Integer,String> ids = containerInstance.getLoadedIDs();
					if (ids!=null && ids.containsKey(currentLoadedID))
						if (ids.get(currentLoadedID).equals(currentLoadedModel))
							changed=false;
					if (changed) {
						enableLoadSimButtons(false);
						textPaneModel.setText("No simulation loaded");
						textPaneID.setText("X");
						currentLoadedID=-99;
						currentLoadedModel="";
						JOptionPane.showMessageDialog(null, "Previously loaded simulation was terminated or changed ID externally");	
					}
				}
			}
			public void windowLostFocus(WindowEvent arg0) {
			}
		});
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton choose = new JButton("Choose simulation");
		choose.setBounds(649, 22, 170, 71);
		contentPane.add(choose);
		
		JButton invmanager = new JButton("Show assertion catalog");
		invmanager.setBounds(649, 142, 170, 71);
		contentPane.add(invmanager);
		
		JButton runstep = new JButton("RunStep");
		runstep.setBounds(97, 365, 188, 40);
		btnRunstep=runstep;
		contentPane.add(runstep);
		
		JButton rununtilempty = new JButton("RunUntilEmpty");
		rununtilempty.setBounds(375, 365, 188, 40);
		btnRununtilempty=rununtilempty;
		contentPane.add(rununtilempty);
		
		JLabel lblModel = new JLabel("Loaded model:");
		lblModel.setHorizontalAlignment(SwingConstants.LEFT);
		lblModel.setBounds(47, 22, 94, 16);
		contentPane.add(lblModel);
		
		JTextPane textPaneModel = new JTextPane();
		textPaneModel.setText("No simulation loaded");
		textPaneModel.setBounds(47, 45, 456, 22);
		textPaneModel.setEditable(false);
		this.textPaneModel = textPaneModel;
		contentPane.add(textPaneModel);

		JLabel lblSimID = new JLabel("Simulation ID:");
		lblSimID.setHorizontalAlignment(SwingConstants.LEFT);
		lblSimID.setBounds(519, 22, 94, 16);
		contentPane.add(lblSimID);
		
		JTextPane textPaneID = new JTextPane();
		textPaneID.setText("X");
		textPaneID.setBounds(519, 45, 78, 22);
		textPaneID.setEditable(false);
		this.textPaneID=textPaneID;
		contentPane.add(textPaneID);
		
		JButton btnStop = new JButton("Stop simulation");
		btnStop.setBounds(649, 251, 170, 71);
		btnStop.setEnabled(false);
		this.btnStop=btnStop;
		contentPane.add(btnStop);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(47, 80, 566, 272);
		contentPane.add(scrollPane);
		
		JTextArea textAreaLog = new JTextArea();
		textAreaLog.setLineWrap(true);
		textAreaLog.setEditable(false);
		DefaultCaret caret = (DefaultCaret)textAreaLog.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		scrollPane.setViewportView(textAreaLog);
		
		JButton runStepTimeout = new JButton("RunStep timeout");
		runStepTimeout.setEnabled(false);
		runStepTimeout.setBounds(97, 418, 188, 40);
		this.btnRunstepTimeout=runStepTimeout;
		contentPane.add(runStepTimeout);
		
		JButton rununtilemptytimeout = new JButton("RunUntilEmpty timeout");	
		rununtilemptytimeout.setEnabled(false);
		rununtilemptytimeout.setBounds(375, 418, 188, 40);
		this.rununtilemptytimeout=rununtilemptytimeout;
		contentPane.add(rununtilemptytimeout);
		
		
		
		
		
		
		choose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentMaxInstances<1)
				{
				String num=JOptionPane.showInputDialog("How many simulations do you want to instantiate?");
					if(num!=null)
					{
						try {
							if(Integer.parseInt(num)>0)
								currentMaxInstances=containerInstance.init(Integer.parseInt(num));
						} catch (NumberFormatException e1) {
							JOptionPane.showMessageDialog(null, "Not a valid number.");
						}
					}
				}
				if(currentMaxInstances>=1)
				{
					Map<Integer, String> ids = containerInstance.getLoadedIDs();
				
		    		LoadComboItem ci=null;
		    		if (!ids.isEmpty()) {
		    			// setAllEnabled(1);
		    			ci = new LoadDialog(containerInstance,ids).showDialog();
		    			//JOptionPane.showMessageDialog(null, ci.getStr());
		    		}else
		    		{
		    			LoadDialog ld = new LoadDialog(containerInstance,ids);
		    			ld.disablebutton();
		    			ci=new LoadDialog(containerInstance,ids).showDialog();
		    			
		    		}
		    		if (ci!=null) {
		    			currentLoadedID = ci.getInt();
		    			currentLoadedModel = ci.getStr();
		    			textPaneID.setText(""+currentLoadedID);
		    			//textPaneModel.setText(currentLoadedModel.substring(currentLoadedModel.lastIndexOf("\\")+1));
		    			if (currentLoadedModel.indexOf("\\")>=0)
		    				textPaneModel.setText(currentLoadedModel.substring(currentLoadedModel.lastIndexOf("\\")+1));
		    			else
		    				textPaneModel.setText(currentLoadedModel.substring(currentLoadedModel.lastIndexOf("\\")+1));
		    			enableLoadSimButtons(true);
		    			textAreaLog.setText("Simulation ready.\n");
		    			/*JTextPane jj=(JTextPane)contentPane.getComponent(1);
		    			jj.setText(currentLoadedModel);
		    			jj=(JTextPane)contentPane.getComponent(2);*/
					    }
				}
			}
		});
		
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentLoadedID>0) {
					int stop = containerInstance.stopExecution(currentLoadedID);
					if (stop==-1)
						JOptionPane.showMessageDialog(null, "Error: Couldn't stop simulation");
					else {
						currentLoadedID=-99;
						currentLoadedModel="";
						enableLoadSimButtons(false);
						textPaneModel.setText("No simulation loaded");
						textPaneID.setText("X");
						textAreaLog.append("Simulation stopped.\n");
					}
				}else
					JOptionPane.showMessageDialog(null, "Error: no simulation selected");
			}
		});
		
		invmanager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentLoadedID<1)
					JOptionPane.showMessageDialog(null, "Error: no simulation selected");
				else
				    new InvariantGUI(containerInstance,currentLoadedID,currentLoadedModel).setVisible();
			}
		});
		
		btnRununtilempty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.setErr(new PrintStream(newConsole));
				System.setOut(new PrintStream(newConsole));
				List<String> monitored = getMonitored();
				RunOutput out=new RunOutput(Esit.UNSAFE, "rout not intialized");
				if (monitored.size()<1)
					out=containerInstance.runUntilEmpty(currentLoadedID);
				else {
					Map<String, String> input = getInput(monitored);
					out=containerInstance.runUntilEmpty(currentLoadedID, input);
				}
				//JOptionPane.showMessageDialog(null, out.toString());	
				//textAreaLog.append("Runstep executed with current result:\n"+out.MytoString()+"\n");
				previousConsole.println(newConsole.toString()); // Display output of newConsole.
				 
		        // Restore back the standard console output.
		        System.setOut(previousConsole);
		        System.setErr(previousConsole);
		        textAreaLog.append("");
		        textAreaLog.append(newConsole.toString());
		        newConsole.reset();
			}
		});
		
		rununtilemptytimeout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.setErr(new PrintStream(newConsole));
				System.setOut(new PrintStream(newConsole));
				List<String> monitored = getMonitored();
				RunOutput out=new RunOutput(Esit.UNSAFE, "rout not intialized");
				int timeout=-1;
				String num=JOptionPane.showInputDialog("Insert timeout (milliseconds):");
				if(num!=null)
				{
					try {
						timeout=Integer.parseInt(num);
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(null, "Not a valid number.");
					}
				}
				if (timeout!=-1) {
					if (monitored.size()<1)
						out=containerInstance.runUntilEmptyTimeout(currentLoadedID,timeout);
					else {
						Map<String, String> input = getInput(monitored);
						out=containerInstance.runUntilEmptyTimeout(currentLoadedID, input,timeout);
					}
					//JOptionPane.showMessageDialog(null, out.toString());	
					//textAreaLog.append("Runstep with timeout executed with current result:\n"+out.MytoString()+"\n");
				} else
					textAreaLog.append("Couldn't execute operation.\n");
				previousConsole.println(newConsole.toString()); // Display output of newConsole.
				 
		        // Restore back the standard console output.
		        System.setOut(previousConsole);
		        System.setErr(previousConsole);
		        textAreaLog.append("");
		        textAreaLog.append(newConsole.toString());
		        newConsole.reset();
			}
		});
		
		runstep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.setErr(new PrintStream(newConsole));
				System.setOut(new PrintStream(newConsole));
				List<String> monitored = getMonitored();
				RunOutput out=new RunOutput(Esit.UNSAFE, "rout not intialized");
				if (monitored.size()<1)
					out=containerInstance.runStep(currentLoadedID);
				else {
					Map<String, String> input = getInput(monitored);
					out=containerInstance.runStep(currentLoadedID, input);
				}
				//JOptionPane.showMessageDialog(null, out.toString());	
				//textAreaLog.append("Runstep executed with current result:\n"+out.MytoString()+"\n");
				 
		        previousConsole.println(newConsole.toString()); // Display output of newConsole.
		 
		        // Restore back the standard console output.
		        System.setOut(previousConsole);
		        System.setErr(previousConsole);
		        textAreaLog.append("");
		        textAreaLog.append(newConsole.toString());
		        newConsole.reset();
			}
		});
		
		runStepTimeout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.setErr(new PrintStream(newConsole));
				System.setOut(new PrintStream(newConsole));
				List<String> monitored = getMonitored();
				RunOutput out=new RunOutput(Esit.UNSAFE, "rout not intialized");
				int timeout=-1;
				String num=JOptionPane.showInputDialog("Insert timeout (milliseconds):");
				if(num!=null)
				{
					try {
						timeout=Integer.parseInt(num);
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(null, "Not a valid number.");
					}
				}
				if (timeout!=-1) {
					if (monitored.size()<1)
						out=containerInstance.runStepTimeout(currentLoadedID,timeout);
					else {
						Map<String, String> input = getInput(monitored);
						out=containerInstance.runStepTimeout(currentLoadedID, input,timeout);
					}
					//JOptionPane.showMessageDialog(null, out.toString());	
					//textAreaLog.append("Runstep with timeout executed with current result:\n"+out.MytoString()+"\n");
				} else
					textAreaLog.append("Couldn't execute operation.\n");
				previousConsole.println(newConsole.toString()); // Display output of newConsole.
				 
		        // Restore back the standard console output.
		        System.setOut(previousConsole);
		        System.setErr(previousConsole);
		        textAreaLog.append("");
		        textAreaLog.append(newConsole.toString());
		        newConsole.reset();
			}
		});
	}
	private void enableLoadSimButtons(boolean enable) {
		btnStop.setEnabled(enable);
		btnRunstep.setEnabled(enable);
		btnRununtilempty.setEnabled(enable);
		btnRunstepTimeout.setEnabled(enable);
		rununtilemptytimeout.setEnabled(enable);
		/*contentPane.getComponent(2).setEnabled(enable);
		contentPane.getComponent(3).setEnabled(enable);
		contentPane.getComponent(9).setEnabled(enable);*/
	}
	private List<String> getMonitored(){
		ArrayList<String> monitoredList = new ArrayList<String>();
		if (!currentLoadedModel.equals("")) {
			File asmFile = new File(currentLoadedModel);
			if (asmFile.exists()) {
				AsmCollection asm;
				try {
					asm = ASMParser.setUpReadAsm(asmFile);// cerco di prendere la classe delle monitorate
					for (int i = 0; i < asm.getMain().getHeaderSection().getSignature().getFunction().size(); i++) {
						if (asm.getMain().getHeaderSection().getSignature().getFunction()
								.get(i) instanceof MonitoredFunctionImpl)
							monitoredList.add(asm.getMain().getHeaderSection().getSignature().getFunction().get(i).getName());
				
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error");
				}			
			}
		}
		return monitoredList;
	}
	private Map<String, String> getInput(List<String> monitored) {
		Map<String, String> input = new HashMap<String, String>();
		for (String m: monitored) {
			String user = JOptionPane.showInputDialog("Insert "+m+" value:");
			input.put(m, user);
		}
		return input;
	}
}



