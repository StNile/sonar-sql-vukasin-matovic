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

import rva.models.Dijagnoza;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DijagnozaControllerIntegrationTests {

    static RestTemplate template = new RestTemplate();
    static String apiUrl = "http://localhost:8080/dijagnozas";
    static long largestId = 0;

    @Test
    @Order(1)
    void getAll() {
        ResponseEntity<List<Dijagnoza>> response = template.exchange(apiUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Dijagnoza>>() {
                });

        assertEquals(200, response.getStatusCode().value());
        assertNotEquals(0, response.getBody().size());
    }

    @Test
    @Order(2)
    void getById() {
        int id = 1;

        ResponseEntity<Dijagnoza> response = template.exchange(apiUrl + "?id=" + id, HttpMethod.GET, null,
                Dijagnoza.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    @Order(3)
    void getByOznaka() {
        String oznaka = "J11"; 

        ResponseEntity<List<Dijagnoza>> response = template.exchange(apiUrl + "?oznaka=" + oznaka, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Dijagnoza>>() {
                });

        assertEquals(200, response.getStatusCode().value());

        for (Dijagnoza d : response.getBody()) {
            assertTrue(d.getOznaka().contains(oznaka));
        }
    }

    @Test
    @Order(4)
    void createDijagnoza() {
        Dijagnoza dijagnoza = new Dijagnoza();

        dijagnoza.setNaziv("Dijabetes");
        dijagnoza.setOpis("Povišen šećer u krvi");
        dijagnoza.setOznaka("S29");

        HttpEntity<Dijagnoza> entity = new HttpEntity<Dijagnoza>(dijagnoza);

        ResponseEntity<Dijagnoza> response = template.exchange(apiUrl, HttpMethod.POST, entity, Dijagnoza.class);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(dijagnoza.getNaziv(), response.getBody().getNaziv());
        assertEquals(dijagnoza.getOpis(), response.getBody().getOpis());
        assertEquals(dijagnoza.getOznaka(), response.getBody().getOznaka());

        if (largestId < response.getBody().getId())
            largestId = response.getBody().getId();
    }

    @Test
    @Order(5)
    void updateDijagnoza() {
        Dijagnoza dijagnoza = new Dijagnoza();

        dijagnoza.setNaziv("Dijabetes");
        dijagnoza.setOpis("Poremećeno stanje šećera u krvi");
        dijagnoza.setOznaka("S29");

        HttpEntity<Dijagnoza> entity = new HttpEntity<Dijagnoza>(dijagnoza);

        ResponseEntity<Dijagnoza> response = template.exchange(apiUrl + "?id=" + largestId, HttpMethod.PUT, entity,
                Dijagnoza.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(dijagnoza.getNaziv(), response.getBody().getNaziv());
        assertEquals(dijagnoza.getOpis(), response.getBody().getOpis());
        assertEquals(dijagnoza.getOznaka(), response.getBody().getOznaka());
    }

    @Test
    @Order(6)
    void deleteDijagnoza() {
        ResponseEntity<?> response = template.exchange(apiUrl + "?id=" + largestId, HttpMethod.DELETE, null,
                Object.class);

        assertEquals(204, response.getStatusCode().value());
        assertNull(response.getBody());
    }
}