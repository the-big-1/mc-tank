package company18.mctank.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import company18.mctank.domain.Reservation;

/**
 * Repository containing {@link Reservation}s.
 * Extends {@link CrudRepository}.
 * @author CS
 *
 */
@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
}
