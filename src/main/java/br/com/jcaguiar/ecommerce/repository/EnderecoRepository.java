package br.com.jcaguiar.ecommerce.repository;

import br.com.jcaguiar.ecommerce.model.Endereco;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

	List<Endereco> findByRuaContaining(String rua, Sort ordene);

	List<Endereco> findByBairroContaining(String bairro, Sort ordene);

	List<Endereco> findByCepContaining(String cep, Sort ordene);

	//TODO: revisar SQL EnderecoRepository
//	@Query("SELECT e FROM endereco e, cliente c WHERE c.endereco = e AND c = ?1")
//	Endereco findEnderecoCliente(Cliente cliente);
//
//	@Query("SELECT e FROM endereco e, cliente c WHERE c.endereco = e AND c.id = ?1")
//	Endereco findEnderecoClienteId(int clienteId);
//
//	@Query("SELECT e FROM endereco e, cliente c WHERE c.endereco = e AND c = ?1")
//	List<Endereco> findOutrosEnderecosCliente(Cliente cliente);
//
//	@Query("SELECT e FROM endereco e, cliente c WHERE c.endereco = e AND c.id = ?1")
//	List<Endereco> findOutrosEnderecosClienteId(int clienteId);
	
}
