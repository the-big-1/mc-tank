package company18.mctank.forms;

import javax.validation.constraints.NotEmpty;

public class LicensePlateForm {
	
	@NotEmpty
	private final String licenseplate;
	
	public LicensePlateForm( String licenseplate){
		this.licenseplate=licenseplate;
	}
	
	public String getLicensePlate(){
		return licenseplate;
	}
}