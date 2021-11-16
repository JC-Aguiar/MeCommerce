package br.com.jcaguiar.ecommerce.repository;

import br.com.jcaguiar.ecommerce.model.Problema;
import org.springframework.stereotype.Repository;

@Repository
public interface ErroRepository extends MasterRepository<Problema, Long> {

}
