package br.com.jcaguiar.ecommerce.dto;

import br.com.jcaguiar.ecommerce.model.Empresa;
import br.com.jcaguiar.ecommerce.model.Endereco;
import br.com.jcaguiar.ecommerce.model.Usuario;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
final public class TransportadorPOST implements MasterPOST {
	
	@NotNull Empresa<Short> empresa;
	@NotNull Usuario usuario;
	@NotNull Endereco endereco;

}
