package br.com.jcaguiar.ecommerce.dto;

import br.com.jcaguiar.ecommerce.model.Produto;
import br.com.jcaguiar.ecommerce.model.Usuario;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
final public class ComentarioPOST implements MasterPOST {
	
	@NotNull Usuario usuario;
	@NotNull Produto produto;
	@NotNull @NotEmpty @Length(min = 2) String texto;
	long reply;
}
