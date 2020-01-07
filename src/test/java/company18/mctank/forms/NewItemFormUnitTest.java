package company18.mctank.forms;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NewItemFormUnitTest {

	public NewItemForm testForm = new NewItemForm("Test", "1.20" , new LinkedList<String>());

    @Test
    void getProductName() {
    	assertEquals(testForm.getProductName(), "Test");
    }

    @Test
    void getPrice() {
    	assertEquals(testForm.getPrice(), "1.20");
    }

    @Test
    void getProductCategories() {
    	assertNotNull(testForm.getProductCategories());
    }

    @Test
    void setProductName() {
    	testForm.setProductName("New");

    	assertEquals(testForm.getProductName(), "New");

    	testForm.setProductName("Test");
    }

    @Test
    void setPrice() {
		testForm.setPrice("1.80");

		assertEquals(testForm.getPrice(), "1.80");

		testForm.setProductName("1.20");
    }

	@Test
	void setProductCategories() {
    	List mcPoints = new LinkedList();
    	mcPoints.add("McTank");
    	mcPoints.add("McSit");

    	testForm.setProductCategories(mcPoints);

    	assertEquals(testForm.getProductCategories(), mcPoints);
	}
}