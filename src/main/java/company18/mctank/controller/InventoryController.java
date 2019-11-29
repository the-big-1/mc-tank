package company18.mctank.controller;

import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InventoryController {
	private final UniqueInventory<UniqueInventoryItem> inv;
	
	InventoryController(UniqueInventory<UniqueInventoryItem> inv) {
		this.inv = inv;
	}
	
	@GetMapping("/inventory")
	String inventory(Model model) {
		model.addAttribute("inventory", inv.findAll());
		return "inventory";
	}
	
}
