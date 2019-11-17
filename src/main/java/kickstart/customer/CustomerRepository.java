package kickstart.customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kickstart.customer.Customer;

@Repository
interface CustomerRepository  extends  CrudRepository<Customer, Long>{

}
