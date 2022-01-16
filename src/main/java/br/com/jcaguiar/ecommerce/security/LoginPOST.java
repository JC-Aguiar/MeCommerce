package br.com.jcaguiar.ecommerce.security;

import br.com.jcaguiar.ecommerce.dto.MasterPOST;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

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
	@NotNull @NotEmpty @Length(min = 7) private String email;
	@NotNull @NotEmpty @Length(min = 7) private String senha;

}
