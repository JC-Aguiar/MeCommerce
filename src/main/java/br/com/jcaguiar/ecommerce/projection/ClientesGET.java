package br.com.jcaguiar.ecommerce.projection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
final public class ClientesGET implements MasterGET {

	private String cpf;
	private String nome;
	private String phone;
	private String sobrenome;
}
