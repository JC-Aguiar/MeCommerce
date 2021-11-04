package br.com.jcaguiar.ecommerce.service;

import br.com.jcaguiar.ecommerce.Console;
import br.com.jcaguiar.ecommerce.model.Setor;
import br.com.jcaguiar.ecommerce.projection.MasterGET;
import br.com.jcaguiar.ecommerce.repository.SetorRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetorService extends MasterService<Setor, Short> {

	public SetorService(SetorRepository jpaRepo) {
		super(jpaRepo);
	}
	
	public Setor validarByNome(String nome) {
		Console.log("<SETOR-SERVICE>", +1);
		List<Setor> setores = findObjectByNome(nome);
		Setor setor;
		if( setores.size() == 0 ) {
			setor = Setor.builder()
					.nome(nome)
					.build();
			JPA_REPO.saveAndFlush(setor);
			Console.log(String.format(
					"Novo Setor criado: %s %d", nome, setor.getId()));
		}
		else {
			setor = setores.get(0);
			Console.log(String.format(
					"Setor %s Identificado", nome));
		}
		Console.log("</SETOR-SERVICE>", -1);
		return setor;
	}

	@Override
	public List<? extends MasterGET> findTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends MasterGET> findTodos(Sort ordene) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends MasterGET> findTodosAdm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends MasterGET> findTodosAdm(Sort ordene) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MasterGET findId(Short id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MasterGET findIdAdm(Short id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MasterGET findEntidade(Setor entidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MasterGET findEntidadeAdm(Setor entidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends MasterGET> findByNome(String nome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends MasterGET> findByNomeAdm(String nome) {
		// TODO Auto-generated method stub
		return null;
	}
	

	public List<Setor> findObjectByNome(String nome) {
		return ((SetorRepository) JPA_REPO).findAllByNomeContaining(nome);
	}


	public List<Setor> findSetorByNomeAdm(String nome) {
		
		return null;
	}

	@Override
	public List<? extends MasterGET> findByNomeContaining(String nome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends MasterGET> findByNomeContainingAdm(String nome) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
