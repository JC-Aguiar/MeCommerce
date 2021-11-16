package br.com.jcaguiar.ecommerce.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
final public class UsuarioPOST implements MasterPOST {
	
	@Email String email;
	@NotBlank @Length(min = 6) String senha;

}
