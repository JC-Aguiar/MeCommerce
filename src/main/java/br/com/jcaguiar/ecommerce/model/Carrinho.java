package br.com.jcaguiar.ecommerce.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**<h1>CONCEITO</h1>
 * Esta classe intermedia Usuários/Clientes e os Pedidos. <br>
 * Para facilitar a compra dos Usuários/CLientes, o carrinho agrupa produtos que se dejsea comprar em algum momento <br>
 * Produtos no carrinho ainda estão vulneráveis a alterações como preço e quantidade em estoque. <br>
 * Sabendo disso que existem os avisos. Mensagens que buscam alertar o usuários de toda e qualquer alterações. <br>
 * <h1>ATRIBUTOS</h1>
 * <b>id:</b> identificador Long do registro no banco de dados <br>
 * <b>item:</b> lista de objetos CarrinhoItem, identificam os produtos selecionados e a respectiva quantidade <br>
 * <b>avisos:</b> lista objetos  CarrinhoAviso, mensagens improtantes relacionadas aos produtos no carrinho <br>
 * <b>total:</b> valor BigDecimal total de produtos no carrinho <br>
 * <b>quantidade:</b> número Short de todos de produtos no carrinho <br>
 * @author João MC Aguiar
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity(name = "carrinho")
final public class Carrinho implements Entidade<Long> {

	/**
	 * TODO: avisos que alertam de promoções do mesmo fornecedor.
	 */

	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL)
	private final List<CarrinhoItem> item = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private final List<CarrinhoAviso> avisos = new ArrayList<>();

	@Column(nullable = false)
	private BigDecimal total;

	@Column(nullable = false)
	private Short quantidade;

}


