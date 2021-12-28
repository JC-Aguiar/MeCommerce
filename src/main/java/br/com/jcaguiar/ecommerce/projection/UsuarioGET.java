package br.com.jcaguiar.ecommerce.projection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
final public class UsuarioGET implements MasterGET {

	private String email;
    private String foto;
    private ClientesGET cliente;
}
