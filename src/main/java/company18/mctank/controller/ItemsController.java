
package company18.mctank.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemsController {
	
	private final Items items;
	private final ItemsManagement management;
	
	public ItemsController(Items items, ItemsManagement management) {
		this.items = items;
		this.management = management;
		
	}
	
	
	@RequestMapping("/items")
	public String index(Model model) {
		model.addAttribute("Items", items.findAll());
		return "items";
	}
	
	@RequestMapping("/newItem")
	public String newItem(Model model, NewItemForm form){  //in der Methode übergeben erzeugt hier ein neues Form
		model.addAttribute("form", form);
		
		return"newItem";
		
	}
		
	@PostMapping("/newItem")
	String registerNew(@Valid NewItemForm form, Errors result) {

		if (result.hasErrors()) {
			return "newItem";
		}

		management.createNewProduct(form);

		return "redirect:/catalog";
	}
}
