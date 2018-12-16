package br.com.pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfFormatter {
	private String route = "C:\\Users\\gabri\\Desktop\\PDF\\";
	private String extension =  ".pdf";
	private Document document;
	private Paragraph paragraph;
	private PdfPTable table;
	private PdfPCell cell;
	private int countPagesFile;
	
    public void createPDF(String nameFile) {
    	document = new Document();
    	countPagesFile = 0;
    	
    	try {
            PdfWriter.getInstance(document, new FileOutputStream(route + nameFile + extension));
            document.setMargins(50, 50, 50, 50);
            document.open();
        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
        }
        catch(IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }
    
    public void insertRow(String row) throws DocumentException {
    	List<String> listRows = new ArrayList();
    	listRows.add(row);
    	insertRows(listRows);
    }
    
    public void insertRows(List<String> rows) throws DocumentException {
    	for (String row : rows) {
    		paragraph = new Paragraph();
        	paragraph.setSpacingAfter(20);
        	paragraph.add(row);
    		document.add(paragraph);
    	}
    }
    
    public void setTypeSize(String typePage) {
    	if(typePage.equals("A4")) {
    		document.setPageSize(PageSize.A4);
    	} else {
    		document.setPageSize(PageSize.A3);
    	}
    }
    
    public void createNewPage() {
    	document.newPage();
    	countPagesFile++;
    }
    
    public void createTable() {
    	table = new PdfPTable(1);
    }
    
    public void createCellTable(List<String> list) throws IOException, DocumentException {
    	cell = new PdfPCell();
    	
    	for (String stringCell : list) {
    		table.addCell(stringCell);
    	}
    	
    	document.add(table);
    }
    
    public void customHTML() {
    	StringBuilder sb = new StringBuilder();
        sb.append("<div>\n<p align=\"center\">");
        sb.append("<font size=\"5\">");
        sb.append("<b>&nbsp;<font color=\"#32cd32\">My centered Para</font></b>");
        sb.append("</font>");
        sb.append("<font color=\"#32cd32\">&nbsp;</font>");
        sb.append("</p>\n</div>");
    }
    
    public void createHeader(List<String> header) throws DocumentException {
    	for (String row : header) {
    		document.add(new Paragraph(row));
    	}
    	insertRow("__________________________________________________________________________");
    }
    
    public void setKeyWord(String keyword) {
    	document.addKeywords(keyword);
    }
    
    public void setSubject(String subject) {
    	document.addSubject(subject);
    }
    
    public void setCreator(String creator) {
    	document.addCreator(creator);
    }
    
    public void setAuthor(String author) {
    	document.addAuthor(author);
    }
    
    public void closeFile() {
    	document.close();
    }
}