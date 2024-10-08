package LiFRsyntax;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;

public class ComplexClass {
	
	public static String processComplexClass(OWLClassExpression axiomclass, ClassExpressionType axiomtype) {
		String complex = "";
		
		if(axiomtype.equals(ClassExpressionType.OWL_CLASS)) {
			complex = PlainClass.processPlainClass(axiomclass);
		}else if(axiomtype.equals(ClassExpressionType.OBJECT_ALL_VALUES_FROM)) {
			OWLObjectAllValuesFrom allcoxclass = (OWLObjectAllValuesFrom) axiomclass;
			Set<OWLClass> forallclasses = allcoxclass.getClassesInSignature();
			Set<OWLObjectProperty> forallrole = allcoxclass.getObjectPropertiesInSignature();
			
			//only one role/object property assumed
			String outrole = forallrole.iterator().next().asOWLObjectProperty().toStringID();
			if(forallclasses.size() > 1) {
				complex = "(ALL " + outrole + " ";
				
				OWLClassExpression morecomplex = allcoxclass.getFiller();
				ClassExpressionType moretype = morecomplex.getClassExpressionType();
//				System.out.println(morecomplex.toString());
				
				complex += processComplexClass(morecomplex, moretype);
				
				complex = complex.trim() + ")";
			}else {
				String outclass = PlainClass.processPlainClass(forallclasses.iterator().next().asOWLClass());
				complex = "(ALL " + outrole + " " + outclass + ")";
			}
		}else if(axiomtype.equals(ClassExpressionType.OBJECT_SOME_VALUES_FROM)) {
			OWLObjectSomeValuesFrom somecoxclass = (OWLObjectSomeValuesFrom) axiomclass;
			Set<OWLClass> someclasses = somecoxclass.getClassesInSignature();
			Set<OWLObjectProperty> somerole = somecoxclass.getObjectPropertiesInSignature();
			
			//only one role/object property assumed
			String outrole = somerole.iterator().next().asOWLObjectProperty().toStringID();
			if(someclasses.size() > 1) {
				complex = "(SOME " + outrole + " ";
				
				OWLClassExpression morecomplex = somecoxclass.getFiller();
				ClassExpressionType moretype = morecomplex.getClassExpressionType();
//				System.out.println(morecomplex.toString());
				
				complex += processComplexClass(morecomplex, moretype);
				
				complex = complex.trim() + ")";
			}else {
				String outclass = PlainClass.processPlainClass(someclasses.iterator().next().asOWLClass());
				complex = "(SOME " + outrole + " " + outclass + ")";
			}
		}else if(axiomtype.equals(ClassExpressionType.OBJECT_INTERSETION_OF)) {
			OWLObjectIntersectionOf andcoxclass = (OWLObjectIntersectionOf) axiomclass;
			Set<OWLClassExpression> conjuncts = andcoxclass.asConjunctSet();
			boolean complexconjunct = false;
			ArrayList<String> finalconjuncts = new ArrayList<String>();
			
//			if(andclasses.size() > 1) {
			if(conjuncts.size() > 1) {
				complex = "(AND ";
				
				for(Iterator<OWLClassExpression> iter = conjuncts.iterator(); iter.hasNext();) {
					OWLClassExpression expression = iter.next();
					ClassExpressionType type = expression.getClassExpressionType();
					if(type.equals(ClassExpressionType.OBJECT_SOME_VALUES_FROM)) {
						
						String somecomplex = "";
						
						OWLObjectSomeValuesFrom somecoxclass = (OWLObjectSomeValuesFrom) expression;
						Set<OWLClass> someclasses = somecoxclass.getClassesInSignature();
						Set<OWLObjectProperty> somerole = somecoxclass.getObjectPropertiesInSignature();
						
						//only one role/object property assumed
						String outrole = somerole.iterator().next().asOWLObjectProperty().toStringID();
						if(someclasses.size() > 1) {
							somecomplex = "(SOME " + outrole + " ";
							
							OWLClassExpression morecomplex = somecoxclass.getFiller();
							ClassExpressionType moretype = morecomplex.getClassExpressionType();
//							System.out.println(morecomplex.toString());
							
							somecomplex += processComplexClass(morecomplex, moretype);
							
							somecomplex = somecomplex.trim() + ")";
						}else {
							String outclass = PlainClass.processPlainClass(someclasses.iterator().next().asOWLClass());
							somecomplex = "(SOME " + outrole + " " + outclass + ")";
						}
						
						finalconjuncts.add(somecomplex);
						
					}else if(type.equals(ClassExpressionType.OWL_CLASS)) {
						String plainclass = PlainClass.processPlainClass(expression);
						finalconjuncts.add(plainclass);
					}else {
						Set<OWLClass> andclasses = andcoxclass.getClassesInSignature();
						
						for(Iterator<OWLClass> iterunknown = andclasses.iterator(); iter.hasNext();) {
							OWLClass plainclass = iterunknown.next();
							finalconjuncts.add(PlainClass.processPlainClass(plainclass));
						}
					}
				}
				
//				Set<OWLClass> andclasses = andcoxclass.getClassesInSignature();
//				}
//				
//				for(Iterator<OWLClass> iter = andclasses.iterator(); iter.hasNext();) {
//					OWLClass plainclass = iter.next();
//					complex += (PlainClass.processPlainClass(plainclass) + " ");
//				}
				
				for(Iterator<String> iter = finalconjuncts.iterator(); iter.hasNext();) {
					String conj = iter.next();
					
					complex += (conj + " ");
				}
				complex = complex.trim() + ")";
			}else {
				//TODO wtf?
			}
		}else if(axiomtype.equals(ClassExpressionType.OBJECT_UNION_OF)) {
			OWLObjectUnionOf orcoxclass = (OWLObjectUnionOf) axiomclass;
			Set<OWLClass> orclasses = orcoxclass.getClassesInSignature();
			if(orclasses.size() > 1) {
				complex = "(OR ";
				for(Iterator<OWLClass> iter = orclasses.iterator(); iter.hasNext();) {
					OWLClass plainclass = iter.next();
					complex += (PlainClass.processPlainClass(plainclass) + " ");
				}
				complex = complex.trim() + ")";
			}else {
				//TODO wtf?
			}
		}else if(axiomtype.equals(ClassExpressionType.OBJECT_COMPLEMENT_OF)) {
			OWLObjectComplementOf notcoxclass = (OWLObjectComplementOf) axiomclass;
			Set<OWLClass> notclasses = notcoxclass.getClassesInSignature();
			if(notclasses.size() > 1) {
				complex = "(NOT ";
				OWLClassExpression morecomplex = notcoxclass.getComplementNNF();
				ClassExpressionType moretype = morecomplex.getClassExpressionType();
//				System.out.println(morecomplex.toString());
				
				complex += processComplexClass(morecomplex, moretype);
				complex = complex.trim() + ")";
			}else {
				complex = "(NOT ";
				complex += (notclasses.iterator().next().toStringID());
				complex = complex.trim() + ")";
			}
		}else {
			System.err.println(axiomclass.toString());
		} //LiFR wouldn't support anything else - DOUBLECHECK IF CORRECT
		
		
		return complex;
	}

}
