package company18.mctank.interceptor;

import company18.mctank.service.CustomerService;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interceptor for updating customer last activity date.
 */
public class UserAvailabilityInterceptor extends HandlerInterceptorAdapter {

	private CustomerService customerService;

	public UserAvailabilityInterceptor(CustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * {@inheritDoc}
	 * Checks if users with long inactivity date should be deleted.
	 * Updates current customer last activity date.
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
								HttpServletResponse response,
								Object handler, Exception ex) throws Exception {

		super.afterCompletion(request, response, handler, ex);
		if (customerService.isManager()) {
			this.customerService.deleteLongInactiveUsers();
		}
		this.customerService.updateCurrentCustomerLastActivityDate();
	}
}
