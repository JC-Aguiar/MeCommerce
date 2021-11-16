package br.com.jcaguiar.ecommerce.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

final public class EmpresaPOST implements MasterPOST {
	
	@NotNull @NotEmpty @Length(min = 2) String nome;
	@NotNull @NotEmpty @Length(min = 4) String razao_social;
	@NotNull @NotEmpty @Length(min = 9, max = 14) String doc;
	@Length(min = 9, max = 14) String ie;
	boolean matriz;

}
