package br.com.jcaguiar.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.*;

import br.com.jcaguiar.ecommerce.util.DataFormato;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
	private Produto produto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Carrinho carrinho;

	@Column(nullable = false)
	private short quantidade;

	@Column(nullable = false)
	private BigDecimal total;
}
