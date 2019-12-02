package company18.mctank.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class OverviewController {

	@RequestMapping(value = "/overview", method = RequestMethod.GET)
	String showOverviewPage() {
		return "overview";
	}
}
