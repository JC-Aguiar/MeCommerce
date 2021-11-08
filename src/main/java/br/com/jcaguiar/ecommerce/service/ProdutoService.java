package br.com.jcaguiar.ecommerce.service;

import br.com.jcaguiar.ecommerce.Console;
import br.com.jcaguiar.ecommerce.dto.ProdutoPOST;
import br.com.jcaguiar.ecommerce.model.Produto;
import br.com.jcaguiar.ecommerce.projection.MasterGET;
import br.com.jcaguiar.ecommerce.repository.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProdutoService extends MasterService<Produto, Integer> {
	
	@Autowired ModelMapper modelMapper;
	
	public ProdutoService(ProdutoRepository jpaRepo) {
		super(jpaRepo);
	}
	
	public List<Produto> findAll(Example<Produto> produtoExemplo) {
		//Realizando consulta
		List<Produto> produtos = ((ProdutoRepository) JPA_REPO).findAll(produtoExemplo);
		Console.log("[PRODUTO-SERVICE] Total: " + produtos.size());
		return produtos;
	}
	
	public List<Produto> findAllFiltros(ProdutoPOST produtoPost) {
		/**
		 * java.lang.IllegalArgumentException: Parameter value [TypedExample{probe=Produto(id=null, categoria=null, 
		 * marca=[], nome=null, descricao=null, modelo=null, valor=null, estoque=null, tamanho=P, medidas=null, 
		 * material=null, codigo=null, fornece=[], imagem=[], comentario=[], acessos=null, votos=null, nota=null), 
		 * matcher=TypedExampleMatcher{nullHandler=IGNORE, defaultStringMatcher=DEFAULT, 
		 * propertySpecifiers=org.springframework.data.domain.ExampleMatcher$PropertySpecifiers@0, ignoredPaths=[], 
		 * defaultIgnoreCase=false, mode=ALL}}] did not match expected type [br.com.jcaguiar.ecommerce.model.Produto (n/a)]
		 */
		//Declarando variáveis
		String nome = produtoPost.getNome();
		String descricao = produtoPost.getDescricao();
		List<String> materiais;
		List<String> marcas = produtoPost.getMarcaNome();
		try {
			materiais = Arrays.asList( produtoPost.getMaterial().split(",") );
		}
		catch (Exception ex) { }
		//Anulando atributos
		produtoPost.setNome(null);
		produtoPost.setDescricao(null);
		produtoPost.setMaterial(null);
		produtoPost.setMarcaNome(null);
		//Convertendo DTO para Entidade
		Produto produtoExemplo = modelMapper.map(produtoPost, Produto.class);
		produtoExemplo.resetDatas();
		//Colentado Produtos com base nos filtros
		List<Produto> produtos = null;
		Console.log("[PRODUTO-SERVICE] Cadastros identificados: " + produtos.size());
		//Retornando lista de Produtos
		return produtos;
	}

	@Override
	public List<? extends MasterGET> findTodos(Sort ordene) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends MasterGET> findTodosAdm(Sort ordene) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MasterGET findId(Integer id) {
		return ((ProdutoRepository) JPA_REPO).findByIdLimited(id);
	}

	@Override
	public MasterGET findIdAdm(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MasterGET findEntidade(Produto entidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MasterGET findEntidadeAdm(Produto entidade) {
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
	
	public void removeAll() {
		((ProdutoRepository) JPA_REPO).deleteAll();
	}


	@Override
	public List<? extends MasterGET> findTodos() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<? extends MasterGET> findTodosAdm() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
