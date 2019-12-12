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
	
	public GasPump() {}

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
// Get  Pump:     GasPump pump = new RestTemplate().getForObject("https://jannusch.xyz/gasoline_pump/"+number, GasPump.class);

