package company18.mctank.service;

import company18.mctank.domain.McTankOrder;
import company18.mctank.exception.AnonymusUserException;
import org.salespointframework.order.OrderManager;
import org.salespointframework.useraccount.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrdersService {
	private static final Logger LOG = LoggerFactory.getLogger(OrdersService.class);

	@Autowired
	CustomerService customerService;

	@Autowired
	OrderManager<McTankOrder> orderManager;


	public List<McTankOrder> getAllOrdersForCustomer() {
		try {
			UserAccount currentAccount = customerService.getCurrentUserAccount();
			return this.findOrdersForUserAccount(currentAccount);
		} catch (AnonymusUserException e) {
			LOG.warn("Request: Get all orders for current user. Fail: Cannot get orders for Anonymous user");
		}
		return null;
	}

	private List<McTankOrder> findOrdersForUserAccount(UserAccount currentAccount) {
		List<McTankOrder> orders = orderManager.findBy(currentAccount).toList();
		LOG.info("Request: Get all orders for current user. Done: Found orders for "
			+ currentAccount.getUsername()
			+ ". Amount: " + orders.size());
		return orders;
	}
}
