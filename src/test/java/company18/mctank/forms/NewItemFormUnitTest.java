package company18.mctank.forms;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

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
    }

    @Test
    void setPrice() {
    }

	@Test
	void setProductCategories() {
	}
}