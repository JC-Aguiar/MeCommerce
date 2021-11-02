package br.com.jcaguiar.ecommerce.model;

import br.com.jcaguiar.ecommerce.util.DataFormato;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * TODO: terminar javadoc
 *
 * @author JM Costal Aguiar
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "pedido_item")
final public class PedidoItem implements Entidade<Long> {

	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private PedidoProduto produto;

	@ManyToOne(fetch = FetchType.LAZY)
	private Pedido pedido;

	@Column(nullable = false)
	private short quantidade;

	@Column(nullable = false)
	private BigDecimal total;

	private final LocalDateTime data_carrinho = DataFormato.now();
}
