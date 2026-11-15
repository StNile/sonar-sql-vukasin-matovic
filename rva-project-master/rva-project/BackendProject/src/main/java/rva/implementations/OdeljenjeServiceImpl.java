package rva.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rva.models.Bolnica;
import rva.models.Odeljenje;
import rva.repositories.OdeljenjeRepository;
import rva.services.OdeljenjeService;

@Component
public class OdeljenjeServiceImpl implements OdeljenjeService {
	@Autowired
	private OdeljenjeRepository repo;

	@Override
	public List<Odeljenje> getAll() {
		return repo.findAll();
	}

	@Override
	public boolean existsById(long id) {
		return repo.existsById(id);
	}

	@Override
	public Odeljenje create(Odeljenje body) {
		return repo.save(body);
	}

	@Override
	public Optional<Odeljenje> update(Odeljenje body, long id) {
		if (existsById(id)) {
			body.setId(id);
			return Optional.of(repo.save(body));
		}
		return Optional.empty();
	}

	@Override
	public void delete(long id) {
		repo.deleteById(id);
	}

	@Override
	public List<Odeljenje> getOdeljenjeByNaziv(String naziv) {
		return repo.findByNazivContainingIgnoreCase(naziv);
	}

	@Override
	public List<Odeljenje> getOdeljenjeByBolnica(Bolnica bolnica) {
		return repo.findByBolnica(bolnica);
	}

	@Override
	public Optional<Odeljenje> findById(long id) {
		return repo.findById(id);
	}

}
