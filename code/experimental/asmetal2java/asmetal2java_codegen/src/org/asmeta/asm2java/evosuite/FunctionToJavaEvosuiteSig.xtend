package org.asmeta.asm2java.evosuite

import asmeta.definitions.DerivedFunction
import asmeta.definitions.StaticFunction
import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.domains.ProductDomain
import asmeta.structure.Asm
import asmeta.definitions.domains.PowersetDomain
import asmeta.definitions.domains.SequenceDomain
import asmeta.definitions.domains.BagDomain
import asmeta.definitions.domains.MapDomain
import org.asmeta.asm2java.translator.FunctionToJavaSig

class FunctionToJavaEvosuiteSig extends FunctionToJavaSig  {


	new(Asm resource) {
		super(resource)
	}
	
	/**
	 * Create an instance of the {@code DomainToJavaEvosuiteSigDef} object.
	 */
	override DomainToJavaEvosuiteSigDef createDomainToJavaSigDef(Asm resource) {
		new DomainToJavaEvosuiteSigDef(resource)
	}
	
	/**
	 * Create an instance of the {@code ToString} object.
	 */
	override ToStringEvosuite createToString(Asm resource) {
		new ToStringEvosuite(resource)
	}

    
    /**
     * Method to build static function.
     * Calls DomainToJavaEvosuiteSigDef instead of DomainToJavaSigDef.
     */
	override String visit(StaticFunction object) {
		
		var StringBuffer function = new StringBuffer
		function.append('''//Funzione di tipo statico
		 ''')
		
		//Caso di studio in cui la funzione ha solo il codominio, quindi non presenta variabili in ingresso alla funzione
		if (object.domain === null) {
			
			
			if (object.codomain instanceof AbstractTd)
			    function.append('''private static «returnDomain(object.codomain,true)» «object.name»;
				''')
			   
			//Metodo per tradurre i comandi di tipo: static nomeFunzione: Prod(Tipo1,Tipo2,...)
			else if(object.codomain instanceof ProductDomain)
			    function.append('''private static «createDomainToJavaSigDef(res).visit(object.codomain)» «object.name»;
			    ''') 
			    
		    //Metodo per tradurre i comandi di tipo: static nomeFunzione: Seq(Tipo)
			else if(object.codomain instanceof SequenceDomain)
			    function.append('''private static List«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name» = new ArrayList«createDomainToJavaSigDef(res).visit(object.codomain)»();
			    ''')
			    
			//Metodo per tradurre i comandi di tipo: static nomeFunzione: Powerset(Tipo)
			else if(object.codomain instanceof PowersetDomain)
			    function.append('''private static Set«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name» = new HashSet«createDomainToJavaSigDef(res).visit(object.codomain)»();
			    ''')
			    
			//Metodo per tradurre i comandi di tipo: static nomeFunzione: Bag(Tipo)
			else if(object.codomain instanceof BagDomain)
			    function.append('''private static Bag«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name» = new HashBag«createDomainToJavaSigDef(res).visit(object.codomain)»();
			    ''')
			    
		    //Metodo per tradurre i comandi di tipo: static nomeFunzione: Map(Tipo1,Tipo2)
			else if(object.codomain instanceof MapDomain)
			    function.append('''private static Map«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name» = new HashMap«createDomainToJavaSigDef(res).visit(object.codomain)»();
			    ''')
			
			else {
				//function.append('''abstract «returnDomain(object.codomain,false)» «object.name»();''')
			}
		} 
		
		//Nel caso opposto rilevo il codominio, quindi studio la funzione con ingresso e uscita
		/*else {
			if (object.domain instanceof ProductDomain)
				function.
					append('''abstract «returnDomain(object.codomain,false)» «object.name» («adaptProdDomain(object.domain as ProductDomain, object.name, true)»);
					''')
		    else if (object.domain instanceof SequenceDomain)
				function.
					append('''abstract ArrayList«returnDomain(object.codomain,false)» «object.name» (ArrayList«returnParamDefinition(object.domain, object.name,true)»);
					''')
			else{
				function.
					append('''abstract «returnDomain(object.codomain,false)» «object.name» («returnParamDefinition(object.domain, object.name,true)»);
					''')
			}
		}*/
		return function.toString
	}

	override String visit(DerivedFunction object) {
		
		var StringBuffer function = new StringBuffer
		
		function.append('''//Funzione di tipo derived
		 ''')
		
		if (object.domain === null) {
			
			
		if (object.codomain instanceof AbstractTd)
			
			function.append('''«returnDomain(object.codomain,true)» «object.name»;
			''')
			
		//Metodo per tradurre i comandi di tipo: derived nomeFunzione: Prod(Tipo1,Tipo2,...)
		else if(object.codomain instanceof ProductDomain)
		    function.append('''«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name»;
		    ''') 
		    
		//Metodo per tradurre i comandi di tipo: derived nomeFunzione: Seq(Tipo)
		else if(object.codomain instanceof SequenceDomain)
		    function.append('''List«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name» = new ArrayList«createDomainToJavaSigDef(res).visit(object.codomain)»();
		    ''')
			    
		//Metodo per tradurre i comandi di tipo: derived nomeFunzione: Powerset(Tipo)
		else if(object.codomain instanceof PowersetDomain)
		    function.append('''Set«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name» = new HashSet«createDomainToJavaSigDef(res).visit(object.codomain)»();
		    ''')
		    
		//Metodo per tradurre i comandi di tipo: derived nomeFunzione: Bag(Tipo)
		else if(object.codomain instanceof BagDomain)
		    function.append('''Bag«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name» = new HashBag«createDomainToJavaSigDef(res).visit(object.codomain)»();
		    ''')
		    
		//Metodo per tradurre i comandi di tipo: derived nomeFunzione: Map(Tipo1,Tipo2)
		else if(object.codomain instanceof MapDomain)
		    function.append('''Map«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name» = new HashMap«createDomainToJavaSigDef(res).visit(object.codomain)»();
		    ''')
			
			/* 
			else
			function.append('''abstract «returnDomain(object.codomain,false)» «object.name»();
			''')
		} else {
			if (object.domain instanceof ProductDomain)
				function.
					append('''abstract «returnDomain(object.codomain,true)» «object.name» («adaptProdDomain(object.domain as ProductDomain, object.name,true)»);
					''')
			else
				function.
					append('''abstract «returnDomain(object.codomain,true)» «object.name» («returnParamDefinition(object.domain, object.name,true)»);
					''')*/
		}
		return function.toString
	}

	
}