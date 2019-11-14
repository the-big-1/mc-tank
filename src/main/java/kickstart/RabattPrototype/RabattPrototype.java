package kickstart.RabattPrototype;

import javax.money.MonetaryAmount;
import javax.validation.constraints.NotEmpty;


public class RabattPrototype{

	@NotEmpty(message = "Gesamtpreis darf nicht leer sein.") //
	private final MonetaryAmount gesamtpreis;

	@NotEmpty(message = "Endpreis darf nicht leer sein.") //
	private final MonetaryAmount endpreis;

	public RabattPrototype(MonetaryAmount gesamtpreis, MonetaryAmount endpreis){
		
		this.gesamtpreis = gesamtpreis;
		this.endpreis = endpreis; 
	}

	public MonetaryAmount getGesamtpreis() {
		return gesamtpreis;
	}

	public MonetaryAmount getEndpreis() {
		return endpreis;
	}


}