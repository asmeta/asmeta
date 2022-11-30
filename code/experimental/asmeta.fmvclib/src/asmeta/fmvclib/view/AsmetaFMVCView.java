package asmeta.fmvclib.view;

/**
 * This interface is used to keep a link of the view inside the controller
 * 
 * @author Andrea Bombarda
 */
public interface AsmetaFMVCView {

	/**
	 * Method used for refreshing the view
	 */
	void refreshView(boolean firstTime);

}
