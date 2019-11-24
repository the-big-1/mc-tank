
package company18.mctank.controller;

import company18.mctank.service.ItemsService;
import company18.mctank.forms.NewItemForm;
import company18.mctank.repository.Items;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemsController {
	
	private final Items items;
	private final ItemsService service;
	
	private final String Categories[] = {"McZapf", "McSit", "McDrive", "McWash"}; //Categories are used to sort the Products
	
	public ItemsController(Items items, ItemsService service) {
		this.items = items;
		this.service = service;
		
	}
	
	
	@RequestMapping("/items")
	public String index(Model model) {
		//model.addAttribute("Items", items.findAll());
		for (String category: Categories) {
			model.addAttribute(category, items.findByCategory(category));  //not working i guesss
		}
		return "items";
	}
	
	@RequestMapping("/newItem")
	public String newItem(Model model, NewItemForm form){  //in der Methode übergeben erzeugt hier ein neues Form
		model.addAttribute("form", form);
		model.addAttribute("Categories", Categories);
		
		return"newItem";
		
	}
		
	@PostMapping("/newItem")
	String registerNew(@Valid NewItemForm form, Errors result) {

		if (result.hasErrors()) {
			return "newItem";
		}

		service.createNewProduct(form);

		return "redirect:/items";
	}
}
