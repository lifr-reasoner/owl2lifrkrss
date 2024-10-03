package test;

import create.LiFRsyntaxOWLtoKRSS;

public class ConvertOWL2LiFRKRSS {
	
	public static void main(String[] args){
		String filepath = "resources/";
		
		String infilename = "NAct.owl";//"NAct_v1.9.1.owl";//"NAct_v1.5-incon.owl";//"PROTEIN_ontology_v1.3.owl"; //"blah.owl";
		String outfilename = "NAct.krss";//"nact_1.9.1.krss";//"nact_1.5_incon.krss";//"protein1.3.krss"; //"blah.krss";
		
		//Select if you want Object Properties' domains and ranges to be included in export
		boolean domrang = false;
		
		//Select if you want full class IRIs to be exported, or if you want to omit the ontology IRI from the class name
		boolean noIRI = true;
		
		LiFRsyntaxOWLtoKRSS.convertOWLtoKRSS(filepath, infilename, filepath, outfilename, domrang, noIRI);
		
	}

}
