package org.asmeta.asm2code

import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.domains.AnyDomain
import asmeta.definitions.domains.BagDomain
import asmeta.definitions.domains.BasicTd
import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.domains.EnumTd
import asmeta.definitions.domains.MapDomain
import asmeta.definitions.domains.PowersetDomain
import asmeta.definitions.domains.ProductDomain
import asmeta.definitions.domains.RuleDomain
import asmeta.definitions.domains.SequenceDomain
import asmeta.definitions.domains.StructuredTd
import asmeta.definitions.domains.impl.StructuredTdImpl
import asmeta.structure.Asm
import org.asmeta.parser.util.ReflectiveVisitor
import asmeta.definitions.domains.Domain
import org.asmeta.asm2code.main.TranslatorOptions
import asmeta.definitions.domains.RealDomain
import asmeta.structure.ImportClause

class ImportToH extends ReflectiveVisitor<String> {
	
	Asm res;
	TranslatorOptions options;
	
	new(Asm resource){
		this.res = resource
		this.options = new TranslatorOptions(true,false,false,false)
	}
	
	def String visit(ImportClause ic){
		return ic.moduleName
	}

}