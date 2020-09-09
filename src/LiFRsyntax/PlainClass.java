package LiFRsyntax;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;

public class PlainClass {
	
	public static String processPlainClass(OWLClass origclass) {
		String outclass = "";
		
		if(origclass.isOWLNothing()) {
			outclass = "BOTTOM";
		}else if(origclass.isOWLThing()) {
			outclass = "TOP";
		}else {
			outclass = origclass.toStringID();
		}
		
		return outclass;
	}
	
	public static String processPlainClass(OWLClassExpression origclass) {
		String outclass = "";
		
		if(origclass.isOWLNothing()) {
			outclass = "BOTTOM";
		}else if(origclass.isOWLThing()) {
			outclass = "TOP";
		}else {
			outclass = ((OWLClass) origclass).toStringID();
		}
		
		return outclass;
	}

}
