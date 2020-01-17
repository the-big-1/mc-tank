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
		cb.moveText(0, -40);
		cb.showText(order.getUserNameString());
		cb.moveText(0, -20);
		cb.showText("Customer Adress");
		cb.moveText(0, -20);
		cb.showText("Customer Email");
		cb.setFontAndSize(bf, 15);
		cb.moveText(300, -70);
		cb.showText("Bill");
		cb.setFontAndSize(bf, 10);
		cb.moveText(0, -20);
		cb.showText("For Order: " + order.getIdString());
		cb.moveText(0, -20);
		cb.showText("Order created at: " + order.getOrderDateString());
		cb.setFontAndSize(bf, 15);
		cb.moveText(-300, -50);
		cb.showText("Your Bill");
		cb.setFontAndSize(bf, 10);
		cb.moveText(0, -40);
		cb.showText("Dear " + order.getUserNameString());
		cb.moveText(0, -20);
		cb.showText("thank you for visiting McTank. We hope you are satisfied and would enjoy to see you again!");
		cb.moveText(0, -100);
		cb.showText("Productname");
		cb.moveText(0, -20);
		cb.showText("Cola 0.5L");
		cb.moveText(100, 20);
		cb.showText("Product-ID");
		cb.moveText(0, -20);
		cb.showText("7fb7f2e8-3e05-4b94-8c59-b70708a5e917");
		cb.moveText(200, 20);
		cb.showText("Quantity");
		cb.moveText(0, -20);
		cb.showText("2");
		cb.moveText(100, 20);
		cb.showText("Price");
		cb.moveText(0, -20);
		cb.showText("3");
		cb.setFontAndSize(bf, 15);
		cb.moveText(-200, -80);
		cb.showText("Total: " + order.getTotalString());

		cb.setCharacterSpacing(2);
		cb.setWordSpacing(12);
		cb.endText();



		document.close();
	}



}