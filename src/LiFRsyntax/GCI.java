package LiFRsyntax;

import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

public class GCI {
	
	public static String translateGCIs(Set<OWLClassAxiom> gcis) {
		String krssGCIs = "";
		
		for(Iterator<OWLClassAxiom> iter = gcis.iterator(); iter.hasNext();) {
			OWLClassAxiom gci = iter.next();
//			System.out.println(gci.toString());
			
//			System.out.println(gci.getAxiomType().getName());
			
			@SuppressWarnings("rawtypes")
			AxiomType type = gci.getAxiomType();
			if(type.equals(AxiomType.SUBCLASS_OF)) {
				OWLSubClassOfAxiom axiom = (OWLSubClassOfAxiom) gci.getAxiomWithoutAnnotations();
////				System.out.println(axiom);
				
				krssGCIs += ImplicationClass.translateImplication(axiom);
			}else if(type.equals(AxiomType.EQUIVALENT_CLASSES)) {
				//TODO 
			}else if(type.equals(AxiomType.DISJOINT_CLASSES)) {
				//TODO 
			}else continue; //LIFR doesn't support other types of GCIs atm
		}
		
		return krssGCIs;
	}
	
}
