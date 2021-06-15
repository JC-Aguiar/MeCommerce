package br.com.jcaguiar.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Entity(name = "pedido")
final public class Pedido extends EntidadeData<Long> {

	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Cliente cliente;
	
	@OneToOne
	private Carrinho carrinho;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private final List<Pagamento> pagamento = new ArrayList<>();
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private final List<NotaFiscal> nf = new ArrayList<>();
	private short status;
	private BigDecimal total;
	private final LocalDateTime data_pedido = LocalDateTime.now();	
}
