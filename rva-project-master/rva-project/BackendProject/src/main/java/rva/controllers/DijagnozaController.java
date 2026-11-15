package rva.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rva.models.Dijagnoza;
import rva.services.DijagnozaService;

@RestController
@CrossOrigin
public class DijagnozaController {

	@Autowired
	private DijagnozaService service;

	@GetMapping("/dijagnozas")
	public ResponseEntity<?> getDijagnoza(@RequestParam(required = false) String oznaka,
			@RequestParam(required = false) Long id) {

		if (oznaka != null && id == null) {
			List<Dijagnoza> dijagnozas = service.getDijagnozaByOznaka(oznaka);

			if (dijagnozas.isEmpty())
				return ResponseEntity.status(404).body(String.format("Dijagnoza with oznaka: %s not found", oznaka));

			return ResponseEntity.ok(dijagnozas);
		}

		else if (oznaka == null && id != null) {
			Optional<Dijagnoza> dijagnoza = service.findById(id);

			if (dijagnoza.isEmpty())
				return ResponseEntity.status(404).body(String.format("Dijagnoza with ID: %s not found", id));

			return ResponseEntity.ok(dijagnoza.get());
		}

		else if (oznaka != null && id != null) {
			return ResponseEntity.status(404).body("Only one query parameter is allowed!");
		}

		else {
			return ResponseEntity.ok(service.getAll());
		}
	}

	@PostMapping("/dijagnozas")
	public ResponseEntity<?> createDijagnoza(@RequestBody Dijagnoza dijagnoza) {
		Dijagnoza createdDijagnoza = service.create(dijagnoza);
		URI uri = URI.create("/dijagnozas?id=" + createdDijagnoza.getId());
		return ResponseEntity.created(uri).body(createdDijagnoza);
	}

	@PutMapping("/dijagnozas")
	public ResponseEntity<?> updateDijagnoza(@RequestBody Dijagnoza dijagnoza, @RequestParam long id) {

		Optional<Dijagnoza> updatedDijagnoza = service.update(dijagnoza, id);

		if (updatedDijagnoza.isEmpty())
			return ResponseEntity.status(404).body(String.format("Resource with an ID: %s not found", id));

		return ResponseEntity.ok(updatedDijagnoza);
	}

	@DeleteMapping("/dijagnozas")
	public ResponseEntity<?> deleteDijagnoza(@RequestParam long id) {

		if (!service.existsById(id))
			return ResponseEntity.status(404).body(String.format("Resource with an ID: %s not found", id));

		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}