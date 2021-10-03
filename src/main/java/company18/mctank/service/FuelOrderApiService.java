package company18.mctank.service;


import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


/**
 * Service for posting orders and getting fuel prices from api.
 *
 * @author CS
 */
public class FuelOrderApiService {

	private static final String API_URL = "https://jannusch.xyz/order/";

	private enum Fuel {
		GASOLINE,
		DIESEL
	}

	/**
	 * Posts order string to api.
	 *
	 * @param order
	 * @return succesful post
	 */
	public static boolean post(String order) {
		String body = "<order>" + order + "</order>";
		RequestEntity<String> request = RequestEntity
				.post(URI.create(API_URL))
				.contentType(MediaType.APPLICATION_XML)
				.body(body);
		try {
			ResponseEntity<String> answer = new RestTemplate().postForEntity(API_URL, request, String.class);
			if (answer.getStatusCode().equals(HttpStatus.OK)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @return gasoline price
	 */
	public static double getBenzinePrice() {
		return new RestTemplate().getForObject(API_URL + Fuel.GASOLINE.toString(), double.class);
	}

	/**
	 * @return diesel price
	 */
	public static double getDieselPrice() {
		return new RestTemplate().getForObject(API_URL + Fuel.DIESEL.toString(), double.class);
	}

}
