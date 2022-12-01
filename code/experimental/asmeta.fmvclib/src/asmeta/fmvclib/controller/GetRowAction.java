package asmeta.fmvclib.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.asmeta.simulator.value.IntegerValue;

import asmeta.fmvclib.model.AsmetaFMVCModel;
import asmeta.fmvclib.view.AsmetaFMVCView;

/**
 * Action used for get the number of the row selected when a ButtonColumn is
 * used
 * 
 * @author Andrea Bombarda
 *
 */
@SuppressWarnings("serial")
public class GetRowAction extends AbstractAction {

	/**
	 * The associated location name
	 */
	String locationName;

	/**
	 * The ASMETA model
	 */
	AsmetaFMVCModel m_model;
	
	/**
	 * The ASMETA view
	 */
	AsmetaFMVCView m_view;

	/**
	 * Builds a new GetRowAction, for a location name on the specified model
	 * 
	 * @param locationName the location name
	 * @param model        the model
	 */
	public GetRowAction(String locationName, AsmetaFMVCModel model, AsmetaFMVCView m_view) {
		this.locationName = locationName;
		this.m_model = model;
		this.m_view = m_view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int row = Integer.valueOf(e.getActionCommand());
		try {
			this.m_model.updateMonitored(m_view, null);
			this.m_model.getReader().addValue(this.locationName, new IntegerValue(row));
			System.out.println("LOCATION MEMORY: " + this.m_model.getReader().getLocationMemory());
			this.m_model.runSimulator();
		} catch (IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
	}
}
