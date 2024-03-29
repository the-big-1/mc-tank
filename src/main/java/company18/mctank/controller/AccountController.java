package company18.mctank.controller;

import company18.mctank.forms.CustomerInfoUpdateForm;

import company18.mctank.forms.LicensePlateForm;
import company18.mctank.service.CustomerService;
import company18.mctank.service.OrdersService;
import company18.mctank.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Controller
public class AccountController {

	@Autowired
	CustomerService customerService;

	@Autowired
	OrdersService ordersService;

	@Autowired
	ReservationService reservationService;

	/**
	 * Collect all information about user, that request this mapping.
	 * User will be collected from context {@link CustomerService}
	 * Data that will be collected:
	 *  - All orders for current User {@link OrdersService}
	 *  - All reservation for current User {@link ReservationService}
	 *
	 * @param model model
	 * @return user-account view
	 */
	@GetMapping("/account")
	public String index(Model model) {
		model.addAttribute("orders", ordersService.getAllOrdersForCustomer());
		model.addAttribute("reservations", reservationService.getAllEventsForCustomer());
		model.addAttribute("customer", customerService.getCurrentCustomer());
		return "user-account";
	}

	/**
	 * Update personal information about user, that request this mapping.
	 * Data that will be updated in User {@link company18.mctank.domain.Customer}:
	 *  - First Name
	 *  - Last Name
	 *  - E-mail
	 *  - Mobile number
	 *
	 * @param form contains new personal information {@link CustomerInfoUpdateForm}
	 * @return redirect on "/account" mapping
	 */
	@PostMapping("/account/update")
	public String updateInfo(@Valid CustomerInfoUpdateForm form){
		customerService.updateCustomer(form);
		return "redirect:/account";
	}
	
	@PostMapping("/account/licenseplate")
	public String updateLicensePlate(@Valid LicensePlateForm form) {
		customerService.updateLicensePlate(form);
		return "redirect:/account";
	}

	/**
	 * Update password for user by id {@link company18.mctank.domain.Customer}
	 *
	 * @param password containing new password
	 * @param id containing current UserId
	 * @return redirect on "/account" mapping
	 */
	@PostMapping("/account/new-password")
	public String updatePassword(@RequestParam @NotEmpty String password, @RequestParam @NotEmpty long id){
		customerService.updatePassword(password, id);
		return "redirect:/account";
	}

	/**
	 * Delete user by current id {@link company18.mctank.domain.Customer}
	 *
	 * @param id containing current UserId
	 * @return redirect on "/account" mapping
	 */
	@PostMapping("/account/delete")
	public String deleteAccount(@RequestParam @NotEmpty long id){
		customerService.deleteCustomer(id);
		return "redirect:/logout";
	}

	/**
	 * Delete current Reservation {@link company18.mctank.domain.Reservation} by ID for user,
	 * that request this mapping.
	 * User will be found automatic.
	 *
	 * @param reservationId contains id for reservation
	 * @return user-account view
	 */
	@PostMapping("/account/reservation/delete")
	public String deleteReservation(@RequestParam @NotEmpty long reservationId){
		reservationService.deleteById(reservationId);
		return "redirect:/account";
	}

}
