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
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.core.ParameterizedTypeReference;
import rva.models.Odeljenje;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OdeljenjeControllerIntegrationTest {

	static RestTemplate template = new RestTemplate();
	static String apiUrl = "http://localhost:8080/odeljenjes";
	static long largestId = 0;

	@Test
	@Order(1)
	void getAll() {
		ResponseEntity<List<Odeljenje>> response = template.exchange(apiUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Odeljenje>>() {
				});

		assertEquals(200, response.getStatusCode().value());
		assertNotEquals(0, response.getBody().size());

	}

	@Test
	@Order(2)
	void getById() {
		int id = 3;
		ResponseEntity<Odeljenje> response = template.exchange(apiUrl + "?id=" + id, HttpMethod.GET, null,
				Odeljenje.class);

		assertEquals(200, response.getStatusCode().value());
		assertEquals(id, response.getBody().getId());
	}

	@Test
	@Order(3)
	void getByBolnica() {
		int bolnicaId = 3;
		ResponseEntity<List<Odeljenje>> response = template.exchange(apiUrl + "/bolnica?bolnicaId=" + bolnicaId,
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Odeljenje>>() {
				});

		assertEquals(200, response.getStatusCode().value());
		for (Odeljenje o : response.getBody()) {
			assertEquals(bolnicaId, o.getBolnica().getId());
		}
	}

	@Test
	@Order(4)
	void getByNaziv() {
		String naziv = "Pedijatrija";
		ResponseEntity<List<Odeljenje>> response = template.exchange(apiUrl + "?naziv=" + naziv, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Odeljenje>>() {
				});

		assertEquals(200, response.getStatusCode().value());
		for (Odeljenje o : response.getBody()) {
			assertTrue(o.getNaziv().contains(naziv));
		}
	}

	@Test
	@Order(5)
	void createOdeljenje() {
		Odeljenje odeljenje = new Odeljenje();
		odeljenje.setNaziv("Oftamologija");
		odeljenje.setLokacija("Maksima Gorkog 12");
		HttpEntity<Odeljenje> entity = new HttpEntity<Odeljenje>(odeljenje);

		ResponseEntity<Odeljenje> response = template.exchange(apiUrl, HttpMethod.POST, entity, Odeljenje.class);
		assertEquals(201, response.getStatusCode().value());
		assertEquals(odeljenje.getNaziv(), response.getBody().getNaziv());
		assertEquals(odeljenje.getLokacija(), response.getBody().getLokacija());

		if (largestId < response.getBody().getId())
			largestId = response.getBody().getId();

	}

	@Test
	@Order(6)
	void updateOdeljenje() {
		Odeljenje odeljenje = new Odeljenje();
		odeljenje.setNaziv("Oftamologija");
		odeljenje.setLokacija("GrckoSkolska 12");
		HttpEntity<Odeljenje> entity = new HttpEntity<Odeljenje>(odeljenje);

		ResponseEntity<Odeljenje> response = template.exchange(apiUrl + "?id=" + largestId, HttpMethod.PUT, entity,
				Odeljenje.class);
		assertEquals(200, response.getStatusCode().value());
		assertEquals(odeljenje.getNaziv(), response.getBody().getNaziv());
		assertEquals(odeljenje.getLokacija(), response.getBody().getLokacija());

	}

	@Test
	@Order(7)
	void deleteOdeljenje() {
		ResponseEntity<?> response = template.exchange(apiUrl + "?id=" + largestId, HttpMethod.DELETE, null,
				Object.class);
		assertEquals(204, response.getStatusCode().value());
		assertNull(response.getBody());

	}
}
