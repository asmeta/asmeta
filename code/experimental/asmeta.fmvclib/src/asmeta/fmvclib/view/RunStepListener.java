package asmeta.fmvclib.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * RunStepListener is used for performing a single step in the simulator. First,
 * it updates the monitored functions in the ASMETA model. Then, it simulates
 * the specification.
 * 
 * @author Andrea Bombarda
 *
 */
public interface RunStepListener extends ActionListener {

//	/**
//	 * The model to be used
//	 */
//	private AsmetaFMVCModel m_model;
//
//	/**
//	 * The view to which the listener has to be attached
//	 */
//	private AsmetaFMVCView m_view;
//
//	/**
//	 * Builds a new RunStepListener
//	 * 
//	 * @param m_model the ASMETA model
//	 * @param m_view  the custom view
//	 */
//	public RunStepListener(AsmetaFMVCModel m_model, AsmetaFMVCView m_view) {
//		super();
//		this.m_model = m_model;
//		this.m_view = m_view;
//	}
//
//	/**
//	 * Handler of the click on a button
//	 */
//	public void actionPerformed(ActionEvent e) {
//		try {
//			m_model.updateMonitored(m_view);
//			m_model.runSimulator();
//			//updateControlled(m_view);
//		} catch (IllegalArgumentException | IllegalAccessException e1) {
//			e1.printStackTrace();
//		}
//	}
}
