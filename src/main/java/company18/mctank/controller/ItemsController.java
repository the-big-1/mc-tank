
package company18.mctank.controller;

import company18.mctank.service.CustomerService;
import company18.mctank.service.ItemsService;
import company18.mctank.forms.NewItemForm;

import org.salespointframework.catalog.Product;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@PreAuthorize("hasAnyRole({'ADMIN', 'MANAGER'})")
public class ItemsController {
	@Autowired
	private ItemsService itemsService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private UniqueInventory<UniqueInventoryItem> inventory;
	
	private static final Quantity NONE = Quantity.of(0);
	
	private final String mcPoints[] = {"McZapf", "McSit", "McDrive", "McWash"}; // TODO: Decomposite to McPoint;

	
	
	@GetMapping("/items")
	public String index(Model model) {
		model.addAttribute("assortment", itemsService.makeAssortment(mcPoints));
		if (customerService.isAdmin()) {
			model.addAttribute("role", "ADMIN");
			return "items-management";
		} else if (customerService.isManager()) {
			model.addAttribute("role", "MANAGER");
			return "items";
		}
		return "redirect:/";
	}
	
	@RequestMapping(value = "/newItem", method = RequestMethod.GET)
	public String newItem(Model model, NewItemForm form){
		model.addAttribute("form", form);
		model.addAttribute("Categories", mcPoints);
		return"newItem";
		
	}
		
	@PostMapping("/item/new")
	public String registerNew(@RequestBody NewItemForm form) {
		itemsService.createNewProduct(form);

		return "redirect:/items";
	}
	
	@GetMapping("/items/{product}")											//itemDetails Page for adding a Product to the Bill/Order
	public String itemDetails(@PathVariable Product product, Model model) {
		
		var quantity = inventory.findByProductIdentifier(product.getId())
				.map(InventoryItem::getQuantity) //
				.orElse(NONE);

		model.addAttribute("product", product);
		model.addAttribute("quantity", quantity);
		model.addAttribute("orderable", quantity.isGreaterThan(product.createQuantity(0)));
		
		return "itemDetails";
	}
}
