package company18.mctank.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import company18.mctank.domain.McSitReservation;
import company18.mctank.domain.McWashReservation;
import company18.mctank.domain.Reservation;
import company18.mctank.repository.ReservationRepository;

@SpringBootTest
public class ReservationServiceUnitTest {
	
	@Autowired 
	private ReservationRepository reservationRepository;
	
	@Autowired
	private ReservationService reservationService;
	
	private int getIterableSize(Iterable<?> iterable) {
		int counter = 0;
		Iterator<?> iterator = iterable.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			counter++;
		}
		return counter;
	}
	
	// saving tests
	@Test
	void savingTests(){
		// clearing repository
		reservationRepository.deleteAll();
		
		// save with incorrect mcPoint
		reservationService.save("McPoint", "abc", LocalDateTime.now().plusDays(10), "boss");
		assertEquals(this.getIterableSize(reservationService.findAll()), 0);
			
		// McSit Reservation
		reservationService.save("McSit", "sit", LocalDateTime.now().plusDays(10), "boss");
		System.out.println(reservationService.findAll());
		int j = this.getIterableSize(reservationService.findAll());
		assertEquals(this.getIterableSize(reservationService.findAll()), 1);
			
		// McWash Reservation
		reservationService.save("McWash", "wash", LocalDateTime.now().plusDays(10), "boss");
		assertEquals(this.getIterableSize(reservationService.findAll()), 2);
	}
	
	// findByClass() tests
	@Test
	void findByClassTests() {
		// clearing repository
		reservationRepository.deleteAll();
				
		// add some entries
		reservationService.save("McSit", "sit", LocalDateTime.now().plusDays(10), "boss");
		reservationService.save("McSit", "sit1", LocalDateTime.now().plusDays(10), "boss");
		reservationService.save("McSit", "sit2", LocalDateTime.now().plusDays(10), "boss");
		reservationService.save("McWash", "wash", LocalDateTime.now().plusDays(10), "boss");
		
		// testing for Reservation class
		assertEquals(this.getIterableSize(reservationService.findByClass(Reservation.class)), 4);
		
		// testing for McSitReservation class
		assertEquals(this.getIterableSize(reservationService.findByClass(McSitReservation.class)), 3);
		
		// testing for McWashReservation class
		assertEquals(this.getIterableSize(reservationService.findByClass(McWashReservation.class)), 1);
	}
	
	// sortByDate() tests
	@Test
	void sortByDateTest() {
		// preparing list (correct order number given in reservations name)
		LinkedList<Reservation> list = new LinkedList<Reservation>();
		list.add(new McSitReservation("4", LocalDateTime.now().plusDays(10), "boss"));
		list.add(new McSitReservation("3", LocalDateTime.now().plusDays(9), "boss"));
		list.add(new McWashReservation("2", LocalDateTime.now().plusDays(8), "boss"));
		list.add(new McWashReservation("5", LocalDateTime.now().plusDays(11), "boss"));
		list.add(new McSitReservation("1", LocalDateTime.now().plusDays(7), "boss"));
		
		// testing correct order
		Iterator<Reservation> iterator = reservationService.sortByDate(list).iterator();
		int no = 1;
		while(iterator.hasNext()) {
			assertEquals(iterator.next().getName(), String.valueOf(no));
			no++;
		}
	}
}
