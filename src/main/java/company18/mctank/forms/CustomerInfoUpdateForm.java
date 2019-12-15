package company18.mctank.forms;

import javax.validation.constraints.NotEmpty;

public class CustomerInfoUpdateForm {

	@NotEmpty(message = "{CustomerInfoUpdateForm.firstname.NotEmpty}")
	private final String firstname;

	@NotEmpty(message = "{CustomerInfoUpdateForm.lastname.NotEmpty}")
	private final String lastname;

	@NotEmpty(message = "{CustomerInfoUpdateForm.email.NotEmpty}")
	private final String email;

	@NotEmpty(message = "{CustomerInfoUpdateForm.mobile.NotEmpty}")
	private final String mobile;

	private final long id;

	public CustomerInfoUpdateForm(String firstname,
								  String lastname,
								  String email,
								  String mobile,
								  long id) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.mobile = mobile;
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getEmail() {
		return email;
	}

	public String getMobile() {
		return mobile;
	}

	public long getId(){
		return id;
	}
}
