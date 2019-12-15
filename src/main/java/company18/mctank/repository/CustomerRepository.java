package company18.mctank.repository;

import company18.mctank.domain.Customer;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Customer CRUD repository.
 */
@Repository
public interface CustomerRepository  extends  CrudRepository<Customer, Long> {
	/**
	 * Find customer by user account.
	 *
	 * @param userAccount user account
	 * @return Customer
	 */
	Customer findCustomerByUserAccount(UserAccount userAccount);

	/**
	 * Delete all users that have {@link Customer#getLastActivityDate()} is before than 120 days ago day.
	 * @param lastPossibleDate last possible date
	 * @return number of deleted customers
	 */
	Integer deleteAllByLastActivityDateBefore(Date lastPossibleDate);
}