package br.com.pdf;
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.DocumentException;

public class GeneratorPDF {
    public static void main(String[] args) throws DocumentException, IOException {
    	PdfFormatter formatter = new PdfFormatter();
    	
    	List<String> listString = new ArrayList();
    	List<String> header = new ArrayList();
    	
    	//create pdf
    	formatter.createPDF("teste");

    	//insert header in file
    	for (int i = 0; i < 2; i ++) {
    		header.add("teste");
    	}
    	formatter.createHeader(header);
    	
    	//insert one row
    	formatter.insertRow("HEADER DO PDF");
    	
    	//insert a list of string
    	for (int i = 0; i < 4; i ++) {
    		listString.add("aaaaaaa");
    	}
    	formatter.insertRows(listString);
    	
    	
    	
    	formatter.createTable();
    	formatter.createCellTable(listString);
    	
    	//close file
    	formatter.closeFile();
    }
}