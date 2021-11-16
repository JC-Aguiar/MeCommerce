package br.com.jcaguiar.ecommerce.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ErroCadastroPOST implements MasterPOST {

	private final String campo;
	private final String erro;
	
}
