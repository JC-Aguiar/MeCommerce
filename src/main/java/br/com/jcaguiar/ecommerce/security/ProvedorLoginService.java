package br.com.jcaguiar.ecommerce.security;

import br.com.jcaguiar.ecommerce.Console;
import br.com.jcaguiar.ecommerce.model.Usuario;
import br.com.jcaguiar.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
/**CONCEITO <br>
 * Classe responsável pela lógica da autenticação.
 * A interface UserDetailsService serve justamente para o Spring reconhecer essa classe como tal. <br>
 *ATRIBUTOS <br>
 * 	userService:		Classe com os métodos CRUD da entidade Usuário.
 *
 */
@Service
public class ProvedorLoginService implements UserDetailsService {

	@Autowired private UsuarioService userService;
	
	/**CONSULTAR USUÁRIO <br>
	 * Método evocado pelo "LoginController" para coletar e retornar o usuário com base no atributo do email.
	 * Método utilizado pela classe ???.
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Console.log("<LOGIN SERVICE>", +1);
		Optional<Usuario> usuario = userService.findByEmail(email);
		if( !usuario.isPresent() ) {
			throw new UsernameNotFoundException("Credenciais inválidas.");
		}	
		Console.log(String.format(
				"Usuário identificado: %s",
				usuario.get().getEmail())
		);
		Console.log("</LOGIN SERVICE>", -1);
		
		return usuario.get();
	}

}
