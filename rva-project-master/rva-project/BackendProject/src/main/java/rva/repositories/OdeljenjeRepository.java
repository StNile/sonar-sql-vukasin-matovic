package rva.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rva.models.Bolnica;
import rva.models.Odeljenje;

@Repository
public interface OdeljenjeRepository extends JpaRepository<Odeljenje, Long> {

	List<Odeljenje> findByNazivContainingIgnoreCase(String naziv);

	List<Odeljenje> findByBolnica(Bolnica bolnica);
}
