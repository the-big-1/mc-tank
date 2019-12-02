package company18.mctank.domain;

public enum CustomerRoles {
	CUSTOMER("CUSTOMER"),
	MANAGER("MANAGER"),
	ADMIN("ADMIN");

	String role;

	CustomerRoles(String role) {
		this.role = role;
	}

	public String getRole(){
		return "ROLE_" + this.role;
	}
}
