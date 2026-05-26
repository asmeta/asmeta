package org.asmeta.xt.validation.utility;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

@SuppressWarnings("all")
public class ErrorType extends MessageType {
  public ErrorType(final String msg, final EStructuralFeature feature, final String code) {
    this(msg, feature, code, null);
  }

  public ErrorType(final String msg, final EStructuralFeature feature, final String code, final EObject error_obj) {
    super(msg, feature, code, error_obj);
  }
}
