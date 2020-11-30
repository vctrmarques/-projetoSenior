package joao.io.projetoRest.repository;

import org.springframework.data.repository.CrudRepository;

import joao.io.projetoRest.model.Hospede;

public interface HospedeRepository extends CrudRepository<Hospede, Long> {
	
}