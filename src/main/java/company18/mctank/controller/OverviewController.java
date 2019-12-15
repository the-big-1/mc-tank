package company18.mctank.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Overview controller.
 */
@Controller
@PreAuthorize("hasRole('ADMIN')")
public class OverviewController {

	private boolean fuelWarning = false;

	/**
	 * Overview page.
	 *
	 * @return overview
	 */
	@GetMapping("/overview")
	String showOverviewPage(Model model) {
		model.addAttribute("fuelWarning", fuelWarning);
		return "overview";
	}

	public void setFuelWarning(boolean fuelWarning) {
		this.fuelWarning = fuelWarning;
	}
}
