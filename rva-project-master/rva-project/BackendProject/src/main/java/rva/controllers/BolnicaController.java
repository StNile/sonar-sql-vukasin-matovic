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

import rva.models.Bolnica;
import rva.services.BolnicaService;

@RestController
@CrossOrigin
public class BolnicaController {

	@Autowired
	private BolnicaService service;

	@GetMapping("/bolnicas")
	public ResponseEntity<?> getBolnica(@RequestParam(required = false) String naziv,
			@RequestParam(required = false) Long id) {
		if (naziv != null && id == null) {
			List<Bolnica> bolnicas = service.getBolnicaByNaziv(naziv);
			if (bolnicas.isEmpty())
				return ResponseEntity.status(404).body(String.format("Bolnica with naziv: %s not found", naziv));
			return ResponseEntity.ok(bolnicas);
		} else if (naziv == null && id != null) {
			Optional<Bolnica> bolnica = service.findById(id);
			if (bolnica.isEmpty())
				return ResponseEntity.status(404).body(String.format("Bolnica with ID: %s not found", id));
			return ResponseEntity.ok(bolnica.get());

		} else if (naziv != null && id != null) {
			return ResponseEntity.status(404).body("Only one query parametar is allowed!");
		} else {
			return ResponseEntity.ok(service.getAll());
		}
	}

	@PostMapping("/bolnicas")
	public ResponseEntity<?> createBolnica(@RequestBody Bolnica bolnica) {
		Bolnica createdBolnica = service.create(bolnica);
		URI uri = URI.create("/bolnicas?id=" + createdBolnica.getId());
		return ResponseEntity.created(uri).body(createdBolnica);
	}

	@PutMapping("/bolnicas")
	public ResponseEntity<?> updateBolnica(@RequestBody Bolnica bolnica, @RequestParam long id) {
		Optional<Bolnica> updatedBolnica = service.update(bolnica, id);
		if (updatedBolnica.isEmpty())
			return ResponseEntity.status(404).body(String.format("Resource with an ID: %s not found", id));
		return ResponseEntity.ok(updatedBolnica);
	}

	@DeleteMapping("/bolnicas")
	public ResponseEntity<?> deleteBolnica(@RequestParam long id) {
		if (!service.existsById(id))
			return ResponseEntity.status(404).body(String.format("Resource with an ID: %s not found", id));
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
