package company18.mctank.forms;

import javax.validation.constraints.NotEmpty;

public class RegistrationForm {

	@NotEmpty(message = "{RegistrationForm.name.NotEmpty}")
	private final String name;

	@NotEmpty(message = "{RegistrationForm.password.NotEmpty}")
	private final String password;

	public RegistrationForm(String name, String password) {

		this.name = name;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

}