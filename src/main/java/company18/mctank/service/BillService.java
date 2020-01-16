package company18.mctank.service;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class BillService {

	public void createPdf(String filename) throws DocumentException, IOException {
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream(filename));
		document.open();

		PdfContentByte cb = writer.getDirectContent();
		BaseFont bf = BaseFont.createFont();
		//setImage(cb, "img/memory.png", 40);
		cb.beginText();
		cb.setFontAndSize(bf, 12);
		cb.moveText(20, 105);
		cb.showText("Falsches Üben von Xylophonmusik quält jeden größeren Zwerg.");
		cb.moveText(120, -16);
		cb.setCharacterSpacing(2);
		cb.setWordSpacing(12);
		cb.newlineShowText("Erst recht auch jeden kleineren.");
		cb.endText();

		document.close();

	}



}