package br.com.jcaguiar.ecommerce.security;

import br.com.jcaguiar.ecommerce.dto.MasterPOST;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public final class LoginPOST implements MasterPOST {
	/**CONCEITO
	 * 
	 */
	/**ATRIBUTOS
	 * 		email:	E-mail do usuário não pode ser vazio, nulo ou menor que 7 caracteres.
	 * 		senha:	Senha do usuário não pode ser vazio, nulo ou menor que 7 caracteres.
	 */
	@NotNull @NotEmpty @Length(min = 7) public String email;
	@NotNull @NotEmpty @Length(min = 7) public String senha;
	
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	/**TOKEN USUÁRIO
	 * Através dos atributos "email" e "senha", será gerado um objeto da classe UsernamePasswordAuthenticationToken.
	 * Este objeto será apurado na validação do método "LoginController.autenticar".
	 * @return
	 */
	public UsernamePasswordAuthenticationToken compilarDados() {
		return new UsernamePasswordAuthenticationToken(email, senha);
	}
	
	

}
