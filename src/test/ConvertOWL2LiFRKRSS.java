package test;

import create.LiFRsyntaxOWLtoKRSS;

public class ConvertOWL2LiFRKRSS {
	
	public static void main(String[] args){
		String filepath = "resources/";
		
		String infilename = "PROTEIN_ontology_v1.3.owl";
		String outfilename = "protein1.3.krss";
		
		//Select if you want Object Properties' domains and ranges to be included in export
		boolean domrang = true;
		
		LiFRsyntaxOWLtoKRSS.convertOWLtoKRSS(filepath, infilename, filepath, outfilename, domrang);
		
	}

}
