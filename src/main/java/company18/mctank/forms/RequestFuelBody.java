package company18.mctank.forms;

public class RequestFuelBody {
	private String fuelType;
	private Integer amount;

	public RequestFuelBody() {
	}

	public RequestFuelBody(String fuelType, Integer amount) {
		this.fuelType = fuelType;
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "RequestFuelBody{" +
				"fuelType='" + fuelType + '\'' +
				", amount=" + amount +
				'}';
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
}
