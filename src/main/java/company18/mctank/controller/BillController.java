package company18.mctank.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


public class BillController {
	
	@GetMapping("/bill")
	public String rechnung() {
		return "bill";
	}

}
