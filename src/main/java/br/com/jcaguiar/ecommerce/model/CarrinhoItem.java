package br.com.jcaguiar.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

/**<h1>CONCEITO</h1>
 * Agrupadores de Produtos e sua respectiva quantidade. <br>
 * Os produtos listados nessa classe são produtos da entidade PedidoProduto, no qual estão á salvo de alterções
 * psoteriores. <br>
 * <h1>ATRIBUTOS</h1>
 * <b>id:</b> identificador Long desse registro no banco de dados <br>
 * <b>carrinho:</b> objeto Carrinho a qual esse agrupador está vinculado <br>
 * <b>produto:</b> objeto Produto que foi selecionado <br>
 * <b>quantidade:</b> número Short de quantos produtos iguais foram selecionados <br>
 * <b>total:</b> BigDecimal do valor total <br>
 * @author João MC Aguiar
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Entity(name = "carrinho_item")
public class CarrinhoItem extends EntidadeData<Long> {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Carrinho carrinho;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Produto produto;

	@Column(nullable = false)
	private Short quantidade;

	@Column(nullable = false)
	private BigDecimal total;
}
