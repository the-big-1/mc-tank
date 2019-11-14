package kickstart.ReservationPrototype;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReservationController {
	private final ReservationRepository repo;
	
	public ReservationController(ReservationRepository repo) {
		Assert.notNull(repo, "ReservationRepository must not be null");
		this.repo = repo;
	}
	
	@GetMapping(value="/reservation")
	public String reservations(Model model) {
		Iterable<Reservation> reservs = repo.findAll();
		model.addAttribute("reservations", reservs);
		model.addAttribute("form", new ReservationForm());
		return "reservation";
	}
	
	@PostMapping(value="/reservation")
	public String reserve(@Valid ReservationForm form, Errors result) {
		repo.save(new Reservation(form.getName(), LocalDateTime.of(form.getDate(), form.getTime())));
		return "redirect:/reservation";
	}
}
