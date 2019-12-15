package company18.mctank.controller;

import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for getting inventory.
 */
@Controller
public class InventoryController {
	private final UniqueInventory<UniqueInventoryItem> inv;
	
	InventoryController(UniqueInventory<UniqueInventoryItem> inv) {
		this.inv = inv;
	}
	
	/**
	 * Adds all {@link UniqueInventoryItem}s to the model.
	 * @param model model
	 * @return views name
	 */
	@GetMapping("/inventory")
	String inventory(Model model) {
		model.addAttribute("inventory", inv.findAll());
		return "inventory";
	}
	
}
