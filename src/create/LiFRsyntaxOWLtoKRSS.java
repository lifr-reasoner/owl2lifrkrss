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
	
	public static void convertOWLtoKRSS(String infilepath, String infilename, String outfilepath, String outfilename, boolean domainrange) {
		File file = new File(infilepath + infilename);
		IRI physicalIRI = IRI.create(file);
		
		try {
			OWLOntology ontology = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(physicalIRI);
			
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
			//boolean: take into account domain/range
				krssRoles = ObjectProperty.translateRoles(roles, ontology, domainrange);
				System.out.println(krssRoles);
			}
			
			/** WRITE KRSS **/
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
