package company18.mctank.forms;

import javax.validation.constraints.NotEmpty;

public class RegistrationForm {

	@NotEmpty(message = "{RegistrationForm.username.NotEmpty}")
	private final String username;

	@NotEmpty(message = "{RegistrationForm.password.NotEmpty}")
	private final String password;

	public RegistrationForm(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return username;
	}

}