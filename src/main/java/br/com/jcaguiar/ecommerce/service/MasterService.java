package br.com.jcaguiar.ecommerce.service;

import br.com.jcaguiar.ecommerce.projection.MasterGET;
import br.com.jcaguiar.ecommerce.repository.MasterRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public abstract class MasterService<OBJ, ID> {

	protected final MasterRepository<OBJ, ID> JPA_REPO;
	
	public MasterService(MasterRepository<OBJ, ID> jpaRepo) {
		this.JPA_REPO = jpaRepo; 
	}
	
	//SALVAR ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	public OBJ salvar(OBJ objeto) {
		return JPA_REPO.save(objeto);
	}
	
	public List<OBJ> saveAll(Iterable<OBJ> objetos) {
		return JPA_REPO.saveAll(objetos);
	}
	
	//BUSQUE TODOS ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	public List<OBJ> findAll() {
		return JPA_REPO.findAll();
	}
	
	public List<OBJ> findAll(Sort ordene) {
		return JPA_REPO.findAll();
	}
	
	public abstract List<? extends MasterGET> findTodos();
	
	public abstract List<? extends MasterGET> findTodos(Sort ordene);
	
	public abstract List<? extends MasterGET> findTodosAdm();
	
	public abstract List<? extends MasterGET> findTodosAdm(Sort ordene);
	
	//BUSQUE POR ID ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	public OBJ findOne(ID id) {
		return JPA_REPO.getOne(id);
	}
	
	public Optional<OBJ> findById(ID id) {
		return JPA_REPO.findById(id);
	}
	
	public abstract MasterGET findId(ID id);
	
	public abstract MasterGET findIdAdm(ID id);
	
	public abstract MasterGET findEntidade(OBJ entidade);
	
	public abstract MasterGET findEntidadeAdm(OBJ entidade);
	
	//BUSQUE POR NOME ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	public abstract List<? extends MasterGET> findByNome(String nome);

	public abstract List<? extends MasterGET> findByNomeAdm(String nome);
	
	public abstract List<? extends MasterGET> findByNomeContaining(String nome);

	public abstract List<? extends MasterGET> findByNomeContainingAdm(String nome);

}
