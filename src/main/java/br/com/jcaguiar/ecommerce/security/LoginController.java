package br.com.jcaguiar.ecommerce.security;

import br.com.jcaguiar.ecommerce.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
/**<h1>CONCEITO</h1>
 *
 *<h1>ATRIBUTOS</h1>
 * 	<b>gerenteLogin:</b> Interface AuthenticationManager orignal do Spring. Cria as condições para autenticar objetos
 * 	UsernamePasswordAuthenticationToken. <br>
 * 	<b>tokenService:</b> Classe TokenService, destinada à criação e decodificação de tokens de autenticação
 * 	no formato JWT (Jason Web Tokens). <br>
 * 	@author JM Costal Aguiar
 */
@RestController
@CrossOrigin(origins = "*") //http://localhost:8100/
@RequestMapping("/login")
public final class LoginController {

	/**TODO: <br>
	 * 		<ol>
	 *  	<li> Implementar tratamento ao tentar se logar.
	 * 		Usuário com credenciais não identificadas no base devem retornam mensagem apropriada </li>
	 * 		<li> Cria método main avulso para criptografar senhas inseridas diretamente no banco (expostas)</li>
	 * 		</ol>
	 */

	@Autowired private AuthenticationManager gerenteLogin;
	@Autowired private TokenService tokenService;
    @Autowired private ProvedorLoginService provedorLogin;


	/**<hr><h2>PADRÃO DE AUTENTICAÇÃO</h2>
	 * Através dos parametros enviados no corpo da requisição, o sistema tentará: autenticar o usuário(1),
	 * criar Token tipo Bearer desse usuário(2) e retornar a versão DTO desse token na resposta ao cliente(3) <br>
	 * <ol>
	 * <li> <b>REALIZANDO AUTENTICAÇÃO</b> <br>
	 * 			<b>UsernamePasswordAuthenticationToken:</b>
	 * 					Objeto criado através do método "login.compilarDados", da classe LoginPOST.
	 * 					Utiliza os atributos "E-mail" e "Senha" para preparar a autenticação. <br>
	 * 			<b>Authentication:</b>
	 * 					Através do "AuthenticationManager.authenticate" o sistema consultará
	 * 					as configurações do Spring, chamando o provedor de autenticação
	 * 					"ProvedorLoginService", pois este consta usando a interface "UserDetailsService".
	 * </li>
	 * <li> <b>CRIANDO TOKEN</b> <br>
	 * 					Através do método "tokenService.newToken", será gerado um token JWT usando
	 * 					os dado deste usuário. Ou seja, é criada uma chave (string) criptografada
	 * 					que representa essa autenticação bem sucedida.
	 * <li> <b>RETORNANDO DTO</b> <br>
	 * 					Com base no token já criado, é definido o tipo de autenticação que o sistema
	 * 					usa para então gerar o DTO que será retornado ao cliente.
	 * </li>
	 * </ol>
	 * @param login objeto da classe LoginPOST, contendo e-mail e senha.
	 * @return token (string) da autenticação para esse usuário.
	 */
    @PostMapping
	public ResponseEntity<?> autenticar(@RequestBody @Valid LoginPOST login, HttpServletRequest request) {
		Console.log("<LOGIN CONTROLER>", +1);
		try {
            //Validando o e-mail do usuário
            provedorLogin.loadUserByUsername(login.getEmail());
			//Criando um objeto só agrupando os atributos "email" e "senha" do tipo UsernamePasswordAuthenticationToken.
            //Este objeto é usado no post de validação de login, no método "LoginController.autenticar".
			Console.log("Compilando Autenticação...");
			UsernamePasswordAuthenticationToken autenticarDados = new UsernamePasswordAuthenticationToken(
                login.getEmail(), login.getSenha());
			//Transformando o UsernamePasswordAuthenticationToken em Authentication
			//A classe ProvedorAutorizadorService foi configurada para realizar método próprio no lugar do "authenticate".
			Console.log("Realizando Autenticação...");
			Authentication userAutenticado = gerenteLogin.authenticate(autenticarDados);
			//Gerando JWT á partir do Authentication
			Console.log("Criando Token...");
			return ResponseEntity.ok(tokenService.newToken(userAutenticado));
		}
		catch (AuthenticationException e) {
//          final HttpStatus status = HttpStatus.UNAUTHORIZED;
//          final String mensagem = "Credenciais inválidas";
//          final ErroInesperado erro = new ErroInesperado(status, mensagem, request.getRequestURI());
//			return new ResponseEntity(erro, HttpStatus.UNAUTHORIZED);
            throw new AuthenticationCredentialsNotFoundException("");
		}
//		catch (Exception e) {
//			return ResponseEntity.badRequest().build();
//		}
		finally {
			Console.log("</LOGIN CONTROLER>", -1);
		}

	}

}
