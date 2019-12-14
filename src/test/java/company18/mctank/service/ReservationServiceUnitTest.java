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
	private ReservationRepository repo;
	
	@Autowired
	private ReservationService service;
	
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
		repo.deleteAll();
		
		// test findAll()
		assertEquals(this.service.findAll(), this.repo.findAll());
		
		// save with incorrect mcPoint
		this.service.save("McPoint", "abc", LocalDateTime.now().plusDays(10));
		assertEquals(this.getIterableSize(this.service.findAll()), 0);
			
		// McSit Reservation
		service.save("McSit", "sit", LocalDateTime.now().plusDays(10));
		System.out.println(this.service.findAll());
		assertEquals(this.getIterableSize(service.findAll()), 1);
			
		// McWash Reservation
		service.save("McWash", "wash", LocalDateTime.now().plusDays(10));
		assertEquals(this.getIterableSize(this.service.findAll()), 2);
		
		// delete
		service.deleteById(this.service.findAll().iterator().next().getId());
		assertTrue(this.service.findAll().iterator().next().getName().equals("wash"));
		
	}
	
	// findByClass() tests
	@Test
	void findByClassTests() {
		// clearing repository
		repo.deleteAll();
				
		// add some entries
		service.save("McSit", "sit", LocalDateTime.now().plusDays(10));
		service.save("McSit", "sit1", LocalDateTime.now().plusDays(10));
		service.save("McSit", "sit2", LocalDateTime.now().plusDays(10));
		service.save("McWash", "wash", LocalDateTime.now().plusDays(10));
		
		// test with reservations date before now, expecting IllegalArgumentException
		try {
			service.save("McSit", "string", LocalDateTime.now().minusDays(3));
		} catch (Exception e){
			assertEquals(e.getClass(), IllegalArgumentException.class);
		}
		
		
		// testing for Reservation class
		assertEquals(this.getIterableSize(service.findByClass(Reservation.class)), 4);
		
		// testing for McSitReservation class
		assertEquals(this.getIterableSize(service.findByClass(McSitReservation.class)), 3);
		
		// testing for McWashReservation class
		assertEquals(this.getIterableSize(service.findByClass(McWashReservation.class)), 1);		
	}
	
	// sortByDate() tests
	@Test
	void sortByDateTest() {
		// preparing list (correct order number given in reservations name)
		LinkedList<Reservation> list = new LinkedList<Reservation>();
		list.add(new Reservation("4", LocalDateTime.now().plusDays(10)));
		list.add(new McSitReservation("3", LocalDateTime.now().plusDays(9)));
		list.add(new McWashReservation("2", LocalDateTime.now().plusDays(8)));
		list.add(new Reservation("5", LocalDateTime.now().plusDays(11)));
		list.add(new Reservation("1", LocalDateTime.now().plusDays(7)));
		
		// testing correct order
		Iterator<Reservation> iterator = ReservationService.sortByDate(list).iterator();
		int no = 1;
		while(iterator.hasNext()) {
			assertEquals(iterator.next().getName(), String.valueOf(no));
			no++;
		}
	}
}
