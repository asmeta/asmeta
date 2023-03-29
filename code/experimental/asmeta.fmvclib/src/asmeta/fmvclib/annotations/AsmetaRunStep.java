package asmeta.fmvclib.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * JButtons annotated with AsmetaRunStep are attached with an ActionListener
 * that updates the monitored functions and perform a single simulation step
 * 
 * @author Andrea Bombarda
 *
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface AsmetaRunStep {
	/**
	 * If it is true, it fires a repaint of the view
	 * 
	 * @return whether the view is repainted after step execution
	 */
	public boolean repaintView() default false;
}
