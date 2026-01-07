package asmeta.mutation;

import java.io.File;
import java.util.List;

import org.asmeta.parser.ASMParser;
import org.eclipse.emf.common.util.EList;

import asmeta.AsmCollection;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.structure.Asm;
import asmeta.structure.DomainDefinition;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;

public class ProvaEstraidomini {

	public static void main(String[] args) throws Exception {
		AsmCollection asm = ASMParser.setUpReadAsm(new File("examples\\chooseToLet.asm"));
		estraiDominio(asm.getMain());
	}

	static void estraiDominio(Asm asm) {
		EList<Domain> dde = asm.getHeaderSection().getSignature().getDomain();
		for (Domain dd : dde)
			estraiElement(dd, asm);
	}

	static void estraiElement(Domain s, Asm asm) {
		EList<DomainDefinition> ddefs = asm.getBodySection().getDomainDefinition();
		for (DomainDefinition dd : ddefs) {
			if (dd.getDefinedDomain().getName().equals(s.getName())) {
				System.out.println(dd.getDefinedDomain().getName());
				System.out.println(dd.getBody());
				System.out.println(random((SetTerm) dd.getBody()));
				return;
			}
		}
		System.out.println(s);
		System.out.println(random((EnumTd)s));
	}

	private static char[] random(EnumTd s) {
		s.getElement();
		return null;
	}

	static Term random(SetTerm st) {
		int s = st.getTerm().size();
		if (s == 0)
			throw new RuntimeException();
		return st.getTerm().getFirst();
	}

}
