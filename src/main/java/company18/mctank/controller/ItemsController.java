
package company18.mctank.controller;

import company18.mctank.service.ItemsService;
import company18.mctank.forms.NewItemForm;
import company18.mctank.repository.ItemsRepository;

import javax.validation.Valid;


import org.salespointframework.catalog.Product;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemsController {
	@Autowired
	private ItemsRepository itemsRepository;
	@Autowired
	private ItemsService itemsService;
	@Autowired
	private UniqueInventory<UniqueInventoryItem> inventory;
	
	private static final Quantity NONE = Quantity.of(0);
	
	private final String mcPoints[] = {"McZapf", "McSit", "McDrive", "McWash"}; // TODO: Decomposite to McPoint;

	
	
	@GetMapping("/items")
	public String index(Model model) {
		model.addAttribute("assortment", itemsService.makeAssortment(mcPoints));
		return "items-management";
	}
	
	@RequestMapping("/newItem")												//New Item Page
	public String newItem(Model model, NewItemForm form){  		//creates new form
		model.addAttribute("form", form);
		model.addAttribute("Categories", mcPoints);
		return"newItem";
		
	}
		
	@PostMapping("/newItem")												//called after you submit the values for the new Items
	public String registerNew(@Valid NewItemForm form, Errors result) {

		if (result.hasErrors()) {
			return "newItem";
		}

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
