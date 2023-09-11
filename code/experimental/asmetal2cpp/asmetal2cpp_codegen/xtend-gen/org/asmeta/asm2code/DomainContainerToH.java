package org.asmeta.asm2code;

import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.EnumTd;
import asmeta.structure.Asm;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * TODO: delete this class -> replaced with DomanToH and FunctionToH
 */
@SuppressWarnings("all")
public class DomainContainerToH extends ReflectiveVisitor<String> {
  private Asm res;
  
  public DomainContainerToH(final Asm resource) {
    this.res = resource;
  }
  
  /**
   * Domain Signature
   */
  public String visit(final ConcreteDomain object) {
    return this.setDomainContainer(object.getName(), (object.getIsDynamic()).booleanValue());
  }
  
  /**
   * TODO: DELETE FOR COVERAGE 	def String visit(RuleDomain object) {
   * 		throw new ExceptionAsmetaToC("RuleDomain", true)
   * 	}
   * 
   * 	def String visit(ProductDomain object) {
   * 		var StringBuffer sb = new StringBuffer
   * 		sb.append('''tuple<''')
   * 		for (var int i = 0; i < object.domains.size; i++) {
   * 			if (object.domains.get(i) instanceof StructuredTd)
   * 				sb.append('''«visit(object.domains.get(i))», ''')
   * 			else
   * 				sb.append('''«new ToString(res).visit(object.domains.get(i))», ''')
   * 		}
   * 
   * 		return sb.toString.substring(0, sb.length - 2).concat(">")
   * 	}
   * 
   * 	def String visit(SequenceDomain object) {
   * 		var StringBuffer sb = new StringBuffer
   * 		sb.append('''list<''')
   * 		if (object.domain instanceof StructuredTd)
   * 			sb.append('''«visit(object.domain)»''')
   * 		else
   * 			sb.append('''«new ToString(res).visit(object.domain)»''')
   * 		return sb.toString.substring(0, sb.length).concat(">")
   * 	}
   * 
   * 	def String visit(PowersetDomain object) {
   * 		var StringBuffer sb = new StringBuffer
   * 		sb.append('''set<''')
   * 		if (object.baseDomain instanceof StructuredTd)
   * 			sb.append('''«visit(object.baseDomain)»''')
   * 		else
   * 			sb.append('''«new ToString(res).visit(object.baseDomain)»''')
   * 		return sb.toString.substring(0, sb.length).concat(">")
   * 	}
   * 
   * 	def String visit(BagDomain object) {
   * 		var StringBuffer sb = new StringBuffer
   * 		sb.append('''multiset<''')
   * 		if (object.domain instanceof StructuredTd)
   * 			sb.append('''«visit(object.domain)»''')
   * 		else
   * 			sb.append('''«new ToString(res).visit(object.domain)»''')
   * 		return sb.toString.substring(0, sb.length).concat(">")
   * 	}
   * 
   * 	def String visit(MapDomain object) {
   * 		var StringBuffer sb = new StringBuffer
   * 		sb.append('''map<''')
   * 		if (object.sourceDomain instanceof StructuredTd)
   * 			sb.append('''«visit(object.sourceDomain)»,''')
   * 		else
   * 			sb.append('''«new ToString(res).visit(object.sourceDomain)»,''')
   * 		if (object.targetDomain instanceof StructuredTd)
   * 			sb.append('''«visit(object.targetDomain)»,''')
   * 		else
   * 			sb.append('''«new ToString(res).visit(object.targetDomain)»''')
   * 		return sb.toString.substring(0, sb.length).concat(">")
   * 	}
   * 
   * 
   * 
   * 	def void visit(AnyDomain object) {
   * 		throw new ExceptionAsmetaToC("AnyDomain", true)
   * 	}
   * 
   * 
   * 
   * 	def String visit(AbstractTd object) {
   * 		return object.name
   * 	}
   * 
   * 	def String visit(BasicTd object) {
   * 		throw new ExceptionAsmetaToC("BasicDomain cannot be defined by the user")
   * 	}
   */
  public String visit(final EnumTd object) {
    return this.setDomainContainer(object.getName(), false);
  }
  
  public String setDomainContainer(final String name, final boolean isDynamic) {
    StringBuffer sb = new StringBuffer();
    if ((!isDynamic)) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("const ");
      sb.append(_builder);
    }
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("set<");
    _builder_1.append(name);
    _builder_1.append("> ");
    String _elemsSetName = Util.getElemsSetName(name);
    _builder_1.append(_elemsSetName);
    _builder_1.append(";");
    _builder_1.newLineIfNotEmpty();
    sb.append(_builder_1);
    return sb.toString();
  }
}
