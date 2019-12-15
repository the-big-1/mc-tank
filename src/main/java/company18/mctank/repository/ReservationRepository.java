package company18.mctank.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import company18.mctank.domain.Reservation;


import java.util.List;


/**
 * Repository containing {@link Reservation}s.
 * Extends {@link CrudRepository}.
 * @author CS
 *
 */
@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
	/**
	 * Fina all reservations for user.
	 *
	 * @param username username of the user
	 * @return list of reservations
	 */
	List<Reservation> findAllByUsername(String username);
}
