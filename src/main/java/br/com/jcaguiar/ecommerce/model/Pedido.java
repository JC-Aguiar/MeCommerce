package br.com.jcaguiar.ecommerce.model;

import br.com.jcaguiar.ecommerce.util.DataFormato;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**<h1>CONCEITO</h1>
 * Produtos que foram oficialmente solciitados para compra. <br>
 * Os produtos que antes estavam no Carrinho são movidos para o Pedido. <br>
 * Diferente do Carrinho, a confirmação de um Pedido impede que seus produtos sofram alterações posteriores. <br>
 * <h1>ATRIBUTOS</h1>
 * <b>id:</b> id da entidade no banco <br>
 * <b>numero:</b> identificador único que identifica o pedido <br>
 * <b>status:</b> se o pedido está PENDENTE, FATURADO, EXPEDIDO, TRANSPORTE ou ENTREGUE <br>
 * <b>quantidade:</b> soma de todos os produtos pedidos (só aceita positivo) <br>
 * <b>total:</b> valor total dos produtos pedidos (só aceita positivo) <br>
 * <b>data_pedido:</b> data-horário em que o pedido foi feito <br>
 * <b>pagamento:</b> lista de objetos Pagamento realizados para faturar o pedido <br>
 * <b>nf:</b> lista de objetos NotaFiscal faturadas para pedido  <br>
 * <b>itens:</b> lista de objetos PedidoItem, identificam os produtos pedidos e a respectiva quantidade <br>
 * <b>cliente:</b> objeto Cliente que identifica o comprador <br>
 * <b>data_validade:</b> prazo final para se efetuar o pagamento <br
 * @author João MC Aguiar
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Entity(name = "pedido")
final public class Pedido extends EntidadeData<Long> {

	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//INFORMAÇÕES DO PEDIDO
	@Column(nullable = false, unique = true)
	private String numero;

	@Column(nullable = false)
	private short status;

	@Column(nullable = false)
	private short quantidadeProdutos;

	@Column(nullable = false)
	private BigDecimal total;

	private LocalDateTime data_pedido = DataFormato.now();

	@OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
	private final List<Pagamento> pagamento = new ArrayList<>();

	@OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
	private final List<NotaFiscal> nf = new ArrayList<>();

	//INFORMAÇÕES DO PRODUTO
	@OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PedidoItem> itens = new ArrayList<>();

	//INFORMAÇÕES DO COMPRADOR
	//TODO: repensar estrutura de armazenamento
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;

	@Column(nullable = false)
	private LocalDate data_validade;

	//	@Column(nullable = false)
	//	private String compradorNome;

	//	@Column(nullable = false)
	//	private String compradorDocumento;

	//	@Column(nullable = false)
	//	private String compradorEndereco;

	//	@Column(nullable = false)
	//	private String compradorCidade;

	//	@Column(nullable = false)
	//	private String compradorCep;

	//	@Column(nullable = false)
	//	private String compradorBairro;

	//	@Column(nullable = false)
	//	private String compradorPais;
	
}
