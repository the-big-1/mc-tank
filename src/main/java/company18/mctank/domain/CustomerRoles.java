package company18.mctank.domain;

import org.salespointframework.useraccount.Role;

public class CustomerRoles {

	public static final Role CUSTOMER = Role.of("CUSTOMER");
	public static final Role MANAGER = Role.of("MANAGER");
	public static final Role ADMIN = Role.of("ADMIN");

	private CustomerRoles(){}

}
