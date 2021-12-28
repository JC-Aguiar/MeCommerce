package br.com.jcaguiar.ecommerce.contorller;

import br.com.jcaguiar.ecommerce.Console;
import br.com.jcaguiar.ecommerce.dto.ProdutoPOST;
import br.com.jcaguiar.ecommerce.model.Produto;
import br.com.jcaguiar.ecommerce.projection.MasterGET;
import br.com.jcaguiar.ecommerce.projection.ProdutoUserGET;
import br.com.jcaguiar.ecommerce.service.*;
import br.com.jcaguiar.ecommerce.util.LeitorCsv;
import br.com.jcaguiar.ecommerce.util.ApiProcesso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("**/produto")
public class ProdutoController extends MasterController<Produto, Integer, ProdutoPOST, ProdutoUserGET>{

	@Autowired SetorService setorService;
	@Autowired CategoriaService categoriaService;
	@Autowired MarcaService marcaService;
	@Autowired ImagemProdutoService imgService;


	public ProdutoController(ProdutoService produtoService) {
		super(produtoService, Produto.class, ProdutoPOST.class, ProdutoUserGET.class);
	}


	/**<hr><h2>BUSCA FILTRADA</h2>
	 * Filtra e busca por produtos contendo todas as especificações informadas.
	 * @param produtoPost {@link ProdutoPOST} é um objeto DTO com atributos básicos da entidade {@link Produto}.
	 *                                        Esse DTO será usado como base da consulta, através da interface
	 *                                        {@link Example}.
	 * @return {@link ResponseEntity} contendo a List<{@link ProdutoUserGET}> dos objetos encontrados.
	 */
	@PostMapping("/filtrar")
	@Transactional
	public ResponseEntity<?> buscarTodos(@RequestBody ProdutoPOST produtoPost) {
		//Iniciando variáveis
		List<Produto> produtos;
		List<ProdutoUserGET> produtosGET;
		Produto produto;
		//Exibindo DTO
		Console.log("ProdutoDTO: ");
		Console.log( produtoPost.toString() );
		//Convertendo DTO para Entidade e anulando atributos inconvenientes para a consulta
		produto = conversorEntidade(produtoPost);
		produto.resetDatas();
		//Criando e configurando exemplo de produto para a consulta
		//TODO: criar uma classe ExampleMatcher estático para evitar criação de novas instâncias
		ExampleMatcher matcher = ExampleMatcher.matchingAll()
				.withIgnorePaths("id")
				.withIgnorePaths("ativo")
				.withIgnorePaths("categoria_data_cadastro")
				.withIgnoreCase()
				.withIgnoreNullValues()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		Example<Produto> produtoExemplo = Example.of(produto, matcher);
		//Consultando produtos com base no exemplo
		produtos = ((ProdutoService) masterService).findAll(produtoExemplo);
		//Convertendo Entidade em DTO
		Console.log("Convertendo dados");
		produtosGET = (List<ProdutoUserGET>) conversorDto(produtos, ProdutoUserGET.class);
//			final short totalItens = (short) produtosGET.size();
//			final short quantidade = (short) (totalItens/10);
//			//Criando paginação com ordenação
//			Sort ordenacao = Sort.by("nome").ascending();
//			Page<ProdutoUserGET> paginaFinal = new PageImpl<>(
//					produtosGET,
//					PageRequest.of(quantidade, 10, ordenacao).first(),
//					totalItens
//			);
		//Retornando resposta
		Console.log("Reportando resposta");
		return paginanar(produtosGET, Sort.by("id").ascending(), 0);
	}


