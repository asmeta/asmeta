/**
 * generated by Xtext 2.36.0
 */
package org.asmeta.xt.asmetal.impl;

import org.asmeta.xt.asmetal.AsmetalPackage;
import org.asmeta.xt.asmetal.BasicFunction;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Basic Function</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class BasicFunctionImpl extends FunctionImpl implements BasicFunction
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected BasicFunctionImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return AsmetalPackage.Literals.BASIC_FUNCTION;
  }

  public org.asmeta.xt.asmetal.FunctionDefinition getDefinition() {
  	org.asmeta.xt.asmetal.FunctionDefinition fDec = org.asmeta.xt.validation.utility.Utility.function_declarations_map.get(this);
	  
	if (fDec != null) 
		return fDec;
  	
  	org.asmeta.xt.asmetal.FunctionInitialization fInit = org.asmeta.xt.validation.utility.Utility.function_initialization_map.get(this);
  	
  	if (fInit != null) {  	
  		
  		fDec = org.asmeta.xt.validation.utility.Utility.function_init_to_def.get(fInit);
  		if (fDec != null) 
  			return fDec;
  		
  		fDec = org.asmeta.xt.asmetal.AsmetalFactory.eINSTANCE.createFunctionDefinition();
  		fDec.setBody(fInit.getBody());
  		fDec.setDefinedFunctionName(fInit.getInizializedFunctionName());
  		org.asmeta.xt.validation.utility.Utility.function_init_to_def.put(fInit, fDec);
  		// TODO: Completare con altre informazioni
  		return fDec;
  		
  	}
  	
  	return null; 
  }
} //BasicFunctionImpl
