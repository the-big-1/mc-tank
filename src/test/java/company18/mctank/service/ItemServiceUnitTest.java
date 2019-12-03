package company18.mctank.service;


import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import company18.mctank.service.ItemsService;

@SpringBootTest
class ItemServiceUnitTest{
	
	@Autowired
	private ItemsService service;
	
	
	@Test
	void createNewProductTest() {
		
		List<String> list = new ArrayList<String>();
		list.add("McZapf");
		list.add("McSit");
		list.add("McDrive");
		list.add("McWash");
		
		//empty inputs for name and cost/price already tested in form
		
		// testing different inputs for the cost/price		
		assertThat(service.createNewProduct("Cola 0,5", "1.30", list)).isNotNull();  	//with .
		assertThat(service.createNewProduct("Cola 0,5", "1,30", list)).isNotNull();		//with ,
		assertThat(service.createNewProduct("Cola 0,5", "1.30 €", list)).isNotNull(); 	//with €
		
	}
}