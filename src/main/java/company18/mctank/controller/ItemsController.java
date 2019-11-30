
package company18.mctank.controller;

import company18.mctank.service.ItemsService;
import company18.mctank.forms.NewItemForm;
import company18.mctank.repository.Items;

import javax.validation.Valid;


import org.salespointframework.catalog.Product;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemsController {
	
	private final Items items;
	private final ItemsService service;
	private final UniqueInventory<UniqueInventoryItem> inventory;
	
	private static final Quantity NONE = Quantity.of(0);
	
	private final String Categories[] = {"McZapf", "McSit", "McDrive", "McWash"}; //Categories are used to sort the Products
	
	public ItemsController(Items items, ItemsService service, UniqueInventory<UniqueInventoryItem> inventory) {
		this.items = items;
		this.service = service;
		this.inventory = inventory;
		
	}
	
	
	@RequestMapping("/items")												//Catalog/Items Page
	public String index(Model model) {
		for (String category: Categories) {
			model.addAttribute(category, items.findByCategory(category));
			model.addAttribute("Categories", Categories);
		}
		return "items";
	}
	
	@RequestMapping("/newItem")												//New Item Page
	public String newItem(Model model, NewItemForm form){  		//creates new form
		model.addAttribute("form", form);
		model.addAttribute("Categories", Categories);
		
		return"newItem";
		
	}
		
	@PostMapping("/newItem")												//called after you submit the values for the new Items
	public String registerNew(@Valid NewItemForm form, Errors result) {

		if (result.hasErrors()) {
			return "newItem";
		}

		service.createNewProduct(form);

		return "redirect:/items";
	}
	
	@GetMapping("/items/{product}")											//itemDetails Page for adding a Product to the Bill/Order
	public String itemDetails(@PathVariable Product product, Model model) {
		
		var quantity = inventory.findByProductIdentifier(product.getId())
				.map(InventoryItem::getQuantity) //
				.orElse(NONE);

		model.addAttribute("product", product);
		model.addAttribute("quantity", quantity);   // there is a problem with the unit *liter*  !!!
		model.addAttribute("orderable", quantity.isGreaterThan(NONE));
		
		return "itemDetails";
	}
}
