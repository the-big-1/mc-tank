package company18.mctank.repository;

import company18.mctank.domain.McTankOrder;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface McTankOrderRepository extends CrudRepository<McTankOrder, Long> {

}
