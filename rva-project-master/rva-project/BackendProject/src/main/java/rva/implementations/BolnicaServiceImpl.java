package rva.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rva.models.Bolnica;
import rva.repositories.BolnicaRepository;
import rva.services.BolnicaService;

@Component
public class BolnicaServiceImpl implements BolnicaService {
	@Autowired

	private BolnicaRepository repo;

	@Override
	public List<Bolnica> getAll() {
		return repo.findAll();
	}

	@Override
	public boolean existsById(long id) {
		return repo.existsById(id);

	}

	@Override
	public Bolnica create(Bolnica body) {
		return repo.save(body);

	}

	@Override
	public Optional<Bolnica> update(Bolnica body, long id) {
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
	public List<Bolnica> getBolnicaByNaziv(String naziv) {
		return repo.findByNazivContainingIgnoreCase(naziv);
	}

	@Override
	public Optional<Bolnica> findById(long id) {
		return repo.findById(id);
	}

}
