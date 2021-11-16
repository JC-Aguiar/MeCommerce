package br.com.jcaguiar.ecommerce.dto;

import br.com.jcaguiar.ecommerce.model.Endereco;
import br.com.jcaguiar.ecommerce.model.Fornece;
import lombok.Getter;

import java.util.List;

@Getter
final public class FornecedorPOST implements MasterPOST {

	String nome;
	List<Fornece> fornece;
	Endereco endereco;
}
