package company18.mctank.controller;

import company18.mctank.domain.McTankOrder;
import company18.mctank.service.BillService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import company18.mctank.service.OrdersService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


@Controller
public class BillController {
	
	@Autowired
	OrdersService ordersService;


	@PostMapping("/bill")
	public String showBill(Model model, @RequestParam String id){
		McTankOrder order = ordersService.findOrderById(ordersService.findAll(), id);
		model.addAttribute("order", order);

		return "bill";
	}

	@PostMapping("/bill/pdf")
	public ResponseEntity<InputStreamResource> pdf(Model model, @RequestParam String pdf) {
		BillService pdf2 = new BillService();
		try{
			pdf2.createPdf("billpdf.pdf", ordersService.findOrderById(ordersService.findAll(), pdf));
		}catch (Exception ex)
		{
			System.out.println("nicht gut");
		}
		java.io.File file = new java.io.File("billpdf.pdf");
		try {
			org.springframework.core.io.InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file));
			return org.springframework.http.ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+file.getName())
					.body(inputStreamResource);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}


}


