package company18.mctank.controller;

import java.time.LocalDateTime;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import company18.mctank.domain.Reservation;
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
		Iterable<Reservation> reservs = ReservationService.sortByDate(reservationService.findAll());
		model.addAttribute("reservations", reservs);
		model.addAttribute("form", new ReservationForm());
		return "reservation";
	}
	
	@PostMapping(value="/reservation")
	public String reserve(@Valid ReservationForm form, BindingResult result) {
		// simply redirect if there is errors for now
		if (result.hasErrors()) return "redirect:/reservation";
		
		// else save and redirect
		reservationService.save(form.getMcPoint(), form.getName(), LocalDateTime.of(form.getDate(), form.getTime()));
		return "redirect:/reservation";
	}
}