	/** INSERE PRODUTOS VIA PLANILHA CSV <br>
	 * Para cada linha do csv, os campos são identificados, tratador e inseridos na listagem final de Produtos.<br>
	 * Atributos  nào coletados via planilha, como Acesso e Votos, são zerdos manualmente antes de inseridos na listagem.
	 * A importação da planilha, até o momento, só aceita padrão UTF-8.
	 *
	 * @param Caminho absoluto do arquivo csv.
	 * @return ResponseEntity List<ProdutoAdmGET> dos Produtos cadastrados com sucesso.
	 *
	 *@apiNote
	 * SETOR: Já existe Setor com mesmo nome? <br>
	 * 		 S: colete esse Setor <br>
	 * 		 N: crie essa Categoria <br><br>
	 *
	 * CATEGORIA:
	 * 		Já existe Categoria com mesmo nome dentro do Setor coletado? <br>
	 * 		 S: colete essa Categoria <br>
	 * 		 N: crie essa Categoria nesse Setor <br><br>
	 *
	 * MARCA:
	 * 		Já existe Marca com mesmo nome? <br>
	 * 		 S: colete essa Marca <br>
	 * 		 N: crie essa Marca <br><br>
	 *
	 * PRECO:
	 * 		Apura se Preço é válido <br><br>
	 *
	 * ESTOQUE:
	 * 		Apura se Estoque é válido <br><br>
	 *
	 * CODIGO EAN:
	 * 		Apurar se o Código é válido. <br>
	 * 		Já existe EAN com mesmo número? <br>
	 * 		 S: Substitua o Produto desse EAN por este novo cadastro <br>
	 * 		 N: Crie um novo produto <br><br>
	 *
	 * MARCA:
	 * 		TratarString.getDepois(":") <br><br>
	 *
	 * MATERIAIS:
	 * 		Divida a string em array, critério: ","	 <br>
	 * 		TratarString.getDepois(":") <br><br>
	 *
	 * IMAGENS:
	 * 		Dividir string em array, critério: "," <br>
	 * 		Adicionar os links aos já existentes <br><br>
	 *
	 * Classe TratarString:
	 * 		O método estático <b>getDepois</b> elimina todos os caracteres até a posição da string informada no
	 * 						  parâmetro targetCharSequence <br>
	 */
	@PostMapping("/file")
	@Transactional
	public ResponseEntity<?> addCsv(@RequestBody String fileName) {
		final LeitorCsv csv = new LeitorCsv(fileName);
		final List<String[]> arquivo = csv.getArquivo();
		final List<Produto> produtos = new ArrayList<>();
		final List<MasterGET> produtosGET = new ArrayList<>();
		final List<ApiProcesso<Produto>> resutlados = new ArrayList<>();
		int mapIndex = 0;
		Console.log("<IMPORTANDO-CSV>", +1);
		Console.log("Planilha coletada. Total de: " + arquivo.size() + " linhas");
		//Iterando linhas da planilha csv
		for(String[] linha : arquivo) {
			int index = mapIndex;
			//O método trata os campos da linha
			resutlados.add(((ProdutoService) masterService).impCsv(linha));
			//Se existe objeto na resposta, prossiga. Caso contrário, capture e anote o erro
			final Optional<Produto> optional = resutlados.get(resutlados.size()).getObjeto();
			optional.ifPresent(produtos::add);
			mapIndex++;
		}
		//Convertendo dados
		Console.log("Convertendo produtos para dto");
		produtosGET.addAll(conversorDto(produtos, ProdutoUserGET.class));
		//Terminando processo
		Console.log("Retornando resposta ao cliente");
		Console.log("</IMPORTANDO-CSV>", -1);
		return new ResponseEntity<>(produtosGET, HttpStatus.OK);
//		catch (PersistenceException | DataIntegrityViolationException e) {
//			e.printStackTrace();
//			throw new CadastroDuplicadoException("SKU já consta em uso. Favor tentar novamente com outro.");
//		}
	}


	@Override
	public ResponseEntity<?> atualizar(@Valid Produto objeto, HttpServletRequest request) {
		return null;
	}


	@Override
	public ResponseEntity<?> atualizarTodos(@Valid List<Produto> objeto, HttpServletRequest request) {
		return null;
	}


	@Override
	public ResponseEntity<?> deletar(@Valid Produto objeto, HttpServletRequest request) {
		return null;
	}


	@DeleteMapping("/all")
	@Transactional
	public ResponseEntity<?> deletAll() {
		((ProdutoService) masterService).removeAll();
		return new ResponseEntity<>(HttpStatus.OK);
	}


	@Override
	public ResponseEntity<?> deletarTodos(@Valid List<Produto> objeto, HttpServletRequest request) {
		return null;
	}

}
