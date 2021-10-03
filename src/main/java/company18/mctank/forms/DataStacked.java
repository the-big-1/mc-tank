package company18.mctank.forms;

import company18.mctank.domain.McTankOrder;
import org.salespointframework.catalog.Product;
import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.order.OrderLine;
import org.salespointframework.time.Interval;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataStacked {
	private Map<LocalDate,Integer> amountsOnMcWash = new HashMap<>();
	private Map<LocalDate,Integer> amountsOnMcSit = new HashMap<>();
	private Map<LocalDate,Integer> amountsOnMcDrive = new HashMap<>();
	private Map<LocalDate,Integer> amountsOnMcZapf = new HashMap<>();

	public DataStacked(List<McTankOrder> orders, Map<ProductIdentifier, Product> products) {
		for (McTankOrder order : orders){
			for (OrderLine line : order.getOrderLines()){
				ProductIdentifier tmpId = line.getProductIdentifier();
				Product tmpProd = products.get(tmpId);
				this.arrangeProduct(tmpProd, order.getDateCreated().toLocalDate());
			}
		}
	}

	private void arrangeProduct(Product product, LocalDate date) {
		if (product == null) return;
		Set<String> categories = product.getCategories().toSet();
		if (categories.contains("McZapf")) {
			updateValue(date, this.amountsOnMcZapf);
		}
		if (categories.contains("McDrive")) {
			updateValue(date, this.amountsOnMcDrive);
		}
		if (categories.contains("McSit")) {
			updateValue(date, this.amountsOnMcSit);
		}
		if (categories.contains("McWash")) {
			updateValue(date, this.amountsOnMcWash);
		}
	}

	private void updateValue(LocalDate date, Map<LocalDate, Integer> map){
		map.merge(date, 1, Integer::sum);
	}

	public Map<LocalDate, Integer> getAmountsOnMcWash() {
		return amountsOnMcWash;
	}

	public void setAmountsOnMcWash(Map<LocalDate, Integer> amountsOnMcWash) {
		this.amountsOnMcWash = amountsOnMcWash;
	}

	public Map<LocalDate, Integer> getAmountsOnMcSit() {
		return amountsOnMcSit;
	}

	public void setAmountsOnMcSit(Map<LocalDate, Integer> amountsOnMcSit) {
		this.amountsOnMcSit = amountsOnMcSit;
	}

	public Map<LocalDate, Integer> getAmountsOnMcDrive() {
		return amountsOnMcDrive;
	}

	public void setAmountsOnMcDrive(Map<LocalDate, Integer> amountsOnMcDrive) {
		this.amountsOnMcDrive = amountsOnMcDrive;
	}

	public Map<LocalDate, Integer> getAmountsOnMcZapf() {
		return amountsOnMcZapf;
	}

	public void setAmountsOnMcZapf(Map<LocalDate, Integer> amountsOnMcZapf) {
		this.amountsOnMcZapf = amountsOnMcZapf;
	}
}
