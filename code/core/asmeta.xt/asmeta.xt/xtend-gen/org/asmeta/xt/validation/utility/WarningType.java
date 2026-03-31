package org.asmeta.xt.validation.utility;

import org.eclipse.emf.ecore.EStructuralFeature;

@SuppressWarnings("all")
public class WarningType extends MessageType {
  private Integer position;

  public WarningType(final String msg, final EStructuralFeature feature, final String code) {
    this(msg, feature, code, Integer.valueOf((-1)));
    this.position = Integer.valueOf((-1));
  }

  public WarningType(final String msg, final EStructuralFeature feature, final String code, final Integer position) {
    super(msg, feature, code);
    this.position = position;
  }

  public Integer getPosition() {
    return this.position;
  }
}
