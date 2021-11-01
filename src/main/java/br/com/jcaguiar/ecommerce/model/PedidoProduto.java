package br.com.jcaguiar.ecommerce.model;

import br.com.jcaguiar.ecommerce.util.JsonConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@Data									//TODO: certificar do alerta -> conflito na JPA usando @Data
@EqualsAndHashCode(callSuper = true)	//TODO: certificar do alerta -> conflito na JPA usando @EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@Entity(name = "pedido_produto")
final public class PedidoProduto extends EntidadeData<Integer> {

	//TODO: certificar de que JsonConveter funciona

	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;

	//------------------------------------------------------------------------------------------------------------------
	//ATRIBUTOS DIRETOS
	@Convert(converter = JsonConverter.class)
	@Column(nullable = false)
	private Categoria categoria;

	@Convert(converter = JsonConverter.class)
	@Column(nullable = false)
	private Marca marca;

	@Column(nullable = false) private String nome;
	@Column(nullable = false) private String descricao;
	@Column(nullable = false) private String modelo;
	@Column(nullable = false) private BigDecimal valor;
	@Column(nullable = false) private short estoque;
	@Column(nullable = false) private String tamanho;
	@Column(nullable = false) private String medidas;
	@Column(nullable = false) private String material;
	@Column(nullable = false) private String codigo;

	//------------------------------------------------------------------------------------------------------------------
	//ATRIBUTOS INDIRETOS
	@Convert(converter = JsonConverter.class)
	@Column(nullable = false)
	private Fornecedor fornece;

	@Convert(converter = JsonConverter.class)
	@Column(nullable = false)
	private ProdutoImagem imagem;
}
