package LiFRsyntax;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;

public class ObjectProperty {
	
	public static String translateRoles(Set<OWLObjectProperty> roles, OWLOntology ontology, boolean domainrange) {
		String krssRolesFull = "";
		String krssRolesClean = "";
		
		for(Iterator<OWLObjectProperty> iter = roles.iterator(); iter.hasNext();) {
			OWLObjectProperty role = iter.next();
			
			krssRolesFull += translateRole(role, ontology, domainrange);
		}
		
		//DELETE DUPLICATES
		String[] all = krssRolesFull.split("\n");
		ArrayList<String> clean = new ArrayList<String>();
		
		for(int i = 0; i < all.length; i++) {
			String axiom = all[i];
			
			if(!clean.contains(axiom)) {
				clean.add(axiom);
			}
		}
		
		for(Iterator<String> iter = clean.iterator(); iter.hasNext();) {
			String finalaxiom = iter.next();
			
			krssRolesClean += (finalaxiom + "\n");
		}
		
		return krssRolesClean;
	}
	
	public static String translateRole(OWLObjectProperty role, OWLOntology ontology, boolean domainrange) {
		String krssroles = "";
		
		//******(ROLE r :PARENT sp :INVERSE si :TRANSITIVE :SYMMETRIC)******
		
		boolean symmetric = false; //role.isSymmetric(ontology);
		boolean transitive = false; //role.isTransitive(ontology);
		
		Set<OWLSubObjectPropertyOfAxiom> supers = ontology.getObjectSubPropertyAxiomsForSuperProperty(role);
		Set<OWLInverseObjectPropertiesAxiom> inverses = ontology.getInverseObjectPropertyAxioms(role);
		
		if(inverses.size() > 0) {
			for(Iterator<OWLInverseObjectPropertiesAxiom> iter = inverses.iterator(); iter.hasNext();) {
				OWLInverseObjectPropertiesAxiom axiom = iter.next();
//				System.out.println(axiom.toString());
				
				OWLObjectPropertyExpression firstexpr = axiom.getFirstProperty();
				OWLObjectPropertyExpression inverseexpr = axiom.getSecondProperty();
			
				OWLObjectProperty first = firstexpr.asOWLObjectProperty();
				OWLObjectProperty inverse = inverseexpr.asOWLObjectProperty();
				
				String krssrole = "(ROLE " + first.toStringID() + " :INVERSE " + inverse.toStringID() + ")\n";
				
				krssroles += krssrole;
			}
		}
		
		if(supers.size() > 0) {
			for(Iterator<OWLSubObjectPropertyOfAxiom> iter = supers.iterator(); iter.hasNext();) {
				OWLSubObjectPropertyOfAxiom axiom = iter.next();
			
				OWLObjectPropertyExpression subroles = axiom.getSubProperty();
				
				OWLObjectProperty subrole = subroles.asOWLObjectProperty();
					
				String krssrole = "(ROLE " + subrole.toStringID() + " ";
					
				krssrole += (":PARENT " + role.toStringID() + ")\n"); 
					
				krssroles += krssrole;
			}
		}
		
		if(transitive) {
			krssroles += ("(ROLE " + role.toStringID() + " :TRANSITIVE)\n");
		}
		
		if(symmetric) {
			krssroles += ("(ROLE " + role.toStringID() + " :SYMMETRIC)\n");
		}
		if(domainrange) {
			krssroles += translateDomainRange(role, ontology);
		}
		
		return krssroles;
	}
	
	
	public static String translateDomainRange(OWLObjectProperty role, OWLOntology ontology) {
		String krssroledomainsranges = "";
		
		Set<OWLObjectPropertyDomainAxiom> domains = ontology.getObjectPropertyDomainAxioms(role);
		Set<OWLObjectPropertyRangeAxiom> ranges = ontology.getObjectPropertyRangeAxioms(role);
		
		if(domains.size() > 0) {
			//******(DOMAIN R C)******
			for(Iterator<OWLObjectPropertyDomainAxiom> iter = domains.iterator(); iter.hasNext();) {
				OWLObjectPropertyDomainAxiom axiom = iter.next();
				
				OWLClassExpression domainclassexpr = axiom.getDomain();
				
				Set<OWLClass> domainclasses = domainclassexpr.getClassesInSignature();
				
				for(Iterator<OWLClass> domiter = domainclasses.iterator(); domiter.hasNext();) {
					OWLClass domainc = domiter.next();
					
					String krssrole = "(DOMAIN " + role.toStringID() + " ";
			
					String domainClass = domainc.toStringID();
			
					krssrole += (domainClass + ")\n");
				
					krssroledomainsranges += krssrole;
				}
			}
		}
		if(ranges.size() > 0) {
			//******(RANGE R C)******
			for(Iterator<OWLObjectPropertyRangeAxiom> iter = ranges.iterator(); iter.hasNext();) {
				OWLObjectPropertyRangeAxiom axiom = iter.next();
				
				OWLClassExpression rangeclassexpr = axiom.getRange();
				
				Set<OWLClass> rangeclasses = rangeclassexpr.getClassesInSignature();
				
				for(Iterator<OWLClass> raniter = rangeclasses.iterator(); raniter.hasNext();) {
					OWLClass rangec = raniter.next();
				
					String krssrole = "(RANGE " + role.toStringID() + " ";
			
					String rangeClass = rangec.toStringID();
				
					krssrole += (rangeClass + ")\n");
				
					krssroledomainsranges += krssrole;
				}
			}
			
		}
			
		return krssroledomainsranges;
	}

}
