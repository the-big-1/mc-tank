package kickstart.ReservationPrototype;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;

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
		return "reservation";
	}

}
