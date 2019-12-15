package company18.mctank.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Object to parse gas pump data into.
 * @author CS
 */
@XmlRootElement(name="gasoline-pump")
public class GasPump {
	public static final String DIESEL = "Diesel";
	public static final String SUPER_BENZIN = "Super Benzin";
	@XmlElement(name="number")
	private int number;
	
	@XmlElement(name="fueltype")
	private String fuelType;
	
	@XmlElement(name="fuelquantity")
	private float fuelQuantity;

	public int getNumber() {
		return this.number;
	}

	public String getFuelType() {
		return "diesel fuel".equals(this.fuelType) ? DIESEL : SUPER_BENZIN;
	}

	public float getFuelQuantity() {
		return this.fuelQuantity;
	}
}