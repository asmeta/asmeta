package test;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;

import asmeta.definitions.domains.DomainsFactory;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.structure.Signature;
import asmeta.structure.StructureFactory;

/** test utile per capire come avere tutte le istance di una certa classe
 * in verit� non si riesce perch� EMF non tiene traccia delle istanze
 * 
 * @author AG
 *
 */
public class GetInstances {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Resource resource = new ResourceImpl();
		Signature s = StructureFactory.eINSTANCE.createSignature();		
		resource.getContents().add(s);		
		resource.setTrackingModification(true);
		IntegerDomain  i = DomainsFactory.eINSTANCE.createIntegerDomain();
		s.getDomain().add(i);
		System.out.println("contenuto in " + i.eContainer());
		TreeIterator<EObject> i1 = org.eclipse.emf.ecore.util.EcoreUtil.getAllContents(resource.getContents()); 
		for (;i1.hasNext();){
			EObject e = i1.next();
			System.out.println(e);
		}
	}

}
