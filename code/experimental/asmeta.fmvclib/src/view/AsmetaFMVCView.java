package view;

import controller.RunStepListener;

/**
 * This interface is used to keep a link of the view inside the controller
 * 
 * @author Andrea Bombarda
 */
public interface AsmetaFMVCView {

	/**
	 * Adds a listener to a component of the view
	 * 
	 * @param runStepListener the listener to be added
	 */
	void addListener(RunStepListener runStepListener);

}
