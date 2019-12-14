package company18.mctank.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Object to parse gas pump data into.
 * @author CS
 */
@XmlRootElement(name="gasoline-pump")
public class GasPump {
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
		return this.fuelType;
	}

	public float getFuelQuantity() {
		return this.fuelQuantity;
	}
}