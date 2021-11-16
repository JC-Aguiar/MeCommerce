package br.com.jcaguiar.ecommerce.contorller;

import br.com.jcaguiar.ecommerce.Console;
import br.com.jcaguiar.ecommerce.dto.MasterPOST;
import br.com.jcaguiar.ecommerce.model.Entidade;
import br.com.jcaguiar.ecommerce.projection.MasterGET;
import br.com.jcaguiar.ecommerce.service.MasterService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ConfigurationException;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**<h1>CONCEITO</h1>
 * Classe mãe que define o básico de todos os demais crontrollers. <br>
 * No construtor das classes herdeiras é necessário preencher as seguintes variáveis (respectivamente): <br>
 * <h1>ATRIBUTOS</h1>
 * @author JM Costal Aguiar
 * @param <OBJ> Genérico que representa a Entidade
 * @param <ID> Genérico que representa a Entidade
 * @param <DTO> Genérico que representa a Entidade
 */
@RestController
@RequiredArgsConstructor 
public abstract class MasterController<OBJ extends Entidade<ID>, ID, DTO extends MasterPOST, REPORT extends MasterGET> {

	//TODO: revisar se devo manter atributos "protected"

	@Autowired protected ModelMapper modelMapper;
	@Autowired protected Class<OBJ> classeModelo;
	@Autowired protected Class<DTO> classeDtoPost;
	@Autowired protected Class<REPORT> classeDtoGet;
	//@Autowired protected URL endPoint;

	protected final MasterService<OBJ, ID> masterService;
	protected boolean admSql = false;
	protected short itensPorPagina = 12;
	protected static final String ADM = "ROLE_ADM";
	private static final String[] LOG = {
			"Consulta Completa",		//0
			"Consulta Restrita",		//1
			"Cadastro Criado",			//2
			"Cadastro Atualizado",		//3
			"Cadastro Excluído",		//4
			"Erro na Operação"			//5+
	};
	
	
	/**<hr><h2>CADASTRAR UM</h2>
	 * 
	 * @param dto
	 * @param request
	 * @param uriBuilder
	 * @return
	 * @throws Exception
	 */
	@PostMapping
	@Transactional
	public ResponseEntity<?> salvar(@RequestBody @Valid DTO dto, HttpServletRequest request, UriComponentsBuilder uriBuilder)
	throws Exception {		
		//Convertendo DTO e Salvando
		final OBJ objModel = conversorEntidade(dto);
		final ID objModelId = (ID) objModel.getId();
		masterService.salvar(objModel);
		//Montando URI de retorno
		return buscarId(objModelId, request);
	}

	
	/**<hr><h2>CADASTRAR TODOS</h2>
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping
	@Transactional
	public ResponseEntity<?> buscarTodos(HttpServletRequest request) {

		/*
		TODO: métodos de implementar opção de retorno DTO de acordo com o perfil do usuário: <br>
		  		1. MasterController possui um outro atributo para informar DTO adm.  <br>
			  	2. As classes filho podem acionar um método do MasterController, informando os tipos
			  	   de DTO via parâmetro
		 */

