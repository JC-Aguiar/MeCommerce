package br.com.jcaguiar.ecommerce.model;

import br.com.jcaguiar.ecommerce.util.MapJsonConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
/**
 * TODO: terminar javadoc
 *
 * @author JM Costal Aguiar
 */
@Data									//TODO: certificar do alerta -> conflito na JPA usando @Data
@EqualsAndHashCode(callSuper = true)	//TODO: certificar do alerta -> conflito na JPA usando @EqualsAndHashCode
@SuperBuilder
@Entity(name = "pedido_produto")
final public class PedidoProduto extends EntidadeData<Integer> {

	//TODO: certificar de que JsonConveter funciona

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	//ATRIBUTOS DIRETOS
	@Column(nullable = false)
	private String categoria;

	@Convert(converter = MapJsonConverter.class)
	@Column(nullable = false)
	private List<String> marca;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String descricao;

	@Column(nullable = false)
	private String modelo;

	@Column(nullable = false)
	private BigDecimal valor;

	@Column(nullable = false)
	private short estoque;

	@Column(nullable = false)
	private String tamanho;

	@Column(nullable = false)
	private String medidas;

	@Column(nullable = false)
	private String material;

	@Column(nullable = false)
	private String codigo;

	//ATRIBUTOS INDIRETOS
	@Convert(converter = MapJsonConverter.class)
	@Column(nullable = false)
	private List<String> fornecedor;

	@Convert(converter = MapJsonConverter.class)
	@Column(nullable = false)
	private List<String> imagens;
}
