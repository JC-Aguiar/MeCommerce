package br.com.jcaguiar.ecommerce.security;

import br.com.jcaguiar.ecommerce.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public final class LoginController {
	/**CONCEITO
	 * 
	 */
	/**ATRIBUTOS
	 * 		gerenteLogin:		Interface AuthenticationManager orignal do Spring.
	 * 							Cria as condições para autenticar objetos UsernamePasswordAuthenticationToken.
	 * 
	 * 		tokenService:		Classe TokenService, destinada à criação e decodificação de tokens de autenticação
	 * 							no formato JWT (Jason Web Tokens).
	 */
	@Autowired
	private AuthenticationManager gerenteLogin;
	
	@Autowired
	private TokenService tokenService;

	//TODO: Implementar tratamento ao tentar se logar.
	// Usuário com credenciais não identificadas no base devem retornam mensagem apropriada

	//TODO: Cria método main avulso para criptografar senhas inseridas diretamente no banco (expostas)
	
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	/**PADRÃO DE AUTENTICAÇÃO
	 * Através dos parametros enviados no corpo da requisição, o sistema tentará:
	 * 1) Autenticar o usuário
	 * 2) Criar Token tipo Bearer desse usuário
	 * 3) Retornar a versão DTO desse token na resposta ao cliente
	 * 
	 * Através do try/catch temos 2 resultados finais:
	 * 			normal: 	usuário cadastrado e autentivado -> 200 OK
	 * 			exception:	usuário não encontrado -> 400 BAD REQUEST
	 * 
	 * 1) Realizando Autenticação:
	 * 			UsernamePasswordAuthenticationToken:
	 * 					Objeto criado através do método "login.compilarDados", da classe LoginDto.
	 * 					Utiliza os atributos "E-mail" e "Senha" para preparar a autenticação.
	 * 
	 * 			Authentication:
	 * 					Através do "AuthenticationManager.authenticate" o sistema consultará
	 * 					as configurações do Spring, chamando o provedor de autenticação 
	 * 					"ProvedorLoginService", pois este consta usando a interface "UserDetailsService".
	 * 
	 * 2) Criando Token:
	 * 					Através do método "tokenService.newToken", será gerado um token JWT usando
	 * 					os dado deste usuário. Ou seja, é criada uma chave (string) criptografada
	 * 					que representa essa autenticação bem sucedida.
	 * 					
	 * 3) Retornando DTO:
	 * 					Com base no token já criado, é definido o tipo de autenticação que o sistema
	 * 					usa para então gerar o DTO que será retornado ao cliente.
	 * 			
	 */ 
	@PostMapping
	public ResponseEntity<?> autenticar(@RequestBody @Valid LoginDto login) {
		Console.log("<LOGIN CONTROLER>", +1);
		Console.log( String.format("Dados coletados: [e-mail]: %s, [senha]: %s", login.getEmail(), login.getSenha() ));
		try {
			Console.log("Compilando Autenticação...");
			UsernamePasswordAuthenticationToken autenticarDados = login.compilarDados();
			Console.log("Realizando Autenticação...");
			Authentication userAutenticado = gerenteLogin.authenticate(autenticarDados);
			Console.log("Criando Token...");
			String token = tokenService.newToken(userAutenticado);
			TokenDto tokenDto = new TokenDto(token, "Bearer");
			Console.log(
					String.format("Token: %s", token)
			);
			return ResponseEntity.ok(tokenDto);
		}
		catch (AuthenticationException e) {
			return ResponseEntity.status(401).build();
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
		finally {
			Console.log("</LOGIN CONTROLER>", -1);
		}
		
	}
	
}
