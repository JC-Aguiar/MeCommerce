package br.com.jcaguiar.ecommerce.contorller;

import br.com.jcaguiar.ecommerce.Console;
import br.com.jcaguiar.ecommerce.dto.ProdutoPOST;
import br.com.jcaguiar.ecommerce.model.*;
import br.com.jcaguiar.ecommerce.projection.MasterGET;
import br.com.jcaguiar.ecommerce.projection.ProdutoAdmGET;
import br.com.jcaguiar.ecommerce.projection.ProdutoUserGET;
import br.com.jcaguiar.ecommerce.service.*;
import br.com.jcaguiar.ecommerce.util.LeitorCsv;
import br.com.jcaguiar.ecommerce.util.TratarString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("**/produto")
public class ProdutoController extends MasterController<Produto, Integer, ProdutoPOST>{
	
	@Autowired SetorService setorService;
	@Autowired CategoriaService categoriaService;
	@Autowired MarcaService marcaService;
	@Autowired ImagemProdutoService imgService;
	

	public ProdutoController(ProdutoService produtoService) {
		super(
				Produto.class,
				ProdutoPOST.class,
				"produto",
				produtoService
		);
	}
	

	/**BUSCA FILTRADA 
	 * Busca por um produto com as especificações informadas.
	 * @apiNote Para os atributos tipo Srting, é necessário coletá-los e 
	 * 
	 * @param HttpServletRequest para validar se usuário é ADM ou não
	 * @return ResponseEntity List<ProdutoAdmGET> ou List<ProdutoUserGET>
	 */
	@PostMapping("/filtrar")
	@Transactional
	public ResponseEntity<List<?>> buscarTodos(@RequestBody ProdutoPOST produtoPost, HttpServletRequest request) {
		//Iniciando variáveis
		List<Produto> produtos;
		List<? extends MasterGET> produtosGET;
		Class<? extends MasterGET> classeAlvo; 
		Produto produto;
		//Exibindo DTO
		Console.log("ProdutoDTO: ");
		Console.log( produtoPost.toString() );
		//Preparando ordenação
		//final Sort ORDENE = Sort.by("id").ascending();
		//Complexidade do resultado com base no perfil do usuário
		if( request.isUserInRole(ADM) || admSql ) {
			log(0); //Consulta ADMIN
			classeAlvo = ProdutoAdmGET.class;
		}
		else {
			log(1); //Consulta USER
			classeAlvo = ProdutoUserGET.class;
		}
		produto = conversorEntidade(produtoPost);
		produto.resetDatas();
		produto.setAtivo(false);
		ExampleMatcher matcher = ExampleMatcher.matching()
	        .withIgnoreCase()
	        .withIgnoreNullValues()
	        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		Example<Produto> produtoExemplo = Example.of(produto, matcher);
		Console.log("ProdutoExemplo Nome: " + produtoExemplo.getProbe().getNome());
		Console.log("ProdutoExemplo Tamanho: " + produtoExemplo.getProbe().getTamanho());
		produtos = ((ProdutoService) MASTER_SERVICE).findAll(produtoExemplo);
		Console.log("Convertendo dados");
		produtosGET = conversorDto(produtos, classeAlvo);
		Console.log("Reportando resposta");
		return new ResponseEntity<>(produtosGET, HttpStatus.OK);
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
		Console.log("<IMPORTANDO-CSV>", +1);
		//Inicializando variáveis
		final LeitorCsv csv = new LeitorCsv(fileName);
		final List<String[]> arquivo = csv.getArquivo();
		final List<Produto> produtos = new ArrayList<>();
		final List<MasterGET> produtosGET = new ArrayList<>();
		Console.log("Coletando planilha. Total de: " + arquivo.size() + " linhas");
		//Iterando linhas da planilha csv
		for(String[] linha : arquivo) {
			Console.log( Arrays.toString(linha) );
			//Iniciando variáveis
			final List<Marca> marcasFinal = new ArrayList<>();
			final List<ProdutoImagem> imagensFinais = new ArrayList<>();
			final String setor = 		linha[0];		//Coluna A
			final String categoria = 	linha[1];		//Coluna B
			final String nome = 		linha[2];		//Coluna C
			final String descricao = 	linha[3];		//Coluna D
			final String marcas = 		linha[4];		//Coluna E
			final String modelo = 		linha[5];		//Coluna F
			final String preco = 		linha[6];		//Coluna G
			final String estoque = 		linha[7];		//Coluna H
			final String materiais = 	linha[8];		//Coluna I
			final String medidas = 		linha[9];		//Coluna J
			final String tamanho = 		linha[10];		//Coluna K
			final String ean = 			linha[11];		//Coluna L
			final String imagens = 		linha[12];		//Coluna M
			//Tratando campos numéricos
			final BigDecimal precoFinal = TratarString.paraBigDecimal(preco);
			final short estoqueFinal = Short.parseShort(estoque);
			final char tamanhoFinal = TratarString.sigla(tamanho);	
			//Criando Setor
			final Setor setorFinal = setorService.validarByNome(setor);
			//Criando Categoria
			final Categoria categoriaFinal = categoriaService
					.validarByNome(setorFinal, categoria);	
			//Criando Marcas
			final List<String> marcasArray = Arrays.asList(
					TratarString.getDepois(marcas,":")
					.split(",")
			);
			for(String mc : marcasArray) {
				marcasFinal.add( marcaService.validarByNome(mc) );
			}
			//Criando Produtos
			Produto produto = Produto.builder()
					.categoria(categoriaFinal)
					.nome(nome)
					.descricao(descricao)
					.modelo(modelo)
					.valor(precoFinal)
					.estoque(estoqueFinal)
					.material(materiais)
					.medidas(medidas)
					.tamanho( String.valueOf(tamanhoFinal) )
					.codigo(ean)
					.build();
			//Criando Imagens
			final List<String> imagensArray = Arrays.asList(imagens.split(","));
			for(String img : imagensArray) {
				imagensFinais.add(ProdutoImagem.builder()
					.imagem(img)
					.produto(produto)
					.build()
				);
			}
			//Inserindo Atributos Pendentes (Imagens e Marca)
			produto.addImagem(imagensFinais);
			produto.addMarca( marcaService.saveAll(marcasFinal) );
			produto.setAcessos(0);
			produto.setNota((short) 0);
			produto.setVotos(0);
			//Populando na lista final de Produtos
			Console.log("Salvando produto");
			produtos.add( ((ProdutoService) MASTER_SERVICE).salvar(produto) );
			Console.log("Produto salvo com sucesso!");
		}
		//Fim do looping (linha)
		//Convertendo dados
		Console.log("Convertendo produtos para dto");
		produtosGET.addAll( conversorDto(produtos, ProdutoAdmGET.class) );
		//Terminando processo
		Console.log("Retornando resposta ao cliente");
		Console.log("</IMPORTANDO-CSV>", -1);
		return new ResponseEntity<>(produtosGET, HttpStatus.OK);
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
		((ProdutoService) MASTER_SERVICE).removeAll();
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

	@Override
	public ResponseEntity<?> deletarTodos(@Valid List<Produto> objeto, HttpServletRequest request) {
		return null;
	}

}
