package LiFRsyntax;

import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

public class ImplicationClass {
	
	public static String translateImplications(Set<OWLSubClassOfAxiom> impls) {
		String krssImplications = "";
		
		for(Iterator<OWLSubClassOfAxiom> iter = impls.iterator(); iter.hasNext();) {
			OWLSubClassOfAxiom impl = iter.next().getAxiomWithoutAnnotations();
			
			//We handle those separately
			if (impl.isGCI()) continue;
			
			krssImplications += translateImplication(impl);
		}
		
		return krssImplications;
	}
	
	public static String translateImplication(OWLSubClassOfAxiom implwithoutannotation) {
		String krssimpl = "";
			
		OWLClassExpression superclass = implwithoutannotation.getSuperClass();
		OWLClassExpression subclass = implwithoutannotation.getSubClass();
//		System.out.println("SUPER: " + superclass);
//		System.out.println("SUB: " + subclass);
			
		//******(IMPLIES SUB SUPER)******
		krssimpl = "(IMPLIES ";
			
		ClassExpressionType supertype = superclass.getClassExpressionType();
		ClassExpressionType subtype = subclass.getClassExpressionType();
//		System.out.println(supertype);
//		System.out.println(subtype);
			
		String krsssuper = ComplexClass.processComplexClass(superclass, supertype);
		String krsssub = ComplexClass.processComplexClass(subclass, subtype);
			
		krssimpl += (krsssub + " " + krsssuper + ")\n");
			
//		System.out.println(krssimpl);
			
		return krssimpl;
	}

}
