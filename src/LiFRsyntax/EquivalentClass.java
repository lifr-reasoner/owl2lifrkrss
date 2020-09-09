package LiFRsyntax;

import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;

public class EquivalentClass {
	
	public static String translateEquivalences(Set<OWLEquivalentClassesAxiom> eqvs) {
		String krssEquivalences = "";
		
		for(Iterator<OWLEquivalentClassesAxiom> iter = eqvs.iterator(); iter.hasNext();) {
			OWLEquivalentClassesAxiom eqv = iter.next().getAxiomWithoutAnnotations();
			
			//We handle those separately
//			if (eqv.isGCI()) continue;
			
			krssEquivalences += translateEquivalence(eqv);
		}
		
		return krssEquivalences;
	}
	
	public static String translateEquivalence(OWLEquivalentClassesAxiom eqvwithoutannotation) {
		String krsseqv = "";
		
//		System.out.println(eqvwithoutannotation.toString());
		
		krsseqv = "(EQUIVALENT ";
		
		//SIZE IS ALWAYS 2
		Set<OWLClassExpression> counterparts = eqvwithoutannotation.getClassExpressions();
		
		for(Iterator<OWLClassExpression> iter = counterparts.iterator(); iter.hasNext();) {
			OWLClassExpression counterpart = iter.next();
//			System.out.println(counterpart.toString());
			
			ClassExpressionType counterparttype = counterpart.getClassExpressionType();
			
			String krsscounterpart = ComplexClass.processComplexClass(counterpart, counterparttype);
			
			krsseqv += (krsscounterpart + " ");
		}
		
		krsseqv = krsseqv.trim() + ")\n";
			
		return krsseqv;
	}

}
