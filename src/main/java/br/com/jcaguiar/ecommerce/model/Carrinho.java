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

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity(name = "carrinho")
final public class Carrinho implements Entidade<Long> {

	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL)
	private final List<CarrinhoItem> item = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private final List<CarrinhoAviso> avisos = new ArrayList<>();

	@Column(nullable = false)
	private BigDecimal total;

	@Column(nullable = false)
	private short quantidade;

}


