package create;

import java.io.File;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import LiFRsyntax.DisjointClass;
import LiFRsyntax.EquivalentClass;
import LiFRsyntax.GCI;
import LiFRsyntax.ImplicationClass;
import LiFRsyntax.ObjectProperty;
import io.FileIO;

public class LiFRsyntaxOWLtoKRSS {
	
	public static void convertOWLtoKRSS(String infilepath, String infilename, String outfilepath, String outfilename, boolean domainrange, boolean omitIRIprefix) {
		File file = new File(infilepath + infilename);
		IRI physicalIRI = IRI.create(file);
		
		try {
			OWLOntology ontology = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(physicalIRI);
			
			IRI ontologyIRI = ontology.getOntologyID().getOntologyIRI();
			
			/** IMPLICATIONS **/
			Set<OWLSubClassOfAxiom> implications = ontology.getAxioms(AxiomType.SUBCLASS_OF);
			
			String krssImpls = "";
			
			if(implications.size() > 0) {
				krssImpls = ImplicationClass.translateImplications(implications).trim();
//				System.out.println(krssImpls);
			}
			
			/** EQUIVALENCES **/
			Set<OWLEquivalentClassesAxiom> equivalences = ontology.getAxioms(AxiomType.EQUIVALENT_CLASSES);
			
			String krssEqvs = "";
			
			if(equivalences.size() > 0) {
				krssEqvs = EquivalentClass.translateEquivalences(equivalences).trim();
//				System.out.println(krssEqvs);
			}
			
			/** DISJOINTS **/
			Set<OWLDisjointClassesAxiom> disjoints = ontology.getAxioms(AxiomType.DISJOINT_CLASSES);
			
			String krssDisjs = "";
			
			if(disjoints.size() > 0) {
				krssDisjs = DisjointClass.translateDisjoints(disjoints).trim();
//				System.out.println(krssDisjs);
			}
			
			/** GCIs **/
			Set<OWLClassAxiom> gcis = ontology.getGeneralClassAxioms();
			
			String krssGCIs = "";
			
			if(gcis.size() > 0) {
				krssGCIs = GCI.translateGCIs(gcis).trim();
//				System.out.println(krssGCIs);
			}
			
			/** ROLE AXIOMS **/
			Set<OWLObjectProperty> roles = ontology.getObjectPropertiesInSignature();
			
			String krssRoles = "";
			
			if(roles.size() > 0) {
				krssRoles = ObjectProperty.translateRoles(roles, ontology, domainrange);
//				System.out.println(krssRoles);
			}
			
			if(omitIRIprefix) {
				String IRIprefix = ontologyIRI.toString();
				
				//Another way to check is: if the ontologyIRI ends with /, then no need to add any other char
				//else (since if OWL ontology IRIs end without a trailing slash, class IRIs start with a "#" to separate them from ontology IRI, 
				//so then add the "#" at the end.
				//This form was elected because it was noted that if you initially have a no-trailing-slash IRI in Protege
				//but then the you change the IRI to one with a trailing slash,
				//Protege will maintain the previously established "#" in class IRIs, like this: http(s)://myontoloIRI/#Classname
				if(krssImpls.contains("#")) IRIprefix = IRIprefix + "#";
				
				if(krssImpls.length() > 0) krssImpls = krssImpls.replaceAll(IRIprefix, ""); 
				if(krssEqvs.length() > 0) krssEqvs = krssEqvs.replaceAll(IRIprefix, "");
				if(krssDisjs.length() > 0) krssDisjs = krssDisjs.replaceAll(IRIprefix, "");
				if(krssGCIs.length() > 0) krssGCIs = krssGCIs.replaceAll(IRIprefix, "");
				if(krssRoles.length() > 0) krssRoles = krssRoles.replaceAll(IRIprefix, "");
				
			}
			
			/** WRITE KRSS FILE **/
//			FileIO.appendToFile("", outfilepath, outfilename, true);
			//initialize the file here, since an ontology is unlikely to not have any implications
			if(krssImpls.length() > 0) FileIO.appendToFile(krssImpls, outfilepath, outfilename, true); 
			if(krssEqvs.length() > 0) FileIO.appendToFile(krssEqvs, outfilepath, outfilename, false);
			if(krssDisjs.length() > 0) FileIO.appendToFile(krssDisjs, outfilepath, outfilename, false);
			if(krssGCIs.length() > 0) FileIO.appendToFile(krssGCIs, outfilepath, outfilename, false);
			if(krssRoles.length() > 0) FileIO.appendToFile(krssRoles, outfilepath, outfilename, false);
			
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
	}
	
}
