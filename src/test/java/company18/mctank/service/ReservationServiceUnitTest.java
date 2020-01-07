package company18.mctank.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

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
	
	// saving tests (+findAll(), deleteById())
	@Test
	void savingTests(){
		// clearing repository
		reservationRepository.deleteAll();
		
		// test findAll()
		assertEquals(this.reservationService.findAll(), this.reservationRepository.findAll());
		
		// save with incorrect mcPoint
		this.reservationService.save("McPoint", "abc", LocalDateTime.now().plusDays(10), "boss");
		assertEquals(this.getIterableSize(this.reservationService.findAll()), 0);
			
		// McSit Reservation
		reservationService.save("McSit", "sit", LocalDateTime.now().plusDays(10), "boss");
		System.out.println(this.reservationService.findAll());
		assertEquals(this.getIterableSize(reservationService.findAll()), 1);
			
		// McWash Reservation
		reservationService.save("McWash", "wash", LocalDateTime.now().plusDays(10), "boss");
		assertEquals(this.getIterableSize(this.reservationService.findAll()), 2);
		
		// delete
		reservationService.deleteById(this.reservationService.findAll().iterator().next().getId());
		assertTrue(this.reservationService.findAll().iterator().next().getName().equals("wash"));
		
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
		
		// test with reservations date before now, expecting IllegalArgumentException
		try {
			reservationService.save("McSit", "string", LocalDateTime.now().minusDays(3), "boss");
		} catch (Exception e){
			assertEquals(e.getClass(), IllegalArgumentException.class);
		}
		
		
		// testing for Reservation class
		assertEquals(this.getIterableSize(reservationService.findByClass(Reservation.class)), 4);
		
		// testing for McSitReservation class
		assertEquals(this.getIterableSize(reservationService.findByClass(McSitReservation.class)), 3);
		
		// testing for McWashReservation class
		assertEquals(this.getIterableSize(reservationService.findByClass(McWashReservation.class)), 1);
	}

	@Test
	void findReservationsFor(){
		assertNotNull(reservationService.findReservationsFor(new ArrayList<Class>()));
	}

	// sortByDate() tests
	@Test
	void sortByDateTest() {
		// preparing list (correct order number given in reservations name)
		LinkedList<Reservation> list = new LinkedList<Reservation>();
		list.add(new McSitReservation("4", LocalDateTime.now().plusDays(10), "boss"));
		list.add(new McSitReservation("3", LocalDateTime.now().plusDays(9), "boss"));
		list.add(new McWashReservation("2", LocalDateTime.now().plusDays(8), "boss"));
		list.add(new McSitReservation("5", LocalDateTime.now().plusDays(11), "boss"));
		list.add(new McWashReservation("1", LocalDateTime.now().plusDays(7), "boss"));
		
		// testing correct order
		Iterator<Reservation> iterator = reservationService.sortByDate(list).iterator();
		int no = 1;
		while(iterator.hasNext()) {
			assertEquals(iterator.next().getName(), String.valueOf(no));
			no++;
		}
	}

	@Test
	void saveTest() {
	}

	@Test
	void getAllEventsForCustomer(){
		//assertNotNull(reservationService.getAllEventsForCustomer());
	}
}
