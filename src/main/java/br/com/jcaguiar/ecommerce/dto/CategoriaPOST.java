package br.com.jcaguiar.ecommerce.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public final class CategoriaPOST implements MasterPOST {
	
	@NotNull @NotEmpty @Length(min = 1) String setor;
	@NotNull @NotEmpty @Length(min = 3) String nome;

}
