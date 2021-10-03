package company18.mctank.service;

import company18.mctank.domain.McTankOrder;

import company18.mctank.exception.AnonymusUserException;
import company18.mctank.forms.DataStacked;
import company18.mctank.repository.ItemsRepository;
import org.salespointframework.catalog.Product;
import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.time.Interval;
import org.salespointframework.useraccount.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.money.MonetaryAmount;
import javax.sql.DataSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrdersService {
	private static final Logger LOG = LoggerFactory.getLogger(OrdersService.class);

	@Autowired
	private CustomerService customerService;

	@Autowired
	private OrderManagement<McTankOrder> orderService;

	@Autowired
	private ItemsService itemsService;

	@Autowired
	private ItemsRepository itemsRepository;


	/**
	 * Returns all orders for customer
	 *
	 * @return list of orders for current customer.
	 */
	public List<McTankOrder> getAllOrdersForCustomer() {
		try {
			UserAccount currentAccount = customerService.getCurrentUserAccount();
			return this.findOrdersForUserAccount(currentAccount);
		} catch (AnonymusUserException e) {
			LOG.warn("Request: Get all orders for current user. Fail: Cannot get orders for Anonymous user");
		}
		return null;
	}

	/**
	 * Returns orders for user account.
	 *
	 * @param currentAccount user account
	 * @return list of orders
	 */
	private List<McTankOrder> findOrdersForUserAccount(UserAccount currentAccount) {
		List<McTankOrder> orders = orderService.findBy(currentAccount).toList();
		LOG.info("Request: Get all orders for current user. Done: Found orders for "
			+ currentAccount.getUsername()
			+ ". Amount: " + orders.size());
		return orders;
	}

	private LocalDateTime convertToLocalDateViaInstant(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		try {
			return formatter.parse(date).toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<McTankOrder> findAll() {
		LocalDateTime start = this.convertToLocalDateViaInstant("2018/01/01");
		LocalDateTime end = this.convertToLocalDateViaInstant("2118/01/01");
		Interval interval = Interval.from(start).to(end);
		return this.orderService
			.findBy(interval)
			.get()
			.sorted()
			.collect(Collectors.toList());

	}

	public void deleteOrderBy(String orderId) {
		try {
			UserAccount userAccount = customerService.getCurrentUserAccount();
			List<McTankOrder> orders = this.orderService.findBy(userAccount).toList();
			McTankOrder order = this.findOrderById(orders, orderId);
			this.returnAllProducts(order);
			orderService.delete(order);
		} catch (AnonymusUserException e) {
			e.printStackTrace();
		}
	}

	public void showOrder(String orderId) {
		try {
			UserAccount userAccount = customerService.getCurrentUserAccount();
			List<McTankOrder> orders = this.orderService.findBy(userAccount).toList();
			McTankOrder order = this.findOrderById(orders, orderId);
			this.returnAllProducts(order);
			} catch (AnonymusUserException e) {
			e.printStackTrace();
		}
	}


	private void returnAllProducts(McTankOrder order) {
		order.getOrderLines()
			.forEach(
				line -> itemsService.updateProductQuantity(line.getProductIdentifier(), line.getQuantity())
			);
	}

	public McTankOrder findOrderById(List<McTankOrder> orders, String orderId) {
		return orders.stream()
			.filter(order -> order.getIdString().equals(orderId))
			.findFirst().get();
	}

	private int findAllActiveOrdersAmount() {
		return (int) findAll().stream().filter(Order::isCompleted).count();
	}

	public String findAllCompletedPercent() {
		float rawPercent = ((float) this.findAllActiveOrdersAmount() / this.findAll().size()) * 100f;
		return String.format("%.1f", rawPercent).replace(",", ".");
	}


	public String findAverageProfitPerOrder() {
		double rawAmount = this.findAll()
			.stream()
			.mapToDouble(o -> o.getTotal().getNumber().doubleValue())
			.sum();
		return String.format( "EUR %.1f", rawAmount / findAll().size());
	}

	public DataStacked stackData() {
		LocalDateTime end = LocalDateTime.now();
		LocalDateTime start = LocalDateTime.now().minusDays(6);
		Interval interval = Interval.from(start).to(end);
		List<McTankOrder> orders = this.orderService
			.findBy(interval)
			.get()
			.sorted()
			.collect(Collectors.toList());
		Map<ProductIdentifier, Product> products = new HashMap<>();
		for(Product product : itemsRepository.findAll()){
			products.put(product.getId(), product);
		}
		return new DataStacked(orders, products);

	}
}
