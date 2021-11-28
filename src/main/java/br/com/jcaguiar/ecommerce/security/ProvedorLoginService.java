package br.com.jcaguiar.ecommerce.security;

import br.com.jcaguiar.ecommerce.Console;
import br.com.jcaguiar.ecommerce.model.Usuario;
import br.com.jcaguiar.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**<h1>CONCEITO </h1>
 * Classe responsável pela lógica da autenticação.
 * A interface UserDetailsService serve justamente para o Spring reconhecer essa classe como tal. <br>
 *<h1>ATRIBUTOS </h1>
 * 	<b>userService:</b> classe DAO com os métodos CRUD da entidade Usuário.
 * @author JM Costal Aguiar
 */
@Service
public class ProvedorLoginService implements UserDetailsService {

	@Autowired private UsuarioService userService;
	
	/**<hr><h2>CONSULTAR USUÁRIO</h2>
	 * Método evocado pelo "LoginController" para coletar e retornar o usuário com base no atributo do email. <br>
	 * Método utilizado pela classe WebSecurityConfig. <br>
	 * @param email do suposto Usuário (String)
	 * @return Usuário detectado.
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Console.log("<LOGIN SERVICE>", +1);
		Usuario usuario = userService.findByEmail(email);
		Console.log(String.format(
				"Usuário identificado: %s",
				usuario.getEmail())
		);
		Console.log("</LOGIN SERVICE>", -1);
		return usuario;
	}

}
