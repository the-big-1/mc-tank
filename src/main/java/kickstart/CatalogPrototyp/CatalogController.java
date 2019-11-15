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
	private final CatalogManagement management;
	
	
	public CatalogController(CatalogPrototyp catalog, CatalogManagement management) {
		this.catalog = catalog;
		this.management = management;
	}
	
	
	//----------------------------------------------------------
 	
	
	@RequestMapping("/catalog")
	public String catalogPage (Model model){
		model.addAttribute("catalog", catalog.findAll());
		return "catalog";
	}
		
	@RequestMapping("/newItem")
	public String newItem(Model model, NewItemForm form){  //in der Methode übergeben erzeugt hier ein neues Form
		model.addAttribute("form", form);
		
		return"newItem";
		
	}
	
	//---------------------------------------------------------
		
	@PostMapping("/newItem")
	String registerNew(@Valid NewItemForm form, Errors result) {

		if (result.hasErrors()) {
			return "newItem";
		}

		management.createNewProduct(form);

		return "redirect:/catalog";
	}
		
	
	
}

