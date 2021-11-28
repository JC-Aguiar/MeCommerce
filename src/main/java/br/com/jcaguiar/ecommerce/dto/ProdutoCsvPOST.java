package br.com.jcaguiar.ecommerce.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
final public class ProdutoCsvPOST implements MasterPOST {

	@Size(min = 3) String setorNome;
	@Size(min = 3) String categoriaNome;
	@Size(min = 3) String nome;
	@Size(min = 3) String descricao;
	final List<String> marcaNome = new ArrayList<>();
	@Size(min = 3) String modelo;
	@Min(value = 1) BigDecimal valor;
	@Min(value = 1) Short estoque;
	@Size(min = 3) String materiais;
	@Size(min = 3) String medidas;
	@Size(min = 1, max = 1) String tamanho;
	@Size(min = 3) String ean;
	final List<String> imagem = new ArrayList<>();

}
