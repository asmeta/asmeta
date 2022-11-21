package asmeta.fmvclib.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.asmeta.simulator.value.Value;

import asmeta.fmvclib.annotations.AsmetaControlledLocation;
import asmeta.fmvclib.annotations.AsmetaModelParameter;
import asmeta.fmvclib.annotations.AsmetaRunStep;
import asmeta.fmvclib.model.AsmetaFMVCModel;
import asmeta.fmvclib.view.AsmetaFMVCView;
import asmeta.fmvclib.view.RunStepListener;

/**
 * The AsmetaFMVCController is a controller to be used when the pattern fMVC is
 * chosen. It interacts with an ASMETA model and a custom view
 * 
 * @author Andrea Bombarda
 *
 */
public class AsmetaFMVCController implements Observer, RunStepListener {
	/**
	 * The model to be used
	 */
	private AsmetaFMVCModel m_model;

	/**
	 * The view to which the listener has to be attached
	 */
	private AsmetaFMVCView m_view;

	/**
	 * Builds a new controller to be used when the pattern fMVC is chosen
	 * 
	 * @param model the ASMETA model
	 * @param view  the view
	 */
	public AsmetaFMVCController(AsmetaFMVCModel model, AsmetaFMVCView view) {
		// Store the reference to the model and view
		m_model = model;
		m_view = view;

		// Attach the ActionListener to components annotated with @AsmetaRunStep
		List<Field> fieldList = FieldUtils.getFieldsListWithAnnotation(m_view.getClass(), AsmetaRunStep.class);
		for (Field f : fieldList) {
			f.setAccessible(true);
			m_view.addListener(this);
		}
		// The controller is used as observer for the model
		m_model.addObserver(this);
	}

	/**
	 * Method automatically called when the controller is modified by the model
	 */
	@Override
	public void update(Observable o, Object arg) {
		List<Field> fieldList = FieldUtils.getFieldsListWithAnnotation(m_view.getClass(),
				AsmetaControlledLocation.class);
		System.out.println(fieldList.size());
//		for (Field f : fieldList) {
//			f.setAccessible(true);
//			// First, get the value
//			String value = m_model.getValue(f.getAnnotation(AsmetaControlledLocation.class).asmLocationName());
//			try {
//				if (f.get(m_view) instanceof JTextField) {
//					((JTextField) (f.get(m_view))).setText(value);
//				} else {
//					if (f.get(m_view) instanceof JLabel) {
//						((JLabel) (f.get(m_view))).setText(value);
//					} else {
//						throw new RuntimeException("This type of component is not yet managed by the fMVC framework");
//					}
//				}
//			} catch (IllegalArgumentException | IllegalAccessException e) {
//				e.printStackTrace();
//			}
//		}
	}

	/**
	 * Listener when an action is performer
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			m_model.updateMonitored(m_view);
			m_model.runSimulator();
		} catch (IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
	}
}
