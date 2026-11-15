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
import rva.models.Odeljenje;
import rva.services.BolnicaService;
import rva.services.OdeljenjeService;

@RestController
@CrossOrigin
public class OdeljenjeController {

	@Autowired
	private OdeljenjeService service;
	@Autowired
	private BolnicaService bolnicaService;

	@GetMapping("/odeljenjes")
	public ResponseEntity<?> getOdeljenje(@RequestParam(required = false) String naziv,
			@RequestParam(required = false) Long id) {

		if (naziv != null && id == null) {
			List<Odeljenje> odeljenjes = service.getOdeljenjeByNaziv(naziv);

			if (odeljenjes.isEmpty())
				return ResponseEntity.status(404).body(String.format("Odeljenje with naziv: %s not found", naziv));

			return ResponseEntity.ok(odeljenjes);
		}

		else if (naziv == null && id != null) {
			Optional<Odeljenje> odeljenje = service.findById(id);

			if (odeljenje.isEmpty())
				return ResponseEntity.status(404).body(String.format("Odeljenje with ID: %s not found", id));

			return ResponseEntity.ok(odeljenje.get());
		}

		else if (naziv != null && id != null) {
			return ResponseEntity.status(404).body("Only one query parameter is allowed!");
		}

		else {
			return ResponseEntity.ok(service.getAll());
		}
	}

	@PostMapping("/odeljenjes")
	public ResponseEntity<?> createOdeljenje(@RequestBody Odeljenje odeljenje) {
		Odeljenje createdOdeljenje = service.create(odeljenje);
		URI uri = URI.create("/odeljenjes?id=" + createdOdeljenje.getId());
		return ResponseEntity.created(uri).body(createdOdeljenje);
	}

	@PutMapping("/odeljenjes")
	public ResponseEntity<?> updateOdeljenje(@RequestBody Odeljenje odeljenje, @RequestParam long id) {

		Optional<Odeljenje> updatedOdeljenje = service.update(odeljenje, id);

		if (updatedOdeljenje.isEmpty())
			return ResponseEntity.status(404).body(String.format("Resource with an ID: %s not found", id));

		return ResponseEntity.ok(updatedOdeljenje);
	}

	@DeleteMapping("/odeljenjes")
	public ResponseEntity<?> deleteOdeljenje(@RequestParam long id) {

		if (!service.existsById(id))
			return ResponseEntity.status(404).body(String.format("Resource with an ID: %s not found", id));

		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/odeljenjes/bolnica")
	public ResponseEntity<?> getOdeljenjebyBolnica(@RequestParam Long bolnicaId){
		Optional<Bolnica> bolnica=bolnicaService.findById(bolnicaId);
		if(bolnica.isEmpty()) {
			return ResponseEntity.status(404)
					.body(String.format("Bolnica with an ID: %s does not exist", bolnicaId));
			
		}
		List<Odeljenje> odeljenja =service.getOdeljenjeByBolnica(bolnica.get());
		if(odeljenja.isEmpty()) {
			return ResponseEntity.status(404)
					.body(String.format("Odeljenje with bolnica ID: %s does not exist", bolnicaId));
		}
		return ResponseEntity.ok(odeljenja);
		
	}
}