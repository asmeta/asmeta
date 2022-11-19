package asmeta.fmvclib.controller;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;

import asmeta.fmvclib.annotations.AsmetaRunStep;
import asmeta.fmvclib.model.AsmetaFMVCModel;
import asmeta.fmvclib.view.AsmetaFMVCView;

/**
 * The AsmetaFMVCController is a controller to be used when the pattern fMVC is
 * chosen. It interacts with an ASMETA model and a custom view
 * 
 * @author Andrea Bombarda
 *
 */
public class AsmetaFMVCController {
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
			m_view.addListener(new RunStepListener(m_model, m_view));
		}
	}
}
