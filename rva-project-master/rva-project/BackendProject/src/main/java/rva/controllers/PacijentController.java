package rva.controllers;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import rva.models.Odeljenje;
import rva.models.Pacijent;
import rva.services.DijagnozaService;
import rva.services.OdeljenjeService;
import rva.services.PacijentService;

@RestController
@CrossOrigin
public class PacijentController {

	@Autowired
	private PacijentService service;
	
	@Autowired
	private OdeljenjeService odeljenjeService;
	@Autowired
	private DijagnozaService dijagnozaService;
	@GetMapping("/pacijents")
	public ResponseEntity<?> getPacijent(@RequestParam(required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd") Date datumRodjenja,
			@RequestParam(required = false) Long id) {

		if (datumRodjenja != null && id == null) {
			List<Pacijent> pacijents = service.getPacijentByDatumRodjenja(datumRodjenja);

			if (pacijents.isEmpty())
				return ResponseEntity.status(404)
						.body(String.format("Pacijent with datumRodjenja: %s not found", datumRodjenja));

			return ResponseEntity.ok(pacijents);
		}

		else if (datumRodjenja == null && id != null) {
			Optional<Pacijent> pacijent = service.findById(id);

			if (pacijent.isEmpty())
				return ResponseEntity.status(404).body(String.format("Pacijent with ID: %s not found", id));

			return ResponseEntity.ok(pacijent.get());
		}

		else if (datumRodjenja != null && id != null) {
			return ResponseEntity.status(404).body("Only one query parameter is allowed!");
		}

		else {
			return ResponseEntity.ok(service.getAll());
		}
	}

	@PostMapping("/pacijents")
	public ResponseEntity<?> createPacijent(@RequestBody Pacijent pacijent) {
		Pacijent createdPacijent = service.create(pacijent);
		URI uri = URI.create("/pacijents?id=" + createdPacijent.getId());
		return ResponseEntity.created(uri).body(createdPacijent);
	}

	@PutMapping("/pacijents")
	public ResponseEntity<?> updatePacijent(@RequestBody Pacijent pacijent, @RequestParam long id) {

		Optional<Pacijent> updatedPacijent = service.update(pacijent, id);

		if (updatedPacijent.isEmpty())
			return ResponseEntity.status(404).body(String.format("Resource with an ID: %s not found", id));

		return ResponseEntity.ok(updatedPacijent);
	}

	@DeleteMapping("/pacijents")
	public ResponseEntity<?> deletePacijent(@RequestParam long id) {

		if (!service.existsById(id))
			return ResponseEntity.status(404).body(String.format("Resource with an ID: %s not found", id));

		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/pacijents/odeljenje")
	public ResponseEntity<?> getPacijentbyOdeljenje(@RequestParam Long odeljenjeId){
		Optional<Odeljenje> odeljenje=odeljenjeService.findById(odeljenjeId);
		if(odeljenje.isEmpty()) {
			return ResponseEntity.status(404)
					.body(String.format("Odeljenje with an ID: %s does not exist", odeljenjeId));
			
		}
		List<Pacijent> pacijenti =service.getPacijentByOdeljenje(odeljenje.get());
		if(pacijenti.isEmpty()) {
			return ResponseEntity.status(404)
					.body(String.format("Pacijent with odeljenje ID: %s does not exist", odeljenjeId));
		}
		return ResponseEntity.ok(pacijenti);
		
	}
	
	@GetMapping("/pacijents/dijagnoza")
	public ResponseEntity<?> getPacijentbyDijagnoza(@RequestParam Long dijagnozaId){
		Optional<Dijagnoza> dijagnoza=dijagnozaService.findById(dijagnozaId);
		if(dijagnoza.isEmpty()) {
			return ResponseEntity.status(404)
					.body(String.format("Dijagnoza with an ID: %s does not exist", dijagnozaId));
			
		}
		List<Pacijent> pacijenti = service.getPacijentByDijagnoza(dijagnoza.get());
		if(pacijenti.isEmpty()) {
			return ResponseEntity.status(404)
					.body(String.format("Pacijent with dijagnoza ID: %s does not exist", dijagnozaId));
		}
		return ResponseEntity.ok(pacijenti);
		
	}
}