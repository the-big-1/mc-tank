package company18.mctank.service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;

import org.springframework.stereotype.Service;

import company18.mctank.domain.McSitReservation;
import company18.mctank.domain.McWashReservation;
import company18.mctank.domain.Reservation;
import company18.mctank.repository.ReservationRepository;

@Service
public class ReservationService {
	private final ReservationRepository repo;
	
	public ReservationService(ReservationRepository repo) {
		this.repo = repo;
	}
	
	public Iterable<Reservation> findAll(){
		return repo.findAll();
	}
	
	public Iterable<Reservation> findByClass(Class<?> reservationClass){
		LinkedList<Reservation> result = new LinkedList<Reservation>();
		for (Reservation reservation : repo.findAll())
			if (reservationClass.isInstance(reservation)) result.add(reservation);
		return result;
	}
	
	
	static public Iterable<Reservation> sortByDate(Iterable<Reservation> reservations){
		LinkedList<Reservation> result = new LinkedList<Reservation>();
		Iterator<Reservation> reservationsIterator = reservations.iterator();
		if (reservationsIterator.hasNext()) result.add(reservationsIterator.next());
		int index;
		while (reservationsIterator.hasNext()) {
			Reservation reservation = reservationsIterator.next();
			for (index = 0; index < result.size(); index++) {
				if (reservation.getDate().isBefore(result.get(index).getDate())) break;	
			}
			result.add(index, reservation);
		}
		return result;
	}
	
	public void save(String mcPoint, String name, LocalDateTime dateAndTime) {
		if (mcPoint.equals("McSit")) repo.save(new McSitReservation(name, dateAndTime));
		if (mcPoint.equals("McWash")) repo.save(new McWashReservation(name, dateAndTime));
	}
}
