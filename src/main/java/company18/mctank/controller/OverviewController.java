package company18.mctank.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Overview controller.
 */
@Controller
@PreAuthorize("hasRole('ADMIN')")
public class OverviewController {

	/**
	 * Overview page.
	 *
	 * @return overview
	 */
	@GetMapping("/overview")
	String showOverviewPage() {
		return "overview";
	}
}
