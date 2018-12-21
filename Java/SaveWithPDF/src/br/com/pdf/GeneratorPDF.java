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

		formatter.createPDF("teste");
		
		formatter.createHeader();
		formatter.setSpacingTop(20f);
		formatter.insertRow("");
		
		for (int i = 10; i < 40; i++) {
			listOne.add("Ligação de 5511909099090 - Duração de " + i + "s");
			listTwo.add("R$ 10,0" + i);
		}
		
		float [] columns = {6, 2};
		
		formatter.createTable(columns);
		formatter.createTitleTable("Movimentações");
		formatter.createSeparatorLineTable(0f, 100f);
		formatter.createHeaderTable("Seriais", "Preço");
		formatter.spacingTopTable(80f);
		formatter.createBodyTable(listOne, listTwo);
		formatter.createSeparatorLineTable(0f, 100f);
		formatter.setFontCustom(1, 14f);
		formatter.createLineTable("Total", "R$ 300,735");
		formatter.generateTable();
		
		formatter.setSpacingTop(50f);
		formatter.setSpacingBottom(0f);
		formatter.setFontCustom(1, 11f);
		formatter.insertRow("32131.1.213111511135.1313131515.31563115515.31631531515.1515");
		formatter.setSpacingTop(0f);
		formatter.createSeparatorLine(0f, 100f);
		formatter.setPositionImage(45f, 10f);
		formatter.setSizeImage(500f, 60f);
		formatter.addImage("codigo_barra.jpg");
		
		formatter.closeFile();
    	
    }
}