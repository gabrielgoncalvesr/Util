package br.com.pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator; 

public class PdfFormatter {
	private String route = "resources/pdf/";
	private String extension = ".pdf";
	private Document document;
	private Paragraph paragraph;
	private PdfPTable table;
	private PdfPCell cell;
	private Font font;
	private Image image;
	private int countPages;
	
	private float columnWidths[];
	
	private float widthImage;
	private float heightImage;
	
	private float widthPositionImage;
	private float heightPositionImage;
	
	private int typeFont;
	private float widthText;
	
	private float size;
	
	private float spacingTop;
	private float spacingBottom;

	//UTIL
	public void createPDF(String nameFile) {
		document = new Document();
		countPages = 0;
		
		widthImage = 100;
		heightImage = 100;
		
		widthText = 0;
		
		typeFont = 1;
		size = 12;

		spacingTop = 10;
		spacingBottom = 10;
		
		try {
			PdfWriter.getInstance(document, new FileOutputStream(route + nameFile + extension));
			document.setMargins(50, 50, 50, 50);
			document.open();
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}

	public void closeFile() {
		document.close();
	}
	
	public void setTypeSize(String typePage) {
		if (typePage.equals("A4")) {
			document.setPageSize(PageSize.A4);
		} else {
			document.setPageSize(PageSize.A3);
		}
	}
	
	public void createHeader() throws DocumentException, MalformedURLException, IOException {
		setPositionImage(10f, 735f);
		addImage("logo_lyra.jpg");
		
		setSpacingLeft(70f);
		setSpacingTopBottom(0f, 0f);
		setFontCustom(3, 15);
		insertRow("Lyra Network Telecomunicações E Meios De Pagamento Ltda.");
		
		setSpacingLeft(140f);
		setSpacingTopBottom(0f, 0f);
		setFontCustom(2, 8);
		insertRow("Av. Queiroz Filho, 1560 - Vila Leopoldina");
		
		setSpacingLeft(180f);
		setSpacingTopBottom(0f, 0f);
		setFontCustom(2, 8);
		insertRow("São Paulo - SP, 05319-000");
		
		setSpacingLeft(200f);
		setSpacingTopBottom(0f, 0f);
		setFontCustom(2, 8);
		insertRow("06.649.157/0001-29");

		createSeparatorLine(5f, 100f);
	}
	
	//INSERT
	public void insertRow(String row) throws DocumentException {
		List<String> listRows = new ArrayList<String>();
		listRows.add(row);
		insertRows(listRows);
	}
	
	public void insertRows(List<String> rows) throws DocumentException {
		for (String row : rows) {
			paragraph = new Paragraph();
			
			if (spacingTop != 10 || spacingBottom != 10) {
				paragraph.setSpacingBefore(spacingTop);
				paragraph.setSpacingAfter(spacingBottom);
				spacingTop = 10;
				spacingBottom = 10;
			}
			
			if (typeFont != 1 || size != 12f) {
				paragraph.setFont(font);
				typeFont = 1;
				size = 12f;
				setFontCustom(typeFont, size);
			}
			
			if (widthText != 0) {
				paragraph.setIndentationLeft(widthText);
				widthText = 0;
			}
			
			paragraph.add(row);
			
			document.add(paragraph);
		}
	}

	public void createNewPage() {
		document.newPage();
		countPages++;
	}
	
	public void createFooterBarcode() throws DocumentException, MalformedURLException, IOException {
		float[] columnWidths = {5, 3};
		table = new PdfPTable(columnWidths);
		table.setWidthPercentage(100);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		Image img = Image.getInstance("resources/images/codigo_barra.jpg");
		img.setAbsolutePosition(0f, 0f);
        img.scaleAbsoluteWidth(100f);
        img.scaleAbsoluteHeight(100f);
		
        cell = new PdfPCell(img, true);
        
        cell.setBorderColor(GrayColor.GRAYWHITE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(8);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("aaaaaa"));
        cell.setBorderColor(GrayColor.GRAYWHITE);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Vencimento: 05/01/2018"));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorderColor(GrayColor.GRAYWHITE);
        table.addCell(cell);
        
        document.add(table);
        
//      setFontCustom(typeFont, size);
		
//		setSpacingTop(50f);
//		setSpacingBottom(0f);
//		setFontCustom(1, 11f);
//		insertRow("32131.1.213111511135.1313131515.31563115515.31631531515.1515");
//		setSpacingTop(0f);
//		createSeparatorLine(0f, 100f);
//		setPositionImage(45f, 10f);
//		setSizeImage(500f, 60f);
//		addImage("codigo_barra.jpg");
	}
	
	//METHODS TO MANIPULATE TABLES
	public void createTable(float [] columnWidths) {
		this.columnWidths = columnWidths;
		table = new PdfPTable(columnWidths);
		table.setWidthPercentage(100);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
	}
	
	public void createTitleTable(String title) throws DocumentException {	
		if (typeFont != 1 || size != 12f) {
			typeFont = 1;
			size = 12f;
		}
		
		setFontCustom(1, 18f);
		
        cell = new PdfPCell(new Paragraph(title, font));
        cell.setBorderColor(GrayColor.GRAYWHITE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(8);
        
        table.addCell(cell);
        
        if (typeFont != 1 || size != 12f) {
			typeFont = 1;
			size = 12f;
			setFontCustom(typeFont, size);
		}
	}
	
	public void createHeaderTable(String headerColumnOne, String headerColumnTwo) throws DocumentException {	
		setFontCustom(1, 14f);
		if (typeFont != 1 || size != 12f) {
			typeFont = 1;
			size = 12f;
		}
		
		cell = new PdfPCell(new Paragraph(headerColumnOne, font));
		cell.setBorderColor(GrayColor.GRAYWHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph(headerColumnTwo, font));
		cell.setBorderColor(GrayColor.GRAYWHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        
        setFontCustom(typeFont, size);
	}
	
	public void createBodyTable(List<String> listOne, List<String> listTwo) throws DocumentException {
		if (listOne.size() == listTwo.size()) {
        	for (int i = 0; i < listOne.size(); i++) {
    			setFontCustom(1, 8);
        		cell = new PdfPCell(new Paragraph(listOne.get(i), font));
            	cell.setBorderColor(GrayColor.GRAYWHITE);
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	table.addCell(cell);
            	
    			setFontCustom(1, 8);
        		cell = new PdfPCell(new Paragraph(listTwo.get(i), font));
            	cell.setBorderColor(GrayColor.GRAYWHITE);
            	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            	table.addCell(cell);
            }
        }
		
		if (typeFont != 1 || size != 12f) {
			typeFont = 1;
			size = 12f;
			setFontCustom(typeFont, size);
		}
	}
	
	public void insertLineTable(String cellOne, String cellTwo) throws DocumentException {	
		if (typeFont != 1 || size != 12f) {
			typeFont = 1;
			size = 12f;
		}
		
		cell = new PdfPCell(new Paragraph(cellOne, font));
		cell.setBorderColor(GrayColor.GRAYWHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph(cellTwo, font));
		cell.setBorderColor(GrayColor.GRAYWHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        
        setFontCustom(typeFont, size);
	}

	public void generateTable() throws DocumentException{
		document.add(table);
	}
	
	public void spacingTopTable(float spacingTopTable) {
		table.setSpacingAfter(spacingTopTable);
	}
	
	public void createSeparatorLineTable(float marginTopSeparator, float widthSeparator) {
		DottedLineSeparator line = new DottedLineSeparator();
		paragraph = new Paragraph();
		
		line.setOffset(marginTopSeparator);
		line.setGap(0f);
		line.setLineWidth(0f);
		line.setPercentage(widthSeparator);
		
		paragraph.add(line);
		
		cell = new PdfPCell(paragraph);
		cell.setBorderColor(GrayColor.GRAYWHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(8);
        table.addCell(cell);
	}

	//HTML
	public void customHTML() {
		StringBuilder sb = new StringBuilder();
		sb.append("<div>\n<p align=\"center\">");
		sb.append("<font size=\"5\">");
		sb.append("<b>&nbsp;<font color=\"#32cd32\">My centered Para</font></b>");
		sb.append("</font>");
		sb.append("<font color=\"#32cd32\">&nbsp;</font>");
		sb.append("</p>\n</div>");
	}
	
	//SPACING
	public void setSpacingLeft(float widthText) {
		this.widthText = widthText;
	}
	
	public void setSpacingTopBottom(float spacingTop, float spacingBottom) {
		setSpacingTop(spacingTop);
		setSpacingBottom(spacingBottom);
	}
	
	public void setSpacingTop(float spacingTop) {
		this.spacingTop = spacingTop;
	}
	
	public void setSpacingBottom(float spacingBottom) {
		this.spacingBottom = spacingBottom;
	}

	//IMAGE
	public void addImage(String img) throws DocumentException, MalformedURLException, IOException {
        image = Image.getInstance("resources/images/" + img);
        image.setAbsolutePosition(widthPositionImage, heightPositionImage);
        
        if (widthImage != 100 && heightImage != 100) {
        	image.scaleAbsoluteWidth(widthImage);
            image.scaleAbsoluteHeight(heightImage);
            widthImage = 100;
            heightImage = 100;
        } else {
        	image.scaleAbsoluteWidth(100);
        	image.scaleAbsoluteHeight(100);
        }
        
        document.add(image);
	}
	
	public void setPositionImage(float widthPositionImage, float heightPositionImage) {
		this.widthPositionImage = widthPositionImage;
		this.heightPositionImage = heightPositionImage;
	}
	
	public void setSizeImage(float widthImage, float heightImage) {
		this.widthImage = widthImage;
		this.heightImage = heightImage;
	}
	
	//CUSTOM
	public void createSeparatorLine(float marginTop, float width) throws DocumentException {
		Paragraph p = new Paragraph();
		DottedLineSeparator line = new DottedLineSeparator();
		
		line.setOffset(marginTop);
		line.setGap(0f);
		line.setLineWidth(0f);
		line.setPercentage(width);
		
        p.add(line);
        document.add(p);
	}
	
	public void setFontCustom(int typeFont, float size) throws DocumentException {
		this.typeFont = typeFont;
		this.size = size;
		
		if (typeFont == 1) {
			font = new Font(FontFamily.TIMES_ROMAN, size);
		} else if (typeFont == 2) {
			font = new Font(FontFamily.COURIER, size);
		} else if (typeFont == 3) {
			font = new Font(FontFamily.HELVETICA, size);
		}
	}
}