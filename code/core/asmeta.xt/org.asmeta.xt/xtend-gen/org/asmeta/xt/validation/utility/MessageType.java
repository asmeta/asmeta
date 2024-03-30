package org.asmeta.xt.validation.utility;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

@SuppressWarnings("all")
public abstract class MessageType {
  private String message;

  private String code;

  private EStructuralFeature feature;

  private EObject obj;

  public MessageType(final String msg, final EStructuralFeature feature, final String code) {
    this(msg, feature, code, null);
  }

  public MessageType(final String msg, final EStructuralFeature feature, final String code, final EObject error_obj) {
    this.message = msg;
    this.feature = feature;
    this.code = code;
    this.obj = error_obj;
  }

  public String getMsg() {
    return this.message;
  }

  public String getCode() {
    return this.code;
  }

  public EStructuralFeature getFeature() {
    return this.feature;
  }

  public EObject getObj() {
    return this.obj;
  }

  @Override
  public String toString() {
    return ("Error: " + this.message);
  }
}
