package company18.mctank.service;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTextArray;
import com.itextpdf.text.pdf.PdfWriter;
import company18.mctank.domain.McTankOrder;
import company18.mctank.domain.Customer;

public class BillService {

	public void createPdf(String filename, McTankOrder order) throws DocumentException, IOException {
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream(filename));
		document.open();

		PdfContentByte cb = writer.getDirectContent();
		BaseFont bf = BaseFont.createFont();
		//setImage(cb, "img/memory.png", 40);
		cb.beginText();
		cb.setFontAndSize(bf, 12);
		cb.moveText(0, 800);
		cb.newlineShowText("Rechnung");
		cb.moveText(120, -16);
		cb.showText("Total: " + order.getTotalString());
		cb.moveText(120, -30);
		cb.showText("Order-ID: " + order.getIdString());
		cb.moveText(120, -40);
		cb.showText(order.getOrderDateString());
		cb.moveText(-20, -40);
		//cb.showText();
		cb.setCharacterSpacing(2);
		cb.setWordSpacing(12);
		cb.endText();

		document.close();

	}



}