		//Preparando ordenação
		final Sort ordene = Sort.by("id").ascending();
		//Validando perfil do usuário
		if( request.isUserInRole(ADM) || admSql ) {
			//Consulta ADMIN
			log(0);
			Console.log("Coletando dados");
			return paginanar(masterService.findAll(), ordene, 0);
//			Console.log("Convertendo dados");
//			dto = conversorDto(entity, classeModelo);
		}
		else {
			//Consulta USER
			log(1);
			Console.log("Coletando dados");
			List<OBJ> entity = masterService.findAll();
			Console.log("Convertendo dados");
			List<? extends MasterGET> dto = conversorDto(entity, classeDtoGet);
			return paginanar(dto, ordene, 0);
		}
	}

	/**<hr><h2>BUSCA POR ID - EXATA</h2>
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@GetMapping("/id/{id}")
	public ResponseEntity<?> buscarId(@PathVariable(name = "id") ID id, HttpServletRequest request) {
		//Preparando ordenação
		final Sort ORDENE = Sort.by("id").ascending();

		if(id == null){
			throw new NullPointerException();
		}
		
		//Usuário da consulta ADMIN?
		if( request.isUserInRole(ADM) || admSql) {
			log(0);//Consulta ADMIN
			final Optional<?> OBJ_VO = masterService.findById(id);
			return new ResponseEntity<>(OBJ_VO, HttpStatus.OK);
		}
		log(1);//Consulta USER
		final MasterGET OBJ_VO = masterService.findId(id);
		
		return new ResponseEntity<>(OBJ_VO, HttpStatus.OK);
	}

	/**<hr><h2>BUSCA POR NOME - CONTÊM</h2>
	 * Dependendo do login/perfil de quem fez a solicitação serão retornados diferentes campos da Entidade.
	 * @param nome
	 * @param request
	 * @return a list with entities and a http response
	 * @throws NumberFormatException
	 */
	@GetMapping("/nome/{nome}")
	public ResponseEntity<?> buscarNomeCom(@PathVariable(name = "nome")String nome, HttpServletRequest request) {
		final Sort ORDENE = Sort.by("id").ascending();
		
		if( request.isUserInRole(ADM) || admSql) {
			log(0);
			final List<?> objetos = masterService.findByNomeContainingAdm(nome);
			return new ResponseEntity<>(objetos, HttpStatus.OK);
		}
		log(1);
		final List<?> objetosReport = masterService.findByNomeContaining(nome);
		
		return new ResponseEntity<>(objetosReport, HttpStatus.OK);
	}

	/**<hr><h2>BUSCA POR NOME - EXATA</h2>
	 * 
	 * @param nome
	 * @param request
	 * @return
	 * @throws NumberFormatException
	 */
	@GetMapping("/nome exato/{nome}")
	public ResponseEntity<?> buscarNome(@PathVariable(name = "nome")String nome, HttpServletRequest request)
	throws NumberFormatException {
		final Sort ORDENE = Sort.by("id").ascending();
		
		if( request.isUserInRole(ADM) || admSql) {
			log(0);
			final List<?> objetos = masterService.findByNomeAdm(nome);
			return new ResponseEntity<>(objetos, HttpStatus.OK);
		}
		log(1);
		final List<?> objetosReport = masterService.findByNomeContainingAdm(nome);
		
		return new ResponseEntity<>(objetosReport, HttpStatus.OK);
	}

	/**<hr><h2>CARREGAR PÁGINA</h2>
	 *
	 * @param nome
	 * @param request
	 * @return
	 * @throws NumberFormatException
	 */
	public ResponseEntity<?> paginanar(List<?> objetos, Sort ordenacao, int pageIndex) {
		Console.log("Paginando resposta");
		pageIndex = pageIndex < 0 ? 0 : pageIndex;
		Page<?> paginaFinal = new PageImpl<>(
				objetos,
				PageRequest.of(pageIndex, this.itensPorPagina, ordenacao),
				objetos.size()
		);
		return new ResponseEntity<>(paginaFinal, HttpStatus.OK);
	}

	/**<hr><h2>ATUALIZA UM CADASTRO</h2>
	 * 
	 * @param objeto
	 * @param request
	 * @return
	 */
	@PutMapping
	public abstract ResponseEntity<?> atualizar(@RequestBody @Valid OBJ objeto, HttpServletRequest request);
	
	
	/**<hr><h2>ATUALIZA MUITOS CADASTROS</h2>
	 * 
	 * @param objeto
	 * @param request
	 * @return
	 */
	@PutMapping("/remover-e-ajustar")
	public abstract ResponseEntity<?> atualizarTodos(@RequestBody @Valid List<OBJ> objeto, HttpServletRequest request);
	
	
	/**<hr><h2>DELETA UM CADASTRO</h2>
	 * 
	 * @param objeto
	 * @param request
	 * @return
	 */
	@DeleteMapping
	public abstract ResponseEntity<?> deletar(@RequestBody @Valid OBJ objeto, HttpServletRequest request);
	
	
	/**<hr><h2>DELETA MUITOS CADASTROS</h2>
	 * 
	 * @param objeto
	 * @param request
	 * @return
	 */
	@DeleteMapping("/remover-e-ajustar")
	public abstract ResponseEntity<?> deletarTodos(@RequestBody @Valid List<OBJ> objeto, HttpServletRequest request);	
	
	
	/**<hr><h2>CONVERSOR: ENTIDADE ~ DTO</h2>
	 * 
	 * @param object
	 * @param classGET
	 * @return
	 * @throws IllegalArgumentException
	 * @throws ConfigurationException
	 * @throws MappingException
	 */
	public MasterGET conversorDto(OBJ object, Class<? extends MasterGET> classGET)
	throws IllegalArgumentException, ConfigurationException, MappingException {
		return modelMapper.map(object, classGET);
	}
	
	
	/**<hr><h2>CONVERSOR (LISTA): ENTIDADE ~ DTO</h2>
	 * 
	 * @param object
	 * @param classGET
	 * @return
	 * @throws IllegalArgumentException
	 * @throws ConfigurationException
	 * @throws MappingException
	 */
	public List<? extends MasterGET> conversorDto(List<OBJ> object, Class<? extends MasterGET> classGET)
	throws IllegalArgumentException, ConfigurationException, MappingException {
		List<MasterGET> dto = new ArrayList<>();
		for(OBJ obj : object) {
			dto.add(conversorDto(obj, classGET));
		}
		return dto;
	}
	
	
	/**<hr><h2>CONVERSOR: DTO ~ ENTIDADE</h2>
	 * 
	 * @param dto
	 * @return
	 * @throws IllegalArgumentException
	 * @throws ConfigurationException
	 * @throws MappingException
	 */
	public OBJ conversorEntidade(DTO dto)
	throws IllegalArgumentException, ConfigurationException, MappingException {
		return modelMapper.map(dto, classeModelo);
	}
	
	
	/**<hr><h2>CONVERSOR (LISTA): DTO ~ ENTIDADE</h2>
	 * 
	 * @param listaDto
	 * @return
	 * @throws IllegalArgumentException
	 * @throws ConfigurationException
	 * @throws MappingException
	 */
	public List<OBJ> conversorEntidade(List<DTO> listaDto)
	throws IllegalArgumentException, ConfigurationException, MappingException {
		List<OBJ> listaObjects = new ArrayList<>();
		for(DTO dto : listaDto) {
			listaObjects.add(conversorEntidade(dto));
		}
		return listaObjects;
	}
	
	
	/** <hr><h2>MENSAGENS DAS OPERAÇÕES</h2>
	 * Mensagens LOG:
	 * 0  "Consulta Completa"
	 * 1  "Consulta Restrita"
	 * 2  "Cadastro Criado", //2
	 * 3  "Cadastro Atualizado"
	 * 4  "Cadastro Excluído"
	 * 5+ "Log indefinido"
	 */
	protected final void log(int num) {
		num = num <= 4? num : 5;
		Console.log("LOG: " + LOG[num]);
	}
	protected final void log(int num, String texto) {
		num = num <= 4? num : 5;
		Console.log(String.format( "LOG: %s. %s \n", LOG[num], texto ));
	}

}
