package company18.mctank.forms;

public class LicensePlateForm {
	
	private final String licenseplate;
	
	private final long id;
	
	public LicensePlateForm( String licenseplate,long id){
		this.licenseplate=licenseplate;
		this.id=id;
	}
	
	public String getLicensePlate(){
		return licenseplate;
	}
	
	public long getId(){
		return id;
	}
}