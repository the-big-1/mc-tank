package company18.mctank.controller;

import java.time.LocalDateTime;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import company18.mctank.domain.Reservation;
import company18.mctank.domain.McSitReservation;
import company18.mctank.domain.McWashReservation;
import company18.mctank.forms.ReservationForm;
import company18.mctank.service.ReservationService;

@Controller
public class ReservationController {
	private final ReservationService reservationService;
	
	public ReservationController(ReservationService reservationService) {
		Assert.notNull(reservationService, "ReservationService must not be null");
		this.reservationService = reservationService;
	}
	
	@GetMapping(value="/reservation")
	public String reservations(Model model) {
		Iterable<Reservation> reservs = ReservationService.sortByDate(reservationService.findByClass(McSitReservation.class));
		model.addAttribute("mcsitreservations", reservs);
		reservs = ReservationService.sortByDate(reservationService.findByClass(McWashReservation.class));
		model.addAttribute("mcwashreservations", reservs);
		model.addAttribute("form", new ReservationForm());
		return "reservation";
	}
	
	@PostMapping(value="/reservation")
	public String reserve(@Valid ReservationForm form, BindingResult result) {
		// simply redirect if there is errors for now
		if (result.hasErrors()) return "redirect:/reservation";
		
		// else save and redirect
		try {
			reservationService.save(form.getMcPoint(), form.getName(), LocalDateTime.of(form.getDate(), form.getTime()));
		}
		catch(IllegalArgumentException e){
			// LocalDateTime given to save() is before now 
		}
		return "redirect:/reservation";
	}
	
	@PostMapping(value="/delete-reservation/{id}")
	public String delete(@PathVariable long id) {
		reservationService.deleteById(id);
		return "redirect:/reservation";
	}
}
