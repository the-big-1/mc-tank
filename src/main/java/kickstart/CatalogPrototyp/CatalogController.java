package kickstart.CatalogPrototyp;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class CatalogController{
	
	private final CatalogPrototyp catalog;
	
	
	public CatalogController(CatalogPrototyp catalog) {
		this.catalog = catalog;
	}
 	
	
	@RequestMapping("/catalog")
	public String catalogPage (Model model, NewItemForm form){
		model.addAttribute("catalog", catalog.findAll());
		return "catalog";
	}
	
	@PostMapping("/newItem")
	String registerNew(@Valid NewItemForm form, Errors result) {

		if (result.hasErrors()) {
			return "newItem";
		}

		//CatalogManagement.createNewProduct(form);

		return "redirect:/catalog";
	}
	
	@RequestMapping("/newItem")
	public String newItem(){
		return"newItem";
	}
	
	
	
}

