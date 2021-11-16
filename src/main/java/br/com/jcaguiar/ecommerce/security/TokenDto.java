package br.com.jcaguiar.ecommerce.security;

import br.com.jcaguiar.ecommerce.dto.MasterPOST;
import lombok.Getter;

@Getter
public final class TokenDto implements MasterPOST {

	public String token;
	public String tipo;
	
	public TokenDto(String token, String tipo) {
		this.token = token;
		this.tipo = tipo;
	}
}
