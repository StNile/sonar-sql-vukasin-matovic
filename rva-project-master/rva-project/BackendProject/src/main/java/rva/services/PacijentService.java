package rva.services;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import rva.models.Dijagnoza;
import rva.models.Odeljenje;
import rva.models.Pacijent;

@Service
public interface PacijentService extends CrudService<Pacijent> {
	List<Pacijent> getPacijentByDatumRodjenja(Date datumRodjenja);

	List<Pacijent> getPacijentByOdeljenje(Odeljenje odeljenje);

	List<Pacijent> getPacijentByDijagnoza(Dijagnoza dijagnoza);

}
