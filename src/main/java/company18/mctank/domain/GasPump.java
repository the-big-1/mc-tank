package company18.mctank.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
		return "diesel fuel".equals(this.fuelType) ? "Diesel" : "Super Benzin";
	}

	public float getFuelQuantity() {
		return this.fuelQuantity;
	}
}