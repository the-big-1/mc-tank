package company18.mctank.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
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
		
		// adding everything to cart
		for (UniqueInventoryItem item : inv.findAll()) {
			cart.addOrUpdateItem(item.getProduct(), item.getQuantity().getAmount().doubleValue());
		}
		
		// check for buying with no account
		Optional<UserAccount> acc = Optional.empty();
		assertFalse(service.buy(cart, acc, Cash.CASH));
		
		// check for correct quantities
		// salespoints isEqualTo() doesnt exist here for some reason
		// https://st.inf.tu-dresden.de/SalesPoint/api/org/salespointframework/quantity/Quantity.html#isEqualTo(org.salespointframework.quantity.Quantity)
		cart.get().forEach((CartItem item) -> 
			assertTrue(item.getQuantity().isGreaterThanOrEqualTo(inv.findByProduct(item.getProduct()).get().getQuantity())
						&& !item.getQuantity().isGreaterThan(inv.findByProduct(item.getProduct()).get().getQuantity())));
		
		// buying with first account from customerrepository (should be boss)
		acc = Optional.of(customerRepository.findAll().iterator().next().getUserAccount());
		assertTrue(service.buy(cart, acc, Cash.CASH));
		
		// check if cart is empty
		assertTrue(cart.isEmpty());

		// check if inventory is empty
		inv.findAll().forEach((UniqueInventoryItem item) -> 
				assertTrue(item.getQuantity().isZeroOrNegative() && !item.getQuantity().isNegative()));
	}
}
