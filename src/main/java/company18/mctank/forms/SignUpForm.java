package company18.mctank.forms;

import javax.validation.constraints.NotEmpty;

public class SignUpForm {

	@NotEmpty(message = "{RegistrationForm.username.NotEmpty}")
	private final String username;

	@NotEmpty(message = "{RegistrationForm.password.NotEmpty}")
	private final String password;

	@NotEmpty(message = "{RegistrationForm.password.NotEmpty}")
	private final String email;

	public SignUpForm(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return username;
	}

	public String getEmail() {
		return email;
	}

}