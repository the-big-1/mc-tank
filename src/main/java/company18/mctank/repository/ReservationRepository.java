package company18.mctank.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import company18.mctank.domain.Reservation;

import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
	List<Reservation> findAllByUsername(String username);
}
