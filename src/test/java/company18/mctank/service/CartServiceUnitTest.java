package company18.mctank.service;

import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.salespointframework.catalog.Product;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.order.CartItem;
import org.salespointframework.payment.Cash;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import company18.mctank.domain.McTankCart;
import company18.mctank.repository.CustomerRepository;

@SpringBootTest
public class CartServiceUnitTest {
	
	@Autowired 
	private CartService service;
	
	@Autowired
	private UniqueInventory<UniqueInventoryItem> inv;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Test
	void buyTest() {
		McTankCart cart = new McTankCart();
		
		// add exceeding quantity to cart
		cart.addOrUpdateItem(inv.findAll().iterator().next().getProduct(), inv.findAll().iterator().next().getQuantity().getAmount().doubleValue() + 1);
		
		// check if buying fails (with first account of customerrepo)
		cart.setCustomer(customerRepository.findAll().iterator().next());
		assertFalse(this.service.buy(cart, Cash.CASH));
		
		cart.clear();
		
		// adding everything to cart
		for (UniqueInventoryItem item : inv.findAll()) {
			cart.addOrUpdateItem(item.getProduct(), item.getQuantity().getAmount().doubleValue());
		}
		
		// check for buying with no account
		cart.setCustomer(null);
		assertFalse(service.buy(cart, Cash.CASH));
		
		// check for correct quantities
		// salespoints isEqualTo() doesnt exist here for some reason
		// https://st.inf.tu-dresden.de/SalesPoint/api/org/salespointframework/quantity/Quantity.html#isEqualTo(org.salespointframework.quantity.Quantity)
		cart.get().forEach((CartItem item) -> 
			assertTrue(item.getQuantity().isGreaterThanOrEqualTo(inv.findByProduct(item.getProduct()).get().getQuantity())
						&& !item.getQuantity().isGreaterThan(inv.findByProduct(item.getProduct()).get().getQuantity())));
		
		// buying with first account from customerrepository (should be boss)
		Optional<UserAccount> acc = Optional.of(customerRepository.findAll().iterator().next().getUserAccount());
		cart.setCustomer(customerRepository.findCustomerByUserAccount(acc.get()));
		assertTrue(service.buy(cart, Cash.CASH));
		
		// check if cart is empty
		assertTrue(cart.isEmpty());

		// check if inventory is empty
		inv.findAll().forEach((UniqueInventoryItem item) -> 
				assertTrue(item.getQuantity().isZeroOrNegative() && !item.getQuantity().isNegative()));
	}
	
	@Test
	void addOrUpdateItemTest() {
		McTankCart cart = new McTankCart();
		Product testProduct =  this.inv.findAll().iterator().next().getProduct();
		
		// adding regular product
		this.service.addOrUpdateItem(cart, testProduct, 3, false);
		
		// test if it is added
		assertTrue(cart.get().findFirst().get().getProduct().equals(testProduct));
		assertTrue(cart.get().findFirst().get().getQuantity().getAmount().intValueExact() == 3);
		
		cart.clear();
		
		// adding product with quantity
		this.service.addOrUpdateItem(cart, testProduct, testProduct.createQuantity(20));
				
		// test if it is added
		assertTrue(cart.get().findFirst().get().getProduct().equals(testProduct));
		assertTrue(cart.get().findFirst().get().getQuantity().getAmount().intValueExact() == 20);
		
		// adding claim
		this.service.addOrUpdateItem(cart, testProduct, 5, true);
		
		// check if claim product is correct
		Iterator<CartItem> iterator = cart.get().iterator();
		iterator.next();
		CartItem claimitem = iterator.next();
		assertTrue(claimitem.getProduct().getName().equals(testProduct.getName()+" REKLAMATION"));
		assertTrue(claimitem.getQuantity().getAmount().intValueExact() == 5);
	}
}
