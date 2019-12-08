package company18.mctank.controller;

import java.util.List;
import javax.validation.Valid;

import company18.mctank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import company18.mctank.domain.McSitReservation;
import company18.mctank.domain.McWashReservation;
import company18.mctank.forms.ReservationForm;
import company18.mctank.service.ReservationService;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@PreAuthorize("hasAnyRole({'ADMIN', 'MANAGER'})")
public class ReservationController {
	@Autowired
	private ReservationService reservationService;

	@Autowired
	private CustomerService customerService;
	
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
	
	@PostMapping(value="/reservation/delete/{id}")
	public String delete(@PathVariable long id) {
		reservationService.deleteById(id);
		return "redirect:/reservation";
	}
}
