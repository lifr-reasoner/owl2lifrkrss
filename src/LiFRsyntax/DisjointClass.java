package LiFRsyntax;

import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;

public class DisjointClass {
	
	public static String translateDisjoints(Set<OWLDisjointClassesAxiom> disjs) {
		String krssDisjoints = "";
		
		for(Iterator<OWLDisjointClassesAxiom> iter = disjs.iterator(); iter.hasNext();) {
			OWLDisjointClassesAxiom disj = iter.next().getAxiomWithoutAnnotations();
			
			//We handle those separately
//			if (disj.isGCI()) continue;
			
			krssDisjoints += translateDisjoint(disj);
		}
		
		return krssDisjoints;
	}
	
	public static String translateDisjoint(OWLDisjointClassesAxiom disjwithoutannotation) {
		String krssdisj = "";
		
//		System.out.println(eqvwithoutannotation.toString());
		
		krssdisj = "(DISJOINT ";
		
		//SIZE IS ALWAYS 2
		Set<OWLClassExpression> counterparts = disjwithoutannotation.getClassExpressions();
		
		for(Iterator<OWLClassExpression> iter = counterparts.iterator(); iter.hasNext();) {
			OWLClassExpression counterpart = iter.next();
//			System.out.println(counterpart.toString());
			
			ClassExpressionType counterparttype = counterpart.getClassExpressionType();
			
			String krsscounterpart = ComplexClass.processComplexClass(counterpart, counterparttype);
			
			krssdisj += (krsscounterpart + " ");
		}
		
		krssdisj = krssdisj.trim() + ")\n";
			
		return krssdisj;
	}

}
