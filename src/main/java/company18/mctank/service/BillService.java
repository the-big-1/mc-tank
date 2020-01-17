package company18.mctank.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
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
		cb.beginText();
		cb.setFontAndSize(bf, 10);
		cb.moveText(40, 730);
		cb.showText("McTank, Musterstraße, 01234 Musterstadt");
		cb.setFontAndSize(bf, 15);
		cb.moveText(300, -80);
		cb.showText("Bill");
		cb.setFontAndSize(bf, 10);
		cb.moveText(0, -20);
		cb.showText("For Order: " + order.getIdString());
		cb.moveText(0, -20);
		cb.showText("Order created at: " + order.getOrderDateString());
		cb.moveText(120, -40);
		cb.showText("Dear " + order.getUserNameString());
		cb.moveText(120, -20);
		cb.showText("thank you for visiting McTank. We hope you are satisfied and would enjoy to see you again!");


		//cb.showText("Total: " + order.getTotalString());

		cb.setCharacterSpacing(2);
		cb.setWordSpacing(12);
		cb.endText();



		document.close();
	}



}