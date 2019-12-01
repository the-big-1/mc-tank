package company18.mctank.service;


import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import company18.mctank.forms.NewItemForm;
import company18.mctank.repository.ItemsRepository;

class ItemServiceUnitTest{
	
	@Test
	void createNewProductTest() {
		
		ItemsRepository itemsRepository = Mockito.mock(ItemsRepository.class);
		Mockito.when(itemsRepository.save(Mockito.any())).then(i -> i.getArgument(0));
		
//		ItemsService service = new ItemsService(items);
//
//		var form = new NewItemForm("prod1", "1.30", new ArrayList<String>());
//
//
//		assertThat(service.createNewProduct(form)).isNotNull();  				// testing if an new Product is created
//
		
		
		//test various inputs for money like with . ; , ; € ; etc
		
		// test if adding Categories works properly
		
		
		
	}
}