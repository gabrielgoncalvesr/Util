package br.com.pdf;
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.DocumentException;

public class GeneratorPDF {
    public static void main(String[] args) throws DocumentException, IOException {
		PdfFormatter formatter = new PdfFormatter();

		List<String> listOne = new ArrayList<>();
		List<String> listTwo = new ArrayList<>();

		float sumValues = 0;
		
		formatter.createPDF("teste");
		
		formatter.createHeader();
		formatter.setSpacingTop(20f);
		formatter.insertRow("");
		
		for (int i = 10; i < 40; i++) {
			listOne.add("Ligação de 5511909099090 - Duração de " + i + "s");
			
			String value = "10." + i;
			listTwo.add("R$ " + value);
			
			sumValues += new Float(value);
		}
		
		float [] columns = {6, 2};
		
		formatter.createTable(columns);
		formatter.createTitleTable("Movimentações");
		formatter.createHeaderTable("Seriais", "Preço");
		formatter.createSeparatorLineTable(0f, 100f);
		formatter.insertLineTable(" ", " ");
		formatter.createBodyTable(listOne, listTwo);
		formatter.createSeparatorLineTable(0f, 100f);
		formatter.setFontCustom(1, 13f);
		formatter.insertLineTable("Total", "R$ " + String.valueOf(String.format("%.2f", sumValues)));
		formatter.generateTable();
		
		formatter.createFooterBarcode();
		
		formatter.closeFile();
    	
    }
}