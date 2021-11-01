package br.com.jcaguiar.ecommerce.model;

import br.com.jcaguiar.ecommerce.util.DataFormato;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Entity(name = "pedido")
final public class Pedido extends EntidadeData<Long> {

	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//------------------------------------------------------------------------------------------------------------------
	//INFORMAÇÕES DO PEDIDO
	@Column(nullable = false, unique = true) private String numero;
	@Column(nullable = false) private short status;
	@Column(nullable = false) private short quantidadeProdutos;
	@Column(nullable = false) private BigDecimal total;
	private LocalDateTime data_pedido = DataFormato.now();

	@OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private final List<Pagamento> pagamento = new ArrayList<>();

	@OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private final List<NotaFiscal> nf = new ArrayList<>();

	//------------------------------------------------------------------------------------------------------------------
	//INFORMAÇÕES DO PRODUTO
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<PedidoItem> itens = new ArrayList<>();

	//------------------------------------------------------------------------------------------------------------------
	//INFORMAÇÕES DO COMPRADOR
	@ManyToOne(fetch = FetchType.LAZY) private Cliente cliente;
	@Column(nullable = false) private String compradorNome;
	@Column(nullable = false) private String compradorDocumento;
	@Column(nullable = false) private String compradorEndereco;
	@Column(nullable = false) private String compradorCidade;
	@Column(nullable = false) private String compradorCep;
	@Column(nullable = false) private String compradorBairro;
	@Column(nullable = false) private String compradorPais;
	
}
