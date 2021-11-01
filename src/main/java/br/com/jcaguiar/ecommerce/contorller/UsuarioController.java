package br.com.jcaguiar.ecommerce.contorller;

import br.com.jcaguiar.ecommerce.Console;
import br.com.jcaguiar.ecommerce.dto.UsuarioPOST;
import br.com.jcaguiar.ecommerce.model.Perfil;
import br.com.jcaguiar.ecommerce.model.PerfilTipo;
import br.com.jcaguiar.ecommerce.model.Usuario;
import br.com.jcaguiar.ecommerce.projection.UsuarioGET;
import br.com.jcaguiar.ecommerce.service.PerfilService;
import br.com.jcaguiar.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**<h1>CONCEITO </h1>
 * Classe responsável pelo end-point "/user".<br>
 * Esse end-point irá conter a lógica e tratamento para o CRUD dos cadastros de usuários.<br>
 * Para desfrutar de CRUDs básicos da superclase MasterController, precisa informar os campos genéticos: <br>
 * <li> Entidade principal </li>
 * <li> Tipo do atributo ID da Entidade </li>
 * <li> Classe DTO da Entidade que será enviada ao front-end </li>
 *<h1>ATRIBUTOS </h1>
 * 	<b>userService:</b> classe com os métodos CRUD da entidade Usuário.<br>
 * 	<b>perfilService:</b> classe com os métodos CRUD da entidade Perfil.<br>
 *	@author JM Costal Aguiar
 */
@RestController
@RequestMapping("user")
public class UsuarioController extends MasterController<Usuario, Integer, UsuarioPOST> {

	@Autowired UsuarioService userService;
	@Autowired PerfilService perfilService;

	/**<hr><h2>CONSTRUTOR PADRÃO</h2>
	 * Irá informar para a superclasse MasterController:<br>
	 * <li> Classe da Entidade controlada </li>
	 * <li> Classe do DTO da Entidade para retornar ao front-end </li>
	 * <li> Nome do end-point </li>
	 * <li> Classe DAO </li>
	 * @param userService (UsuarioService) será a Classe de serviço DAO.
	 */
		public UsuarioController(UsuarioService userService) {
		super(
				Usuario.class,
				UsuarioPOST.class,
				"user",
				userService
		);
	}


	@PostMapping("/add")
	@Transactional
	public ResponseEntity<?> add(@Valid @RequestBody UsuarioPOST userPost) {
		Console.log("Nova solicitação para cadastro de usuário.");
		//Convertendo DTO para Entidade
		Usuario user = conversorEntidade(userPost);
		//Encriptando senha
		final BCryptPasswordEncoder ENCRIPT = new BCryptPasswordEncoder();
		user.setSenha( ENCRIPT.encode(user.getSenha()) );
		//Salvando novo Usuario
		userService.salvar(user);
		//Salvando Perfil do Usuário
		Perfil perfil = Perfil.builder()
				.nome(PerfilTipo.USER)
				.usuario(user)
				.ativo(true)
				.build();
		perfilService.salvar(perfil);
		//Convertendo Entidade para DTO
		UsuarioGET userGet = (UsuarioGET) conversorDto(user, UsuarioGET.class);
		//Retornando ao front-end
		return new ResponseEntity<>(userGet, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> atualizar(@Valid Usuario objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> atualizarTodos(@Valid List<Usuario> objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deletar(@Valid Usuario objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deletarTodos(@Valid List<Usuario> objeto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
