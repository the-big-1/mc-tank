package company18.mctank.service;

import company18.mctank.domain.McTankOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrdersServiceTest {

	@Autowired
	private OrdersService ordersService;

    @Test
    void getAllOrdersForCustomer() {
    	Exception thrown = assertThrows(Exception.class,
				() -> ordersService.getAllOrdersForCustomer());
    	assertNotNull(thrown);
    }

    @Test
    void findAll() {
    	List<McTankOrder> orders = ordersService.findAll();
		assertNotNull(orders);
		assertFalse(orders.isEmpty());
    }

    @Test
    void deleteOrderBy() {
		Exception thrown =
				assertThrows(Exception.class,
						() -> ordersService.deleteOrderBy(""),
						"Expected doThing() to throw, but it didn't");

		assertNotNull(thrown);
    }
}