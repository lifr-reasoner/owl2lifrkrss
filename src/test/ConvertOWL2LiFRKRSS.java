package test;

import create.LiFRsyntaxOWLtoKRSS;

public class ConvertOWL2LiFRKRSS {
	
	public static void main(String[] args){
		String filepath = "resources/";
		
		String infilename = "PROTEIN_ontology_v1.3.owl"; //"blah.owl";
		String outfilename = "protein1.3.krss"; //"blah.krss";
		
		//Select if you want Object Properties' domains and ranges to be included in export
		boolean domrang = true;
		
		//Select if you want full class IRIs to be exported, or if you want to omit the ontology IRI from the class name
		boolean noIRI = true;
		
		LiFRsyntaxOWLtoKRSS.convertOWLtoKRSS(filepath, infilename, filepath, outfilename, domrang, noIRI);
		
	}

}
