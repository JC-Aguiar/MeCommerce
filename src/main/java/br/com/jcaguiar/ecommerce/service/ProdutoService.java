package br.com.jcaguiar.ecommerce.service;

import br.com.jcaguiar.ecommerce.Console;
import br.com.jcaguiar.ecommerce.dto.ProdutoCsvPOST;
import br.com.jcaguiar.ecommerce.dto.ProdutoPOST;
import br.com.jcaguiar.ecommerce.model.*;
import br.com.jcaguiar.ecommerce.projection.MasterGET;
import br.com.jcaguiar.ecommerce.repository.ProdutoRepository;
import br.com.jcaguiar.ecommerce.util.ApiProcesso;
import br.com.jcaguiar.ecommerce.util.TratarString;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProdutoService extends MasterService<Produto, Integer> {
	
	@Autowired private ModelMapper modelMapper;
	@Autowired private SetorService setorService;
	@Autowired private CategoriaService categoriaService;
	@Autowired private MarcaService marcaService;
	@Autowired private ImagemProdutoService imgService;
	@Autowired private ProblemaService problemaService;
	
	public ProdutoService(ProdutoRepository jpaRepo) {
		super(jpaRepo);
	}

	public ApiProcesso<Produto> impCsv(String[] linhaCsv) {
		final Produto produto;
		final ProdutoCsvPOST produtoDTO;
		//Identificando valores de cada campo
		final String setor 		= linhaCsv[0];  //Coluna A
		final String categoria 	= linhaCsv[1];  //Coluna B
		final String nome 		= linhaCsv[2];  //Coluna C
		final String descricao 	= linhaCsv[3];  //Coluna D
		final String marcas 	= linhaCsv[4];  //Coluna E
		final String modelo 	= linhaCsv[5];  //Coluna F
		final String valor 		= linhaCsv[6];  //Coluna G
		final String estoque 	= linhaCsv[7];  //Coluna H
		final String materiais 	= linhaCsv[8];  //Coluna I
		final String medidas 	= linhaCsv[9];  //Coluna J
		final String tamanho 	= linhaCsv[10]; //Coluna K
		final String ean 		= linhaCsv[11]; //Coluna L
		final String imagens 	= linhaCsv[12]; //Coluna M
		//Declarando variáveis finais (a tratar)
		final BigDecimal valorFinal;
		final short estoqueFinal;
		final String tamanhoFinal;
		final List<Marca> marcasFinal = new ArrayList<>();
		final List<ProdutoImagem> imagensFinais = new ArrayList<>();
		//Declarando variáveis de tratamento
		final List<String> marcasArray = Arrays.asList(
				TratarString.getDepois(marcas,":").split(","));
		final String[] imagensArray = imagens.split(",");
		//Tratando VALOR - conversão
		try {
			valorFinal = TratarString.paraBigDecimal(valor);
		} catch (NumberFormatException e) {
			return ApiProcesso.<Produto>builder()
					.erro(true)
					.causa("Campo 'Valor' fora do padrão numérico válido")
					.exception(e)
					.build();
		}
		//Tratando ESTOQUE - conversão
		try {
			estoqueFinal = Short.parseShort(estoque);
		} catch (NumberFormatException e) {
			return ApiProcesso.<Produto>builder()
					.erro(true)
					.causa("Campo 'Estoque' fora do padrão numérico válido")
					.exception(e)
					.build();
		}
		//Tratando TAMANHO - conversão
		try {
			tamanhoFinal = TratarString.sigla(tamanho, 2);
		} catch (Exception e) {
			return ApiProcesso.<Produto>builder()
					.erro(true)
					.causa("Campo 'Tamanho' fora do padrão válido (PP, P, M, G, GG, EG)")
					.exception(e)
					.build();
		}
		//Convertando campos em DTO POST - para validação
		//TODO: Será  chamado outro método, passando o csvPOST por parâmetro, responsável em converter
		//      DTO em entidade e então salva-lo no banco de dados.
		try {
			produtoDTO = ProdutoCsvPOST.builder()
					.setorNome(setor)
					.categoriaNome(categoria)
					.nome(nome)
					.descricao(descricao)
					.modelo(modelo)
					.valor(valorFinal)
					.estoque(estoqueFinal)
					.materiais(materiais)
					.medidas(medidas)
					.tamanho(tamanhoFinal)
					.ean(ean)
					.build();
		} catch (Exception e) {
			return ApiProcesso.<Produto>builder()
					.erro(true)
					.causa(e.getLocalizedMessage())
					.exception(e)
					.build();
		}
		try {
			//Crie ou Pegue: Setor, Categoria e Marcas
			final Setor setorFinal = setorService.validarByNome(setor);
			final Categoria categoriaFinal = categoriaService.validarByNome(setorFinal, categoria);
			marcasArray.forEach(mc -> marcasFinal.add(marcaService.validarByNome(mc)));
			//Criando Produtos
			produto = Produto.builder()
					.categoria(categoriaFinal)
					.nome(nome)
					.descricao(descricao)
					.modelo(modelo)
					.valor(valorFinal)
					.estoque(estoqueFinal)
					.material(materiais)
					.medidas(medidas)
					.tamanho(tamanhoFinal)
					.codigo(ean)
					.build();
			//Criando Imagens
//				imagensArray.forEach(
//						img -> imagensFinais.add(ProdutoImagem.builder()
//								.imagem(img)
//								.produto(produto)
//								.build())
//				);
			for(String img : imagensArray) {
				imagensFinais.add(ProdutoImagem.builder()
						.imagem(img)
						.produto(produto)
						.build()
				);
			}
			//Inserindo Atributos Pendentes (Imagens e Marca)
			produto.addImagem(imagensFinais);
			produto.addMarca(marcasFinal);
			produto.setAcessos(0);
			produto.setNota((short) 0);
			produto.setVotos(0);
			return ApiProcesso.<Produto>builder()
					.erro(false)
					.objeto(salvar(produto))
					.build();
		} catch (Exception e) {
			problemaService.salvar( new Problema(e) );
			Console.log("Erro ao tentar processar/salvar produto!");
			Console.log("Causa: " + e.getLocalizedMessage());
			return ApiProcesso.<Produto>builder()
					.erro(true)
					.causa(e.getLocalizedMessage())
					.build();
		}
	}
	
	public List<Produto> findAll(Example<Produto> produtoExemplo) {
		//Realizando consulta
		List<Produto> produtos = JPA_REPO.findAll(produtoExemplo);
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
		JPA_REPO.deleteAll();
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
