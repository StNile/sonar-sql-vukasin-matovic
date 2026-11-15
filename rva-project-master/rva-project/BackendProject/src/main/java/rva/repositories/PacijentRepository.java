package rva.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rva.models.Dijagnoza;
import rva.models.Odeljenje;
import rva.models.Pacijent;

@Repository
public interface PacijentRepository extends JpaRepository<Pacijent, Long> {
	List<Pacijent> findByDatumRodjenja(Date datumRodjenja);

	List<Pacijent> findByOdeljenje(Odeljenje odeljenje);

	List<Pacijent> findByDijagnoza(Dijagnoza dijagnoza);
}
