package company18.mctank.service;

import java.time.LocalDateTime;
import java.util.*;

import com.mysema.commons.lang.Assert;
import company18.mctank.exception.AnonymusUserException;
import company18.mctank.forms.ReservationForm;
import org.salespointframework.catalog.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import company18.mctank.domain.McSitReservation;
import company18.mctank.domain.McWashReservation;
import company18.mctank.domain.Reservation;
import company18.mctank.repository.ReservationRepository;

@Service
public class ReservationService {
	private static final Logger LOG = LoggerFactory.getLogger(ReservationService.class);

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private CustomerService customerService;
	
	public Iterable<Reservation> findAll(){
		return reservationRepository.findAll();
	}
	
	// finds reservations by class (Reservation, McSitReservation or McWashReservation [.class] possible)
	public List<Reservation> findByClass(Class<?> reservationClass){
		LinkedList<Reservation> result = new LinkedList<Reservation>();
		for (Reservation reservation : reservationRepository.findAll()){
			if (reservationClass.isInstance(reservation)) {
				result.add(reservation);
			}
		}
		return result;
	}

	public Map<String, List<Reservation>> findReservationsFor(List<Class> points){
		Map<String, List<Reservation>> reservations = new HashMap<>();
		points
			.forEach(point ->
				reservations.put(point.getSimpleName(), this.sortByDate(this.findByClass(point)))
			);
		return reservations;
	}
	
	// sorts reservations by date
	public List<Reservation> sortByDate(List<Reservation> reservations){
		List<Reservation> sorted = new ArrayList<>(reservations);
		sorted.sort(Comparator.comparing(Reservation::getDate));
		return sorted;
	}
	
	public void deleteById(long id) {
		reservationRepository.deleteById(id);
	}

	public void save(ReservationForm form){
		LocalDateTime time = LocalDateTime.of(form.getDate(), form.getTime());
		this.save(form.getMcPoint(), form.getName(), time, form.getUsername());
	}
	
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
