package br.com.jcaguiar.ecommerce.security;

import br.com.jcaguiar.ecommerce.dto.MasterPOST;
import br.com.jcaguiar.ecommerce.projection.UsuarioGET;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public final class TokenDto implements MasterPOST {

	private Map<String, String> token = new HashMap<>() {{
        put("tipo", "");
        put("code", "");
    }};
    private UsuarioGET usuario;

	public TokenDto(String code, String tipo, UsuarioGET usuario) {
		this.token.put("code", code);
		this.token.put("tipo", tipo);
        this.usuario = usuario;
	}
}
