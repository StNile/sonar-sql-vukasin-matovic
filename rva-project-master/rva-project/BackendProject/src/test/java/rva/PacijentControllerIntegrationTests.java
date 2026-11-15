package rva;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;
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

import rva.models.Dijagnoza;
import rva.models.Odeljenje;
import rva.models.Pacijent;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PacijentControllerIntegrationTests {

    static RestTemplate template = new RestTemplate();
    static String apiUrl = "http://localhost:8080/pacijents";
    static long largestId = 0;

    @Test
    @Order(1)
    void getAll() {
        ResponseEntity<List<Pacijent>> response = template.exchange(apiUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Pacijent>>() {
                });

        assertEquals(200, response.getStatusCode().value());
        assertNotEquals(0, response.getBody().size());
    }

    @Test
    @Order(2)
    void getById() {
        int id = 1;

        ResponseEntity<Pacijent> response = template.exchange(apiUrl + "?id=" + id, HttpMethod.GET, null,
                Pacijent.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    @Order(3)
    void getByOdeljenje() {
        int odeljenjeId = 1;

        ResponseEntity<List<Pacijent>> response = template.exchange(apiUrl + "/odeljenje?odeljenjeId=" + odeljenjeId,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Pacijent>>() {
                });

        assertEquals(200, response.getStatusCode().value());

        for (Pacijent p : response.getBody()) {
            assertEquals(odeljenjeId, p.getOdeljenje().getId());
        }
    }

    @Test
    @Order(4)
    void getByDijagnoza() {
        int dijagnozaId = 1;

        ResponseEntity<List<Pacijent>> response = template.exchange(apiUrl + "/dijagnoza?dijagnozaId=" + dijagnozaId,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Pacijent>>() {
                });

        assertEquals(200, response.getStatusCode().value());

        for (Pacijent p : response.getBody()) {
            assertEquals(dijagnozaId, p.getDijagnoza().getId());
        }
    }
    
    @Test
    @Order(5)
    void getByDatumRodjenja() {
        String datumRodjenja = "1990-05-15";

        ResponseEntity<List<Pacijent>> response = template.exchange(apiUrl + "?datumRodjenja=" + datumRodjenja,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Pacijent>>() {
                });

        assertEquals(200, response.getStatusCode().value());
        assertNotEquals(0, response.getBody().size());
    }

    @Test
    @Order(6)
    void createPacijent() {
        Odeljenje odeljenje = new Odeljenje();
        odeljenje.setId(1);

        Dijagnoza dijagnoza = new Dijagnoza();
        dijagnoza.setId(1);

        Pacijent pacijent = new Pacijent();
        pacijent.setIme("Ana");
        pacijent.setPrezime("Pekic");
        pacijent.setDatumRodjenja(new Date());
        pacijent.setZdrOsiguranje(true);
        pacijent.setOdeljenje(odeljenje);
        pacijent.setDijagnoza(dijagnoza);

        HttpEntity<Pacijent> entity = new HttpEntity<Pacijent>(pacijent);

        ResponseEntity<Pacijent> response = template.exchange(apiUrl, HttpMethod.POST, entity, Pacijent.class);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(pacijent.getIme(), response.getBody().getIme());
        assertEquals(pacijent.getPrezime(), response.getBody().getPrezime());
        assertEquals(pacijent.isZdrOsiguranje(), response.getBody().isZdrOsiguranje());
        assertEquals(1, response.getBody().getOdeljenje().getId());
        assertEquals(1, response.getBody().getDijagnoza().getId());

        if (largestId < response.getBody().getId())
            largestId = response.getBody().getId();
    }

    @Test
    @Order(7)
    void updatePacijent() {
        Odeljenje odeljenje = new Odeljenje();
        odeljenje.setId(1);

        Dijagnoza dijagnoza = new Dijagnoza();
        dijagnoza.setId(1);

        Pacijent pacijent = new Pacijent();
        pacijent.setIme("Tara");
        pacijent.setPrezime("Pekic");
        pacijent.setDatumRodjenja(new Date());
        pacijent.setZdrOsiguranje(false);
        pacijent.setOdeljenje(odeljenje);
        pacijent.setDijagnoza(dijagnoza);

        HttpEntity<Pacijent> entity = new HttpEntity<Pacijent>(pacijent);

        ResponseEntity<Pacijent> response = template.exchange(apiUrl + "?id=" + largestId, HttpMethod.PUT, entity,
                Pacijent.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(pacijent.getIme(), response.getBody().getIme());
        assertEquals(pacijent.getPrezime(), response.getBody().getPrezime());
        assertEquals(pacijent.isZdrOsiguranje(), response.getBody().isZdrOsiguranje());
        assertEquals(1, response.getBody().getOdeljenje().getId());
        assertEquals(1, response.getBody().getDijagnoza().getId());
    }

    @Test
    @Order(8)
    void deletePacijent() {
        ResponseEntity<?> response = template.exchange(apiUrl + "?id=" + largestId, HttpMethod.DELETE, null,
                Object.class);

        assertEquals(204, response.getStatusCode().value());
        assertNull(response.getBody());
    }
}