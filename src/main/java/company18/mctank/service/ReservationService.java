package company18.mctank.service;

import java.time.LocalDateTime;
import java.util.*;

import company18.mctank.exception.AnonymusUserException;
import company18.mctank.forms.ReservationForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import company18.mctank.domain.McSitReservation;
import company18.mctank.domain.McWashReservation;
import company18.mctank.domain.Reservation;
import company18.mctank.repository.ReservationRepository;

/**
 * Service for {@link Reservation} management.
 * @author CS
 */
@Service
public class ReservationService {
	private static final Logger LOG = LoggerFactory.getLogger(ReservationService.class);

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private CustomerService customerService;
	
	/**
	 * Finds all reservations in {@link ReservationRepository}.
	 * @return reservations
	 */
	public Iterable<Reservation> findAll(){
		return reservationRepository.findAll();
	}

	/**
	 * Finds reservations by class.
	 * @param reservationClass class of reservations to be found
	 * @return reservations with matching class
	 */
	public List<Reservation> findByClass(Class<?> reservationClass){
		LinkedList<Reservation> result = new LinkedList<Reservation>();
		for (Reservation reservation : reservationRepository.findAll()){
			if (reservationClass.isInstance(reservation)) {
				result.add(reservation);
			}
		}
		return result;
	}

	/**
	 * Find reservations for list of points.
	 *
	 * @param points list of points
	 * @return map of string and list of reservations
	 */
	public Map<String, List<Reservation>> findReservationsFor(List<Class> points){
		Map<String, List<Reservation>> reservations = new HashMap<>();
		points
			.forEach(point ->
				reservations.put(point.getSimpleName(), this.sortByDate(this.findByClass(point)))
			);
		return reservations;
	}

	/**
	 * Sorts reservations by date.
	 * @param reservations List of reservations to be sorted
	 * @return sorted reservations
	 */
	public List<Reservation> sortByDate(List<Reservation> reservations){
		List<Reservation> sorted = new ArrayList<>(reservations);
		sorted.sort(Comparator.comparing(Reservation::getDate));
		return sorted;
	}
	
	/**
	 * Deletes reservation with given id from {@link ReservationRepository}.
	 * @param id reservation to be deleted
	 */
	public void deleteById(long id) {
		reservationRepository.deleteById(id);
	}

	public void save(ReservationForm form){
		LocalDateTime time = LocalDateTime.of(form.getDate(), form.getTime());
		this.save(form.getMcPoint(), form.getName(), time, form.getUsername());
	}

	/**
	 * Saves reservation as {@link McSitReservation} or {@link McWashReservation}.
	 * Throws {@link IllegalArgumentException} if reservations date is before now.
	 * @param mcPoint name of McPoint the reservation belongs to
	 * @param name reservations name
	 * @param dateAndTime date and time of reservation
	 * @param username username of owner of this reservation
	 */
	public void save(String mcPoint, String name, LocalDateTime dateAndTime, String username){
		// test if dateAndTime makes sense
		if (dateAndTime.isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException();
		}
		
		// save entry as concrete class
		if (mcPoint.equals("McSit")) {
			reservationRepository.save(new McSitReservation(name, dateAndTime, username));
		}
		if (mcPoint.equals("McWash")) {
			reservationRepository.save(new McWashReservation(name, dateAndTime, username));
		}
	}

	/**
	 * Get all Events for customer.
	 *
	 * @return list of reservations for customer
	 */
	public List<Reservation> getAllEventsForCustomer() {
		String currentUsername = null;
		try {
			currentUsername = customerService.getCurrentUserAccount().getUsername();
		} catch (AnonymusUserException e) {
			LOG.error("Cannot get reservations for Customer. Cause: " + e.getMessage());
		}
		return reservationRepository.findAllByUsername(currentUsername);
	}
}
