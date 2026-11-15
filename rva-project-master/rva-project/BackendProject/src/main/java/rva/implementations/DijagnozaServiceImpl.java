package rva.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rva.models.Dijagnoza;
import rva.repositories.DijagnozaRepository;
import rva.services.DijagnozaService;

@Component

public class DijagnozaServiceImpl implements DijagnozaService {
	@Autowired
	private DijagnozaRepository repo;

	@Override
	public List<Dijagnoza> getAll() {
		return repo.findAll();

	}

	@Override
	public boolean existsById(long id) {
		return repo.existsById(id);

	}

	@Override
	public Dijagnoza create(Dijagnoza body) {
		return repo.save(body);

	}

	@Override
	public Optional<Dijagnoza> update(Dijagnoza body, long id) {
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
	public List<Dijagnoza> getDijagnozaByOznaka(String oznaka) {
		return repo.findByOznakaContainingIgnoreCase(oznaka);
	}

	@Override
	public Optional<Dijagnoza> findById(long id) {
		return repo.findById(id);
	}

}
