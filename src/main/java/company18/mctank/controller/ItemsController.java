
package company18.mctank.controller;

import company18.mctank.forms.NewItemForm;
import company18.mctank.service.CustomerService;
import company18.mctank.service.GasPumpService;
import company18.mctank.service.ItemsService;
import org.salespointframework.quantity.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controller for managing items.
 *
 * @author ArtemSer
 * @author David Leistner
 */
@Controller
@PreAuthorize("hasAnyRole({'ADMIN', 'MANAGER'})")
public class ItemsController {

	@Autowired
	private ItemsService itemsService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private GasPumpService pumpService;
	
	private static final Quantity NONE = Quantity.of(0);
	
	private static final String[] mcPoints = {"McZapf", "McSit", "McDrive", "McWash"};

	/**
	 * Return items with data.
	 *
	 * @param model model
	 * @return view name
	 */
	@GetMapping("/items")
	public String index(Model model) {
		model.addAttribute("quantityMap", itemsService.getQuantityMap());
		model.addAttribute("orderMap", itemsService.getOrderMap());
		model.addAttribute("assortment", itemsService.makeAssortment(mcPoints));
		model.addAttribute("pumps", pumpService.getPumps());
		
		if (customerService.isAdmin()) {
			model.addAttribute("role", "ADMIN");
			return "items-management";
		} else if (customerService.isManager()) {
			model.addAttribute("role", "MANAGER");
			return "items";
		}
		
		return "redirect:/";
	}

	/**
	 * Returns view with newItem.
	 *
	 * @param model model
	 * @param form form
	 * @return view name
	 */
	@GetMapping("/newItem")
	public String newItem(Model model, NewItemForm form){
		model.addAttribute("form", form);
		model.addAttribute("Categories", mcPoints);
		return"newItem";
	}

	/**
	 * Saving new item.
	 *
	 * @param form new item form
	 * @return redirect to items
	 */
	@PostMapping("/item/new")
	public String registerNew(@RequestBody NewItemForm form) {
		itemsService.createNewProduct(form);
		return "redirect:/items";
	}

	//not used?
	/**
	 * Pump details.
	 *
	 * @param number pump number
	 * @param model model
	 * @return view name
	 */
	@GetMapping("/pump/{number}")	
	public String itemDetails(@PathVariable int number, Model model) {
		model.addAttribute("number", number);
		if (pumpService.isInValid(number)) {
			model.addAttribute("invalid", true);
			return "pump-details";
		}
		model.addAttribute("invalid", false);
		model.addAttribute("fuelproduct", pumpService.getFuel(number));
		model.addAttribute("quantity", pumpService.getFuelQuantity(number));
		model.addAttribute("price", pumpService.getPrice(number));
		model.addAttribute("orderable", pumpService.getFuelQuantity(number) > 0);
		return "pump-details";
	}
}
