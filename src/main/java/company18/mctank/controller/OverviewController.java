package company18.mctank.controller;

import company18.mctank.domain.GasPump;
import company18.mctank.repository.CustomerRepository;
import company18.mctank.service.CustomerService;
import company18.mctank.service.ItemsService;
import company18.mctank.service.OrdersService;
import org.salespointframework.catalog.Product;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Metric;
import org.salespointframework.quantity.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

/**
 * Overview controller.
 */
@Controller
@PreAuthorize("hasRole('ADMIN')")
public class OverviewController {

	private static final Quantity MIN_FUEL_QUANTITY = Quantity.of(10000L, Metric.LITER);  //min quantity before warning is triggered

	@Autowired
	private UniqueInventory<UniqueInventoryItem> inventoryRepository;

	@Autowired
	private ItemsService itemsService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private OrdersService ordersService;


	/**
	 * Overview page.
	 *
	 * @return overview
	 */
	@GetMapping("/overview")
	public String showOverviewPage(Model model) {
		this.addUserAndOrders(model);
		this.addFuelStats(model);
		return "overview";
	}

	private void addUserAndOrders(Model model) {
		model.addAttribute("activeUserAmount", customerService.findAll().size());
		model.addAttribute("ordersAmount", ordersService.findAll().size());
		model.addAttribute("activeUserPercent", customerService.findAllActivePercent());
		model.addAttribute("completedOrdersPercent", ordersService.findAllCompletedPercent());
		model.addAttribute("averageProfitPerOrder", ordersService.findAverageProfitPerOrder());
		model.addAttribute("bestProducts", itemsService.findBestProducts());
	}


	private void addFuelStats(Model model){
		List<Product> productList = itemsService.findByCategory("McZapf");
		Product benzine = getProductByName(productList, GasPump.SUPER_BENZIN);
		Product diesel = getProductByName(productList, GasPump.DIESEL);
		boolean isBenzineFinishing = isFuelFinishing(benzine);
		boolean isDieselFinishing = isFuelFinishing(diesel);
		model.addAttribute("fuelBenzineWarning", isBenzineFinishing);
		model.addAttribute("fuelDieselWarning", isDieselFinishing);
		model.addAttribute("benzineTotal", itemsService.getProductQuantity(benzine));
		model.addAttribute("benzineFuture", itemsService.getFuelFuture(benzine));
		model.addAttribute("dieselTotal", itemsService.getProductQuantity(diesel));
		model.addAttribute("dieselFuture", itemsService.getFuelFuture(diesel));
	}

	public Product getProductByName(List<Product> productList, String name) {
		return productList.stream()
				.filter(product -> product.getName().equals(name))
				.findFirst()
				.orElse(null);
	}

	public boolean isFuelFinishing(Product product) {
		Optional<UniqueInventoryItem> inventoryItem = inventoryRepository.findByProduct(product).or(Optional::empty);
		return inventoryItem
				.map(this::isItemGreaterThanMinFuelQuantity)
				.orElse(false);
	}

	private boolean isItemGreaterThanMinFuelQuantity(UniqueInventoryItem uniqueInventoryItem) {
		return uniqueInventoryItem.getQuantity().isLessThan(MIN_FUEL_QUANTITY);
	}
}
