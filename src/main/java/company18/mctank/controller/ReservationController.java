package company18.mctank.controller;

import company18.mctank.domain.McSitReservation;
import company18.mctank.domain.McWashReservation;
import company18.mctank.forms.ReservationForm;
import company18.mctank.service.CustomerService;
import company18.mctank.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Locale;

/**
 * Controller for creating and handling reservations.
 */
@Controller
@PreAuthorize("hasAnyRole({'ADMIN', 'MANAGER'})")
public class ReservationController {
	@Autowired
	private ReservationService reservationService;

	@Autowired
	private CustomerService customerService;
	
	/**
	 * Adds all reservations sorted by McPoint and date to template.
	 * @param model model
	 * @return views name
	 */
	@GetMapping(value="/reservation")
	public String reservations(Model model) {
		List<Class> points = List.of(McSitReservation.class, McWashReservation.class);
		model.addAttribute("reservations", this.reservationService.findReservationsFor(points));
		if (customerService.isAdmin()) {
			return "reservation-management";
		} else if (customerService.isManager()) {
			return "reservation";
		}
		return "reservation";
	}
	
	/**
	 * Saves reservation if date is after now.
	 * @param form The {@link ReservationForm} containing reservations data
	 * @param result {@link BindingResult} to check for validation errors
	 * @return views name
	 */
	@PostMapping(value="/reservation")
	public String reserve(@RequestBody ReservationForm form, BindingResult result) {
		// simply redirect if there is errors for now
		if (result.hasErrors()) {
			return "redirect:/reservation";
		}
		// else save and redirect
		try {
			reservationService.save(form);
		} catch(IllegalArgumentException e){
			// LocalDateTime given to save() is before now 
		}
		return "redirect:/reservation";
	}

	/**
	 * Deletes reservations by id.
	 * @param id of reservation to be deleted
	 * @return views name
	 */
	@PostMapping(value="/reservation/delete/{id}")
	public String delete(@PathVariable long id) {
		reservationService.deleteById(id);
		return "redirect:/reservation";
	}
	
	/**
	 * Ajax mapping to check if McSit reservation is possible.
	 * @param persons number of persons.
	 * @param timeStr String of pattern 'yyyy/MM/dd - hh:mm a'
	 * @return if reservation is possible
	 */
	@GetMapping("/reservation/possible")
	public ResponseEntity<Boolean> reservationPossible(@RequestParam("personCount") int persons, @RequestParam("resTime") String timeStr){
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("yyyy/MM/dd - hh:mm a").toFormatter(Locale.US);
		LocalDateTime time = LocalDateTime.parse(timeStr, formatter);
		return ResponseEntity.ok(this.reservationService.mcSitReservationPossible(persons, time));
	}
}
