package company18.mctank.service;

import company18.mctank.domain.McProduct;
import company18.mctank.domain.McTankOrder;
import company18.mctank.repository.ItemsRepository;


import company18.mctank.forms.NewItemForm;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.swing.text.html.parser.Entity;

import org.salespointframework.catalog.Product;
import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.order.ChargeLine;
import org.salespointframework.order.OrderLine;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.quantity.Metric;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * Service for handling Items which are {@link Product}s from SalesPointFramework.
 *
 * The Items are stored in a {@link ItemsRepository}.
 *
 * @author David Leistner
 * @author Artem Sierikov
 */

@Service
@Transactional
public class ItemsService {
	@Autowired
	private ItemsRepository itemsRepository;
	@Autowired
	private OrderManagement<McTankOrder> orderManager;
	@Autowired
	private UniqueInventory<UniqueInventoryItem> inventoryRepository;
	@Autowired
	private OrdersService ordersService;


	/**
	 * Get's the values from the form.
	 *
	 * @param form must not be {@literal null}
	 * @return calls the actual method
	 */

	public Product createNewProduct(NewItemForm form) {
		return  createNewProduct(
			form.getProductName(),
			form.getPrice(),
			form.getAmount(),
			form.getProductCategories()
		);
	}

	/**
	 * Creates new {@link Product}s and saves them in the {@link ItemsRepository}.
	 *
	 * @param name must not be {@literal null}. The name of the new {@link Product}.
	 * @param cost must not be {@literal null}. The price of the new {@link Product}.
	 * @param mcPoints must not be {@literal null}. The McPoints where the new {@link Product} is available.
	 */

	public Product createNewProduct(String name, String cost, int amount, List<String> mcPoints){

		String priceAsString = cost
			.replace(",", ".")
			.replace("€", "")
			.replace(" ", "");

		double priceToDouble = Double.parseDouble(priceAsString);
		MonetaryAmount price = Monetary.getDefaultAmountFactory()
			.setCurrency("EUR")
			.setNumber(priceToDouble)
			.create();

		var product = new Product(name, price);
		var quantity =  product.createQuantity(amount);
		var inventoryItem = new UniqueInventoryItem(product, quantity);

		if (mcPoints != null) {
			for (String point : mcPoints) {
				product.addCategory(point);
			}
		}
		itemsRepository.save(product);
		inventoryRepository.save(inventoryItem);
		return product;
	}


	/**
	 * Deletes Product with given ID form inventory and items.
	 *
	 * @param id
	 */
	public void deleteProduct(ProductIdentifier id){
		UniqueInventoryItem invItem = inventoryRepository.findByProductIdentifier(id).get();
		inventoryRepository.delete(invItem);
		itemsRepository.deleteById(id);
	}

	/**
	 * Makes a Map with Products sorted by McPoints.
	 *
	 * @param mcPoints array with the McPoints in which the Product is available.
	 * @return map with McPoints as Keys and Lists of Products (available at this Point) as Value.
	 */

	public Map<String, List<Product>> makeAssortment(String[] mcPoints) {
		Map<String, List<Product>> assortmentMap = new HashMap<>();

		for (String point : mcPoints) {
			assortmentMap.put(point, itemsRepository.findByCategory(point).toList());
		}

		return assortmentMap;
	}

	public Optional<Product> getProduct(ProductIdentifier identifier) {
		return itemsRepository.findById(identifier);
	}

	public Map<String, Integer> getOrderMap() {
		Map<String, Integer> orderMap = new HashMap<>();
		orderManager.findAll(PageRequest.of(0, 100)).stream().forEach(mcTankOrder -> {
			mcTankOrder.getOrderLines().forEach(orderLine -> {
				String lineId = orderLine.getProductIdentifier().getIdentifier();
				Integer ordersCount = orderMap.get(lineId);
				if (ordersCount == null) {
					ordersCount = 0;
				}
				ordersCount += orderLine.getQuantity().getAmount().intValue();
				orderMap.put(lineId, ordersCount);
			});
		});
		return orderMap;
	}

	public Map<String, Integer> getQuantityMap() {
		Map<String, Integer> quantityMap = new HashMap<>();
		for (UniqueInventoryItem item : inventoryRepository.findAll()) {
			String id = Objects.requireNonNull(item.getProduct().getId()).getIdentifier();
			Integer amount = item.getQuantity().getAmount().intValue();
			quantityMap.put(id, amount);
		}
		return quantityMap;
	}


	public Optional<UniqueInventoryItem> findProduct(Product product){
		return inventoryRepository.findByProduct(product);
	}

	public Optional<UniqueInventoryItem> findProduct(ProductIdentifier productId){
		return inventoryRepository.findByProductIdentifier(productId);
	}

	public Quantity getProductQuantity(Product product) {
		return this.findProduct(product).map(InventoryItem::getQuantity).orElse(null);
	}

	public void updateProductQuantity(ProductIdentifier productId, Quantity q){
		Optional<UniqueInventoryItem> p;
		if ((p = this.findProduct(productId)).isPresent()) {
			p.get().increaseQuantity(q);
		}
	}

	public List<Product> findByCategory(String category) {
		return itemsRepository.findByCategory(category).toList();
	}

	public Map<String, McProduct> findBestProducts() {
		String[] p = {"McZapf", "McSit", "McDrive"};
		Map<String, McProduct> result = new HashMap<>();
		Map<String, List<Product>> points = this.makeAssortment(p);
		Map<String, Integer> orderMap = this.getOrderMap();
		if(!orderMap.isEmpty()) {
			for (Map.Entry<String, List<Product>> point : points.entrySet()) {
				McProduct bestProduct = this.findBest(orderMap, point.getValue());
				result.put(point.getKey(), bestProduct);
			}
		}
		return result;
	}

	private McProduct findBest(Map<String, Integer> orderMap, List<Product> products) {
		String tmpId;
		int orders = 0, tmpOrders = 0;
		Product prod = null;
		for (Product product : products){
			tmpId = Objects.requireNonNull(product.getId()).getIdentifier();
			if (orderMap.containsKey(tmpId)) {
				tmpOrders = orderMap.get(tmpId);
			}
			if (tmpOrders > orders){
				orders = tmpOrders;
				prod = product;
			}
		}
		return new McProduct(prod, this.getProductQuantity(prod), orders);

	}

	/**
	 * Prediction for future Fuel Orders made with data of last 100 days.
	 *
	 * @param fuel
	 * @return Prediction
	 */
	public String getFuelFuture(Product fuel) {
		List<McTankOrder> daysOrders;
		float usedLiters = 0;
		for (int i = 100; i > 0; i--) {
			// get orders from day
			daysOrders = this.orderManager.findBy(
					Interval.from(LocalDateTime.of(LocalDate.now().minusDays(i), LocalTime.of(0, 0)))
					.to(LocalDateTime.of(LocalDate.now().minusDays(((long)i)-1), LocalTime.of(0, 0)))).toList();
			// add to total liters used
			for (McTankOrder order: daysOrders) {
				usedLiters += order.getQuantity(fuel).getAmount().floatValue();
			}
		}
		// returns average per day since divided by 100
		return String.format( "%.1f", (float) usedLiters/100);
	}
}
