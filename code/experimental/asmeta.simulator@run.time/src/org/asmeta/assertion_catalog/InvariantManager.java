package org.asmeta.assertion_catalog;

import java.io.File;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.asmeta.runtime_container.IModelAdaptation;
import org.asmeta.runtime_container.InvariantData;

/**
 * @author Federico Rebucini, Hernan Altamirano, Daniele Troiano
 * Controller class for InvariantGUI
 */
public class InvariantManager {
	
	JFileChooser fileChooser = new JFileChooser();
	StringBuilder sb = new StringBuilder();
	static String model = "";

	File file;
	
	Scanner input;
	
	public InvariantData refreshInvariants(IModelAdaptation instance, int id) throws Exception
	{
		//List<String> final_list = new ArrayList<String>();
		/*SimulationContainer imp = SimulationContainer.getInstance();
		imp.init(1);
		int id = imp.startExecution(model);*/
		if(id>0)
		{
			InvariantGUI.setAddRefreshEnabled();
			return /*final_list = */instance.viewListInvariant(id);
		}
		else {
			return null;
		}
	}
	
	public String chooseModel() throws Exception {
		if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			file = fileChooser.getSelectedFile();
			input = new Scanner(file);
			
			while(input.hasNext()) {
				sb.append(input.nextLine());
				sb.append("\n");
			}
			model = file.getAbsolutePath();
			input.close();
		}
		else
		{
			sb.append("");
		}
		return sb.toString();
	}
	
	public static int addInvariant(String s, IModelAdaptation instance, int id) throws Exception {
		//boolean add_result=true;
		int check=0;
		/*SimulationContainer imp = SimulationContainer.getInstance();
		imp.init(1);
		int id = imp.startExecution(model);*/
		check = instance.addInvariant(id,s);
		if(check>0)
		{
			InvariantGUI.showInvariants();
			//InvariantGraphicsInterface.setAllEnabled(1);
			InvariantGUI.setAddRefreshEnabled();
		}
		else {
				if(check==-8)
					JOptionPane.showMessageDialog(null, "Variable is already taken");
				else if(check==-7)
					JOptionPane.showMessageDialog(null, "Cannot add the invariant as it is violated in the current state");
				else if(check==-5)
					JOptionPane.showMessageDialog(null, "Invalid Invariant");
				else if(check==-1)
					JOptionPane.showMessageDialog(null, "Simulation was previously terminated");
				//add_result=false;
		}
		return check;
	}
	
	public static int updateInvariant(String new_invariant, String old_invariant, IModelAdaptation instance, int id) throws Exception {
		   int result;
		   /*SimulationContainer imp = SimulationContainer.getInstance();
		   imp.init(1);
		   int id = imp.startExecution(model);*/
		   result = instance.updateInvariant(id,new_invariant,old_invariant);
		  // boolean check;
		   if(result > 0)
			{
			  // check=true;
				InvariantGUI.showInvariants();
				InvariantGUI.setAllEnabled(1);
			}
			else {
				//check=false;
				if(result == -8)
					JOptionPane.showMessageDialog(null, "Variable is already taken");
				else if(result==-7)
					JOptionPane.showMessageDialog(null, "Cannot add the invariant as it is violated in the current state");
				else if(result==-5)
					JOptionPane.showMessageDialog(null, "Invalid Invariant");
				else if (result==-1)
					JOptionPane.showMessageDialog(null, "Simulation was previously terminated");
				else
					JOptionPane.showMessageDialog(null, "An error has occurred - failed to rename");
			}
		   return result;
		  }
	
	
	
	public static void removeInvariant(String s,IModelAdaptation instance,int id) throws Exception {
		boolean result;
		/*SimulationContainer imp = SimulationContainer.getInstance();
		imp.init(1);
		int id = imp.startExecution(model);*/
		result = instance.removeInvariant(id,s);
		if(result)
		{
			InvariantGUI.showInvariants();
			InvariantGUI.setAllEnabled(1);
		}
		else {
			JOptionPane.showMessageDialog(null, "Simulation was previously terminated");
			InvariantGUI.showInvariants();
		}
	}
	
	public String getModel() 
	{
		return model;
	}
	
	public void setModel(String model) {
		InvariantManager.model = model;
	}
	
	
}
