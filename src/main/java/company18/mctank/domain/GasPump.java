package company18.mctank.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="gasoline-pump")
public class GasPump {
	@XmlElement
	private int number;
	
	@XmlElement
	private String fueltype;
	
	@XmlElement
	private float fuelquantity;
	
	public GasPump() {}
}
/** Get all Pumps
int maxPump = 12;
GasPump[] pumps = new GasPump[maxPump];
for (int i = 1; i <= maxPump; i++) {
	try {
		pumps[i-1] = new RestTemplate().getForObject("https://jannusch.xyz/gasoline_pump/"+i, GasPump.class);
	}
	catch (Exception e) {
		pumps[i-1] = null;
	}
}**/
