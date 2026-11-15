package rva.implementations;

import java.util.List;
import java.util.Optional;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rva.models.Dijagnoza;
import rva.models.Pacijent;
import rva.models.Odeljenje;
import rva.repositories.PacijentRepository;
import rva.services.PacijentService;

@Component
public class PacijentServiceImpl implements PacijentService {
	@Autowired

	private PacijentRepository repo;

	@Override
	public List<Pacijent> getAll() {
		return repo.findAll();
	}

	@Override
	public boolean existsById(long id) {
		return repo.existsById(id);
	}

	@Override
	public Pacijent create(Pacijent body) {
		return repo.save(body);
	}

	@Override
	public Optional<Pacijent> update(Pacijent body, long id) {
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
	public List<Pacijent> getPacijentByDatumRodjenja(Date datumRodjenja) {
		return repo.findByDatumRodjenja(datumRodjenja);
	}

	@Override
	public List<Pacijent> getPacijentByOdeljenje(Odeljenje odeljenje) {
		return repo.findByOdeljenje(odeljenje);
	}

	@Override
	public List<Pacijent> getPacijentByDijagnoza(Dijagnoza dijagnoza) {
		return repo.findByDijagnoza(dijagnoza);
	}

	@Override
	public Optional<Pacijent> findById(long id) {
		return repo.findById(id);
	}

}
