package company18.mctank.repository;

import company18.mctank.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository  extends  CrudRepository<Customer, Long>{

}