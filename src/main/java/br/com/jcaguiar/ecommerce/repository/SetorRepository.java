package br.com.jcaguiar.ecommerce.repository;

import br.com.jcaguiar.ecommerce.model.Setor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SetorRepository extends MasterRepository<Setor, Short> {

	Optional<Setor> findAllByNomeContaining(String nome);
	
}
