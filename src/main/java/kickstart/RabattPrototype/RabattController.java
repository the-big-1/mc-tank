package kickstart.RabattPrototype;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RabattController {

	@GetMapping(value="/rabatt")
	public String rabatt(){

		return "rabatt";
	}

	@PostMapping(value="/rabatt")
	public String rabattcode(@RequestParam("rabattcode") String Rabattcode, double gesamtpreis, double endpreis){

		gesamtpreis = 35.76;
		if(Rabattcode == "McTen"){
			endpreis = gesamtpreis * 0.9;
		} else if(Rabattcode == "McFive"){
			endpreis = gesamtpreis * 0.95;
		} endpreis = gesamtpreis;

		return "rabatt";
	}
}
