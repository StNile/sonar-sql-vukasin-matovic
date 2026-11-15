package rva;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import rva.models.Bolnica;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BolnicaControllerIntegrationTests {

	static RestTemplate template = new RestTemplate();
	static String apiUrl = "http://localhost:8080/bolnicas";
	static long largestId = 0;

	@Test
	@Order(1)
	void getAll() {
		ResponseEntity<List<Bolnica>> response = template.exchange(apiUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Bolnica>>() {
				});

		assertEquals(200, response.getStatusCode().value());
		assertNotEquals(0, response.getBody().size());
	}

	@Test
	@Order(2)
	void getById() {
		int id = 1;

		ResponseEntity<Bolnica> response = template.exchange(apiUrl + "?id=" + id, HttpMethod.GET, null, Bolnica.class);

		assertEquals(200, response.getStatusCode().value());
		assertEquals(id, response.getBody().getId());
	}

	@Test
	@Order(3)
	void getByNaziv() {
		String naziv = "VMA";

		ResponseEntity<List<Bolnica>> response = template.exchange(apiUrl + "?naziv=" + naziv, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Bolnica>>() {
				});

		assertEquals(200, response.getStatusCode().value());

		for (Bolnica b : response.getBody()) {
			assertTrue(b.getNaziv().contains(naziv));
		}
	}

	@Test
	@Order(4)
	void createBolnica() {
		Bolnica bolnica = new Bolnica();

		bolnica.setNaziv("Betanija");
		bolnica.setAdresa("Majevička 3");
		bolnica.setBudzet(5000000);

		HttpEntity<Bolnica> entity = new HttpEntity<Bolnica>(bolnica);

		ResponseEntity<Bolnica> response = template.exchange(apiUrl, HttpMethod.POST, entity, Bolnica.class);

		assertEquals(201, response.getStatusCode().value());
		assertEquals(bolnica.getNaziv(), response.getBody().getNaziv());
		assertEquals(bolnica.getAdresa(), response.getBody().getAdresa());
		assertEquals(bolnica.getBudzet(), response.getBody().getBudzet());

		if (largestId < response.getBody().getId())
			largestId = response.getBody().getId();
	}

	@Test
	@Order(5)
	void updateBolnica() {
		Bolnica bolnica = new Bolnica();

		bolnica.setNaziv("Betanija");
		bolnica.setAdresa("Majevička 15");
		bolnica.setBudzet(7000000);

		HttpEntity<Bolnica> entity = new HttpEntity<Bolnica>(bolnica);

		ResponseEntity<Bolnica> response = template.exchange(apiUrl + "?id=" + largestId, HttpMethod.PUT, entity,
				Bolnica.class);

		assertEquals(200, response.getStatusCode().value());
		assertEquals(bolnica.getNaziv(), response.getBody().getNaziv());
		assertEquals(bolnica.getAdresa(), response.getBody().getAdresa());
		assertEquals(bolnica.getBudzet(), response.getBody().getBudzet());
	}

	@Test
	@Order(6)
	void deleteBolnica() {
		ResponseEntity<?> response = template.exchange(apiUrl + "?id=" + largestId, HttpMethod.DELETE, null,
				Object.class);

		assertEquals(204, response.getStatusCode().value());
		assertNull(response.getBody());
	}
}