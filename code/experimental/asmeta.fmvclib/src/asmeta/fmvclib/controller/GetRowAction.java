package asmeta.fmvclib.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.asmeta.simulator.value.IntegerValue;

import asmeta.fmvclib.model.AsmetaFMVCModel;

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
	 * Builds a new GetRowAction, for a location name on the specified model
	 * 
	 * @param locationName the location name
	 * @param model        the model
	 */
	public GetRowAction(String locationName, AsmetaFMVCModel model) {
		this.locationName = locationName;
		this.m_model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int row = Integer.valueOf(e.getActionCommand());
		this.m_model.getReader().addValue(this.locationName, new IntegerValue(row));
		this.m_model.runSimulator();
	}
}
