package br.com.jcaguiar.ecommerce.service;

import br.com.jcaguiar.ecommerce.Console;
import br.com.jcaguiar.ecommerce.model.Marca;
import br.com.jcaguiar.ecommerce.model.Produto;
import br.com.jcaguiar.ecommerce.projection.MasterGET;
import br.com.jcaguiar.ecommerce.repository.MarcaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarcaService extends MasterService<Marca, Short> {

	public MarcaService(MarcaRepository jpaRepo) {
		super(jpaRepo);
	}
	
	public List<Marca> findByProduto(Produto produto){
		System.out.printf("CONSULTANDO MARCA\n");
		List<Marca> marcas = new ArrayList<Marca>();
		List<String> nomes = ((MarcaRepository) JPA_REPO).findMarcasDoProduto( produto );
		nomes.forEach(n->{
			marcas.add( Marca.builder().nome(n).build() );
		});
		return marcas;
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
	public MasterGET findEntidade(Marca entidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MasterGET findEntidadeAdm(Marca entidade) {
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

	public Marca validarByNome(String nome) {
		Console.log("<MARCA-SERVICE>", +1);
		List<Marca> marcas = findEntentyByNome(nome);
		Marca marca;
		if( marcas.size() == 0 ) {
			marca = Marca.builder()
					.nome(nome)
					.build();
			JPA_REPO.saveAndFlush(marca);
			Console.log(String.format("Criada nova Marca: %s", nome));
		}
		else {
			marca = marcas.get(0);
			Console.log(String.format("Marca %s identificada", nome));
		}
		Console.log("</MARCA-SERVICE>", -1);
		return marca;
	}
	
	private List<Marca> findEntentyByNome(String nome) {
		List<Marca> marcas = ((MarcaRepository) JPA_REPO).findAllByNomeContaining(nome);
		Console.log("Marcas coletadas: " + marcas.size() );
		return marcas;
	}
	
	public void deletar(Marca marca) {
		((MarcaRepository) JPA_REPO).delete(marca);
	}

}
