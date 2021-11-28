package br.com.jcaguiar.ecommerce.security;

import br.com.jcaguiar.ecommerce.Console;
import br.com.jcaguiar.ecommerce.model.Usuario;
import br.com.jcaguiar.ecommerce.service.UsuarioService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

/**<h1>CONCEITO</h1>
 * Classe destinada à criação e decodificação de tokens de autenticação formato JWT (Jason Web Tokens) <br>
 * <h1>TRIBUTOS</h1>
 * 	<b>segredo:</b>	assinatura de validação dos tokens <br>
 * 	<b>tempoLogin:</b> validade do token após criado (em milisegundos) <br>
 * 	<b>userService:</b> classe DAO para realizar CRUD 	 <br>
 * 	@author JM Costal Aguiar
 */
@Service
public final class TokenService {

	@Autowired private UsuarioService userService;
	final static private String SEGREDO = "AAAAB3NzaC1yc2EAAAADAQABAAABAQCu9uKkd/f23+CSmwp/Sx72HkRu1wW5Qn238DRzTW7IZWJi2IruikgxXewhaL9ncS8Bm437ScfmjjewLZuVxyRwMs2vBCb4yuXvYl4v2gd+vjw3QdlpHOplTE3BzA1LPco8vVEevBO9j8vFJoHcYjdwnhaOVqFl2Nm+I2WEBFVlnJtWV/zmdmVZxrCxvYEuZ1kLigfA9dtwtOEWrvcieIg132rB73HgmnjhKUKjBjbXzDEW0drgUnjt/Q8Jr/ix6IgPX6F71V6bwkJb0POv/rOHXOnh8gshgZQMgvrQ9/IFk6Ko+FBtMenqIeEZyNnB0chwo2SPAyOdo5w9y6XxcIQ9 ";
	final static private int TEMPO_LOGIN = 1800000;


	/**<hr><h2>CRIANDO TOKEN APÓS LOGIN</h2>
	 * Uma vez autenticado, o token JWT será gerado a este usuário <br>
	 * <ol>
	 * <li> <b>COLETANDO USUÁRIO</b> <br>
	 * 			<b>Authentication:</b>		Classe gerenciada pelo AuthenticationManager (no LoginController) e quando esta mesma
	 * 										classe é instanciada, recebe a email e senha do usuário. <br>
	 * 			<b>getPrincipal:</b>		Método que retorna a classe usuário que está salvo no Authentication.
	 * </li>
	 * <li> <b>DEFININDO DATAS</b> <br>
	 * 			<b>hoje:</b>				Data atual. <br>
	 * 			<b>validade:</b>			Data de expiração do token para esse login. <br>
	 * </li>
	 * <li> <b>CLASSSE JWT PARA CRIAÇÃO DO TOKEN</b> <br>
	 * 			<b>builder:</b>				Preenchendo campos do construtor Jwts. <br>
	 * 			<b>setIssuer:</b>			Nome do emissor. <br>
	 * 			<b>setSubject:</b>			Identificador único (id) do objeto de login (usuário) em formato String. <br>
	 * 			<b>setIssuedAt:</b>			Data emissáo do token. <br>
	 * 			<b>setExpiration:</b>		Data de expiração de uso do token. <br>
	 * 			<b>signWith:</b>			Recebe método de criptografia e assinatura secreta da API. <br>
	 * 			<b>SignatureAlgorithm:</b>	Método estático do Jwts com criptorgrafias disponíveis. <br>
	 * 			<b>compact:</b>				Criar token. <br>
	 * </li>
	 * </ol>
	 * @param userAutenticado da CLasse Authentication. Contendo e-mail e senha.
	 * @return String token
	 */
	public String newToken(Authentication userAutenticado) {
		Console.log("<TOKEN SERVICE>", +1);
		Usuario usuario = userService.findByEmail( userAutenticado.getName() );
		Date hoje = new Date();
		Date validade = new Date(hoje.getTime() + TEMPO_LOGIN);
		String token = Jwts.builder()
				.setIssuer("API ECOMMERCE")
				.setSubject( usuario.getId().toString() )
				.setIssuedAt(hoje)
				.setExpiration(validade)
				.signWith(SignatureAlgorithm.HS256, SEGREDO)
				.compact();
		Console.log("Token JWT criado com sucesso.");
		Console.log("</TOKEN SERVICE>.", -1);
		return token;

	}

	/**<hr><h2>VALIDANDO TOKEN DO REQUEST</h2>
	 * Tentar decodificar token do usuário <br><br>
	 * 		<b>Jwts:</b>				Classe responsável pelas interfaces Jwt. <br>
	 * 		<b>parser:</b> 				Decodificado o token. <br>
	 * 		<b>setSigningKey:</b>	 	Define assinatura do decodificador, (tanto o token quanto
	 * 									o sistema devem ser iguais). <br>
	 * 		<b>parseClaimsJws:</b>	 	Converta o playload (usuário). <br>
	 * @param token String do token propriamente dito.
	 * @return ID do usuário ou -1, caso o usuário não seja indicificado e/ou algum erro ocorreu
	 */
	public int validar(String token) {
		try {
			//Processando token Jwt
			String userIdString = Jwts.parser().setSigningKey(SEGREDO).parseClaimsJws(token).getBody().getSubject();
			Integer userId = Integer.parseInt(userIdString); 
			Console.log("Usuário logado com sucesso");
			return userId;
		}
		catch (MalformedJwtException e) {
			//Erro padrão JWT
			Console.log("AVISO: A criptografia do token não é um JWT válido");
		}
		catch (SignatureException e) {
			//Erro na assinatura do token
			Console.log("AVISO: Assinatura do token não é compatível");
		}
		catch (ExpiredJwtException e) {
			//Erro validade expirada
			Console.log("AVISO: Token com tempo de validade expirado");
		}
		catch (IllegalArgumentException e) {
			//Erro token sem conteúdo
			Console.log("AVISO: Token com valores incorretos (vazio, nulo ou em branco)");
		}
		return -1;
	}
	
}
