package company18.mctank.forms;

import company18.mctank.domain.McTankOrder;
import org.junit.jupiter.api.Test;
import org.salespointframework.catalog.Product;
import org.salespointframework.catalog.ProductIdentifier;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class DataStackedUnitTest {

	public DataStacked testStack = new DataStacked(new LinkedList<McTankOrder>(), new HashMap<ProductIdentifier, Product>());

    @Test
    void getAmountsOnMcWash() {
    	assertNotNull(testStack.getAmountsOnMcWash());
    }

    @Test
    void setAmountsOnMcWash() {
    }

    @Test
    void getAmountsOnMcSit() {
		assertNotNull(testStack.getAmountsOnMcSit());
    }

    @Test
    void setAmountsOnMcSit() {
    }

    @Test
    void getAmountsOnMcDrive() {
		assertNotNull(testStack.getAmountsOnMcDrive());
    }

    @Test
    void setAmountsOnMcDrive() {
    }

    @Test
    void getAmountsOnMcZapf() {
		assertNotNull(testStack.getAmountsOnMcZapf());
    }

    @Test
    void setAmountsOnMcZapf() {
    }
}