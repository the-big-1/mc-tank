package company18.mctank.service;

import company18.mctank.domain.McTankOrder;
import company18.mctank.repository.ItemsRepository;


import company18.mctank.forms.NewItemForm;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.salespointframework.catalog.Product;
import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.order.OrderManager;
import org.salespointframework.quantity.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ItemsService {
	@Autowired
	private ItemsRepository itemsRepository;
	@Autowired
	private OrderManager<McTankOrder> orderManager;
	@Autowired
	private UniqueInventory<UniqueInventoryItem> inventoryRepository;

	public Product createNewProduct(NewItemForm form) {
		return  createNewProduct(form.getProductName(), form.getPrice(), form.getProductCategories());
	}

	public Product createNewProduct(String name, String cost, List<String> mcPoints){

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

		if (mcPoints != null) {
			for (String point : mcPoints) {
				product.addCategory(point);
			}
		}


		return itemsRepository.save(product);
	}

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
				if (ordersCount == null)
					ordersCount = 0;
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
		this.findProduct(productId).map(p -> p.increaseQuantity(q));
	}

}